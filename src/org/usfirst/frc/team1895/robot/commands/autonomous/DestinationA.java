package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.claw.DeployCube_Claw;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToSwitch;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Testing 1/30/18
 * Starting Position is variable - 2 or 3, test both
 * ourLeftSwitch is true
 * closeRoute is true
 */
public class DestinationA extends CommandGroup {

	boolean ourLeftSwitch; // get from FMS
	boolean closeRoute;  //either from smartdash or based on decisions during auto

	public DestinationA() {

		// retrieve FMS data to determine where scale is
		if(Robot.closeSwitchNum == 1) { //our switch is on the left
			ourLeftSwitch = true;
		}
		else {
			ourLeftSwitch = false;
		}
		
		closeRoute = true; //hard coding it for now
		addSequential(new PrintCommand("A"));
		/*//2  -- for testing conditional command
		addSequential(new DriveStraightWithoutPID(0.3, 10));
		addSequential(new TurnWithoutPID(0.3, -90));
		addSequential(new DriveStraightWithoutPID(0.3, 35));
		addSequential(new TurnWithoutPID(0.3, 90));
		addSequential(new DriveStraightWithoutPID(0.3, 5));
		addSequential(new DeployCube_Claw());*/
		

		if (ourLeftSwitch) {
			switch (Robot.startPos) {
			case 1:
				addSequential(new DriveStraightWithoutPID(0.3, 80));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithoutPID(0.3, 56));
				addSequential(new TurnWithoutPID(0.3, -90));
				//addSequential(new AlignToSwitch());
				//addSequential(new DeployCube_Claw());
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
			switch (Robot.startPos) {
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
