package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveStraightSetDistance extends Command {

	boolean finished;
	double speed;
	double distance;
    public DriveStraightSetDistance(double v, double goalDis) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        speed = v;
        distance = goalDis;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    	System.out.println("encoders have been reset");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	finished = Robot.drivetrain.driveStraightSetDistance(speed, distance);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return finished;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("orange");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
