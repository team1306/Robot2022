package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.UserAnalog;
import frc.robot.utils.UserDigital;

/**
 * template command for shooter subsystem
 */
public class ShooterCommand extends CommandBase {
    private final Shooter shooter;
    private final UserDigital dump, near, far;
    private final UserDigital stall;
    private final UserAnalog intake;
    private final int OFF = 0, DUMP = 1, NEAR = 2, FAR = 3;

    /**
     * @param shooter
     * @param dump
     * @param near
     * @param far
     * @param intake
     */
    public ShooterCommand(
        Shooter shooter,
        UserDigital dump,
        UserDigital near,
        UserDigital far,
        UserDigital stall,
        UserAnalog intake
    ) {
        this.shooter = shooter;
        this.dump = dump;
        this.near = near;
        this.far = far;
        this.stall = stall;
        this.intake = intake;
    }

    @Override
    public void execute() {
        int state;
        if (near.get()) {
            state = NEAR;
        } else if (far.get()) {
            state = FAR;
        } else if (dump.get()) {
            state = DUMP;
        } else {
            state = OFF;
        }
        shooter.moveMotor(state, intake.get(), stall.get());
    }

}
