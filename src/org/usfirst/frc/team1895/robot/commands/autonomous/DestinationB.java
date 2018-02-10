package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.claw.DeployCube_Claw;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationB extends CommandGroup {

    public DestinationB() {
        
    	addSequential(new PrintCommand("B"));
    	/*addSequential(new DriveStraightWithoutPID(0.75, 5));
    	addSequential(new PrintCommand("drive 1 done"));
		addSequential(new TurnWithoutPID(0.4, 90));
		addSequential(new PrintCommand("turn 1 done"));
		addSequential(new DriveStraightWithoutPID(0.75, 5));
		addSequential(new PrintCommand("drive 2 done"));
		addSequential(new TurnWithoutPID(0.4, 90));
		addSequential(new PrintCommand("turn 2 done"));
		addSequential(new DriveStraightWithoutPID(0.75, 5));
		addSequential(new PrintCommand("drive 3 done"));
		addSequential(new TurnWithoutPID(0.4, 90));
		addSequential(new PrintCommand("turn 3 done"));
		addSequential(new DriveStraightWithoutPID(0.75, 5));
		addSequential(new PrintCommand("drive 4 done"));
		addSequential(new TurnWithoutPID(0.4, 90));
		addSequential(new PrintCommand("turn 4 done"));*/
		
    }
}
