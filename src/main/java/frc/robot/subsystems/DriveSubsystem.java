package frc.robot.subsystems;

import java.io.File;
import java.io.IOException;

import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import swervelib.parser.SwerveParser;
import swervelib.math.SwerveMath;
import swervelib.SwerveController;
import swervelib.SwerveDrive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.networktables.StructPublisher;

public class DriveSubsystem extends SubsystemBase {

    SwerveDrive swerveDrive;
    StructPublisher<Pose2d> posePublisher;

    public DriveSubsystem(File directory, double maximumSpeed) throws IOException {
        swerveDrive = new SwerveParser(directory).createSwerveDrive(maximumSpeed);
        posePublisher = NetworkTableInstance.getDefault().getStructTopic("Robot Pose", Pose2d.struct).publish();
    }

    


    
    
 
    public void drive(double translationX, double translationY, double headingX) {
            ChassisSpeeds targetSpeeds = swerveDrive.swerveController.getTargetSpeeds(translationX, translationY, headingX, swerveDrive.getOdometryHeading().getRadians(), swerveDrive.getMaximumChassisVelocity());
            swerveDrive.driveFieldOriented(targetSpeeds);   
            posePublisher.set(swerveDrive.getPose());
            
        }

    @Override 
    public void periodic() {
        posePublisher.set(swerveDrive.getPose());
    }

    
}



