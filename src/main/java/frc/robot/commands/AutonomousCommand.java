package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

import com.kauailabs.navx.frc.AHRS;

/**
 * shell code for autonomous command
 */
public class AutonomousCommand extends CommandBase {
    private final AHRS navx;

    DriveTrain driveTrain;

    /**
     * initializes autonomous command
     */
    public AutonomousCommand(DriveTrain driveTrain) {
        this.navx = null;
        this.driveTrain = driveTrain;
        this.addRequirements(driveTrain);
        // driveTrain.setDefaultCommand(this);
    }

    /**
     * 
     */
    @Override
    public void initialize() {}

    /**
     * 
     */
    @Override
    public void execute() {
        driveTrain.arcadeDrive(1, 0);
    }

    /**
     * 
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * 
     */
    @Override
    public void end(boolean interrupted) {
        cancel();
    }

    public void remove() {}
}
