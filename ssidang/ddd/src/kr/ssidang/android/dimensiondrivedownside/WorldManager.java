package kr.ssidang.android.dimensiondrivedownside;

import java.util.List;

import android.graphics.PointF;

public class WorldManager {
	// ���� �ȿ� ����ִ� ��� ��ü��
	private WorldObject player;
	private List<WorldObject> obtacles;
	
	// ���� ��ü�� ���� ����
	private float width;
	private float height;
	
	// ����(view)�� ���õ� ����
	private PointF lookAt;
	private float rotation;
	
	public WorldManager() {
		
	}
}
