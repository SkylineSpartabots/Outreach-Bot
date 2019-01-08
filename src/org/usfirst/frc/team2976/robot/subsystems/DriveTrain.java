package org.usfirst.frc.team2976.robot.subsystems;

import org.usfirst.frc.team2976.robot.Robot;
import org.usfirst.frc.team2976.robot.RobotMap;
import org.usfirst.frc.team2976.robot.commands.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSourceType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class DriveTrain extends Subsystem implements PIDOutput{
	private WPI_TalonSRX leftFront, leftBack, rightFront, rightBack;
	double kP = 0.002;
	double kD = 0.0;
	double kI = 0.0;
	public double threshold = 2;
	
	public PIDController turnController = null; 
	//private DifferentialDrive drive = null;
	//private SpeedControllerGroup left, right;
	public double output;
	
	public AHRS navx;
	//k_u 0.0346
	//
	
	public DriveTrain() {
		
		leftFront = new WPI_TalonSRX(RobotMap.leftFrontMotorPort);
		leftBack = new WPI_TalonSRX(RobotMap.leftBackMotorMotorPort);
		rightFront = new WPI_TalonSRX(RobotMap.rightFrontMotorPort);
		rightBack = new WPI_TalonSRX(RobotMap.rightBackMotorPort);
		
		rightFront.setInverted(true);
		rightBack.setInverted(true);
		
	//	left = new SpeedControllerGroup(leftFront, leftBack);
		//right = new SpeedControllerGroup(rightFront, rightBack);
		
		//drive = new DifferentialDrive(left, right);
		//drive.setSubsystem("drive train");

		navx = new AHRS(SPI.Port.kMXP);
		navx.setPIDSourceType(PIDSourceType.kDisplacement);
		navx.reset();
		navx.zeroYaw();
		turnController = new PIDController(kP, kI, kD, navx, this);
		turnController.setInputRange(-360, 360);
		turnController.setOutputRange(-1, 1);
		turnController.setAbsoluteTolerance(threshold);
		turnController.setContinuous(false);
		//turnController.
	}
	
	public void rotate(double angle) {
		//this.basePower = basePower;
		turnController.setPID(kP, kI, kD);
		turnController.reset();
		turnController.setSetpoint(angle);
		turnController.enable();
	}
	
	@Override
	protected void initDefaultCommand() {
		// TODO Auto-generated method stub
		setDefaultCommand(new ArcadeDrive());
	}
	
	public void setPower(ControlMode mode, double leftPower, double rightPower) {
		
		leftFront.set(mode, leftPower);
		leftBack.set(mode, leftPower);
		rightFront.set(mode, rightPower);
		rightBack.set(mode, rightPower);
		
		
		
	}
	
	public void arcadeDrive(double turn, double speed) {
		//drive.arcadeDrive(speed, turn);
	}
	
	public void tankDrive(double leftPower, double rightPower) {
		//drive.tankDrive(leftPower, rightPower);
		leftFront.set(leftPower);
		leftBack.set(leftPower);
		rightFront.set(rightPower);
		rightBack.set(rightPower);
		
	}

	@Override
	public void pidWrite(double output) {
		// TODO Auto-generated method stub
		this.output = output;
		setPower(ControlMode.PercentOutput, -output, output);
		
	}
	
}
