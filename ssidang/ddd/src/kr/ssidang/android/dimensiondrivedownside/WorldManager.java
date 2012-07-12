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

public class WorldManager {
	// ���� �ȿ� ����ִ� ��� ��ü��
	private BallObject ball;
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
		boolean debug_ = true;
		
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

	public void makeMockWorld() {
//		makeRandomWorld();
		makeWorld1();
	}
	
	/**
	 * �׽�Ʈ �޼���; �ƹ� ���̳� �߰���
	 */
	private void makeRandomWorld() {
		// 10�� �ʺ�� �սô�.
		width = 160 * 10;
		height = 160 * 10;
		
		Random r = new Random();
		obstacles.clear();
		// �� 30�� ������ ������ ���� �ʰھ��.
		for (int i = 0; i < 30; ++i) {
			int width = 30 + r.nextInt(170);
			int x = r.nextInt((int) this.width);
			int y = r.nextInt((int) this.height);
			obstacles.add(new ObstacleObject(x, y, x + width, y + (6000 / width)));
		}
		
		ball = new BallObject(r.nextInt((int) width), r.nextInt((int) height), 30);
	}
	
	private void makeWorld1() {
		width = 160 * 5;
		height = 160 * 5;
		
		obstacles.clear();
		obstacles.add(new ObstacleObject(400, 300, 700, 500));
		ball = new BallObject(100, 400, 30);
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
		ball.acc.x = FloatMath.cos(G.gravityDirection) * 1.f;
		ball.acc.y = -FloatMath.sin(G.gravityDirection) * 1.f;
		
		for (ObstacleObject ob : obstacles) {
			ob.draw(canvas);
		}
		ball.draw(canvas);
		
		moveObjects(canvas, G.delta);
	}

