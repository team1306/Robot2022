/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ResourceBundle.Control;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.AutoDriveTrain;
import frc.robot.commands.AutoShooter;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.Controller;
import frc.robot.utils.UserAnalog;
import frc.robot.utils.UserDigital;
import frc.robot.subsystems.Shooter;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("unused")
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private Command autoCommand;
    private Command driveCommand;
    private DriveTrain driveTrain;
    private Shooter shooter;
    private Climber climber;
    private Command climberCommand;
    private Command shooterCommand;

    private final boolean RUN_AUTO = false;

    // inputs for drive train
    private UserAnalog speedDriveTrain;
    private UserAnalog backwardsTurbo;
    private UserAnalog forwardTurbo;
    private UserAnalog joystickRotationDriveTrain;
    // intake / shooter
    private UserDigital dumpShot, nearShot, farShot;
    private UserDigital stall;
    private UserAnalog intakeInput;
    // climber
    private UserAnalog climberInput;
    private UserDigital unlockClimber;



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
        shooter = new Shooter();
        climber = new Climber();
        autoCommand = getAutonomousCommand();
        driveCommand = new DriveCommand(
            driveTrain,
            speedDriveTrain,
            backwardsTurbo,
            forwardTurbo,
            joystickRotationDriveTrain
        );

        shooterCommand = new ShooterCommand(
            shooter,
            dumpShot,
            nearShot,
            farShot,
            stall,
            intakeInput
        );

        climberCommand = new ClimberCommand(climber, climberInput);
        // new IndexCommand(indexInput, new Index());
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by instantiating a
     * {@link GenericHID} or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
     * and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        speedDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LY);
        backwardsTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LTRIGGER);
        forwardTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RTRIGGER);
        joystickRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LX);


        // either A button pressed
        var pstall = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_A);
        var sstall = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_A);
        stall = () -> sstall.get() || pstall.get();
        intakeInput = Controller.simpleAxis(Controller.SECONDARY, Controller.AXIS_RY);
        dumpShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_X);
        nearShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_LTRIGGER);
        farShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_RTRIGGER);


        climberInput = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RY);
        // both bumpers pressed
        var plb = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_LBUMPER);
        var prb = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_RBUMPER);
        unlockClimber = () -> plb.get() && prb.get();


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
        // This makes sure that the autonomous stops running when teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove this line or comment it out.
        if (RUN_AUTO)
            autoCommand.cancel();

        driveCommand.schedule();
        shooterCommand.schedule();
        climberCommand.schedule();
        // driveTrain.setDefaultCommand(driveCommand);
    }

    public Command getAutonomousCommand() {
        return new AutoDriveTrain(driveTrain, 5).beforeStarting(new AutoShooter(shooter, 3)) // shoot before
            .andThen(new AutoShooter(shooter, 3)); // shoot after;
    }

}
