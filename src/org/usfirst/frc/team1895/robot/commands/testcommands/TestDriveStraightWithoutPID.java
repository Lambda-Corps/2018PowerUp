package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveStraightWithoutPID extends Command {

	double t_goalDis;
	double t_speed;
	boolean t_goalReached;
    public TestDriveStraightWithoutPID() {
        requires(Robot.drivetrain);
        t_goalReached = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t_goalDis = SmartDashboard.getNumber("Test Drive Distance:", 30.0);
    	t_speed = SmartDashboard.getNumber("Test Drive Speed:", .4);
    	Robot.drivetrain.resetEncoders();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	t_goalReached = Robot.drivetrain.driveStraightSetDistance(t_speed, t_goalDis);
    	System.out.println("speed " + t_speed + " distance " + t_goalDis); 
    	
    	SmartDashboard.putNumber("PID Drive L Encoder Value: ", Robot.drivetrain.getEncoderValue(Robot.drivetrain.LEFT_MOTOR_ENCODER));
    	SmartDashboard.putNumber("PID Drive R Encoder Value: ", Robot.drivetrain.getEncoderValue(Robot.drivetrain.RIGHT_MOTOR_ENCODER));

    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return t_goalReached;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}