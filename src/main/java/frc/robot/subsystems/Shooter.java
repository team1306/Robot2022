package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase {

    // on the robot, "kicker" was connected to the controller w/ "ind"
    private final TalonSRX frontMotor, kicker, index;
    private final CANSparkMax backMotor;

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        // frontMotor = initNeo(SHOOTER_UPPER_MOTOR_ID);
        // backMotor = initNeo(SHOOTER_LOWER_MOTOR_ID);
        // kicker = initNeo(SHOOTER_KICKER_ID,);

        frontMotor = initTalonSRX(SHOOTER_UPPER_MOTOR_ID);
        backMotor = initSparkMax(SHOOTER_LOWER_MOTOR_ID);
        kicker = initTalonSRX(SHOOTER_KICKER_ID);
        index = initTalonSRX(SHOOTER_INDEX_ID);
    }

    /**
     * 
     * @param isShooting
     * @param isIntaking
     */
    public void moveMotor(boolean isShooting, boolean isIntaking) {
        /*if (isShooting) {
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
        } */

        // PART ABOVE IS MEANT FOR CanSparkMax's

        if (isShooting) {
            backMotor.set(0.8);
            frontMotor.set(ControlMode.PercentOutput, 0.9);
        } else {
            backMotor.set(0);
            frontMotor.set(ControlMode.PercentOutput, 0);
        }

        if (isIntaking) {
            index.set(ControlMode.PercentOutput, 1);
            kicker.set(ControlMode.PercentOutput, .8);
        } else {
            index.set(ControlMode.PercentOutput, 0);
            kicker.set(ControlMode.PercentOutput, 0);
        }


        SmartDashboard.putNumber("Main Shooter Speed", 0);
        SmartDashboard.putNumber("Sub Shooter Speed", 0);
    }
}
