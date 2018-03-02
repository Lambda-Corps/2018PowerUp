package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * USES ENCODERS 
 */
public class RotateArmToAngle extends Command {
	private int angle;
	
	boolean done;
	
    public RotateArmToAngle(int angle) {
        // Use requires() here to declare subsystem dependencies
    	this.angle = angle;
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	done = false;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	done = Robot.arm.setPosition(angle);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return (done || Robot.arm.getArmEncoder()>=14000 || Robot.arm.getArmEncoder()<=0);
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
