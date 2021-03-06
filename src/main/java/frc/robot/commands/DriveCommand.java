package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.UserAnalog;
import frc.robot.RobotContainer;
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
    private double max_spd = .65;

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
        double rotation = -joystickRotation.get();

        if (RobotContainer.RC_MAX_SPEED < .65 || RobotContainer.RC_MAX_SPEED > 1) {
            max_spd = .75;
        } else {
            max_spd = RobotContainer.RC_MAX_SPEED;
        }

        if (Math.abs(forwardTurbo.get() - backwardsTurbo.get()) > .05) {
            spd = max_spd * (backwardsTurbo.get() - forwardTurbo.get());
        } else {
            spd = 0;
        }

        if (Math.abs(rotation) < .05) {
            rotation = 0;
        }

        // SmartDashboard.putNumber("Target Speed Drive Command", spd);
        // SmartDashboard.putNumber("max speed mult", max_spd);
        driveTrain.arcadeDrive(spd, .8 * rotation);

    }

    // the current speed, the current stick position
}
