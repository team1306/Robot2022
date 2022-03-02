/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

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

import java.util.List;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private Command autoCommand;
    private Command driveCommand;
    private DriveTrain driveTrain;

    private final boolean RUN_AUTO = true;

    // inputs for drive train
    private UserAnalog speedDriveTrain;
    private UserAnalog leftRotationDriveTrain;
    private UserAnalog rightRotationDriveTrain;

    DifferentialDriveKinematics DriveKinematics = new DifferentialDriveKinematics(Constants.TRACK_WIDTH_METERS);
    double kRamseteB = 2;
    double KRamseteZeta = .7;



    // The robot's inputs that it recieves from the controller are defined here

    /**
     * The container for the robot. Contains subsystems, OI devices.
     */
    public RobotContainer() {
        // initialize controller using Controller init method to ensure Controller is properly initialized when the
        // Controllern is required to be initialized at the start of the game. Controller represents an xbox controller
        // which controls the robot using standard controller inputs such as joysticks, buttons, and triggers.
        Controller.init();
        configureButtonBindings();

        driveTrain = new DriveTrain();
        autoCommand = getAutonomousCommand();
        driveCommand = new DriveCommand(driveTrain, speedDriveTrain, leftRotationDriveTrain, rightRotationDriveTrain);

        // new ShooterCommand(shooterMainInput, shooterSubInput, new Shooter());
        // new IndexCommand(indexInput, new Index());
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by instantiating a
     * {@link GenericHID} or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
     * and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        this.speedDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LY);
        this.leftRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LTRIGGER);
        this.rightRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RTRIGGER);
        // shooterMainInput = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_LBUMPER);
        // shooterSubInput = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_A);
        // indexInput = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RY);
    }

    /**
     * called when autonomous is started should create all commands that are used in auto
     */
    public void startAuto() {
        if (RUN_AUTO) {
            driveCommand.cancel();
            autoCommand.schedule();
        }
    }

    /**
     * start off teleop period by cancelling autonomous command and switching the drivetrain command to the user driving
     * command
     * 
     */
    public void startTeleop() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (RUN_AUTO)
            autoCommand.cancel();

        driveCommand.schedule();
        // driveTrain.setDefaultCommand(driveCommand);
    }

    public RamseteCommand getAutonomousCommand() {
        var speedConstraint = new DifferentialDriveVoltageConstraint(
            new SimpleMotorFeedforward(Constants.Ks, Constants.Kv, Constants.Ka), DriveKinematics, 5
        );
        TrajectoryConfig config = new TrajectoryConfig(
            Constants.MAX_SPEED_MPS, Constants.MAX_ACCELERATION_MPSS
        ).setKinematics(DriveKinematics).addConstraint(speedConstraint);
        Trajectory exampleTrajectory = TrajectoryGenerator.generateTrajectory(
            new Pose2d(0, 0, new Rotation2d(0)),
            List.of(new Translation2d(0, 10)),
            new Pose2d(0, 20, new Rotation2d(0)),
            config
        );
        System.out.println(exampleTrajectory.toString());
        RamseteCommand ramseteCommand = new RamseteCommand(
            exampleTrajectory, driveTrain::getPose, new RamseteController(kRamseteB, KRamseteZeta),
            new SimpleMotorFeedforward(Constants.Ks, Constants.Kv, Constants.Ka), DriveKinematics,
            driveTrain::getWheelSpeeds, new PIDController(0, 0, 0), new PIDController(0, 0, 0),
            (driveTrain::tankDriveVolts), driveTrain
        );

        driveTrain.resetOdometry(exampleTrajectory.getInitialPose());
        ramseteCommand.addRequirements(driveTrain);
        return ramseteCommand;
    }

}
