package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class AutoShooter extends TimedCommand {
    private Shooter shooter;
    private double intakeSpeed;

    // public static final int NOT_SHOOTING = 0, NEAR = 4, FAR = 5;
    public static enum ShootState {
        NOT_SHOOTING, NEAR, FAR
    }

    private ShootState state;
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
        ShootState state,
        double intakeSpeed,
        boolean kicker
    ) {
        super(timeoutS);
        this.shooter = shooter;
        this.intakeSpeed = intakeSpeed;
        this.kicker = kicker;
        this.addRequirements(shooter);

        this.state = state;
    }

    @Override
    public void execute() {
        if (kicker) {
            shooter.moveMotor(state.ordinal(), intakeSpeed, false, 1);
        } else {
            shooter.moveMotor(state.ordinal(), intakeSpeed, false, 0);
        }
    }

    @Override
    public void end(boolean interrupted) {
        shooter.moveMotor(0, 0, false, 0);
    }
}