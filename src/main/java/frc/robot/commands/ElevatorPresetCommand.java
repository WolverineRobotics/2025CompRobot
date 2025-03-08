package frc.robot.commands;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ElevatorSubsystem;

public class ElevatorPresetCommand extends Command {

    private final ElevatorSubsystem m_ElevatorSubsystem;
    private final double setpoint; 

    public ElevatorPresetCommand(ElevatorSubsystem m_ElevatorSubsystem, double setpoint) {
        this.m_ElevatorSubsystem = m_ElevatorSubsystem;
        this.addRequirements(m_ElevatorSubsystem);
        this.setpoint = setpoint;
    }   

    @Override
    public void initialize() {
        m_ElevatorSubsystem.resetPID(setpoint);
    }

    @Override 
    public void execute() {
        m_ElevatorSubsystem.elevationPreset(m_ElevatorSubsystem.calculateSpeed(), setpoint);
    }

    @Override 
    public void end(boolean interrupted) {
        m_ElevatorSubsystem.elevationPreset(0, setpoint);
    }

    @Override 
    public boolean isFinished() {
        return m_ElevatorSubsystem.atSetpoint();
    }


}