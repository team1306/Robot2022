package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.utils.UserAnalog;

/**
 * template command for shooter subsystem
 */
public class IndexCommand extends CommandBase {
    private final UserAnalog speedInput;
    private final Index index;

    /**
     * initializes shooter command with given shooter and speed input
     * 
     * @param index      shooter to bind
     * @param speedInput input for shooter speed
     */
    public IndexCommand(UserAnalog speedInput, Index index) {
        this.speedInput = speedInput;
        this.index = index;
        this.addRequirements(index);
        index.setDefaultCommand(this);
    }

    /**
     * executes command
     */
    @Override
    public void execute() {
        index.moveMotor(speedInput.get());
    }

}
