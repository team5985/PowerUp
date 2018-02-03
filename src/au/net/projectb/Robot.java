package au.net.projectb;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	Compressor compressor;
	
	TeleopController teleop;
	Tuning tuner;
	
	@Override
	public void robotInit() {
		compressor = new Compressor(Constants.kPcm);
		
		teleop = new TeleopController();
		tuner = new Tuning();
	}

	@Override
	public void autonomousInit() {
	}

	@Override
	public void autonomousPeriodic() {
	}

	@Override
	public void teleopPeriodic() {
		compressor.start();
		teleop.run();
	}
	
	@Override
	public void testPeriodic() {
		tuner.run();
	}
}
