package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationE extends CommandGroup {

    public DestinationE() {
        
    	addSequential(new PrintCommand("E"));
    	addSequential(new DriveStraightWithPID(10));
/*    	addSequential(new DriveStraightWithoutPID(0.5, 50));
    	addSequential(new WaitCommand(1.5));
    	addSequential(new DriveStraightWithoutPID(0.5, -50));
    	addSequential(new WaitCommand(1.5));
    	addSequential(new TurnWithoutPID(0.3, -90));
    	*/
    }
}
