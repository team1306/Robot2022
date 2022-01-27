package frc.robot.utils;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public interface MotorUtils {
    public static TalonSRX initMotor(int motorID) {
        var motor = new TalonSRX(motorID);
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Brake);
        return motor;
    }

    // for use with NEO motor
    public static CANSparkMax initShooterMotor(int motorID) {
        var motor = new CANSparkMax(motorID, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        return motor;
    }
}
