
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
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
        new SimpleMotorFeedforward(Constants.Ks, Constants.Kv, Constants.Ka),
        driveKinematics,
        5
    );

    static Pose2d pose = new Pose2d(0, 0, new Rotation2d(0));
    static RamseteController m_follower = new RamseteController(2, .7);
    static Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
        pose,
        List.of(new Translation2d(10, 0)),
        new Pose2d(20, 0, new Rotation2d(0)),
        new TrajectoryConfig(Constants.MAX_SPEED_MPS, Constants.MAX_ACCELERATION_MPSS).setKinematics(
            driveKinematics
        ).addConstraint(speedConstraint)
    );

    public static void printSpeeds() {
        Pose2d prevpose = pose;
        for (double time = 0; time < 10; time += .02) {
            var state = exampleTrajectory.sample(time);
            System.out.println(
                driveKinematics.toWheelSpeeds(m_follower.calculate(prevpose, state)).toString().replace(
                    "DifferentialDriveWheelSpeeds",
                    ""
                )
            );
            prevpose = state.poseMeters;
        }
    }

    public static void main(String[] args) {
        System.out.println(exampleTrajectory.toString());
        System.out.println("\n\n");
        printSpeeds();
    }
}