package frc.robot.utils;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class MotorUtils {
    public static TalonSRX initTalonSRX(int motorID) {
        var motor = new TalonSRX(motorID);
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Brake);
        return motor;
    }

    public static WPI_TalonSRX initWPITalonSRX(int motorID) {
        var motor = new WPI_TalonSRX(motorID);
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Brake);
        return motor;
    }

    public static TalonFX initTalonFX(int motorID) {
        var motor = new TalonFX(motorID);
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Coast);
        return motor;
    }

    public static WPI_TalonFX initWPITalonFX(int motorID) {
        var motor = new WPI_TalonFX(motorID);
        motor.configFactoryDefault();
        motor.setNeutralMode(NeutralMode.Brake);
        motor.configClosedloopRamp(2);
        return motor;
    }

    // for use with NEO motor
    public static CANSparkMax initSparkMax(int motorID) {
        var motor = new CANSparkMax(motorID, MotorType.kBrushless);
        motor.restoreFactoryDefaults();
        motor.setIdleMode(CANSparkMax.IdleMode.kBrake);
        return motor;
    }
}
