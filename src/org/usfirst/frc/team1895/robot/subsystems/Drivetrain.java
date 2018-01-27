package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.Default_Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;
/**
 * Function: Drives around. Can gear shift into high and low gear. I am also including the Compressor here. 
 * Motors: 2 pistons (transmission), 4 motors (wheels)
 * Sensors: Encoders, gyro, rangefinders (4)
 * Last Updated: 1/13/2018 by Maddy
 */

public class Drivetrain extends Subsystem {
	int index = 0;
	int index2 = 0;
	
	// motors                           CAN ID #
    private TalonSRX left_dt_motor1;  //1
    private TalonSRX left_dt_motor2;  //2
    private TalonSRX right_dt_motor1; //4
    private TalonSRX right_dt_motor2; //5
    
    // pneumatics
    //private final Compressor compressor;
    //private final DoubleSolenoid transmission_solenoid;
    
    // digital IO sensors
    private Encoder l_encoder;
    private Encoder r_encoder;
    
    // analog sensors
    private AnalogGyro gyro;
    private AHRS ahrs;
    private AnalogInput fr_rangefinder;
    private AnalogInput l_rangefinder;
    private AnalogInput r_rangefinder;
    private AnalogInput in_rangefinder;
    
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
    	
    	//digital IO sensors
    	l_encoder = new Encoder(RobotMap.LEFT_ENCODER_A_PORT, RobotMap.LEFT_ENCODER_B_PORT, true);
    	r_encoder = new Encoder(RobotMap.RIGHT_ENCODER_A_PORT, RobotMap.RIGHT_ENCODER_B_PORT, true); //third negates encoder values
    	
    	l_encoder.setDistancePerPulse(.022);
    	r_encoder.setDistancePerPulse(.022);
    	
    	//analog sensors
    	gyro = new AnalogGyro(RobotMap.GYRO_PORT);
    	try {
	   		 /* Communicate w/navX-MXP via the MXP SPI Bus. */
	   		 /* Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or
	   		 SerialPort.Port.kUSB */
	   		 /* See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/
	   		 for details. */
	   		 ahrs = new AHRS(SPI.Port.kMXP);
   		 } catch (RuntimeException ex ) {
	   		 DriverStation.reportError("Error instantiating navX-MXP: " +
	   		 ex.getMessage(), true);
   		 }
    	fr_rangefinder = new AnalogInput(RobotMap.FRONT_RANGEFINDER_PORT);
    	l_rangefinder = new AnalogInput(RobotMap.LEFT_RANGEFINDER_PORT);
    	r_rangefinder = new AnalogInput(RobotMap.RIGHT_RANGEFINDER_PORT);
    	in_rangefinder = new AnalogInput(RobotMap.INTAKE_RANGEFINDER_PORT);
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
    
    public void tankDrive(double left, double right) {
		if (left > 1.0)
			left = 1.0;
		if (left < -1.0)
			left = -1.0;
		if (right > 1.0)
			right = 1.0;
		if (right < -1.0)
			right = -1.0;
		left_dt_motor1.set(ControlMode.PercentOutput, left);
    	left_dt_motor2.follow(left_dt_motor1);
		right_dt_motor1.set(ControlMode.PercentOutput, -right);
    	right_dt_motor2.follow(right_dt_motor1);
		// Check to see if gear shifting is necessary. if it is, then shift
		// shiftGears();
	}
   
//=ENCODERS AND DRIVE STRAIGHT==================================================================================================================
    public void resetEncoders() {
    	l_encoder.reset();
    	r_encoder.reset();
    }
    
    public double getLeftEncoder() {
    	return l_encoder.getDistance();
    }

    public double getRightEncoder() {
    	return r_encoder.getDistance();
    }
    
    public boolean driveStraightSetDistance(double speed, double targetDis) {
    	boolean targetReached = false;
    	
    	double leftDis = l_encoder.getDistance();
    	double rightDis = r_encoder.getDistance();
    	
    	index = index +1;
    	if (index == 5) {
    		index=0;
    		
    		index2 = index2 +1;
    		System.out.println(String.format("index2: %4d Left: %5.1f right: %5.1f target distance: %5.1f" , index2, leftDis, rightDis, targetDis));
    	}
    	double error = leftDis-rightDis;  //if L>R ie needs to go left --> positive and vice versa
    	double toleranceDis = .1; // inches
    
    	if (targetDis > 0) {//moving forward code
    	//Checking if we still have to keep driving or not. If we haven't reached our target distance, keep driving straight. Otherwise, stop.
    		if((targetDis > leftDis) && (targetDis > rightDis)) {
    			if(error < -toleranceDis) { //drifting left, needs to go right
        			tankDrive(speed*0.75, speed);
        			System.out.println("drifting left");
    			}
    			else if(error > toleranceDis) { //drifting right, needs to go left
    				tankDrive(speed, speed*0.75);
    				System.out.println("drifting right");
    			}
    			else {
    				tankDrive(speed, speed);
    				System.out.println("help i'm not correcting myself");
    			}
    		} else {
    			arcadeDrive(0,0);
    			targetReached = true;
    		}
    	}else { //moving backwards code
    		if((targetDis < leftDis) && (targetDis < rightDis)) {
    			if(error < -toleranceDis) { //drifting left, needs to go right
        			tankDrive(-speed, -speed*0.75);
    			}
    			else if(error > toleranceDis) { //drifting right, needs to go left
    				tankDrive(-speed*0.75, -speed);
    			}
    			else {
    				tankDrive(-speed, -speed);
    			}
    		} else {
    			arcadeDrive(0,0);
    			targetReached = true;
    		}
    	}
    	return targetReached;
    }
    
    
 //=GYRO AND TURNING===============================================================================================================================
    public void resetAHRSGyro() {
    	ahrs.reset();
    }
    
