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
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.util.datalog.DoubleLogEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class LimelightInterface extends SubsystemBase {
  /** Creates a new LimelightInterface Subsystem. */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
    // SmartDashboard.putNumber("BotPose X",LimelightHelpers.getBotPose2d_wpiBlue("limelight").getX());
    // SmartDashboard.putNumber("BotPose Y",LimelightHelpers.getBotPose2d_wpiBlue("limelight").getY());
    // SmartDashboard.putNumber("BotPose Rotation",LimelightHelpers.getBotPose2d_wpiBlue("limelight").getRotation().getRadians());

    // SmartDashboard.putNumber("Target ID",NetworkTableInstance.getDefault().getTable("limelight").getEntry("tid").getDouble(0));

    SmartDashboard.putNumber("AT Pose X Cam", getAprilTagPose2d().getX());
    SmartDashboard.putNumber("AT Pose Y Cam", getAprilTagPose2d().getY());
}

  public Pose2d getAprilTagPose2d() {
    Pose3d tagPose =  LimelightHelpers.getTargetPose3d_RobotSpace("limelight");
    double poseY = tagPose.getZ();
    double poseX = tagPose.getX();
    Rotation2d poseRot = new Rotation2d(
      tagPose.getRotation().getZ()
    );
    return new Pose2d(poseX, poseY, poseRot);
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
