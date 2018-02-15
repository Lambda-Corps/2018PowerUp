package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;
import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightWithoutPID;
import org.usfirst.frc.team1895.robot.commands.drivetrain.TurnWithoutPID;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class DestinationE extends CommandGroup {

    public DestinationE() {
        
    	addSequential(new PrintCommand("E"));
/*    	addSequential(new DriveStraightWithoutPID(0.5, 50));
    	addSequential(new WaitCommand(1.5));
    	addSequential(new DriveStraightWithoutPID(0.5, -50));
    	addSequential(new WaitCommand(1.5));
    	addSequential(new TurnWithoutPID(0.3, -90));
    	*/
    }
}
