package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.commands.claw.Default_Claw;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Can grab/release cubes  
 * Motors: 2 motors
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

public class Claw extends Subsystem {

	private TalonSRX claw_intake_motor1;
	private TalonSRX claw_intake_motor2;
	
	public Claw() {
		
	}

    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_Claw());
    }
}

