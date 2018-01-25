package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import au.net.projectb.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Robot's lift system. Travels and holds at a range of heights, as well as extends and retracts. Carries the Intake.
 */
public class Lift extends Subsystem {
	private static Lift instance;
	
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
}
