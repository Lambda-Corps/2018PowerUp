package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.arm.Default_Arm;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXL345_I2C;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.BuiltInAccelerometer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/**
 * Function: Moves up and down to put cubes onto switch or scale. Can also extend for added height. Contains
 * latch for climbing. Includes wrist for claw. 
 * Motors: 1 piston (telescoping), 4-5 (1 wrist, 1-2 claw intake, 2 arm rotation)
 * Sensors: Encoders, accelerometer
 * Last Updated: 1/13/2018 by Maddy
 */
public class Arm extends Subsystem {

	// ARM-RELATED COMPONENTS
	// motors
    private TalonSRX wrist_motor;
    private TalonSRX top_arm_rotation_motor;			// arm_rotation_motor1 
    private TalonSRX bot_arm_rotation_motor;			// arm_rotation_motor2 
    
    public final Accelerometer accel;
	public final BuiltInAccelerometer BIA;
    
    // pneumatics
    private final DoubleSolenoid telescoping_solenoid;
    
    // CLAW and Intake RELATED COMPONENTS
    private TalonSRX claw_intake_motor1;
	private TalonSRX claw_intake_motor2;
	private static final double CLAW_INTAKE_SPEED = 0.6;
	private static final double CLAW_DEPLOY_SPEED = -.7;

	
	private AnalogInput in_rangefinder;
	private AnalogInput potentiometer;
	
	// final variables for potentiometer
	private final double VOLTVAL_1ROT = 0.6438;
	private final double VOLTVAL_3QUARTERROT = 0.48285;
	private final double VOLTVAL_HALFROT = 0.3219;
	private final double VOLTVAL_QUARTERROT = 0.16095;
	
	//potentiometer variables
	private final double ARM_SPEED = 0.6;
	private final double WRIST_SPEED = 0.25;
	
	boolean hasCube = false;
    
	// Positional Variables to represent the arm positions for scale and switch
	// TODO -- Check my numbers here that I've remembered them correctly
//	public static final int ARM_LOWER_SOFT_LIMIT = 700;
//	public static final int ARM_UPPER_SOFT_LIMIT = 16000;
//	private static final int ARM_EXTENSION_LOWER_LIMIT = 3000;
//	private static int ARM_EXTENSION_UPPER_LIMIT = 11500;
	
//	public static final int ARM_LOWEST_POSITION = ARM_LOWER_SOFT_LIMIT;
//	public static final int ARM_SWITCH_POSITION = 5600;
//	public static final int ARM_SCALE_LOW_POSITION = 13000;
//	public static final int ARM_SCALE_MID_POSITION = 13001;
//	public static final int ARM_SCALE_HIGH_POSITION = 13002;
//	public static final int ARM_CLIMB_POSITION = ARM_UPPER_SOFT_LIMIT;
//	public static final int ARM_POSITIONAL_TOLERANCE = 750;
	
	public static final double ARM_LOWEST_LIMIT = 0.650; //0.980;
	public static final double ARM_LOWER_SOFT_LIMIT = .67;
	public static final double ARM_UPPER_SOFT_LIMIT = 0.037;
	private static final double ARM_EXTENSION_LOWER_LIMIT = 0.5;
	private static final double ARM_EXTENSION_UPPER_LIMIT = .2;
	
	public static final double ARM_LOWEST_POSITION = ARM_LOWER_SOFT_LIMIT;
	public static final double ARM_SWITCH_POSITION = .394;
	public static final double ARM_SCALE_LOW_POSITION = .139;
	public static final double ARM_SCALE_MID_POSITION = 0.139;
	public static final double ARM_SCALE_HIGH_POSITION = .139;
	public static final double ARM_CLIMB_POSITION = ARM_UPPER_SOFT_LIMIT;
	public static final double ARM_POSITIONAL_TOLERANCE = .007;
	
	private boolean endGameStarted;
	
