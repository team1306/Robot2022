package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Climber;
import frc.robot.utils.UserAnalog;

/**
 * command for climber
 */
public class ClimberCommand extends CommandBase {
    private final Climber climber;
    private final UserAnalog speed;

    /**
     * initializes climber command with climber and intial state
     * 
     * @param climber climber to bind
     * @param speed   input for speed of climber
     */
    public ClimberCommand(Climber climber, UserAnalog speed) {
        this.climber = climber;
        this.speed = speed;
    }

    /**
     * extends climber at given speed
     */
    @Override
    public void execute() {
        climber.extension(speed.get());
    }
}
