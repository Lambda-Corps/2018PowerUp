package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.Default_Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogGyro;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Function: Drives around. Can gear shift into high and low gear. I am also
 * including the Compressor here. Motors: 2 pistons (transmission), 4 motors
 * (wheels) Sensors: Encoders, gyro, rangefinders (4) Last Updated: 1/13/2018 by
 * Maddy
 */

public class Drivetrain extends Subsystem {

	boolean isCorrecting = false; //

	// motors CAN ID #
	private TalonSRX left_dt_motor1; // 1
	private TalonSRX left_dt_motor2; // 2
	private TalonSRX right_dt_motor1; // 4
	private TalonSRX right_dt_motor2; // 5

	// pneumatics
	private final Compressor compressor;
	// private final DoubleSolenoid transmission_solenoid;

	// Gyro
	private AnalogGyro gyro;
	private AHRS ahrs;

	// digital IO sensors
	private Encoder l_encoder;
	private Encoder r_encoder;

	// analog sensors
	private AnalogInput fr_rangefinder;
	private AnalogInput l_rangefinder;
	private AnalogInput r_rangefinder;
	private AnalogInput in_rangefinder;
	private AnalogInput accelerometer;

	// PID
	private MyPIDOutput myPIDOutputDriving;
	private MyPIDOutput myPIDOutputTurning;
	private PIDController pidControllerDriving;
	private PIDController pidControllerTurning;

	final double pGainDriv = 0, iGainDriv = 0, dGainDriv = 0;
	
	final double pGainTurn = 0, iGainTurn = 0, dGainTurn = 0;
	
	boolean pid_done = false;

	public static final int LEFT_MOTOR_ENCODER = 0;
	public static final int RIGHT_MOTOR_ENCODER = 1;
	
	public Drivetrain() {
		// motors
		left_dt_motor1 = new TalonSRX(RobotMap.LEFT_DT_MOTOR1_PORT);
		left_dt_motor2 = new TalonSRX(RobotMap.LEFT_DT_MOTOR2_PORT);
		right_dt_motor1 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR1_PORT);
		right_dt_motor2 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR2_PORT);

		left_dt_motor2.follow(left_dt_motor1);
		right_dt_motor2.follow(right_dt_motor1);

		// current limited to 10 amps when current is >15amps for 100 milliseconds
		// left_dt_motor1.configContinuousCurrentLimit(10, 0);
		// left_dt_motor1.configPeakCurrentLimit(15, 0);
		// left_dt_motor1.configPeakCurrentDuration(100, 0);
		// left_dt_motor1.enableCurrentLimit(true);

		// pneumatics
		compressor = new Compressor();
		// transmission_solenoid = new
		// DoubleSolenoid(RobotMap.DRIVETRAIN_SOLENOID_A_PORT,
		// RobotMap.DRIVETRAIN_SOLENOID_B_PORT);

		// digital IO sensors
		l_encoder = new Encoder(RobotMap.LEFT_ENCODER_A_PORT, RobotMap.LEFT_ENCODER_B_PORT, true);
		r_encoder = new Encoder(RobotMap.RIGHT_ENCODER_A_PORT, RobotMap.RIGHT_ENCODER_B_PORT, true); // third negates
																										// encoder
		l_encoder.setDistancePerPulse(.022);
		r_encoder.setDistancePerPulse(.022);

		// analog sensors
		try {
			/* Communicate w/navX-MXP via the MXP SPI Bus. */
			/*
			 * Alternatively: I2C.Port.kMXP, SerialPort.Port.kMXP or SerialPort.Port.kUSB
			 */
			/*
			 * See http://navx-mxp.kauailabs.com/guidance/selecting-an-interface/ for
			 * details.
			 */
			ahrs = new AHRS(SPI.Port.kMXP);
		} catch (RuntimeException ex) {
			DriverStation.reportError("Error instantiating navX-MXP: " + ex.getMessage(), true);
		}

		// rangefinders
		fr_rangefinder = new AnalogInput(RobotMap.FRONT_RANGEFINDER_PORT);
		l_rangefinder = new AnalogInput(RobotMap.LEFT_RANGEFINDER_PORT);
		r_rangefinder = new AnalogInput(RobotMap.RIGHT_RANGEFINDER_PORT);
		in_rangefinder = new AnalogInput(RobotMap.INTAKE_RANGEFINDER_PORT);

		// accelerometer
		accelerometer = new AnalogInput(RobotMap.ACCELEROMETER_PORT);

	}

	public void setState(boolean s) {
		isCorrecting = s;
	}

	public boolean getState() {
		return isCorrecting;
	}

