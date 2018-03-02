package org.usfirst.frc.team1895.robot.commands.arm;

import org.usfirst.frc.team1895.robot.Robot;

import edu.wpi.first.wpilibj.command.InstantCommand;

/**
 * Set the endgame boolean to true, this disables the protection for 
 * the auto-extend/retract of the arm during teleop
 */
public class SetArmEndgame extends InstantCommand {

    public SetArmEndgame() {
        super();
        requires(Robot.arm);
    }

    // Called once when the command executes
    protected void initialize() {
    		Robot.arm.setArmEndGameTrue();
    }

}
