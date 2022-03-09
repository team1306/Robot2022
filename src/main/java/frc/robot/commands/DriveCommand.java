package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.UserAnalog;
import frc.robot.subsystems.DriveTrain;

/**
 * Command to drive the robot based on controller input
 */
public class DriveCommand extends CommandBase {

    private UserAnalog speed;
    // private UserAnalog rightSpeed;
    private DriveTrain driveTrain;
    private UserAnalog leftRotation;
    private UserAnalog rightRotation;
    private UserAnalog joystickRotation;
    private boolean useJoystickRotation = true;

    /**
     * initalizes drive command from given drivetrain, speed, and rotation
     * 
     * @param driveTrain    drivetrain to bind
     * @param speed         initial speed
     * @param leftRotation  initial left rotation
     * @param rightRotation initial right rotation
     */
    public DriveCommand(
        DriveTrain driveTrain,
        UserAnalog speed,
        UserAnalog leftRotation,
        UserAnalog rightRotation,
        UserAnalog joystickRotation
    ) {
        this.speed = speed;
        this.driveTrain = driveTrain;
        this.addRequirements(driveTrain);
        this.leftRotation = leftRotation;
        this.rightRotation = rightRotation;
        this.joystickRotation = joystickRotation;
    }

    /**
     * called repeatedly when command is schedules to run
     */

    @Override
    public void execute() {
        double spd = speed.get();
        double rotation;
        if (useJoystickRotation) {
            rotation = joystickRotation.get();
        } else {
            rotation = rightRotation.get() - leftRotation.get();

        }

        if (Math.abs(spd) > .2)
            spd = 1.0 / 6 * Math.signum(spd) + 5.0 / 6 * spd * Math.abs(spd);

        // if (Math.abs(rotation) > .2)
        // rotation = .2 + (rotation - .2) * Math.abs(rotation - .2);

        // spd *= Math.abs(spd);
        rotation *= Math.abs(rotation);
        if (useJoystickRotation) {
            // double addedTriggerSpeed = rightRotation.get() - leftRotation.get();
            // double targetSpeed = Math.copySign(Math.max(Math.abs(spd), Math.abs(addedTriggerSpeed)), spd);
            driveTrain.arcadeDrive(.5 * spd, .5 * rotation);
        } else {
            driveTrain.arcadeDrive(.5 * spd, .5 * rotation);

        }
    }

    // the current speed, the current stick position
}
