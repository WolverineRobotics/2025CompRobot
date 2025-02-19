package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Input {
    public static XboxController driveController = new XboxController(0);

    public static double getHorizontal() {
        return driveController.getLeftX() * -0.1;
    }

    public static double getRotation() {
        return driveController.getRightX() * -0.1;
    }

    public static double getVertical() {
        return driveController.getLeftY() * -0.1;
    }

    public static Boolean turnRight() {
        return driveController.getBButton();
    }
}
