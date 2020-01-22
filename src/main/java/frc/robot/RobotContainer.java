/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.drive.ArcadeDrive;
import frc.robot.commands.drive.Curvature2Drive;
import frc.robot.commands.drive.CurvatureDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.ExampleSubsystem;
import lombok.Getter;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    // The robot's subsystems and commands are defined here...
    private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

    private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

    /* SUBSYSTEMS */
    @Getter
    private final DriveTrain driveTrain = DriveTrain.getInstance();

    /* COMMANDS */
    private final ArcadeDrive arcadeDriveCMD = new ArcadeDrive(driveTrain);
    private final CurvatureDrive curvatureDriveCMD = new CurvatureDrive(driveTrain);
    private final Curvature2Drive curvature2DriveCMD = new Curvature2Drive(driveTrain);

    /* CONTROLS */
    @Getter
    private static final Joystick rightJoystick = new Joystick(RobotConstants.RJS_CH);
    @Getter
    private static final Joystick leftJoystick = new Joystick(RobotConstants.LJS_CH);

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {

        // Configure the button bindings
        configureButtonBindings();

        // Configure the default commands
        configureDefaultCommands();
    }

    private final void configureDefaultCommands() {
        driveTrain.setDefaultCommand(curvature2DriveCMD);
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {

    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        // An ExampleCommand will run in autonomous
        return m_autoCommand;
    }
}
