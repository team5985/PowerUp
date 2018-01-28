package au.net.projectb;

import edu.wpi.first.wpilibj.IterativeRobot;

public class Robot extends IterativeRobot {
	TeleopController teleop;
	
	@Override
	public void robotInit() {
		teleop = new TeleopController();
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
}
