package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Default_Drivetrain extends Command {
	
	boolean isDrivingStraight = false;
	double angle;
	double tolerance = 1.0;

    public Default_Drivetrain() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command run.s the first time
    protected void initialize() {
    	Robot.drivetrain.resetAHRSGyro(); //reset gyro
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//Robot.drivetrain.arcadeDrive(-0.25, 0);
    	Robot.drivetrain.arcadeDrive(-Robot.oi.gamepad.getAxis(F310.LY), Robot.oi.gamepad.getAxis(F310.RX));
    
    }

    // Make this return true when this Command no longer needs to runexecute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
