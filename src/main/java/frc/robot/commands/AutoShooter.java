package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AutoShooter extends CommandBase {
    private Timer timer;
    private double timeoutS;
    private Shooter shooter;
    private double intakeSpeed;
    public static final int NOT_SHOOTING = 0, NEAR = 4, FAR = 5;
    private int state;
    private boolean kicker;

    /**
     * 
     * @param shooter
     * @param timeoutS    number of seconds the command should run for
     * @param state       0 is off, 4 is close, 5 is far
     * @param intakeSpeed speed at which it should be intaking
     */
    public AutoShooter(
        Shooter shooter,
        double timeoutS,
        int state,
        double intakeSpeed,
        boolean kicker
    ) {
        this.shooter = shooter;
        this.timeoutS = timeoutS;
        this.intakeSpeed = intakeSpeed;
        this.kicker = kicker;
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
        if (kicker) {
            shooter.moveMotor(state, intakeSpeed, false, 1);
        } else {
            shooter.moveMotor(state, intakeSpeed, false, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.moveMotor(0, 0, false, 0);
    }

    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeoutS);
    }
}
