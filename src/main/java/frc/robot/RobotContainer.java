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
import frc.robot.commands.DriveCommand;
import frc.robot.subsystems.DriveTrain;
import frc.robot.utils.Controller;
import frc.robot.utils.UserAnalog;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a "declarative" paradigm, very
 * little robot logic should actually be handled in the {@link Robot} periodic methods (other than the scheduler calls).
 * Instead, the structure of the robot (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final Command autoCommand;

    private final boolean runAuto = false;

    // inputs for drive train
    private UserAnalog speedDriveTrain;
    private UserAnalog leftRotationDriveTrain;
    private UserAnalog rightRotationDriveTrain;


    // The robot's inputs that it recieves from the controller are defined here


    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        autoCommand = null;
        configureButtonBindings();

        new DriveCommand(new DriveTrain(), speedDriveTrain, leftRotationDriveTrain, rightRotationDriveTrain);
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be created by instantiating a
     * {@link GenericHID} or one of its subclasses ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}),
     * and then passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        Controller.init();
        this.speedDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LY);
        this.leftRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_LTRIGGER);
        this.rightRotationDriveTrain = Controller.simpleAxis(Controller.PRIMARY, Controller.AXIS_RTRIGGER);

    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        System.out.println("getAutonomousCommand() IN RobotContainer IS RUNNING");
        if (runAuto) {
            return autoCommand;
        } else {
            return null;
        }
    }
}
