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
    	t_p = SmartDashboard.getNumber("P value: ", .1);
    	t_i = SmartDashboard.getNumber("I value: ", 0.0);
    	t_d = SmartDashboard.getNumber("D value: ", -.01);
    	t_Distance = SmartDashboard.getNumber("Test Drive Distance: ", 20.0);
    	
    	//Robot.drivetrain.makeNewPidDriving(t_p, t_i, t_d); //get p,i,d from smartdashboard
    	//Robot.drivetrain.setPIDSetpoints(t_Distance);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	//t_done = Robot.drivetrain.driveStraightWithPID(t_Distance); needs method
    	SmartDashboard.putNumber("PID Drive L Encoder Value: ", Robot.drivetrain.getEncoderValue(Robot.drivetrain.LEFT_MOTOR_ENCODER));
    	SmartDashboard.putNumber("PID Drive R Encoder Value: ", Robot.drivetrain.getEncoderValue(Robot.drivetrain.RIGHT_MOTOR_ENCODER));
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return t_done;
    }

    // Called once after isFinished returns true
    protected void end() {
    	t_Distance = 0.0;
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}