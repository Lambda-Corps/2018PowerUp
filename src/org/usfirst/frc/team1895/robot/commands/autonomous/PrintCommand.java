package org.usfirst.frc.team1895.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PrintCommand extends Command {
	
	String msg;

    public PrintCommand(String msg) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	this.msg = msg;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println(msg);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return true;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
