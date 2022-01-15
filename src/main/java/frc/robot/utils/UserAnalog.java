package frc.robot.utils;

public interface UserAnalog {

    /**
     * Creates a UserAnalog
     * 
     * @param digital  - the digital input to construct from
     * @param trueVal  - the output when the digitalInput is true
     * @param falseVal - the output when teh digitalinput is false
     * @return the constructed Analog
     */
    public static UserAnalog fromDigital(UserDigital digital, double trueVal, double falseVal) {
        return () -> {
            if (digital.get()) {
                return trueVal;
            } else {
                return falseVal;
            }
        };
    }

    /**
     * Binds a double to the expected [-1,1] range
     */
    public static double clamp(double val) {
        return Math.max(-1, Math.min(1, val));
    }

    /**
     * @return value - a [-1,1] value based on the input from the user or controller
     */
    public double get();
}