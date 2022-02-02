package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * Used by climber command to climber up monkey bars
 */
public class Climber extends SubsystemBase {
    private TalonFX motor;

    /**
     * Initialize climber and configure motors
     */
    public Climber() {
        motor = initMotor(CLIMBER_MOTOR_ID);
    }

    /**
     * Moves the robot climber up and down TODO: does there need to be limit for max extension
     * 
     * @param analogInput the velocity at which the robot will move
     */
    public void extend(double analogInput) {
        motor.set(ControlMode.PercentOutput, analogInput);
    }

}
