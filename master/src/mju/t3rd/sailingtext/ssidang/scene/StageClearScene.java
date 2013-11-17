package mju.t3rd.sailingtext.ssidang.scene;

import mju.t3rd.sailingtext.ssidang.engine.Vis;
import android.graphics.Canvas;

public class StageClearScene extends Scene {

	public void onFrame() {
		mManager.stopScenePropagation();
	}

	public void onRender(Canvas canvas) {
		Game.resetCamera(canvas, mData.scaleRatio);
		canvas.drawColor(0xcc0909a0);
		canvas.drawText("Goal", mData.screenWidth / 2 - 8,
				mData.screenHeight / 2 + 3, Vis.white);
	}

}
