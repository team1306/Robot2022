package frc.robot.subsystems;


import static frc.robot.utils.MotorUtils.*;

import java.util.List;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase implements AutoCloseable {

    // on the robot, "kicker" was connected to the controller w/ "ind"
    private final CANSparkMax frontShooter, shooterKicker, backShooter;
    private final WPI_TalonSRX frontIndex, backIndex;
    private final int OFF = 0, DUMP = 1, NEAR = 2, FAR = 3;


    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        // frontMotor = initSparkMax(SHOOTER_UPPER_MOTOR_ID);grad
        // backMotor = initSparkMax(SHOOTER_LOWER_MOTOR_ID);
        // kicker = initSparkMax(SHOOTER_KICKER_ID,);


        frontIndex = initWPITalonSRX(Constants.FRONT_INDEX_ID);
        backIndex = initWPITalonSRX(Constants.BACK_INDEX_ID);

        frontShooter = initSparkMax(Constants.FRONT_SHOOTER_ID);
        shooterKicker = initSparkMax(Constants.SHOOTER_KICKER_ID);
        backShooter = initSparkMax(Constants.BACK_SHOOTER_ID);
        // // probably doesnt factor in gearing
        // REVPhysicsSim.getInstance().addSparkMax(backMotor, DCMotor.getNEO(1));
    }

    /**
     * shooter runner for demo shooter configuration
     * 
     * @param isShooting input for main shooter wheels
     * @param isIntaking input for kicker wheel
     */
    public void moveMotor(int shootState, double intake, boolean stall) {
        // System.out.println(List.of(shootState, intake, stall));
        switch (shootState) {
            case OFF:
                // System.out.println("off");
                backShooter.set(0);
                frontShooter.set(0);
                shooterKicker.set(0);
                break;
            case DUMP: // shoot low
                System.out.println("low");
                backShooter.set(-.3);
                frontShooter.set(-.2);
                shooterKicker.set(-.2);
                break;
            case NEAR:
                System.out.println("near");
                backShooter.set(-.7);
                frontShooter.set(-.8);
                shooterKicker.set(-.5);
                break;
            case FAR:
                System.out.println("FAR");
                backShooter.set(-.64); // original (with no problems?) back = -.9, front = -.7
                frontShooter.set(-.75);
                shooterKicker.set(-.7);
                break;
            default:
                throw new Error("invalid state (wutchu doin over there?)");
        }

        if (stall) {
            frontIndex.set(ControlMode.PercentOutput, 1);
            backIndex.set(ControlMode.PercentOutput, -1);
        } else if (Math.abs(intake) > 0.05) {
            frontIndex.set(ControlMode.PercentOutput, -intake);
            backIndex.set(ControlMode.PercentOutput, -intake);
        } else {
            frontIndex.set(ControlMode.PercentOutput, 0);
            backIndex.set(ControlMode.PercentOutput, 0);
        }
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
