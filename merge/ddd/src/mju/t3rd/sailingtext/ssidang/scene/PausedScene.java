package mju.t3rd.sailingtext.ssidang.scene;

import mju.t3rd.sailingtext.ssidang.engine.Vis;
import android.graphics.Canvas;
import android.os.SystemClock;

public class PausedScene extends Scene {

	public void onFrame() {
		mData.timestamp = SystemClock.uptimeMillis();
		mManager.stopScenePropagation();
	}

	public void onRender(Canvas canvas) {
		// TODO 메뉴 그려주기
		Game.resetCamera(canvas, mData.scaleRatio);
		canvas.drawColor(0xa0000000);
		canvas.drawText("Paused", mData.screenWidth / 2 - 13,
				mData.screenHeight / 2 + 3, Vis.white);
	}

}
