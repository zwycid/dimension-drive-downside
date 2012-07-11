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
import android.util.FloatMath;
import android.util.Log;

public class WorldManager {
	// ���� �ȿ� ����ִ� ��� ��ü��
	private BallObject player;
	private List<ObstacleObject> obstacles;
	
	// ���� ��ü�� ���� ����
	private float width;
	private float height;
	
	// ����(view)�� ���õ� ����
	private PointF lookAt;
//	private float rotation;

	private GameParams G;
	
	///////////////////////////////////////////////////////////////////////////
	// GameData
	///////////////////////////////////////////////////////////////////////////
	class GameParams {
		float delta;
		float gravityDirection;
		
		float azimuth;
		float pitch;
		float roll;
	}
	
	public WorldManager() {
		this.obstacles = new ArrayList<ObstacleObject>();
		this.G = new GameParams();
	}
	
	public GameParams getGameParams() {
		return G;
	}

	/**
	 * �׽�Ʈ �޼���; �ƹ� ���̳� �߰���
	 */
	public void randomizeWorld() {
		// 10�� �ʺ�� �սô�.
		width = 160 * 10;
		height = 160 * 10;
		
		// �� 30�� ������ ������ ���� �ʰھ��.
		Random r = new Random();
		obstacles.clear();
		for (int i = 0; i < 30; ++i) {
			int width = 30 + r.nextInt(170);
			int x = r.nextInt((int) this.width);
			int y = r.nextInt((int) this.height);
			obstacles.add(new ObstacleObject(x, y, x + width, y + (6000 / width)));
		}
		
		player = new BallObject(r.nextInt((int) width), r.nextInt((int) height), 30);
	}
	
	/**
	 * ���带 �׷��ݴϴ�.
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		float s = 3.f / 10.001f;
		canvas.setMatrix(null);
		canvas.scale(s, s);
		
		// ���� �׵θ�
		Paint p = new Paint();
		p.setStyle(Style.STROKE);
		p.setColor(Color.WHITE);
		canvas.drawRect(new RectF(0, 0, width, height), p);
		
		// ������ ó��
		player.acc.x = FloatMath.cos(G.gravityDirection) * 1.f;
		player.acc.y = -FloatMath.sin(G.gravityDirection) * 1.f;
		moveObjects(G.delta);
		
		for (ObstacleObject ob : obstacles) {
			ob.draw(canvas);
		}
		player.draw(canvas);
	}

	private void moveObjects(float delta) {
		// TODO moving
		player.velo.x += (player.acc.x * delta);
		player.velo.y += (player.acc.y * delta);

		player.pos.x += (player.velo.x * delta);
		player.pos.y += (player.velo.y * delta);
		
		// TODO �ݻ� ó��..........
		float coeffi = .7f;
		if (player.pos.x < 0) {
			player.pos.x = 0;
			player.velo.x = -player.velo.x * coeffi;
		}
		if (player.pos.x > width) {
			player.pos.x = width;
			player.velo.x = -player.velo.x * coeffi;
		}
		if (player.pos.y < 0) {
			player.pos.y = 0;
			player.velo.y = -player.velo.y * coeffi;
		}
		if (player.pos.y > height) {
			player.pos.y = height;
			player.velo.y = -player.velo.y * coeffi;
		}
		
		// TODO air friction
		float friction = .1f;
		player.acc.x += (-player.velo.x * friction);
		player.acc.y += (-player.velo.y * friction);
	}
}
