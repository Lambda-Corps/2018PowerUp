package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveParallel extends Command {
	
	double speed;
	double buffer;
	double goalDistance;
	boolean onLeft;
	boolean done;

    public DriveParallel(double speed, double buffer, double goalDistance, boolean onLeft) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    	this.speed = speed;
    	this.buffer = buffer;
    	this.goalDistance = goalDistance;
    	this.onLeft = onLeft;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.driveParallel(speed, buffer, goalDistance, onLeft);
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
