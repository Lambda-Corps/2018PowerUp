package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 * Testing 1/30/18 Starting Position is variable - 2 or 3, test both
 * ourLeftSwitch is true closeRoute is true
 */
public class DestinationA extends CommandGroup {

	boolean ourLeftSwitch; // get from FMS
	boolean closeRoute; // either from smartdash or based on decisions during auto

	public DestinationA() {

		addSequential(new PrintCommand("A"));

		// retrieve FMS data to determine where scale is
		if (Robot.closeSwitchNum == 1) { // our switch is on the left
			ourLeftSwitch = true;
		} else {
			ourLeftSwitch = false;
		}

		closeRoute = true; // hard coding it for now
		addSequential(new PrintCommand("A"));

		if (ourLeftSwitch) {
			addSequential(new PrintCommand("we are on left"));
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("A, ourLeftSwitch, position 1"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(30));
				// now directly in front of switch, 40" away
				// temporarily making DriveToObstacle go all the way Dest.A, but in the real
				// code it'll vision-align
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
				// addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("A, ourLeftSwitch, position 2"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(100));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(30));
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
				// addSequential(new DeployCube_Claw());

				break;
			case 3: // USING FOR TEST
				addSequential(new PrintCommand("A, ourLeftSwitch, position 3, close route"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(173));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(30));
				break;
			}

		} else { // our switch is on the right
			addSequential(new PrintCommand("we are on right"));
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("A, not ourLeftSwitch, position 1"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(173));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(30));
				// now in front of Destination A, right switch
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
				// addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("A, not ourLeftSwitch, position 2"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new DriveStraightWithPID(30));
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
				// addSequential(new DeployCube_Claw());
				break;
			case 3: // NOT DONE YET
				addSequential(new PrintCommand("A, not ourLeftSwitch, position 3, closeRoute"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(30));
				break;
			}

		}
	}

	@Override
	public void initialize() {

	}
}
