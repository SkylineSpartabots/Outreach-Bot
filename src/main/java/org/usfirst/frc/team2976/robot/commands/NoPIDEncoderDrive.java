/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2976.robot.commands;

import org.usfirst.frc.team2976.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class NoPIDEncoderDrive extends Command {

  

  private final static double COUNTS_PER_REV = 1000;
  private final static double WHEEL_DIAMETER = 8;
  private final static double DRIVE_WHEEL_REDUCTION = 1;
  private final static double COUNTS_PER_INCH = (COUNTS_PER_REV/DRIVE_WHEEL_REDUCTION)/(WHEEL_DIAMETER/Math.PI);

  private double distanceInches;

  private double leftPower, rightPower;

  private double rightTarget, leftTarget;

  public NoPIDEncoderDrive(double distanceInches, double leftPower, double rightPower) {
    // Use requires() here to declare subsystem dependencies
    // eg. requires(chassis);
    this.distanceInches = distanceInches;
    requires(Robot.m_drive);
  }

  // Called just before this Command runs the first time
  @Override
  protected void initialize() {
    rightTarget = COUNTS_PER_INCH * this.distanceInches + Robot.m_drive.leftEncoder.getRaw();
    leftTarget = COUNTS_PER_INCH * this.distanceInches + Robot.m_drive.leftEncoder.getRaw();
    Robot.m_drive.tankDrive(0, 0);
  }

  // Called repeatedly when this Command is scheduled to run
  @Override
  protected void execute() {
    Robot.m_drive.tankDrive(leftPower, rightPower);
  }

  // Make this return true when this Command no longer needs to run execute()
  @Override
  protected boolean isFinished() {
    return leftTarget >= Robot.m_drive.leftEncoder.getRaw() || rightTarget >= Robot.m_drive.rightEncoder.getRaw();
      
    
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
  }
}
