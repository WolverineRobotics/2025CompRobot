package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeSubsystem;

public class IntakeCommand extends Command {

    private final IntakeSubsystem intake;
    
    public IntakeCommand(IntakeSubsystem intake) {
        this.intake = intake;
        this.addRequirements(intake);
    }

    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        intake.storeCoral();
    }

    @Override  
    public void end(boolean interrupted) {

    }

    @Override 
    public boolean isFinished() {
        return intake.hasGamepiece();
    }
}
