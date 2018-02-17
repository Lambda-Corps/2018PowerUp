package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.climbing.Default_ManuallyClimb;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Climbs up and down 
 * Motors: 1
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

// TODO -- Code will not work with new Talon library, needs to be fixed.
public class Climber extends Subsystem {

    private TalonSRX climber_motor;
    
    public Climber() {
    	climber_motor = new TalonSRX(RobotMap.CLIMBER_MOTOR_PORT);
    	System.out.println("Init of climber");
    }
    
    public void manualClimbing(double velocity) {
    	
    	// Range checking
    	if (velocity > 1.0) velocity = 1.0;
    	if (velocity <-1.0) velocity = -1.0;
    	climber_motor.set(ControlMode.PercentOutput, velocity);
    	//Get encoder value
    	//System.out.println(String.format("encoder: %6.2f", climber_motor.getSensorCollection().getQuadraturePosition()));
    	//System.out.println (String.format("velocity: %6.2f ", velocity));
    	//climber_motor.set(ControlMode.PercentOutput, velocity);
    }
    
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_ManuallyClimb());
    }
}

