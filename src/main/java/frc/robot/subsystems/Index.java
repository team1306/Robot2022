package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Index extends SubsystemBase {
    private final TalonFX frontMotor, backMotor;

    /**
     * Initialize Index and configure motors
     */
    public Index() {
        frontMotor = initMotor(INDEX_FRONT_MOTOR_ID);
        backMotor = initMotor(INDEX_BACK_MOTOR_ID);
    }

    /**
     * Control the motor with the given speed
     * 
     * @param speed speed of the motor
     */
    public void moveMotor(double speed) {
        backMotor.set(ControlMode.PercentOutput, -speed);
        frontMotor.set(ControlMode.PercentOutput, speed);
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
