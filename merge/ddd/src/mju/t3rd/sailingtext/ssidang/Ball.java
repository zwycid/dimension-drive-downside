package mju.t3rd.sailingtext.ssidang;

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
	int maxHitpoint		= 100;
	int hitpoint;
	
	
	private Matrix mat = new Matrix();
	private static final Paint ballPaint;
	static {
		ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballPaint.setColor(0xffa7dbf3);
	}
	
	// µð¹ö±ë¿ë
	Vector2D debug_pos = new Vector2D();
	Vector2D debug_dir = new Vector2D();
	

	public Ball(float x, float y, float radius) {
		this.pos.set(x, y);
		this.radius = radius;
		this.hitpoint = maxHitpoint;
	}
	
	public boolean isDead() {
		return (hitpoint <= 0);
	}
	
	public void giveDamage(int amount) {
		hitpoint = Math.max(hitpoint - amount, 0);
	}

	public void draw(Canvas canvas, Bitmap image) {
		if (Vis.isFrameMode()) {
			canvas.drawCircle(pos.x, pos.y, radius, ballPaint);
		}
		else {
			GameUtil.transformImage(mat, pos.x, pos.y, radius,
					(float) Math.toDegrees(-rotation) - 90, image);
			canvas.drawBitmap(image, mat, null);
		}
	}
}
