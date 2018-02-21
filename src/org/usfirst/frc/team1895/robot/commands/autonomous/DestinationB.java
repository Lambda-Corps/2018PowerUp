package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArm;
import org.usfirst.frc.team1895.robot.commands.claw.DeployCube_Claw;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationB extends CommandGroup {

	boolean ourLeftSwitch;

	public DestinationB() {

		addSequential(new PrintCommand("B"));

		if (Robot.closeSwitchNum == 1) { // our switch is on the left
			ourLeftSwitch = true;
		} else { // our switch is on the right
			ourLeftSwitch = false;
		}

		if (ourLeftSwitch) {
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithoutPID(0.7, 150));
				addSequential(new TurnWithoutPID(0.5, 90));
				addSequential(new RotateArm(45));
				addSequential(new DeployCube_Claw());
				//addSequential(new DriveStraightWithPID(0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithPID(55)); // should be 3in clearance of cubes
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(150));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(85));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithPID(55)); // should be 3in clearance of cubes
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(244));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(85));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			}
		} else {
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithPID(55)); // should be 3in clearance of cubes
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(244));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(70));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithPID(55));
				addSequential(new TurnWithoutPID(0.3, 90));
				addSequential(new DriveStraightWithPID(70));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(85));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithPID(140));
				addSequential(new TurnWithoutPID(0.3, -90));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			}
		}

	}
}
