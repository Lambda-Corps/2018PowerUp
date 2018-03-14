package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetractTelescopingPart extends Command {
	
	int counter;
	boolean done;

    public RetractTelescopingPart() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	    	counter = 0;
	    	done = false;
	    	Robot.arm.retractTelescopingArm();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		// Run this command for a 1/2 second
//	    	if(counter>=30) {
//	    		done = true;
//	    	}
    	done = true;
	    	
	    	counter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
