package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ExtendTelescopingPart extends Command {
	
	int counter; // Counter that allows the pneumatic time to complete before returning
	boolean done;
    public ExtendTelescopingPart() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        
        counter = 0;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		counter = 0;
    		done = false;
    		Robot.arm.extendTelescopingArm();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    		// Run for 1/3 of a second
//    		if (counter++ == 5) {
//    			done = true;
//    		}
    	done = true;
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
