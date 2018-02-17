package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestTurnWithPID extends Command {

	double t_GoalAngle, t_p, t_i, t_d;
	boolean t_done;
	
    public TestTurnWithPID() {
    	requires(Robot.drivetrain);
    	t_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t_p = SmartDashboard.getNumber("Turn: P value: ", .025);
    	t_i = SmartDashboard.getNumber("Turn: I value: ", 0.0);
    	t_d = SmartDashboard.getNumber("Turn: D value: ", -.005);
    	t_GoalAngle = SmartDashboard.getNumber("Test Turn Angle: ", 90.0);
    	
    	Robot.drivetrain.setTurningPIDSetpoints(t_GoalAngle);
    	Robot.drivetrain.makeNewPidTurning(t_p, t_i, t_d);  //get p,i,d from smartdashboard
    	Robot.drivetrain.setTurningPIDSetpoints(t_GoalAngle);
    	System.out.println("P = " + t_p + " I = " + t_i + " D = " + t_d + " dist = " + t_GoalAngle);
   	
    	
    	// Reset the Gyro state and enable the PID controller
    	Robot.drivetrain.resetAHRSGyro();
    	//Robot.drivetrain.setUpPIDTurning(t_GoalAngle);
    }

    protected void execute() {
    	SmartDashboard.putNumber("Test Turn Gyro Angle: ", Robot.drivetrain.getAHRSGyroAngle());
    	
    	t_done = Robot.drivetrain.turnWithPID(t_GoalAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return t_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("finished");
    	t_GoalAngle = 0.0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}