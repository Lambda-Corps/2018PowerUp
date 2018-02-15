package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class ShiftGearsTestCommand extends CommandGroup {

    public ShiftGearsTestCommand() {
    	
    	addSequential(new ShiftGearsTestStartLG());
    	addSequential(new ShiftGearsShiftHighGear());
    	addSequential(new ShiftGearsTestShiftLG());
    	addSequential(new Default_Drivetrain(0,0));
    	//add a stoprobot command lolol

    }
}
