package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.UserAnalog;
import frc.robot.subsystems.DriveTrain;

/**
 * Command to drive the robot based on controller input
 */
public class DriveCommand extends CommandBase {

    private UserAnalog speed;
    private DriveTrain driveTrain;
    private UserAnalog leftRotation;
    private UserAnalog rightRotation;

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
        UserAnalog rightRotation
    ) {
        this.speed = speed;
        this.driveTrain = driveTrain;
        this.driveTrain.setDefaultCommand(this);
        this.addRequirements(driveTrain);
        this.leftRotation = leftRotation;
        this.rightRotation = rightRotation;
    }

    /**
     * called repeatedly when command is schedules to run
     */
    @Override
    public void execute() {
        double spd = speed.get();
        double rotation = rightRotation.get() - leftRotation.get();
        driveTrain.arcadeDrive(spd * spd, rotation * rotation);
    }
}
