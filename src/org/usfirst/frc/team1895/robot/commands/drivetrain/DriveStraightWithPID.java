package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightWithPID extends Command {

	double goalDistance;
	boolean done;
	
    public DriveStraightWithPID(double dist) {
    	requires(Robot.drivetrain);
    	done = false;
    	goalDistance = dist;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.setDrivingPIDSetpoints(goalDistance);

    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {

    	done = Robot.drivetrain.driveStraightWithPID(goalDistance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("finished");
    	
    	//System.out.println("LE: " + Robot.drivetrain.getLeftEncoder() + " RE: " + Robot.drivetrain.getRightEncoder());
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}