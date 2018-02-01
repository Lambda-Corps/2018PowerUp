package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
/**
 *
 */
public class ConditionalCommand1 extends ConditionalCommand {
	int var;
	
    public ConditionalCommand1(CommandGroup onTrue, CommandGroup onFalse){
    	super(onTrue, onFalse);
    	var = 0;
    }
    
    protected boolean condition() {
    	
    	if (Robot.startPos==2){
    		return true;
    	}
    	else {
    		return false;
    	}
    }
}