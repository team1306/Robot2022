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
import edu.wpi.first.wpilibj.command.Command;
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
    DifferentialDriveKinematics DriveKinematics;
    double kRamseteB = 2;
    double KRamseteZeta = .7;

    /**
     * initializes autonomous command
     */
    public AutonomousCommand(DriveTrain driveTrain) {
        DriveKinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH_METERS);
        this.driveTrain = driveTrain;
        // this.addRequirements(driveTrain);
        this.navx = new AHRS();
        // driveTrain.setDefaultCommand(this);
    }

    /**
     * 
     */
    @Override
    public void initialize() {

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
