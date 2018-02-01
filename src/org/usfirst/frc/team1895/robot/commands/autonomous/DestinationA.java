package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.claw.DeployCube_Claw;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationA extends CommandGroup {
	
	/*
	 * Testing 1/30/18
	 * Starting Position is variable - 2 or 3, test both
	 * ourLeftSwitch is true
	 * closeRoute is true
	 * 
	 */

	int startPos = 2; // get from smartdash?  TEST THIS.
	boolean ourLeftSwitch; // get from FMS
	boolean closeRoute;  //either from smartdash or based on decisions during auto

	public DestinationA() {

		// retrieve FMS data
		ourLeftSwitch = true;
		
		closeRoute = true;
		
		/*//2  -- for testing conditional command
		addSequential(new DriveStraightWithoutPID(0.3, 10));
		addSequential(new TurnWithoutPID(0.3, -90));
		addSequential(new DriveStraightWithoutPID(0.3, 35));
		addSequential(new TurnWithoutPID(0.3, 90));
		addSequential(new DriveStraightWithoutPID(0.3, 5));
		addSequential(new DeployCube_Claw());*/
		

		if (ourLeftSwitch) {
			switch (startPos) {
			case 1:
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new DeployCube_Claw());
			case 2:  //USING FOR TEST
				addSequential(new DriveStraightWithoutPID(0.3, 10));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithoutPID(0.3, 35));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithoutPID(0.3, 5));
				addSequential(new DeployCube_Claw());
			case 3:  //USING FOR TEST
				if (closeRoute) {
					addSequential(new DriveStraightWithoutPID(0.3, 10));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, 90));
					addSequential(new DriveStraightWithoutPID(0.3, 5));
					addSequential(new DeployCube_Claw());
				} else {
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new DeployCube_Claw());
				}
			}

		} else {   //this would be changed - not same as leftOwn

			switch (startPos) {
			case 1:
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new DeployCube_Claw());
			case 2:
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithoutPID(0.3, 50));
				addSequential(new DeployCube_Claw());
			case 3:
				if (closeRoute) {
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, 90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new DeployCube_Claw());
				} else {
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new TurnWithoutPID(0.3, -90));
					addSequential(new DriveStraightWithoutPID(0.3, 50));
					addSequential(new DeployCube_Claw());
				}
			}

		}
	}
}
