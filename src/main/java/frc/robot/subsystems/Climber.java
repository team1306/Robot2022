package frc.robot.subsystems;

import static frc.robot.Constants.*;
import static frc.robot.utils.MotorUtils.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.utils.UserDigital;

/**
 * Used by climber command to climber up monkey bars
 */
// TODO everything
@SuppressWarnings("unused")
public class Climber extends SubsystemBase {
    private TalonFX motor;
    private double velocity;
    private double height;
    private boolean isOverLimit;

    private double lowerBound;
    private double upperBound;

    private boolean limitClimber;

    /**
     * Initialize climber and configure motors
     */
    public Climber() {
        this.motor = initTalonFX(CLIMBER_MOTOR_ID);
        this.motor.setNeutralMode(NeutralMode.Brake);
        this.height = 0;
        this.velocity = 0;
        this.isOverLimit = false;
        motor.setSelectedSensorPosition(0);
        this.lowerBound = motor.getSelectedSensorPosition() + 5000;
        this.upperBound = 185000;
        // SmartDashboard.putNumber("LowerBound, Climber", lowerBound);
        // SmartDashboard.putNumber("UpperBound, Climber", upperBound);
        this.limitClimber = true;

    }

    /**
     * Moves the robot climber up and down when manual mode is on or the height is under the limit In order to start
     * contracting climber after reaching the max height, flip the value when it's over the limit
     * 
     * @param analogInput the velocity at which the robot will move
     */
    public void extend(double userAnalog, boolean isManual, boolean limitClimber) {
        // if the button is pressed the climber should no longer be limited

        // SmartDashboard.putBoolean("isClimberLimited", limitClimber);

        // SmartDashboard.putNumber("Climber Height, Climber", motor.getSelectedSensorPosition());
        // trying to go up and encoder thinks we are above the highest position -> stop
        if (userAnalog < 0 && motor.getSelectedSensorPosition() > upperBound && !limitClimber) {
            userAnalog = 0;
        }

        // trying to go down and the encoder thinks we are below the starting position -> stop
        if (userAnalog > 0 && motor.getSelectedSensorPosition() < lowerBound && !limitClimber) {
            userAnalog = 0;
        }

        motor.set(ControlMode.PercentOutput, -userAnalog);
    }

    public void calcHeight(double velocityPercentage) {
        height += velocityPercentage; // TODO: Convert percentage to meter or inch?
    }
}
