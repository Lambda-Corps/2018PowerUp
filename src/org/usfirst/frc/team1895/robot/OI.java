package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.commands.arm.CancelArm;
import org.usfirst.frc.team1895.robot.commands.arm.CubeIn;
import org.usfirst.frc.team1895.robot.commands.arm.DeployCube;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToPosition;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArm_Scale_High;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArm_Scale_Low;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArm_Scale_Mid;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArm_SwitchPos;
import org.usfirst.frc.team1895.robot.commands.arm.ToggleTelescope;
import org.usfirst.frc.team1895.robot.commands.climbing.ClimbSequence;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToCube;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.LowerLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RaiseLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.ToggleLowerIntake;
import org.usfirst.frc.team1895.robot.oi.F310;
import org.usfirst.frc.team1895.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.buttons.JoystickButton;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	// remember to add secret button

	public F310 gamepad1;
	public F310 gamepad2;
	
	//Drivetrain
	public JoystickButton drivetrainCancel;
	public JoystickButton alignToCube;
	
	//claw and intake
	public JoystickButton deployCube_Claw;
	public JoystickButton grabCube_Claw;
	public JoystickButton deployCube_LowerIntake;
	public JoystickButton grabCube_LowerIntake;
	public JoystickButton retract_LowerIntake;
	public JoystickButton extend_LowerIntake;
	public JoystickButton raise_LowerIntake;
	public JoystickButton lower_LowerIntake;
	
	//arm and special arm positions
	public JoystickButton rotateArm_switchPos;
	public JoystickButton rotateArm_Lowest;
	public JoystickButton rotateArm_Scale_Mid;
	public JoystickButton rotateArm_Scale_High;
	public JoystickButton armCancel;
	
	public JoystickButton telescopeOut;
	public JoystickButton telescopeIn;
	public JoystickButton climbSequence;

	public OI() {
		gamepad1 = new F310(RobotMap.GAMEPAD_PORT);
		
		// Gamepad 1 mapped buttons and Axis
		// LY -- Trans Speed, 
		// RY -- Yaw Speed (turning)
		// LT -- Fine Tuning  (not sure if this actually ever gets scheduled)
		// A  -- Align To Cube
		// LB -- Grab Cube
		// RB -- Retract Lower Intake
		// DPAD UP -- Cancel Scheduled Drivetrain Commands
		// DPAD Down -- Turn 180 degrees
		// DPAD Left -- Turn left 90 degrees
		// DPAD Right -- Turn right 90 degrees
		// Y  -- Climbing Sequence for the endgame

		//drivetrain buttons
		alignToCube = new JoystickButton(gamepad1, F310.A);
		alignToCube.whenPressed(new AlignToCube());
		
		climbSequence = new JoystickButton(gamepad1, F310.Y);
		climbSequence.whenPressed(new ClimbSequence());
		
		//lower intake buttons
		grabCube_LowerIntake = new JoystickButton(gamepad1, F310.LB);
		grabCube_LowerIntake.whenPressed(new CubeIn());
		
		retract_LowerIntake = new JoystickButton(gamepad1,F310.RB);
		//retract_LowerIntake.whenPressed(new RaiseLowerIntake());  //TODO: which one?
		retract_LowerIntake.whenPressed(new ToggleLowerIntake());
		
		//extend_LowerIntake = new JoystickButton(gamepad1, F310.B);
		//extend_LowerIntake.whenPressed(new ExtendLowerIntake());
//		
		raise_LowerIntake = new JoystickButton(gamepad1, F310.Y);
		raise_LowerIntake.whenPressed(new RaiseLowerIntake());
		
		telescopeOut = new JoystickButton(gamepad1, F310.X);
		telescopeOut.whenPressed(new ToggleTelescope());
		lower_LowerIntake = new JoystickButton(gamepad1, F310.B);
		lower_LowerIntake.whenPressed(new LowerLowerIntake());

		
		// Gamepad 1 open buttons
		// B, X, RT
		
		
		// Setup the secondary driver controller and button mappings
		gamepad2 = new F310(RobotMap.GAMEPAD2_PORT);
		
		// Gamepad 2 Mappings
		// LY -- Arm Manual Driver
		// RY -- Wrist Manual Driver
		// LT -- Manual Climber
		// A  -- Move Arm to lowest position
		
		// X  -- Move Arm to Switch
		// B  -- Toggle Arm Telescope
		// Y  -- Move Arm to Scale High
		// RB -- Deploy Cube (score it)
		// LB -- Cancel Arm Commands
		rotateArm_Lowest = new JoystickButton(gamepad2, F310.A);
		rotateArm_Lowest.whenPressed(new RotateArmToPosition(Arm.ARM_LOWEST_POSITION));
		rotateArm_Lowest.whenPressed(new RotateArm_Scale_Low());
		rotateArm_switchPos = new JoystickButton(gamepad2, F310.X);
		rotateArm_switchPos.whenPressed(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
		rotateArm_switchPos.whenPressed(new RotateArm_SwitchPos());
		rotateArm_Scale_Mid = new JoystickButton(gamepad2, F310.B);
		rotateArm_Scale_Mid.whenPressed(new RotateArmToPosition(Arm.ARM_SCALE_MID_POSITION));
		rotateArm_Scale_Mid.whenPressed(new RotateArm_Scale_Mid());
		rotateArm_Scale_High = new JoystickButton(gamepad2, F310.Y);
		rotateArm_Scale_High.whenPressed(new RotateArmToPosition(Arm.ARM_SCALE_HIGH_POSITION));
		rotateArm_Scale_High.whenPressed(new RotateArm_Scale_High());
		armCancel = new JoystickButton(gamepad2, F310.LB);
		armCancel.whenPressed(new CancelArm());
		
		//claw buttons 
		deployCube_Claw = new JoystickButton(gamepad2, F310.RB);
		deployCube_Claw.whenPressed(new DeployCube());
		
		// Gamepad 2 open buttons
		// RT, Dpad -- Up, Left, Right, Down
	}
}
