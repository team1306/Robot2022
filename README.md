# Robot2022

2022 robot for Rapid React using Java 11

## Code Layout

list of some important source code files and folders

```txt
📦src
 ┗ 📂main/java/frc/robot
   ┗ 📂commands (commands for the robot)
   ┃ ┣ 📜AutonomousCommand.java (autnomous command using trajectories (currently not used))
   ┃ ┣ 📜AutoShooter.java       (autonomous command for shooter)
   ┃ ┣ 📜ClimberCommand.java    (teleop command for climber)
   ┃ ┣ 📜DriveCommand.java      (teleop command for drivetrain)
   ┃ ┣ 📜ShooterCommand.java    (teleop command for shooter)
   ┃ ┗ 📜TimedCommand.java      (base timed command)
   ┣ 📂subsystems (low level subsystems for the robot)
   ┃ ┣ 📜Climber.java           (climber)
   ┃ ┣ 📜DriveTrain.java        (tankdrive drivetrain)
   ┃ ┗ 📜Shooter.java           (the shooter)
   ┣ 📂utils (utility classes)
   ┃ ┣ 📜Controller.java        (helps with handling controller input)
   ┃ ┣ 📜MotorUtils.java        (helps with motor creation)
   ┃ ┣ 📜REVDigitBoard.java
   ┃ ┣ 📜UserAnalog.java        (encapsulates analog input)
   ┃ ┗ 📜UserDigital.java       (encapsulate digital (boolean) input)
   ┣ 📜Constants.java           (assortment of robot related constants)  
   ┣ 📜Main.java                 
   ┣ 📜Robot.java               (interface for the robot itself)
   ┗ 📜RobotContainer.java      (container for many robot functionalities)
```

## organizational diagram

![uml](/uml.png)
