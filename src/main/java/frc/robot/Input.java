package frc.robot;

import edu.wpi.first.wpilibj.XboxController;

public class Input {
    
    public static XboxController driveController = new XboxController(0);


    //Driver Controls
    public static double getVertical(){
        return (driveController.getLeftY());
    }

    public static double getHorizontal(){
        return driveController.getLeftX();
    }

    public static double getRotation(){
        return driveController.getRightX();
    }
}
