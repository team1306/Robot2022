package frc.robot;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

/**
 * A class for holding constant values in a single editable spot. What goes in
 * this file: -Robot Motorcontroller ports and IDS -Robot phsyical attributes
 * -Field attributes
 * 
 * Things that don't go in this file: -Subsystem-specific PID values
 * -Subsystem-specific sensor thresholds
 */
public final class Constants {

    // public static final int K_DRIVE_FRONT_LEFT = 1;
    // public static final int K_DRIVE_RIGHT_BACK_ID = 2;

    // public static final int K_DRIVE_LEFT_FRONT_ID = 3;
    // public static final int K_DRIVE_LEFT_BACK_ID = 4;


    // motors and encoders on the robot
    public static final int K_DRIVE_FRONT_LEFT_ID = 1;
    public static final int K_DRIVE_BACK_LEFT_ID = 2;
    public static final int K_DRIVE_FRONT_RIGHT_ID = 3;
    public static final int K_DRIVE_BACK_RIGHT_ID = 4;

    public static final int K_TURN_FRONT_LEFT_ID = 5;
    public static final int K_TURN_BACK_LEFT_ID = 6;
    public static final int K_TURN_FRONT_RIGHT_ID = 7;
    public static final int K_TURN_BACK_RIGHT_ID = 8;

    public static final int K_ENCODER_FRONT_LEFT_ID = 9;
    public static final int K_ENCODER_BACK_LEFT_ID = 10;
    public static final int K_ENCODER_FRONT_RIGHT_ID = 11;
    public static final int K_ENCODER_BACK_RIGHT_ID = 12;

    public static final int K_INTAKE = 13;

    public static final int NAVX_ID = 14;

    public static final boolean DIRECTION_FORWARD = true;

    public static final int UNITS_PER_ROTATION = 4096;

    // robot information
    public static final double K_TRACK_WIDTH_METERS = 0.7175;
    public static final double K_WHEEL_RADIUS_INCHES = 3.75; //pnuematic, so ish
    public static final double K_WHEEL_RADIUS_METERS = Units.inchesToMeters(K_WHEEL_RADIUS_INCHES);

    public static final double MAX_VOLTS = 24;

    public static final double FASTEST_SPEED_METERS = 12;

    public static final double FASTEST_ACCELERATION = 1;


    public static final double X_CONTROLLER_P = 1;
    public static final double X_CONTROLLER_I = 0;
    public static final double X_CONTROLLER_D = 0;

    public static final double Y_CONTROLLER_P = 1;
    public static final double Y_CONTROLLER_I = 0;
    public static final double Y_CONTROLLER_D = 0;

    public static final double THETA_CONTROLLER_P = 1;
    public static final double THETA_CONTROLLER_I = 0;
    public static final double THETA_CONTROLLER_D = 0;
    

    //wheel base is y
    //track front is width of front of the robot and the x value
    //track back is width of the back of the robot and the other x value
    public static final double ROBOT_DISTANCE_BETWEEN_WHEELS = (16.0/36.0);
    /**
     * meters
     */
    public static final double WHEEL_DISTANCE_TO_CENTER = Math.sqrt(ROBOT_DISTANCE_BETWEEN_WHEELS * ROBOT_DISTANCE_BETWEEN_WHEELS * 2); // meters
    /**
     * radians per second!!!
     */
    public static final double FASTEST_ANGULAR_VELOCITY = FASTEST_SPEED_METERS * 2.0 * Math.PI / (WHEEL_DISTANCE_TO_CENTER * 2.0 * Math.PI);

    // autonomous constants
    public static final double A_kRamseteB = 2.0;
    public static final double A_kRamseteZeta = 0.7;
    public static final double kMaxAngularSpeedRadians = 1;
    public static final double kMaxAngularVelocityRadians = 1;

    public static final double FRONT_LEFT_OFFSET = 0;
    public static final double FRONT_RIGHT_OFFSET = 0;
    public static final double BACK_LEFT_OFFSET = 0;
    public static final double BACK_RIGHT_OFFSET = 0;

    public static final double ksVolts = 0.22;
    public static final double kvVoltSecondsPerMeter = 1.98;
    public static final double kaVoltSecondsSquaredPerMeter = 0.2;

    public static final double kEncoderDistancePerPulse = (K_WHEEL_RADIUS_METERS * Math.PI * 2) / (6.8 * 4096);

    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(K_TRACK_WIDTH_METERS);

    // todo change those^^^!!!!
}
