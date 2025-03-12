package frc.robot.commands;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Input;
import frc.robot.subsystems.DriveSubsystem;

public class TeleopDriveCommand extends Command {
    
    DriveSubsystem driveBase;


    public TeleopDriveCommand(DriveSubsystem driveBase) {
        this.driveBase = driveBase;
        addRequirements(driveBase);
        
    }

    @Override 
    public void initialize() {
    }

    @Override 
    public void execute() {
        driveBase.driveTeleop(Input.getVertical(), Input.getHorizontal(), Input.getHorizontalRotation());
    
  
    }

    @Override
    public void end(boolean interrupted) {
        //driveBase.drive(0, 0, 0);
    }

    @Override 
    public boolean isFinished() {
        return false;
    }

}
