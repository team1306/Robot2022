package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.UserDigital;

/**
 * Used by climber command to climber up monkey bars
 */
public class Climber extends SubsystemBase {
    private TalonFX motor;
    private double velocity;
    private double height;
    private boolean isOverLimit;

    /**
     * Initialize climber and configure motors
     */
    public Climber() {
        this.motor = initTalonFX(CLIMBER_MOTOR_ID);
        this.height = 0;
        this.velocity = 0;
        this.isOverLimit = false;
    }

    /**
     * Moves the robot climber up and down when manual mode is on or the height is under the limit In order to start
     * contracting climber after reaching the max height, flip the value when it's over the limit
     * 
     * @param analogInput the velocity at which the robot will move
     */
    public void extend(double userAnalog, boolean isManual) {
        if (isManual || !isOverLimit) {
            velocity = Constants.CLIMBER_VELOCITY;
        } else {
            velocity = -Constants.CLIMBER_VELOCITY;
        }
        motor.set(ControlMode.PercentOutput, velocity);
        calcHeight(velocity);
    }

    public void calcHeight(double velocityPercentage) {
        height += velocityPercentage; // TODO: Convert percentage to meter or inch?
    }
}
