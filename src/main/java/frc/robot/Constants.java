// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;

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
    public static final double DRIVE_OFFSET = -1; 
    public static final double CONTROLLER_DEADBAND = 0.1;
  }

  public static class ExampleSubsystemConst {
    //You can add variables in here so later in the code numbers are referenced as, for example:
    // Constants.ExampleSubsystem.leftMotorCAN;

    public static final int leftMotorCAN = 1;
    public static final int righttMotorCAN = 2;
    public static final int encoderDIO = 0;
  }
  public static class DebugConst {
  public static final Pose2d DEBUG_POSE2D = new Pose2d(100, 100, new Rotation2d(Math.PI));

  } 
  public static class DriveConst {
    //X pids 
    public static final double X_TRANSLATION_P = 0;
    public static final double X_TRANSLATION_I = 0;
    public static final double X_TRANSLATION_D = 0;

    //Y pids 
    public static final double Y_TRANSLATION_P = 0;
    public static final double Y_TRANSLATION_I = 0;
    public static final double Y_TRANSLATION_D = 0;

    //Rotation pids 
    public static final double ROTATION_P = 0;
    public static final double ROTATION_I = 0;
    public static final double ROTATION_D = 0;

    //Reef 
    public static final double ROT_SETPOINT_REEF_ALIGNMENT = 0;  // Rotation
    public static final double ROT_TOLERANCE_REEF_ALIGNMENT = 0.5;
    public static final double X_SETPOINT_REEF_ALIGNMENT = -0.5;  // Vertical pose
    public static final double X_TOLERANCE_REEF_ALIGNMENT = 0.005;
    public static final double Y_SETPOINT_REEF_ALIGNMENT = 0.19;  // Horizontal pose
    public static final double Y_TOLERANCE_REEF_ALIGNMENT = 0.005;

    
    public static final double DONT_SEE_TAG_WAIT_TIME = 1;
    public static final double POSE_VALIDATION_TIME = 0.3;
  }

}
