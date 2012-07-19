package kr.ssidang.android.dimensiondrivedownside;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.FloatMath;

public class WorldManager {
	private static final float AIR_FRICTION_COEFFICIENT = .1f;
	private static final float GRAVITY_CONSTANT = 1.5f;
	private static final float BORDER_THICK = 20;
	private static final int MAX_PARTICLE = 15;
	
	// ���� �ȿ� ����ִ� ��� ��ü��
	private BallObject ball;
	private List<ObstacleObject> obstacles;
	private Particle[] particles;
	
	// ���� ��ü�� ���� ����
	private float width;
	private float height;
	
	// ����(view)�� ���õ� ����
	private Vector2D lookAt;

	private Random random;
	private GameParams G;
	private Paint borderPaint;
	
	// ���� ���ҽ�.............
	private Bitmap ballBitmap;
	
	// ������...........
	Canvas debugCanvas;
	Paint debugBlue;
	Paint debugOrange;
	
	///////////////////////////////////////////////////////////////////////////
	// GameData
	///////////////////////////////////////////////////////////////////////////
	class GameParams {
		boolean debug_ = true;
		
		float screenWidth;
		float screenHeight;
		
		float delta;
		float gravityDirection;
		
		float azimuth;
		float pitch;
		float roll;
	}
	
	public WorldManager(Context context) {
		this.obstacles = new ArrayList<ObstacleObject>();
//		this.particles = new ArrayList<Particle>();
		this.particles = new Particle[MAX_PARTICLE];
		
		this.random = new Random();
		this.G = new GameParams();
		this.lookAt = new Vector2D();
		
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(0xff596691);
		borderPaint.setStrokeWidth(BORDER_THICK);
		
		ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_ball);

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
		makeRandomWorld();
//		makeWorld1();
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
		
		ball = new BallObject(ballBitmap,
				r.nextInt((int) width), r.nextInt((int) height), 30);
	}
	
	private void makeWorld1() {
		width = 160 * 5;
		height = 160 * 5;
		
		obstacles.clear();
		obstacles.add(new ObstacleObject(400, 300, 700, 500));
		ball = new BallObject(ballBitmap, 100, 400, 30);
	}
	
	/**
	 * ���带 �׷��ݴϴ�.
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		debugCanvas = canvas;
		
		// ����
		lookAt.set(ball.pos.x - G.screenWidth / 2,
				ball.pos.y - G.screenHeight / 2);
		canvas.translate(-lookAt.x, -lookAt.y);
//		if (G.debug_) {
//			float s = 3.f / 10.001f;
//			canvas.setMatrix(null);
//			canvas.scale(s, s);
//		}
//		else {
//			canvas.translate(-lookAt.x, -lookAt.y);
//		}

		float border = BORDER_THICK / 2;
		canvas.drawRect(-border, -border, width + border, height + border,
				borderPaint);
		
		// ���� ǥ�� ��ƼŬ
		for (Particle p : particles) {
			if (p != null) {
				p.draw(canvas);
			}
		}

		for (ObstacleObject ob : obstacles) {
			ob.draw(canvas);
		}
		ball.draw(canvas);
	}

	public void moveObjects(Canvas canvas, float delta) {
		// �߷�
		ball.acc.x = FloatMath.cos(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.y = -FloatMath.sin(G.gravityDirection) * GRAVITY_CONSTANT;
		
		// TODO ���� ���� ���� ó��
		float airFrictionCoeffi = AIR_FRICTION_COEFFICIENT;
		ball.acc.add(ball.velo, -airFrictionCoeffi);

		// �� �ӵ� ���ϱ�
		ball.velo.add(ball.acc, delta);
		
		// �̹��� �̵��� ��ġ�� ����մϴ�.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		// �ų��� �浹ó��
		
		// TODO �� �ݻ� ó��..........
		checkCollisionWithBorder(beforePos, afterPos);
		
		
		// TODO ��ֹ� �ݻ� ó��.........
		for (ObstacleObject ob : obstacles) {
			checkCollisionWithObstacle(ob, beforePos, afterPos);
		}
		
		if (G.debug_) {
			// �� ���� ǥ��
			Vector2D dir = Vector2D.subtract(afterPos, beforePos).setLength(100);
			GameUtil.drawArrow(canvas, beforePos.x, beforePos.y,
					beforePos.x + dir.x, beforePos.y + dir.y, debugBlue);
		}
		
		// ���� �� ��ġ�� Ȯ���մϴ�.
		ball.pos.set(afterPos);
		
		// ������ ǥ���ϴ� ��ƼŬ�� ó���մϴ�.
		for (int i = 0; i < particles.length; ++i) {
			if (particles[i] == null) {
				float offsetX = random.nextFloat() * G.screenWidth - G.screenWidth / 2;
				float offsetY = random.nextFloat() * G.screenHeight - G.screenHeight / 2;
				float lifetime = random.nextFloat() * 45 + 15;
				particles[i] = new Particle(offsetX, offsetY, lifetime);
			}
			
			Particle p = particles[i];
			if (p.isDead())
				particles[i] = null;
			else {
				p.age(G.delta);
				p.addTrail(ball.pos.x, ball.pos.y);
			}
		}
	}

	private void checkCollisionWithObstacle(ObstacleObject ob,
			Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
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

	private void checkCollisionWithBorder(Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
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
		edge2.set(width - ball.radius, height + 10);
		normal.set(-1, 0);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(0, height - ball.radius);
		edge2.set(width + 10, height - ball.radius);
		normal.set(0, -1);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
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
			afterPos.subtract(interPt);

			// �հ� �� ���� ��ġ�� �����ϰ�...
			if (normal.dotProd(afterPos) < 0) {
				// ƨ�ܳ��� ��ġ�� ã��...
				afterPos.mirror(normal);
				
				// �浹 �� �ӵ��� ���մϴ�.
				float speed = velocity.length();
				velocity.set(afterPos, speed * coeffi);
				
//				// ���� �鿡 �������� �ϴ��� �˻��մϴ�.
//				float d = Vector2D.distanceSq(edgeStart, edgeStop, afterPos);
//				if (d < 10.f) {
//				}
			}
			
			// ��ġ�� �����մϴ�.
			afterPos.add(interPt);
		}
	}
}
