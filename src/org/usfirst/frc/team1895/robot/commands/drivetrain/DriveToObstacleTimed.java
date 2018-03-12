package org.usfirst.frc.team1895.robot.commands.drivetrain;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.TimedCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class DriveToObstacleTimed extends TimedCommand {

	double goalDistance;
	boolean done;
	double speed;
	
    public DriveToObstacleTimed(double speed, double distancetoObstacle, double time) {
    	super("DriveToObstacleTimed", time);
        // Use requires() here to declare subsystem dependencies
        requires(Robot.drivetrain);
        //goalDistance = distancetoObstacle;
//        this.speed = speed;
        done = false;
        this.speed = speed;
        goalDistance = distancetoObstacle;
    }
    

    // Called just before this Command runs the first time
    protected void initialize() {
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//System.out.println(Robot.drivetrain.fr_rangefinderDist());
		SmartDashboard.putNumber("front rf", Robot.drivetrain.fr_rangefinderDist());
    	done = Robot.drivetrain.drivefr_RFDistance(goalDistance, speed);
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
