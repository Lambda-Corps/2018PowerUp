package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.commands.lowerIntake.Default_LowerIntake;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Intake system can retract and extend to wrap around cubes using a piston. Intake can also grab and 
 * release cubes. 
 * Motors: 2 pistons (retracting/extending), 2 motors (grabbing/releasing cubes)
 * Sensors: 
 * Last Updated: 1/13/2018 by Maddy
 */

public class LowerIntake extends Subsystem {

	// motors
	private CANTalon left_lower_intake_motor;
	private CANTalon right_lower_intake_motor;
	
	// pneumatics
	private final DoubleSolenoid lower_intake_solenoid;
	
	public LowerIntake() {
		//motors
		
		//pneumatics
		
	}
	
    public void initDefaultCommand() {
        // Set the default command for a subsystem here.
        setDefaultCommand(new Default_LowerIntake());
    }
}

