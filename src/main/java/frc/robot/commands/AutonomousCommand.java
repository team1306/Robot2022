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
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.Constants;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Shooter;

import java.util.List;

import com.kauailabs.navx.frc.AHRS;

/**
 * shell code for autonomous command
 */
@SuppressWarnings("unused")
public class AutonomousCommand extends RamseteCommand {

    DriveTrain driveTrain;
    Shooter shooter;
    public static int state = 0;
    static DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(
        Constants.TRACK_WIDTH_METERS
    );
    static TrajectoryConfig config = new TrajectoryConfig(
        Constants.MAX_SPEED_MPS,
        Constants.MAX_ACCELERATION_MPSS
    ).setKinematics(driveKinematics)
        .addConstraint(
            new DifferentialDriveVoltageConstraint(
                new SimpleMotorFeedforward(Constants.K_S, Constants.K_V, Constants.K_A),
                driveKinematics,
                5 // max voltage
            )
        );

    // Start from position B facing goal and get ball 2 and then ball 1. Return to starting position
    // Poisitions and balls are labeled on the field map on whiteboard
    static Trajectory trajectory1 = TrajectoryGenerator.generateTrajectory(
        // List.of(
        // new Pose2d(0, 0, new Rotation2d(0)),
        // new Pose2d(4, 0, new Rotation2d(0)),
        // new Pose2d(4, 4, new Rotation2d(Math.PI / 2)),
        // new Pose2d(0, 4, new Rotation2d(Math.PI / 2)),
        // new Pose2d(0, 0, new Rotation2d(Math.PI / 2))
        // ),
        List.of(
            new Pose2d(0, 0, new Rotation2d(0)),
            new Pose2d(-4, 0, new Rotation2d(0)),
            new Pose2d(0, 0, new Rotation2d(0))
        ),
        config
    );
    // Start on posision D facing ball 4. Collect ball 4 and move to position E facing the goal.
    static Trajectory trajectory2 = TrajectoryGenerator.generateTrajectory(
        List.of(
            new Pose2d(0, 0, new Rotation2d(0)),
            new Pose2d(Units.inchesToMeters(46.37), Units.inchesToMeters(71.58), new Rotation2d(0)),
            new Pose2d(
                Units.inchesToMeters(-55.41),
                Units.inchesToMeters(-44.33),
                new Rotation2d(Math.PI * 5.0 / 6)
            )
        ),
        config
    );

    /**
     * initializes autonomous command
     */
    public AutonomousCommand(DriveTrain driveTrain, Shooter shooter) {
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
        this.shooter = shooter;
        // this.addRequirements(driveTrain);
        // this.navx = new AHRS();
        // driveTrain.setDefaultCommand(this);
    }

    @Override
    public void execute() {
        shooter.moveMotor(state, 0, true);
        super.execute();
    }
}
