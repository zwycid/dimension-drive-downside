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
	
	// 공 모양과 재질
	public float radius;
	public float restitution	= .8f;
	public float mass			= 1.f;
	public int hitpoint			= 100;
	
	// 그리기 데이터
	private Matrix drawMat		= new Matrix();
	private Bitmap ballBitmap;
	
	private static final Paint ballPaint;
	static {
		ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballPaint.setColor(0xffa7dbf3);
	}
	
	// 디버깅용
	Vector2D debug_pos = new Vector2D();
	Vector2D debug_dir = new Vector2D();
	

	public Ball(Bitmap ballBitmap, float radius) {
		this.ballBitmap = ballBitmap;
		this.radius = radius;
	}
	
	public Ball(Bitmap ballBitmap, float x, float y, float radius) {
		this.ballBitmap = ballBitmap;
		this.pos = new Vector2D(x, y);
		this.radius = radius;
	}

	public void draw(Canvas canvas) {
//		float ballFactor = 2.f / ballBitmap.getWidth();
		canvas.drawCircle(pos.x, pos.y, radius, ballPaint);
		
//		drawMat.setScale(radius * scaleFactor, radius * scaleFactor);
//		drawMat.postTranslate(pos.x - radius, pos.y - radius);
//		canvas.drawBitmap(ballBitmap, drawMat, ballPaint);
	}
}
