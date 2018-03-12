package org.usfirst.frc.team1895.robot;

import java.util.ArrayList;

import org.usfirst.frc.team1895.robot.commands.arm.DeployCube;
import org.usfirst.frc.team1895.robot.commands.arm.DriveWristToMax;
import org.usfirst.frc.team1895.robot.commands.arm.ExtendTelescopingPart;
import org.usfirst.frc.team1895.robot.commands.arm.RetractTelescopingPart;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToPosition;
import org.usfirst.frc.team1895.robot.commands.autonomous.AutoCommandBuilder;
import org.usfirst.frc.team1895.robot.commands.autonomous.CommandHolder;
import org.usfirst.frc.team1895.robot.commands.drivetrain.CancelDrivetrain;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RetractLowerIntake;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestRotateArm;
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
import edu.wpi.first.wpilibj.command.PrintCommand;
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
@SuppressWarnings("unused")
public class Robot extends TimedRobot {
	public static final int SWITCH_LEFT = 0;
	public static final int SWITCH_RIGHT = 1;
	public static final int SCALE_RIGHT = 1;
	public static final int SCALE_LEFT = 0;

	public static Drivetrain drivetrain;
	public static OI oi;
	public static Climber climber;
	public static FilteredCamera camera;
	public static LowerIntake lowerIntake;
	Command autonomousCommand;
	int printCount;

	public static Arm arm;

	// choosers
	SendableChooser<Integer> pos_chooser = new SendableChooser<>();
	SendableChooser<String> priority_chooser = new SendableChooser<>();
	SendableChooser<String> near_far = new SendableChooser<>();
	SendableChooser<String> cross_field = new SendableChooser<>();
	SendableChooser<String> switch_scale = new SendableChooser<>();

	public static int startPos;
	public static String priority;
	public static String nearfar;
	public static String crossfield;
	public static String switchscale;

	public static int ourSwitchSide;
	public static int ourScaleSide;
	public static int theirSwitchSide;

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		drivetrain = new Drivetrain();
		arm = new Arm();
		lowerIntake = new LowerIntake();
		// setPeriod(0.015); // update more frequently - every 25ms
		// camera = new FilteredCamera();
		// Robot.camera.startVisionThread();

		// choosing start position
		SmartDashboard.putData("Start Position", pos_chooser);
		pos_chooser.addDefault("Default (drive forward to cross auto line)", 0);
		pos_chooser.addObject("Position One", 1);
		pos_chooser.addObject("Position Two", 2);
		pos_chooser.addObject("Position Three", 3);

		// choosing initial priority
		SmartDashboard.putData("Priority", priority_chooser);
		priority_chooser.addDefault("Switch", "Switch");
		priority_chooser.addObject("Scale", "Scale");
		priority_chooser.addObject("Whichever", "Whichever");

		// choosing avoid far side or not
		SmartDashboard.putData("Score side (only pick near if we should avoid far)", near_far);
		near_far.addDefault("Near", "Near");
		near_far.addObject("Far", "Far");

		// choosing avoid far side or not
		SmartDashboard.putData("Cross Field or not", cross_field);
		cross_field.addDefault("Cross", "Cross");
		cross_field.addObject("Stay", "Stay");

		// choosing avoid far side or not
		SmartDashboard.putData("Switch or Scale(Once across the field)", switch_scale);
		switch_scale.addDefault("Switch", "Switch");
		switch_scale.addObject("Scale", "Scale");

		// toggle conditional wait at beginning
		SmartDashboard.putNumber("AUTO WAIT TIME", 0);

