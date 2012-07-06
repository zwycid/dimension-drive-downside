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
	// 세상 안에 들어있는 모든 객체들
	private BallObject player;
	private List<ObstacleObject> obstacles;
	
	// 세상 자체에 대한 정보
	private float width;
	private float height;
	
	// 시점(view)에 관련된 사항
	private PointF lookAt;
	private float rotation;
	
	public WorldManager() {
		this.obstacles = new ArrayList<ObstacleObject>();
	}
	
	/**
	 * 테스트 메서드; 아무 블럭이나 추가함
	 */
	public void randomizeWorld() {
		// 5배 너비로 합시다.
		width = 160 * 5;
		height = 160 * 5;
		
		// 한 50개 정도는 만들어야 하지 않겠어요.
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
	 * 월드를 그려줍니다.
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
