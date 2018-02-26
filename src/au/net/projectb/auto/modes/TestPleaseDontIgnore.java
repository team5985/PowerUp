package au.net.projectb.auto.modes;

import au.net.projectb.auto.AutoController.FieldPosition;

public class TestPleaseDontIgnore extends AutoMode {
	FieldPosition startPosition;  // Robot start position
	FieldPosition switchPosition;
	FieldPosition scalePosition;

	@Override
	public void setFieldPositions(FieldPosition startPos, FieldPosition switchPos, FieldPosition scalePos) {
		startPosition = startPos;
		switchPosition = switchPos;
		scalePosition = scalePos;
	}

	@Override
	public void runStep(int stepIndex) {
		
	}

	@Override
	public boolean getStepIsCompleted(int stepIndex) {
		// TODO Auto-generated method stub
		return false;
	}

}
