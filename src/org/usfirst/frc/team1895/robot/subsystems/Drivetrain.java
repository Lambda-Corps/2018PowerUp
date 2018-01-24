package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.Default_Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Function: Drives around. Can gear shift into high and low gear. I am also including the Compressor here. 
 * Motors: 2 pistons (transmission), 4 motors (wheels)
 * Sensors: Encoders, gyro, rangefinders (4)
 * Last Updated: 1/13/2018 by Maddy
 */

public class Drivetrain extends Subsystem {
	
	// motors                           CAN ID #
    private TalonSRX left_dt_motor1;  //1
    private TalonSRX left_dt_motor2;  //2
    private TalonSRX right_dt_motor1; //4
    private TalonSRX right_dt_motor2; //5
    
    // pneumatics
    //private final Compressor compressor;
    //private final DoubleSolenoid transmission_solenoid;
   
    //Gyro
    private AnalogGyro gyro;
    //private AHRS ahrs;
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
    	gyro = new AnalogGyro(RobotMap.GYRO_PORT);
    	
    }

    // Testing current limiting
    public void arcadeDrive(double trans_speed, double yaw) {
		double left_speed = trans_speed + yaw;
		double right_speed = yaw - trans_speed;

		double max_speed = Math.max(Math.abs(left_speed), Math.abs(right_speed));
		if (Math.abs(max_speed) > 1.0) {
			left_speed /= max_speed;
			right_speed /= max_speed;
		}
    	
		//motorgroup stuffs - CHANGED TO LEADER FOLLOWER TESTING		
		left_dt_motor1.set(ControlMode.PercentOutput, left_speed);
    	left_dt_motor2.follow(left_dt_motor1);
		right_dt_motor1.set(ControlMode.PercentOutput, right_speed);
    	right_dt_motor2.follow(right_dt_motor1);
	}
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Default_Drivetrain());
	}
}
