package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import com.ctre.phoenix.motorcontrol.*;
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
    private final Compressor compressor;
    private final DoubleSolenoid transmission_solenoid;
    
    public Drivetrain() {
    	//motors
    	left_dt_motor1 = new TalonSRX(RobotMap.LEFT_DT_MOTOR1_PORT);
    	left_dt_motor2 = new TalonSRX(RobotMap.LEFT_DT_MOTOR2_PORT);
    	right_dt_motor1 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR1_PORT);
    	right_dt_motor2 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR2_PORT);
    	
    	//current limited to 10 amps when current is >15amps for 100 milliseconds
    	left_dt_motor1.configContinuousCurrentLimit(10, 0); 
    	left_dt_motor1.configPeakCurrentLimit(15,  0);
    	left_dt_motor1.configPeakCurrentDuration(100, 0);
    	left_dt_motor1.enableCurrentLimit(true);
    	
    	left_dt_motor2.set(ControlMode.Follower, 6); 
    	
    	//pneumatics
    	compressor = new Compressor();
    	transmission_solenoid = new DoubleSolenoid(RobotMap.DRIVETRAIN_SOLENOID_A_PORT, RobotMap.DRIVETRAIN_SOLENOID_B_PORT);
    }

    // Testing current limiting
    
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		// setDefaultCommand(new MySpecialCommand());
	}
}
