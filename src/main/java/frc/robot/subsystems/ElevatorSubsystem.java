package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorSubsystemConst;
import frc.robot.commands.DefaultElevatorCommand;

public class ElevatorSubsystem extends SubsystemBase{
    private final SparkMax m_LeftMotor;
    private final SparkMax m_RightMotor;
    private final RelativeEncoder m_leftEncoder;
    private final RelativeEncoder m_rightEncoder;
    private final ProfiledPIDController m_Controller;

    public ElevatorSubsystem() {
        m_LeftMotor = new SparkMax(ElevatorSubsystemConst.leftElevatorMotorCAN, MotorType.kBrushless);
        m_RightMotor = new SparkMax(ElevatorSubsystemConst.rightElevatorMotorCAN, MotorType.kBrushless);

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
    }

    public void changeElevation(double speed) {
        m_LeftMotor.set(speed);
        m_RightMotor.set(speed);   // assume their mounted in opposite rotation
    }

    public void zeroEncoders() {
        m_leftEncoder.setPosition(0);
        m_rightEncoder.setPosition(0);
    }

    public void elevationPreset(double preset) {
        m_Controller.setGoal(preset);
        while (!m_Controller.atGoal()){
        m_LeftMotor.set(m_Controller.calculate(m_leftEncoder.getPosition()));
        }

        }
    
    public boolean atSetpoint() {
        return m_Controller.atGoal();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Left Motor", m_LeftMotor.get());
        SmartDashboard.putNumber("Right Motor", m_LeftMotor.get());
        SmartDashboard.putNumber("Left Encoder", m_leftEncoder.getPosition());
        SmartDashboard.putNumber("Right Encoder", -1*m_rightEncoder.getPosition());

        
    }
}
