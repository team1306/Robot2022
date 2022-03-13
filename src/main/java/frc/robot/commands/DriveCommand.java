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
    private UserAnalog backwardsTurbo;
    private UserAnalog forwardTurbo;
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
        UserAnalog backwardsTurbo,
        UserAnalog forwardTurbo,
        UserAnalog joystickRotation
    ) {
        this.speed = speed;
        this.driveTrain = driveTrain;
        this.addRequirements(driveTrain);
        this.backwardsTurbo = backwardsTurbo;
        this.forwardTurbo = forwardTurbo;
        this.joystickRotation = joystickRotation;
    }

    /**
     * called repeatedly when command is schedules to run
     */

    @Override
    public void execute() {
        double spd = speed.get();
        double rotation = joystickRotation.get();

        if (Math.abs(forwardTurbo.get() - backwardsTurbo.get()) > .05) {
            driveTrain.arcadeDrive(forwardTurbo.get() - backwardsTurbo.get(), 0);
        } else {
            if (Math.abs(spd) > .2)
                spd = 1.0 / 6 * Math.signum(spd) + 5.0 / 6 * spd * Math.abs(spd);

            // if (Math.abs(rotation) > .2)
            // rotation = .2 + (rotation - .2) * Math.abs(rotation - .2);

            // spd *= Math.abs(spd);
            rotation *= Math.abs(rotation);
            if (useJoystickRotation) {
                // double addedTriggerSpeed = rightRotation.get() - leftRotation.get();
                // double targetSpeed = Math.copySign(Math.max(Math.abs(spd), Math.abs(addedTriggerSpeed)), spd);
                driveTrain.arcadeDrive(.25 * spd, .25 * rotation);
            } else {
                driveTrain.arcadeDrive(.25 * spd, .25 * rotation);

            }
        }



    }

    // the current speed, the current stick position
}
