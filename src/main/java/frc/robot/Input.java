package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Input {
    
    private final static XboxController OP_CONTROLLER = new XboxController(1);

    public static double elevationChangeInput() {
        return OP_CONTROLLER.getRightY() * 0.5;
    } 
}

