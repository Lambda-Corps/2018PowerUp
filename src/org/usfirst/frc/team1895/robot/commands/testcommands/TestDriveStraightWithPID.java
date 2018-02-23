package org.usfirst.frc.team1895.robot.commands.testcommands;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class TestDriveStraightWithPID extends Command {

	double t_p, t_i, t_d, t_Distance;
	boolean t_done;
	
    public TestDriveStraightWithPID() {
    	requires(Robot.drivetrain);
    	t_done = false;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	t_p = SmartDashboard.getNumber("Distance: P value: ", .1);
    	t_i = SmartDashboard.getNumber("Distance: I value: ", 0.0);
    	t_d = SmartDashboard.getNumber("Distance: D value: ", -.01);
    	t_Distance = SmartDashboard.getNumber("Test Drive Distance:", 30.0);
    	
    	Robot.drivetrain.setDrivingPIDSetpoints(t_Distance);
    	Robot.drivetrain.makeNewPidDriving(t_p, t_i, t_d); //get p,i,d from smartdashboard
    	Robot.drivetrain.setDrivingPIDSetpoints(t_Distance);
    	System.out.println("P = " + t_p + " I = " + t_i + " D = " + t_d + " dist = " + t_Distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//t_done = Robot.drivetrain.driveStraightWithPID(t_Distance); needs method
    	SmartDashboard.putNumber("PID Drive L Encoder Value: ", Robot.drivetrain.getEncoderValue(Robot.drivetrain.LEFT_MOTOR_ENCODER));
    	SmartDashboard.putNumber("PID Drive R Encoder Value: ", Robot.drivetrain.getEncoderValue(Robot.drivetrain.RIGHT_MOTOR_ENCODER));
    	
    	t_done = Robot.drivetrain.driveStraightWithPID(t_Distance);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return t_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	System.out.println("finished");
    	//System.out.println("LE: " + Robot.drivetrain.getLeftEncoder() + " RE: " + Robot.drivetrain.getRightEncoder());
    	t_Distance = 0.0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}