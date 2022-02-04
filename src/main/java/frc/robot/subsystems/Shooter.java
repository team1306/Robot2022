package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase {

    private final TalonSRX frontMotor, backMotor, kicker;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        frontMotor = initTalonSRX(SHOOTER_UPPER_MOTOR_ID);
        kicker = initTalonSRX(SHOOTER_MID_MOTOR_ID);
        backMotor = initTalonSRX(SHOOTER_LOWER_MOTOR_ID);
    }

    /**
     * 
     * @param isShooting
     * @param isIntaking
     */
    public void moveMotor(boolean isShooting, boolean isIntaking) {
        // |F|:|M|:|B| = 1, 0.8, 0.9 Back height: 38.5
        if (isShooting) {
            backMotor.set(ControlMode.PercentOutput, -0.9);
            frontMotor.set(ControlMode.PercentOutput, 1);
        } else {
            backMotor.set(ControlMode.PercentOutput, 0);
            frontMotor.set(ControlMode.PercentOutput, 0);
        }

        if (isIntaking) {
            kicker.set(ControlMode.PercentOutput, .8);
        } else {
            kicker.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putNumber("Main Shooter Speed", 0);
        SmartDashboard.putNumber("Sub Shooter Speed", 0);
    }
}
