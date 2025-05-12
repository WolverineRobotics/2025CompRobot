package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Input;
import frc.robot.subsystems.IntakeSubsystem;

public class ShootCommand extends Command {

    private final IntakeSubsystem intake;
    
    public ShootCommand(IntakeSubsystem intake) {
        this.intake = intake;
    }

    @Override 
    public void initialize() {

    }

    @Override 
    public void execute() {
        intake.shoot();
    }

    @Override  
    public void end(boolean interrupted) {
        intake.setSpeed(0);
    }

    @Override 
    public boolean isFinished() {
        return !intake.hasGamepiece() || Input.endShootCommand();
    }

}
