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
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;

import java.util.List;

import com.kauailabs.navx.frc.AHRS;

/**
 * shell code for autonomous command
 */
public class AutonomousCommand extends RamseteCommand {
    private final AHRS navx;

    DriveTrain driveTrain;
    static DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH_METERS);
    static TrajectoryConfig config = new TrajectoryConfig(
        Constants.MAX_SPEED_MPS,
        Constants.MAX_ACCELERATION_MPSS
    ).setKinematics(driveKinematics).addConstraint(
        new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(Constants.K_S, Constants.K_V, Constants.K_A),
            driveKinematics,
            5 // max voltage
        )
    );

    // Go intake two balls
    // PROBLEM : We want to intake the ball at each waypoint, but
    static Trajectory trajectory1 = TrajectoryGenerator.generateTrajectory(
        List.of(
            new Pose2d(0, 0, new Rotation2d(0)),
            new Pose2d(-2.7, -0.7875, new Rotation2d(Math.PI * 2.0 / 3)),
            new Pose2d(0, -4, new Rotation2d(Math.PI * 4.0 / 3)),
            new Pose2d(0, 0, new Rotation2d(0))
        ),
        config
    );

    /**
     * initializes autonomous command
     */
    public AutonomousCommand(DriveTrain driveTrain) {
        super(
            trajectory1,
            driveTrain::getPose,
            new RamseteController(Constants.K_RAMSETE_B, Constants.K_RAMSETE_ZETA),
            new SimpleMotorFeedforward(Constants.K_S, Constants.K_V, Constants.K_A),
            driveKinematics,
            driveTrain::getWheelSpeeds,
            new PIDController(Constants.K_P, Constants.K_I, Constants.K_D),
            new PIDController(Constants.K_P, Constants.K_I, Constants.K_D),
            driveTrain::tankDriveVolts,
            driveTrain
        );
        driveTrain.resetOdometry(trajectory1.getInitialPose());
        driveKinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH_METERS);
        this.driveTrain = driveTrain;
        // this.addRequirements(driveTrain);
        this.navx = new AHRS();
        // driveTrain.setDefaultCommand(this);
    }
}
