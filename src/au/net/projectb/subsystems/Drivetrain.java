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
	}
}