		climber = new Climber();
		oi = new OI();

	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
	 */
	@Override
	public void disabledInit() {
		// Robot.camera.putVideo(false);
		Scheduler.getInstance().removeAll();
	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void testInit() {
		Scheduler.getInstance().removeAll();
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

		Scheduler.getInstance().removeAll();

		Robot.drivetrain.setBrakeMode();
		Robot.drivetrain.shiftToLowGear();

		// access FMS data
		// Game message comes in the form of three letters representing ourSwitch,
		// Scale, theirSwitch.
		// The message is looking at the field from the alliance driver's perspective.
		// For example, the game message "LLL" would represent our color alliance side
		// chosen for all
		// three to be our driver's left.
		String colorString;
		// System.out.println("auto init");
		do {
			colorString = DriverStation.getInstance().getGameSpecificMessage();
		} while (colorString.length() == 0);
		if (colorString.charAt(0) == 'L') { // if switch closest to us has our color on the left, we are 1
			ourSwitchSide = SWITCH_LEFT;
		} else {
			ourSwitchSide = SWITCH_RIGHT;
		}
		if (colorString.charAt(1) == 'L') {
			ourScaleSide = SCALE_LEFT;
		} else {
			ourScaleSide = SCALE_RIGHT;
		}
		if (colorString.charAt(2) == 'L') { // if switch farthest from us has our color on the left, we are 1
			theirSwitchSide = SWITCH_LEFT;
		} else {
			theirSwitchSide = SWITCH_RIGHT;
		}
		autonomousCommand = determineAuto();
		// autonomousCommand = new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,
		// 140);

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

		// Remove any of the previously scheduled commands
		Scheduler.getInstance().removeAll();

		Robot.drivetrain.resetEncoders();
		Robot.drivetrain.setBrakeMode();
		Robot.drivetrain.shiftToLowGear();

		SmartDashboard.putNumber("Dist per pulse", 0.02475);

		SmartDashboard.putNumber("Rangefinder (front)", Robot.drivetrain.fr_rangefinderDist());

		// Testing Turning
		// SmartDashboard.putNumber("Turn: P value: ", .025);
		// SmartDashboard.putNumber("Turn: I value: ", 0.0);
		// SmartDashboard.putNumber("Turn: D value: ", -.005);
		SmartDashboard.putNumber("Test Turn Angle: ", 90.0);
		SmartDashboard.putNumber("Test NP Turn Speed: ", drivetrain.AUTO_TURN_SPEED);
		SmartDashboard.putNumber("Test Turn Tolerance: ", 3);

		// SmartDashboard.putData("Test Turn With PID", new TestTurnWithPID());
		SmartDashboard.putData("Test Turn Without PID", new TestTurnWithoutPID());

		// Distance Related Testing
		// SmartDashboard.putNumber("Distance: P value: ", .1);
		// SmartDashboard.putNumber("Distance: I value: ", 0.0);
		// SmartDashboard.putNumber("Distance: D value: ", -.01);
		SmartDashboard.putNumber("Test Drive Distance:", 30.0);
		SmartDashboard.putNumber("Test Drive Speed:", drivetrain.AUTO_DRIVE_SPEED);
		// SmartDashboard.putNumber("Test Drive Tank Scalar:", .94); // in case of
		// drifting
		// SmartDashboard.putNumber("Test Drive Buffer:", 10);
		SmartDashboard.putNumber("Test DTO Distance:", 50.0);
		//
		// SmartDashboard.putData("Test DriveStraight With PID", new
		// TestDriveStraightWithPID());
		SmartDashboard.putData("Test DriveStraight No PID", new TestDriveStraightWithoutPID());
		SmartDashboard.putData("Test Drive With RangeFinder", new TestDriveToObstacle());
		// SmartDashboard.putData("Test Drive Parallel", new TestDriveParallel());

		// SmartDashboard.putBoolean("Test boolean onLeft Value", false);

		// Arm/lower intake commands
		// SmartDashboard.putNumber("Test RotateArm Position", 1150);
		// SmartDashboard.putData("Test RotateArm", new TestRotateArm());
		// SmartDashboard.putData("Test DriveWrist to max", new DriveWristToMax());
		// ////
		//// SmartDashboard.putNumber("Claw Speed", 0);
		//// SmartDashboard.putNumber("Arm Speed", 0.2);
		////
		// SmartDashboard.putData("Test CubeIn", new CubeIn());
		////
		//// SmartDashboard.putData("Test ResetArm", new ResetArm());
		////
		//// SmartDashboard.putData("Test Deploy Cube and Retract", new
		//// DeployCubeAndRetract());
		// SmartDashboard.putData("Test Extend Lower Intake", new ExtendLowerIntake());
		// SmartDashboard.putData("Test Retract Lower Intake", new
		// RetractLowerIntake());
		// SmartDashboard.putData("Test Raise Lower Intake", new RaiseLowerIntake());
		// SmartDashboard.putData("Test Lower Lower Intake", new LowerLowerIntake());
		////
		//// SmartDashboard.putNumber("Lower Intake Speed", 0.4);
		////
		//// SmartDashboard.putData("Test Grab Cube Lower Intake", new
		//// GrabCube_LowerIntake());
		// SmartDashboard.putData("Test Extend Telescoping Part", new
		// ExtendTelescopingPart());
		// SmartDashboard.putData("Test Retract Telescoping Part", new
		// RetractTelescopingPart());
		// SmartDashboard.putData("Test RotateArm_Scale_High", new
		// RotateArm_Scale_High());
		//// SmartDashboard.putData("Test RotateArm_Scale_Low", new
		// RotateArm_Scale_Low());
		//// SmartDashboard.putData("Test RotateArm_Scale_Mid", new
		// RotateArm_Scale_Mid());
		// SmartDashboard.putData("Test RotateArm_SwitchPos", new
		// RotateArm_SwitchPos());
		////
		// SmartDashboard.putData("TestDriveStraightWithoutPID 50 in", new
		// DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 50));
		//
		//// SmartDashboard.putNumber("Cube Close Value", 1);
		////
		//// SmartDashboard.putData("Rotate Arm to Zero", new RotateArmToZero());
		////
		//// SmartDashboard.putData("Rotate Wrist", new RotateWrist());
		//
		// SmartDashboard.putData("Drive To Obstacle", new TestDriveToObstacle());
		//// SmartDashboard.putData("Drive Parallel", new TestDriveParallel());
		//
		SmartDashboard.putNumber("fr RF scalar", 40);
		//
		// SmartDashboard.putData("Climb Sequence", new ClimbSequence());

	}

	/**
	 * This function is called periodically during operator control
	 */

	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		// SmartDashboard.putNumber("Short Range Finder", Robot.arm.getInRangeFinder());
		// SmartDashboard.putNumber("Accel Value X", Robot.arm.getXValue());
		// SmartDashboard.putNumber("Accel Value Y", Robot.arm.getYValue());
		// SmartDashboard.putNumber("Accel Value Z", Robot.arm.getZValue());
		// SmartDashboard.putNumber("Wrist Encoder 2.0", Robot.arm.getWristEncoder());
		// SmartDashboard.putNumber("Arm potentiometer",
		// Robot.arm.getPotentiometerVoltage());
		// SmartDashboard.putNumber("Accel x", Robot.arm.getXValue());
		// SmartDashboard.putNumber("Accel y", Robot.arm.getYValue());
		// SmartDashboard.putNumber("Accel z", Robot.arm.getZValue());
		// System.out.println("<joy> LY " + Robot.oi.gamepad.getAxis(F310.LY) + " RX " +
		// Robot.oi.gamepad.getAxis(F310.RX));
		// System.out.printf("<joy> LY: %5.1f RX: %5.1f",
		// Robot.oi.gamepad.getAxis(F310.LY), Robot.oi.gamepad.getAxis(F310.RX) );
		// System.out.println("<joy> LY " + Robot.oi.gamepad.getAxis(F310.LY) + " RX " +
		// Robot.oi.gamepad.getAxis(F310.RX));

		// System.out.printf("<joy> LY: %5.1f RX: %5.1f",
		// Robot.oi.gamepad1.getAxis(F310.LY), Robot.oi.gamepad1.getAxis(F310.RX) );

		// System.out.println("gyro teleop: " + Robot.drivetrain.getAHRSGyroAngle());

		// System.out.println("LE " + Robot.drivetrain.getLeftEncoder() + " RE " +
		// Robot.drivetrain.getRightEncoder());
		if (Robot.oi.gamepad1.getPOV() == 0) {
			Command turnCmd = new CancelDrivetrain();
			turnCmd.start();
		}

		// SmartDashboard.putNumber("RM Current: ", Robot.drivetrain.getRMCurrent());
		// SmartDashboard.putNumber("LM Current: ", Robot.drivetrain.getLMCurrent());

		// SmartDashboard.putNumber("intake RF", Robot.arm.getIntakeRF());

		// SmartDashboard.putNumber("encoder", Robot.arm.getArmEncoder());
		SmartDashboard.putNumber("LeftEncoder", drivetrain.getLeftEncoder());
		SmartDashboard.putNumber("RightEncoder", drivetrain.getRightEncoder());

		SmartDashboard.putNumber("Rangefinder (front)", Robot.drivetrain.fr_rangefinderDist());

	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		Scheduler.getInstance().run();
		double value = Robot.oi.gamepad2.getAxis(F310.LY);
		Robot.arm.testDriveArm(value);

		SmartDashboard.putNumber("in rf", Robot.arm.getIntakeRF());

	}

