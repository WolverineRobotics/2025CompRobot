// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ResetMode;

public class ElevatorSubsystem extends SubsystemBase {
  public final SparkMax leftMotor, rightMotor;
  public final DigitalInput bottomSwitch, topSwitch;
  public final RelativeEncoder leftEncoder, rightEncoder;
  // public final SparkMaxConfig rightMotorConfig;

  public ElevatorSubsystem() {
    // Create Instances Of Electronics
    leftMotor = new SparkMax(Constants.kLeftElevatorMotor, MotorType.kBrushless);
    rightMotor = new SparkMax(Constants.kRightElevatorMotor, MotorType.kBrushless);

    // WPILib Deprecated the Method for Inverting SparkMaxes What The Sigma
    /* Code To Change Configuration In-Case We Can't Invert Them Through Rev
    rightMotorConfig = new SparkMaxConfig();
    rightMotorConfig
      .inverted(true);

    rightMotor.configure(rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
     */
    
    bottomSwitch = new DigitalInput(Constants.kBottomLimit);
    topSwitch = new DigitalInput(Constants.kTopLimit);

    // Create Instances of Encoders
    leftEncoder = leftMotor.getEncoder();
    rightEncoder = rightMotor.getEncoder();
  }

  // Change Elevation Of Elevator
  public void moveElevator(double speed) {
    leftMotor.set(speed);
    rightMotor.set(-1*speed);
  }

  // Detect Elevator At Limits
  public boolean atBottomLimit() {
    if (bottomSwitch.get()) {
      return true;
    } 

    else {return false;}
  }

  public boolean atTopLimit() {
    if (topSwitch.get()) {
      return true;
    } 

    else {return false;}
  }
    
  // Get Encoder Measurements
  public double getEncoderAverage() {
    return (getLeftEncoder() + getRightEncoder())/2;
  }

  public double getLeftEncoder() {
    return leftEncoder.getPosition();
  }

  public double getRightEncoder() {
    return rightEncoder.getPosition();
  }

  /**
   * Example command factory method.
   *
   * @return a command
   * 
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // Get Telemetry Data
    SmartDashboard.putNumber("Left Motor", leftMotor.get());
    SmartDashboard.putNumber("Right Motor", rightMotor.get());
    SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
    SmartDashboard.putNumber("Right Encoder", getRightEncoder());
    SmartDashboard.putBoolean("Bottom Limit Switch", atBottomLimit());
    SmartDashboard.putBoolean("Top Limit Switch", atTopLimit());
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
