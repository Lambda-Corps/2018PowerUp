package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;
import edu.wpi.first.wpilibj.command.Command;

/**
 * ROTATES ARM TO A CERTAIN POSITION WITH POTENTIOMETER. USED FOR THE BUTTONS THAT BRING THE ARM 
 * TO VARIOUS SETPOINTS
 */
public class RotateArmToPosition extends Command {

	boolean done;
	int armPosition;
    public RotateArmToPosition(int armPosition) {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.arm);
        this.armPosition = armPosition;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    		done = false;
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
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
