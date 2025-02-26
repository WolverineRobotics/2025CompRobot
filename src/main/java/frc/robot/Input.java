package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Input {
    public static XboxController driveController = new XboxController(0);

    public static double getHorizontal() {
        return driveController.getLeftX() * -1;
    }

    public static double getHorizontalRotation() {
        return driveController.getRightX() * -0.75;
    }

    public static double getVerticalRotation() {
        return driveController.getRightY() * -0.75;
    }

    public static double getVertical() {
        return driveController.getLeftY() * -1;
    }

    public static Boolean turnRight() {
        return driveController.getBButton();
    }
}
