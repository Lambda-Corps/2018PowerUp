package org.usfirst.frc.team1895.robot.commands.autonomous;

import java.util.ArrayList;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */
public class AutoCommandBuilder extends CommandGroup {

    public AutoCommandBuilder(ArrayList<CommandHolder> commandList) {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.

        // A command group will require all of the subsystems that each member
        // would require.
        // e.g. if Command1 requires chassis, and Command2 requires arm,
        // a CommandGroup containing them would require both the chassis and the
        // arm.
    	

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
