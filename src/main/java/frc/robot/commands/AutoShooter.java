package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AutoShooter extends CommandBase {
    private Timer timer;
    private double timeoutS;
    private Shooter shooter;

    public AutoShooter(Shooter shooter, double timeoutS) {
        this.shooter = shooter;
        this.timeoutS = timeoutS;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }

    @Override
    public void execute() {
        shooter.moveMotor(2, false, false);
    }

    @Override
    public void end(boolean interrupted) {
        shooter.moveMotor(0, false, false);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeoutS);
    }
}
