package org.usfirst.frc.team1895.robot.commands.climbing;

import org.usfirst.frc.team1895.robot.commands.arm.ExtendTelescopingPart;
import org.usfirst.frc.team1895.robot.commands.arm.RetractTelescopingPart;
import org.usfirst.frc.team1895.robot.commands.arm.RotateArmToPosition;
import org.usfirst.frc.team1895.robot.commands.arm.SetArmEndgame;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.ExtendLowerIntake;
import org.usfirst.frc.team1895.robot.commands.lowerIntake.RaiseLowerIntake;
import org.usfirst.frc.team1895.robot.subsystems.Arm;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class ClimbSequence extends CommandGroup {

    public ClimbSequence() {
        // Add Commands here:
        // Sequence is started after the driver has centered the arm on the rung, and 
    		// driven the robot backward such that the bumpers are flush with the wall:

    		addSequential(new SetArmEndgame());
    		addSequential(new ExtendLowerIntake());
        	addSequential(new RaiseLowerIntake());
    		addSequential(new DriveStraightWithoutPID(.4, 4));
    		addParallel(new RotateArmToPosition(Arm.ARM_SWITCH_POSITION));
    		addParallel(new ExtendTelescopingPart());
    		addSequential(new RotateArmToPosition(Arm.ARM_CLIMB_POSITION));
    		addSequential(new WaitCommand(1));
    		addSequential(new RetractTelescopingPart());
    		addSequential(new RotateArmToPosition(Arm.ARM_LOWEST_POSITION));
    		addSequential(new DriveStraightWithoutPID(.4, 6));
    		addSequential(new TurnWithoutPID(.6, -90));
    		addSequential(new DriveStraightWithoutPID(.4, 20));
    }
}
