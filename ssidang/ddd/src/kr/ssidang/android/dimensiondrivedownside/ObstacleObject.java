package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;

public class ObstacleObject extends WorldObject {
	private RectF bounds;
	
	// 그리기 데이터
	private Paint fillPaint;
	private Paint borderPaint;
	
	public ObstacleObject() {
		this.setBounds(new RectF());
		init();
	}
	
	public ObstacleObject(float left, float top, float right, float bottom) {
		this.setBounds(new RectF(left, top, right, bottom));
		init();
	}
	
	private void init() {
		fillPaint = new Paint();
		fillPaint.setStyle(Style.FILL);
		fillPaint.setColor(0xff0e8b78);
		
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(0xff0e6074);
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
