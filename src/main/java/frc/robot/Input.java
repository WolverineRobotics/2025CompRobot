package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OperatorConstants;

// A class to get input from the controller effectivlly 
public class Input {
    public static XboxController driveController = new XboxController(0);

    public static double getHorizontal() {
        if (driveController.getLeftX() > 0.1) {
            return driveController.getLeftX() * OperatorConstants.DRIVE_OFFSET;
        }
        return 0;
    }

    public static double getHorizontalRotation() {
        if (driveController.getRightX() > 0.1) {
            return driveController.getRightX() * OperatorConstants.DRIVE_OFFSET;
        }
        return 0; 
    }

    public static double getVertical() {
        if (driveController.getLeftY() > 0.1) {
            return driveController.getLeftY() * OperatorConstants.DRIVE_OFFSET;
        }
        return 0;
    }

    // Debug method only 
    public static Boolean turnRight() {
        return driveController.getBButton();
    }
}
