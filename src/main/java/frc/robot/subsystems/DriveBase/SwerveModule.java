package frc.robot.subsystems.DriveBase;

import com.revrobotics.RelativeEncoder;
import com.revrobotics.servohub.ServoHub.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.wpilibj.AnalogEncoder;
import frc.robot.Constants.DriveConstants;


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

        //Declaring the motors for drive and steer
        driveMotor = new SparkMax(driveMotorCANID, MotorType.kBrushless);
        steerMotor = new SparkMax(steerMotorCANID, MotorType.kBrushless);
        absoluteEncoder = new AnalogEncoder(absoluteEncoderChannel); 


        //Getting the encoders from the motors 
        driveEncoder = driveMotor.getEncoder();
        steerEncoder = steerMotor.getEncoder();


        //Creating the PID controllers 
        drivePidController = new PIDController(DriveConstants.kPDrive, DriveConstants.kIDrive, DriveConstants.kDDrive); 
        steerPidController = new PIDController(DriveConstants.kPSteer, DriveConstants.kISteer, DriveConstants.kDSteer);

        // Creating the config objects for the drive and steer 
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

        //drivePidController.setSetpoint(state.speedMetersPerSecond);

        driveMotor.set(drivePidController.calculate(driveEncoder.getPosition(), state.speedMetersPerSecond));

        steerMotor.set(steerPidController.calculate(steerEncoder.getPosition(), state.angle.getDegrees()));
    }

    public double getDriveVelocity() {
        return driveEncoder.getVelocity();

    }

    public double getSteerVelocity() {
        return steerEncoder.getVelocity();
    }


    

}
