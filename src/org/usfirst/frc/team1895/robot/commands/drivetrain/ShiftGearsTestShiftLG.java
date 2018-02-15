package org.usfirst.frc.team1895.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftGearsTestShiftLG extends Command {

	boolean done = false;
    public ShiftGearsTestShiftLG() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.drivetrain.shiftToLG();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("Low gear has been reached");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
