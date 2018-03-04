package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.arm.DeployCube;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToPosition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;
import org.usfirst.frc.team1895.robot.subsystems.Arm;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DestinationB extends CommandGroup {

	boolean ourLeftSwitch;

	public DestinationB() {

		addSequential(new PrintCommand("B"));
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
				addSequential(new DriveStraightWithoutPID(0.7, 150));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(0.7, 90));
				addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
				addSequential(new DriveStraightWithoutPID(0.7, 20));
				// TODO insert the rangefinder to avoid penalty here
				addSequential(new DeployCube());
				//addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,(0));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,55)); // should be 3in clearance of cubes
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,150));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,85));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				addSequential(new DeployCube());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,55)); // should be 3in clearance of cubes
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,244));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,85));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube());
				break;
			}
		} else {
			switch (Robot.startPos) {
			case 1:
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,140));
				/*addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,55)); // should be 3in clearance of cubes
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,244));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,70));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube());*/
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,55));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,70));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,85));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,140));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
//				addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
//				addSequential(new DeployCube());
				break;
			}
		}

	}
}
