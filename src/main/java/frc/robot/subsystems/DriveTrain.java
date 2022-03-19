package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import java.util.concurrent.locks.LockSupport;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Used by DriveTrain command to move robot Calculates output for each side of the drivetrain
 */
@SuppressWarnings("unused")
public class DriveTrain extends SubsystemBase implements AutoCloseable {
    private WPI_TalonFX leftLeader;
    private WPI_TalonFX leftFollower;

    private WPI_TalonFX rightLeader;
    private WPI_TalonFX rightFollower;
    // private AHRS gyro = new AHRS();
    private final DifferentialDriveOdometry m_odometry;
    private PIDController controller;

    private double previousRightPercentOutput;

    private double previousLeftPercentOutput;



    /**
     * Initializing drive train and talonmFX settings
     */
    public DriveTrain() {
        leftLeader = initWPITalonFX(DRIVE_LEFT_LEADER_ID);
        rightLeader = initWPITalonFX(DRIVE_RIGHT_LEADER_ID);
        leftFollower = initWPITalonFX(DRIVE_LEFT_FOLLOWER_ID);
        rightFollower = initWPITalonFX(DRIVE_RIGHT_FOLLOWER_ID);

        // leftLeader.config_kP(0, .5);
        // rightLeader.config_kP(0, .5);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        m_odometry = new DifferentialDriveOdometry(new Rotation2d());


        // leftLeader.config_kP(0, .25);
        // leftLeader.config_kI(0, 0);
        // leftLeader.config_kD(0, 0);
        // leftLeader.setControlFramePeriod(0, 20);
        // leftLeader.configClosedloopRamp(1);

        // rightLeader.config_kP(0, .25);
        // rightLeader.config_kI(0, 0);
        // rightLeader.config_kD(0, 0);
        // rightLeader.setControlFramePeriod(0, 20);
        // rightLeader.configClosedloopRamp(1);
        // gyro.reset();
        previousRightPercentOutput = 0;
        previousLeftPercentOutput = 0;
    }

    /**
     * Sets the talonmFX speeds for the given speed and rotation
     * 
     * @param speed    speed from a joystick input
     * @param rotation rotation from joystick triggers
     */
    public void arcadeDrive(double speed, double rotation) {
        double maxInput = Math.copySign(Math.max(Math.abs(speed), Math.abs(rotation)), speed);
        double leftMotorOutput;
        double rightMotorOutput;

        // speed is -1 to 1, rotation is also -1 to 1
        if (speed >= 0) {
            if (rotation >= 0) {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            } else {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            }
        } else {
            if (rotation >= 0) {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            }
        }

        leftMotorOutput = limitAcceleration(leftMotorOutput, previousLeftPercentOutput);
        rightMotorOutput = limitAcceleration(rightMotorOutput, previousRightPercentOutput);


        System.out.printf("left: %.02f, right : %.02f\n", leftMotorOutput, rightMotorOutput);

        leftLeader.set(-leftMotorOutput);
        rightLeader.set(rightMotorOutput);

        previousLeftPercentOutput = leftMotorOutput;
        previousRightPercentOutput = rightMotorOutput;
    }

    /**
     * limits acceleration for the robot, should prevent rocking does not prevent rapid decceleration
     * 
     * @param currentTargetPercentOutput target output for motor that user inputed
     * @param previousPercentOutput      previous outputed target for motor
     * @return a target motor value
     */
    public double limitAcceleration(
        double currentTargetPercentOutput,
        double previousPercentOutput
    ) {
        // change that the user wants
        double error = currentTargetPercentOutput - previousPercentOutput;

        // target is going towards 0
        boolean isDecel = Math.abs(currentTargetPercentOutput) < .05;

        if (isDecel) { return currentTargetPercentOutput; }


        // divide that change over a period of time
        // if the change in acceleration is too large positively, accelerate slower
        if (error > (Constants.TIME_PER_LOOP / Constants.TIME_TO_FULL_SPEED)) {
            return previousPercentOutput + Constants.TIME_PER_LOOP / Constants.TIME_TO_FULL_SPEED;
        }

        // the change in acceleration is too large negatively, accelerate to the negative direction slower
        if (error < -(Constants.TIME_PER_LOOP / Constants.TIME_TO_FULL_SPEED)) {
            return previousPercentOutput - Constants.TIME_PER_LOOP / Constants.TIME_TO_FULL_SPEED;
        }

        return currentTargetPercentOutput;
    }

    /**
     * Test the lead motors and folowing motors test to see if initialization process for setting 'following' is correct
     * 
     * @param left  left talonmFX output
     * @param right right talonmFX output
     */
    public void testDrive(double left, double right) {
        leftLeader.set(ControlMode.PercentOutput, left);
        rightLeader.set(ControlMode.PercentOutput, right);
    }

