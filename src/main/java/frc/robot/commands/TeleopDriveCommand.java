package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Input;
import frc.robot.subsystems.DriveSubsystem;

public class TeleopDriveCommand extends Command {
    
    DriveSubsystem swerveBase;


    public TeleopDriveCommand(DriveSubsystem driveBase) {
        swerveBase = driveBase;
        addRequirements(swerveBase);
        
    }

    @Override 
    public void initialize() {
        swerveBase.drive(0, 0, 0);
    }

    @Override 
    public void execute() {
        swerveBase.driveTeleop(Input.getVertical(), Input.getHorizontal(), Input.getHorizontalRotation());
    
  
    }

    @Override
    public void end(boolean interrupted) {
        swerveBase.drive(0, 0, 0);
    }

    @Override 
    public boolean isFinished() {
        return false;
    }

}
