package org.usfirst.frc.team1895.robot.commands.lowerIntake;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class RetractLowerIntake extends Command {
	
	int counter;
	boolean done;

    public RetractLowerIntake() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.lowerIntake);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	counter = 0;
    	done = false;
    	Robot.lowerIntake.retractLowerIntake();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(counter>=7) {
    		done = true;
    	}
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