    public Arm() {
	    	// motors
	    	claw_intake_motor1 = new TalonSRX(RobotMap.CLAW_INTAKE_MOTOR1_PORT);
	    	claw_intake_motor1.setInverted(true);
		claw_intake_motor2 = new TalonSRX(RobotMap.CLAW_INTAKE_MOTOR2_PORT);
	    	claw_intake_motor2.setInverted(true);
	    	claw_intake_motor2.follow(claw_intake_motor1);
	    	wrist_motor = new TalonSRX(RobotMap.WRIST_MOTOR_PORT);
	    	wrist_motor.getSensorCollection().setQuadraturePosition(0, 0);
	    	top_arm_rotation_motor = new TalonSRX(RobotMap.TOP_ARM_ROTATION_MOTOR_PORT);
	    	bot_arm_rotation_motor = new TalonSRX(RobotMap.BOT_ARM_ROTATION_MOTOR_PORT);
	    	
	    	/*
			// TODO -- Test this code so we can uncomment if it works.
			// current limited to 10 amps when current is >15amps for 100 milliseconds
			 left_dt_motor1.configContinuousCurrentLimit(10, 0);
			 left_dt_motor1.configPeakCurrentLimit(15, 0);
			 left_dt_motor1.configPeakCurrentDuration(100, 0);
			 left_dt_motor1.enableCurrentLimit(true);
			 left_dt_motor1.configOpenloopRamp(0.15, 0);
			 
			 right_dt_motor1.configContinuousCurrentLimit(10, 0);
			 right_dt_motor1.configPeakCurrentLimit(15, 0);
			 right_dt_motor1.configPeakCurrentDuration(100, 0);
			 right_dt_motor1.enableCurrentLimit(true);
			 right_dt_motor1.configOpenloopRamp(0.15, 0);
			 */
	    	
	//    	bot_arm_rotation_motor.setInverted(true);
	//    	top_arm_rotation_motor.setInverted(true);
	    	top_arm_rotation_motor.follow(bot_arm_rotation_motor);
	    	resetEncoder();
	    	
	    	//accelerometer
	    	BIA = new BuiltInAccelerometer();
		accel = new ADXL345_I2C(I2C.Port.kOnboard, Accelerometer.Range.k4G);
		
		//analog sensors
		in_rangefinder = new AnalogInput(RobotMap.INTAKE_RANGEFINDER_PORT);
		potentiometer = new AnalogInput(RobotMap.POTENTIOMETER_PORT);
		
		//led
		//led1 = new DigitalOutput(9); //COMMENTED OUT FOR STEAMWORKS BOT
		//TODO:find out if there is range finder on arm if not delete later
		//range_finder = new AnalogInput(0);	
//		LiveWindow.add(this);
//		LiveWindow.addChild(this, accel);
	
	    	//pneumatics
	    	telescoping_solenoid = new DoubleSolenoid(RobotMap.ARM_TELESCOPING_SOLENOID_A_PORT, RobotMap.ARM_TELESCOPING_SOLENOID_B_PORT);
	    	
	    	endGameStarted = false;
    }
    
//==Arm Movement=======================================================================================================
    public void driveArm(double armSpeed) {
    		//armSpeed*=-1;
    		// Normalize the input, must be in a -1 <= x <= 1 range
    		armSpeed = normalizeMotorInput(armSpeed);
    		armSpeed = removeDeadZoneInput(armSpeed);
    		
    		// Square the inputs for smoother arm controls.
    		armSpeed = Math.copySign(armSpeed* armSpeed, armSpeed);
    	
    		// Check where we are with respect to the limits
	    	//int armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
    		double armPotentiometer = getPotentiometerVoltage();
//	    	SmartDashboard.putNumber("ARM ENCODER", armEncoderValue);
//	    	SmartDashboard.putNumber("ARM SPEED", armSpeed);
	    	//These variable control the angle at which the piston will extend
	    	if(armSpeed>0) {
	    		if(armPotentiometer>ARM_LOWER_SOFT_LIMIT) {
	    			armSpeed = 0;
	    			System.out.println("stopped by lower limit");
	    		} 
	    		armSpeed *= 0.5;
	    	} else if(armSpeed<0){
	    		if(armPotentiometer <= ARM_UPPER_SOFT_LIMIT) {
	    			armSpeed = 0;
	    			System.out.println("stopped by upper limit");
	    		} 
	    	}
	    
	    	// We only want to manage telescoping during the rounds, not during the endgame
	    	if(!endGameStarted) {
		    	if(armPotentiometer>ARM_EXTENSION_LOWER_LIMIT && 
		    			armPotentiometer<ARM_EXTENSION_UPPER_LIMIT) {
		    		//led1.set(true);
		    		telescoping_solenoid.set(DoubleSolenoid.Value.kForward);  //retract
		    	}
		    	else {
		    		//led1.set(false);
		    		//telescoping_solenoid.set(DoubleSolenoid.Value.kReverse);
		    	}
	    	}
	    	bot_arm_rotation_motor.set(ControlMode.PercentOutput, armSpeed);
	    	//System.out.println("Setting Arm to: " + armSpeed);  
	    	
	    	//wrist_motor.set(ControlMode.PercentOutput, armSpeed);
	    	if(getPotentiometerVoltage() <ARM_SWITCH_POSITION || !hasCube) {
	    		//SlevelWrist();
	    	}
    }
    
