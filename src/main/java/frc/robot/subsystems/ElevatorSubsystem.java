package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorSubsystemConst;
import frc.robot.commands.DefaultElevatorCommand;

public class ElevatorSubsystem extends SubsystemBase{
    private final SparkMax m_LeftMotor;
    private final SparkMax m_RightMotor;
    private final RelativeEncoder m_LeftEncoder;
    private final RelativeEncoder m_RightEncoder;
    private final DigitalInput m_TopSwitch;
    private final DigitalInput m_BotSwitch;
    private final SparkMaxConfig rightMotorConfig;

    // Trapezoidal Motion Profile Controller
    private final ProfiledPIDController m_controller = 
        new ProfiledPIDController(
            ElevatorSubsystemConst.kp, ElevatorSubsystemConst.ki, ElevatorSubsystemConst.kd,
            new TrapezoidProfile.Constraints(
                ElevatorSubsystemConst.kVel, ElevatorSubsystemConst.kAcc
            ) 
        );

    public ElevatorSubsystem() {
        // Create Instances Of Electronics
        m_LeftMotor = new SparkMax(ElevatorSubsystemConst.leftElevatorMotorCAN, MotorType.kBrushless);
        m_RightMotor = new SparkMax(ElevatorSubsystemConst.rightElevatorMotorCAN, MotorType.kBrushless);

        m_LeftEncoder = m_LeftMotor.getEncoder();
        m_RightEncoder = m_RightMotor.getEncoder();
        
        m_TopSwitch = new DigitalInput(ElevatorSubsystemConst.kTopLimitSwitch);
        m_BotSwitch = new DigitalInput(ElevatorSubsystemConst.kBotLimitSwitch);


        // Configure Motors
        rightMotorConfig = new SparkMaxConfig();
        rightMotorConfig.follow(m_LeftMotor, true);
        m_RightMotor.configure(rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        // Set Default Command
        this.setDefaultCommand(new DefaultElevatorCommand(this));

    }

    // Detect Limit Switches
    public boolean detectTopLimitSwitch() {
        return m_TopSwitch.get();
    }

    public boolean detectBotLimitSwitch() {
        return m_BotSwitch.get();
    }
    
    // Get Encoder Readouts
    public double getEncoderAverage() {
        return (getLeftEncoder() + getRightEncoder())/2;
    }

    public double getLeftEncoder() {
        return m_LeftEncoder.getPosition();
    }

    public double getRightEncoder() {
        return m_RightEncoder.getPosition();
    }

    // Set Elevator Speed
    public void changeElevation(double speed) {
        m_LeftMotor.set(speed);
    }

    @Override
    public void periodic() {
        // Get Telemetry Data
        SmartDashboard.putNumber("Left Encoder", getLeftEncoder());
        SmartDashboard.putNumber("Right Encoder", getRightEncoder());
        SmartDashboard.putBoolean("Top Limit Switch", detectTopLimitSwitch());
        SmartDashboard.putBoolean("Bottom Limit Switch", detectBotLimitSwitch());
    }
}
