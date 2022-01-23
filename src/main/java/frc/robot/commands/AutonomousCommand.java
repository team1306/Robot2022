package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import com.kauailabs.navx.frc.AHRS;

/**
 * shell code for autonomous command
 */
public class AutonomousCommand extends CommandBase {
    private final AHRS navx;

    /**
     * initializes autonomous command
     */
    public AutonomousCommand() {
        this.navx = null;
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
    public void execute() {}

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
}
