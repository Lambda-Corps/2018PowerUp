package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;

import edu.wpi.first.wpilibj.command.Command;

/**
 * USES ENCODERS 
 */
public class RotateArmToAngle extends Command {
	private double angle;
	
    public RotateArmToAngle(double angle) {
        // Use requires() here to declare subsystem dependencies
    	this.angle = angle;
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	Robot.arm.setPosition(angle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (Robot.arm.setPosition(angle)||Robot.arm.getArmEncoder()>=14000);
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.stopClawIntake();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	Robot.arm.stopClawIntake();
    }
}
