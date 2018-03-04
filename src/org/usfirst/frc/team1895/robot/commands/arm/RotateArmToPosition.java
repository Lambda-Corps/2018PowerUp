package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * ROTATES ARM TO A CERTAIN POSITION WITH POTENTIOMETER. USED FOR THE BUTTONS
 * THAT BRING THE ARM TO VARIOUS SETPOINTS
 */
public class RotateArmToPosition extends Command {

	boolean done;
	double armPosition;

	public RotateArmToPosition(double armPosition) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.arm);
		this.armPosition = armPosition;
//		SmartDashboard.putString("status", "ocns");

	}

	// Called just before this Command runs the first time
	protected void initialize() {
		SmartDashboard.putString("status", "init");
		done = false;
		this.armPosition = armPosition;
//		System.out.println("init");

	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		done = Robot.arm.setPosition(armPosition);
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return done;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.arm.driveArm(0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
