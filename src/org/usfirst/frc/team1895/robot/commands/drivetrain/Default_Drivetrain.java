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
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("LX: " + Robot.oi.gamepad.getAxis(F310.LX));
    	System.out.println("LY: " + Robot.oi.gamepad.getAxis(F310.LY));
    	System.out.println("RX: " + Robot.oi.gamepad.getAxis(F310.RX));
    	System.out.println("RY: " + Robot.oi.gamepad.getAxis(F310.RY));
    	
    	if(Robot.oi.gamepad.getAxis(F310.LX)<0.005 && Robot.oi.gamepad.getAxis(F310.LX)>-0.005) {  //if no x input --> correcting mode
    		if(!isDrivingStraight) {  //if correcting mode is not already in progress
    			System.out.println("setting up correcting mode");
    			isDrivingStraight = true;  //set flag
    			Robot.drivetrain.resetGyro(); //reset gyro (METHOD UNFINISHED)
    		}
    		if(Robot.oi.gamepad.getAxis(F310.RY)<-0.005) {  //if backward
    			angle = Robot.drivetrain.getGyro();
    			System.out.printf("BW Drive, angle = " + angle + "\n");
    			if(angle<-tolerance) {  //if drifting left
    				System.out.println("drifting left, correcting");
    				Robot.drivetrain.tankDrive(Robot.oi.gamepad.getAxis(F310.RY), 0.75*Robot.oi.gamepad.getAxis(F310.RY));  //go right  -- TODO: check if correction will work w/arcadedrive this way
    			} else if(angle>tolerance) {  //if drifting right
    				System.out.println("drifting right, correcting");
    				Robot.drivetrain.tankDrive(0.75*Robot.oi.gamepad.getAxis(F310.RY), Robot.oi.gamepad.getAxis(F310.RY));  //go left  -- TODO: check if correction will work w/arcadedrive this way
    			} else {
    				Robot.drivetrain.tankDrive(Robot.oi.gamepad.getAxis(F310.RY), Robot.oi.gamepad.getAxis(F310.RY));  //go straight
    			}
    		} else if(Robot.oi.gamepad.getAxis(F310.RY)>0.005){  //if forward
    			angle = Robot.drivetrain.getGyro();
    			System.out.printf("FW Drive, angle = " + angle + "\n");
    			if(angle<-tolerance) {  //if drifting left
    				System.out.println("drifting left, correcting");
    				Robot.drivetrain.tankDrive(0.75*Robot.oi.gamepad.getAxis(F310.RY), Robot.oi.gamepad.getAxis(F310.RY));  //go right  -- TODO: check if correction will work w/arcadedrive this way
    			} else if(angle>tolerance) {  //if drifting right
    				System.out.println("drifting right, correcting");
    				Robot.drivetrain.tankDrive(Robot.oi.gamepad.getAxis(F310.RY), 0.75*Robot.oi.gamepad.getAxis(F310.RY));  //go left  -- TODO: check if correction will work w/arcadedrive this way
    			} else {
    				System.out.println("going straight ahead");
    				Robot.drivetrain.tankDrive(Robot.oi.gamepad.getAxis(F310.RY), Robot.oi.gamepad.getAxis(F310.RY));  //go straight
    			}
    		} else {
    			Robot.drivetrain.arcadeDrive(0, 0);  //if no y input, stop
    		}
    	} else {  //has x input --> normal mode
    		if(isDrivingStraight) {  //if correcting mode still in progress
    			isDrivingStraight = false;  //unset flag
    		}
    		Robot.drivetrain.arcadeDrive(Robot.oi.gamepad.getAxis(F310.RY), Robot.oi.gamepad.getAxis(F310.LX));  //drive normally
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
