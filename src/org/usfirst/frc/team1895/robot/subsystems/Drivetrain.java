package org.usfirst.frc.team1895.robot.subsystems;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.RobotMap;
import org.usfirst.frc.team1895.robot.commands.drivetrain.Default_Drivetrain;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
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
	
	public static boolean amCorrecting;

	// motors CAN ID #
	private TalonSRX left_dt_motor1; // 1
	private TalonSRX left_dt_motor2; // 2
	private TalonSRX right_dt_motor1; // 4
	private TalonSRX right_dt_motor2; // 5

	// pneumatics
	private final Compressor compressor;
	private final DoubleSolenoid transmission_solenoid;

	// Gyro
	private AHRS ahrs;

	// digital IO sensors
	private Encoder l_encoder;
	private Encoder r_encoder;

	// analog sensors
	private AnalogInput fr_rangefinder;
	private AnalogInput l_rangefinder;
	private AnalogInput r_rangefinder;
	private AnalogInput in_rangefinder;

	// TODO - We need to determine which accelerometer we are going to use and
	// change
	// variable type here.
	private AnalogInput accelerometer;

	// PID
	private MyPIDOutput myPIDOutputDriving;
	private MyPIDOutput myPIDOutputTurning;
	private PIDController pidControllerDriving;
	private PIDController pidControllerTurning;

	final double pGainDriv = 0, iGainDriv = 0, dGainDriv = 0;

	final double pGainTurn = 0, iGainTurn = 0, dGainTurn = 0;

	boolean pid_done = false;

	// These values are used as an enumeration to get encoder values
	// Values are not important, they are only required to be unique.
	public static final int LEFT_MOTOR_ENCODER = 0;
	public static final int RIGHT_MOTOR_ENCODER = 1;
	public static final int ARM_MOTOR_ENCODER = 2;

	// gear-shifting
	int highgear_count = 0;
	int highgear_count_test = 0;
	int lowgear_count = 0;
	boolean inHigh = false;
	DigitalOutput redLED; // 7
	DigitalOutput yellowLED; // 8
	DigitalOutput greenLED; // 9

	public Drivetrain() {
		// motors
		left_dt_motor1 = new TalonSRX(RobotMap.LEFT_DT_MOTOR1_PORT);
		left_dt_motor1.setInverted(true);
		left_dt_motor2 = new TalonSRX(RobotMap.LEFT_DT_MOTOR2_PORT);
		left_dt_motor2.setInverted(true);
		right_dt_motor1 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR1_PORT);
		right_dt_motor1.setInverted(true);
		right_dt_motor2 = new TalonSRX(RobotMap.RIGHT_DT_MOTOR2_PORT);
		right_dt_motor2.setInverted(true);
		left_dt_motor2.follow(left_dt_motor1);
		right_dt_motor2.follow(right_dt_motor1);

		// TODO -- Test this code so we can uncomment if it works.
		// current limited to 10 amps when current is >15amps for 100 milliseconds
		// left_dt_motor1.configContinuousCurrentLimit(10, 0);
		// left_dt_motor1.configPeakCurrentLimit(15, 0);
		// left_dt_motor1.configPeakCurrentDuration(100, 0);
		// left_dt_motor1.enableCurrentLimit(true);

		// pneumatics
		compressor = new Compressor();

		transmission_solenoid = new DoubleSolenoid(RobotMap.DRIVETRAIN_SOLENOID_A_PORT,
				RobotMap.DRIVETRAIN_SOLENOID_B_PORT);

		// digital IO sensors
		l_encoder = new Encoder(RobotMap.LEFT_ENCODER_A_PORT, RobotMap.LEFT_ENCODER_B_PORT, true);
		r_encoder = new Encoder(RobotMap.RIGHT_ENCODER_A_PORT, RobotMap.RIGHT_ENCODER_B_PORT, true);

		l_encoder.setDistancePerPulse(.0159); // PowerUp
		r_encoder.setDistancePerPulse(.0159); // PowerUp
		// l_encoder.setDistancePerPulse(0.0225); //Steamworks
		// r_encoder.setDistancePerPulse(0.0225); //Steamworks

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
		// in_rangefinder = new AnalogInput(RobotMap.INTAKE_RANGEFINDER_PORT);

		// accelerometer
		// TODO -- After we figure out the type, fix this constructor
		// accelerometer = new AnalogInput(RobotMap.ACCELEROMETER_PORT);

		myPIDOutputDriving = new MyPIDOutput();
		myPIDOutputTurning = new MyPIDOutput();
		pidControllerDriving = new PIDController(pGainDriv, iGainTurn, dGainDriv, l_encoder, myPIDOutputDriving); // Input
																													// //
																													// output
		pidControllerTurning = new PIDController(pGainTurn, iGainTurn, dGainTurn, ahrs, myPIDOutputTurning);

		greenLED = new DigitalOutput(RobotMap.GEAR_LED_PORT);

	}

	// ==Manual
	// driving============================================================================
	public void arcadeDrive(double trans_speed, double yaw) {

		setAHRSAdjustment(0);

		double left_speed = trans_speed + yaw;
		double right_speed = yaw - trans_speed;

		// These three variables are for self correcting driving. The angle is the
		// heading
		// from the gyro, the tolerance is how far we deviate (in degrees) before we
		// self correct, and the scalar is the percentage we use to reduce the over
		// corrected side.
		double tolerance = 0.1;
		double scalar = 0.75; // Correlate it with the trans_speed

		// This is to normalize the speed inputs. The Talons require a speed of -1 > x >
		// 1.
		double max_speed = Math.max(Math.abs(left_speed), Math.abs(right_speed));

		if (Math.abs(max_speed) > 1.0) {
			left_speed /= max_speed;
			right_speed /= max_speed;
		}
		
		if (Math.abs(yaw) < 0.005) { // if no x input --> correcting mode
			if(!amCorrecting) {
				amCorrecting = true;
				resetEncoders();
			}
			double l_distance = l_encoder.getDistance();
			double r_distance = r_encoder.getDistance();
			System.out.println(Math.abs(l_distance-r_distance));
			if (Math.abs(l_distance - r_distance) < tolerance) {
				// already straight
				System.out.println("im good");
			} else {
				// forward
				if (trans_speed > 0) {
					System.out.println("fwd");
					// determine whether drifting left or right
					if (l_distance > r_distance) { // drifting right, need to go left
						System.out.println("drifting right");
						//left_speed *= scalar; // go left
					} else { // drifting left, need to go right
						System.out.println("drifting left");
						//right_speed *= scalar; // go right
					}
				} else { // backward
					System.out.println("bwd");
					// determine whether drifting left or right
					if (l_distance > r_distance) { // drifting right, need to go left
						System.out.println("drifting left");
						//right_speed *= scalar; // go left
					} else { // drifting left, need to go right
						System.out.println("drifting right");
						//left_speed *= scalar; // go right
					}
				}
			}
		} else {
			amCorrecting = false;
		}
		// The .985 scalar value was determined through testing that our left motors
		// were outputting more power than the right.
		
		if(trans_speed>0) {  //forward
			left_dt_motor1.set(ControlMode.PercentOutput, left_speed * .985); // .978 -- .95
			right_dt_motor1.set(ControlMode.PercentOutput, right_speed);
		} else {
			left_dt_motor1.set(ControlMode.PercentOutput, left_speed);
			right_dt_motor1.set(ControlMode.PercentOutput, right_speed * 0.972);
		}


		shiftGears();
		// System.out.println("Am I in high gear? " + inHigh);
		// if(highgear_count % 33 == 0) {
		// System.out.println("LE: " + l_encoder.getDistance() + "RE: " +
		// r_encoder.getDistance());
		// }
		// if(highgear_count % 100 == 0) {
		// System.out.println("Am I in high gear? " + inHigh);
		// System.out.println("LE: " + l_encoder.getDistance() + "RE: " +
		// r_encoder.getDistance());
		// }
	}

	public void shiftGears() {
		// add something so that it doesn't shift gears in autonomous

		double current_speed = Math.max(Math.abs(l_encoder.getRate()), Math.abs(r_encoder.getRate()));

		// max speed to be in low gear is 4.71ft/sec (56.52 inches/sec), max high gear
		// is 12.47 ft/sec
		// unit should be inches per second
		double downshift_speed = 56.52;

		// if in high gear..
		if (inHigh) {
			// ..and you dropped below the minimum high speed, switch to low gear
			if (current_speed < downshift_speed) {
				transmission_solenoid.set(DoubleSolenoid.Value.kReverse);
				inHigh = false;
			}
		}
		// if in low gear..
		else {
			// ..and you went above the max low gear speed..
			if (current_speed > downshift_speed) {
				highgear_count++;

				// ..after ~ 1.5 seconds, shift into high gear
				if (highgear_count == 100) {
					transmission_solenoid.set(DoubleSolenoid.Value.kForward);
					inHigh = true;
					highgear_count = 0;
				}
			}
		}

		// update LED
		if (inHigh) {
			greenLED.set(true); // led on = high gear
		} else {
			greenLED.set(false); // led off = low gear
		}

	}

	// for ShiftGearsTestStartLG command
	public boolean testStartLG() {
		boolean done = false;
		if (!inHigh) {
			arcadeDrive(0.5, 0);
			lowgear_count++;
			if (lowgear_count % 100 == 0) {
				done = true;
				lowgear_count = 0;
			}
		} else {
			System.out.println("started in high gear, test failed");
		}
		return done;
	}

	// for ShiftGearsShiftHighGear command
	public boolean driveUntilHighGear() {
		boolean highWasReached = false;
		if (!inHigh) {
			arcadeDrive(1, 0);
			System.out.println("trying to shift to high gear");
		}
		// if you are in high gear, and about 3ish seconds have passed, slow down
		else {
			arcadeDrive(1, 0);
			highgear_count_test++;
			if (highgear_count_test % 40 == 0) {
				System.out.println("High gear has been reached");
			}
			System.out.println("High gear has been reached");
			if (highgear_count_test % 190 == 0) {
				highWasReached = true;
			}
		}
		return highWasReached;
	}

	// for ShiftGearsShiftHighGear command
	public boolean shiftToLG() {
		boolean lowWasReached = false;
		if (inHigh) {
			arcadeDrive(0.5, 0);
			System.out.println("trying to shift to low gear");
		} else {
			arcadeDrive(0.5, 0);
			if (lowgear_count % 40 == 0) {
				System.out.println("low gear has been reached");
			}
			lowgear_count++;
			if (lowgear_count % 190 == 0) {
				lowWasReached = true;
			}
		}
		return lowWasReached;
	}

	// ==Driving straight code and encoder
	// code==========================================================================
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

	/*
	 * This method will return the value from the encoder specified in the argument.
	 * If the encoder you are looking for does not have an entry here, simply add it
	 * to the code up above and then add a section to the switch statement.
	 */
	public double getEncoderValue(int encoderPort) {
		double dRet = 0.0;
		switch (encoderPort) {
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

	public boolean driveStraightSetDistance(double speed, double targetDis) {
		boolean targetReached = false;

		double leftDis = l_encoder.getDistance();
		double rightDis = r_encoder.getDistance();

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
	// ==Rangefinder
	// Code==========================================================================================================

	public boolean drivefr_RFDistance(double goaldistance, double speed) { // TODO: do we need separate methods??? this
		// only does front RF
		System.out.printf("rangefinder: %5.1f \n", fr_rangefinderDist());
		if (fr_rangefinderDist() <= (goaldistance)) { // if the robot crossed the goal distance + buffer then the code
			// will stop
			arcadeDrive(0, 0);
			System.out.printf("rangefinder distance achieved: %5.1f \n", fr_rangefinderDist());
			System.out.println(" for goal of " + goaldistance);

			return true;
		} else { // if it hasn't crossed it will run at a determined speed
			arcadeDrive(speed, 0);
			return false;
		}
	}

	public double fr_rangefinderDist() {
		double voltage = fr_rangefinder.getAverageVoltage();
		double inches = 40 * voltage; // from LinReg
		return inches;
	}

	public double l_rangefinderDist() { // TODO: check that this is right / T U N E. also check if battery affects
		// output
		// double outputValue = l_rangefinder.getAverageVoltage();
		// if (outputValue > 2.4 || outputValue < 0.4) { // code currently only
		//// accurate from 0.4-2.4
		//// volts
		// return -1;
		//// TODO: Add code to handle that -1 so the robot can act accordingly
		// }
		// double voltage = Math.pow(outputValue, -1.16);
		// double coefficient = 10.298;
		// double d = voltage * coefficient;
		// return d;
		double voltage = l_rangefinder.getAverageVoltage();
		double inches = 40 * voltage; // from LinReg
		return inches;
	}

	public double r_rangefinderDist() { // TODO: check that this is right / T U N E. also check if battery affects
		// output
		// double outputValue = r_rangefinder.getAverageVoltage();
		// if (outputValue > 2.4 || outputValue < 0.4) { // code currently only
		//// accurate from 0.4-2.4
		//// volts
		// return -1;
		//// TODO: Add code to handle that -1 so the robot can act accordingly
		// }
		// double voltage = Math.pow(outputValue, -1.16);
		// double coefficient = 10.298;
		// double d = voltage * coefficient;
		// return d;

		double voltage = r_rangefinder.getAverageVoltage();
		double inches = 40 * voltage; // from LinReg
		return inches;
	}

	public double in_rangefinderDist() { // TODO: check that this is right / T U N E. also check if battery affects
		// output
		double outputValue = in_rangefinder.getAverageVoltage();
		return outputValue;
	}

	// TODO -- This has not been tested, needs to be done.
	// Currently all the YAW values are 0, these need to be determined through
	// testing
	public boolean driveParallel(double speed, double buffer, double goalDistance, boolean onLeft) {
		boolean done = false;

		double fromWall;

		if (onLeft) {
			fromWall = l_rangefinderDist();
		} else {
			fromWall = r_rangefinderDist();
		}
		// System.out.println("distance; "+ fromWall);
		double currentDistance = l_encoder.getDistance() + r_encoder.getDistance();

		double goalTolerance = 1.0; // inches
		double bufferTolerance = 1.0;

		// Checking if we still have to keep driving or not. If we haven't reached our
		// target distance, keep driving along the wall. Otherwise, stop.

		// Feb 10, 2018, Kevin and Mr Frederick testing and revising

		if (Math.abs(currentDistance - goalDistance) > goalTolerance) { // if not yet at target
			if (onLeft) { // wall is on left of robot
				if (fromWall < buffer - bufferTolerance) { // drifting left (toward wall), needs to go right
					arcadeDrive(speed, 0.1);
					System.out.printf("[wall is on left of robot] drifting left, toward wall -- correcting %5.1f \n",
							fromWall);
				} else if (fromWall > buffer + bufferTolerance) { // drifting right (away from wall), needs to go left
					arcadeDrive(speed, -0.1);
					System.out.printf(
							"[wall is on left of robot] drifting right, away from wall -- correcting  %5.1f \n",
							fromWall);
				} else {
					arcadeDrive(speed, 0);
					System.out.printf("[wall is on left of robot] I am already parallel %5.1f \n", fromWall);
				}
			} else { // wall is on right of robot
				if (fromWall > buffer + bufferTolerance) { // drifting left (away from wall), needs to go right
					arcadeDrive(speed, 0.1);
					System.out.printf(
							"[wall is on right of robot] drifting left, away from wall -- correcting %5.1f \n",
							fromWall);
				} else if (fromWall < buffer - bufferTolerance) { // drifting right (toward wall), needs to go left
					arcadeDrive(speed, -0.1);
					System.out.printf("[wall is on right of robot] drifting right, toward wall -- correcting  %5.1f \n",
							fromWall);
				} else {
					arcadeDrive(speed, 0);
					System.out.printf("[wall is on right of robot] I am already parallel %5.1f \n", fromWall);
				}
			}
		} else { // has reached target -- stop
			arcadeDrive(0, 0);
			done = true;
		}
		return done;
	}

	// ==Accelerometer
	// Code==========================================================================
	public double getAccelerometerValue() {
		return accelerometer.getValue(); // TODO: is this the right method?
	}

	public void resetAccelerometer() {
		accelerometer.resetAccumulator();
	}

	// ==Gyro
	// Code====================================================================================
	public double getAHRSGyroAngle() {
		return ahrs.getAngle();
	}

	public void resetAHRSGyro() {
		ahrs.reset();
	}

	public void setAHRSAdjustment(double adj) {
		ahrs.setAngleAdjustment(adj);
	}

	// ==PID Related Driving
	// Code===============================================================================================
	public void makeNewPidDriving(double p, double i, double d) {
		myPIDOutputDriving = new MyPIDOutput();
		pidControllerDriving = new PIDController(p, i, d, l_encoder, myPIDOutputDriving);
	}

	public void makeNewPidTurning(double p, double i, double d) {
		myPIDOutputTurning = new MyPIDOutput();
		pidControllerTurning = new PIDController(p, i, d, ahrs, myPIDOutputTurning);
	}

	public void setDrivingPIDSetpoints(double setpoint) {
		l_encoder.reset();
		r_encoder.reset();
		pidControllerDriving.setSetpoint(setpoint);
		pidControllerDriving.enable();
	}

	public void setTurningPIDSetpoints(double setpoint) {
		ahrs.reset();
		pidControllerTurning.setSetpoint(setpoint);
		pidControllerTurning.enable();
	}

	// TODO -- This is last year's code, with the self correcting and updated arcade
	// we may not needs any
	// speedfactors or scaling here. Must be tested, and confirmed.
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

		System.out.println("l_encoder.getDistance() " + l_encoder.getDistance() + " target " + desiredMoveDistance);

		double pidOutput = myPIDOutputDriving.get();
		if (Double.isNaN(pidOutput)) {
			System.out.println("Got invalid PID output for driving");
		} else {
			arcadeDrive(0.8 * pidOutput, error); // note 0.8 scalar
			System.out.println("trying to drive " + pidOutput);
		}

		pid_done = pidControllerDriving.onTarget();
		System.out.println(pid_done);

		if (pid_done) {
			pidControllerDriving.disable();
		}
		return pid_done;
	}

	public void setTurningPIDSetPoint(double angle) {
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
			System.out.println("trying to drive " + pidOutput);
		}

		pid_done = pidControllerTurning.onTarget();

		if (pid_done) {
			pidControllerTurning.disable();
		}

		System.out.println("gyro: " + ahrs.getAngle());

		return pid_done;
	}

	// temp
	public double getLMCurrent() {
		return left_dt_motor1.getOutputCurrent();
	}

	public double getRMCurrent() {
		return right_dt_motor1.getOutputCurrent();
	}

	public double getLM2Current() {
		return left_dt_motor2.getOutputCurrent();
	}

	public double getRM2Current() {
		return right_dt_motor2.getOutputCurrent();
	}

	// ==Default
	// Command==========================================================================
	public void initDefaultCommand() {
		// Set the default command for a subsystem here.
		setDefaultCommand(new Default_Drivetrain());
	}

}
