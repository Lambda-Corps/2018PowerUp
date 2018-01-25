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
    private AHRS ahrs;
    
    // digital IO sensors
    private Encoder l_encoder;
    private Encoder r_encoder;
    
    // analog sensors
    private AnalogGyro gyro;
    AHRS ahrs;
    private AnalogInput rangefinder;
    
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
    	
        // TODO -- Fix me, are we using navx or analog gyro?
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
    	rangefinder = new AnalogInput(RobotMap.RANGEFINDER_PORT);

    }

    // TODO -- Fix me, are the comments accurate here?
    // Testing current limiting
    public void arcadeDrive(double trans_speed, double yaw) {
		double left_speed = trans_speed + yaw;
		double right_speed = yaw - trans_speed;

		double max_speed = Math.max(Math.abs(left_speed), Math.abs(right_speed));
		if (Math.abs(max_speed) > 1.0) {
			left_speed /= max_speed;
			right_speed /= max_speed;
		}

        // TODO -- Is this as bug, or unnecessary code?
    	left_speed = left_speed;

        // TODO -- Fix me, should the follower mode be set in the constructor only?
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
    
    public void resetEncoders() {
    	l_encoder.reset();
    	r_encoder.reset();
    }
    
    public boolean driveStraightSetDistance(double speed, double targetDis) {
    	boolean targetReached = false;
    	
    	double leftDis = l_encoder.getDistance();
    	double rightDis = r_encoder.getDistance();
    	
    	double error = leftDis-rightDis;  //if L>R ie needs to go left --> positive and vice versa
    	double toleranceDis = .1; // inches
    
        // TODO -- Fix me, why aren't we just arcade driving all the time?  Is there some
        // need or benefit to tank driving?
    	if (targetDis > 0) {//moving forward code
    	//Checking if we still have to keep driving or not. If we haven't reached our target distance, keep driving straight. Otherwise, stop.
    		if((targetDis > leftDis) && (targetDis > rightDis)) {
    			if(error < -toleranceDis) { //drifting left, needs to go right
        			tankDrive(speed*0.75, speed);
        			//System.out.println("drifting left");
    			}
    			else if(error > toleranceDis) { //drifting right, needs to go left
    				tankDrive(speed, speed*0.75);
    				//System.out.println("drifting right");
    			}
    			else {
    				tankDrive(speed, speed);
    				//System.out.println("help i'm not correcting myself");
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
    
    public void resetGyro() {
    	ahrs.reset();
    }
    
    public double getGyro() {
    	return ahrs.getAngle();
    }
    
	public boolean driveRFDistance(double goaldistance, double speed) {
		double left_speed = speed ;
		double right_speed = speed;
		if (checkRFVoltage() <= (goaldistance)) {  // if the robot crossed the goal distance + buffer then the code will stop
			tankDrive(0, 0);
			return true;
		} else {  // if it hasn't crossed it will run at a determined speed
			tankDrive(left_speed, right_speed);
			return false;
		}
	}
	
	public double checkRFVoltage() {  //TODO: check that this is right / T U N E. also check if battery affects output
		double outputValue = rangefinder.getAverageVoltage();
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

    public void resetGyro() {
    	gyro.reset();
    }

    public double getAngle() {
    	return gyro.getAngle();
    }
    
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Default_Drivetrain());
	}
	
}
