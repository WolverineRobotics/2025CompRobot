package frc.robot;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OperatorConstants;

// A class to get input from the controller effectivlly 
public class Input {
    private static XboxController DRIVE_CONTROLLER = new XboxController(0);
    private final static XboxController OP_CONTROLLER = new XboxController(1);
  
  
    private static double deadBand(double input, double deadBand) {
        if (Math.abs(input) > deadBand) {
            return input;
        } else {
            return 0;
        }
    }

    public static double getHorizontal() {
        return deadBand(
                DRIVE_CONTROLLER.getLeftX() * OperatorConstants.DRIVE_OFFSET,
                OperatorConstants.CONTROLLER_DEADBAND
        );
    }

    public static double getHorizontalRotation() {
        return deadBand(
            DRIVE_CONTROLLER.getRightX() * OperatorConstants.DRIVE_OFFSET,
            OperatorConstants.CONTROLLER_DEADBAND
        );
    }

    public static double getVertical() {
        return deadBand(
            DRIVE_CONTROLLER.getLeftY() * OperatorConstants.DRIVE_OFFSET,
            OperatorConstants.CONTROLLER_DEADBAND
        );
    }
  
    public static double elevationChangeInput() {
        return OP_CONTROLLER.getRightY() * 0.1;
    } 

    public static boolean setL4() {
        return OP_CONTROLLER.getYButton();
    }

    public static boolean setL3() {
        return OP_CONTROLLER.getXButton();
    }

    public static boolean setL2() {
        return OP_CONTROLLER.getBButton();
    }

    public static boolean setL1() {
        return OP_CONTROLLER.getAButton();
    }

}
