package frc.robot;

/**
 * A class for holding constant values in a single editable spot. What goes in this file: -Robot
 * Motorcontroller ports and IDS -Robot phsyical attributes -Field attributes
 * 
 * Things that don't go in this file: -Subsystem-specific PID values -Subsystem-specific sensor
 * thresholds
 * 
 * Constants is interface as interface fields are public, static, and final by default
 * 
 */
public interface Constants {
    int SHOOTER_UPPER_MOTOR_ID = 1;
    int SHOOTER_LOWER_MOTOR_ID = 3;
    int SHOOTER_MID_MOTOR_ID = 8;

    int INDEX_FRONT_MOTOR_ID = 0;
    int INDEX_BACK_MOTOR_ID = 0;
    /**
     * TODO: set the actual ID values
     */
}
