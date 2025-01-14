package frc.robot.subsystems.DriveBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.servohub.ServoHub.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogEncoder;
import frc.robot.Constants.RobotConstants;


public class SwerveModule {
    

    private final SparkMax steerMotor; 
    private final SparkMax driveMotor; 
    private final AnalogEncoder absoluteEncoder; 
    private final RelativeEncoder driveEncoder; 
    private final RelativeEncoder steerEncoder; 
    private final PIDController drivePidController;
    private final PIDController steerPidController; 
    private final SparkBaseConfig driveMotorConfig; 
    private final SparkMaxConfig steerMotorConfig; 

    



    public SwerveModule(int driveMotorCANID, int steerMotorCANID, int absoluteEncoderChannel) {

        driveMotor = new SparkMax(driveMotorCANID, null);
        steerMotor = new SparkMax(steerMotorCANID, null);
        absoluteEncoder = new AnalogEncoder(absoluteEncoderChannel); 


        driveEncoder = driveMotor.getEncoder();
        steerEncoder = steerMotor.getEncoder();

        drivePidController = new PIDController(RobotConstants.kPDrive, RobotConstants.kIDrive, RobotConstants.kDDrive); 
        steerPidController = new PIDController(RobotConstants.kPSteer, RobotConstants.kISteer, RobotConstants.kDSteer);

        driveMotorConfig = new SparkMaxConfig();
        steerMotorConfig = new SparkMaxConfig();
        
        //steer motor config 
        steerMotorConfig.inverted(false);
        steerMotor.configure(steerMotorConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        //drive motor config 
        driveMotorConfig.inverted(false);
        driveMotor.configure(driveMotorConfig, com.revrobotics.spark.SparkBase.ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);


           
    }

    public double getDistance() {
        return driveEncoder.getPosition();
    }

    public Rotation2d getAngle() {
        return Rotation2d.fromDegrees(steerEncoder.getPosition());
    }

    public void setState(SwerveModuleState state) {

        steerPidController.setSetpoint(state.angle.getDegrees());
        drivePidController.setSetpoint(state.speedMetersPerSecond);
    }

    public double getDriveVelocity() {
        return driveEncoder.getVelocity();

    }

    public double getSteerVelocity() {
        return steerEncoder.getVelocity();
    }


    

}
