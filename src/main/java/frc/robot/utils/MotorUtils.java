package frc.robot.utils;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public interface MotorUtils {
    public static TalonFX initMotor(int motorID) {
        var motor = new TalonFX(motorID);
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Brake);
        return motor;
    }
}
