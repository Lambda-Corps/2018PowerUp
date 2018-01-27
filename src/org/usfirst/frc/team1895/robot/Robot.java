
package org.usfirst.frc.team1895.robot;

<<<<<<< HEAD
import org.usfirst.frc.team1895.robot.commands.autonomous.Autonomous1;
import org.usfirst.frc.team1895.robot.commands.autonomous.Autonomous2;
import org.usfirst.frc.team1895.robot.commands.autonomous.Autonomous3;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveParallel;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestEmptyCommand;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestTurnWithPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestTurnWithoutPID;
=======
import org.usfirst.frc.team1895.robot.commands.drivetrain.Default_Drivetrain;
import org.usfirst.frc.team1895.robot.subsystems.Climber;
import org.usfirst.frc.team1895.robot.commands.autonomous.Autonomous;
>>>>>>> aea375b60b023ef39887db59ab8a905bfa8ede53
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

<<<<<<< HEAD
	public static final Drivetrain drivetrain = new Drivetrain();
=======
	public static Drivetrain drivetrain = new Drivetrain();
>>>>>>> aea375b60b023ef39887db59ab8a905bfa8ede53
	public static OI oi;
	public static Climber climber;
	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
<<<<<<< HEAD
		chooser.addDefault("Auto 1", new Autonomous1());
		chooser.addObject("Auto 2", new Autonomous2());
		chooser.addObject("Auto 3", new Autonomous3());
		chooser.addObject("Test Commands", new TestEmptyCommand());
=======
		chooser.addDefault("Default Auto", new Autonomous());
		System.out.println("robotInit");
>>>>>>> aea375b60b023ef39887db59ab8a905bfa8ede53
		SmartDashboard.putData("Auto mode", chooser);
		climber = new Climber();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if(!autonomousCommand.getName().contains("Test")){
			if (autonomousCommand != null) {
				autonomousCommand.start();
			}
		}
		else{
			// Testing Turning
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
			SmartDashboard.putNumber("Test Drive Distance: ", 20.0);
			SmartDashboard.putNumber("Test Drive TankDrive Speed: ", .4);
			SmartDashboard.putNumber("Test Drive Tank Scalar:", .94); //incase of drifting
			SmartDashboard.putNumber("Test Drive Buffer:", 10);
				    	
			SmartDashboard.putData("Test DriveStraight With PID", new TestDriveStraightWithPID());
			SmartDashboard.putData("Test DriveStraight No PID", new TestDriveStraightWithoutPID());
			SmartDashboard.putData("Test Drive With RangeFinder", new TestDriveToObstacle());
			SmartDashboard.putData("Test Drive Parallel", new TestDriveParallel());
			
			SmartDashboard.putBoolean("Test boolean onLeft Value", false);
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
}
