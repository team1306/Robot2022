/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved. */
/* Open Source Software - may be modified and shared by FRC teams. The code */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project. */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import frc.robot.commands.AutoShooter;
import frc.robot.commands.AutonomousCommand;
import frc.robot.commands.DriveCommand;
import frc.robot.commands.ShooterCommand;
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
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private Command autoCommand;
    private Command driveCommand;
    private DriveTrain driveTrain;
    private Shooter shooter;
    private Command shooterCommand;

    private final boolean RUN_AUTO = false;

    // inputs for drive train
    private UserAnalog speedDriveTrain;
    private UserAnalog backwardsTurbo;
    private UserAnalog forwardTurbo;
    private UserAnalog joystickRotationDriveTrain;

    private UserDigital primaryIntakeInput, secondaryIntakeInput, intakeInput;
    private UserDigital dumpShot, nearShot, farShot;
    private UserDigital stall;

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
        // autoCommand = getAutonomousCommand();
        driveCommand = new DriveCommand(
            driveTrain,
            speedDriveTrain,
            backwardsTurbo,
            forwardTurbo,
            joystickRotationDriveTrain
        );

        shooterCommand = new ShooterCommand(
            new Shooter(),
            dumpShot,
            nearShot,
            farShot,
            stall,
            intakeInput
        );
        // new IndexCommand(indexInput, new Index());
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by instantiating a
     * {@link GenericHID} or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
     * and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        this.speedDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LY);
        this.backwardsTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LTRIGGER);
        this.forwardTurbo = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RTRIGGER);
        this.joystickRotationDriveTrain = Controller.simpleAxis(
            Controller.PRIMARY,
            Controller.AXIS_LX
        );


        secondaryIntakeInput = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_A);
        primaryIntakeInput = Controller.simpleButton(Controller.PRIMARY, Controller.BUTTON_A);
        intakeInput = () -> primaryIntakeInput.get() || secondaryIntakeInput.get();
        dumpShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_X);
        nearShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_LTRIGGER);
        farShot = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_RTRIGGER);
        stall = Controller.simpleButton(Controller.SECONDARY, Controller.BUTTON_A);

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
            autoCommand.beforeStarting(new AutoShooter(shooter, 2000)).andThen(
                new AutoShooter(shooter, 4000)
            ).schedule();
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
        shooterCommand.schedule();
        // driveTrain.setDefaultCommand(driveCommand);
    }

    public RamseteCommand getAutonomousCommand() {
        return new AutonomousCommand(driveTrain, shooter);
    }

}
