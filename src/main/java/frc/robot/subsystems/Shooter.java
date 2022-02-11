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

    // on the robot, "kicker" was connected to the controller w/ "ind"
    private final TalonSRX frontMotor, backMotor, kicker, index;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        // frontMotor = new CANSparkMax(Constants.SHOOTER_UPPER_MOTOR_ID, MotorType.kBrushless);
        // backMotor = new CANSparkMax(Constants.SHOOTER_LOWER_MOTOR_ID, MotorType.kBrushless);
        // kicker = new CANSparkMax(Constants.SHOOTER_KICKER_ID, MotorType.kBrushless);

        frontMotor = new TalonSRX(Constants.SHOOTER_UPPER_MOTOR_ID);
        backMotor = new TalonSRX(Constants.SHOOTER_LOWER_MOTOR_ID);
        kicker = new TalonSRX(Constants.SHOOTER_KICKER_ID);
        index = new TalonSRX(Constants.SHOOTER_INDEX_ID);


    }

    /**
     * 
     * @param isShooting
     * @param isIntaking
     */
    public void moveMotor(boolean isShooting, boolean isIntaking) {
        // |F|:|M|:|B| = 1, 0.8, 0.9 Back height: 38.5
        // if (isShooting) {
        // backMotor.set( -0.9);
        // frontMotor.set( 1);
        // } else {
        // backMotor.set( 0);
        // frontMotor.set(0);
        // }

        // if (isIntaking) {
        // kicker.set( .8);
        // } else {
        // kicker.set(0);
        // }

        // PART ABOVE IS MEANT FOR CanSparkMax's

        if (isShooting) {
            backMotor.set(ControlMode.PercentOutput, -0.9);
            frontMotor.set(ControlMode.PercentOutput, 1);
        } else {
            backMotor.set(ControlMode.PercentOutput, 0);
            frontMotor.set(ControlMode.PercentOutput, 0);
        }

        if (isIntaking) {
            index.set(ControlMode.PercentOutput, -0.8);
            kicker.set(ControlMode.PercentOutput, .8);
        } else {
            index.set(ControlMode.PercentOutput, 0);
            kicker.set(ControlMode.PercentOutput, 0);
        }

        SmartDashboard.putNumber("Main Shooter Speed", 0);
        SmartDashboard.putNumber("Sub Shooter Speed", 0);
    }
}
