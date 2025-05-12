package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Input;
import frc.robot.subsystems.DriveSubsystem;

public class LockWheelsCommand extends Command {
DriveSubsystem driveBase;


    public LockWheelsCommand(DriveSubsystem driveBase) {
        this.driveBase = driveBase;
        addRequirements(driveBase);
        
    }

    @Override 
    public void initialize() {
    }

    @Override 
    public void execute() {
        driveBase.lockWheels();
    
  
    }

    @Override
    public void end(boolean interrupted) {
        
    }

    @Override 
    public boolean isFinished() {
        return false;
    }
}
