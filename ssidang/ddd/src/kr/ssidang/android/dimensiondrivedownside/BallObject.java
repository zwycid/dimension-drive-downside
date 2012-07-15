package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;

public class BallObject extends WorldObject {
	public Vector2D pos = new Vector2D();
	public Vector2D velo = new Vector2D();
	public Vector2D acc = new Vector2D();
	public float radius;
	
	// 그리기 데이터
	private Paint ballPaint;

	public BallObject(float radius) {
		this.radius = radius;
		init();
	}
	
	public BallObject(float x, float y, float radius) {
		this.pos = new Vector2D(x, y);
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