	private void moveObjects(Canvas canvas, float delta) {
		// TODO moving
		GameUtil.vec2AddScaled(ball.velo, ball.velo, ball.acc, delta);
		
		// õõ��
//		if (G.debug_) {
//			GameUtil.vec2Scale(ball.velo, .8f);
//		}
		
		// �ų��� �浹ó��
		float coeffi = .7f;
		
		// �̵� ���͸� ���մϴ�
		PointF pos = GameUtil.clonePointF(ball.pos);
		PointF dest = GameUtil.vec2AddScaled(new PointF(), pos, ball.velo, delta);
		
		// ���� ���Ϳ� ���⿡ ������ ���͸� ���մϴ�.
		PointF dir = GameUtil.vec2Sub(dest, pos);
		PointF dirCross = GameUtil.vec2Rotation(GameUtil.clonePointF(dir),
				(float) (Math.PI / 2));
		GameUtil.vec2SetLength(dirCross, ball.radius);
		
		// �� ���̸�ŭ �̵��� ������
		float length = GameUtil.vec2Length(dir) + ball.radius;
		PointF dirScaled = new PointF(dir.x, dir.y);
		GameUtil.vec2SetLength(dirScaled, length);
		
		// ���� �浹�� ���� ���ϱ�
		PointF pos1 = new PointF(pos.x + dirCross.x, pos.y - dirCross.y);
		PointF pos2 = new PointF(pos.x - dirCross.x, pos.y + dirCross.y);
		PointF dest1 = new PointF(pos1.x + dirScaled.x, pos1.y + dirScaled.y);
		PointF dest2 = new PointF(pos2.x + dirScaled.x, pos2.y + dirScaled.y);
		
		// �� �浹 ó��....................
		PointF edge1 = new PointF();
		PointF edge2 = new PointF();
		PointF normal = new PointF();
		PointF interPt = new PointF();
		PointF reflect = new PointF();
		List<PointF> collisionList = new ArrayList<PointF>();
		
		for (ObstacleObject ob : obstacles) {
			RectF bound = ob.getBounds();
			
			// left
			edge1.x = bound.left;
			edge1.y = bound.top;
			edge2.x = bound.left;
			edge2.y = bound.bottom;
			normal.x = -1;
			normal.y = 0;
			if (GameUtil.findLineIntersection(pos1, dest1, edge1, edge2, interPt)) {
				collisionList.add(GameUtil.clonePointF(interPt));
				
				reflect.x = interPt.x;
				reflect.y = interPt.y;
				GameUtil.vec2Sub(reflect, dest1, interPt);
				GameUtil.vec2Mirror(reflect, normal);
				GameUtil.vec2Add(dest, interPt, reflect);
				
				ball.velo.x = -ball.velo.x * coeffi;
			}
			if (GameUtil.findLineIntersection(pos2, dest2, edge1, edge2, interPt)) {
				collisionList.add(GameUtil.clonePointF(interPt));
				
				reflect.x = interPt.x;
				reflect.y = interPt.y;
				GameUtil.vec2Sub(reflect, dest1, interPt);
				GameUtil.vec2Mirror(reflect, normal);
				GameUtil.vec2Add(dest, interPt, reflect);
				
				ball.velo.x = -ball.velo.x * coeffi;
			}
			
			// top
			edge1.x = bound.left;
			edge1.y = bound.top;
			edge2.x = bound.right;
			edge2.y = bound.top;
			normal.x = 0;
			normal.y = -1;
			if (GameUtil.findLineIntersection(pos1, dest1, edge1, edge2, interPt)) {
				ball.velo.y = -ball.velo.y * coeffi;
				dest.y = bound.top - ball.radius;
				collisionList.add(GameUtil.clonePointF(interPt));
			}
			if (GameUtil.findLineIntersection(pos2, dest2, edge1, edge2, interPt)) {
				ball.velo.y = -ball.velo.y * coeffi;
				dest.y = bound.top - ball.radius;
				collisionList.add(GameUtil.clonePointF(interPt));
			}
			
			// right
			edge1.x = bound.right;
			edge1.y = bound.top;
			edge2.x = bound.right;
			edge2.y = bound.bottom;
			normal.x = 1;
			normal.y = 0;
			if (GameUtil.findLineIntersection(pos1, dest1, edge1, edge2, interPt)) {
				ball.velo.x = -ball.velo.x * coeffi;
				dest.x = bound.right + ball.radius;
				collisionList.add(GameUtil.clonePointF(interPt));
			}
			if (GameUtil.findLineIntersection(pos2, dest2, edge1, edge2, interPt)) {
				ball.velo.x = -ball.velo.x * coeffi;
				dest.x = bound.right + ball.radius;
				collisionList.add(GameUtil.clonePointF(interPt));
			}
			
			// bottom
			edge1.x = bound.left;
			edge1.y = bound.bottom;
			edge2.x = bound.right;
			edge2.y = bound.bottom;
			normal.x = 0;
			normal.y = 1;
			if (GameUtil.findLineIntersection(pos1, dest1, edge1, edge2, interPt)) {
				ball.velo.y = -ball.velo.y * coeffi;
				dest.y = bound.bottom + ball.radius;
				collisionList.add(GameUtil.clonePointF(interPt));
			}
			if (GameUtil.findLineIntersection(pos2, dest2, edge1, edge2, interPt)) {
				ball.velo.y = -ball.velo.y * coeffi;
				dest.y = bound.bottom + ball.radius;
				collisionList.add(GameUtil.clonePointF(interPt));
			}
		}
		
		if (G.debug_) {
			Paint p = new Paint();
			p.setColor(Color.BLUE);
			p.setStrokeWidth(10);
			
			Paint pp = new Paint();
			pp.setColor(0xffff8030);
			pp.setStrokeWidth(10);
			
			// �浹����
//			canvas.drawLine(pos1.x, pos1.y, pos1.x + dirScaled.x, pos1.y + dirScaled.y, p);
//			canvas.drawLine(pos2.x, pos2.y, pos2.x + dirScaled.x, pos2.y + dirScaled.y, p);
			
			// �浹�� �׸���
			for (PointF pt : collisionList) {
				canvas.drawCircle(pt.x, pt.y, 10, pp);
			}
			
			// �� ���� ǥ��
			GameUtil.vec2SetLength(dir, 100);
			GameUtil.drawArrow(canvas, ball.pos.x, ball.pos.y,
					ball.pos.x + dir.x, ball.pos.y + dir.y, p);
		}

		ball.pos = dest;
//		player.pos.x += (player.velo.x * delta);
//		player.pos.y += (player.velo.y * delta);
		
		// TODO �ݻ� ó��..........
		if (ball.pos.x < 0) {
			ball.pos.x = 0;
			ball.velo.x = -ball.velo.x * coeffi;
		}
		if (ball.pos.x > width) {
			ball.pos.x = width;
			ball.velo.x = -ball.velo.x * coeffi;
		}
		if (ball.pos.y < 0) {
			ball.pos.y = 0;
			ball.velo.y = -ball.velo.y * coeffi;
		}
		if (ball.pos.y > height) {
			ball.pos.y = height;
			ball.velo.y = -ball.velo.y * coeffi;
		}
		
		// TODO air friction
		float friction = .1f;
		ball.acc.x += (-ball.velo.x * friction);
		ball.acc.y += (-ball.velo.y * friction);
	}
}
