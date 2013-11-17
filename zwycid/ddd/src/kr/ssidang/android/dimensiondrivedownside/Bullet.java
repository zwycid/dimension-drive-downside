package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;

public class Bullet {
	private static final float BULLET_SIZE = 3;
	
	private float x;		// ��ġ
	private float y;		// ��ġ
	private float vx;		// �ӵ�
	private float vy;		// �ӵ�
	private float lifetime;	// ����
	
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
	 * �Ѿ��� ��ġ, �ӵ�, ������ �����մϴ�.
	 * �ڱ� �ڽ��� �����ݴϴ�.
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
	 * �Ѿ��� ������ ����ϴ�.
	 * 
	 * @param dt	���� �ð�
	 */
	public void age(float dt) {
		lifetime = Math.max(lifetime - dt, 0);
	}
	
	/**
	 * �Ѿ��� �����Դϴ�.
	 */
	public void trace() {
		x += vx;
		y += vy;
	}
	
	/**
	 * �Ѿ��� ���۴ϴ�.
	 */
	public void kill() {
		lifetime = 0;
	}
	
	/**
	 * ���ư��� �ִ� �Ѿ����� �˷��ݴϴ�.
	 */
	public boolean isDead() {
		return lifetime <= 0;
	}
	
	/**
	 * �Ѿ��� ���� �¾Ҵ��� �˻��մϴ�.
	 */
	public boolean isHit(Ball ball) {
		return (Vector2D.length(ball.pos.x - x, ball.pos.y - y)
				< ball.radius + BULLET_SIZE);
	}

	/**
	 * �Ѿ��� �׷��ݴϴ�.
	 */
	public void draw(Canvas canvas) {
		canvas.drawPoint(x, y, pointPaint);
	}
}
