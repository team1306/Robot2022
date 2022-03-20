package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class AutoDriveTrain extends CommandBase {
    private DriveTrain driveTrain;
    private Timer timer;
    private double timeoutS;

    public AutoDriveTrain(DriveTrain driveTrain, double timeoutS) {
        this.timeoutS = timeoutS;
        this.driveTrain = driveTrain;
        this.addRequirements(driveTrain);
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        driveTrain.arcadeDrive(0.2, 0);
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
