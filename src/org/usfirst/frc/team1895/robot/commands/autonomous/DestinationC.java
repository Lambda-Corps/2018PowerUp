package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationC extends CommandGroup {

    public DestinationC() {
		addSequential(new PrintCommand("C"));
    	addSequential(new PrintCommand("StartPos" + Robot.startPos));
        if(Robot.startPos == 1) {
        	addSequential(new PrintCommand("position 1, destination c"));
        }
        else if (Robot.startPos == 2) {
        	addSequential(new PrintCommand("position 2, destination c"));
        }
        else if(Robot.startPos == 3) {
        	addSequential(new PrintCommand("position 3, destination c"));
        }
        else {
        	addSequential(new PrintCommand("else"));
        }
    }
}
