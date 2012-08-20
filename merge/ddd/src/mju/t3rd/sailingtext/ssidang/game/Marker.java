package mju.t3rd.sailingtext.ssidang.game;

import mju.t3rd.sailingtext.ssidang.engine.GameUtil;
import mju.t3rd.sailingtext.ssidang.engine.Vector2D;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Marker extends Unit {
	private float left;
	private float top;
	private float right;
	private float bottom;
	private boolean visible;
	
	private Vector2D pos = new Vector2D();
	private Vector2D dir = new Vector2D();
	private float scale = .1f;
	private float degrees;
	
	private Matrix mat = new Matrix();
	
	public Marker(float left, float top, float right, float bottom) {
		this.left = left;
		this.top = top;
		this.right = right;
		this.bottom = bottom;
	}
	
	public void setDirection(Vector2D origin, Vector2D target) {
		dir.x = target.x - origin.x;
		dir.y = target.y - origin.y;
		dir.normalize();
		degrees = (float) Math.toDegrees(Math.atan2(dir.y, dir.x)) - 90;
		
		// 화면 끝 어디에 둬야 할지 찾습니다.
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		
		// 화면 안에 들어왔으면 안 그립니다.
		float left   = origin.x + this.left;
		float top    = origin.y + this.top;
		float right  = origin.x + this.right;
		float bottom = origin.y + this.bottom;
		visible = origin.inBounds(left, top, right, bottom);

		if (visible) {
			// left
			edge1.set(left, top);
			edge2.set(left, bottom);
			checkEdge(origin, target, edge1, edge2);
			
			// top
			edge1.set(left, top);
			edge2.set(right, top);
			checkEdge(origin, target, edge1, edge2);
			
			// right
			edge1.set(right, top);
			edge2.set(right, bottom);
			checkEdge(origin, target, edge1, edge2);
			
			// bottom
			edge1.set(left, bottom);
			edge2.set(right, bottom);
			checkEdge(origin, target, edge1, edge2);
		}
	}

	private boolean checkEdge(Vector2D origin, Vector2D target,
			Vector2D edge1, Vector2D edge2) {
		Vector2D interPt = new Vector2D();
		if (Vector2D.findLineIntersection(origin, target, edge1, edge2, interPt)) {
			pos.set(interPt);
			pos.add(dir, -1.2f / scale);
			return true;
		}
		return false;
	}
	
	public void draw(Canvas canvas, Bitmap image) {
		if (visible) {
			GameUtil.transform(mat, pos.x, pos.y, degrees, scale, image.getWidth(), image.getHeight());
			canvas.drawBitmap(image, mat, null);
		}
	}
}
