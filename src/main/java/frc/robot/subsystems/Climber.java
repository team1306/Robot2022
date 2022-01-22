package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.*;

/**
 * Used by climber command to climber up monkey bars
 */
public class Climber extends SubsystemBase {
    private TalonFX motor;

    /**
     * Initialize climber and configure motors
     */
    public Climber() {
        motor = new TalonFX(CLIMBER_MOTOR_ID);
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Brake);
    }

    /**
     * Moves the robot climber up and down
     * 
     * @param analogInput the velocity at which the robot will move
     */
    public void extension(double analogInput) {
        motor.set(ControlMode.PercentOutput, analogInput);
    }

}
