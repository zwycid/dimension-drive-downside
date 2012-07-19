package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Particle {
	public static final int MAX_TRAIL = 30;
	
	private float lifetime;
	private float birthday;
	
	private float offsetX;
	private float offsetY;
	
	private float[] trails;
	private int first;
	private int last;
	private int count;
	
	// 그리기 데이터
	private Paint linePaint;
	
	public Particle(float offsetX, float offsetY, float lifetime) {
		reset(offsetX, offsetY, lifetime);
		init();
	}
	
	private void init() {
		this.trails = new float[MAX_TRAIL * 2];
		
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(0xffffffff);
		linePaint.setStrokeWidth(1);
	}
	
	public void reset(float offsetX, float offsetY, float lifetime) {
		this.offsetX = offsetX;
		this.offsetY = offsetY;
		this.lifetime = lifetime;
		this.birthday = lifetime;
	}
	
	public void addTrail(float x, float y) {
		trails[last * 2    ] = x - offsetX;
		trails[last * 2 + 1] = y - offsetY;
		last = (last + 1) % MAX_TRAIL;
		
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
	
	public boolean isDead() {
		return lifetime <= 0;
	}
	
	public void draw(Canvas canvas) {
		int prev = first;
		for (int i = 1; i < count; ++i) {
			int index = (first + i) % MAX_TRAIL;
			
//			linePaint.setAlpha((count - i) * 255 / count);
			linePaint.setAlpha((int) ((i * 255) * (lifetime / birthday) / count));
			canvas.drawLine(trails[prev * 2], trails[prev * 2 + 1],
					trails[index * 2], trails[index * 2 + 1], linePaint);
			prev = index;
		}
	}
}
