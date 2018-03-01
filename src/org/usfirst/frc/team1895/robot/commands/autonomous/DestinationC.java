package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.arm.DeployCube;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToAngle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DestinationC extends CommandGroup {
	
	boolean ourLeftSwitch;

	public DestinationC() {
		
		addSequential(new PrintCommand("C"));
		addSequential(new WaitCommand(SmartDashboard.getNumber("AUTO WAIT TIME", 0)));

		if (Robot.closeSwitchNum == 1) { // our switch is on the left
			ourLeftSwitch = true;
		} else { // our switch is on the right
			ourLeftSwitch = false;
		}

		if (ourLeftSwitch) {
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithPID(205));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.5, 90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(40));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.5, 90.0));
				addSequential(new RotateArmToAngle(45));
				//addSequential(new DriveStraightWithPID(10));
				addSequential(new DeployCube());
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(160));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(75));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(75));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 3:
//				addSequential(new PrintCommand("Position 3"));
//				addSequential(new DriveStraightWithPID(55));
//				addSequential(new TurnWithoutPID(0.3, -90.0));
//				addSequential(new DriveStraightWithPID(298));
//				addSequential(new TurnWithoutPID(0.3, 90.0));
//				addSequential(new DriveStraightWithPID(225));
//				addSequential(new TurnWithoutPID(0.3, 90.0));
//				addSequential(new DriveStraightWithPID(60));
//				addSequential(new TurnWithoutPID(0.3, 90.0));
////				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
////				addSequential(new DeployCube_Claw());
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithPID(265));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(232));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			}
		} else {
			switch (Robot.startPos) {
			case 1:   //goes the far way
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithPID(55));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(298));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(225));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithPID(55));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, 90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(70));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(100));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithPID(190));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(40));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.3, -90.0));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithPID(30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube_Claw());
				break;
			}
		}
	}
}
