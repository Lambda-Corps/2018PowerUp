package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveForTime extends Command {

	double counter;
	int c = 0;
    public TestDriveForTime() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        counter = SmartDashboard.getNumber("Seconds for DriveForTime", 1)*30; //multiply seconds by how many times the code runs in a second
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if(c <= counter) {
    		Robot.drivetrain.arcadeDrive(0, 0.5);
    		c++;
    	}
    }

    // Make this return true when this Command no longer needs to run execute()
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
