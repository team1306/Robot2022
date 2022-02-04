package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utils.UserDigital;

/**
 * template command for shooter subsystem
 */
public class ShooterCommand extends CommandBase {
    private final UserDigital mainShooterInput;
    private final UserDigital subShooterInput;
    private final Shooter shooter;

    /**
     * initializes shooter command with given shooter and speed input
     * 
     * @param shooter    shooter to bind
     * @param speedInput input for shooter speed
     */
    public ShooterCommand(UserDigital mainShooterInput, UserDigital subShooterInput, Shooter shooter) {
        this.mainShooterInput = mainShooterInput;
        this.subShooterInput = subShooterInput;
        this.shooter = shooter;
        this.addRequirements(shooter);
        shooter.setDefaultCommand(this);
    }

    /**
     * executes command
     */
    @Override
    public void execute() {
        shooter.moveMotor(mainShooterInput.get(), subShooterInput.get());
    }

}
