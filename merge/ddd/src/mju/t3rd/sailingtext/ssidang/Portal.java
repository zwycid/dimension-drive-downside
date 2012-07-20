package mju.t3rd.sailingtext.ssidang;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

public class Portal extends Unit {
	public Vector2D pos;
	public float radius;
	private float rotation;
	
	
	private Matrix mat = new Matrix();
	
	private static final Paint portalPaint;
	static {
		portalPaint = new Paint();
		portalPaint.setColor(0xff9afa5a);
	}
	
	public Portal(float x, float y, float radius) {
		this.pos = new Vector2D(x, y);
		this.radius = radius;
	}
	

	/**
	 * 공과 겹쳤는지 검사합니다.
	 * 
	 * @param ball
	 * @return	음수이면 겹친 상태
	 */
	public float isOverlapped(Ball ball) {
		return Vector2D.distance(ball.pos, pos)
				- (ball.radius + radius);
	}
	
	public void rotate(float delta) {
		rotation += 5f * delta;
	}
	
	public void draw(Canvas canvas, Bitmap image) {
		if (Vis.isFrameMode()) {
			canvas.drawCircle(pos.x, pos.y, radius, portalPaint);
		}
		else {
			GameUtil.transformImage(mat, pos.x, pos.y, radius, rotation, image);
			canvas.drawBitmap(image, mat, null);
		}
	}
}
