package frc.robot.commands;

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
    // private UserAnalog backwardsTurbo;
    // private UserAnalog forwardTurbo;
    private UserAnalog joystickRotation;
    private double max_spd = .65;

    /**
     * initalizes drive command from given drivetrain, speed, and rotation currently actual speed is back speed - front
     * speed, then multiplied by the maximum speed
     * 
     * @param driveTrain       drivetrain to bind
     * @param speed            initial speed
     * @param backwardsTurbo   speed backwards
     * @param forwardTurbo     speed forwards
     * @param joystickRotation joystick input (currently unused)
     */
    public DriveCommand(
        DriveTrain driveTrain,
        UserAnalog speed,
        // UserAnalog backwardsTurbo,
        // UserAnalog forwardTurbo,
        UserAnalog joystickRotation
    ) {
        this.speed = speed;
        this.driveTrain = driveTrain;
        this.addRequirements(driveTrain);
        // this.backwardsTurbo = backwardsTurbo;
        // this.forwardTurbo = forwardTurbo;
        this.joystickRotation = joystickRotation;
    }

    /**
     * called repeatedly when command is schedules to run
     */

    @Override
    public void execute() {
        double spd = speed.get();
        spd *= Math.abs(spd);
        double rotation = -joystickRotation.get();

        // RC_MAX_SPEED is the max speed set in Shuffle Board
        max_spd = RobotContainer.RC_MAX_SPEED > 1 ? .75 : RobotContainer.RC_MAX_SPEED;

        if (Math.abs(spd) < .05) {
            spd = 0;
        }
        spd = spd * max_spd;

        if (Math.abs(rotation) < .05) {
            rotation = 0;
        }

        // SmartDashboard.putNumber("Target Speed Drive Command", spd);
        // SmartDashboard.putNumber("max speed mult", max_spd);
        driveTrain.arcadeDrive(spd, .8 * rotation);

    }

    // the current speed, the current stick position
}
