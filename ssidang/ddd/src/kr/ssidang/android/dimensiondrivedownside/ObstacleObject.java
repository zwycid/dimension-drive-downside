package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class ObstacleObject extends WorldObject {
	private RectF bounds;
	
	// 그리기 데이터
	private Paint obstaclePaint;
	
	public ObstacleObject() {
		this.setBounds(new RectF());
		init();
	}
	
	public ObstacleObject(float left, float top, float right, float bottom) {
		this.setBounds(new RectF(left, top, right, bottom));
		init();
	}
	
	private void init() {
		obstaclePaint = new Paint();
		obstaclePaint.setColor(0xff108810);
	}

	public RectF getBounds() {
		return bounds;
	}

	public void setBounds(RectF bounds) {
		this.bounds = new RectF(bounds);
	}
	
	public void setBounds(float left, float top, float right, float bottom) {
		this.bounds = new RectF(left, top, right, bottom);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawRect(bounds, obstaclePaint);
	}
}
