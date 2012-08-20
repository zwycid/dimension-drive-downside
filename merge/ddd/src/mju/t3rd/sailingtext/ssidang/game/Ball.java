package mju.t3rd.sailingtext.ssidang.game;

import mju.t3rd.sailingtext.ssidang.engine.GameUtil;
import mju.t3rd.sailingtext.ssidang.engine.Vector2D;
import mju.t3rd.sailingtext.ssidang.engine.Vis;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Ball extends Unit {
	public Vector2D pos = new Vector2D();
	public Vector2D velo = new Vector2D();
	public Vector2D acc = new Vector2D();

	public float rotation;
	public float radius;
	public float restitution	= .8f;
	public float mass			= 1.f;
	public int maxHitpoint		= 100;
	public int hitpoint;
	
	
	private Matrix mat = new Matrix();
	private static final Paint ballPaint;
	static {
		ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballPaint.setColor(0xffa7dbf3);
	}
	
	// µð¹ö±ë¿ë
	public Vector2D debug_pos = new Vector2D();
	public Vector2D debug_dir = new Vector2D();
	

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
