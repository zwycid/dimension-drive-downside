package mju.t3rd.sailingtext.ssidang.scene;

import mju.t3rd.sailingtext.ssidang.engine.Vis;
import android.graphics.Canvas;

public class GameOverScene extends Scene {

	public void onFrame() {
		mManager.stopScenePropagation();
	}

	public void onRender(Canvas canvas) {
		Game.resetCamera(canvas, mData.scaleRatio);
		canvas.drawColor(0xcca00909);
		canvas.drawText("Dead", mData.screenWidth / 2 - 8,
				mData.screenHeight / 2 + 3, Vis.white);
	}

}
