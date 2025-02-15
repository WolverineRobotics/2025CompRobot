package frc.robot.commands;

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

    }

    @Override 
    public void execute() {
        swerveBase.drive(Input.getVertical(), Input.getHorizontal(), Input.getRotation());
    }

    @Override
    public void end(boolean interrupted) {

    }

    @Override 
    public boolean isFinished() {
        return false;
    }

}