    public double getAHRSAngle() {
    	return ahrs.getAngle();
    }
    
    public void resetGyro() {
    	gyro.reset();
    }
    public double getAngle() {
    	return gyro.getAngle();
    }
    
    
//=RANGEFINDER CODE===============================================================================================================================
	public boolean drivefr_RFDistance(double goaldistance, double speed) {  //TODO: do we need separate methods??? this only does front RF
		double left_speed = speed ;
		double right_speed = speed;
		if (fr_rangefinderDist() <= (goaldistance)) {  // if the robot crossed the goal distance + buffer then the code will stop
			tankDrive(0, 0);
			return true;
		} else {  // if it hasn't crossed it will run at a determined speed
			tankDrive(left_speed, right_speed);
			return false;
		}
	}
	
	public double fr_rangefinderDist() {  //TODO: check that this is right / T U N E. also check if battery affects output
		double outputValue = fr_rangefinder.getAverageVoltage();
		if (outputValue > 2.4 || outputValue < 0.4) { // code currently only
														// accurate from 0.4-2.4
														// volts
			return -1;
			// TODO: Add code to handle that -1 so the robot can act accordingly
		}
		double voltage = Math.pow(outputValue, -1.16);
		double coefficient = 10.298;
		double d = voltage * coefficient;
		return d;
	}

	public double l_rangefinderDist() {  //TODO: check that this is right / T U N E. also check if battery affects output
		double outputValue = l_rangefinder.getAverageVoltage();
		if (outputValue > 2.4 || outputValue < 0.4) { // code currently only
														// accurate from 0.4-2.4
														// volts
			return -1;
			// TODO: Add code to handle that -1 so the robot can act accordingly
		}
		double voltage = Math.pow(outputValue, -1.16);
		double coefficient = 10.298;
		double d = voltage * coefficient;
		return d;
	}
	
	public double r_rangefinderDist() {  //TODO: check that this is right / T U N E. also check if battery affects output
		double outputValue = r_rangefinder.getAverageVoltage();
		if (outputValue > 2.4 || outputValue < 0.4) { // code currently only
														// accurate from 0.4-2.4
														// volts
			return -1;
			// TODO: Add code to handle that -1 so the robot can act accordingly
		}
		double voltage = Math.pow(outputValue, -1.16);
		double coefficient = 10.298;
		double d = voltage * coefficient;
		return d;
	}
	
	public double in_rangefinderDist() {  //TODO: check that this is right / T U N E. also check if battery affects output
		double outputValue = in_rangefinder.getAverageVoltage();
		if (outputValue > 2.4 || outputValue < 0.4) { // code currently only
														// accurate from 0.4-2.4
														// volts
			return -1;
			// TODO: Add code to handle that -1 so the robot can act accordingly
		}
		double voltage = Math.pow(outputValue, -1.16);
		double coefficient = 10.298;
		double d = voltage * coefficient;
		return d;
	}
	
	public boolean driveParallel(double speed, double buffer, double goalDistance, boolean onLeft) {
		boolean done = false;
    	
		double fromWall;
		if(onLeft) {
	    	fromWall = l_rangefinderDist();
		} else {
	    	fromWall = r_rangefinderDist();
		}
    	
    	double currentDistance = l_encoder.getDistance() + r_encoder.getDistance();
    	
    	double goalTolerance = 1.0; // inches
    	double bufferTolerance = 1.0;
    
    	//Checking if we still have to keep driving or not. If we haven't reached our target distance, keep driving along the wall. Otherwise, stop.
    	if(Math.abs(currentDistance - goalDistance) > goalTolerance) {  //if not yet at target
    		if(onLeft) {  //wall is on left of robot
    			if(fromWall < buffer - bufferTolerance) { //drifting left (toward wall), needs to go right
           			tankDrive(speed, speed*0.75);
           			System.out.println("drifting left, toward wall -- correcting");
        		} else if(fromWall > buffer + bufferTolerance) { //drifting right (away from wall), needs to go left
        			tankDrive(speed*0.75, speed);
        			System.out.println("drifting right, away from wall -- correcting");
        		} else {
        			tankDrive(speed, speed);
        			System.out.println("I am already parallel");
        		}
    		} else {  //wall is on right of robot
    			if(fromWall > buffer + bufferTolerance) { //drifting left (away from wall), needs to go right
           			tankDrive(speed, speed*0.75);
           			System.out.println("drifting left, away from wall -- correcting");
        		} else if(fromWall < buffer - bufferTolerance) { //drifting right (toward wall), needs to go left
        			tankDrive(speed*0.75, speed);
        			System.out.println("drifting right, toward wall -- correcting");
        		} else {
        			tankDrive(speed, speed);
        			System.out.println("I am already parallel");
        		}
    		}
    	} else {   //has reached target -- stop
    		arcadeDrive(0,0);
    		done = true;
    	}
		
		return done;
	}
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Default_Drivetrain());
	}
	
}
