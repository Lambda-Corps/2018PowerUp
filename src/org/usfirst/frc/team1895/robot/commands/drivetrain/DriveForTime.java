package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveForTime extends Command {

	double m_counter;
	boolean done;
	
    public DriveForTime(double seconds) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        m_counter = seconds* 50; //multiply seconds by how many times the code runs in a second
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		done = false;
    		Robot.drivetrain.arcadeDrive(Drivetrain.AUTO_DRIVE_SPEED, 0);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	SmartDashboard.putNumber("counter --------------", m_counter);
	    	if(m_counter-- <= 0) {
	    		Robot.drivetrain.arcadeDrive(0, 0);
	    		done = true;
	    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    		Robot.drivetrain.arcadeDrive(0, 0);
    }
}
