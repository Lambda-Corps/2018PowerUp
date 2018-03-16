package org.usfirst.frc.team1895.robot.commands.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class AutoCommandBuilder extends CommandGroup {

    public AutoCommandBuilder(ArrayList<CommandHolder> commandList) {
    	
//    		for( int i = commandList.size() -1; i >= 0; i--) {
//    			CommandHolder commandHolder = commandList.get(i);
//    			if(commandHolder.isSequentialCommand()) {
//	    			addSequential(commandHolder.getCommand());
//	    		}
//	    		else {
//	    			addParallel(commandHolder.getCommand());
//	    		}
//    		}
    	
//    			for( int i = 0; i < commandList.size(); i++) {
//				CommandHolder commandHolder = commandList.get(i);
//				if(commandHolder.isSequentialCommand()) {
//		    			addSequential(commandHolder.getCommand());
//		    		}
//		    		else {
//		    			addParallel(commandHolder.getCommand());
//		    		}
//    			}
    	
			for( int i = 0; i < commandList.size(); i++) {
		    			addSequential(commandList.get(i).getCommand());
			}

    }
    
}
