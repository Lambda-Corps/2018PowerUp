package org.usfirst.frc.team1895.robot.commands.autonomous;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OurWaitCommand extends Command {

	double counter;
	double cycles;
	boolean done;

	public OurWaitCommand(double seconds) {
		counter = 0;
		cycles = seconds * 66.667;
		done = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if (counter >= cycles) { // wait seconds
			done = true;
		}
		counter++;
//		System.out.println(counter + "/" + cycles);
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
	}
}
