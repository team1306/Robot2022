package frc.robot.subsystems;

import static frc.robot.Constants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Index extends SubsystemBase {
    private final TalonSRX motor;

    /**
     * Initialize Index and configure motors
     */
    public Index() {
        motor = new TalonSRX(SHOOTER_INDEX_ID);
    }

    /**
     * Control the motor with the given speed
     * 
     * @param speed speed of the motor
     */
    public void moveMotor(double speed) {
        motor.set(ControlMode.PercentOutput, speed);
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
