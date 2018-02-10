package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationE extends CommandGroup {

    public DestinationE() {
        
    	addSequential(new PrintCommand("E"));
    	
    	//testing purposes - don't use in final code
    	
    	//addSequential(new DriveToObstacle(0.3, 40));
    	addSequential(new DriveStraightWithoutPID(1.0, 500.0));
    	//addSequential(new TurnWithoutPID(0.3, 90));
    	//addSequential(new DriveStraightWithPID(5.0));
    	
    }
}
