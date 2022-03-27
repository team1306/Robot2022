package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoDriveTrain extends CommandBase {
    private DriveTrain driveTrain;
    private Timer timer;
    private boolean direction;
    private double timeoutS;
    private boolean rotate180;

    public AutoDriveTrain(DriveTrain driveTrain, double timeoutS, boolean direction, boolean rotate180) {
        this.timeoutS = timeoutS;
        this.direction = direction;
        this.driveTrain = driveTrain;
        this.rotate180 = rotate180;
        this.addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        if(!rotate180) {
            if(direction) {
                driveTrain.arcadeDrive(0.2, 0);
    
            } else {
                driveTrain.arcadeDrive(-0.2, 0);
    
            }
        } else {
            driveTrain.arcadeDrive(0, .2);
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeoutS);
    }
}
