package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ToggleTelescope extends Command {

	int count = 0;
	boolean done = false;
    public ToggleTelescope() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.arm.toggleTelescopingArm();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(count++ == 30) {
    		done = true;
    	}
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
