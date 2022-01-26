package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase {
    private final TalonSRX upperMotor, lowerMotor;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        upperMotor = initMotor(SHOOTER_UPPER_MOTOR_ID);
        lowerMotor = initMotor(SHOOTER_LOWER_MOTOR_ID);
    }

    /**
     * Control the motor with the given speed
     * 
     * @param speed speed of the motor
     */
    public void moveMotor(double speed) {
        lowerMotor.set(ControlMode.PercentOutput, speed);
        upperMotor.set(ControlMode.PercentOutput, -speed);
    }

}
