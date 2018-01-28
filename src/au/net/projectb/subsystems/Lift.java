package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import au.net.projectb.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;

/**
 * Robot's lift system. Travels and holds at a range of heights, as well as extends and retracts. Carries the Intake.
 */
public class Lift extends Subsystem {
	private static Lift instance;
	
	public enum LiftPosition {
		GROUND,
		SWITCH,
		SCALE_LO,
		SCALE_MI,
		SCALE_HI
	}
	
	DoubleSolenoid p_extension;	
	TalonSRX m_elbow;
	
	public static Lift getInstance() {
		if (instance == null) {
			instance = new Lift();
		}
		return instance;
	}
	
	private Lift() {
		p_extension = new DoubleSolenoid(Constants.kBobcatCylinderReverse, Constants.kBobcatCylinderForward);
		
		m_elbow = new TalonSRX(Constants.kBobcatMotor);
		m_elbow.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		m_elbow.config_kP(0, Constants.kPElbow, 0);
		m_elbow.config_kP(0, Constants.kIElbow, 0);
		m_elbow.config_kD(0, Constants.kDElbow, 0);
	}
	
	/**
	 * Set what height the lift aims to be in.
	 * @param setpoint
	 * @return True if within a range of the setpoint.
	 */
	public boolean setPosition(LiftPosition setpoint) {
		m_elbow.set(ControlMode.Position, 0); // TODO: Check positions and zeroing logic
		return m_elbow.getClosedLoopError(0) < Constants.kElbowErrorWindow;
	}
	
	/**
	 * Sets the position of the extension in the arm. True is out, false is in.
	 * @param extend
	 * @return True
	 */
	public boolean setExtension(boolean extend) {
		if (extend) {
			p_extension.set(Value.kForward);
		} else {
			p_extension.set(Value.kReverse);
		}
		return true;
	}
}
