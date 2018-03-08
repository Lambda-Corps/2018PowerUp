package org.usfirst.frc.team1895.robot.commands.arm;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class DeployCubeAndRetract extends CommandGroup {

    public DeployCubeAndRetract() {
        
    	addSequential(new DeployCube());
    	addSequential(new RetractTelescopingPart());
    	
    }
}
