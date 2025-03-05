package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OperatorConstants;

// A class to get input from the controller effectivlly 
public class Input {
    public static XboxController driveController = new XboxController(0);

    public static double getHorizontal() {
        if (Math.abs(driveController.getLeftX()) > OperatorConstants.CONTROLLER_DEADBAND) {
            return driveController.getLeftX() * OperatorConstants.DRIVE_OFFSET;
        }
        return 0;
    }

    public static double getHorizontalRotation() {
        if (Math.abs(driveController.getRightX()) > OperatorConstants.CONTROLLER_DEADBAND) {
            return driveController.getRightX() * OperatorConstants.DRIVE_OFFSET;
        }
        return 0; 
    }

    public static double getVertical() {
        if (Math.abs(driveController.getLeftY()) > OperatorConstants.CONTROLLER_DEADBAND) {
            return driveController.getLeftY() * OperatorConstants.DRIVE_OFFSET;
        }
        return 0;
    }

    public static Boolean alignAprilTag() {
        return driveController.getBButton();
    }
}
