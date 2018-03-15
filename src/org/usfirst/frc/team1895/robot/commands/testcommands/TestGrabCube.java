package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestGrabCube extends Command {

	double speed;
	double speed2;
	double claw_speed;
	double t_counter;
	boolean t_done;

	public boolean t_hasCube;

	public TestGrabCube() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.lowerIntake);
		requires(Robot.arm);
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		t_counter = 0;
		t_hasCube = false;
		speed = SmartDashboard.getNumber("Lower Intake grab cube speed:", 0.2);
		speed2 = SmartDashboard.getNumber("Speed2", 0.2);
		claw_speed = SmartDashboard.getNumber("Claw Intake grab cube speed", 0.6);
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		Robot.lowerIntake.test_grabCube(speed, speed2);
		Robot.arm.test_grabCube(claw_speed);

		if (Robot.arm.cubeIsClose()) {
			Robot.lowerIntake.retractLowerIntake();
			// hasCube = true;
		}

		t_hasCube = Robot.arm.cubeIsIn();
		if (t_hasCube) {
			// Stop the motors if we have a cube
			Robot.lowerIntake.setLowerIntakeMotors(0);
			Robot.arm.stopClaw();
		}
		t_counter++;
	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return t_hasCube;
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
