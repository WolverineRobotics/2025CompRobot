// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static class DriveConstants {

    // Distance from center to front of robot or to one of the sides
    public static final double kDistanceToWheel = 12.5;

    // Constants for the PID controllers for drive and steer 
    public static final double kPDrive = 0.25; 
    public static final double kIDrive = 0.01; 
    public static final double kDDrive = 0.01;

    public static final double kPSteer = 0.25; 
    public static final double kISteer = 0.01; 
    public static final double kDSteer = 0.01; 

    // Dimensional Analyis to convert Rotor spins to degrees gear ratio: 6.75:1 
    public static final double kSteerPositionConversionFactor = (1 / 6.75) * (1 / 360);
    public static final double kSteerVelocityConversionFactor = ((1 / 6.75) * (1 / 360)) / (60 * 1);
    public static final double kDrivePositionConversionFactor = (1 / 6.75) * (1 / 360);
    public static final double kDriveVelocityConversionFactor = ((1 / 6.75) * (1 / 360)) / (60 * 1);
    
  }
}
