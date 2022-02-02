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

    private final TalonSRX backMotor, frontMotor, index;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        backMotor = initMotor(SHOOTER_UPPER_MOTOR_ID);
        index = initMotor(SHOOTER_MID_MOTOR_ID);
        frontMotor = initMotor(SHOOTER_LOWER_MOTOR_ID);
    }

    /**
     * 
     * @param isShooting
     * @param isIntaking
     */
    public void moveMotor(boolean isShooting, boolean isIntaking) {
        if (isShooting) {
            frontMotor.set(ControlMode.PercentOutput, -0.8);
            backMotor.set(ControlMode.PercentOutput, 0.8);
        } else {
            frontMotor.set(ControlMode.PercentOutput, 0);
            backMotor.set(ControlMode.PercentOutput, 0);
        }

        if (isIntaking) {
            index.set(ControlMode.PercentOutput, -0.8);
        } else {
            index.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putNumber("Main Shooter Speed", 0);
        SmartDashboard.putNumber("Sub Shooter Speed", 0);
    }
}
