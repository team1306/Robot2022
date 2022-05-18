package frc.robot.subsystems;


import static frc.robot.utils.MotorUtils.*;

import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Used to shoot cargos with the other functionality
 */
@SuppressWarnings("unused")
public class Shooter extends SubsystemBase implements AutoCloseable {
    // on the robot, "kicker" was connected to the controller w/ "ind"
    private final CANSparkMax frontShooter, shooterKicker, backShooter;
    private final WPI_TalonSRX frontIndex, backIndex;
    private final int OFF = 0, DUMP = 1, NEAR = 2, FAR = 3, NEAR_AUTO = 4, FAR_AUTO = 5;
    private double previousIntakeFront = 0, previousIntakeBack = 0;

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        frontIndex = initWPITalonSRX(Constants.FRONT_INDEX_ID);
        backIndex = initWPITalonSRX(Constants.BACK_INDEX_ID);

        frontShooter = initSparkMax(Constants.FRONT_SHOOTER_ID);
        shooterKicker = initSparkMax(Constants.SHOOTER_KICKER_ID);
        backShooter = initSparkMax(Constants.BACK_SHOOTER_ID);
    }

    /**
     * 
     * @param shootState OFF -> not shooting, DUMP -> get rid of ball, NEAR -> shoot while lined up, FAR -> shoot from
     *                   farther away, NEAR_AUTO -> near shot for auto, FAR_AUTO -> far shot for auto
     * @param intake     speed of intake
     * @param stall      whether stalling or no
     * @param kicker     whether kicker is turning or no
     */
    public void moveMotor(int shootState, double intake, boolean stall, double kicker) {

        int sign = 0;
        if (kicker > .05)
            sign = -1;
        if (kicker < -.05)
            sign = 1;

        double kickerMag = .7;

        switch (shootState) {
            case OFF:
                backShooter.set(0);
                frontShooter.set(0);
                break;
            case DUMP: // shoot low
                backShooter.set(-.3);
                frontShooter.set(-.2);
                shooterKicker.set(-.5);
                break;
            case NEAR:
                backShooter.set(-.525);
                frontShooter.set(-.625);
                kickerMag = .5;
                break;
            case FAR:
                backShooter.set(-1); // original (with no problems?) back = -.9, front = -.7
                frontShooter.set(-.6);
                break;
            case NEAR_AUTO:
                // System.out.print("near auto");
                backShooter.set(-.5);
                frontShooter.set(-.60);
                kickerMag = .5;
                break;
            case FAR_AUTO:
                backShooter.set(-1);
                frontShooter.set(-.6);
                kickerMag = .8;
            default:
                throw new Error("invalid state");
        }
        shooterKicker.set(sign * kickerMag);

        // desired right and left speeds
        double fspeed, bspeed;
        if (stall) {
            fspeed = 1;
            bspeed = -1;
        } else if (Math.abs(intake) > 0.05) {
            fspeed = bspeed = intake;
        } else {
            fspeed = bspeed = 0;
        }

        previousIntakeFront = limitAcceleration(fspeed, previousIntakeFront);
        previousIntakeBack = limitAcceleration(bspeed, previousIntakeBack);
        frontIndex.set(-previousIntakeFront);
        backIndex.set(-previousIntakeBack);
    }

    public double limitAcceleration(
        double currentTargetPercentOutput,
        double previousPercentOutput
    ) {
        final double INCR = Constants.TIME_PER_LOOP / Constants.TIME_TO_FULL_SPEED;
        // change that the user wants
        double error = currentTargetPercentOutput - previousPercentOutput;

        // target is going towards 0
        boolean isDecel = Math.abs(currentTargetPercentOutput) < .05;

        if (isDecel)
            return currentTargetPercentOutput;

        // divide that change over a period of time
        // if the change in acceleration is too large positively, accelerate slower
        if (error > INCR)
            return previousPercentOutput + INCR;

        // the change in acceleration is too large negatively, accelerate to the negative direction slower
        if (error < -INCR)
            return previousPercentOutput - INCR;

        return currentTargetPercentOutput;
    }

    @Override
    public void close() throws Exception {
        frontShooter.close();
        shooterKicker.close();
        backShooter.close();
        frontIndex.close();
        backIndex.close();
    }
}
