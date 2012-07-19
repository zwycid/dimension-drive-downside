package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Attractor extends Unit {
	public Vector2D pos;
	public float influence;
	public float power;
	
	private Paint attrPaint;
	
	public Attractor(float x, float y, float influence, float power) {
		this.pos = new Vector2D(x, y);
		this.influence = influence;
		this.power = power;
		
		attrPaint = new Paint();
		attrPaint.setColor(0x30dda0dd);
	}
	
	public void draw(Canvas canvas) {
		canvas.drawCircle(pos.x, pos.y, influence, attrPaint);
	}
}
