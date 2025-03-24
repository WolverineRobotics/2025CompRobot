package frc.robot.subsystems;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    private final SparkMax powerMotor = new SparkMax(IntakeConstants.INTAKE_MOTOR_CAN_ID, MotorType.kBrushless);
    private final RelativeEncoder motorEncoder; 
    private final SparkMaxConfig motorConfig = new SparkMaxConfig();
    private final DigitalInput limitSwitch = new DigitalInput(IntakeConstants.LIMIT_SWITCH_PORT);

    public IntakeSubsystem() {
        motorConfig.secondaryCurrentLimit(IntakeConstants.CURRENT_LIMIT);
        powerMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        motorEncoder = powerMotor.getEncoder();
    }
    
    public void storeCoral() {
        if (!hasGamepiece()) {
            powerMotor.set(IntakeConstants.INTAKE_SPEED);
        }
        else {
        powerMotor.set(0);
        }
    }

    public void shoot() {
        powerMotor.set(IntakeConstants.OUTTAKE_SPEED);
    }

    public boolean hasGamepiece() {
        return !limitSwitch.get();
    }

    public void setSpeed(double speed) {
        powerMotor.set(speed);
    }

    @Override 
    public void periodic() {
        SmartDashboard.putBoolean("Coral", hasGamepiece());
        SmartDashboard.putNumber("Intake Encoder", motorEncoder.getPosition());
    }
}
