package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

import java.util.List;

import com.kauailabs.navx.frc.AHRS;

/**
 * shell code for autonomous command
 */
public class AutonomousCommand extends CommandBase {
    private final AHRS navx;

    DriveTrain driveTrain;
    DifferentialDriveKinematics DriveKinematics = new DifferentialDriveKinematics(Constants.TrackwidthMeters);
    double kRamseteB = 2;
    double KRamseteZeta = .7;

    /**
     * initializes autonomous command
     */
    public AutonomousCommand(DriveTrain driveTrain) {
        this.driveTrain = driveTrain;
        this.addRequirements(driveTrain);
        this.navx = new AHRS();
        // driveTrain.setDefaultCommand(this);

    }

    /**
     * 
     */
    @Override
    public void initialize() {
        var speedConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(Constants.Ks, Constants.Kv, Constants.Ka), DriveKinematics, 10
        );
        TrajectoryConfig config = new TrajectoryConfig(
            Constants.maxSpeedMPS, Constants.maxAccelerationMPSS
        ).setKinematics(DriveKinematics).addConstraint(speedConstraint);
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(1, 1), new Translation2d(2, -1)),
            new Pose2d(3, 0, new Rotation2d(0)),
            config
        );
        RamseteCommand ramseteCom = new RamseteCommand(
            exampleTrajectory, DriveTrain::getPose, new RamseteController(kRamseteB, KRamseteZeta),
            new SimpleMotorFeedforward(Constants.Ks, Constants.Kv, Constants.Ka), DriveKinematics,
            DriveTrain::getWheelSpeeds, new PIDController(1, 0, 0), new PIDController(1, 0, 0),
            DriveTrain::tankDriveVolts, driveTrain
        );
    }

    /**
     * 
     */
    @Override
    public void execute() {
        // driveTrain.arcadeDrive(.1, 0);
        SmartDashboard.putNumber("Gryo Yaw value", navx.getYaw());
        SmartDashboard.putNumber("Gyro Roll value", navx.getRoll());
        SmartDashboard.putNumber("Gryo Pitch value", navx.getPitch());
    }

    /**
     * 
     */
    @Override
    public boolean isFinished() {
        return false;
    }

    /**
     * 
     */
    @Override
    public void end(boolean interrupted) {
        cancel();
    }
}
