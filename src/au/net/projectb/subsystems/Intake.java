package au.net.projectb.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import au.net.projectb.Constants;
import edu.wpi.first.wpilibj.DoubleSolenoid;

/**
 * Robot's intake system. Intakes, stows, and scores power cubes.
 */
public class Intake extends Subsystem {
	private static Intake instance;
	
	DoubleSolenoid p_claw;	
	TalonSRX m_wrist;
	
	public static Intake getInstance() {
		if (instance == null) {
			instance = new Intake();
		}
		return instance;
	}
	
	private Intake() {
		p_claw = new DoubleSolenoid(Constants.kIntakeClawReverse, Constants.kIntakeClawForward);
		
		m_wrist = new TalonSRX(Constants.kWristMotor);
		m_wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute, 0, 0);
		m_wrist.config_kP(0, Constants.kPWrist, 0);
		m_wrist.config_kP(0, Constants.kIWrist, 0);
		m_wrist.config_kD(0, Constants.kDWrist, 0);
	}
}
