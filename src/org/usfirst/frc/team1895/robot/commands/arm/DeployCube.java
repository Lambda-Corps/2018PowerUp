package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DeployCube extends Command {
	
	public boolean hasCube;
	public int counter;
	boolean done;

    public DeployCube() {
    	requires(Robot.arm);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	counter = 0;
    	done = false;
    	SmartDashboard.putString("status", "INIT----------------");
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if(counter>=100) {
			done = true;
		}
    	hasCube = Robot.arm.cubeIsIn();
    	Robot.arm.deployCube_Claw();
    	counter++;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.arm.stopClaw();
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
