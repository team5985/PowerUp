package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.SerialPort.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import au.net.projectb.Constants;

/**
 * Makes the robot move.
 */
public class Drivetrain extends Subsystem {
	private static Drivetrain m_DrivetrainInstance;
	
	TalonSRX mLeftMaster, mLeftSlaveA, mLeftSlaveB;
	TalonSRX mRightMaster, mRightSlaveA, mRightSlaveB;
	
	AHRS navx;
	
	boolean driveDirectionIsForwards;
	double throttlePreset;
	
	public enum ThrottlePreset {
		ANALOGUE,
		LOW,
		MID,
		HIGH
	}
	
	public static Drivetrain getInstance() {
		if (m_DrivetrainInstance == null) {
			m_DrivetrainInstance = new Drivetrain();
		}
		return m_DrivetrainInstance;
	}
	
	private Drivetrain() {
		navx = new AHRS(Port.kMXP);
		navx.reset();
		
		// Left Side
		mLeftMaster = new TalonSRX(Constants.kLeftDriveMaster);	// CIM
		mLeftSlaveA = new TalonSRX(Constants.kLeftDriveSlaveA);	// CIM
		mLeftSlaveB = new TalonSRX(Constants.kLeftDriveSlaveB);	// MiniCIM
		
//		mLeftMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		mLeftMaster.configOpenloopRamp(Constants.kDriveVoltageRamp, 0);
		mLeftMaster.configContinuousCurrentLimit(40, 0);
		mLeftMaster.configPeakCurrentLimit(60, 0);
		mLeftMaster.configPeakCurrentDuration(100, 0);
		mLeftMaster.enableCurrentLimit(true);
//		mLeftMaster.enableVoltageCompensation(true);
		
		mLeftMaster.setNeutralMode(Constants.kDriveNeutralMode);
		mLeftSlaveA.setNeutralMode(Constants.kDriveNeutralMode);
		mLeftSlaveB.setNeutralMode(Constants.kDriveNeutralMode);
		
		mLeftSlaveA.set(ControlMode.Follower, Constants.kLeftDriveMaster);
		mLeftSlaveB.set(ControlMode.Follower, Constants.kLeftDriveMaster);
		
		// Right Side
		mRightMaster = new TalonSRX(Constants.kRightDriveMaster);	// CIM
		mRightSlaveA = new TalonSRX(Constants.kRightDriveSlaveA);	// CIM
		mRightSlaveB = new TalonSRX(Constants.kRightDriveSlaveB);	// MiniCIM
		
		mRightMaster.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		mRightMaster.configOpenloopRamp(Constants.kDriveVoltageRamp, 0);
		mRightMaster.configContinuousCurrentLimit(40, 0);
		mRightMaster.configPeakCurrentLimit(60, 0);
		mRightMaster.configPeakCurrentDuration(100, 0);
		mRightMaster.enableCurrentLimit(true);
//		mRightMaster.enableVoltageCompensation(true);
		// Interestingly enough, this side doesn't need to be reversed...
		
		mRightMaster.setNeutralMode(Constants.kDriveNeutralMode);
		mRightSlaveA.setNeutralMode(Constants.kDriveNeutralMode);
		mRightSlaveB.setNeutralMode(Constants.kDriveNeutralMode);
		
		mRightSlaveA.set(ControlMode.Follower, Constants.kRightDriveMaster);
		mRightSlaveB.set(ControlMode.Follower, Constants.kRightDriveMaster);
		
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
		if (throttlePreset != -1) {
			throttle = throttlePreset;
		}
		if (!driveDirectionIsForwards) {
			throttle = -throttle;
			steering = -steering;
		}
		double leftPower = (power + steering) * throttle;
		double rightPower = (power - steering) * throttle;
		setMotorPower(leftPower, rightPower);
		
		SmartDashboard.putNumber("Throttle", throttle);
		SmartDashboard.putNumber("LeftPower", leftPower);
		SmartDashboard.putNumber("RightPower", rightPower);
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
	public void setMotorPower(double left, double right) {
		mLeftMaster.set(ControlMode.PercentOutput, left);
		mLeftSlaveA.follow(mLeftMaster);
		mLeftSlaveB.follow(mLeftMaster);
		mRightMaster.set(ControlMode.PercentOutput, right);
		mRightSlaveA.follow(mRightMaster);
		mRightSlaveB.follow(mRightMaster);
	}
	
	public void setThrottlePreset(ThrottlePreset preset) {
		switch (preset) {
			case LOW:
				throttlePreset = 0.40;
				break;
			case MID:
				throttlePreset = 0.75;
				break;
			case HIGH:
				throttlePreset = 1.00;
				break;
			default: // Also handles ANALOGUE
				throttlePreset = -1; // see line 86
		}
	}
	
	public double getAngle() {
		return navx.getAngle();
	}
	
	public void zeroGyro() {
		navx.reset();
	}
	
	public void driveMotor(int canID, double power) {
		switch (canID) {
		case 1:
			mLeftMaster.set(ControlMode.PercentOutput, power);
			break;
		case 2:
			mLeftSlaveA.set(ControlMode.PercentOutput, power);
			break;
		case 3:
			mLeftSlaveB.set(ControlMode.PercentOutput, power);
			break;
		case 4:
			mRightMaster.set(ControlMode.PercentOutput, power);
			break;
		case 5:
			mRightSlaveA.set(ControlMode.PercentOutput, power);
			break;
		case 6:
			mRightSlaveB.set(ControlMode.PercentOutput, power);
			break;
		default:
			DriverStation.reportWarning("=== Invalid CAN ID input! ===", false);
			break;	
		}
	}
	
	/**
	 * Point turn to an absolute gyro angle TODO: Implement gyro and its zeroing
	 * @param targetAngle
	 */
//	public void actionPointTurn(double targetAngle) {
//		double turnPower = (navx.getAngle() - targetAngle) * Constants.kPPointTurn;
//		arcadeDrive(0, turnPower, 1);
//	}
}
