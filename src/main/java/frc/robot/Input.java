package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.OperatorConstants;

public class Input {
    private static final XboxController opController = new XboxController(OperatorConstants.kOperatorControllerPort);

    public static boolean getShoot() {
        return opController.getAButton();
    }

    public static boolean getIntake() {
        return opController.getBButton();
    }
}
