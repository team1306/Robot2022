package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.UserAnalog;

/**
 * template command for shooter subsystem
 */
public class ShooterCommand extends CommandBase {
    private final UserAnalog speedInput;
    private final Shooter shooter;

    /**
     * initializes shooter command with given shooter and speed input
     * 
     * @param shooter    shooter to bind
     * @param speedInput input for shooter speed
     */
    public ShooterCommand(UserAnalog speedInput, Shooter shooter) {
        this.speedInput = speedInput;
        this.shooter = shooter;
    }

    /**
     * executes command
     */
    @Override
    public void execute() {
        shooter.moveMotor(speedInput.get());
    }

}
