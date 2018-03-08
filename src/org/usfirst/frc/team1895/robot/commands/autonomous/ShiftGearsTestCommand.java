package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.ShiftGearsShiftHighGear;
import org.usfirst.frc.team1895.robot.commands.drivetrain.ShiftGearsTestShiftLG;
import org.usfirst.frc.team1895.robot.commands.drivetrain.ShiftGearsTestStartLG;
import org.usfirst.frc.team1895.robot.commands.drivetrain.StopRobot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;

/**
 *
 */

public class ShiftGearsTestCommand extends CommandGroup {

    public ShiftGearsTestCommand() {
    	
    	addSequential(new PrintCommand("Beginning ShiftGears Test Suite"));
    	addSequential(new ShiftGearsTestStartLG());
    	addSequential(new ShiftGearsShiftHighGear());
    	addSequential(new ShiftGearsTestShiftLG());
    	addSequential(new StopRobot());
    	addSequential(new PrintCommand("Ending ShiftGears Test Suite"));
    }
}
