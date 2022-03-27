package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AutoShooter extends CommandBase {
    private Timer timer;
    private double timeoutS;
    private Shooter shooter;
    private double intakeSpeed;
    private final int NOT_SHOOTING = 0,  NEAR_AUTO = 4, FAR_AUTO = 5;
    private int state;

    /**
     * 
     * @param shooter 
     * @param timeoutS number of seconds the command should run for
     * @param state 0 is off, 4 is close, 5 is far
     * @param isIntaking whether it should be intaking
     */
    public AutoShooter(Shooter shooter, double timeoutS, int state, double intakeSpeed) {
        this.shooter = shooter;
        this.timeoutS = timeoutS;
        this.intakeSpeed = intakeSpeed;
        this.addRequirements(shooter);
        
        this.state = state;
    }

    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();

        
    }

    @Override
    public void execute() {
        shooter.moveMotor(state, intakeSpeed, false);
        
    }

    @Override
    public void end(boolean interrupted) {
        shooter.moveMotor(0, 0, false);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeoutS);
    }
}
