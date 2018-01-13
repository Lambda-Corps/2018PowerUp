package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.commands.claw.Default_Claw;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Can grab/release cubes  
 * Motors: 2 motors
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

public class Claw extends Subsystem {

	private CANTalon claw_intake_motor1;
	private CANTalon claw_intake_motor2;
	
	public Claw() {
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_Claw());
    }
}

