package au.net.projectb.auto.modes;

public abstract class AutoMode {
	public abstract void runStep(int stepIndex);
	public abstract boolean getStepIsCompleted(int stepIndex);
}