    private double removeDeadZoneInput(double value) {
    		// Deadband is normally in the third significant digit (e.g. .003)
    		// so we'll use the second signficant digit as the limiter.
		if( value > 0 && value < 0.01 ) {
			return 0;
		}
		
		if( value < 0 && value > -0.01) {
			return 0;
		}
		return value;
	}

	private double normalizeMotorInput(double armSpeed) {
		if( armSpeed < -1.0 ) {
			return -1.0;
		}
		if( armSpeed > 1.0 ) {
			return 1.0;
		}
		return armSpeed;
	}

	public double getAccelValue() {
	    	double accelValue = accel.getX();
	    	
//	    	bot_arm_rotation_motor.getSensorCollection().s
	    	
	    	return accelValue;
    }
    
    public boolean setPosition(double armPosition) {
    		boolean bPosReturn = false;
			SmartDashboard.putString("status", "method");
    		 if(armPosition == ARM_LOWEST_POSITION) {
    			 bPosReturn = setPositionLowestPOT();
    		 }
    		 else if(armPosition ==ARM_CLIMB_POSITION) {
    			 bPosReturn = setPositionClimbPOT();
    		 }
    		 else if(armPosition ==ARM_SWITCH_POSITION) {
    			 bPosReturn = setPositionSwitchPOT();
    		 }
    		 else if(armPosition ==ARM_SCALE_HIGH_POSITION) {
    			 bPosReturn = setPositionScalePOT(ARM_SCALE_HIGH_POSITION);
    		}
    		 else if(armPosition ==ARM_SCALE_MID_POSITION) {
     			bPosReturn = setPositionScalePOT(ARM_SCALE_MID_POSITION);
    		 }
    		 else if(armPosition ==ARM_SCALE_LOW_POSITION) {
     			bPosReturn = setPositionScalePOT(ARM_SCALE_LOW_POSITION);
    		 }

	    	return bPosReturn;
    }
    
