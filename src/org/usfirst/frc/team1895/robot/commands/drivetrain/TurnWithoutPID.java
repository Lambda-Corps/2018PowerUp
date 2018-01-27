package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnWithoutPID extends Command {
	private double goalAngle = 0.0;
	private boolean isDone = false;
	private double factor = 1.0;
	private double error = 1.0;
	
    public TurnWithoutPID(double givenAngle) {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.drivetrain);
    	goalAngle = givenAngle;
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetGyro();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(goalAngle<0) {//If the angle is negative
    		Robot.drivetrain.arcadeDrive(0, -1*factor);//set the yaw to negative to spin left
    	}
    	else if(goalAngle>0){//If the angle is positive
    		Robot.drivetrain.arcadeDrive(0, 1*factor);//set the yaw to positive to spin right
    	}
    	else{//If the angle is zero
    		Robot.drivetrain.arcadeDrive(0, 0);
    	}
    	
    	//Stopping the command
    	if(Math.abs(goalAngle-Robot.drivetrain.getAngle()) < error) {
    		Robot.drivetrain.arcadeDrive(0, 0);
    		isDone = true;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return isDone;
    }

    // Called once after isFinished returns true
    protected void end() {
    	goalAngle = 0.0;
    	Robot.drivetrain.resetGyro();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
