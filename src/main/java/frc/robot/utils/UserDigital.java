package frc.robot.utils;

public interface UserDigital {

    /**
     * Constructs a UserDigital from a UserAnalog using thresholded values
     * 
     * @param analog    - getter for the analog value
     * @param threshold - if analog val above or equal threshold = true, below=false
     * @param flip      - if true, flips the threshold from above = true to above =
     *                  false
     * @return the constructed object
     */
    public static UserDigital fromAnalog(UserAnalog analog, double threshold, boolean flip) {
        return () -> {
            return (analog.get() >= threshold) ^ flip;
        };
    }

    /**
     * @return value - a boolean value based on the input from the user or
     *         controller.
     */
    public boolean get();
} 