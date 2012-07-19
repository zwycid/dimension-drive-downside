package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Ball extends Unit {
	Vector2D pos = new Vector2D();
	Vector2D velo = new Vector2D();
	Vector2D acc = new Vector2D();

	float rotation;
	float radius;
	float restitution	= .8f;
	float mass			= 1.f;
	int hitpoint		= 100;
	
	
	private Matrix mat = new Matrix();
	private Bitmap image;
	
	private static final Paint ballPaint;
	static {
		ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballPaint.setColor(0xffa7dbf3);
	}
	
	// µð¹ö±ë¿ë
	Vector2D debug_pos = new Vector2D();
	Vector2D debug_dir = new Vector2D();
	

	public Ball(Bitmap image, float radius) {
		this.image = image;
		this.radius = radius;
	}
	
	public Ball(Bitmap image, float x, float y, float radius) {
		this.image = image;
		this.pos = new Vector2D(x, y);
		this.radius = radius;
	}
	
	public boolean isDead() {
		return (hitpoint <= 0);
	}
	
	public void giveDamage(int amount) {
		hitpoint = Math.max(hitpoint - amount, 0);
	}

	public void draw(Canvas canvas) {
		if (Vis.isFrameMode()) {
			canvas.drawCircle(pos.x, pos.y, radius, ballPaint);
		}
		else {
			GameUtil.tranformImage(mat, pos.x, pos.y, radius,
					(float) Math.toDegrees(-rotation) - 90, image);
			canvas.drawBitmap(image, mat, null);
		}
	}
}
