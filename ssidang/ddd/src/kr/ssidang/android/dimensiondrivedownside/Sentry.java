package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Sentry extends Unit {
	public static final float SENTRY_SPEED = 1.8f;
	public static final int LOAD_DELAY = 40;
	public static final int FIRE_DELAY = 10;
	public static final int MAGAZINE = 5;
	
	public Vector2D pos;
	public Vector2D dir;
	public float sight;
	public float range;
	
	private int magazine;
	private int shotDelay;
	
	private Paint bodyPaint;
	private Paint borderPaint;
	
	public Sentry(float x, float y, float sight) {
		this.pos = new Vector2D(x, y);
		this.dir = new Vector2D(0, -1);
		this.sight = sight;
		this.range = sight * 0.7f;	// 사정거리는 시야의 70%
		
		bodyPaint = new Paint();
		bodyPaint.setColor(0xf0f0d0d0);
		bodyPaint.setStrokeWidth(3);
		
		borderPaint = new Paint();
		borderPaint.setColor(0xfff0f0d0);
		borderPaint.setStrokeWidth(5);
	}
	
	public void chargeWeapon() {
		if (shotDelay > 0)
			--shotDelay;
		else {
			magazine = MAGAZINE;
		}
	}
	
	public boolean tryShootTarget(Bullet[] pool, Vector2D target) {
		// 총알 있고 딜레이 지났고 사정거리에 들었다면
		if (shotDelay <= 0 && magazine > 0 && Vector2D.distance(pos, target) < range) {
			// 발사 가능
			for (Bullet bull : pool) {
				if (bull.isDead()) {
					Vector2D dir = new Vector2D(this.dir).setLength(5);
					bull.reset(pos.x, pos.y, dir.x, dir.y, 80);
					
					--magazine;
					shotDelay = (magazine == 0 ? LOAD_DELAY : FIRE_DELAY);
					return true;
				}
			}
		}
		return false;
	}
	
	public void draw(Canvas canvas) {
		// 전장길이 40..
		// 가로 30...
		Vector2D length = new Vector2D(dir).setLength(20);
		Vector2D front = Vector2D.add(pos, length);
		Vector2D center = Vector2D.subtract(pos, length);
		Vector2D side = dir.getNormalDir().setLength(15);
		side.y = -side.y;	// 좌표계 보정
		Vector2D left = Vector2D.add(center, side);
		Vector2D right = Vector2D.subtract(center, side);
		
		canvas.drawLine(front.x, front.y, left.x, left.y, bodyPaint);
		canvas.drawLine(front.x, front.y, right.x, right.y, bodyPaint);
		canvas.drawLine(left.x, left.y, right.x, right.y, bodyPaint);
		canvas.drawLine(center.x, center.y, front.x, front.y, borderPaint);
	}
}
