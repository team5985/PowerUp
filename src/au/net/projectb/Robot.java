package au.net.projectb;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	TeleopController teleop;
	Tuning tuner;
	
	@Override
	public void robotInit() {
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
		teleop.run();
	}
	
	@Override
	public void testPeriodic() {
		tuner.run();
	}
}
