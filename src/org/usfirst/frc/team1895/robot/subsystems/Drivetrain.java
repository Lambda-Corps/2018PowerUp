package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.Default_Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Function: Drives around. Can gear shift into high and low gear. I am also including the Compressor here. 
 * Motors: 2 pistons (transmission), 4 motors (wheels)
 * Sensors: Encoders, gyro, rangefinders (4)
 * Last Updated: 1/13/2018 by Maddy
 */

public class Drivetrain extends Subsystem {
	
	// motors
    private TalonSRX left_dt_motor1;
    private TalonSRX left_dt_motor2;
    private TalonSRX right_dt_motor1;
    private TalonSRX right_dt_motor2;
    
    // pneumatics
    //private final Compressor compressor;
    //private final DoubleSolenoid transmission_solenoid;
    
    public Drivetrain() {
    	//motors
    	left_dt_motor1 = new TalonSRX(RobotMap.LEFT_DT_MOTOR1_PORT);
    	left_dt_motor2 = new TalonSRX(RobotMap.LEFT_DT_MOTOR2_PORT);
    	right_dt_motor1 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR1_PORT);
    	right_dt_motor2 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR2_PORT);
    	
    	//current limited to 10 amps when current is >15amps for 100 milliseconds
    	//left_dt_motor1.configContinuousCurrentLimit(10, 0); 
    	//left_dt_motor1.configPeakCurrentLimit(15,  0);
    	//left_dt_motor1.configPeakCurrentDuration(100, 0);
    	//left_dt_motor1.enableCurrentLimit(true);
    	
    	//pneumatics
    	//compressor = new Compressor();
    	//transmission_solenoid = new DoubleSolenoid(RobotMap.DRIVETRAIN_SOLENOID_A_PORT, RobotMap.DRIVETRAIN_SOLENOID_B_PORT);
    }

    // Testing current limiting
    public void arcadeDrive(double trans_speed, double yaw) {
		yaw *= -1.0;
		trans_speed *= -1.0;
		double left_speed = yaw - trans_speed;
		double right_speed = yaw + trans_speed;

		double max_speed = Math.max(Math.abs(left_speed), Math.abs(right_speed));
		if (Math.abs(max_speed) > 1.0) {
			left_speed /= max_speed;
			right_speed /= max_speed;
		}
		
		//motorgroup stuffs
		left_dt_motor2.set(ControlMode.Current, left_speed);
    	right_dt_motor2.set(ControlMode.Current, left_speed);
	}
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Default_Drivetrain());
	}
}
