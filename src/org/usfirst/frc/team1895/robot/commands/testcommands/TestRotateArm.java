package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestRotateArm extends Command {
	private int angle;
	boolean done;
	
    public TestRotateArm() {
        // Use requires() here to declare subsystem dependencies
    	this.angle = (int)SmartDashboard.getNumber("Test RotateArm Position", 1150);
        requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	done = false;
    	this.angle = (int) SmartDashboard.getNumber("Test RotateArm Position", 1150);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println("we are in the command---------");
    	done = Robot.arm.setPosition(angle);
    	
    	SmartDashboard.putNumber("Arm Encoder", Robot.arm.getArmEncoder());
//    	SmartDashboard.putNumber("Wrist Encoder", Robot.arm.getWristEncoder());
//    	SmartDashboard.putNumber("Accel Value", Robot.arm.getAccelValue());
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
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
