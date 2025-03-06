package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorPresetCommand extends Command {

    private final ElevatorSubsystem m_ElevatorSubsystem;
    private final double preset; 

    public ElevatorPresetCommand(ElevatorSubsystem m_ElevatorSubsystem, double preset) {
        this.m_ElevatorSubsystem = m_ElevatorSubsystem;
        this.addRequirements(m_ElevatorSubsystem);
        this.preset = preset;
    }   

    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        m_ElevatorSubsystem.elevationPreset(preset);
    }

    @Override 
    public void end(boolean interrupted) {

    }

    @Override 
    public boolean isFinished() {
        return false;
    }


}