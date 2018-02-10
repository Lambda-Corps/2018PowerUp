package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.OI;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationA;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationB;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationC;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationD;
import org.usfirst.frc.team1895.robot.commands.autonomous.DestinationE;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveParallel;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestDriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestEmptyCommand;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestTurnWithPID;
import org.usfirst.frc.team1895.robot.commands.testcommands.TestTurnWithoutPID;
import org.usfirst.frc.team1895.robot.oi.F310;
import org.usfirst.frc.team1895.robot.subsystems.Climber;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;
import org.usfirst.frc.team1895.robot.subsystems.FilteredCamera;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.TimedRobot;
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
public class Robot extends TimedRobot {

	public static Drivetrain drivetrain = new Drivetrain();
	public static OI oi;
	public static Climber climber;
	public static FilteredCamera camera;
	Command autonomousCommand;
	int printCount;
	
	//choosers
	SendableChooser<String> chooser = new SendableChooser<>();
	SendableChooser<Integer> pos_chooser = new SendableChooser<>();

	public static int startPos;
	String decision;
	
	public static int closeSwitchNum;
	public static int scaleNum;
	public static int farSwitchNum;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		//climber = new Climber();
		// TODO -- This is less frequent, I think the default is 20 ms
		setPeriod(0.025);  //update more frequently - every 25ms
		//camera = new FilteredCamera();
		//Robot.camera.startVisionThread();
		
		//choosing start position
		SmartDashboard.putData("Start Position", pos_chooser);
		pos_chooser.addObject("Position One", 1);
		pos_chooser.addObject("Position Two", 2);
		pos_chooser.addObject("Position Three", 3);
		pos_chooser.addDefault("Default (drive forward to cross auto line)", 0);
		
		//choosing destination
		/*chooser.addObject("Destination A", new DestinationA());
		chooser.addObject("Destination B", new DestinationB());
		chooser.addObject("Destination C", new DestinationC());
		chooser.addObject("Destination D", new DestinationD());
		chooser.addObject("Destination E", new DestinationE());
		chooser.addDefault("Test Commands", new TestEmptyCommand());*/
		
		//choosing destination
		chooser.addObject("Destination A", "A");
		chooser.addObject("Destination B", "B");
		chooser.addObject("Destination C", "C");
		chooser.addObject("Destination D", "D");
		chooser.addObject("Destination E", "E");
		chooser.addDefault("Test Commands", "F");
		SmartDashboard.putData("Destination", chooser);
		
		climber = new Climber();
		setPeriod(0.025);  //update more frequently - every 25ms
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {
		//Robot.camera.putVideo(false);
		
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
		autonomousCommand = determineAuto();
		
		
		if(!autonomousCommand.getName().contains("Test")){
			if (autonomousCommand != null) {
				autonomousCommand.start();
			}
		}
		else{
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
		//Robot.camera.putVideo(true);
		
		//access FMS data
		String colorString;
		colorString = DriverStation.getInstance().getGameSpecificMessage();
		if(colorString.charAt(0) == 'L') { //if switch closest to us has our color on the left, we are 1
			closeSwitchNum = 1;
		} else {
			closeSwitchNum = 2;
		}
		if(colorString.charAt(1) == 'L') { 
			scaleNum = 1 ;
		} else {
			scaleNum = 2 ;
		}
		if(colorString.charAt(2) == 'L') { //if switch farthest from us has our color on the left, we are 1
			farSwitchNum = 1;
		} else {
			farSwitchNum = 2;
		}
		
		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
		
		//+Robot.camera.putVideo(true);
		
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
		//if(printCount%10==0)
			//System.out.println("RF: " + Robot.drivetrain.fr_rangefinderDist());
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
		
	}

	/**
	 * This function is called periodically during operator control
	 */
	
	
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
	//	System.out.println("<joy> LY " + Robot.oi.gamepad.getAxis(F310.LY) + " RX " + Robot.oi.gamepad.getAxis(F310.RX));
    	System.out.printf("<joy> LY: %5.1f    RX: %5.1f", Robot.oi.gamepad.getAxis(F310.LY), Robot.oi.gamepad.getAxis(F310.RX) );
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	}
	
	public Command determineAuto() {
		startPos = pos_chooser.getSelected().intValue();
		decision = chooser.getSelected();
		
		if(decision.equals("A")) {
			return new DestinationA();
		}
		else if(decision.equals("B")) {
			return new DestinationB();
		}
		else if(decision.equals("C")) {
			return new DestinationC();
		}
		else if(decision.equals("D")) {
			return new DestinationD();
		}
		else if(decision.equals("E")) {
			return new DestinationE();
		}
		else {
			return new TestEmptyCommand();
		}
	}
}
