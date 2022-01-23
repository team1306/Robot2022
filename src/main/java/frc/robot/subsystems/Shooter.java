package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used to shoot cargos with the other functionality
 */
public class Shooter extends SubsystemBase {
    private final TalonFX upperMotor, lowerMotor;

    // falcon 500 not talon

    /**
     * Initialize Shooter and configure motors
     */
    public Shooter() {
        upperMotor = initMotor(SHOOTER_UPPER_MOTOR_ID);
        lowerMotor = initMotor(SHOOTER_LOWER_MOTOR_ID);
    }

    // helper function to initialize motors w/ basic config given an id
    private TalonFX initMotor(int motorID) {
        var self = new TalonFX(motorID);
        self.configFactoryDefault();
        self.setNeutralMode(NeutralMode.Brake);
        return self;
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

    /**
     * Return whether the index is empty or not This is primarily for autonomous
     * 
     * @return if the index is empty
     */
    public boolean isEmpty() {
        // TODO vision stuff later
        return false;
    }
}
