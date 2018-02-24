package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TurnWithoutPID extends Command {
	private double goalAngle = 0.0;
	private boolean isDone = false;
	private double speed;
	private double tolerance = 3;
	private double currentAngle;
	
    public TurnWithoutPID(double speed, double givenAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	goalAngle = givenAngle;
    	this.speed = speed;
    	isDone = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetAHRSGyro();
    	isDone = false;
 

    	//Robot.drivetrain.setAHRSAdjustment(80.0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	currentAngle = Robot.drivetrain.getAHRSGyroAngle();
    	System.out.println("angle: " + currentAngle);
    	SmartDashboard.putNumber("Gyro: ", currentAngle);
    	if(Math.abs(goalAngle - currentAngle) < tolerance) {  //if within tolerance
    		System.out.println("Done!");
    		Robot.drivetrain.arcadeDrive(0, 0);
    		isDone = true;
    	} else if(currentAngle < goalAngle) {  //If left of target angle
    		Robot.drivetrain.arcadeDrive(0, speed);  //turn clockwise
    	} else if(currentAngle > goalAngle){  //If right of target angle
    		Robot.drivetrain.arcadeDrive(0, -speed);  //turn counterclockwise
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.setAHRSAdjustment(0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.drivetrain.arcadeDrive(0, 0);
    	isDone = true;
    }
}
