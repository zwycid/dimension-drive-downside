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
	
	
	private Matrix mat		= new Matrix();
	private Bitmap ballBitmap;
	
	private static final Paint ballPaint;
	static {
		ballPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		ballPaint.setColor(0xffa7dbf3);
	}
	
	// ������
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
	
	public boolean isDead() {
		return (hitpoint <= 0);
	}
	
	public void giveDamage(int amount) {
		hitpoint = Math.max(hitpoint - amount, 0);
	}

	public void draw(Canvas canvas) {
		canvas.drawCircle(pos.x, pos.y, radius, ballPaint);
		
		// (1) ������ �������� ���߰�
		// (2) ���ϴ� ������ ȸ����Ų ��
		// (3) ���ϴ� ũ��� ������ ����
		// (4) �� ��ġ�� �����ϴ�.
		float length = (ballBitmap.getWidth() + ballBitmap.getHeight()) / 2;
		
		mat.setRotate((float) Math.toDegrees(-rotation) - 90);
		mat.preTranslate(-length / 2, -length / 2);
		mat.postScale(radius * 2 / length, radius * 2 / length);
		mat.postTranslate(pos.x, pos.y);
		canvas.drawBitmap(ballBitmap, mat, ballPaint);
	}
}
