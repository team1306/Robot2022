package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.utils.UserDigital;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase {
    private final TalonSRX upperMotor, lowerMotor, midMotor;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        upperMotor = initMotor(SHOOTER_UPPER_MOTOR_ID);
        midMotor = initMotor(SHOOTER_MID_MOTOR_ID);
        lowerMotor = initMotor(SHOOTER_LOWER_MOTOR_ID);
    }

    /**
     * Control the motor with the given speed
     * 
     * @param speed speed of the motor
     */
    public void moveMotor(boolean isMain, boolean isSub) {
        int mainSpeed = isMain ? 1 : 0;
        int subSpeed = isSub ? 1 : 0;
        lowerMotor.set(ControlMode.PercentOutput, -mainSpeed);
        upperMotor.set(ControlMode.PercentOutput, mainSpeed);
        midMotor.set(ControlMode.PercentOutput, -subSpeed);
        SmartDashboard.putNumber("Main Shooter Speed", mainSpeed);
        SmartDashboard.putNumber("Sub Shooter Speed", -subSpeed);
    }
}
