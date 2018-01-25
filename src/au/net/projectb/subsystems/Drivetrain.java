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
	
	TalonSRX m_leftMaster, m_leftSlaveA, m_leftSlaveB;
	TalonSRX m_rightMaster, m_rightSlaveA, m_rightSlaveB;
	
	public static Drivetrain getInstance() {
		if (instance == null) {
			instance = new Drivetrain();
		}
		return instance;
	}
	
	private Drivetrain() {
		// Left Side
		m_leftMaster = new TalonSRX(Constants.kLeftDriveMaster);	// CIM
		m_leftSlaveA = new TalonSRX(Constants.kLeftDriveSlaveA);	// CIM
		m_leftSlaveB = new TalonSRX(Constants.kLeftDriveSlaveB);	// MiniCIM
		
		m_leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		m_leftMaster.configOpenloopRamp(Constants.kDriveVoltageRamp, 0);
		m_leftMaster.configContinuousCurrentLimit(40, 0);
		m_leftMaster.configPeakCurrentLimit(60, 0);
		m_leftMaster.configPeakCurrentDuration(100, 0);
		m_leftMaster.enableCurrentLimit(true);
		
		m_leftMaster.setNeutralMode(Constants.kDriveNeutralMode);
		m_leftSlaveA.setNeutralMode(Constants.kDriveNeutralMode);
		m_leftSlaveB.setNeutralMode(Constants.kDriveNeutralMode);
		
		m_leftSlaveA.set(ControlMode.Follower, 0);
		m_leftSlaveB.set(ControlMode.Follower, 0);
		
		// Right Side
		m_rightMaster = new TalonSRX(Constants.kRightDriveMaster);	// CIM
		m_rightSlaveA = new TalonSRX(Constants.kRightDriveSlaveA);	// CIM
		m_rightSlaveB = new TalonSRX(Constants.kRightDriveSlaveB);	// MiniCIM
		
		m_rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 0);
		m_rightMaster.configOpenloopRamp(Constants.kDriveVoltageRamp, 0);
		m_rightMaster.configContinuousCurrentLimit(40, 0);
		m_rightMaster.configPeakCurrentLimit(60, 0);
		m_rightMaster.configPeakCurrentDuration(100, 0);
		m_rightMaster.enableCurrentLimit(true);
		
		m_rightMaster.setNeutralMode(Constants.kDriveNeutralMode);
		m_rightSlaveA.setNeutralMode(Constants.kDriveNeutralMode);
		m_rightSlaveB.setNeutralMode(Constants.kDriveNeutralMode);
		
		m_rightSlaveA.set(ControlMode.Follower, 0);
		m_rightSlaveB.set(ControlMode.Follower, 0);
	}
}
