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
	// 세상 안에 들어있는 모든 객체들
	private BallObject player;
	private List<ObstacleObject> obstacles;
	
	// 세상 자체에 대한 정보
	private float width;
	private float height;
	
	// 시점(view)에 관련된 사항
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
	 * 테스트 메서드; 아무 블럭이나 추가함
	 */
	public void randomizeWorld() {
		// 10배 너비로 합시다.
		width = 160 * 10;
		height = 160 * 10;
		
		// 한 30개 정도는 만들어야 하지 않겠어요.
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
	 * 월드를 그려줍니다.
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		float s = 3.f / 10.001f;
		canvas.setMatrix(null);
		canvas.scale(s, s);
		
		// 월드 테두리
		Paint p = new Paint();
		p.setStyle(Style.STROKE);
		p.setColor(Color.WHITE);
		canvas.drawRect(new RectF(0, 0, width, height), p);
		
		// 프레임 처리
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
		
		// TODO 반사 처리..........
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
