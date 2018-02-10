package org.usfirst.frc.team1895.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationE extends CommandGroup {

    public DestinationE() {
        
    	addSequential(new PrintCommand("E"));
    	
    }
}
