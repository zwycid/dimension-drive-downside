package kr.ssidang.android.dimensiondrivedownside;

public class Portal extends Unit {
	public Vector2D pos;
	public float radius;
	
	public Portal(float x, float y, float radius) {
		this.pos = new Vector2D(x, y);
		this.radius = radius;
	}
}
