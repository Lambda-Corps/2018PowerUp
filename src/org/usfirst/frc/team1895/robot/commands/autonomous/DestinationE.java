package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationE extends CommandGroup {

	boolean ourLeftSwitch;

	public DestinationE() {
		addSequential(new PrintCommand("E"));

		if (Robot.closeSwitchNum == 1) { // our switch is on the left
			ourLeftSwitch = true;
		} else { // our switch is on the right
			ourLeftSwitch = false;
		}
		
//		addSequential(new WaitCommand(3));
//		addSequential(new PrintCommand("done"));

		if (ourLeftSwitch) {
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithPID(220));
				addSequential(new TurnWithoutPID(0.5, 90));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.5, -90));
				addSequential(new DriveStraightWithPID(80));
				addSequential(new TurnWithoutPID(0.5, 90));
				addSequential(new DriveStraightWithPID(100));
				addSequential(new TurnWithoutPID(0.5, 90));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithPID(250));
				addSequential(new TurnWithoutPID(0.5, -90));
				addSequential(new DriveStraightWithPID(260));
				addSequential(new TurnWithoutPID(0.5, 90));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.5, 90));
				break;
			}
		} else {
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithPID(250));
				addSequential(new TurnWithoutPID(0.5, 90));
				addSequential(new DriveStraightWithPID(260));
				addSequential(new TurnWithoutPID(0.5, -90));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.5, -90));
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new TurnWithoutPID(0.5, 90));
				addSequential(new DriveStraightWithPID(60));
				addSequential(new TurnWithoutPID(0.5, -90));
				addSequential(new DriveStraightWithPID(100));
				addSequential(new TurnWithoutPID(0.5, -90));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithPID(220));
				addSequential(new TurnWithoutPID(0.5, -90));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			}
		}
	}
}
