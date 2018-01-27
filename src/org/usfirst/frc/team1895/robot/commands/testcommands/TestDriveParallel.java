package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveParallel extends Command {

	double t_speed;
	double t_buffer;
	double t_goalDistance;
	boolean t_onLeft;
	boolean t_done;
	public TestDriveParallel() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t_speed = SmartDashboard.getNumber("Test Drive TankDrive Speed ", .4);
    	t_buffer = SmartDashboard.getNumber("Test Drive Buffer:", 10);
    	t_goalDistance = SmartDashboard.getNumber("Test Drive Distance ", 20.0);
    	t_onLeft = SmartDashboard.getBoolean("Test boolean onLeft Value", false);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	t_done = Robot.drivetrain.driveParallel(t_speed, t_buffer, t_goalDistance, t_onLeft);
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
