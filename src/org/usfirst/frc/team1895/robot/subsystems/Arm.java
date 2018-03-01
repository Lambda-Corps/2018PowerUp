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
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
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
    
    private Encoder armUpEncoder;
    
    //private DigitalOutput led1;
    
    public final Accelerometer accel;
	public final BuiltInAccelerometer BIA;
    
    // pneumatics
    private final DoubleSolenoid telescoping_solenoid;
    
    // CLAW-RELATED COMPONENTS
    private TalonSRX claw_intake_motor1;
	private TalonSRX claw_intake_motor2;
	private static final double CLAW_SPEED = 0.4;
	
	private AnalogInput in_rangefinder;
	private AnalogInput potentiometer;
	
	// final variables for potentiometer
	private final double VOLTVAL_1ROT = 0.6438;
	private final double VOLTVAL_3QUARTERROT = 0.48285;
	private final double VOLTVAL_HALFROT = 0.3219;
	private final double VOLTVAL_QUARTERROT = 0.16095;
	
	//potentiometer variables
	private double pot_voltage;
	private double arm_speed = 0.4;
    
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
    	bot_arm_rotation_motor.setInverted(true);
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
		LiveWindow.add(this);
		LiveWindow.addChild(this, accel);

    	//pneumatics
    	telescoping_solenoid = new DoubleSolenoid(RobotMap.ARM_TELESCOPING_SOLENOID_A_PORT, RobotMap.ARM_TELESCOPING_SOLENOID_B_PORT);
    }
    
