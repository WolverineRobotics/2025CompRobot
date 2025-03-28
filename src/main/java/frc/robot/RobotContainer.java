// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;
import frc.robot.Constants.ElevatorSubsystemConst;
import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.AlignAprilTag;

import frc.robot.commands.ElevatorPresetCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.AutoShootCommand;

import frc.robot.subsystems.IntakeSubsystem;

import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;

import frc.robot.subsystems.LimelightInterface;

import java.io.File;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.auto.NamedCommands;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  public final IntakeSubsystem m_IntakeSubsystem = new IntakeSubsystem();

  public final DriveSubsystem m_DriveSubsystem; 
  public final LimelightInterface m_LimelightInterface;


  public final ElevatorSubsystem m_ElevatorSubsystem;

  // Replace with CommandPS4Controller or CommandJoystick if needed
  private final CommandXboxController m_driverController =
      new CommandXboxController(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    
    try {
      //Creating a drivesubsystem from the config files and handling the case where they do not exist 
      m_DriveSubsystem = new DriveSubsystem(new File(Filesystem.getDeployDirectory(), "swerve"), 5); 
      m_LimelightInterface = new LimelightInterface();
    }

    catch(Exception e) {
      throw new RuntimeException(e);
    }

    m_ElevatorSubsystem = new ElevatorSubsystem();
    NamedCommands.registerCommand("L4 Preset", new ElevatorPresetCommand(m_ElevatorSubsystem, ElevatorSubsystemConst.L4_ENCODER_VALUE));
    NamedCommands.registerCommand("L3 Preset", new ElevatorPresetCommand(m_ElevatorSubsystem, ElevatorSubsystemConst.L3_ENCODER_VALUE));
    NamedCommands.registerCommand("L2 Preset", new ElevatorPresetCommand(m_ElevatorSubsystem, ElevatorSubsystemConst.L2_ENCODER_VALUE));
    NamedCommands.registerCommand("L1 Preset", new ElevatorPresetCommand(m_ElevatorSubsystem, ElevatorSubsystemConst.L1_ENCODER_VALUE));
    NamedCommands.registerCommand("Auto Shoot", new AutoShootCommand(m_IntakeSubsystem));

    
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return m_DriveSubsystem.getAutoCommand("");
  }

  public void teleopSequence() {
    if (Input.setL1()) {
      new ElevatorPresetCommand(
        m_ElevatorSubsystem,
        ElevatorSubsystemConst.L1_ENCODER_VALUE
      );
    }

    if (Input.setL2()) {
      new ElevatorPresetCommand(
        m_ElevatorSubsystem,
        ElevatorSubsystemConst.L2_ENCODER_VALUE
      ).schedule();
    }

    // if (Input.setL3()) {
    //   new ElevatorPresetCommand(
    //     m_ElevatorSubsystem,
    //     ElevatorSubsystemConst.L3_ENCODER_VALUE
    //   ).schedule();
    // }

    // if (Input.setL4()) {
    //   new ElevatorPresetCommand(
    //     m_ElevatorSubsystem,
    //     ElevatorSubsystemConst.L4_ENCODER_VALUE
    //   );
    // }

    if (Input.toggleSpeed()) {
      m_DriveSubsystem.toggleLimit();
    }

    if (Input.getIntake()) {
      new IntakeCommand(m_IntakeSubsystem).schedule();
    }

    if (Input.getShoot()) {
      new ShootCommand(m_IntakeSubsystem).schedule();
    }

    if (Input.zeroGyro()) {
      m_DriveSubsystem.zero();
    }

    if (Input.getIntakeSpeed() != 0) {
      m_IntakeSubsystem.setSpeed(Input.getIntakeSpeed());
    }

    SmartDashboard.putData("", CommandScheduler.getInstance());
  }
}
