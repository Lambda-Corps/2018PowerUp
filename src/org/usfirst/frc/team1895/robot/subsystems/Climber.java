package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.climbing.Default_ManuallyClimb;

<<<<<<< HEAD
import com.ctre.phoenix.motorcontrol.can.*;
=======
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

>>>>>>> aea375b60b023ef39887db59ab8a905bfa8ede53
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Climbs up and down 
 * Motors: 1
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

public class Climber extends Subsystem {

    private TalonSRX climber_motor;
    
    public Climber() {
    	climber_motor = new TalonSRX(RobotMap.CLIMBER_MOTOR_PORT);
    }
    public void manualClimbing(double velocity) {
    	if (velocity > 1.0) velocity = 1.0;
    	if (velocity <-1.0) velocity = -1.0;
    	climber_motor.set(null, velocity);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_ManuallyClimb());
    }
}

