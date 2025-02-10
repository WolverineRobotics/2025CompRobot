package frc.robot.subsystems;

import java.io.File;
import java.io.IOException;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveSubsystemConstants;
import swervelib.parser.SwerveParser;
import swervelib.parser.json.modules.DriveConversionFactorsJson;
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
        setupPathPlanner();

          
    }
 
    public void drive(double translationX, double translationY, double headingX) {
            ChassisSpeeds targetSpeeds = swerveDrive.swerveController.getTargetSpeeds(translationX, translationY, headingX, swerveDrive.getOdometryHeading().getRadians(), swerveDrive.getMaximumChassisVelocity());
            swerveDrive.driveFieldOriented(targetSpeeds);   
            posePublisher.set(swerveDrive.getPose());        
        }
    
    public void driveToPoint(Pose2d point) {
        Pose2d botPose = swerveDrive.getPose();
        double xDistance = botPose.getX() - point.getX();
        double yDistance = botPose.getY() - point.getY();
        double netDistance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));
        double time = netDistance / DriveSubsystemConstants.kAlignVelocity;
        ChassisSpeeds speeds = new ChassisSpeeds((xDistance / time), (yDistance / time), 0);
        swerveDrive.driveFieldOriented(speeds);   
    }

    @Override 
    public void periodic() {
        posePublisher.set(swerveDrive.getPose());
        SmartDashboard.putNumber("Front Left Angle Encoder", swerveDrive.getModules()[0].getPosition().angle.getDegrees());
        SmartDashboard.putNumber("Front Right Angle Encoder", swerveDrive.getModules()[1].getPosition().angle.getDegrees());
        SmartDashboard.putNumber("Back Left Angle Encoder", swerveDrive.getModules()[2].getPosition().angle.getDegrees());
        SmartDashboard.putNumber("Back Right Angle Encoder", swerveDrive.getModules()[3].getPosition().angle.getDegrees());

        SmartDashboard.putNumber("Front Left Drive Encoder", swerveDrive.getModules()[0].getDriveMotor().getVelocity());
        SmartDashboard.putNumber("Front Right Drive Encoder", swerveDrive.getModules()[1].getDriveMotor().getVelocity());
        SmartDashboard.putNumber("Back Left Drive Encoder",  swerveDrive.getModules()[2].getDriveMotor().getVelocity());
        SmartDashboard.putNumber("Back Right Drive Encoder", swerveDrive.getModules()[3].getDriveMotor().getVelocity());

    }

    public void zero() {
        swerveDrive.zeroGyro();
    }   
    
    public void setupPathPlanner()
    {
        // Load the RobotConfig from the GUI settings. You should probably
        // store this in your Constants file
        RobotConfig config;
        try
        {
        config = RobotConfig.fromGUISettings();

        final boolean enableFeedforward = true;
        // Configure AutoBuilder last
        AutoBuilder.configure(
            swerveDrive::getPose,
            // Robot pose supplier
            swerveDrive::resetOdometry,
            // Method to reset odometry (will be called if your auto has a starting pose)
            swerveDrive::getRobotVelocity,
            // ChassisSpeeds supplier. MUST BE ROBOT RELATIVE
            (speedsRobotRelative, moduleFeedForwards) -> {
                if (enableFeedforward)
                {
                swerveDrive.drive(
                    speedsRobotRelative,
                    swerveDrive.kinematics.toSwerveModuleStates(speedsRobotRelative),
                    moduleFeedForwards.linearForces()
                                );
                } else
                {
                swerveDrive.setChassisSpeeds(speedsRobotRelative);
                }
            },
            // Method that will drive the robot given ROBOT RELATIVE ChassisSpeeds. Also optionally outputs individual module feedforwards
            new PPHolonomicDriveController(
                // PPHolonomicController is the built in path following controller for holonomic drive trains
                new PIDConstants(5.0, 0.0, 0.0),
                // Translation PID constants
                new PIDConstants(5.0, 0.0, 0.0)
                // Rotation PID constants
            ),
            config,
            // The robot configuration
            () -> {
                // Boolean supplier that controls when the path will be mirrored for the red alliance
                // This will flip the path being followed to the red side of the field.
                // THE ORIGIN WILL REMAIN ON THE BLUE SIDE

                var alliance = DriverStation.getAlliance();
                if (alliance.isPresent())
                {
                return alliance.get() == DriverStation.Alliance.Red;
                }
                return false;
            },
            this
            // Reference to this subsystem to set requirements
                            );

        } catch (Exception e)
        {
        // Handle exception as needed
        e.printStackTrace();
        }
        //Preload PathPlanner Path finding
        // IF USING CUSTOM PATHFINDER ADD BEFORE THIS LINE
        PathfindingCommand.warmupCommand().schedule();
    }

    public Command getAutoCommand(String pathName) {
        return new PathPlannerAuto(pathName);
    }

}



