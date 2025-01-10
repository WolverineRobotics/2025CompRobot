package frc.robot.subsystems.DriveBase;

import com.ctre.phoenix6.hardware.Pigeon2;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.RobotConstants;

public class SwerveDrive extends SubsystemBase {

    private final SwerveDriveKinematics kinematics; 
    private final Pigeon2 gyro;
    private final SwerveDriveOdometry odometry;  
    private final SwerveModule[] swerveModules = {new SwerveModule(0, 0, 0), new SwerveModule(0, 0, 0), new SwerveModule(0, 0, 0), new SwerveModule(0, 0, 0)};; 


    public SwerveDrive() {
        kinematics = new SwerveDriveKinematics(
            new Translation2d(Units.inchesToMeters(RobotConstants.kDistanceToWheel), Units.inchesToMeters(RobotConstants.kDistanceToWheel)),
            new Translation2d(Units.inchesToMeters(RobotConstants.kDistanceToWheel), Units.inchesToMeters(RobotConstants.kDistanceToWheel * -1)),
            new Translation2d(Units.inchesToMeters(RobotConstants.kDistanceToWheel * -1), Units.inchesToMeters(RobotConstants.kDistanceToWheel)),
            new Translation2d(Units.inchesToMeters(RobotConstants.kDistanceToWheel * -1 ), Units.inchesToMeters(RobotConstants.kDistanceToWheel * -1))
        );

        gyro = new Pigeon2(0);


        odometry = new SwerveDriveOdometry(
            kinematics, 
            new Rotation2d(Units.degreesToRadians(gyro.getYaw().getValueAsDouble())), 
            new SwerveModulePosition[]{
                new SwerveModulePosition(), 
                new SwerveModulePosition(), 
                new SwerveModulePosition(), 
                new SwerveModulePosition()}, 
            new Pose2d(0, 0, new Rotation2d())
        );

    }


    public void drive(double xVelocity, double yVelocity, double angularVelocity) {
         ChassisSpeeds speed = new ChassisSpeeds(Units.inchesToMeters(xVelocity), Units.inchesToMeters(yVelocity), Units.degreesToRadians(angularVelocity));

         SwerveModuleState[] swerveModuleStates = kinematics.toSwerveModuleStates(speed);

         swerveModules[0].setState(swerveModuleStates[0]);
         swerveModules[1].setState(swerveModuleStates[1]);
         swerveModules[2].setState(swerveModuleStates[2]);
         swerveModules[3].setState(swerveModuleStates[3]);

    }

    public SwerveModulePosition[] getCurrentSwerveModulePositions() {

        return new SwerveModulePosition[]{
            new SwerveModulePosition(swerveModules[0].getDistance(), swerveModules[0].getAngle()),
            new SwerveModulePosition(swerveModules[1].getDistance(), swerveModules[1].getAngle()),
            new SwerveModulePosition(swerveModules[2].getDistance(), swerveModules[2].getAngle()),
            new SwerveModulePosition(swerveModules[3].getDistance(), swerveModules[3].getAngle())
        };
    }

    @Override 
    public void periodic() {
        odometry.update( new Rotation2d(Units.degreesToRadians(gyro.getYaw().getValueAsDouble())), getCurrentSwerveModulePositions());
    }
    
    
}
