package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Input {
    
    private final static XboxController OP_CONTROLLER = new XboxController(1);

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

