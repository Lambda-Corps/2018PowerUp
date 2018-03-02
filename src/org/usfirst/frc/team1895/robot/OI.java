package org.usfirst.frc.team1895.robot;

import org.usfirst.frc.team1895.robot.commands.arm.CancelArm;
import org.usfirst.frc.team1895.robot.commands.arm.CubeIn;
import org.usfirst.frc.team1895.robot.commands.arm.DeployCube;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToAngle;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToPosition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.AlignToCube;
import org.usfirst.frc.team1895.robot.oi.F310;

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
	public JoystickButton ninetyDegreeRight;
	public JoystickButton ninetyDegreeLeft;
	public JoystickButton turn180Left;
	public JoystickButton drivetrainCancel;
	public JoystickButton fineTuning;
	public JoystickButton alignToCube;
	
	//claw and intake
	public JoystickButton deployCube_Claw;
	public JoystickButton grabCube_Claw;
	public JoystickButton deployCube_LowerIntake;
	public JoystickButton grabCube_LowerIntake;
	
	//arm and special arm positions
	public JoystickButton ninetyUp;
	public JoystickButton ninetyDown;
	public JoystickButton rotateArm_switchPos;
	public JoystickButton rotateArm_Scale_Low;
	public JoystickButton rotateArm_Scale_Mid;
	public JoystickButton rotateArm_Scale_High;
	public JoystickButton armCancel;

	public OI() {
		gamepad1 = new F310(RobotMap.GAMEPAD_PORT);
		gamepad2 = new F310(RobotMap.GAMEPAD2_PORT);

		//drivetrain buttons
		alignToCube = new JoystickButton(gamepad1, F310.A);
		alignToCube.whenPressed(new AlignToCube());
		
		//claw buttons 
		deployCube_Claw = new JoystickButton(gamepad2, F310.RB);
		deployCube_Claw.whenPressed(new DeployCube());

		//lower intake buttons
		grabCube_LowerIntake = new JoystickButton(gamepad1, F310.LB);
		grabCube_LowerIntake.whenPressed(new CubeIn());
		
		//arm buttons
//		ninetyUp = new JoystickButton(gamepad2, F310.Y);
//		ninetyUp.whenPressed(new RotateArmToAngle(90));
//		ninetyDown = new JoystickButton(gamepad2, F310.A);
//		ninetyDown.whenPressed(new RotateArmToAngle(135));
		rotateArm_switchPos = new JoystickButton(gamepad2, F310.A);
		rotateArm_switchPos.whenPressed(new RotateArmToPosition(Robot.arm.ARM_SWITCH_POSITION));
		
		// TODO -- There should be a button to bring the arm to the lowest position, instead of scale 
		// low this would work.
		rotateArm_Scale_Low = new JoystickButton(gamepad2, F310.X);
		rotateArm_Scale_Low.whenPressed(new RotateArmToPosition(Robot.arm.ARM_SCALE_LOW_POSITION));
		rotateArm_Scale_Mid = new JoystickButton(gamepad2, F310.B);
		rotateArm_Scale_Mid.whenPressed(new RotateArmToPosition(Robot.arm.ARM_SCALE_MID_POSITION));
		rotateArm_Scale_High = new JoystickButton(gamepad2, F310.Y);
		rotateArm_Scale_High.whenPressed(new RotateArmToPosition(Robot.arm.ARM_SCALE_HIGH_POSITION));
	}
}
