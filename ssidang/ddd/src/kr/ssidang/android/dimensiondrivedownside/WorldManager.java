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
//	private PointF lookAt;
//	private float rotation;

	private GameParams G;
	
	// ������...........
	Canvas debugCanvas;
	Paint debugBlue;
	Paint debugOrange;
	
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
		

		// TODO ����� �ڵ�
		debugBlue = new Paint();
		debugBlue.setColor(Color.BLUE);
		debugBlue.setStrokeWidth(10);
		
		debugOrange = new Paint();
		debugOrange.setColor(0xffff8030);
		debugOrange.setStrokeWidth(10);
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
		debugCanvas = canvas;
		
		float s = 3.f / 10.001f;
		canvas.setMatrix(null);
		canvas.scale(s, s);
		
		// ���� �׵θ�
		Paint p = new Paint();
		p.setStyle(Style.STROKE);
		p.setColor(Color.WHITE);
		canvas.drawRect(new RectF(0, 0, width, height), p);
		
		for (ObstacleObject ob : obstacles) {
			ob.draw(canvas);
		}
		ball.draw(canvas);
	}

	public void moveObjects(Canvas canvas, float delta) {
		// �߷�
		ball.acc.x = FloatMath.cos(G.gravityDirection) * 1.5f;
		ball.acc.y = -FloatMath.sin(G.gravityDirection) * 1.5f;
		
		// TODO ���� ���� ���� ó��
		float airFrictionCoeffi = .1f;
		ball.acc.add(ball.velo, -airFrictionCoeffi);

		// �� �ӵ� ���ϱ�
		ball.velo.add(ball.acc, delta);
		
		// �̹��� �̵��� ��ġ�� ����մϴ�.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		// �ų��� �浹ó��
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
		
		// TODO �� �ݻ� ó��..........
		// left
		edge1.set(ball.radius, 0);
		edge2.set(ball.radius, height);
		normal.set(1, 0);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);

		// top
		edge1.set(0, ball.radius);
		edge2.set(width, ball.radius);
		normal.set(0, 1);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// right
		edge1.set(width - ball.radius, 0);
		edge2.set(width - ball.radius, height);
		normal.set(-1, 0);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(0, height - ball.radius);
		edge2.set(width, height - ball.radius);
		normal.set(0, -1);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		
		// TODO ��ֹ� �ݻ� ó��.........
		for (ObstacleObject ob : obstacles) {
			RectF bound = ob.getBounds();
			
			// left
			edge1.set(bound.left - ball.radius, bound.top);
			edge2.set(bound.left - ball.radius, bound.bottom);
			normal.set(-1, 0);
			collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);

			// top
			edge1.set(bound.left, bound.top - ball.radius);
			edge2.set(bound.right, bound.top - ball.radius);
			normal.set(0, -1);
			collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
			
			// right
			edge1.set(bound.right + ball.radius, bound.top);
			edge2.set(bound.right + ball.radius, bound.bottom);
			normal.set(1, 0);
			collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);

			// bottom
			edge1.set(bound.left, bound.bottom + ball.radius);
			edge2.set(bound.right, bound.bottom + ball.radius);
			normal.set(0, 1);
			collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		}
		
		if (G.debug_) {
			// �� ���� ǥ��
			Vector2D dir = Vector2D.subtract(afterPos, beforePos).setLength(100);
			GameUtil.drawArrow(canvas, beforePos.x, beforePos.y,
					beforePos.x + dir.x, beforePos.y + dir.y, debugBlue);
		}
		
		// ���� �� ��ġ�� Ȯ���մϴ�.
		ball.pos.set(afterPos);
	}

	private void collisionBallWithEdge(Vector2D beforePos, Vector2D afterPos,
			Vector2D edgeStart, Vector2D edgeStop, Vector2D velocity,
			Vector2D normal) {
		float coeffi = ball.restitution;
		Vector2D interPt = new Vector2D();
		
		// �켱 �浹 ������ ã�Ƴ���...
		if (Vector2D.findLineIntersection(beforePos, afterPos, edgeStart, edgeStop, interPt)) {
			if (G.debug_)
				debugCanvas.drawCircle(interPt.x, interPt.y, 10, debugOrange);
			
			// penetration depth�� ���մϴ�.
			beforePos.set(interPt);
			afterPos.subtract(interPt).mirror(normal);
			
			// �浹 �� �ӵ��� ���մϴ�.
			float speed = velocity.length() * coeffi;
			velocity.set(afterPos, speed);
			
			// ������ ��ġ�� ���մϴ�.
			afterPos.add(beforePos);
			
			// �����ߴµ� ������ ���� �������� �ʾ�����...
			float d = Vector2D.distanceSq(edgeStart, edgeStop, afterPos);
			if (d < 1.f) {
				
			}
		}
	}
}
