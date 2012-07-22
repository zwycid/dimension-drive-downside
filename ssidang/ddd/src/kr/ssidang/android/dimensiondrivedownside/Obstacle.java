package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;

public class Obstacle extends Unit {
	float left;
	float top;
	float right;
	float bottom;
	boolean visible = true;
	
	private static final Paint fillPaint;
	private static final Paint borderPaint;
	static {
		fillPaint = new Paint();
		fillPaint.setStyle(Style.FILL);
		fillPaint.setColor(0xee8d2124);
		
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(0xff561416);
		borderPaint.setStrokeWidth(3);
	}
	
	public Obstacle() {
	}
	
	public Obstacle(float left, float top, float right, float bottom) {
		this.setBounds(left, top, right, bottom);
	}
	
	public void setBounds(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public void draw(Canvas canvas) {
		if (visible) {
			canvas.drawRect(left, top, right, bottom, fillPaint);
			canvas.drawRect(left, top, right, bottom, borderPaint);
		}
	}
}
