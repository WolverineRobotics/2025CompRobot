package frc.robot.subsystems;

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

    private final SparkMax powerMotor = new SparkMax(IntakeConstants.kIntakeMotorCANID, MotorType.kBrushless);
    private final SparkMaxConfig motorConfig = new SparkMaxConfig();
    private final DigitalInput limitSwitch = new DigitalInput(IntakeConstants.kLimitSwitchPort);

    public IntakeSubsystem() {
        motorConfig.secondaryCurrentLimit(40);
        powerMotor.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
    
    public void storeCoral() {
        if (!limitSwitch.get()) {
            powerMotor.set(1);
        }
        powerMotor.set(0);
    }

    public void shoot() {
        powerMotor.set(1);
    }

    public boolean hasGamepiece() {
        return limitSwitch.get();
    }

    @Override 
    public void periodic() {
        SmartDashboard.putBoolean("Coral", this.hasGamepiece());
    }
}
