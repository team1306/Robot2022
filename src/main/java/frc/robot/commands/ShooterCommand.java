package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.UserAnalog;
import frc.robot.utils.UserDigital;
import frc.robot.subsystems.Shooter.State;
import frc.robot.subsystems.Shooter.State.*;

/**
 * omnibus command for all things relating to the shooter subsystem. shot precedence order : near > far > dump
 */
public class ShooterCommand extends CommandBase {
    private final Shooter shooter;
    private final UserDigital dump, near, far;
    private final UserAnalog intake, kickerUp, kickerDown;
    // private final int OFF = 0, DUMP = 1, NEAR = 2, FAR = 3;

    /**
     * creates shooter command from given inputs
     * 
     * @param shooter    shooter to bind
     * @param dump       whether to dump (near, low)
     * @param near       whether to take a near high shot
     * @param far        whether to take a far high shot
     * @param intake     whether to intake the ball
     * @param kickerUp   speed to move kicker up
     * @param kickerDown speed to move kicker down
     */
    public ShooterCommand(
        Shooter shooter,
        UserDigital dump,
        UserDigital near,
        UserDigital far,
        UserAnalog intake,
        UserAnalog kickerUp,
        UserAnalog kickerDown
    ) {
        this.kickerDown = kickerDown;
        this.kickerUp = kickerUp;
        this.shooter = shooter;
        this.addRequirements(shooter);
        this.dump = dump;
        this.near = near;
        this.far = far;
        this.intake = intake;
    }

    @Override
    public void execute() {
        State state;
        if (near.get()) {
            state = State.NEAR;
        } else if (far.get()) {
            state = State.FAR;
        } else if (dump.get()) {
            state = State.DUMP;
        } else {
            state = State.OFF;
        }
        shooter.moveMotor(state, intake.get(), kickerUp.get() - kickerDown.get());
    }

}
