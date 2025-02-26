package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConstants;

public class IntakeSubsystem extends SubsystemBase {

    private final SparkMax powerMotor = new SparkMax(IntakeConstants.kIntakeMotorCANID, MotorType.kBrushless);
    private final DigitalInput limitSwitch = new DigitalInput(IntakeConstants.kLimitSwitchPort);

    public IntakeSubsystem() {

    }
    
    public void storeCoral() {
        while (!limitSwitch.get()) {
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
}
