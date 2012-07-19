package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Particle {
	private static final int MAX_TRAIL = 30;
	
	private float lifetime;
	private float birthday;
	
	private float x;
	private float y;
	
	private float[] trails;
	private int first;
	private int last;
	private int count;
	
	// 그리기 데이터
	private Paint linePaint;
	
	public Particle() {
		init();
	}
	
	public Particle(float x, float y, float lifetime) {
		init();
		reset(x, y, lifetime);
	}
	
	private void init() {
		this.trails = new float[MAX_TRAIL * 2];
		
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(0xffffffff);
		linePaint.setStrokeWidth(1);
	}
	
	public void reset(float x, float y, float lifetime) {
		this.x = x;
		this.y = y;
		this.lifetime = lifetime;
		this.birthday = lifetime;
		
		first = 0;
		last = 0;
		count = 0;
	}
	
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
	
	public void age(float dt) {
		lifetime = Math.max(lifetime - dt, 0);
	}
	
	public void kill() {
		lifetime = 0;
		first = 0;
		last = 0;
		count = 0;
	}
	
	public boolean isDead() {
		return lifetime <= 0;
	}
	
	public boolean isInBound(float left, float top, float right, float bottom) {
		return (left <= x && x <= right) && (top <= y && y <= bottom);
	}
	
	public void draw(Canvas canvas) {
		int prev = first;
		for (int i = 1; i < count; ++i) {
			int index = (first + i) % MAX_TRAIL;
			
//			linePaint.setAlpha((int) (((count - i) * 255) * (lifetime / birthday) / count));
			linePaint.setAlpha((int) ((i * 255) * (lifetime / birthday) / count));
			canvas.drawLine(trails[prev * 2], trails[prev * 2 + 1],
					trails[index * 2], trails[index * 2 + 1], linePaint);
			prev = index;
		}
	}
}
