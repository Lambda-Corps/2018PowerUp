package org.usfirst.frc.team1895.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DestinationD extends CommandGroup {

    public DestinationD() {
 
    	addSequential(new PrintCommand("D"));
    	
    }
}
