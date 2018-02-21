package org.usfirst.frc.team1895.robot.commands.climbing;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;

import edu.wpi.first.wpilibj.command.Command;
/**
 *
 */
public class Default_ManuallyClimb extends Command {

    public Default_ManuallyClimb() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
    	requires(Robot.climber);
    	
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//  Read the Operator joy stick right side 
    	double WinchcontrolAxis = Robot.oi.gamepad2.getAxis(F310.LT);
    	//System.out.println (String.format("WinchcontrolAxis: %6.2f ", WinchcontrolAxis));
    	
    	//  Create a dead zone where the winch does not try to move unless greater than 0.2 
    	//if ((WinchcontrolAxis > 0.2) || (WinchcontrolAxis < -0.2)) {
    	if (WinchcontrolAxis > 0.2) { //can only climb up
    		Robot.climber.manualClimbing(WinchcontrolAxis);
    	}
    	else {
    		Robot.climber.manualClimbing(0);

    	}
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
