package kr.ssidang.android.dimensiondrivedownside;

public class Portal extends Unit {
	public Vector2D pos;
	public float radius;
	
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
}
