package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveToObstacle;

//import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;

/**
 *
 */
public class Autonomous extends CommandGroup {

    public Autonomous() {
//    	System.out.println("1---------------------------");
//        addSequential(new DriveStraightWithoutPID(0.25, 50)); //units in inches
//    	System.out.println("2-------------------------------");
//    	addSequential(new WaitCommand(1));
//    	System.out.println("3------------------");
//    	addSequential(new DriveStraightWithoutPID(0.25, -50)); //units in inches
//    	addSequential(new DriveToObstacle(15, 0.25));
    	
    	addSequential(new ConditionalCommand1(new DestinationA(), new DestinationB()));

    }
}
