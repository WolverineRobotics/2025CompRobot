package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.LimelightHelpers;
import frc.robot.Constants.DriveConst;
import frc.robot.subsystems.DriveSubsystem;
import frc.robot.subsystems.LimelightInterface;


public class AlignAprilTag extends Command {
    private final DriveSubsystem driveSubsystem; 
    private final PIDController xController, yController, rotController;
    private final boolean isRightScore;
    private Timer stopTimer, dontSeeTagTimer; 
    

    public AlignAprilTag(DriveSubsystem driveSubsystem, boolean isRightScore) {

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

        yController.setSetpoint(isRightScore ? DriveConst.Y_SETPOINT_REEF_ALIGNMENT : -1 * DriveConst.Y_SETPOINT_REEF_ALIGNMENT);
        yController.setTolerance(DriveConst.Y_TOLERANCE_REEF_ALIGNMENT);

        rotController.setSetpoint(DriveConst.ROT_SETPOINT_REEF_ALIGNMENT);
        rotController.setTolerance(DriveConst.ROT_TOLERANCE_REEF_ALIGNMENT);
    }

    @Override 
    public void execute() {
       if (LimelightHelpers.getTV("limelight")) {
        double[] positions =  LimelightHelpers.getBotPose_TargetSpace("limelight");
        double xSpeed = xController.calculate(positions[2]);
        double ySpeed = -yController.calculate(positions[0]);
        double rotSpeed = -rotController.calculate(positions[4]);

        driveSubsystem.drive(xSpeed, ySpeed, rotSpeed);         // other speeds set to 0 for PID tuning
        
        SmartDashboard.putNumber("X PID", xSpeed);
        SmartDashboard.putNumber("Y PID", ySpeed);
        SmartDashboard.putNumber("ROT PID", rotSpeed);
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
