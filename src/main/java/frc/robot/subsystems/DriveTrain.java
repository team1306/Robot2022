package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Used by DriveTrain command to move robot Calculates output for each side of the drivetrain
 */
public class DriveTrain extends SubsystemBase {
    private WPI_TalonFX leftLeader;
    private WPI_TalonFX leftFollower;

    private WPI_TalonFX rightLeader;
    private WPI_TalonFX rightFollower;
    private AHRS gyro = new AHRS();
    private final DifferentialDriveOdometry m_odometry;




    /**
     * Initializing drive train and talonmFX settings
     */
    public DriveTrain() {
        /*
         * Old drive train initialization
         * leftLeader = new TalonSRX(LEFT_DRIVE_LEADER);
         * rightLeader = new TalonSRX(RIGHT_DRIVE_LEADER); 
         * leftFollower = new VictorSPX(LEFT_DRIVE_FOLLOWER); 
         * rightFollower = new VictorSPX(RIGHT_DRIVE_FOLLOWER);
         */
        leftLeader = initWPITalonFX(DRIVE_LEFT_LEADER_ID);
        rightLeader = initWPITalonFX(DRIVE_RIGHT_LEADER_ID);
        leftFollower = initWPITalonFX(DRIVE_LEFT_FOLLOWER_ID);
        rightFollower = initWPITalonFX(DRIVE_RIGHT_FOLLOWER_ID);


        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);

        m_odometry = new DifferentialDriveOdometry(gyro.getRotation2d());


        gyro.reset();
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

        leftLeader.set(-rightMotorOutput);
        rightLeader.set(leftMotorOutput);;
        SmartDashboard.putNumber("LeftVelocity", getWheelSpeeds().leftMetersPerSecond / Constants.WHEEL_CIRCUMFERENCE);
        SmartDashboard.putNumber(
            "RightVelocity",
            getWheelSpeeds().rightMetersPerSecond / Constants.WHEEL_CIRCUMFERENCE
        );
        SmartDashboard.putNumber("LeftEncoder things", leftLeader.getSelectedSensorVelocity());
        SmartDashboard.putNumber("Gyro heading", gyro.getYaw());

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
    public void testMotors(double leftFront, double rightFront, double leftFollow, double rightFollow) {
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
        // Disabling gyro
        var rot = new Rotation2d(0);
        double ldist = getLeftDistance();
        double rdist = getRightDistance();
        // monkaS
        var field = new Field2d();
        field.setRobotPose(ldist, rdist, rot);
        SmartDashboard.putData(field);
        m_odometry.update(rot, ldist, rdist);

    }

    public Pose2d getPose() {
        return m_odometry.getPoseMeters();
    }

    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        // encoder ticks per 100 ms
        // rotations per second
        // meters per second
        double leftSpeed = leftLeader.getSelectedSensorVelocity()
            * (10.0 / 2048 / 5.88)
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
        m_odometry.resetPosition(pose, gyro.getRotation2d());
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
}
