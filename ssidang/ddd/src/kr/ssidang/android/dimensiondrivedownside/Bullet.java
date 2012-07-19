package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;

public class Bullet {
	private float lifetime;
	
	private float x;
	private float y;
	private float vx;
	private float vy;
	
	// 그리기 데이터
	private Paint pointPaint;
	
	public Bullet() {
		init();
	}
	
	public Bullet(float x, float y, float vx, float vy, float lifetime) {
		init();
		reset(x, y, vx, vy, lifetime);
	}
	
	private void init() {
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(0xa0ff66cc);
		pointPaint.setStrokeWidth(3);
		pointPaint.setStrokeCap(Cap.ROUND);
	}
	
	public void reset(float x, float y, float vx, float vy, float lifetime) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.lifetime = lifetime;
	}
	
	public void age(float dt) {
		lifetime = Math.max(lifetime - dt, 0);
	}
	
	public void trace() {
		x += vx;
		y += vy;
	}
	
	public void kill() {
		lifetime = 0;
	}
	
	public boolean isDead() {
		return lifetime <= 0;
	}
	
	public boolean isHit(Ball ball) {
		return (Vector2D.length(ball.pos.x - x, ball.pos.y - y) < ball.radius);
	}
//	
//	public boolean isInBound(float left, float top, float right, float bottom) {
//		return (left <= x && x <= right) && (top <= y && y <= bottom);
//	}
//	
	public void draw(Canvas canvas) {
		canvas.drawPoint(x, y, pointPaint);
	}
}
