package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Attractor extends Unit {
	public Vector2D pos;
	public float influence;
	public float power;
	private float rotation;
	
	private Matrix mat = new Matrix();
	
	private static final Paint attrPaint;
	private static final Paint imagePaint;
	static {
		attrPaint = new Paint();
		attrPaint.setColor(0x30dda0dd);
		
		imagePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		imagePaint.setColor(0x80ffffff);
	}
	
	// µð¹ö±ë¿ë
	boolean debug_inInfluence = false;
	Vector2D debug_force = new Vector2D();
	
	
	public Attractor(float x, float y, float influence, float power) {
		this.pos = new Vector2D(x, y);
		this.influence = influence;
		this.power = power;
	}
	
	public void rotate(float delta) {
		rotation += 1f * delta;
	}
	
	public void draw(Canvas canvas, Bitmap image) {
		if (Vis.isFrameMode()) {
			canvas.drawCircle(pos.x, pos.y, influence, attrPaint);
		}
		else {
			GameUtil.tranformImage(mat, pos.x, pos.y, influence, rotation, image);
			canvas.drawBitmap(image, mat, imagePaint);
		}
	}
}
