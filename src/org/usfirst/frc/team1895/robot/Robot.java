package org.usfirst.frc.team1895.robot;

//import org.usfirst.frc.team1895.robot.commands.autonomous.KevinSequence;
import org.usfirst.frc.team1895.robot.subsystems.Claw;
import org.usfirst.frc.team1895.robot.OI;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationA;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationB;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationC;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationD;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationE;
import org.usfirst.frc.team1895.robot.commands.autonomous.ShiftGearsTestCommand;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveParallel;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestEmptyCommand;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestTurnWithPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestTurnWithoutPID;
import org.usfirst.frc.team1895.robot.oi.F310;
import org.usfirst.frc.team1895.robot.subsystems.Arm;
import org.usfirst.frc.team1895.robot.subsystems.Climber;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1895.robot.subsystems.FilteredCamera;
import org.usfirst.frc.team1895.robot.subsystems.LowerIntake;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends TimedRobot {

	public static Drivetrain drivetrain;
	public static OI oi;
	public static Climber climber;
	public static FilteredCamera camera;
	public static Claw claw;
	public static LowerIntake lowerIntake;
	Command autonomousCommand;
	int printCount;

	public static Arm arm;

	// choosers
	SendableChooser<String> chooser = new SendableChooser<>();
	SendableChooser<Integer> pos_chooser = new SendableChooser<>();

	public static int startPos;
	String decision;

	public static int closeSwitchNum;
	public static int scaleNum;
	public static int farSwitchNum;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		arm = new Arm();
		claw = new Claw();
		lowerIntake = new LowerIntake();
		setPeriod(0.015); // update more frequently - every 25ms
		// camera = new FilteredCamera();
		// Robot.camera.startVisionThread();

		// choosing start position
		SmartDashboard.putData("Start Position", pos_chooser);
		pos_chooser.addDefault("Default (drive forward to cross auto line)", 0);
		pos_chooser.addObject("Position One", 1);
		pos_chooser.addObject("Position Two", 2);
		pos_chooser.addObject("Position Three", 3);

		// choosing destination
		// TODO -- make a new default command that just drives forward to cross the
		// autoline
		chooser.addDefault("Destination A", "A");
		chooser.addObject("Destination B", "B");
		chooser.addObject("Destination C", "C");
		chooser.addObject("Destination D", "D");
		chooser.addObject("Destination E", "E");
		chooser.addObject("Gear Shifting Test Suite", "T");
		SmartDashboard.putData("Destination", chooser);
		climber = new Climber();
		oi = new OI();

		System.out.println("Adding command to Dashboard");
		// SmartDashboard.putData("Arm Command", new RotateArmUp());
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		// Robot.camera.putVideo(false);
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {

	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		
		Robot.drivetrain.setBrakeMode();

		// access FMS data
		String colorString;
		System.out.println("auto init");
		do {
			colorString = DriverStation.getInstance().getGameSpecificMessage();
			System.out.println("string: " + colorString);
		} while (colorString.length() == 0);
		System.out.println("finished do while");
		if (colorString.charAt(0) == 'L') { // if switch closest to us has our color on the left, we are 1
			closeSwitchNum = 1;
		} else {
			closeSwitchNum = 2;
		}
		if (colorString.charAt(1) == 'L') {
			scaleNum = 1;
		} else {
			scaleNum = 2;
		}
		if (colorString.charAt(2) == 'L') { // if switch farthest from us has our color on the left, we are 1
			farSwitchNum = 1;
		} else {
			farSwitchNum = 2;
		}

		autonomousCommand = determineAuto();

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();

		// +Robot.camera.putVideo(true);

	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		// System.out.printf("rangefinder: %5.1f \n",
		// Robot.drivetrain.fr_rangefinderDist());
	}

	@Override
	public void teleopInit() {

		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		printCount = 0;
		if (autonomousCommand != null)
			autonomousCommand.cancel();

		Robot.drivetrain.resetEncoders();
		Robot.drivetrain.setCoastMode();

		// Testing Turning
		SmartDashboard.putNumber("Turn: P value: ", .025);
		SmartDashboard.putNumber("Turn: I value: ", 0.0);
		SmartDashboard.putNumber("Turn: D value: ", -.005);
		SmartDashboard.putNumber("Test Turn Angle: ", 90.0);
		SmartDashboard.putNumber("Test NP Turn Speed: ", .4);

		SmartDashboard.putData("Test Turn With PID", new TestTurnWithPID());
		SmartDashboard.putData("Test Turn Without PID", new TestTurnWithoutPID());

		// Distance Related Testing
		SmartDashboard.putNumber("Distance: P value: ", .1);
		SmartDashboard.putNumber("Distance: I value: ", 0.0);
		SmartDashboard.putNumber("Distance: D value: ", -.01);
		SmartDashboard.putNumber("Test Drive Distance:", 30.0);
		SmartDashboard.putNumber("Test Drive Speed: ", .4);
		SmartDashboard.putNumber("Test Drive Tank Scalar:", .94); // incase of drifting
		SmartDashboard.putNumber("Test Drive Buffer:", 10);

		SmartDashboard.putData("Test DriveStraight With PID", new TestDriveStraightWithPID());
		SmartDashboard.putData("Test DriveStraight No PID", new TestDriveStraightWithoutPID());
		SmartDashboard.putData("Test Drive With RangeFinder", new TestDriveToObstacle());
		SmartDashboard.putData("Test Drive Parallel", new TestDriveParallel());

		SmartDashboard.putBoolean("Test boolean onLeft Value", false);
	}

	/**
	 * This function is called periodically during operator control
	 */

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// System.out.println("<joy> LY " + Robot.oi.gamepad.getAxis(F310.LY) + " RX " +
		// Robot.oi.gamepad.getAxis(F310.RX));
		// System.out.printf("<joy> LY: %5.1f RX: %5.1f",
		// Robot.oi.gamepad.getAxis(F310.LY), Robot.oi.gamepad.getAxis(F310.RX) );
		// System.out.println("<joy> LY " + Robot.oi.gamepad.getAxis(F310.LY) + " RX " +
		// Robot.oi.gamepad.getAxis(F310.RX));

		// System.out.printf("<joy> LY: %5.1f RX: %5.1f",
		// Robot.oi.gamepad.getAxis(F310.LY), Robot.oi.gamepad.getAxis(F310.RX) );

		// System.out.println("gyro teleop: " + Robot.drivetrain.getAHRSGyroAngle());

		System.out.println("LE " + Robot.drivetrain.getLeftEncoder() + " RE " + Robot.drivetrain.getRightEncoder());
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
	}

	// This function will take in the our starting position from the smart
	// dashboard,
	// and use the desired destination to figure out which command sequence to run.
	public Command determineAuto() {
		startPos = pos_chooser.getSelected().intValue();
		decision = chooser.getSelected();

		if (decision.equals("A")) {
			return new DestinationA();
		} else if (decision.equals("B")) {
			return new DestinationB();
		} else if (decision.equals("C")) {
			return new DestinationC();
		} else if (decision.equals("D")) {
			return new DestinationD();
		} else if (decision.equals("E")) {
			return new DestinationE();
		} else if (decision.equals("T")) {
			return new ShiftGearsTestCommand();
		} else {
			return new TestEmptyCommand();
		}
	}
}
