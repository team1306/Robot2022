package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.subsystems.DriveTrain;

public class AutoDriveTrain extends TimedCommand {
    private DriveTrain driveTrain;
    private boolean direction;
    // public static final int DRIVE = 0, TIMED_ROTATION = 1, TARGET_ROTATION = 2;
    private DriveState state;
    private double targetRotation;

    public static enum DriveState {
        DRIVE, TIMED_ROTATION, TARGET_ROTATION
    }



    /**
     * 
     * @param driveTrain     our driveTrain
     * @param timeoutS       length that the command should last
     * @param direction      if true -> reversed, false -> not reversed
     * @param state          DRIVE -> move forward/backward
     * 
     *                       TIMED_ROTATION -> rotate for a set amount of time
     * 
     *                       TARGET_ROTATION -> rotate to a set position
     * @param targetRotation where to rotate to if target rotation is the state
     */
    public AutoDriveTrain(
        DriveTrain driveTrain,
        double timeoutS,
        boolean direction,
        DriveState state,
        double targetRotation
    ) {
        super(timeoutS);
        this.direction = direction;
        this.driveTrain = driveTrain;
        this.addRequirements(driveTrain);
        this.state = state;
        this.targetRotation = targetRotation;
    }

    /**
     * 
     * should be called when the state is target_rotation
     * 
     * @param desiredRotation target position to rotate to
     */
    private void rotateTo(double desiredRotation) {
        SmartDashboard.putNumber("STATE", desiredRotation);

        if (Math.abs(Robot.navx.getYaw() - desiredRotation) < 5) {
            driveTrain.arcadeDrive(0, 0);
        } else if (Robot.navx.getYaw() < desiredRotation) {
            driveTrain.arcadeDrive(0, -.2);
        } else {
            driveTrain.arcadeDrive(0, .2);
        }
    }

    /**
     * should be called when the state is drive drives
     * 
     * forward/backward at .2 speed
     */
    private void drive() {
        if (direction) {
            driveTrain.arcadeDrive(.2, 0);
        } else {
            driveTrain.arcadeDrive(-.2, 0);
        }
    }

    /**
     * should be classed when state is timed_rotation
     * 
     * drives counterclockwise/clockwise
     */
    private void rotate() {
        if (direction) {
            driveTrain.arcadeDrive(0, .2);
        } else {
            driveTrain.arcadeDrive(0, -.2);
        }
    }


    @Override
    public void execute() {
        switch (state) {
            case DRIVE:
                drive();
                break;
            case TIMED_ROTATION:
                rotate();
                break;
            case TARGET_ROTATION:
                rotateTo(targetRotation);
                break;
        }
    }

    @Override
    public void end(boolean interrupted) {
        driveTrain.arcadeDrive(0, 0);
    }
}
