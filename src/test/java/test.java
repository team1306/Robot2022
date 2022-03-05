
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.Trajectory.State;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.MotorSafety;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.Controller;
import frc.robot.utils.UserAnalog;
import frc.robot.utils.UserDigital;

import java.util.stream.Collectors;

import org.opencv.core.Mat;

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import frc.robot.Constants;

/**
 * test
 */
public class test {

    private static DifferentialDriveKinematics driveKinematics = new DifferentialDriveKinematics(
        Constants.TRACK_WIDTH_METERS
    );
    private static DifferentialDriveVoltageConstraint speedConstraint = new DifferentialDriveVoltageConstraint(
        new SimpleMotorFeedforward(Constants.K_S, Constants.K_V, Constants.K_A),
        driveKinematics,
        5
    );

    static Pose2d pose = new Pose2d(0, 0, new Rotation2d(0));
    static RamseteController m_follower = new RamseteController(2, .7);
    static TrajectoryConfig config = new TrajectoryConfig(
        Constants.MAX_SPEED_MPS + 1,
        Constants.MAX_ACCELERATION_MPSS + 2
    ).setKinematics(driveKinematics).addConstraint(speedConstraint);
    static Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        List.of(
            new Pose2d(0, 0, new Rotation2d(0)),
            new Pose2d(
                Units.inchesToMeters(-92.945),
                Units.inchesToMeters(-30.51),
                new Rotation2d(Math.PI * -120.0 / 180)
            ),
            new Pose2d(Units.inchesToMeters(-2.85), Units.inchesToMeters(-67.95), new Rotation2d(Math.PI * -60 / 180)),
            new Pose2d(0, 0, new Rotation2d(0))
        ),
        config
    );

    public static void printSpeeds() {
        Pose2d prevpose = pose;
        for (double time = 0; time < 10; time += .05) {
            var state = exampleTrajectory.sample(time);
            // System.out.print(state.poseMeters.toString().replaceAll("[A-za-z]+2d", "") + " ");
            var x = m_follower.calculate(prevpose, state);
            System.out.printf(
                "%.2gs: %s\n",
                time,
                driveKinematics.toWheelSpeeds(x).toString().replace("DifferentialDriveWheelSpeeds", "")
            );
            System.out.println(state.poseMeters);
            prevpose = state.poseMeters;
        }
    }

    public static void main(String[] args) {
        System.out.println(exampleTrajectory.toString().replace("State", "").replaceAll("[A-za-z]+2d", ""));
        System.out.println("\n\n");
        System.out.println(Units.inchesToMeters(-92.945) + "," + Units.inchesToMeters(-30.51));
        System.out.println(Units.inchesToMeters(-2.85) + "," + Units.inchesToMeters(-67.95));
        printSpeeds();
    }
}