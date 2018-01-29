package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import au.net.projectb.Constants;

/**
 * Makes the robot move.
 */
public class Drivetrain extends Subsystem {
	private static Drivetrain instance;
	
	TalonSRX mLeftMaster, mLeftSlaveA, mLeftSlaveB;
	TalonSRX mRightMaster, mRightSlaveA, mRightSlaveB;
	
	boolean driveDirectionIsForwards;
	double throttlePreset;
	
	enum ThrottlePreset {
		ANALOGUE,
		LOW,
		MID,
		HIGH
	}
	
	public static Drivetrain getInstance() {
		if (instance == null) {
			instance = new Drivetrain();
		}
		return instance;
	}
	
	private Drivetrain() {
		// Left Side
		mLeftMaster = new TalonSRX(Constants.kLeftDriveMaster);	// CIM
		mLeftSlaveA = new TalonSRX(Constants.kLeftDriveSlaveA);	// CIM
		mLeftSlaveB = new TalonSRX(Constants.kLeftDriveSlaveB);	// MiniCIM
		
		mLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		mLeftMaster.configOpenloopRamp(Constants.kDriveVoltageRamp, 0);
		mLeftMaster.configContinuousCurrentLimit(40, 0);
		mLeftMaster.configPeakCurrentLimit(60, 0);
		mLeftMaster.configPeakCurrentDuration(100, 0);
		mLeftMaster.enableCurrentLimit(true);
		
		mLeftMaster.setNeutralMode(Constants.kDriveNeutralMode);
		mLeftSlaveA.setNeutralMode(Constants.kDriveNeutralMode);
		mLeftSlaveB.setNeutralMode(Constants.kDriveNeutralMode);
		
		mLeftSlaveA.set(ControlMode.Follower, 0);
		mLeftSlaveB.set(ControlMode.Follower, 0);
		
		// Right Side
		mRightMaster = new TalonSRX(Constants.kRightDriveMaster);	// CIM
		mRightSlaveA = new TalonSRX(Constants.kRightDriveSlaveA);	// CIM
		mRightSlaveB = new TalonSRX(Constants.kRightDriveSlaveB);	// MiniCIM
		
		mRightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		mRightMaster.configOpenloopRamp(Constants.kDriveVoltageRamp, 0);
		mRightMaster.configContinuousCurrentLimit(40, 0);
		mRightMaster.configPeakCurrentLimit(60, 0);
		mRightMaster.configPeakCurrentDuration(100, 0);
		mRightMaster.enableCurrentLimit(true);
		
		mRightMaster.setNeutralMode(Constants.kDriveNeutralMode);
		mRightSlaveA.setNeutralMode(Constants.kDriveNeutralMode);
		mRightSlaveB.setNeutralMode(Constants.kDriveNeutralMode);
		
		mRightSlaveA.set(ControlMode.Follower, 0);
		mRightSlaveB.set(ControlMode.Follower, 0);
		
		driveDirectionIsForwards = true;
		throttlePreset = -1.0;
	}
	
	/**
	 * Arcade drive, sets motor powers.
	 * @param power
	 * @param steering
	 * @param throttle
	 */
	public void arcadeDrive(double power, double steering, double throttle) {
		if (throttlePreset != -1.0) {
			throttle = throttlePreset;
		}
		if (!driveDirectionIsForwards) {
			throttle = -throttle;
		}
		double leftPower = (power + steering) * throttle;
		double rightPower = (power - steering) * throttle;
		setMotorPower(leftPower, rightPower);
	}
	
	/**
	 * Reverse the driving direction for teleopDrive.
	 */
	public void reverseDirection() {
		driveDirectionIsForwards = !driveDirectionIsForwards;
	}
	
	/**
	 * Wrapper for setting motor powers. Only Drivetrain can do so directly.
	 * @param left
	 * @param right
	 */
	private void setMotorPower(double left, double right) {
		mLeftMaster.set(ControlMode.PercentOutput, left);
		mRightMaster.set(ControlMode.PercentOutput, -right);
	}
	
	public void setThrottlePreset(ThrottlePreset preset) {
		switch (preset) {
			case LOW:
				throttlePreset = 0.3;
				break;
			case MID:
				throttlePreset = 0.6;
				break;
			case HIGH:
				throttlePreset = 0.8;
				break;
			default: // Also handles ANALOGUE
				throttlePreset = -1.0;
		}
	}
}
