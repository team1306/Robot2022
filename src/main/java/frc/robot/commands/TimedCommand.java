package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

/**
 * base command that runs for a given amount of time
 */
public class TimedCommand extends CommandBase {
    private Timer timer;
    private final double timeoutS;

    /**
     * creates timed command
     * 
     * @param timeoutS timeout in seconds
     */
    public TimedCommand(final double timeoutS) {
        this.timeoutS = timeoutS;
    }

    /**
     * creates a timer and starts counting
     */
    @Override
    public void initialize() {
        timer = new Timer();
        timer.start();
    }


    /**
     * checks whether the given amount of time has elapsed
     * 
     * @return whether the given time has elapsed
     */
    @Override
    public boolean isFinished() {
        return timer.hasElapsed(timeoutS);
    }
}
