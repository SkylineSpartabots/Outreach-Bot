/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2976.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team2976.robot.Robot;

/**
 * An example command.  You can replace me with your own command.
 */
public class TimeDrive extends Command {
    Timer timer;
    private double time;
    private double leftPower, rightPower;

	public TimeDrive(double time, double leftPower, double rightPower) {
		// Use requires() here to declare subsystem dependencies
        requires(Robot.m_drive);
        this.time = time;
        this.leftPower = leftPower;
        this.rightPower = rightPower;
        timer = new Timer();
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
        timer.reset();
        timer.start();
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
        Robot.m_drive.tankDrive(leftPower, rightPower);
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return timer.get() > time;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
        Robot.m_drive.tankDrive(0, 0);
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
        end();
	}
}
