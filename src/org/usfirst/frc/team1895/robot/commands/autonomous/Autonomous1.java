package org.usfirst.frc.team1895.robot.commands.autonomous;

import org.usfirst.frc.team1895.robot.commands.drivetrain.DriveStraightSetDistance;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

/**
 *
 */
public class Autonomous1 extends CommandGroup {

<<<<<<< HEAD:src/org/usfirst/frc/team1895/robot/commands/autonomous/Autonomous1.java
    public Autonomous1() {
        // Add Commands here:
        // e.g. addSequential(new Command1());
        //      addSequential(new Command2());
        // these will run in order.

        // To run multiple commands at the same time,
        // use addParallel()
        // e.g. addParallel(new Command1());
        //      addSequential(new Command2());
        // Command1 and Command2 will run in parallel.
=======
    public Autonomous() {
        addSequential(new DriveStraightSetDistance(0.25, 120)); //units in inches
        addSequential(new WaitCommand(1));
        addSequential(new DriveStraightSetDistance(0.25, -120)); //units in inches
>>>>>>> aea375b60b023ef39887db59ab8a905bfa8ede53:src/org/usfirst/frc/team1895/robot/commands/autonomous/Autonomous.java

    }
}
