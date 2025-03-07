package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.DriveConst;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightInterface;


public class AlignAprilTag extends Command {
    private final LimelightInterface limeLightSubsystem;
    private final DriveSubsystem driveSubsystem; 
    private final PIDController xController, yController, rotController;
    private final boolean isRightScore;
    private Timer stopTimer, dontSeeTagTimer; 
    

    public AlignAprilTag(LimelightInterface limeLightSubsystem, DriveSubsystem driveSubsystem, boolean isRightScore) {

        this.limeLightSubsystem = limeLightSubsystem; 
        this.addRequirements(limeLightSubsystem);

        this.driveSubsystem = driveSubsystem;
        this.addRequirements(driveSubsystem);

        this.isRightScore = isRightScore;

        xController = new PIDController(
            DriveConst.X_TRANSLATION_P,
            DriveConst.X_TRANSLATION_I,
            DriveConst.X_TRANSLATION_D
        );

        yController = new PIDController(
            DriveConst.Y_TRANSLATION_P,
            DriveConst.Y_TRANSLATION_I,
            DriveConst.Y_TRANSLATION_D
        );

        rotController = new PIDController(
            DriveConst.ROTATION_P,
            DriveConst.ROTATION_I, 
            DriveConst.ROTATION_D
        );
    }

    public boolean atSetpoint() {
        return rotController.atSetpoint() || xController.atSetpoint() || yController.atSetpoint();
    }
    
    @Override 
    public void initialize() {
        this.dontSeeTagTimer = new Timer(); 
        dontSeeTagTimer.start(); 

        this.stopTimer = new Timer();
        stopTimer.start();

        xController.setSetpoint(DriveConst.X_SETPOINT_REEF_ALIGNMENT);
        xController.setTolerance(DriveConst.X_TOLERANCE_REEF_ALIGNMENT);

        yController.setSetpoint(isRightScore ? DriveConst.Y_SETPOINT_REEF_ALIGNMENT : -DriveConst.Y_SETPOINT_REEF_ALIGNMENT);
        yController.setTolerance(DriveConst.Y_TOLERANCE_REEF_ALIGNMENT);

        rotController.setSetpoint(DriveConst.ROT_SETPOINT_REEF_ALIGNMENT);
        rotController.setTolerance(DriveConst.ROT_TOLERANCE_REEF_ALIGNMENT);
    }

    @Override 
    public void execute() {
       if (LimelightHelpers.getTV("limelight")) {
        Pose2d botPose =  LimelightHelpers.getBotPose2d("limelight");
        double xSpeed = xController.calculate(botPose.getX());
        double ySpeed = yController.calculate(botPose.getY());
        double rotSpeed = rotController.calculate(botPose.getRotation().getRadians());
        driveSubsystem.drive(xSpeed, ySpeed, rotSpeed);
       }

       if (!atSetpoint()) {
        stopTimer.reset();
       }

       else {
        driveSubsystem.drive(0, 0, 0);
       }

    }

    @Override
    public void end(boolean interrupted) {
        driveSubsystem.drive(0, 0, 0);
    }

    @Override 
    public boolean isFinished() {
        return this.dontSeeTagTimer.hasElapsed(DriveConst.DONT_SEE_TAG_WAIT_TIME) || this.stopTimer.hasElapsed(DriveConst.POSE_VALIDATION_TIME);
    }
}
