package frc;

import edu.wpi.first.wpilibj.XboxController;

public class Input {
    public static XboxController driveController = new XboxController(0);

    public static double getHorizontal() {
        return driveController.getLeftX() * -1 ;
    }

    public static double getRotation() {
        return driveController.getRightX() * -1;
    }

    public static double getVertical() {
        return driveController.getLeftY() * -1 ;
    }
}
