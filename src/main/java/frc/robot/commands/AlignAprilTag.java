package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightInterface;


public class AlignAprilTag extends Command {
    LimelightInterface limeLightSubsystem;
    DriveSubsystem driveSubsystem; 

    public AlignAprilTag(LimelightInterface limeLightSubsystem, DriveSubsystem driveSubsystem) {
        this.limeLightSubsystem = limeLightSubsystem;
        this.driveSubsystem = driveSubsystem; 
    }
    
    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
       Pose2d tagPose = limeLightSubsystem.getAprilTagPose2d();
       Pose2d targetPose = new Pose2d(tagPose.getX(), tagPose.getY() - Units.inchesToMeters(27), tagPose.getRotation());
       Command pathfindCommand = driveSubsystem.driveToPoint(targetPose);
       pathfindCommand.addRequirements(driveSubsystem);
       pathfindCommand.schedule();
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
