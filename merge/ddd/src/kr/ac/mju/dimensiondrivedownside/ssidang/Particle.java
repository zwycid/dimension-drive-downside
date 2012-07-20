package kr.ac.mju.dimensiondrivedownside.ssidang;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Particle {
	private static final int MAX_TRAIL = 30;
	
	private float x;
	private float y;
	
	private float lifetime;
	private float birthday;
	
	private float[] trails = new float[MAX_TRAIL * 2];
	private int first;
	private int last;
	private int count;
	
	private static final Paint linePaint;
	static {
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(0xffffffff);
		linePaint.setStrokeWidth(1);
	}
	
	public Particle() {
	}
	
	public Particle reset(float x, float y, float lifetime) {
		this.x = x;
		this.y = y;
		this.lifetime = lifetime;
		this.birthday = lifetime;
		
		this.first = 0;
		this.last = 0;
		this.count = 0;
		
		return this;
	}
	
	/**
	 * 파티클 꼬리를 추가합니다.
	 * 
	 * @param dx
	 * @param dy
	 */
	public void addTrail(float dx, float dy) {
		trails[last * 2    ] = x + dx;
		trails[last * 2 + 1] = y + dy;
		last = (last + 1) % MAX_TRAIL;
		x += dx;
		y += dy;
		
		// 오래된 것을 덮어씁니다.
		if (count < MAX_TRAIL) {
			count++;
		}
		else {
			first = (first + 1) % MAX_TRAIL;
		}
	}
	
	/**
	 * 수명을 깎습니다.
	 * 
	 * @param dt
	 */
	public void age(float dt) {
		lifetime = Math.max(lifetime - dt, 0);
	}
	
	/**
	 * 파티클을 지웁니다.
	 */
	public void kill() {
		lifetime = 0;
		first = 0;
		last = 0;
		count = 0;
	}
	
	public boolean isDead() {
		return lifetime <= 0;
	}
	
	/**
	 * 파티클이 사각 영역 안에 속하는지 확인합니다.
	 * 
	 * @param left
	 * @param top
	 * @param right
	 * @param bottom
	 * @return
	 */
	public boolean isInBound(float left, float top, float right, float bottom) {
		return Vector2D.inBounds(x, y, left, top, right, bottom);
	}
	
	public void draw(Canvas canvas) {
		int prev = first;
		for (int i = 1; i < count; ++i) {
			int index = (first + i) % MAX_TRAIL;
			
//			linePaint.setAlpha((int) (((count - i) * 255) * (lifetime / birthday) / count));
			// 꼬리로 갈수록 진해지고, 수명이 다 되어갈수록 옅어집니다.
			linePaint.setAlpha((int) ((i * 255) * (lifetime / birthday) / count));
			canvas.drawLine(trails[prev * 2], trails[prev * 2 + 1],
					trails[index * 2], trails[index * 2 + 1], linePaint);
			prev = index;
		}
	}
}