	// This function will take in the our starting position from the smart
	// dashboard,
	// and use the desired destination to figure out which command sequence to run.
	public Command determineAuto() {
		startPos = pos_chooser.getSelected().intValue();
		priority = priority_chooser.getSelected();
		nearfar = near_far.getSelected();
		crossfield = cross_field.getSelected();
		switchscale = switch_scale.getSelected();

		System.out.println("Determining Auto Sequence for Switch: " + ourSwitchSide);

		ArrayList<CommandHolder> commandList = new ArrayList<CommandHolder>();
		switch (startPos) {
		case 1:
			// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
			// new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 150)));
			if (ourSwitchSide == SWITCH_RIGHT && priority == "Switch") {
				commandList.add(
						new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("go for right switch")));
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
				// new TurnWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 90)));
				// commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND,
				// new RotateArmToPosition(Arm.ARM_SWITCH_POSITION)));
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
				// new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 20)));
				// // TODO insert the rangefinder to avoid penalty here
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND, new
				// DeployCube()));
			} else if (ourSwitchSide == SWITCH_LEFT && priority == "Scale" && nearfar == "Far") {
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("go for")));
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
				// new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 100)));
				// commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND,
				// new RotateArmToPosition(Arm.ARM_SCALE_HIGH_POSITION)));
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
				// new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 20)));
				// // TODO insert the rangefinder to avoid penalty here
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND, new
				// DeployCube()));
			} else if (ourSwitchSide == SWITCH_RIGHT && priority == "Switch" && nearfar == "Far") {
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
				// new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 130)));
				// TODO: finish this case
			}
			break;
		case 2:
			if (ourSwitchSide == SWITCH_RIGHT) {
				commandList.add(
						new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("Raise arm to switch")));
				commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
						new PrintCommand("Drive to RangeFinder Distance")));
				commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND, new PrintCommand("DeployCube")));

			} else {
				commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
						new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 16)));
				commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
						new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -65)));
				// commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new
				// PrintCommand("Raise Arm to Switch")));
				commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
						new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 120)));
				commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,
						new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 65)));
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND,new
				// PrintCommand("Drive to RangeFinder Distance")));
				// commandList.add(new CommandHolder(CommandHolder.SEQUENTIAL_COMMAND, new
				// PrintCommand("DeployCube")));
			}
			break;
		case 3:

			if (ourSwitchSide == SWITCH_RIGHT && priority == "Switch") {
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("3BR")));
				// 3BR
			} else if (ourScaleSide == SCALE_RIGHT && priority == "Scale") {
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("3ER")));
				// 3ER
			} else if (ourSwitchSide == SWITCH_LEFT && priority == "Switch" && nearfar == "Far") {
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("3CL")));
				// 3CL
			} else if (ourScaleSide == SCALE_LEFT && priority == "Scale" && nearfar == "Far") {
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("3DL")));
				// 3DL

				// if we can't go into the far part to score - go for non-priority
			} else if (ourSwitchSide == SWITCH_RIGHT && priority == "Scale") {
				// 3BR
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("3BR2")));
			} else if (ourScaleSide == SCALE_RIGHT && priority == "Switch") {
				// 3ER
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("3ER2")));
			} else {
				// cross auto line
				commandList.add(new CommandHolder(CommandHolder.PARALLEL_COMMAND, new PrintCommand("cross auto line")));
			}

			break;
		}

		Command autoCommand = new AutoCommandBuilder(commandList);
		return autoCommand;
	}
}
