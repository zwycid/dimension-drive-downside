package kr.ssidang.android.dimensiondrivedownside;

import java.util.List;

import android.graphics.PointF;

public class WorldManager {
	// 세상 안에 들어있는 모든 객체들
	private WorldObject player;
	private List<WorldObject> obtacles;
	
	// 세상 자체에 대한 정보
	private float width;
	private float height;
	
	// 시점(view)에 관련된 사항
	private PointF lookAt;
	private float rotation;
	
	public WorldManager() {
		
	}
}
