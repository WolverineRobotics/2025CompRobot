// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;
import frc.robot.Robot;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightInterface extends SubsystemBase {
  /** Creates a new LimelightInterface Subsystem. */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    SmartDashboard.putNumber("BotPose X",LimelightHelpers.getBotPose2d_wpiBlue("limelight").getX());
    SmartDashboard.putNumber("BotPose Y",LimelightHelpers.getBotPose2d_wpiBlue("limelight").getY());
    SmartDashboard.putNumber("BotPose Rotation",LimelightHelpers.getBotPose2d_wpiBlue("limelight").getRotation().getRadians());

    SmartDashboard.putNumber("Target ID",NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDouble(0));
}

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
