package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Sentry extends Unit {
	private static final float SENTRY_SPEED = 1.8f;
	private static final int LOAD_DELAY = 40;
	private static final int FIRE_DELAY = 10;
	private static final int MAGAZINE = 5;
	
	public Vector2D pos;
	public Vector2D dir;
	public float sight;
	public float range;
	
	private int magazine;
	private int shotDelay;
	
	private Matrix mat = new Matrix();
	
	private static final Paint bodyPaint;
	private static final Paint borderPaint;
	static {
		bodyPaint = new Paint();
		bodyPaint.setColor(0xf0f0d0d0);
		bodyPaint.setStrokeWidth(3);
		
		borderPaint = new Paint();
		borderPaint.setColor(0xfff0f0d0);
		borderPaint.setStrokeWidth(5);
	}
	
	// ������
	boolean debug_inSight = false;

	
	public Sentry(float x, float y, float sight) {
		this.pos = new Vector2D(x, y);
		this.dir = new Vector2D(0, -1);
		this.sight = sight;
		this.range = sight * 0.7f;	// �����Ÿ��� �þ��� 70%
	}
	
	/**
	 * ���⸦ �����մϴ�.
	 */
	public void chargeWeapon() {
		if (shotDelay > 0)
			--shotDelay;
		else {
			magazine = MAGAZINE;
		}
	}
	
	/**
	 * ���� ��ó�� ������ �Ѿ��� �߻��մϴ�.
	 * 
	 * @param pool
	 * @param target
	 * @return
	 */
	public boolean tryShootTarget(Bullet[] pool, Vector2D target) {
		// �Ѿ� �ְ� ������ ������ �����Ÿ��� ����ٸ�
		if (shotDelay <= 0 && magazine > 0 && Vector2D.distance(pos, target) < range) {
			// �߻� ����
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
	
	public void trace(Ball ball, float distance) {
		// �Ÿ��� �������� �������ϴ�.
		dir.add(ball.pos).subtract(pos)
			.setLength(Sentry.SENTRY_SPEED * distance / sight);
		
		// ��� ���� ��������� �̵����� �ʽ��ϴ�.
		if (distance > ball.radius * 2.5f)
			pos.add(dir);
	}
	
	public void draw(Canvas canvas, Bitmap image) {
		if (Vis.isFrameMode()) {
			// ������� 40.. ���� 30...
			Vector2D length = new Vector2D(dir).setLength(20);
			Vector2D front = Vector2D.add(pos, length);
			Vector2D center = Vector2D.subtract(pos, length);
			Vector2D side = dir.getNormalDir().setLength(15);
			side.y = -side.y;	// ��ǥ�� ����
			Vector2D left = Vector2D.add(center, side);
			Vector2D right = Vector2D.subtract(center, side);
			
			canvas.drawLine(front.x, front.y, left.x, left.y, bodyPaint);
			canvas.drawLine(front.x, front.y, right.x, right.y, bodyPaint);
			canvas.drawLine(left.x, left.y, right.x, right.y, bodyPaint);
			canvas.drawLine(center.x, center.y, front.x, front.y, borderPaint);
		}
		else {
			GameUtil.transformImage(mat, pos.x, pos.y, 20,
					(float) Math.toDegrees(Math.atan2(dir.y, dir.x)) + 90, image);
			canvas.drawBitmap(image, mat, null);
		}
	}
}
