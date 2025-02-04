package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class ShiftGearsShiftHighGear extends Command {

	boolean inHG = false;
    public ShiftGearsShiftHighGear() {
        requires(Robot.drivetrain);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	Robot.drivetrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	inHG = Robot.drivetrain.driveUntilHighGear();
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return inHG;
    }

    // Called once after isFinished returns true
    protected void end() {
//    	System.out.println("Has been in high gear for a few seconds. High gear command end");
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
