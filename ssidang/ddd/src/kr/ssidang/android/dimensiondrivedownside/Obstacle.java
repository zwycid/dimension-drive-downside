package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class Obstacle extends Unit {
	private RectF bounds;
	
	// 그리기 데이터
	private Paint fillPaint;
	private Paint borderPaint;
	
	public Obstacle() {
		this.setBounds(new RectF());
		init();
	}
	
	public Obstacle(float left, float top, float right, float bottom) {
		this.setBounds(new RectF(left, top, right, bottom));
		init();
	}
	
	private void init() {
		fillPaint = new Paint();
		fillPaint.setStyle(Style.FILL);
		fillPaint.setColor(0xffed1c24);
		
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(0xffd9111b);
		borderPaint.setStrokeWidth(3);
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
		canvas.drawRect(bounds, fillPaint);
		canvas.drawRect(bounds, borderPaint);
	}
}
