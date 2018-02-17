package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.claw.DeployCube_Claw;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationB extends CommandGroup {

	public DestinationB() {

		// our color is on left
		// from position 1
		addSequential(new DriveStraightWithoutPID(0.5, 120));
		addSequential(new TurnWithoutPID(0.5, 90));
		addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
		addSequential(new DeployCube_Claw());
		// from position 2
		addSequential(new DriveStraightWithoutPID(0.5, 55)); // should be 3in clearance of cubes
		addSequential(new TurnWithoutPID(0.5, -90));
		addSequential(new DriveStraightWithoutPID(0.5, 191));
		addSequential(new TurnWithoutPID(0.5, 90));
		addSequential(new DriveStraightWithoutPID(0.5, 113));
		addSequential(new TurnWithoutPID(0.5, 90));
		addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
		addSequential(new DeployCube_Claw());
		// from position 3
		addSequential(new DriveStraightWithoutPID(0.5, 55)); // should be 3in clearance of cubes
		addSequential(new TurnWithoutPID(0.5, -90));
		addSequential(new DriveStraightWithoutPID(0.5, 244));
		addSequential(new TurnWithoutPID(0.5, 90));
		addSequential(new DriveStraightWithoutPID(0.5, 113));
		addSequential(new TurnWithoutPID(0.5, 90));
		addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
		addSequential(new DeployCube_Claw());

		// our color is on right
		// from position 1
		addSequential(new DriveStraightWithoutPID(0.5, 55)); // should be 3in clearance of cubes
		addSequential(new TurnWithoutPID(0.5, 90));
		addSequential(new DriveStraightWithoutPID(0.5, 244));
		addSequential(new TurnWithoutPID(0.5, -90));
		addSequential(new DriveStraightWithoutPID(0.5, 113));
		addSequential(new TurnWithoutPID(0.5, -90));
		addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
		addSequential(new DeployCube_Claw());
		// from position 2
		addSequential(new DriveStraightWithoutPID(0.5, 55));
		addSequential(new TurnWithoutPID(0.5, 90));
		addSequential(new DriveStraightWithoutPID(0.5, 40));
		addSequential(new TurnWithoutPID(0.5, -90));
		addSequential(new DriveStraightWithoutPID(0.5, 70));
		addSequential(new TurnWithoutPID(0.5, -90));
		addSequential(new DriveToObstacle(0.5, 5));  // accurate within this distance?
		addSequential(new DeployCube_Claw());
		// from position 3
		addSequential(new DriveStraightWithoutPID(0.5, 120));
		addSequential(new TurnWithoutPID(0.5, -90));
		addSequential(new DriveToObstacle(0.5, 5)); // accurate within this distance?
		addSequential(new DeployCube_Claw());
		
		//C---------------------------------------
		
		//our color is on left
		//p1
		addSequential(new DriveStraightWithoutPID(0.3, 209));
		
		//p2
		
		//p3
		
		
		
		//our color is on right

	}
}
