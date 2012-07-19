package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Coin extends Unit {
	public static final int COIN_SCORE = 100;
	
	private static final float COIN_SIZE = 10;
	
	public Vector2D pos;
	private float rotation;
	private boolean eaten;
	
	private Matrix mat = new Matrix();
	
	private static final Paint coinPaint;
	static {
		coinPaint = new Paint();
		coinPaint.setColor(0xffffed26);
	}
	
	
	public Coin(float x, float y) {
		this.pos = new Vector2D(x, y);
	}
	
	public void rotate(float delta) {
		rotation -= 8f * delta;
	}
	
	public boolean isHit(Ball ball) {
		return (Vector2D.distance(ball.pos, pos)
				< ball.radius + COIN_SIZE);
	}
	
	public void eat() {
		eaten = true;
	}
	
	public boolean isEaten() {
		return eaten;
	}
	
	public void draw(Canvas canvas, Bitmap image) {
		if (Vis.isFrameMode()) {
			canvas.drawCircle(pos.x, pos.y, COIN_SIZE, coinPaint);
		}
		else {
			GameUtil.transformImage(mat, pos.x, pos.y, COIN_SIZE, rotation, image);
			canvas.drawBitmap(image, mat, null);
		}
	}
}
