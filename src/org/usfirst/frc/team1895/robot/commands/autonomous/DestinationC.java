package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationC extends CommandGroup {
	
	boolean ourLeftSwitch;

	public DestinationC() {
		addSequential(new PrintCommand("C"));

		if (Robot.closeSwitchNum == 1) { // our switch is on the left
			ourLeftSwitch = true;
		} else { // our switch is on the right
			ourLeftSwitch = false;
		}

		if (ourLeftSwitch) {
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithoutPID(0.7, 190));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 40));
				addSequential(new TurnWithoutPID(0.3, 90.0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithoutPID(0.7, 55));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 160));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 75));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 75));
				addSequential(new TurnWithoutPID(0.3, 90.0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 3:
//				addSequential(new PrintCommand("Position 3"));
//				addSequential(new DriveStraightWithoutPID(0.7, 55));
//				addSequential(new TurnWithoutPID(0.3, -90.0));
//				addSequential(new DriveStraightWithoutPID(0.7, 298));
//				addSequential(new TurnWithoutPID(0.3, 90.0));
//				addSequential(new DriveStraightWithoutPID(0.7, 225));
//				addSequential(new TurnWithoutPID(0.3, 90.0));
//				addSequential(new DriveStraightWithoutPID(0.7, 60));
//				addSequential(new TurnWithoutPID(0.3, 90.0));
////				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
////				addSequential(new DeployCube_Claw());
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithPID(265));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new DriveStraightWithPID(232));
				addSequential(new TurnWithoutPID(0.3, -90.0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			}
		} else {
			switch (Robot.startPos) {
			case 1:   //goes the far way
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithoutPID(0.7, 55));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 298));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 225));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 50));
				addSequential(new TurnWithoutPID(0.3, -90.0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithoutPID(0.7, 55));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 70));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 100));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 50));
				addSequential(new TurnWithoutPID(0.3, -90.0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithoutPID(0.7, 190));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new DriveStraightWithoutPID(0.7, 40));
				addSequential(new TurnWithoutPID(0.3, -90.0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			}
		}
	}
}
