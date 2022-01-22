package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.utils.UserAnalog;
import frc.robot.subsystems.DriveTrain;

/**
 * Command to drive the robot based on controller input
 */
public class DriveCommand extends CommandBase {

    private UserAnalog speed;
    private UserAnalog rotation;
    private DriveTrain driveTrain;

    /**
     * initalizes drive command from given drivetrain, speed, and rotation
     * 
     * @param driveTrain drivetrain to bind
     * @param speed      initial speed
     * @param rotation   initial rotation
     */
    public DriveCommand(
        DriveTrain driveTrain,
        UserAnalog speed,
        UserAnalog rotation
    ) {
        this.speed = speed;
        this.rotation = rotation;
        this.driveTrain = driveTrain;
        this.driveTrain.setDefaultCommand(this);
        this.addRequirements(driveTrain);
    }

    /**
     * called repeatedly when command is schedules to run
     */
    @Override
    public void execute() {
        driveTrain.arcadeDrive(speed.get(), rotation.get());
    }
}
