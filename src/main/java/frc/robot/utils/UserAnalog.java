package frc.robot.utils;

/**
 * interface for analong inputs to the robot
 */
public interface UserAnalog {

    /**
     * Creates a UserAnalog+
     * 
     * @param digital  - the digital input to construct from
     * @param trueVal  - the output when the digitalInput is true
     * @param falseVal - the output when teh digitalinput is false
     * @return the constructed Analog
     */
    public static UserAnalog fromDigital(UserDigital digital, double trueVal, double falseVal) {
        return () -> digital.get() ? trueVal : falseVal;
    }

    /**
     * @return value - a [-1,1] value based on the input from the user or controller
     */
    public double get();
}