package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestTurnWithoutPID extends Command {
	private double goalAngle = 0.0;
	private boolean isDone = false;
	private double speed;
	private double tolerance = 3;
	private double currentAngle;

	boolean t_done;
    public TestTurnWithoutPID() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        t_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	goalAngle = SmartDashboard.getNumber("Test Turn Angle: ", 90.0);
    	speed = SmartDashboard.getNumber("Test NP Turn Speed: ", 0.4);
    	Robot.drivetrain.resetAHRSGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("Test Turn Gyro Angle: ", Robot.drivetrain.getAHRSGyroAngle());
    	currentAngle = Robot.drivetrain.getAHRSGyroAngle();
    	System.out.println("angle: " + currentAngle);
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}