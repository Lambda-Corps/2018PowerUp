package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveStraightWithoutPID extends Command {

	double goal;
	double speed;
	boolean goalReached;

	public DriveStraightWithoutPID(double s, double g) {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.drivetrain);
		goal = g;
		speed = s;
		goalReached = false;
	}

	// Called just before this Command runs the first time
	protected void initialize() {
		Robot.drivetrain.resetAHRSGyro(); // reset gyro
		Robot.drivetrain.resetEncoders();
	}

	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
		goalReached = Robot.drivetrain.driveStraightSetDistance(speed, goal);
		// System.out.println("left: " + Robot.drivetrain.getEncoderValue(0) + ", " +
		// Robot.drivetrain.getEncoderValue(1));
		SmartDashboard.putNumber("PID Drive L Encoder Value: ",
				Robot.drivetrain.getEncoderValue(Drivetrain.LEFT_MOTOR_ENCODER));
		SmartDashboard.putNumber("PID Drive R Encoder Value: ",
				Robot.drivetrain.getEncoderValue(Drivetrain.RIGHT_MOTOR_ENCODER));

	}

	// Make this return true when this Command no longer needs to run execute()
	protected boolean isFinished() {
		return goalReached;
	}

	// Called once after isFinished returns true
	protected void end() {

	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	protected void interrupted() {
	}
}
