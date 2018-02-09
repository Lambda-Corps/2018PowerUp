package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestTurnWithoutPID extends Command {

	double t_GoalAngle, t_speed;
	boolean t_done;
    public TestTurnWithoutPID() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        t_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t_GoalAngle = SmartDashboard.getNumber("Test Turn Angle: ", 90.0);
    	t_speed = SmartDashboard.getNumber("Test NP Turn Speed: ", 0.4);
    	Robot.drivetrain.resetAHRSGyro();
    	Robot.drivetrain.resetAHRSGyro(); //not sure which one
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Test Turn Gyro Angle: ", Robot.drivetrain.getAHRSAngle());
    	//t_done = Robot.drivetrain. //we need a turning method
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