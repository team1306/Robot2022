package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase {

    private final CANSparkMax frontMotor, backMotor, kicker;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        frontMotor = new CANSparkMax(Constants.SHOOTER_UPPER_MOTOR_ID, MotorType.kBrushless);
        backMotor = new CANSparkMax(Constants.SHOOTER_LOWER_MOTOR_ID, MotorType.kBrushless);
        kicker = new CANSparkMax(Constants.SHOOTER_KICKER_ID, MotorType.kBrushless);

      
    }

    /**
     * 
     * @param isShooting
     * @param isIntaking
     */
    public void moveMotor(boolean isShooting, boolean isIntaking) {
        // |F|:|M|:|B| = 1, 0.8, 0.9 Back height: 38.5
        if (isShooting) {
            backMotor.set( -0.9);
            frontMotor.set( 1);
        } else {
            backMotor.set( 0);
            frontMotor.set(0);
        }

        if (isIntaking) {
            kicker.set( .8);
        } else {
            kicker.set(0);
        }

        SmartDashboard.putNumber("Main Shooter Speed", 0);
        SmartDashboard.putNumber("Sub Shooter Speed", 0);
    }
}
