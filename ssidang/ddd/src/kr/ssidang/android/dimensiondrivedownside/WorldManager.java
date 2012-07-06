package kr.ssidang.android.dimensiondrivedownside;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PointF;
import android.graphics.RectF;

public class WorldManager {
	// ���� �ȿ� ����ִ� ��� ��ü��
	private BallObject player;
	private List<ObstacleObject> obstacles;
	
	// ���� ��ü�� ���� ����
	private float width;
	private float height;
	
	// ����(view)�� ���õ� ����
	private PointF lookAt;
	private float rotation;
	
	public WorldManager() {
		this.obstacles = new ArrayList<ObstacleObject>();
	}
	
	/**
	 * �׽�Ʈ �޼���; �ƹ� ���̳� �߰���
	 */
	public void randomizeWorld() {
		// 5�� �ʺ�� �սô�.
		width = 160 * 5;
		height = 160 * 5;
		
		// �� 50�� ������ ������ ���� �ʰھ��.
		Random r = new Random();
		obstacles.clear();
		for (int i = 0; i < 50; ++i) {
			int area = r.nextInt(3000);
			int width = 30 + r.nextInt(70);
			int x = r.nextInt((int) this.width);
			int y = r.nextInt((int) this.height);
			obstacles.add(new ObstacleObject(x, y, x + width, y + (area / width)));
		}
		
		player = new BallObject(r.nextInt((int) width), r.nextInt((int) height), 30);
	}
	
	/**
	 * ���带 �׷��ݴϴ�.
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		float s = 3.f / 5.01f;
		canvas.setMatrix(null);
		canvas.scale(s, s);
		
		Paint p = new Paint();
		p.setStyle(Style.STROKE);
		p.setColor(Color.WHITE);
		canvas.drawRect(new RectF(0, 0, width, height), p);
		
		for (ObstacleObject ob : obstacles) {
			ob.draw(canvas);
		}
		player.draw(canvas);
	}
}
