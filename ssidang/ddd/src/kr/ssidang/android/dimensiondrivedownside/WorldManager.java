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
	
	// 세상 안에 들어있는 모든 객체들
	private BallObject ball;
	private List<ObstacleObject> obstacles;
	private Particle[] particles;
	
	// 세상 자체에 대한 정보
	private float width;
	private float height;
	
	// 시점(view)에 관련된 사항
	private Vector2D lookAt;

	private Random random;
	private GameParams G;
	private Paint borderPaint;
	
	// 게임 리소스.............
	private Bitmap ballBitmap;
	
	// 디버깅용...........
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

		// TODO 디버그 코드
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
	 * 테스트 메서드; 아무 블럭이나 추가함
	 */
	private void makeRandomWorld() {
		// 10배 너비로 합시다.
		width = 160 * 10;
		height = 160 * 10;
		
		Random r = new Random();
		obstacles.clear();
		// 한 30개 정도는 만들어야 하지 않겠어요.
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
	 * 월드를 그려줍니다.
	 * 
	 * @param canvas
	 */
	public void draw(Canvas canvas) {
		debugCanvas = canvas;
		
		// 시점
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
		
		// 방향 표시 파티클
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
		// 중력
		ball.acc.x = FloatMath.cos(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.y = -FloatMath.sin(G.gravityDirection) * GRAVITY_CONSTANT;
		
		// TODO 공기 저항 대충 처리
		float airFrictionCoeffi = AIR_FRICTION_COEFFICIENT;
		ball.acc.add(ball.velo, -airFrictionCoeffi);

		// 공 속도 구하기
		ball.velo.add(ball.acc, delta);
		
		// 이번에 이동할 위치를 계산합니다.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		// 신나는 충돌처리
		
		// TODO 벽 반사 처리..........
		checkCollisionWithBorder(beforePos, afterPos);
		
		
		// TODO 장애물 반사 처리.........
		for (ObstacleObject ob : obstacles) {
			checkCollisionWithObstacle(ob, beforePos, afterPos);
		}
		
		if (G.debug_) {
			// 공 방향 표시
			Vector2D dir = Vector2D.subtract(afterPos, beforePos).setLength(100);
			GameUtil.drawArrow(canvas, beforePos.x, beforePos.y,
					beforePos.x + dir.x, beforePos.y + dir.y, debugBlue);
		}
		
		// 공의 새 위치를 확정합니다.
		ball.pos.set(afterPos);
		
		// 방향을 표시하는 파티클을 처리합니다.
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
		
		// 우선 충돌 지점을 찾아내고...
		if (Vector2D.findLineIntersection(beforePos, afterPos, edgeStart, edgeStop, interPt)) {
			if (G.debug_)
				debugCanvas.drawCircle(interPt.x, interPt.y, 10, debugOrange);
			
			// penetration depth를 구합니다.
			afterPos.subtract(interPt);

			// 뚫고 들어갈 때만 위치를 교정하고...
			if (normal.dotProd(afterPos) < 0) {
				// 튕겨나간 위치를 찾고...
				afterPos.mirror(normal);
				
				// 충돌 후 속도를 구합니다.
				float speed = velocity.length();
				velocity.set(afterPos, speed * coeffi);
				
//				// 공이 면에 굴러가야 하는지 검사합니다.
//				float d = Vector2D.distanceSq(edgeStart, edgeStop, afterPos);
//				if (d < 10.f) {
//				}
			}
			
			// 위치를 적용합니다.
			afterPos.add(interPt);
		}
	}
}
