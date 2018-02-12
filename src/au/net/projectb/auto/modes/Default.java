package au.net.projectb.auto.modes;

import au.net.projectb.subsystems.Intake;
import au.net.projectb.subsystems.Lift;
import au.net.projectb.subsystems.Lift.LiftPosition;

public class Default extends AutoMode {
	/**
	 * Run a given step in auto iteratively.
	 */
	@Override
	public void runStep(int stepIndex) {
		// Code here is step by step, and concurrent actions are possible if in the same step.
		switch (stepIndex) {
			case 0:
				Intake.getInstance().actionStow();
				Lift.getInstance().actionMoveTo(LiftPosition.SWITCH);
				break;
				
			case 1:
				Intake.getInstance().actionScoreCube();
				break;
		}
		
	}
	
	/**
	 * Each step has an exit/finish condition, which is defined in this method.
	 * @return True if exit condition of a step is reached.
	 */
	@Override
	public boolean getStepIsCompleted(int stepIndex) {
		
		switch (stepIndex) {
			case 0:
				return Lift.getInstance().actionMoveTo(LiftPosition.SWITCH);
			default:
				return false;
		}
		
	}
}
