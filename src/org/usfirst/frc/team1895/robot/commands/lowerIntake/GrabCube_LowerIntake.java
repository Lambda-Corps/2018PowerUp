package org.usfirst.frc.team1895.robot.commands.lowerIntake;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class GrabCube_LowerIntake extends Command {
	
	double counter;
	boolean done;

	public boolean hasCube;

	public GrabCube_LowerIntake() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.lowerIntake);
		requires(Robot.arm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		counter = 0;
		hasCube = false;
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		if(Robot.arm.cubeIsClose()) {
			Robot.lowerIntake.retractLowerIntake();
			//hasCube = true;
		}
		hasCube = Robot.arm.cubeIsIn();
		Robot.lowerIntake.grabCube();
		Robot.arm.grabCube();
		counter++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return hasCube;
	}

	// Called once after isFinished returns true
	protected void end() {
		Robot.lowerIntake.setLowerIntakeMotors(0);
		Robot.arm.stopClaw();
		Robot.lowerIntake.extendLowerIntake();
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
		Robot.lowerIntake.setLowerIntakeMotors(0);
		Robot.arm.stopClaw();
		Robot.lowerIntake.extendLowerIntake();
	}
}
