package frc.robot;

/**
 * A class for holding constant values in a single editable spot. What goes in this file: -Robot Motorcontroller ports
 * and IDS -Robot phsyical attributes -Field attributes
 * 
 * Things that don't go in this file: -Subsystem-specific PID values -Subsystem-specific sensor thresholds
 * 
 * Constants is interface as interface fields are public, static, and final by default
 * 
 */
public interface Constants {
    int SHOOTER_UPPER_MOTOR_ID = 1;
    int SHOOTER_LOWER_MOTOR_ID = 2;// 3;
    int SHOOTER_KICKER_ID = 8;
    int SHOOTER_INDEX_ID = 5;

    int DRIVE_LEFT_LEADER_ID = 0;
    int DRIVE_RIGHT_LEADER_ID = 0;
    int DRIVE_LEFT_FOLLOWER_ID = 0;
    int DRIVE_RIGHT_FOLLOWER_ID = 0;

    int CLIMBER_MOTOR_ID = 0;
    /**
     * TODO: set the actual ID values
     */
}
