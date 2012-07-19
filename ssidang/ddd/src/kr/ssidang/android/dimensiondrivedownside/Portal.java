package kr.ssidang.android.dimensiondrivedownside;

public class Portal extends Unit {
	public Vector2D pos;
	public float radius;
	
	public Portal(float x, float y, float radius) {
		this.pos = new Vector2D(x, y);
		this.radius = radius;
	}
	

	/**
	 * ���� ���ƴ��� �˻��մϴ�.
	 * 
	 * @param ball
	 * @return	�����̸� ��ģ ����
	 */
	public float isOverlapped(Ball ball) {
		return Vector2D.distance(ball.pos, pos)
				- (ball.radius + radius);
	}
}
