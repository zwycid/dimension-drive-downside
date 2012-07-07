package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.Log;

public class BallObject extends WorldObject {
	public PointF pos;
	public PointF velo;
	public PointF acc;
	public float radius;
	
	// 그리기 데이터
	private Paint ballPaint;

	public BallObject(float radius) {
		this.pos = new PointF();
		this.velo = new PointF();
		this.acc = new PointF();
		this.radius = radius;
		init();
	}
	
	public BallObject(float x, float y, float radius) {
		this.pos = new PointF(x, y);
		this.velo = new PointF();
		this.acc = new PointF();
		this.radius = radius;
		init();
	}

	private void init() {
		ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballPaint.setColor(0xffa7dbf3);
	}

	public void draw(Canvas canvas) {
		canvas.drawCircle(pos.x, pos.y, radius, ballPaint);
	}
}
