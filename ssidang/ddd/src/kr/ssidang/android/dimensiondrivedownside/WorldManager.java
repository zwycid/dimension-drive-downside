package kr.ssidang.android.dimensiondrivedownside;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.util.FloatMath;
import android.view.MotionEvent;

public class WorldManager {
	public static final float SCALE_UNIT = 160.f;
	public static final float TIME_UNIT = 30.f;
	
	private static final float AIR_FRICTION_COEFFICIENT = .1f;
	private static final float GRAVITY_CONSTANT = 1.5f;
	private static final float BORDER_THICK = 20;
	private static final int MAX_PARTICLE = 15;
	
	private static final int STATE_READY = 0;
	private static final int STATE_PAUSED = 1;
	private static final int STATE_PLAYING = 2;
	private static final int STATE_COMPLETED = 3;
	private static final int STATE_DEAD = 4;
	
	// Stage data
	private float width;
	private float height;
	
	private Portal startPoint;
	private Portal goal;
	private Ball ball;
	private List<Obstacle> obstacles;
	private Particle[] particles;
	
	private int score;
	
	// 시점(view)에 관련된 사항
	private Vector2D lookAt;

	private Random random;
	private GameParams G;
	
	// 게임 리소스.............
	private Paint borderPaint;
	private Bitmap ballBitmap;
	
	// 디버깅용...........
	Canvas debugCanvas;
	Paint debugBlue;
	Paint debugOrange;
	Paint debugText;
	Paint debugRed;
	
	///////////////////////////////////////////////////////////////////////////
	// GameData
	///////////////////////////////////////////////////////////////////////////
	class GameParams {
		boolean debug_ = true;
		
		int state = STATE_READY;
		
		float screenWidth;
		float screenHeight;
		float scaleFactor;
		
		int tick;
		long timestamp;
		float delta;
		
		float gravityDirection;
		float azimuth;
		float pitch;
		float roll;
	}
	
	public WorldManager(Context context) {
		this.obstacles = new ArrayList<Obstacle>();
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
		debugText = new Paint(Paint.ANTI_ALIAS_FLAG);
		debugText.setColor(Color.WHITE);
		debugText.setTextSize(8);
		
		debugBlue = new Paint();
		debugBlue.setColor(Color.BLUE);
		debugBlue.setStrokeWidth(10);
		
		debugOrange = new Paint();
		debugOrange.setColor(0xffff8030);
		debugOrange.setStrokeWidth(10);
		
		debugRed = new Paint(Paint.ANTI_ALIAS_FLAG);
		debugRed.setColor(Color.RED);
		debugRed.setStrokeWidth(3);
	}
	
	public GameParams getGameParams() {
		return G;
	}
	
