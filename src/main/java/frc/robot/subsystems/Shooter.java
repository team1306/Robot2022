package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase {
    private final TalonFX upperMotor, lowerMotor, kicker;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        upperMotor = initMotor(SHOOTER_UPPER_MOTOR_ID);
        lowerMotor = initMotor(SHOOTER_LOWER_MOTOR_ID);
        kicker = initMotor(SHOOTER_KICKER_ID);
    }

    /**
     * Control the motor with the given speed TODO: need two different motor speed sets for the different position when
     * we shoot
     * 
     * @param speed speed of the motor
     */
    public void moveMotor(boolean isShooting) {
        if (isShooting) {
            lowerMotor.set(ControlMode.PercentOutput, .8);
            upperMotor.set(ControlMode.PercentOutput, -.8);
            kicker.set(ControlMode.PercentOutput, -.8);
        } else {
            lowerMotor.set(ControlMode.PercentOutput, 0);
            upperMotor.set(ControlMode.PercentOutput, 0);
            kicker.set(ControlMode.PercentOutput, 0);

        }

    }

}
