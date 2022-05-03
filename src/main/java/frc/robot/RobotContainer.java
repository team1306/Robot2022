/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ResourceBundle.Control;
import java.util.concurrent.atomic.AtomicBoolean;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.AutoDriveTrain;
import frc.robot.commands.AutoShooter;
import frc.robot.commands.AutonomousCommand;
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

import static frc.robot.commands.AutoDriveTrain.DriveState;
import static frc.robot.commands.AutoShooter.ShootState;


/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 */
@SuppressWarnings("unused")
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private Command autoCommand;
    private Command autoCommand2;
    private Command autoCommand3;
    private Command autoCommand4;
    private Command driveCommand;
    private DriveTrain driveTrain;
    private Shooter shooter;
    private Climber climber;
    private Command climberCommand;
    private Command shooterCommand;

    private final boolean RUN_AUTO = true;

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
    private SendableChooser<Command> m_chooser;
    private SendableChooser<Double> max_speed;

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

        driveTrain = new DriveTrain();
        shooter = new Shooter();
        climber = new Climber();
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
            intakeInput,
            kickerUp,
            kickerDown
        );

        climberCommand = new ClimberCommand(climber, climberInput, limitClimber);
        // var auto = new AutoCommandFactory(driveTrain, shooter);
        // close 1 ball auto
        autoCommand = new AutoDriveTrain(driveTrain, 3, true, DriveState.DRIVE, 0)
            .beforeStarting(new AutoShooter(shooter, 3, ShootState.NEAR, -1, true));

        // far 1 ball auto
        autoCommand2 = new AutoDriveTrain(driveTrain, 3, true, DriveState.DRIVE, 0)
            .beforeStarting(new AutoShooter(shooter, 2, ShootState.FAR, -1, true));

        // 2 ball auto (pain)

        // drive foward(?)
        // autoCommand3 = new AutoDriveTrain(driveTrain, .25, false, DriveState.DRIVE, 0)
        // // drive forward and intake
        // .andThen(
        // new AutoDriveTrain(driveTrain, 1.75, false, DriveState.DRIVE, 0)
        // .alongWith(new AutoShooter(shooter, 1.75, ShootState.NOT_SHOOTING, -.8, false))
        // )
        // // drive back
        // .andThen(new AutoDriveTrain(driveTrain, 2, true, DriveState.DRIVE, 0))
        // // rotate (left?) around 180 deg
        // .andThen(new AutoDriveTrain(driveTrain, 1.5, false, DriveState.TIMED_ROTATION, 0))
        // // drive forward
        // .andThen(new AutoDriveTrain(driveTrain, 2, false, DriveState.DRIVE, 0))
        // // move cargo down (allow motors to spin up)
        // .andThen(new AutoShooter(shooter, .25, ShootState.NOT_SHOOTING, 1, false))
        // .andThen(new AutoDriveTrain(driveTrain, .20, true, DriveState.DRIVE, 0))
        // .andThen(new AutoDriveTrain(driveTrain, .05, false, DriveState.DRIVE, 0))
        // .andThen(new AutoShooter(shooter, .5, ShootState.NOT_SHOOTING, 0, false))
        // // bring cargo up and run shooter
        // .andThen(new AutoShooter(shooter, 3, ShootState.NEAR, -1, true));

        autoCommand3 = new AutoDriveTrain(driveTrain, .25, false, DriveState.DRIVE, 0)
            // drive forward and intake
            .andThen(
                new AutoDriveTrain(driveTrain, 1.75, false, DriveState.DRIVE, 0)
                    .alongWith(new AutoShooter(shooter, 1.75, ShootState.NOT_SHOOTING, -.8, false)),
                new AutoDriveTrain(driveTrain, 2, true, DriveState.DRIVE, 0),
                new AutoDriveTrain(driveTrain, 1.5, false, DriveState.TIMED_ROTATION, 0),
                new AutoDriveTrain(driveTrain, 2, false, DriveState.DRIVE, 0),
                new AutoShooter(shooter, .25, ShootState.NOT_SHOOTING, 1, false),
                new AutoDriveTrain(driveTrain, .20, true, DriveState.DRIVE, 0),
                new AutoDriveTrain(driveTrain, .05, false, DriveState.DRIVE, 0),
                new AutoShooter(shooter, .5, ShootState.NOT_SHOOTING, 0, false),
                new AutoShooter(shooter, 3, ShootState.NEAR, -1, true)
            );

        // try for 3 ball auto (lol no)
        autoCommand4 = new WaitCommand(1);
        // new AutoDriveTrain(
        // driveTrain,
        // 15,
        // false,
        // DriveState.TARGET_ROTATION,
        // -40
        // );


        // experimental
        // autoCommand4 = auto.shoot(3, ShootState.FAR) // shoot
        // .andThen(
        // auto.drive(1.8, false),
        // auto.wait(.55),
        // auto.turn(.5)
        // );

        // revBoard = new REVDigitBoard();

        // new IndexCommand(indexInput, new Index());
        m_chooser = new SendableChooser<Command>();
        max_speed = new SendableChooser<Double>();

        m_chooser.setDefaultOption("Close Shot", autoCommand);
        m_chooser.addOption("Far Shot", autoCommand2);
        m_chooser.addOption("Two Ball Auto", autoCommand3);
        m_chooser.addOption("Three Ball Auto", autoCommand4);

        max_speed.setDefaultOption(".25", .25);
        max_speed.addOption(".3", .3);
        max_speed.addOption(".35", .35);
        max_speed.addOption(".4", .4);
        max_speed.addOption(".45", .45);
        max_speed.addOption(".5 ", .5);
        max_speed.addOption(".65", .65);
        max_speed.addOption(".7", .7);
        max_speed.addOption(".75", .75);
        max_speed.addOption(".8", .8);
        max_speed.addOption(".85", .85);
        max_speed.addOption(".9", .9);
        max_speed.addOption(".95", .95);
        max_speed.addOption("1", 1.0);

        SmartDashboard.putData(max_speed);
        SmartDashboard.putData(m_chooser);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by instantiating a
     * {@link GenericHID} or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
     * and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        speedDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LY);
        // backwardsTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LTRIGGER);
        // forwardTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RTRIGGER);
        joystickRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LX);


        // either A button pressed
        intakeInput = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RY);
        dumpShot = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_LBUMPER);
        nearShot = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_RBUMPER);
        farShot = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_LTRIGGER);
        // kickerUp = Controller.simpleAxis(Controller.SECONDARY, Controller.AXIS_RTRIGGER);
        // kickerDown = Controller.simpleAxis(Controller.SECONDARY, Controller.AXIS_LTRIGGER);



        climberInput = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RY);
        // both bumpers pressed

        limitClimber = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_START);

        // shooterMainInput = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_LBUMPER);
        // shooterSubInput = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_A);
        // indexInput = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RY);
    }

    /**
     * called when autonomous is started should create all commands that are used in auto
     */
    public void startAuto() {
        autoCommand = getAutonomousCommand();
        if (RUN_AUTO) {
            driveCommand.cancel();
            autoCommand.schedule();
        }
        System.out.println("startAuto() IS RUNNING.");
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

        RC_MAX_SPEED = max_speed.getSelected();
        driveCommand.schedule();
        shooterCommand.schedule();
        climberCommand.schedule();
    }

    public Command getAutonomousCommand() {
        return m_chooser.getSelected();
    }

}