    private boolean setPositionScalePOT(double armScaleHighPosition) {
    	boolean bReturnPos = false;
    	boolean armHigherThanPosition;
		// First get the current position in space
    	double currArmPosition = getPotentiometerVoltage();
    	
    	if(armScaleHighPosition ==ARM_SCALE_HIGH_POSITION) {
    		armHigherThanPosition = currArmPosition < ARM_SCALE_HIGH_POSITION;
    		if(armHigherThanPosition) {
				if( ARM_SCALE_HIGH_POSITION -currArmPosition <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				// We aren't there yet, drive the motor downward
				driveArm(ARM_SPEED);
			}
			else {
				if( currArmPosition -ARM_SCALE_HIGH_POSITION <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				driveArm(-ARM_SPEED);
			}
    	}
    	return bReturnPos;
    }
    
    /*private boolean setPositionScale(int armScaleHighPosition) {
	    	boolean bReturnPos = false;
	    	boolean armHigherThanPosition;
			// First get the current position in space
		int currArmPosition = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
		
		
		switch(armScaleHighPosition) {
		case ARM_SCALE_HIGH_POSITION:
			armHigherThanPosition = (currArmPosition > ARM_SCALE_HIGH_POSITION);
			// First condition checks arm is above position, else checks for equal or below
			if(armHigherThanPosition) {
				if( currArmPosition - ARM_SCALE_HIGH_POSITION <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				// We aren't there yet, drive the motor downward
				driveArm(ARM_SPEED);
			}
			else {
				if( ARM_SCALE_HIGH_POSITION - currArmPosition <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				driveArm(-ARM_SPEED);
			}
			break;
		case ARM_SCALE_MID_POSITION:
			armHigherThanPosition = (currArmPosition > ARM_SCALE_MID_POSITION);
			// First condition checks arm is above position, else checks for equal or below
			if(armHigherThanPosition) {
				if( currArmPosition - ARM_SCALE_MID_POSITION <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				// We aren't there yet, drive the motor downward
				driveArm(ARM_SPEED);
			}
			else {
				if( ARM_SCALE_MID_POSITION - currArmPosition <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				driveArm(-ARM_SPEED);
			}
			
			break;
		case ARM_SCALE_LOW_POSITION:
			armHigherThanPosition = (currArmPosition > ARM_SCALE_LOW_POSITION);
			// First condition checks arm is above position, else checks for equal or below
			if(armHigherThanPosition) {
				if( currArmPosition - ARM_SCALE_LOW_POSITION <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				// We aren't there yet, drive the motor downward
				driveArm(ARM_SPEED);
			}
			else {
				if( ARM_SCALE_LOW_POSITION - currArmPosition <= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				//up
				driveArm(-ARM_SPEED);
			}
			break;
		}
		
	
		return bReturnPos;
	}*/

//	private boolean setPositionSwitch() {
//    	boolean bReturnPos = false;
//    	// First get the current position in space
//		int currArmPosition = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
//		System.out.println("current arm pos " + currArmPosition);
//		boolean armHigherThanPosition = (currArmPosition > ARM_SWITCH_POSITION);
//		
//		// First condition checks arm is above position, else checks for equal or below
//		if(armHigherThanPosition) {
//			if( currArmPosition - ARM_SWITCH_POSITION <= ARM_POSITIONAL_TOLERANCE) {
//				// We are there
//				driveArm(0);
//				bReturnPos = true;
//			}
//			
//			// We aren't there yet, drive the motor downward
//			driveArm(ARM_SPEED); 
//		}
//		else {
//			if( ARM_SWITCH_POSITION - currArmPosition <= ARM_POSITIONAL_TOLERANCE) {
//				// We are there
//				driveArm(0);
//				bReturnPos = true;
//			}
//			
//			//up
//			driveArm(-ARM_SPEED);
//		}
//		
//		return bReturnPos;
//	}
    
    private boolean setPositionSwitchPOT() {
    	boolean bReturnPos = false;
    	// First get the current position in space
		double currArmPosition = getPotentiometerVoltage();
		//System.out.println("current arm pos " + currArmPosition);
		boolean armHigherThanPosition = (currArmPosition < ARM_SWITCH_POSITION);
		
		// First condition checks arm is above position, else checks for equal or below
		if(armHigherThanPosition) {
			if( ARM_SWITCH_POSITION - currArmPosition <= ARM_POSITIONAL_TOLERANCE) {
				// We are there
				driveArm(0);
				bReturnPos = true;
			}
			
			// We aren't there yet, drive the motor downward
			driveArm(ARM_SPEED); 
		}
		else {
			if( currArmPosition - ARM_SWITCH_POSITION<= ARM_POSITIONAL_TOLERANCE) {
				// We are there
				driveArm(0);
				bReturnPos = true;
			}
			
			//up
			driveArm(-ARM_SPEED);
		}
		
		return bReturnPos;
	}

	// This method will be called by the outer wrapper setPosition
    // method.  It will drive the arm down to the lowest position
    // as defined above.
//    private boolean setPositionLowest() {
//    		// First get the current position in space
//    		int currArmPosition = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
//    		
//    		// If we are within the tolerance, then stop
//    		//
//    		// If this wasn't the lowest position there would be two checks, one from
//    		// tolerance + and one from tolerance -.  We are at the bottom so we just
//    		// need to worry about tolerance -.  The tolerance is really only there 
//    		// to compensate for the timing of the scheduler.  Should we move this to 
//    		// be a PID controlled loop, we wouldn't need it.
//    		if( currArmPosition - ARM_LOWEST_POSITION <= ARM_POSITIONAL_TOLERANCE) {
//    			// We are there
//    			driveArm(0);
//    			return true;
//    		} 
//    		
//    		// We aren't within the tolerance, so drive the arm down.
//    		driveArm(ARM_SPEED);
//    		
//    		return false;
//	}
    
    private boolean setPositionLowestPOT() {
		// First get the current position in space
		double currArmPosition = getPotentiometerVoltage();
		
		// If we are within the tolerance, then stop
		//
		// If this wasn't the lowest position there would be two checks, one from
		// tolerance + and one from tolerance -.  We are at the bottom so we just
		// need to worry about tolerance -.  The tolerance is really only there 
		// to compensate for the timing of the scheduler.  Should we move this to 
		// be a PID controlled loop, we wouldn't need it.
		if( ARM_LOWEST_POSITION -currArmPosition <= ARM_POSITIONAL_TOLERANCE) {
			// We are there
			driveArm(0);
			return true;
		} 
		
		// We aren't within the tolerance, so drive the arm down.
		driveArm(ARM_SPEED);
		
		return false;
}
    
    // This method will be called by the outer wrapper setPosition
    // to bring the arm to the climbing position
//    private boolean setPositionClimb() {
//    		// First get the current position in space
//		int currArmPosition = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
//		
//		// If we are within the tolerance, then stop
//		//
//		// If this wasn't the lowest position there would be two checks, one from
//		// tolerance + and one from tolerance -.  We are at the bottom so we just
//		// need to worry about tolerance -.  The tolerance is really only there 
//		// to compensate for the timing of the scheduler.  Should we move this to 
//		// be a PID controlled loop, we wouldn't need it.
//		if( currArmPosition >= ARM_CLIMB_POSITION) {
//			// are there
//			driveArm(0);
//			return true;
//		} 
//		
//		// We aren't there yet, so drive the arm up
//		driveArm(-ARM_SPEED);
//		
//		return false;
//    }
    
    private boolean setPositionClimbPOT() {
		// First get the current position in space
	double currArmPosition = getPotentiometerVoltage();
	
	// If we are within the tolerance, then stop
	//
	// If this wasn't the lowest position there would be two checks, one from
	// tolerance + and one from tolerance -.  We are at the bottom so we just
	// need to worry about tolerance -.  The tolerance is really only there 
	// to compensate for the timing of the scheduler.  Should we move this to 
	// be a PID controlled loop, we wouldn't need it.
	if( currArmPosition <= ARM_CLIMB_POSITION) {
		// are there
		driveArm(0);
		return true;
	} 
	
	// We aren't there yet, so drive the arm up
	driveArm(-ARM_SPEED);
	
	return false;
}

	public void stopClawIntake() {
    		claw_intake_motor1.set(ControlMode.PercentOutput, 0);
    }
    
	public void calibrate(){
		return;
	}
	
	public boolean findZero() {
//		System.out.println("encoder value: " + getArmEncoder());
//		System.out.println("potentiometer value: " + getPotentiometerVoltage());
//		System.out.println("accel z " + getZValue());
		return true;
	}
    
//==Encoder Methods====================================================================================================
    public double getArmEncoder() {
	    	double armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
	    	//System.out.print(armEncoderValue);
	    	return armEncoderValue;
    }
    
    public double getWristEncoder() {
	    	double wristEncoderValue = wrist_motor.getSensorCollection().getQuadraturePosition();
	    	//System.out.print(wristEncoderValue);
	    	return wristEncoderValue;
    }
    
	public void resetEncoder() {
		bot_arm_rotation_motor.getSensorCollection().setQuadraturePosition(0, 0);
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
	
//==Arm Get Methods========================================================================================================
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
    	//System.out.println (String.format("X =  %6.2f   Y =  %6.2f  Z =  %6.2f ", accel.getX(), accel.getY(), accel.getZ()));
		return String.format("X =  %6.2f   Y =  %6.2f  Z =  %6.2f ", accel.getX(), accel.getY(), accel.getZ());
	}
    
	public void driveArmShoulder(double setSpeed){
	    	//System.out.println("Set Speed: " + setSpeed);
		bot_arm_rotation_motor.set(ControlMode.PercentOutput, setSpeed);
		top_arm_rotation_motor.set(ControlMode.PercentOutput, setSpeed);
		  
	}
	    
	public boolean moveToZAxis(double zaxis) {
		boolean retVal = false;	
		// Figure out where the arm is in space
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

		public void driveArmWrist(double wristSpeed) {
			wristSpeed = normalizeMotorInput(wristSpeed);
			wristSpeed = removeDeadZoneInput(wristSpeed);
			//TODO set limits
			if(wrist_motor.getSensorCollection().getQuadraturePosition() > 125) {
				wristSpeed = 0;
			}
			
			if(wristSpeed > 0) {
				wrist_motor.set(ControlMode.PercentOutput, -WRIST_SPEED);
			}
			else if(wristSpeed < 0){
				wrist_motor.set(ControlMode.PercentOutput, WRIST_SPEED);
			}
			else {
				wrist_motor.set(ControlMode.PercentOutput, 0);
			}
			SmartDashboard.putNumber("wrist value", Robot.arm.getWristEncoder());
		}
		
		public boolean setMaxWrist() {
			boolean done = false;
			if(getWristEncoder() < 120) {
				//driveArmWrist(WRIST_SPEED);
			} else {
				done = true;
				driveArmWrist(0);
			}
			return done;
		}
	
		
//==Telescoping Arm Code===============================================================================================
	public void toggleTelescopingArm() {
		DoubleSolenoid.Value val = telescoping_solenoid.get();
    	if( val == DoubleSolenoid.Value.kReverse ) {
    		telescoping_solenoid.set( DoubleSolenoid.Value.kForward);
    		return;
    	}
    	else {
    		telescoping_solenoid.set( DoubleSolenoid.Value.kReverse);
    		return;
    	}
	}
	public void extendTelescopingArm() {
		
	    	int armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
	    	//These variable control the angle at which the piston will extend
	    	
	    	// If we're already extended, just return
	    	if( telescoping_solenoid.get() == DoubleSolenoid.Value.kReverse ) {
	    		return;
	    	}
	    	
	    	// If we're going to extend, just make sure we're not going to get a violation
	    	if(armEncoderValue>ARM_EXTENSION_LOWER_LIMIT && armEncoderValue<ARM_EXTENSION_UPPER_LIMIT) {
	    		// We already verified that we're retracted, so just return since we're in the 
	    		// violation zone
	    		return;
	    	} 
	    	
	    	// We're safe to extend, let it rip
    		telescoping_solenoid.set(DoubleSolenoid.Value.kReverse);  //extend
	}
	
	public void retractTelescopingArm() {
		telescoping_solenoid.set(DoubleSolenoid.Value.kForward); // retract
	}
 
//==Potentiometer Code=========================================================================================================
	public double getPotentiometerVoltage() {
		double voltage = potentiometer.getVoltage();
		//System.out.println("Potentiometer voltage " + voltage);
//		SmartDashboard.putNumber("potentiometer", voltage);
		return voltage;
	}
	
	public boolean rotateToUpperScale() {
		boolean done = false;
		double voltage = potentiometer.getVoltage();
		if(voltage < VOLTVAL_1ROT) {
			driveArm(ARM_SPEED);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	
	public boolean rotateToMidScale() {
		boolean done = false;
		double voltage = potentiometer.getVoltage();
		if(voltage < VOLTVAL_3QUARTERROT) {
			driveArm(ARM_SPEED);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	 
	public boolean rotateToLowerScale() {
		boolean done = false;
		double voltage = potentiometer.getVoltage();
		if(voltage < VOLTVAL_HALFROT) {
			driveArm(ARM_SPEED);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	
	public boolean rotateToSwitch() {
		boolean done = false;
		double voltage = potentiometer.getVoltage();
		if(voltage < VOLTVAL_QUARTERROT) {
			driveArm(ARM_SPEED);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	
    public void testDriveArm(double armSpeed) {
	    	double speed = armSpeed / 2;
	    	bot_arm_rotation_motor.set(ControlMode.PercentOutput, speed);
    }
	
//==Wrist Code=========================================================================================================
	public void levelWrist() {
		double wristEncVal = getWristEncoder() * -1;
		double armEncVal = getArmEncoder();
		double targetEncVal = armEncVal/10;
		double wristSpeed = 1.2*WRIST_SPEED;
		double tolerance = 120;
	
		if(wristEncVal > targetEncVal) {
			//move the wrist up
			if((wristEncVal - targetEncVal) <= tolerance) {
				wristSpeed = 0;
				System.out.println("don't drive wrist");
			}
			System.out.println("wrist should go up");
			
		}
		else if(wristEncVal < targetEncVal) {
			//move the wrist down
			if((targetEncVal - wristEncVal) <= tolerance) {
				wristSpeed = 0;
				System.out.println("don't drive wrist");
			}
			System.out.println("wrist should go down");
			wristSpeed *= -1;
		} else {
			wristSpeed = 0;
		}
		if(getPotentiometerVoltage() > ARM_SWITCH_POSITION) {
			wristSpeed = 0;
		}
		//driveArmWrist(wristSpeed);
	}
    
    
//==Claw Code===================================================================================================== 
	public void grabCube() {
	    claw_intake_motor1.set(ControlMode.PercentOutput, CLAW_INTAKE_SPEED);
	}

	public void deployCube_Claw() {
	    claw_intake_motor1.set(ControlMode.PercentOutput, CLAW_DEPLOY_SPEED);
	    hasCube = false;
	}
	
	public void stopClaw() {
		claw_intake_motor1.set(ControlMode.PercentOutput, 0);
	}
	
	public double getIntakeRF() {
		return in_rangefinder.getAverageVoltage();
	}
	
	// TODO --  Fix this, it may falsely trigger when the cube is in
	// the intake, but oriented in a way we haven't grabbed it yet.
	public double getInRangeFinder() {
		return in_rangefinder.getAverageVoltage();
	}
	
	public void getArmCurrent() {
//		double top = bot_arm_rotation_motor.getOutputCurrent();
//		double bottom = top_arm_rotation_motor.getOutputCurrent();
//		System.out.println("ARM Currents: " + top + " " + bottom);
	}
	
	public boolean cubeIsIn() {
		if(in_rangefinder.getAverageVoltage()>2.0) {
			hasCube = true;
			return true;
		} else {
			return false;
		}
	}
	
	public boolean cubeIsClose() {
		if(in_rangefinder.getAverageVoltage()>.8) {
			return true;
		} else {
			return false;
		}
	}

	public void initDefaultCommand() {
        setDefaultCommand(new Default_Arm());
        
    }
	
	public void setArmEndGameTrue() {
		endGameStarted = true;
	}
}