    /**
     * testing method for eaching individual talonmFX only works if constructor does not set follow
     * 
     * @param leftFront   left front talonmFX output
     * @param rightFront  right front talonmFX output
     * @param leftFollow  left follow talonmFX output
     * @param rightFollow right followe talonmFX output
     */
    public void testMotors(
        double leftFront,
        double rightFront,
        double leftFollow,
        double rightFollow
    ) {
        leftLeader.set(ControlMode.PercentOutput, leftFront);
        rightLeader.set(ControlMode.PercentOutput, rightFront);

        // experimental
        leftFollower.follow(leftFollower);
        rightFollower.follow(rightFollower);

        leftFollower.set(ControlMode.PercentOutput, leftFollow);
        rightFollower.set(ControlMode.PercentOutput, rightFollow);

        // experimental
        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);
    }

    /**
     * take encoder ticks displacement of left wheels and convert into meters
     * 
     * @return displacement of left wheels in meters
     */
    public double getLeftDistance() {
        double encoderTicks = leftLeader.getSelectedSensorPosition();
        double distance = encoderTicks / 2048 / 5.88 * Constants.WHEEL_CIRCUMFERENCE;
        return distance;
    }

    /**
     * Take encoder ticks displacement of right wheels and convert into meters
     * 
     * @return displacement of right wheels in meters
     */
    public double getRightDistance() {
        double encoderTicks = rightLeader.getSelectedSensorPosition();
        double distance = encoderTicks / 2048 / 5.88 * Constants.WHEEL_CIRCUMFERENCE;
        return distance;
    }

    @Override
    public void periodic() {
        m_odometry.update(new Rotation2d(), getLeftDistance(), getRightDistance());

    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        // encoder ticks per 100 ms
        // rotations per second
        // meters per second
        double leftSpeed = leftLeader.getSelectedSensorVelocity()
            * (10.0 / 2048 / 5.88) // dm -> m, et -> rot, gear ratio
            * Constants.WHEEL_CIRCUMFERENCE;
        SmartDashboard.putNumber("leftSpeed", leftSpeed);
        double rightSpeed = rightLeader.getSelectedSensorVelocity()
            * (10.0 / 2048 / 5.88)
            * Constants.WHEEL_CIRCUMFERENCE;
        SmartDashboard.putNumber("rightSpeed", rightSpeed);

        return new DifferentialDriveWheelSpeeds(leftSpeed, rightSpeed);
    }

    public void resetOdometry(Pose2d pose) {
        resetEncoders();
        m_odometry.resetPosition(pose, new Rotation2d());
    }

    public void resetEncoders() {
        leftLeader.setSelectedSensorPosition(0);
        rightLeader.setSelectedSensorPosition(0);
    }

    public void tankDriveVolts(double lVolts, double rVolts) {
        leftLeader.set(ControlMode.PercentOutput, lVolts / 12.0 * (46.0 / 48.0));
        rightLeader.set(ControlMode.PercentOutput, rVolts / 12.0);
        SmartDashboard.putNumber("lVolts", lVolts);
        SmartDashboard.putNumber("rVolts", rVolts);
    }

    /**
     * two params : speed, rotation use those two params to generate target state of robot, which means we need to know
     * the speed forward and rotational speed. Then compare those two speeds to the current speed of the robot, and find
     * the difference between the current speed between the target speed Adjust the target speed based on the current
     * speed and the difference. Basically arcadeDrive.
     */
    public void adjustedArcadeDrive(double speed, double rotation) {
        double maxInput = Math.copySign(Math.max(Math.abs(speed), Math.abs(rotation)), speed);
        double leftMotorOutput;
        double rightMotorOutput;
        if (speed >= 0) {
            if (rotation >= 0) {
                leftMotorOutput = speed;
                rightMotorOutput = speed - rotation;
            } else {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            }
        } else {
            if (rotation >= 0) {
                leftMotorOutput = speed + rotation;
                rightMotorOutput = maxInput;
            } else {
                leftMotorOutput = maxInput;
                rightMotorOutput = speed - rotation;
            }
        }
        DifferentialDriveWheelSpeeds currentSpeed = getWheelSpeeds();


        // Somehow the flipped output for the right motor is assined to the left motor.
        // Somehow it woirks in the original arcadeDrive.
        double desiredLeftOutput = rightMotorOutput;
        double desiredRightOutput = -leftMotorOutput;
        // System.out.println("current: " + lspeed + " " + rspeed);
        // System.out.println("desired: " + desiredLeftOutput + " " + desiredRightOutput);
        // P(ID?) Constants
        final double P = .5;
        // normalize speeds to [-1,1]
        double curLSpeed = currentSpeed.leftMetersPerSecond / Constants.MAX_SPEED_MPS;
        double curRSpeed = currentSpeed.rightMetersPerSecond / Constants.MAX_SPEED_MPS;
        // get error
        double leftErr = desiredLeftOutput - curLSpeed;// curLSpeed - desiredLeftOutput;
        double rightErr = desiredRightOutput - curRSpeed;// curRSpeed - desiredRightOutput;
        // got proportional offset
        double leftDiff = leftErr * P;
        double rightDiff = rightErr * P;

        // get final speeds;
        double leftSpeed = curLSpeed + leftDiff;
        double rightSpeed = curRSpeed + rightDiff;
        System.out.printf("left: %f , right : %f\n", leftSpeed, rightSpeed);
        leftLeader.set(leftSpeed);
        rightLeader.set(rightSpeed);
    }

    @Override
    public void close() throws Exception {
        leftLeader.close();
        rightLeader.close();
        leftFollower.close();
        rightFollower.close();
    }
}
