package frc.robot.commands;

import frc.robot.subsystems.Shooter;

/**
 * autnomous command for shooter, controlling shooting state, kicker state, and speed of intaking
 */
public class AutoShooter extends TimedCommand {
    private Shooter shooter;
    private double intakeSpeed;

    // shooter states
    public static enum ShootState {
        NOT_SHOOTING, NEAR, FAR
    }

    private ShootState state;
    private boolean kicker;

    /**
     * creates auto shooter command with given inputs
     * 
     * @param shooter     shooter to bind
     * @param timeoutS    timeout in seconds0
     * @param state       what type of shot
     * @param intakeSpeed speed of intake
     * @param kicker      whether the kicker should run
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