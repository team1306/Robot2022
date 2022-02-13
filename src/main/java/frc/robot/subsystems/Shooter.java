package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.REVPhysicsSim;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase implements AutoCloseable {

    // on the robot, "kicker" was connected to the controller w/ "ind"
    private final WPI_TalonSRX frontMotor, kicker, index;
    private final CANSparkMax backMotor;


    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        // frontMotor = initSparkMax(SHOOTER_UPPER_MOTOR_ID);grad
        // backMotor = initSparkMax(SHOOTER_LOWER_MOTOR_ID);
        // kicker = initSparkMax(SHOOTER_KICKER_ID,);

        frontMotor = initWPITalonSRX(SHOOTER_UPPER_MOTOR_ID);
        backMotor = initSparkMax(SHOOTER_LOWER_MOTOR_ID);
        kicker = initWPITalonSRX(SHOOTER_KICKER_ID);
        index = initWPITalonSRX(SHOOTER_INDEX_ID);
        // probably doesnt factor in gearing
        REVPhysicsSim.getInstance().addSparkMax(backMotor, DCMotor.getNEO(1));
    }

    /**
     * shooter runner for demo shooter configuration
     * 
     * @param isShooting input for main shooter wheels
     * @param isIntaking input for kicker wheel
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

    @Override
    public void close() throws Exception {
        // TODO Auto-generated method stub
        frontMotor.close();
        backMotor.close();
        kicker.close();
        index.close();
    }
}