//==Arm Movement=======================================================================================================
    public void driveArm(double armSpeed) {
    	int armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
    	int armEncoderUpperLimit = 16000;
    	int armEncoderLowerLimit = 250;  //should be pos
    	//These variable control the angle at which the piston will extend
    	int upperArmLimit = 13000;  //test
    	int lowerArmLimit = 2000;  //test
//    	SmartDashboard.putNumber("arm encoder", armEncoderValue);
//    	SmartDashboard.putNumber("accelerometer z" , Robot.arm.getZValue());
//    	SmartDashboard.putNumber("accelerometer y" , Robot.arm.getYValue());
//    	SmartDashboard.putNumber("accelerometer x" , Robot.arm.getXValue());
//    	System.out.println("This is the encoder value2 "+ anglex);
    	if(armSpeed>0) {
    		if(armEncoderValue>=armEncoderUpperLimit) {
    			armSpeed = 0;
    			System.out.println("stopped by upper limit");
    		} 
    	} else if(armSpeed<0){
    		if(armEncoderValue<=armEncoderLowerLimit) {
    			armSpeed = 0;
    			System.out.println("stopped by lower limit");
    		} 
    	}
    	bot_arm_rotation_motor.set(ControlMode.PercentOutput, armSpeed);
    	System.out.println("arm speed: " + armSpeed + " encoder " + armEncoderValue);
    	if(armEncoderValue>lowerArmLimit && armEncoderValue<upperArmLimit) {
    		//led1.set(true);
    		telescoping_solenoid.set(DoubleSolenoid.Value.kForward);  //retract
    	}
    	else {
    		//led1.set(false);
    		//telescoping_solenoid.set(DoubleSolenoid.Value.kReverse);
    	}
    	//wrist_motor.set(ControlMode.PercentOutput, armSpeed);
    	//System.out.println(wrist_motor.getSensorCollection().getQuadraturePosition());
    ///	System.out.println(String.format("Arm Encoder:  %5d     Arm Speed:   %6.2f ",armEncoderValue, armSpeed));
    	//System.out.println("Arm Encoder: " + armEncoderValue + "  Arm Speed;" + armSpeed);
    	getPotentiometerVoltage();
    }
    
    public double getAccelValue() {
    	double accelValue = accel.getX();
    	return accelValue;
    }
    
    public boolean setPosition(double goalangle) {
    	double accelValue = accel.getX();
    	double tolerance = 200; //encoder
    	//0<angle<180
    	//Error checking
    	if(goalangle < 0) {
    		goalangle = 0;
    	}
    	
    	if(goalangle > 180) {
    		goalangle = 180;
    	}
    	
    	//convert angle to 0-14000
    	goalangle = goalangle*(14000/180);
    	
    	double armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
    	System.out.printf("encoder value %5.1f   accel  %5.1f", armEncoderValue, accelValue);
    	
    	if(Math.abs(armEncoderValue-goalangle)<tolerance) {
    		driveArm(0);
    		return true;
    	} else if(armEncoderValue < goalangle){
    		driveArm(-.25);
    		System.out.println("driving arm negative");
    	}
    	else if(armEncoderValue > goalangle) {
    		driveArm(.25);
    		System.out.println("driving arm positive");
    	}
    	return false;
    }
    
    public void stopClawIntake() {
    	claw_intake_motor1.set(ControlMode.PercentOutput, 0);
    }
    
	public void calibrate(){
		return;
	}
	
	public boolean findZero() {
		System.out.println("encoder value: " + getArmEncoder());
		System.out.println("potentiometer value: " + getPotentiometerVoltage());
		System.out.println("accel z " + getZValue());
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

		public void driveArmWrist(double wristSpeed) {
			//TODO set limits
			wrist_motor.set(ControlMode.PercentOutput, wristSpeed);
//			SmartDashboard.putNumber("wrist value", Robot.arm.getWristEncoder());
		}
	
		
//==Telescoping Arm Code===============================================================================================
	public void extendTelescopingArm() {
    	int armEncoderValue = bot_arm_rotation_motor.getSensorCollection().getQuadraturePosition();
    	//These variable control the angle at which the piston will extend
    	int upperArmLimit = 13000;  //test
    	int lowerArmLimit = 2000;  //test
    	if(armEncoderValue>lowerArmLimit && armEncoderValue<upperArmLimit) {
    		telescoping_solenoid.set(DoubleSolenoid.Value.kForward);  //retract
    	} else {
    		telescoping_solenoid.set(DoubleSolenoid.Value.kReverse);  //extend
    	}
	}
	
	public void retractTelescopingArm() {
		telescoping_solenoid.set(DoubleSolenoid.Value.kForward);
	}
 
//==Potentiometer Code=========================================================================================================
	public double getPotentiometerVoltage() {
		double voltage = potentiometer.getVoltage();
		//System.out.println("Potentiometer voltage " + voltage);
//		SmartDashboard.putNumber("potentiometer", voltage);
		return pot_voltage;
	}
	
	public boolean rotateToUpperScale() {
		boolean done = false;
		pot_voltage = potentiometer.getVoltage();
		if(pot_voltage < VOLTVAL_1ROT) {
			driveArm(arm_speed);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	
	public boolean rotateToMidScale() {
		boolean done = false;
		pot_voltage = potentiometer.getVoltage();
		if(pot_voltage < VOLTVAL_3QUARTERROT) {
			driveArm(arm_speed);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	 
	public boolean rotateToLowerScale() {
		boolean done = false;
		pot_voltage = potentiometer.getVoltage();
		if(pot_voltage < VOLTVAL_HALFROT) {
			driveArm(arm_speed);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	
	public boolean rotateToSwitch() {
		boolean done = false;
		pot_voltage = potentiometer.getVoltage();
		if(pot_voltage < VOLTVAL_QUARTERROT) {
			driveArm(arm_speed);
		} else {
			done = true;
			driveArm(0);
		}
		return done;
	}
	
//==Wrist Code=========================================================================================================
	
    
//==Claw Code===================================================================================================== 
	public void grabCube_Claw() {
		double velocity = CLAW_SPEED;
	    if (velocity > 1.0) velocity = 1.0;
	    if (velocity <-1.0) velocity = -1.0;
	    claw_intake_motor1.set(ControlMode.PercentOutput, velocity);
	}

	public void deployCube_Claw() {
		double velocity = -1;
	    if (velocity > 1.0) velocity = 1.0;
	    if (velocity <-1.0) velocity = -1.0;
	    claw_intake_motor1.set(ControlMode.PercentOutput, velocity);
	}
	
	public void stopClaw() {
		claw_intake_motor1.set(ControlMode.PercentOutput, 0);
	}
	
	public boolean cubeIsIn() {
		System.out.println(in_rangefinder.getAverageVoltage());
		if(in_rangefinder.getAverageVoltage()>2.0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean cubeIsClose() {
		System.out.println(in_rangefinder.getAverageVoltage());
		if(in_rangefinder.getAverageVoltage()>0.8) {
			return true;
		} else {
			return false;
		}
	}

	public void initDefaultCommand() {
        setDefaultCommand(new Default_Arm());
        
    }
}

