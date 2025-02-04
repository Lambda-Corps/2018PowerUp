package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DestinationE extends CommandGroup {

	boolean ourLeftSwitch;

	public DestinationE() {
		
		addSequential(new PrintCommand("E"));
		addSequential(new WaitCommand(SmartDashboard.getNumber("AUTO WAIT TIME", 0)));

		if (Robot.ourSwitchSide == 1) { // our switch is on the left
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
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,250));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,20));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,80));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,200));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,200));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,260));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				break;
			}
		} else {
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("Position 1"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,250));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,260));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				break;
			case 2:
				addSequential(new PrintCommand("Position 2"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,60));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,200));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			case 3:
				addSequential(new PrintCommand("Position 3"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,220));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				// addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
				// addSequential(new DeployCube_Claw());
				break;
			}
		}
	}
}
