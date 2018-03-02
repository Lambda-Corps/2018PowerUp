package org.usfirst.frc.team1895.robot.subsystems;

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
	private static final double CLAW_INTAKE_SPEED = 0.4;
	private static final double CLAW_DEPLOY_SPEED = .7;

	
	private AnalogInput in_rangefinder;
	private AnalogInput potentiometer;
	
	// final variables for potentiometer
	private final double VOLTVAL_1ROT = 0.6438;
	private final double VOLTVAL_3QUARTERROT = 0.48285;
	private final double VOLTVAL_HALFROT = 0.3219;
	private final double VOLTVAL_QUARTERROT = 0.16095;
	
	//potentiometer variables
	private final double ARM_SPEED = 0.5;
    
	// Positional Variables to represent the arm positions for scale and switch
	// TODO -- Check my numbers here that I've remembered them correctly
	public static final int ARM_LOWER_SOFT_LIMIT = 1150;
	public static final int ARM_UPPER_SOFT_LIMIT = 16700;
	private static final int ARM_EXTENSION_LOWER_LIMIT = 3000;
	private static int ARM_EXTENSION_UPPER_LIMIT = 13000;
	
	public static final int ARM_LOWEST_POSITION = ARM_LOWER_SOFT_LIMIT;
	public static final int ARM_SWITCH_POSITION = 5600;
	public static final int ARM_SCALE_LOW_POSITION = 13000;
	public static final int ARM_SCALE_MID_POSITION = 13001;
	public static final int ARM_SCALE_HIGH_POSITION = 13002;
	public static final int ARM_CLIMB_POSITION = ARM_UPPER_SOFT_LIMIT;
	public static final int ARM_POSITIONAL_TOLERANCE = 750;
	
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
    		// Normalize the input, must be in a -1 <= x <= 1 range
    		armSpeed = normalizeMotorInput(armSpeed);
    		armSpeed = removeDeadZoneInput(armSpeed);
    		
    		// Square the inputs for smoother arm controls.
    		armSpeed = Math.copySign(armSpeed* armSpeed, armSpeed);
    	
    		// Check where we are with respect to the limits
	    	int armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
	    	//These variable control the angle at which the piston will extend
	    	if(armSpeed<0) {
	    		if(armEncoderValue>=ARM_UPPER_SOFT_LIMIT) {
	    			armSpeed = 0;
	    			System.out.println("stopped by upper limit");
	    		} 
	    	} else if(armSpeed>0){
	    		if(armEncoderValue<=ARM_LOWER_SOFT_LIMIT) {
	    			armSpeed = 0;
	    			System.out.println("stopped by lower limit");
	    		} 
	    	}
	    	
	    	// We only want to manage telescoping during the rounds, not during the endgame
	    	if(!endGameStarted) {
		    	if(armEncoderValue>ARM_EXTENSION_LOWER_LIMIT && 
		    	   armEncoderValue<ARM_EXTENSION_UPPER_LIMIT) {
		    		//led1.set(true);
		    		telescoping_solenoid.set(DoubleSolenoid.Value.kForward);  //retract
		    	}
		    	else {
		    		//led1.set(false);
		    		//telescoping_solenoid.set(DoubleSolenoid.Value.kReverse);
		    	}
	    	}
	    	bot_arm_rotation_motor.set(ControlMode.PercentOutput, armSpeed);
	    	
	    	//wrist_motor.set(ControlMode.PercentOutput, armSpeed);
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
	    	return accelValue;
    }
    
    public boolean setPosition(int armPosition) {
    		boolean bPosReturn = false;
    		
    		switch(armPosition) {
    		case ARM_LOWEST_POSITION:
    			bPosReturn = setPositionLowest();
    			break;
    		case ARM_CLIMB_POSITION:
    			bPosReturn = setPositionClimb();
    			break;
    		case ARM_SWITCH_POSITION:
    			bPosReturn = setPositionSwitch();
    			break;
    		case ARM_SCALE_HIGH_POSITION:
    			bPosReturn = setPositionScale(ARM_SCALE_HIGH_POSITION);
    			break;
    		case ARM_SCALE_MID_POSITION:
    			bPosReturn = setPositionScale(ARM_SCALE_MID_POSITION);
    			break;
    		case ARM_SCALE_LOW_POSITION:
    			bPosReturn = setPositionScale(ARM_SCALE_LOW_POSITION);
    			break;
    		default:
    			// Don't do anything, just return
    		}

	    	return bPosReturn;
    }
    
    private boolean setPositionScale(int armScaleHighPosition) {
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
				driveArm(-ARM_SPEED);
			}
			else {
				if( ARM_SCALE_HIGH_POSITION - currArmPosition >= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				driveArm(ARM_SPEED);
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
				driveArm(-ARM_SPEED);
			}
			else {
				if( ARM_SCALE_MID_POSITION - currArmPosition >= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				driveArm(ARM_SPEED);
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
				driveArm(-ARM_SPEED);
			}
			else {
				if( ARM_SCALE_LOW_POSITION - currArmPosition >= ARM_POSITIONAL_TOLERANCE) {
					// We are there
					driveArm(0);
					bReturnPos = true;
				}
				
				driveArm(ARM_SPEED);
			}
			break;
		}
		
	
		return bReturnPos;
	}

	private boolean setPositionSwitch() {
    		boolean bReturnPos = false;
    		// First get the current position in space
		int currArmPosition = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
		boolean armHigherThanPosition = (currArmPosition > ARM_SWITCH_POSITION);
		
		// First condition checks arm is above position, else checks for equal or below
		if(armHigherThanPosition) {
			if( currArmPosition - ARM_SWITCH_POSITION <= ARM_POSITIONAL_TOLERANCE) {
				// We are there
				driveArm(0);
				bReturnPos = true;
			}
			
			// We aren't there yet, drive the motor downward
			driveArm(-ARM_SPEED);
		}
		else {
			if( ARM_SWITCH_POSITION - currArmPosition >= ARM_POSITIONAL_TOLERANCE) {
				// We are there
				driveArm(0);
				bReturnPos = true;
			}
			
			driveArm(ARM_SPEED);
		}
		
		return bReturnPos;
	}

	// This method will be called by the outer wrapper setPosition
    // method.  It will drive the arm down to the lowest position
    // as defined above.
    private boolean setPositionLowest() {
    		// First get the current position in space
    		int currArmPosition = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
    		
    		// If we are within the tolerance, then stop
    		//
    		// If this wasn't the lowest position there would be two checks, one from
    		// tolerance + and one from tolerance -.  We are at the bottom so we just
    		// need to worry about tolerance -.  The tolerance is really only there 
    		// to compensate for the timing of the scheduler.  Should we move this to 
    		// be a PID controlled loop, we wouldn't need it.
    		if( currArmPosition - ARM_LOWEST_POSITION <= ARM_POSITIONAL_TOLERANCE) {
    			// We are there
    			driveArm(0);
    			return true;
    		} 
    		
    		// We aren't within the tolerance, so drive the arm down.
    		driveArm(-ARM_SPEED);
    		
    		return false;
	}
    
    // This method will be called by the outer wrapper setPosition
    // to bring the arm to the climbing position
    private boolean setPositionClimb() {
    		// First get the current position in space
		int currArmPosition = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
		
		// If we are within the tolerance, then stop
		//
		// If this wasn't the lowest position there would be two checks, one from
		// tolerance + and one from tolerance -.  We are at the bottom so we just
		// need to worry about tolerance -.  The tolerance is really only there 
		// to compensate for the timing of the scheduler.  Should we move this to 
		// be a PID controlled loop, we wouldn't need it.
		if( currArmPosition >= ARM_CLIMB_POSITION) {
			// are there
			driveArm(0);
			return true;
		} 
		
		// We aren't there yet, so drive the arm up
		driveArm(ARM_SPEED);
		
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
			//TODO set limits
			wrist_motor.set(ControlMode.PercentOutput, wristSpeed);
//			SmartDashboard.putNumber("wrist value", Robot.arm.getWristEncoder());
		}
	
		
//==Telescoping Arm Code===============================================================================================
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
	
    
//==Claw Code===================================================================================================== 
	public void grabCube() {
	    claw_intake_motor1.set(ControlMode.PercentOutput, CLAW_INTAKE_SPEED);
	}

	public void deployCube_Claw() {
	    claw_intake_motor1.set(ControlMode.PercentOutput, CLAW_DEPLOY_SPEED);
	}
	
	public void stopClaw() {
		claw_intake_motor1.set(ControlMode.PercentOutput, 0);
	}
	
	public double getIntakeRF() {
		return in_rangefinder.getAverageVoltage();
	}
	
	// TODO --  Fix this, it may falsely trigger when the cube is in
	// the intake, but oriented in a way we haven't grabbed it yet.
	public boolean cubeIsIn() {
		if(in_rangefinder.getAverageVoltage()>2.0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean cubeIsClose() {
		if(in_rangefinder.getAverageVoltage()>3.0) {
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

