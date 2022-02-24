package frc.robot.utils;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;


/**
 * contains controller mappings and related methods
 */
public class Controller {
    // Controller Map:
    public static final int AXIS_RTRIGGER = 3;
    public static final int AXIS_LTRIGGER = 2;
    public static final int BUTTON_LTRIGGER = 5;
    public static final int BUTTON_RTRIGGER = 6;
    public static final int BUTTON_RBUMPER = BUTTON_RTRIGGER;
    public static final int BUTTON_LBUMPER = BUTTON_LTRIGGER;
    public static final int AXIS_LY = 1;
    public static final int AXIS_LX = 0;
    public static final int AXIS_RY = 5;
    public static final int AXIS_RX = 4;
    public static final int BUTTON_START = 8;
    public static final int BUTTON_BACK = 7;
    public static final int BUTTON_X = 3;
    public static final int BUTTON_Y = 4;
    public static final int BUTTON_A = 1;
    public static final int BUTTON_B = 2;

    public static final int PRIMARY = 0;
    public static final int SECONDARY = 1;

    public static Joystick primaryJoystick = null;
    public static Joystick secondaryJoystick = null;

    /**
     * intializes primary and secondary joysticks
     */
    public static void init() {
        primaryJoystick = new Joystick(PRIMARY);
        secondaryJoystick = new Joystick(SECONDARY);
    }

    /**
     * Constructs a UserAnalog that precisely mirrors the axis, with no tranformation.
     * 
     * @param player - one of either PRIMARY or SECONDARY. This value is validated, and an invalid parameter will return
     *               a UserAnalog that always gets 0
     * @param axis   - The axis on the Xbox controller to grab values from. This parameter is not validated, so make
     *               sure you have a valid axis!
     * @return the UserAnalog instance
     */
    public static UserAnalog simpleAxis(int player, int axis) {
        Joystick joystick;
        if (player == PRIMARY) {
            joystick = primaryJoystick;
        } else if (player == SECONDARY) {
            joystick = secondaryJoystick;
        } else {
            System.err.println("ERROR: Invalid Player Controller requested");
            return () -> 0;
        }
        return () -> {
            // deadbanding
            double raw = joystick.getRawAxis(axis);
            double sign = Math.signum(raw);
            final double deadband = 0.1;
            final double multiplier = 1 / (1 - deadband);
            return sign * Math.max(0, Math.abs(raw) - deadband) * multiplier;
        };
    }

    /**
     * Constructs a UserDigital that precisely mirrors the button value, with no tranformation.
     * 
     * @param player - one of either PRIMARY or SECONDARY. This value is validated, and an invalid parameter will return
     *               a UserDigital that always gets false.
     * @param button - The button on the Xbox controller to grab values from. This parameter is not validated, so make
     *               sure you have a valid button!
     * @return the UserDigital instance
     */
    public static UserDigital simpleButton(int player, int button) {
        Joystick joystick;
        if (player == PRIMARY) {
            joystick = primaryJoystick;
        } else if (player == SECONDARY) {
            joystick = secondaryJoystick;
        } else {
            System.err.println("ERROR: Invalid Player Controller requested");
            return () -> false;
        }
        return () -> joystick.getRawButton(button);
    }

    /**
     * binds callback to given button and retunr it
     * 
     * @param player   player id
     * @param button   button id
     * @param callback callback to bind to specified button
     * @return the bound button in case other operations need to be done and to protect against trash collection
     */
    public static JoystickButton bindCallback(int player, int button, Runnable callback) {
        JoystickButton b = getJoystickButton(player, button);
        b.whenPressed(callback);
        return b;
    }

    /**
     * get joystick button from player and button ids, secondary if invalid player is given
     * 
     * @param player player id (secondary by default)
     * @param button button id
     * @return the corresponding joystick button
     */
    public static JoystickButton getJoystickButton(int player, int button) {
        Joystick joystick;
        if (player == PRIMARY) {
            joystick = primaryJoystick;
        } else if (player == SECONDARY) {
            joystick = secondaryJoystick;
        } else {
            System.err.println("ERROR: Invalid Player Controller requested");
            joystick = secondaryJoystick;
        }
        return new JoystickButton(joystick, button);
    }
}
