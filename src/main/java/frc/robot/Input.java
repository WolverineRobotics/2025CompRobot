package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OperatorConstants;

// A class to get input from the controller effectivlly 
public class Input {
    public static XboxController driveController = new XboxController(0);

    public static double getHorizontal() {
        return deadBand(
                driveController.getLeftX() * OperatorConstants.DRIVE_OFFSET,
                OperatorConstants.CONTROLLER_DEADBAND
        );
    }

    public static double getHorizontalRotation() {
        return deadBand(
            driveController.getRightX() * OperatorConstants.DRIVE_OFFSET,
            OperatorConstants.CONTROLLER_DEADBAND
        );
    }

    public static double getVertical() {
        return deadBand(
            driveController.getLeftY() * OperatorConstants.DRIVE_OFFSET,
            OperatorConstants.CONTROLLER_DEADBAND
        );
    }

    public static Boolean alignAprilTag() {
        return driveController.getAButton();
    }

    private static double deadBand(double input, double deadBand) {
        if (Math.abs(input) > deadBand) {
            return input;
        } else {
            return 0;
        }
    }
}
