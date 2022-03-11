package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

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


        frontIndex = initWPITalonSRX(0);
        backIndex = initWPITalonSRX(0);

        frontShooter = initSparkMax(0);
        shooterKicker = initSparkMax(0);
        backShooter = initSparkMax(0);
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
        switch (shootState) {
            case OFF:
                backShooter.set(0);
                frontShooter.set(0);
                shooterKicker.set(0);
                break;
            case DUMP: // shoot low
                backShooter.set(.2);
                frontShooter.set(.2);
                shooterKicker.set(.2);
                break;
            case NEAR:
                backShooter.set(.6);
                frontShooter.set(.5);
                shooterKicker.set(.5);
                break;
            case FAR:
                backShooter.set(0.8);
                frontShooter.set(.9);
                shooterKicker.set(.7);
                break;
        }

        if (stall) {
            frontIndex.set(ControlMode.PercentOutput, 1);
            backIndex.set(ControlMode.PercentOutput, 1);
        } else if (Math.abs(intake) > 0.05) {
            frontIndex.set(ControlMode.PercentOutput, intake);
            backIndex.set(ControlMode.PercentOutput, intake);
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