	public void onTouchEvent(Activity parent, MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			if (G.state == STATE_COMPLETED)
				parent.finish();
			else
				pause();
		}
	}
	
	public void onBackPressed(Activity parent) {
		if (G.state == STATE_COMPLETED)
			parent.finish();
		else if (isPaused())
			parent.finish();
		else {
			pause(true);
		}
	}
	
	public void pause() {
		if (G.state == STATE_PLAYING)
			G.state = STATE_PAUSED;
		else if (G.state == STATE_PAUSED)
			G.state = STATE_PLAYING;
	}
	
	public void pause(boolean set) {
		if (set && G.state == STATE_PLAYING)
			G.state = STATE_PAUSED;
		else if (! set && G.state == STATE_PAUSED)
			G.state = STATE_PLAYING;
	}
	
	public boolean isPaused() {
		return G.state == STATE_PAUSED;
	}

	public void makeMockWorld() {
//		makeRandomWorld();
		makeWorld1();
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
			obstacles.add(new Obstacle(x, y, x + width, y + (6000 / width)));
		}
		
		ball = new Ball(ballBitmap, 0, 0, 20);
		startPoint = new Portal(r.nextInt((int) width), r.nextInt((int) height), 20);
		goal = new Portal(r.nextInt((int) width), r.nextInt((int) height), 20);
	}
	
	private void makeWorld1() {
		width = 160 * 5;
		height = 160 * 5;
		
		obstacles.clear();
		obstacles.add(new Obstacle(400, 300, 700, 500));
		ball = new Ball(ballBitmap, 0, 0, 30);
		startPoint = new Portal(100, 400, 30);
		goal = new Portal(750, 750, 40);
	}
	
	private void resetView(Canvas canvas) {
		canvas.setMatrix(null);
		canvas.scale(G.scaleFactor, G.scaleFactor);
	}
	
	/**
	 * 월드를 그려줍니다.
	 * 
	 * @param canvas
	 */
	public void onRender(Canvas canvas) {
		debugCanvas = canvas;

		switch (G.state) {
		case STATE_READY:
			onRenderReady(canvas);
			break;
		case STATE_PAUSED:
			onRenderPaused(canvas);
			break;
		case STATE_PLAYING:
			onRenderPlaying(canvas);
			break;
		case STATE_COMPLETED:
			onRenderCompleted(canvas);
			break;
		case STATE_DEAD:
			onRenderDead(canvas);
			break;
		}
	}

	private void onRenderReady(Canvas canvas) {
		// 아무것도 안 함
	}

	private void onRenderPaused(Canvas canvas) {
		// 게임 화면을 그려주고...
		onRenderPlaying(canvas);
		
		// 메뉴를 그려줘야 함.
		resetView(canvas);
		canvas.drawColor(0xa0000000);
		canvas.drawText("Paused", G.screenWidth / 2 - 13,
				G.screenHeight / 2 + 3, debugText);
	}

	private void onRenderPlaying(Canvas canvas) {
		// 배경 지우기
		resetView(canvas);
		canvas.drawColor(Color.BLACK);
		
		// 시점
		lookAt.set(ball.pos.x - G.screenWidth / 2,
				ball.pos.y - G.screenHeight / 2);
		canvas.translate(-lookAt.x, -lookAt.y);
		
		if (G.debug_) {
			float s = 3.f / 10.001f;
			canvas.setMatrix(null);
			canvas.scale(s, s);
		}
	
		float border = BORDER_THICK / 2;
		canvas.drawRect(-border, -border, width + border, height + border,
				borderPaint);
		
		// 방향 표시 파티클
		for (Particle p : particles) {
			if (p != null) {
				p.draw(canvas);
			}
		}
		
		// 시작점 / 골 그려주기
		Paint portalPaint = new Paint();
		portalPaint.setColor(0xfff9ae4a);
		canvas.drawCircle(startPoint.pos.x, startPoint.pos.y, startPoint.radius, portalPaint);
		portalPaint.setColor(0xff9afa5a);
		canvas.drawCircle(goal.pos.x, goal.pos.y, goal.radius, portalPaint);
	
		for (Obstacle ob : obstacles) {
			ob.draw(canvas);
		}
		ball.draw(canvas);
	}

	private void onRenderCompleted(Canvas canvas) {
		// TODO onRenderCompleted
		// 게임 화면을 그려주고...
		onRenderPlaying(canvas);
		
		resetView(canvas);
		canvas.drawColor(0x8090a090);
		canvas.drawText("Win", G.screenWidth / 2 - 8,
				G.screenHeight / 2 + 3, debugText);
	}

	private void onRenderDead(Canvas canvas) {
		// TODO onRenderDead
	}

	/**
	 * 물체들을 움직입니다.
	 * 
	 * @param delta
	 */
	public void onFrame(float delta) {
		switch (G.state) {
		case STATE_READY:
			onFrameReady();
			break;
		case STATE_PAUSED:
			onFramePaused();
			break;
		case STATE_PLAYING:
			onFramePlaying(delta);
			break;
		case STATE_COMPLETED:
			onFrameCompleted();
			break;
		case STATE_DEAD:
			onFrameDead();
			break;
		}
		
		// TODO Debug
		if (G.debug_) {
			resetView(debugCanvas);
			
			float fps = 1000.f / (G.delta * TIME_UNIT);
			GameUtil.drawTextMultiline(debugCanvas,
					"Tick: " + G.tick + " (fps: " + fps + ")"
					+ "\nScreen = (" + width + ", " + height + ")"
					+ "\nAzimuth = " + G.azimuth
					+ "\nPitch = " + G.pitch
					+ "\nRoll = " + G.roll
					, 0, 0, debugText);
			
			float centerX = width / 2;
			float centerY = height / 2;
			float offsetX = FloatMath.cos(G.gravityDirection) * 50;
			float offsetY = FloatMath.sin(G.gravityDirection) * 50;
			GameUtil.drawArrow(debugCanvas, centerX, centerY,
					centerX + offsetX, centerY - offsetY, debugRed);
			
		}
	}

	private void onFrameReady() {
		ball.pos.set(startPoint.pos);
		G.state = STATE_PLAYING;
	}

	private void onFramePaused() {
		// 아무것도 안 함
	}

	private void onFramePlaying(float delta) {
		// 중력 및 공기 저항
		ball.acc.x = FloatMath.cos(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.y = -FloatMath.sin(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.add(ball.velo, -AIR_FRICTION_COEFFICIENT);

		// 공 속도
		ball.velo.add(ball.acc, delta);
		
		// 이동할 위치를 계산합니다.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		// 벽 충돌 처리
		checkCollisionWithBorder(beforePos, afterPos);
		
		// 장애물 충돌 처리
		for (Obstacle ob : obstacles) {
			checkCollisionWithObstacle(ob, beforePos, afterPos);
		}
		
		if (G.debug_) {
			// 공 방향 표시
			Vector2D dir = Vector2D.subtract(afterPos, beforePos).setLength(100);
			GameUtil.drawArrow(debugCanvas, beforePos.x, beforePos.y,
					beforePos.x + dir.x, beforePos.y + dir.y, debugBlue);
		}
		
		// 공의 새 위치를 확정합니다.
		ball.pos.set(afterPos);
		
		// 골에 도달했는지 검사합니다.
		if (Vector2D.distance(ball.pos, goal.pos) < ball.radius + goal.radius) {
			G.state = STATE_COMPLETED;
		}
		
		// 배경 파티클 처리
		moveParticles();
	}

	private void onFrameCompleted() {
		// TODO onFrameCompleted
	}

	private void onFrameDead() {
		// TODO onFrameDead
	}

	private void moveParticles() {
		// 방향을 표시하는 파티클을 처리합니다.
		for (int i = 0; i < particles.length; ++i) {
			if (particles[i] == null) {
				// 공 근방에 아무 위치에나 만들기
				float offsetX = random.nextFloat() * G.screenWidth - G.screenWidth / 2;
				float offsetY = random.nextFloat() * G.screenHeight - G.screenHeight / 2;
				float lifetime = random.nextFloat() * 45 + 15;
				particles[i] = new Particle(offsetX, offsetY, lifetime);
			}
			
			Particle p = particles[i];
			if (p.isDead())
				particles[i] = null;
			else {
				// 꼬리 추가
				p.age(G.delta);
				p.addTrail(ball.pos.x, ball.pos.y);
			}
		}
	}

	private void checkCollisionWithObstacle(Obstacle ob,
			Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		RectF bound = ob.getBounds();
		
		float radius = ball.radius;
		
		// 근사적으로 코너도 처리함
//		
//		// left
//		edge1.set(bound.left - ball.radius, bound.top);
//		edge2.set(bound.left - ball.radius, bound.bottom);
//		normal.set(-1, 0);
//		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
//
//		// top
//		edge1.set(bound.left, bound.top - ball.radius);
//		edge2.set(bound.right, bound.top - ball.radius);
//		normal.set(0, -1);
//		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
//		
//		// right
//		edge1.set(bound.right + ball.radius, bound.top);
//		edge2.set(bound.right + ball.radius, bound.bottom);
//		normal.set(1, 0);
//		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
//
//		// bottom
//		edge1.set(bound.left, bound.bottom + ball.radius);
//		edge2.set(bound.right, bound.bottom + ball.radius);
//		normal.set(0, 1);
//		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// left
		edge1.set(bound.left - radius, bound.top - radius);
		edge2.set(bound.left - radius, bound.bottom + radius);
		normal.set(-1, 0);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// top
		edge1.set(bound.left - radius, bound.top - radius);
		edge2.set(bound.right + radius, bound.top - radius);
		normal.set(0, -1);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// right
		edge1.set(bound.right + radius, bound.top - radius);
		edge2.set(bound.right + radius, bound.bottom + radius);
		normal.set(1, 0);
		collisionBallWithEdge(beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(bound.left - radius, bound.bottom + radius);
		edge2.set(bound.right + radius, bound.bottom + radius);
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
