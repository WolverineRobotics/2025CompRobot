package frc.robot.subsystems;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.util.internal.PrivateMaxEntriesMap;
import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.commands.PathPlannerAuto;
import com.pathplanner.lib.commands.PathfindingCommand;
import com.pathplanner.lib.config.PIDConstants;
import com.pathplanner.lib.config.RobotConfig;
import com.pathplanner.lib.controllers.PPHolonomicDriveController;
import com.pathplanner.lib.path.PathConstraints;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.StructPublisher;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Input;
import frc.robot.Constants.DriveConst;
import frc.robot.commands.TeleopDriveCommand;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveControllerConfiguration;
import swervelib.parser.SwerveParser;
import swervelib.telemetry.SwerveDriveTelemetry;
import swervelib.telemetry.SwerveDriveTelemetry.TelemetryVerbosity;

public class DriveSubsystem extends SubsystemBase {

    private SwerveDrive swerveDrive;
    private StructPublisher<Pose2d> posePublisher;
    private boolean limitSpeed; 
    

    public DriveSubsystem(File directory, double maximumSpeed) throws IOException {
        SwerveDriveTelemetry.verbosity = TelemetryVerbosity.HIGH;
        //Creating the swerveDrive from the configuration files 
        swerveDrive = new SwerveParser(directory).createSwerveDrive(maximumSpeed);

        setDefaultCommand(new TeleopDriveCommand(this));
        
        
        //Setting up path planner 
        setupPathPlanner();

        //Zeros Gyro at start of auto 
        RobotModeTriggers.autonomous().onTrue(Commands.runOnce(this::zero));

        limitSpeed = false; 

          
    }
 
    /**
     * Teleop drive method expecting controller input
     * @param translationX value between -1 and 1
     * @param translationY value between -1 and 1
     * @param headingX rotational velocity as a value between -1 and 1
     */
    public void driveTeleop(double translationX, double translationY, double headingX) {
            // Scaling the inputs to the correct speeds
            double maxSpeed = limitSpeed ? swerveDrive.getMaximumChassisVelocity() * DriveConst.TELEOP_DRIVE_SPEED_LIMIT : swerveDrive.getMaximumChassisVelocity();
            ChassisSpeeds targetSpeeds = new ChassisSpeeds(
                translationX * maxSpeed, 
                translationY * maxSpeed, 
                headingX * swerveDrive.getMaximumChassisAngularVelocity()
            );
            swerveDrive.driveFieldOriented(targetSpeeds);
               
        }

    public double getMaxSpeed() {
        return swerveDrive.getMaximumChassisVelocity();
    }

        
    /**
     * Converted velocity inputs
     * @param xSpeed Value in m/s
     * @param ySpeed Value in m/s
     * @param rotSpeed Value in rad/s
     */
    public void drive(double xSpeed, double ySpeed, double rotSpeed) {
        //System.out.println("DRIVE!!!!");
        ChassisSpeeds targetSpeeds = new ChassisSpeeds(xSpeed, ySpeed, rotSpeed);
        swerveDrive.drive(targetSpeeds); //Error is somewhere here
    }

    public double getAngularVelocity() {
        return Input.getHorizontalRotation() * swerveDrive.getMaximumChassisAngularVelocity();
    }

    public double getMaxAngularVelocity() {
        return swerveDrive.getMaximumChassisAngularVelocity();
    }

    public void toggleLimit() {
        limitSpeed = true;
    }
    
    @Override 
    public void periodic() {
        
        // Putting Encoder values to smartdashboard
        SmartDashboard.putNumber("Front Right Angle Encoder", swerveDrive.getModules()[0].getAbsoluteEncoder().getAbsolutePosition());
        SmartDashboard.putNumber("Front Left Angle Encoder", swerveDrive.getModules()[1].getAbsoluteEncoder().getAbsolutePosition());
        SmartDashboard.putNumber("Back Left Angle Encoder", swerveDrive.getModules()[2].getAbsoluteEncoder().getAbsolutePosition());
        SmartDashboard.putNumber("Back Right Angle Encoder", swerveDrive.getModules()[3].getAbsoluteEncoder().getAbsolutePosition());
        

    }

    public void zero() {
        //Zeroing the gyro 
        swerveDrive.zeroGyro();
    } 
    
   
    
    //IDK I copied and pasted this method
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



