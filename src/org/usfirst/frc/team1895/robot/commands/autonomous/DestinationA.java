package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.Robot;
import org.usfirst.frc.team1895.robot.commands.arm.DeployCube;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToPosition;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;
import org.usfirst.frc.team1895.robot.subsystems.Arm;
import org.usfirst.frc.team1895.robot.subsystems.Drivetrain;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Testing 1/30/18 Starting Position is variable - 2 or 3, test both
 * ourLeftSwitch is true closeRoute is true
 */
public class DestinationA extends CommandGroup {

	boolean ourLeftSwitch; // get from FMS
	boolean closeRoute; // either from smartdash or based on decisions during auto

	public DestinationA() {

		addSequential(new PrintCommand("A"));
		addSequential(new WaitCommand(SmartDashboard.getNumber("AUTO WAIT TIME", 0)));

		// retrieve FMS data to determine where scale is
		if (Robot.closeSwitchNum == 1) { // our switch is on the left
			ourLeftSwitch = true;
		} else {
			ourLeftSwitch = false;
		}

		closeRoute = true; // hard coding it for now
		addSequential(new PrintCommand("A"));

		if (ourLeftSwitch) {
			addSequential(new PrintCommand("we are on left"));
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("A, ourLeftSwitch, position 1"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 30));  //switch to rf
				addSequential(new DeployCube());
				// now directly in front of switch, 40" away
				// temporarily making DriveToObstacle go all the way Dest.A, but in the real
				// code it'll vision-align
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
				// addSequential(new DeployCube());
				break;
			case 2:
				addSequential(new PrintCommand("A, ourLeftSwitch, position 2"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED, 75));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_TURN_SPEED, 40));
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
				addSequential(new DeployCube());
				break;
			case 3: // USING FOR TEST
				addSequential(new PrintCommand("A, ourLeftSwitch, position 3"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,173));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
				addSequential(new DeployCube());
				break;
			}

		} else { // our switch is on the right
			addSequential(new PrintCommand("we are on right"));
			switch (Robot.startPos) {
			case 1:
				addSequential(new PrintCommand("A, not ourLeftSwitch, position 1"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,173));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
				addSequential(new DeployCube());
				break;
			case 2:
				addSequential(new PrintCommand("A, not ourLeftSwitch, position 2"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,40));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_TURN_SPEED, 40));
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
				addSequential(new DeployCube());
				// addSequential(new DriveToObstacle(2, 0.5));
				// addSequential(new AlignToSwitch());
//				addSequential(new DeployCube());
				break;
			case 3: // NOT DONE YET
				addSequential(new PrintCommand("A, not ourLeftSwitch, position 3, closeRoute"));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, -90));
				addSequential(new WaitCommand(0.5));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,50));
				addSequential(new WaitCommand(0.5));
				addSequential(new TurnWithoutPID(Drivetrain.AUTO_TURN_SPEED, 90));
				addSequential(new WaitCommand(0.5));
//				addSequential(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
				addSequential(new DriveStraightWithoutPID(Drivetrain.AUTO_DRIVE_SPEED,30));
				addSequential(new DeployCube());
				break;
			}

		}
	}

	@Override
	public void initialize() {

	}
}
