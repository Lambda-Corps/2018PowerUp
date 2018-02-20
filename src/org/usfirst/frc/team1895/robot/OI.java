package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.commands.arm.RotateArm;
import org.usfirst.frc.team1895.robot.commands.claw.DeployCube_Claw;
import org.usfirst.frc.team1895.robot.commands.claw.GrabCube_Claw;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToCube;
import org.usfirst.frc.team1895.robot.commands.drivetrain.FineTuningMode;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.DeployCube_LowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.GrabCube_LowerIntake;
import org.usfirst.frc.team1895.robot.oi.F310;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());

	// remember to add secret button

	public F310 gamepad1;
	public F310 gamepad2;
	
	public JoystickButton ninetyDegreeRight;
	public JoystickButton ninetyDegreeLeft;
	public JoystickButton fineTuning;
	public JoystickButton deployCube_Claw;
	public JoystickButton grabCube_Claw;
	public JoystickButton deployCube_LowerIntake;
	public JoystickButton grabCube_LowerIntake;
	public JoystickButton ninetyUp;
	public JoystickButton ninetyDown;
	public JoystickButton alignToCube;

	public OI() {
		gamepad1 = new F310(RobotMap.GAMEPAD_PORT);
		gamepad2 = new F310(RobotMap.GAMEPAD2_PORT);

		//drivetrain buttons
		ninetyDegreeRight = new JoystickButton(gamepad1, F310.B);
		ninetyDegreeRight.whenPressed(new TurnWithoutPID(0.3, 90.0));
		ninetyDegreeLeft = new JoystickButton(gamepad1, F310.X);
		ninetyDegreeLeft.whenPressed(new TurnWithoutPID(0.3, -90.0));
		
		alignToCube = new JoystickButton(gamepad1, F310.A);
		alignToCube.whenPressed(new AlignToCube());
		
		//fineTuning = new JoystickButton(gamepad1, F310.RB);
		//fineTuning.whileHeld(new FineTuningMode());
		
		//claw buttons 
		grabCube_Claw = new JoystickButton(gamepad2, F310.LB);
		deployCube_Claw = new JoystickButton(gamepad2, F310.RB);
		grabCube_Claw.whenPressed(new GrabCube_Claw());
		deployCube_Claw.whenPressed(new DeployCube_Claw());

		//lower intake buttons
		grabCube_LowerIntake = new JoystickButton(gamepad1, F310.LB);
		deployCube_LowerIntake = new JoystickButton(gamepad1, F310.RB);
		grabCube_LowerIntake.whenPressed(new GrabCube_LowerIntake());
		deployCube_LowerIntake.whenPressed(new DeployCube_LowerIntake());
		
		//arm buttons
		ninetyUp = new JoystickButton(gamepad2, F310.Y);
		ninetyUp.whenPressed(new RotateArm(90));
		ninetyDown = new JoystickButton(gamepad2, F310.A);
		ninetyDown.whenPressed(new RotateArm(135));
		
	}
}
