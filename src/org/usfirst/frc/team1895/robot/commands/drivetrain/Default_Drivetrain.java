package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.oi.F310;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class Default_Drivetrain extends Command {
	
	boolean isDrivingStraight = false;
	double angle;
	double tolerance = 1.0;

    public Default_Drivetrain() {
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
    }

    // Called just before this Command run.s the first time
    protected void initialize() {
    	Robot.drivetrain.resetAHRSGyro(); //reset gyro
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
	    	double xAxisValue = Robot.oi.gamepad1.getAxis(F310.RX);
	    	double yAxisValue = -Robot.oi.gamepad1.getAxis(F310.LY);
	    	int povValue = Robot.oi.gamepad1.getPOV();
	
//	    	Robot.drivetrain.arcadeDrive(Robot.oi.gamepad1.getAxis(F310.LY), Robot.oi.gamepad1.getAxis(F310.RX));
	
	    	if ((xAxisValue > 0.01 || xAxisValue < -0.01) || (yAxisValue > 0.01 || yAxisValue < -0.01)) {            //Remove deadband if needed
	        	Robot.drivetrain.arcadeDrive(yAxisValue, xAxisValue);
	    	} else {
	    	       	    
	    	    switch (povValue) {
	    	    case 90:
	        	    Command turnCmd2 = new TurnWithoutPID(.5, 90);
	        	    turnCmd2.start();
	        	    break;
	    	    case 180:
	        	    Command turnCmd3 = new TurnWithoutPID(.5, 180);
	        	    turnCmd3.start();
	        	    break;
	    	    case 270:
	        	    Command turnCmd4 = new TurnWithoutPID(.5, -90);
	        	    turnCmd4.start();
	        	    break;
	        	default:
	        		break;
	    	    }
	    	    Robot.drivetrain.arcadeDrive(0, 0);
	    	}

    }

    // Make this return true when this Command no longer needs to runexecute()
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
