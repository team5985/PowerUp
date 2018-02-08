package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import au.net.projectb.Constants;

/**
 * Robot's lift system. Travels and holds at a range of heights. Carries the Intake.
 */
public class Lift extends Subsystem {
	private static Lift m_LiftInstance;
	
	public enum LiftPosition {
		GROUND,
		SWITCH,
		SCALE_LO,
		SCALE_MI,
		SCALE_HI
	}
		
	TalonSRX mElbow;
	
	public static Lift getInstance() {
		if (m_LiftInstance == null) {
			m_LiftInstance = new Lift();
		}
		return m_LiftInstance;
	}
	
	private Lift() {		
		mElbow = new TalonSRX(Constants.kBobcatMotor);
		mElbow.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		mElbow.setInverted(true); // Positive voltage goes down, so reverse output so positive is up. Encoder is also positive up.
		mElbow.enableVoltageCompensation(true);
		mElbow.setNeutralMode(NeutralMode.Coast);
		updateConstants();
	}
	
	/**
	 * Sometimes constants aren't constant, and you want to change them while the robot is running (tuning)
	 * This method should be called periodically by Tuning so the gains are actually changed, and once at construction to set up initially
	 */
	public void updateConstants() {
		mElbow.config_kP(0, Constants.kPElbow, 0);
		mElbow.config_kI(0, Constants.kIElbow, 0);
		mElbow.config_kD(0, Constants.kDElbow, 0);
		mElbow.configPeakOutputForward(Constants.kElbowMaxVoltage / 12, 0);
		mElbow.configPeakOutputReverse(-Constants.kElbowMaxVoltage / 12, 0);
	}
	
	/**
	 * Set what height the lift aims to be in.
	 * @param setpoint
	 * @return True if within a range of the setpoint.
	 */
	public boolean actionMoveTo(LiftPosition setpoint) {
		switch (setpoint) {
			case GROUND:
				setElbowPosition(Constants.kElbowGroundPosition);
				break;
			case SWITCH:
				setElbowPosition(Constants.kElbowSwitchPosition);
				break;
			case SCALE_LO:
				setElbowPosition(Constants.kElbowScaleLoPosition);
				break;
			case SCALE_MI:
				setElbowPosition(Constants.kElbowScaleMiPosition);
				break;
			case SCALE_HI:
				setElbowPosition(Constants.kElbowScaleHiPosition);
				break;
		}
		return mElbow.getClosedLoopError(0) < Constants.kElbowErrorWindow;
	}
	
	/**
	 * @return Elbow sensor's encoder position
	 */
	public int getElbowPosition() {
		return mElbow.getSelectedSensorPosition(0);
	}
	
	/**
	 * @return True if the arm would be outside the 16" rule if the intake were deployed.
	 */
	public boolean getArmIsInIllegalPos() {
		return mElbow.getSelectedSensorPosition(0) > Constants.kElbowIllegalPosLowerBound && mElbow.getSelectedSensorPosition(0) < Constants.kElbowIllegalPosUpperBound;
	}
	
	/**
	 * Wrapper for moving the arm.
	 * @param setpoint
	 */
	public void setElbowPosition(double setpoint) {
		// Possible arm position safety
		mElbow.set(ControlMode.Position, setpoint);
	}
	
	/**
	 * For manual control, set power of arm
	 * @param power
	 */
	public void setElbowPower(double power) {
		mElbow.set(ControlMode.PercentOutput, power);
	}
}