//==Manual driving============================================================================
	public void arcadeDrive(double trans_speed, double yaw) { // 0, 0.3

		setAHRSAdjustment(0);
		//trans_speed *= -1;// Fixes Direction if you use Steamworks robot
		yaw *= 1;

		double left_speed = trans_speed + yaw;
		double right_speed = yaw - trans_speed;

		double angle;
		double tolerance = 1.0;
		double scalar = 0.75; // Correlate it with the trans_speed

		double max_speed = Math.max(Math.abs(left_speed), Math.abs(right_speed));

		double direction = 0;
		if (trans_speed < 0) {// Forwards
			direction = 1;
		} else {
			direction = -1;
		}
		angle = Robot.drivetrain.getAHRSGyroAngle();

		if (Math.abs(max_speed) > 1.0) {
			left_speed /= max_speed;
			right_speed /= max_speed;
		}

		if (Math.abs(yaw) < 0.005) { // if no x input --> correcting mode
			if (isCorrecting == false) { // if correcting mode is not already in progress
				isCorrecting = true; // set flag
				Robot.drivetrain.resetAHRSGyro(); // reset gyro
			}
			if (direction > 0) {

				if (angle < (-tolerance)) { // if drifting left
					right_speed *= scalar; // go right
				} else if (angle > (tolerance)) { // if drifting right
					left_speed *= scalar; // go left
				} else {
					// don't modify speed
				}
			} else {

				if (angle < (-tolerance)) { // if drifting left
					left_speed *= scalar; // go right
				} else if (angle > (tolerance)) { // if drifting right
					right_speed *= scalar; // go left
				} else {
					// don't modify speed
				}
			}
		} else { // has x input --> normal mode
			isCorrecting = false;
		}

		// motorgroup stuffs - CHANGED TO LEADER FOLLOWER TESTING
		left_dt_motor1.set(ControlMode.PercentOutput, left_speed);
		right_dt_motor1.set(ControlMode.PercentOutput, right_speed);
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

//==Driving straight code and encoder code==========================================================================
	public void resetEncoders() {
		l_encoder.reset();
		r_encoder.reset();
	}

	public boolean driveStraightSetDistance(double speed, double targetDis) {
		boolean targetReached = false;

		double leftDis = l_encoder.getDistance();
		double rightDis = r_encoder.getDistance();

		double error = leftDis - rightDis; // if L>R ie needs to go left --> positive and vice versa
		double toleranceDis = .1; // inches

		// TODO -- Fix me, why aren't we just arcade driving all the time? Is there some
		// need or benefit to tank driving?
		if (targetDis > 0) {// moving forward code
			// Checking if we still have to keep driving or not. If we haven't reached our
			// target distance, keep driving straight. Otherwise, stop.
			if ((targetDis > leftDis) && (targetDis > rightDis)) {

				arcadeDrive(speed, 0);
			} else {
				arcadeDrive(0, 0);
				targetReached = true;
			}
		} else { // moving backwards code
			if ((targetDis < leftDis) && (targetDis < rightDis)) {
				arcadeDrive(-speed, 0);
			} else {
				arcadeDrive(0, 0);
				targetReached = true;
			}
		}
		return targetReached;
	}

//==Rangefinder Code==========================================================================================================
	public boolean drivefr_RFDistance(double goaldistance, double speed) { // TODO: do we need separate methods??? this
																			// only does front RF
		double left_speed = speed;
		double right_speed = speed;
		if (fr_rangefinderDist() <= (goaldistance)) { // if the robot crossed the goal distance + buffer then the code
														// will stop
			tankDrive(0, 0);
			return true;
		} else { // if it hasn't crossed it will run at a determined speed
			tankDrive(left_speed, right_speed);
			return false;
		}
	}

	public double fr_rangefinderDist() { // TODO: check that this is right / T U N E. also check if battery affects
											// output
		double voltage = fr_rangefinder.getAverageVoltage();
		double inches = 32.1328 * voltage + 17.581; // from LinReg
		return inches;
	}

	public double l_rangefinderDist() { // TODO: check that this is right / T U N E. also check if battery affects
										// output
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

	public double r_rangefinderDist() { // TODO: check that this is right / T U N E. also check if battery affects
										// output
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

	public double in_rangefinderDist() { // TODO: check that this is right / T U N E. also check if battery affects
											// output
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
		if (onLeft) {
			fromWall = l_rangefinderDist();
		} else {
			fromWall = r_rangefinderDist();
		}

		double currentDistance = l_encoder.getDistance() + r_encoder.getDistance();

		double goalTolerance = 1.0; // inches
		double bufferTolerance = 1.0;

		// Checking if we still have to keep driving or not. If we haven't reached our
		// target distance, keep driving along the wall. Otherwise, stop.
		if (Math.abs(currentDistance - goalDistance) > goalTolerance) { // if not yet at target
			if (onLeft) { // wall is on left of robot
				if (fromWall < buffer - bufferTolerance) { // drifting left (toward wall), needs to go right
					tankDrive(speed, speed * 0.75);
				} else if (fromWall > buffer + bufferTolerance) { // drifting right (away from wall), needs to go left
					tankDrive(speed * 0.75, speed);
				} else {
					tankDrive(speed, speed);
				}
			} else { // wall is on right of robot
				if (fromWall > buffer + bufferTolerance) { // drifting left (away from wall), needs to go right
					tankDrive(speed, speed * 0.75);
				} else if (fromWall < buffer - bufferTolerance) { // drifting right (toward wall), needs to go left
					tankDrive(speed * 0.75, speed);
				} else {
					tankDrive(speed, speed);
				}
			}
		} else { // has reached target -- stop
			arcadeDrive(0, 0);
			done = true;
		}
		return done;
	}

//==Accelerometer Code==========================================================================
	public double getAccelerometer() {
		return accelerometer.getValue(); // TODO: is this the right method?
	}

	public void resetAccelerometer() {
		accelerometer.resetAccumulator();
	}

//==Gyro Code====================================================================================
	public double getAHRSGyroAngle() {
		return ahrs.getAngle();
	}
	
	public void resetAHRSGyro() {
		ahrs.reset();
	}

	public void setAHRSAdjustment(double adj) {
		ahrs.setAngleAdjustment(adj);
	}

//==PID Related Driving Code===============================================================================================
	public void setPIDSetpoints(double setpoint) {
		l_encoder.reset();
		r_encoder.reset();
		pidControllerDriving.setSetpoint(setpoint);
		pidControllerDriving.enable();
	}

	public boolean driveStraightWithPID(double desiredMoveDistance) {
		double speedfactor = 0.1; // This is the "P" factor to scale the error between encoders values to the
								  // motor drive bias
		double maxErrorValue = 0.0; // Limits the control the error has on driving
		double error = speedfactor * (l_encoder.getDistance() - r_encoder.getDistance());
		if (error >= maxErrorValue)
			error = maxErrorValue;
		if (error <= -maxErrorValue)
			error = -maxErrorValue;

		pidControllerDriving.setAbsoluteTolerance(1);

		double pidOutput = myPIDOutputDriving.get();
		if (Double.isNaN(pidOutput)) {
			System.out.println("Got invalid PID output for driving");
		} else {
			arcadeDrive(0.8 * (myPIDOutputDriving.get()), error);  //note 0.8 scalar
		}
		
		pid_done = pidControllerDriving.onTarget();
		
		if (pid_done) {
			pidControllerDriving.disable();
		}
		return pid_done;
	}

	public void setUpPIDTurning(double angle) {
		resetAHRSGyro();
		pidControllerTurning.setSetpoint(angle);
		pidControllerTurning.enable();
	}

	public boolean turnWithPID(double desiredTurnAngle) {

		pidControllerTurning.setAbsoluteTolerance(1.0);

		double pidOutput = myPIDOutputTurning.get();
		
		if (Double.isNaN(pidOutput)) {
			System.out.println("Got invalid output from Turn PID Controller");
		} else {
			arcadeDrive(0.0, pidOutput);
		}

		pid_done = pidControllerTurning.onTarget();

		if (pid_done) {
			pidControllerTurning.disable();
		}

		return pid_done;
	}
//==Default Command==========================================================================
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Default_Drivetrain());
	}

	/*
	 * This method will return the value from the encoder specified in the argument.
	 * If the encoder you are looking for does not have an entry here, simply add
	 * it to the code up above and then add a section to the switch statement.
	 */
	public double getEncoderValue(int encoderPort) {
		double dRet = 0.0;
		switch(encoderPort){
		case LEFT_MOTOR_ENCODER:
			dRet = l_encoder.getDistance();
			break;
		case RIGHT_MOTOR_ENCODER:
			dRet = r_encoder.getDistance();
			break;
		default:
			break;
		}
		
		return dRet;
	}
	
}
