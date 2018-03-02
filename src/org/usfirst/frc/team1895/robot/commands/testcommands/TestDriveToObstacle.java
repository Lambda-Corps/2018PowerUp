package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveToObstacle extends Command {

	double t_speed, t_goalDistance, t_scalar;
	boolean t_done;

	public TestDriveToObstacle() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drivetrain);
		t_done = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		t_speed = SmartDashboard.getNumber("Test Drive Speed:", .4);
		t_goalDistance = SmartDashboard.getNumber("Test Drive Distance:", 13);
		// t_scalar = SmartDashboard.getNumber("Test Drive Tank Scalar:", .94);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		t_done = Robot.drivetrain.drivefr_RFDistance(t_goalDistance, t_speed); // method needed
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return t_done;
	}

	// Called once after isFinished returns true
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}