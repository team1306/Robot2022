/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.Map;
import java.util.ResourceBundle.Control;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.commands.ClimberCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ShooterCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.Controller;
import frc.robot.utils.REVDigitBoard;
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
    private Command driveCommand;
    private DriveTrain driveTrain;
    private Shooter shooter;
    private Climber climber;
    private Command climberCommand;
    private Command shooterCommand;

    // inputs for drive train
    private UserAnalog speedDriveTrain;
    private UserAnalog backwardsTurbo;
    private UserAnalog forwardTurbo;
    private UserAnalog joystickRotationDriveTrain;
    // intake / shooter
    private UserDigital dumpShot, nearShot, farShot;
    private UserDigital stall;
    private UserAnalog intakeInput, kickerUp, kickerDown;
    // climber
    private UserAnalog climberInput;
    private UserDigital limitClimber;

    // private REVDigitBoard revBoard;
    private NetworkTableEntry max_speed;

    public static double RC_MAX_SPEED;


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

        // initalize subsystems
        driveTrain = new DriveTrain();
        shooter = new Shooter();
        climber = new Climber();
        // initialize commands
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
            intakeInput,
            kickerUp,
            kickerDown
        );

        climberCommand = new ClimberCommand(climber, climberInput, limitClimber);

        // max_speed = new SendableChooser<>();

        // max_speed.setDefaultOption(".25", .25);
        // max_speed.addOption(".3", .3);
        // max_speed.addOption(".35", .35);
        // max_speed.addOption(".4", .4);
        // max_speed.addOption(".45", .45);
        // max_speed.addOption(".5 ", .5);
        // max_speed.addOption(".65", .65);
        // max_speed.addOption(".7", .7);
        // max_speed.addOption(".75", .75);
        // max_speed.addOption(".8", .8);
        // max_speed.addOption(".85", .85);
        // max_speed.addOption(".9", .9);
        // max_speed.addOption(".95", .95);
        // max_speed.addOption("1", 1.0);

        max_speed = Shuffleboard.getTab("SmartDashboard")
            .add("Max Speed", 1)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1))
            .getEntry();

        max_speed.setDouble(.25);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by instantiating a
     * {@link GenericHID} or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
     * and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        speedDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LY);
        joystickRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LX);

        intakeInput = Controller.simpleAxis(Controller.SECONDARY, Controller.AXIS_LY);
        dumpShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_X);
        nearShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_LTRIGGER);
        farShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_RTRIGGER);
        kickerUp = Controller.simpleAxis(Controller.SECONDARY, Controller.AXIS_RTRIGGER);
        kickerDown = Controller.simpleAxis(Controller.SECONDARY, Controller.AXIS_LTRIGGER);

        backwardsTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LTRIGGER);
        forwardTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RTRIGGER);
        joystickRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LX);

        climberInput = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RY);
        limitClimber = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_START);
    }

    /**
     * start off teleop period by cancelling autonomous command and switching the drivetrain command to the user driving
     * command
     * 
     */
    public void startTeleop() {
        RC_MAX_SPEED = max_speed.getDouble(.25);
        System.out.println("maximum speed " + RC_MAX_SPEED);
        driveCommand.schedule();
        shooterCommand.schedule();
        climberCommand.schedule();
    }

}
