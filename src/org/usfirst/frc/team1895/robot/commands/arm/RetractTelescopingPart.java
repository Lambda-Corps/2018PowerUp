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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(counter>=200) {
    		done = true;
    	}
    	Robot.arm.retractTelescopingArm();
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
