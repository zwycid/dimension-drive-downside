package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;

public class Ball extends Unit {
	public Vector2D pos = new Vector2D();
	public Vector2D velo = new Vector2D();
	public Vector2D acc = new Vector2D();
	
	// �� ���� ����
	public float radius;
	public float restitution;
	public float mass;
//	public int hitpoint;
	
	// �׸��� ������
	private Matrix drawMat;
	private Bitmap ballBitmap;
	private Paint ballPaint;

	public Ball(Bitmap ballBitmap, float radius) {
		this.ballBitmap = ballBitmap;
		this.radius = radius;
		init();
	}
	
	public Ball(Bitmap ballBitmap, float x, float y, float radius) {
		this.ballBitmap = ballBitmap;
		this.pos = new Vector2D(x, y);
		this.radius = radius;
		init();
	}

	private void init() {
		this.drawMat = new Matrix();
		this.restitution = .8f;
		this.mass = 1.f;
		
		ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballPaint.setColor(0xffa7dbf3);
		
	}

	public void draw(Canvas canvas) {
		float ballFactor = 2.f / ballBitmap.getWidth();
		canvas.drawCircle(pos.x, pos.y, radius, ballPaint);
		
//		drawMat.setScale(radius * scaleFactor, radius * scaleFactor);
//		drawMat.postTranslate(pos.x - radius, pos.y - radius);
//		canvas.drawBitmap(ballBitmap, drawMat, ballPaint);
	}
}
