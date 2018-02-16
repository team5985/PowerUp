package au.net.projectb.auto.modes;

import au.net.projectb.auto.AutoController.FieldPosition;

public abstract class AutoMode {
	public abstract void setFieldPositions(FieldPosition startPos, FieldPosition switchPos, FieldPosition scalePos);
	public abstract void runStep(int stepIndex);
	public abstract boolean getStepIsCompleted(int stepIndex);
}
