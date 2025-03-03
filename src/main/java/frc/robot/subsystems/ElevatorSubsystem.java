package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ElevatorSubsystemConst;
import frc.robot.commands.DefaultElevatorCommand;

public class ElevatorSubsystem extends SubsystemBase{
    private final SparkMax m_LeftMotor;
    private final SparkMax m_RightMotor;

    public ElevatorSubsystem() {
        m_LeftMotor = new SparkMax(ElevatorSubsystemConst.leftElevatorMotorCAN, MotorType.kBrushless);
        m_RightMotor = new SparkMax(ElevatorSubsystemConst.rightElevatorMotorCAN, MotorType.kBrushless);

        this.setDefaultCommand(new DefaultElevatorCommand(this));
    }

    public void changeElevation(double speed) {
        m_LeftMotor.set(speed);
        m_RightMotor.set(-speed);   // assume their mounted in opposite rotation
    }
}
