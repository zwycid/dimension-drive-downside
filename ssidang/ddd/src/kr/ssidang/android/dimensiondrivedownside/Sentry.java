package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Sentry extends Unit {
	public static final float SENTRY_SPEED = 1.8f;
	
	public Vector2D pos;
	public Vector2D dir;
	public float sight;
	
	private Paint bodyPaint;
	private Paint borderPaint;
	
	public Sentry(float x, float y, float sight) {
		this.pos = new Vector2D(x, y);
		this.dir = new Vector2D(0, -1);
		this.sight = sight;
		
		bodyPaint = new Paint();
		bodyPaint.setColor(0xf0f0d0d0);
		bodyPaint.setStrokeWidth(3);
		
		borderPaint = new Paint();
		borderPaint.setColor(0xfff0f0d0);
		borderPaint.setStrokeWidth(5);
	}
	
	public void draw(Canvas canvas) {
		// 전장길이 40..
		// 가로 30...
		Vector2D length = new Vector2D(dir).setLength(20);
		Vector2D front = Vector2D.add(pos, length);
		Vector2D center = Vector2D.subtract(pos, length);
		Vector2D side = dir.getNormalDir().setLength(15);
		side.y = -side.y;
		Vector2D left = Vector2D.add(center, side);
		Vector2D right = Vector2D.subtract(center, side);
		
		canvas.drawLine(front.x, front.y, left.x, left.y, bodyPaint);
		canvas.drawLine(front.x, front.y, right.x, right.y, bodyPaint);
		canvas.drawLine(left.x, left.y, right.x, right.y, bodyPaint);
		canvas.drawLine(center.x, center.y, front.x, front.y, borderPaint);
	}
}
