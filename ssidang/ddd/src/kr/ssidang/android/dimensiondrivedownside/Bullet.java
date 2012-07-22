package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;

public class Bullet {
	private static final float BULLET_SIZE = 3;
	
	private float x;		// 위치
	private float y;		// 위치
	private float vx;		// 속도
	private float vy;		// 속도
	private float lifetime;	// 수명
	
	private static final Paint pointPaint;
	static {
		pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		pointPaint.setColor(0xa0ff66cc);
		pointPaint.setStrokeWidth(BULLET_SIZE);
		pointPaint.setStrokeCap(Cap.ROUND);
	}
	
	public Bullet() {
	}
	
	/**
	 * 총알의 위치, 속도, 수명을 설정합니다.
	 * 자기 자신을 돌려줍니다.
	 */
	public Bullet reset(float x, float y, float vx, float vy, float lifetime) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.lifetime = lifetime;
		return this;
	}
	
	/**
	 * 총알의 수명을 깎습니다.
	 * 
	 * @param dt	지난 시간
	 */
	public void age(float dt) {
		lifetime = Math.max(lifetime - dt, 0);
	}
	
	/**
	 * 총알을 움직입니다.
	 */
	public void trace() {
		x += vx;
		y += vy;
	}
	
	/**
	 * 총알을 없앱니다.
	 */
	public void kill() {
		lifetime = 0;
	}
	
	/**
	 * 날아가고 있는 총알인지 알려줍니다.
	 */
	public boolean isDead() {
		return lifetime <= 0;
	}
	
	/**
	 * 총알이 공에 맞았는지 검사합니다.
	 */
	public boolean isHit(Ball ball) {
		return (Vector2D.length(ball.pos.x - x, ball.pos.y - y)
				< ball.radius + BULLET_SIZE);
	}

	/**
	 * 총알을 그려줍니다.
	 */
	public void draw(Canvas canvas) {
		canvas.drawPoint(x, y, pointPaint);
	}
}
