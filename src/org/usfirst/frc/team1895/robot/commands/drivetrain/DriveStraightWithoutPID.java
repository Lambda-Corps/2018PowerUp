package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightWithoutPID extends Command {
	
	double goal;
	double speed;
	boolean goalReached;

    public DriveStraightWithoutPID(double s, double g) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    	goal = g;
    	speed = s;
    	goalReached = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	goalReached = Robot.drivetrain.driveStraightSetDistance(speed, goal);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return goalReached;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
