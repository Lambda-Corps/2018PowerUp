package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithPID extends Command {
	double goalAngle = 0.0;
	boolean done = false;
    public TurnWithPID(double givenAngle) {
    	requires(Robot.drivetrain);
        goalAngle = givenAngle;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetAHRSGyro();
    	Robot.drivetrain.setUpPIDTurning(goalAngle);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = 	Robot.drivetrain.turnWithPID(goalAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	goalAngle = 0.0;
    	done = false;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
