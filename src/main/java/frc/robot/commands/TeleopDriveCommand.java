package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Input;
import frc.robot.subsystems.DriveBase.SwerveDrive;

public class TeleopDriveCommand extends Command {
    private final SwerveDrive m_Drive;


    public TeleopDriveCommand(SwerveDrive subsystem) {
        m_Drive = subsystem; 
        addRequirements(m_Drive);
    }


    @Override 
    public void execute() {
        m_Drive.drive(Input.getHorizontal() * 0.2 , Input.getVertical() * 0.2 , Input.getRotation() * 0.2);
    }
    
}
