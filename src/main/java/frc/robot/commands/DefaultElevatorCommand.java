package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Input;
import frc.robot.subsystems.ElevatorSubsystem;

public class DefaultElevatorCommand extends Command{
    
    private final ElevatorSubsystem m_ElevatorSubsystem;

    public DefaultElevatorCommand(ElevatorSubsystem m_ElevatorSubsystem) {

        this.m_ElevatorSubsystem = m_ElevatorSubsystem;
        this.addRequirements(m_ElevatorSubsystem);

    }


    @Override
    public void initialize() {
        // m_ElevatorSubsystem.zeroEncoders();
    }
  
    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        m_ElevatorSubsystem.changeElevation(Input.elevationChangeInput());
        
    }
  
    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {}
  
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
      return false;
    }

}