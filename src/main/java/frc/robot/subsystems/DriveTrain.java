package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used by DriveTrain command to move robot Calculates output for each side of the drivetrain
 */
public class DriveTrain extends SubsystemBase {
    private TalonFX leftLeader;
    private TalonFX leftFollower;

    private TalonFX rightLeader;
    private TalonFX rightFollower;

    /**
     * Initializing drive train and talonmFX settings
     */
    public DriveTrain() {
        leftLeader = initMotor(DRIVE_LEFT_LEADER_ID);
        rightLeader = initMotor(DRIVE_RIGHT_LEADER_ID);
        leftFollower = initMotor(DRIVE_LEFT_FOLLOWER_ID);
        rightFollower = initMotor(DRIVE_RIGHT_FOLLOWER_ID);

        leftFollower.follow(leftLeader);
        rightFollower.follow(rightLeader);
    }

    /**
     * Sets the talonmFX speeds for the given speed and rotation
     * 
     * @param speed    speed from a joystick input
     * @param rotation rotation from joystick triggers
     */
    public void arcadeDrive(double speed, double rotation) {
        double maxInput = Math.copySign(
            Math.max(Math.abs(speed), Math.abs(rotation)),
            speed
        );
        double leftMotorOutput;
        double rightMotorOutput;

        /*
         * perhaps make into single if-else statement later, like: if (Math.sign(speed) ==
         * Math.signum(rotation)) <case 1> else <case2>
         */
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

        leftLeader.set(ControlMode.PercentOutput, leftMotorOutput);
        rightLeader.set(ControlMode.PercentOutput, rightMotorOutput);
    }

    /**
     * Test the lead motors and folowing motors test to see if initialization process for setting
     * 'following' is correct
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
}
