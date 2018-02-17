package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.arm.Default_Arm;

import edu.wpi.first.wpilibj.DoubleSolenoid;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.Encoder;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
/**
 * Function: Moves up and down to put cubes onto switch or scale. Can also extend for added height. Contains
 * latch for climbing. Includes wrist for claw. 
 * Motors: 1 piston (telescoping), 4-5 (1 wrist, 1-2 claw intake, 2 arm rotation)
 * Sensors: Encoders, accelerometer
 * Last Updated: 1/13/2018 by Maddy
 */
public class Arm extends Subsystem {

	// motors
    private TalonSRX wrist_motor;
    private TalonSRX arm_intake1;
    private TalonSRX arm_intake2;
    private TalonSRX top_arm_rotation_motor;			// arm_rotation_motor1 
    private TalonSRX bot_arm_rotation_motor;			// arm_rotation_motor2 
    
    private Encoder armUpEncoder;
    
    public final Accelerometer accel;
	public final BuiltInAccelerometer BIA;
    
    // pneumatics
    //private final DoubleSolenoid telescoping_solenoid;
    
    
    public Arm() {
    	// motors
    	arm_intake1 = new TalonSRX(7);
    	top_arm_rotation_motor = new TalonSRX(RobotMap.TOP_ARM_ROTATION_MOTOR_PORT);
    	bot_arm_rotation_motor = new TalonSRX(RobotMap.BOT_ARM_ROTATION_MOTOR_PORT);
    	
    	top_arm_rotation_motor.follow(bot_arm_rotation_motor);
    	resetEncoder();
    	
    	//accelerometer
    	BIA = new BuiltInAccelerometer();
		accel = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
		
		//TODO:find out if there is range finder on arm if not delete later
		//range_finder = new AnalogInput(0);	
		LiveWindow.add(this);
		LiveWindow.addChild(this, accel);

    	//pneumatics
    	//telescoping_solenoid = new DoubleSolenoid(RobotMap.ARM_TELESCOPING_SOLENOID_A_PORT, RobotMap.ARM_TELESCOPING_SOLENOID_B_PORT);
    }
    
    public void driveArm(double armSpeed) {
    	int armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
    	int armEncoderUpperLimit = 14000;
    	int armEncoderLowerLimit = 0;
    	
    	if (((armEncoderValue > armEncoderUpperLimit) && (armSpeed < 0)) ||
    	((armEncoderValue < armEncoderLowerLimit) && (armSpeed > 0))){
    		armSpeed = 0;
    	}
    	bot_arm_rotation_motor.set(ControlMode.PercentOutput, armSpeed );
    
    	
    	//System.out.println("Arm Encoder: " + armEncoderValue + "  Arm Speed;" + armSpeed);
    }
    
    public void setArmToZero() {
    	arm_intake1.set(ControlMode.PercentOutput, 0);
    }
    
  //TODO: change these to match power-up
//	public double getVoltage(){
//		return range_finder.getVoltage();
//	}
//	
//	public double getShoulderVaule(){
//		return shoulder.getPosition();
//
//	}
	public double getXValue(){
		return accel.getX();
	}
	
	public double getYValue(){
		return accel.getY();
	}
	
	public double getZValue(){
		return accel.getZ();
	}
    
    public String getAllAxesString(){
		return String.format("X =  %6.2f   Y =  %6.2f  Z =  %6.2f ", accel.getX(), accel.getY(), accel.getZ());
	}
	
	public void calibrate(){
		return;
	}
    
	  public void driveShoulder(double setSpeed){
	    	//System.out.println("Set Speed: " + setSpeed);
		  bot_arm_rotation_motor.set(ControlMode.PercentOutput, setSpeed);
		  top_arm_rotation_motor.set(ControlMode.PercentOutput, setSpeed);
		  
	    }
	    
		public boolean moveToZAxis(double zaxis) {
			boolean retVal = false;
			
			// Figure out where the arm is in space
			double yAccel = accel.getY();
			
//			
//			double z_tolerUp = zaxis + .05;
//			double z_tolerDo = zaxis - .05;
//			
//			if( (yAccel <= z_tolerUp) && (yAccel >= z_tolerDo) ){
//				bot_arm_rotation_motor.set
//				setSpeed(0.0);
//				retVal = true;
//			}
//			else if( zaxis > yAccel ){
//				shoulder.setSpeed(.1);
//			}
//			else if( zaxis < yAccel ){
//				shoulder.setSpeed(-.1);
//			}
//			else{
//				System.out.println("Got some weird numbers : " + zaxis);
//			}
//				
			return retVal;
		}

//		public void driveWrist(double setSpeed) {
//			
//	    	wrist.setPosition(setSpeed);
//		}
//	
    public void initDefaultCommand() {
        setDefaultCommand(new Default_Arm());
        
    }

	public void resetEncoder() {
		bot_arm_rotation_motor.getSensorCollection().setQuadraturePosition(0, 0);
		
	}
	
}

