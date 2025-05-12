package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import java.security.KeyStore.PrivateKeyEntry;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

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
    private final SparkMaxConfig leftMotorConfig; 
    private final SparkMaxConfig rightMotorConfig; 
    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;
    private final ProfiledPIDController m_Controller;
    private final DigitalInput limitSwitch; 
    private boolean limitSpeed; 
 

    public ElevatorSubsystem() {
        m_LeftMotor = new SparkMax(ElevatorSubsystemConst.LEFT_ELEVATOR_CAN, MotorType.kBrushless);
        m_RightMotor = new SparkMax(ElevatorSubsystemConst.RIGHT_ELEVATOR_CAN, MotorType.kBrushless);

        m_leftEncoder = m_LeftMotor.getEncoder();
        m_rightEncoder = m_RightMotor.getEncoder();

        this.setDefaultCommand(new DefaultElevatorCommand(this));

        m_Controller = new ProfiledPIDController(
            ElevatorSubsystemConst.kP,
            ElevatorSubsystemConst.kI,
            ElevatorSubsystemConst.kD,
             new TrapezoidProfile.Constraints(
                ElevatorSubsystemConst.MAX_SPEED, 
                ElevatorSubsystemConst.MAX_ACCELERATION));

        m_Controller.setTolerance(0.5);

        limitSpeed = false; 
        limitSwitch = new DigitalInput(ElevatorSubsystemConst.limitSwitchPort);
       
        leftMotorConfig = new SparkMaxConfig(); 
        rightMotorConfig = new SparkMaxConfig(); 

        leftMotorConfig.smartCurrentLimit(
            ElevatorSubsystemConst.ELEVATOR_CURRENT_LIMIT, 
            ElevatorSubsystemConst.ELEVATOR_CURRENT_LIMIT, 
            0
        );


        rightMotorConfig.smartCurrentLimit(
            ElevatorSubsystemConst.ELEVATOR_CURRENT_LIMIT, 
            ElevatorSubsystemConst.ELEVATOR_CURRENT_LIMIT, 
            0
        );

        rightMotorConfig.follow(ElevatorSubsystemConst.LEFT_ELEVATOR_CAN, true);
        m_LeftMotor.configure(leftMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        m_RightMotor.configure(rightMotorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
    }

    public void changeElevation(double speed) {
        if (atBottom() && -speed < 0) {
            m_LeftMotor.set(0);
        }
        else {
            if (limitSpeed) { 
                m_LeftMotor.set(-speed * ElevatorSubsystemConst.ELEVATOR_SPEED_LIMIT);
                
            }
    
            else {
                m_LeftMotor.set(-speed);
                
            }
        }

    }
    
    public boolean atBottom() {
        return !limitSwitch.get();
    }

    public void zeroEncoders() {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
    }

    public void elevationPreset(double speed, double setpoint) {
        m_Controller.setGoal(setpoint);
        if (!m_Controller.atGoal()) {
            if (atBottom() && -speed < 0) {
                m_LeftMotor.set(0);
            }
            else {
                m_LeftMotor.set(speed);
            }
            
        }

    }
    
    public boolean atSetpoint() {
        return m_Controller.atGoal();
    }

    public void resetPID(double currentPos) {
        m_Controller.reset(currentPos);
    }

    public double calculateSpeed() {
        return m_Controller.calculate(m_leftEncoder.getPosition());
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Motor", m_LeftMotor.get());
        SmartDashboard.putNumber("Right Motor", m_LeftMotor.get());
        SmartDashboard.putNumber("Left Encoder", m_leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Encoder", m_rightEncoder.getPosition());
        SmartDashboard.putNumber("Setpoint", m_Controller.getGoal().position);
        SmartDashboard.putBoolean("???", m_Controller.atSetpoint());

        if (m_leftEncoder.getPosition() > 40) {
            limitSpeed = true;
        }

        else {
            limitSpeed = false; 
        }

        if (atBottom()) {
            zeroEncoders();
        }
        
    }
}
