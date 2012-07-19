package kr.ssidang.android.dimensiondrivedownside;

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
import android.view.KeyEvent;
import android.view.MotionEvent;

public class WorldManager {
	public static final float SCALE_UNIT = 160.f;
	public static final float TIME_UNIT = 30.f;
	
	private static final int DEBUG_LEVEL = 3;
	
	private static final float AIR_FRICTION_COEFFICIENT = .1f;
	private static final float GRAVITY_CONSTANT = .5f;
	private static final float BORDER_THICK = 20;
	private static final int MAX_PARTICLE = 30;
	
	private static final int STATE_READY = 0;
	private static final int STATE_PAUSED = 1;
	private static final int STATE_PLAYING = 2;
	private static final int STATE_COMPLETED = 3;
	private static final int STATE_DEAD = 4;
	
	// Stage data
	private Stage stage;
	private Ball ball;
	private Particle[] particles;
	private int score;
	
	// ����(view)�� ���õ� ����
	private Vector2D lookAt;

	private Random random;
	private GameParams G;
	
	// ���� ���ҽ�.............
	private Paint borderPaint;
	private Bitmap ballBitmap;
	
	// ������...........
	Canvas debugCanvas;
	Paint debugBlue;
	Paint debugOrange;
	Paint debugText;
	Paint debugRed;
	Paint debugGreen;
	
	///////////////////////////////////////////////////////////////////////////
	// GameData
	///////////////////////////////////////////////////////////////////////////
	class GameParams {
		int debug_;
		
		int state = STATE_READY;
		
		float screenWidth;
		float screenHeight;
		float scaleFactor;
		
		int tick;
		long timestamp;
		float delta;
		
		long playTime;
		long baseTime;
		
		float gravityDirection;
		float azimuth;
		float pitch;
		float roll;
	}
	
	public WorldManager(Context context) {
		this.particles = new Particle[MAX_PARTICLE];
		this.random = new Random();
		this.G = new GameParams();
		this.lookAt = new Vector2D();
		
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(0xff596691);
		borderPaint.setStrokeWidth(BORDER_THICK);
		
		ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_ball);

		{
			// TODO ����� �ڵ�
			debugText = new Paint(Paint.ANTI_ALIAS_FLAG);
			debugText.setColor(Color.WHITE);
			debugText.setTextSize(8);
			
			debugBlue = new Paint();
			debugBlue.setColor(Color.BLUE);
			debugBlue.setStrokeWidth(7);
			
			debugOrange = new Paint();
			debugOrange.setColor(0xffff8030);
			debugOrange.setStrokeWidth(5);
			
			debugRed = new Paint(Paint.ANTI_ALIAS_FLAG);
			debugRed.setColor(Color.RED);
			debugRed.setStrokeWidth(3);
			
			debugGreen = new Paint(Paint.ANTI_ALIAS_FLAG);
			debugGreen.setColor(0x4030f030);
			debugGreen.setStrokeWidth(3);
		}
	}
	
	public GameParams getGameParams() {
		return G;
	}
	
	public boolean onTouchEvent(Activity parent, MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			if (G.state == STATE_COMPLETED)
				parent.finish();
			else
				pause();
			return true;
		}
		return false;
	}
	
	public boolean onKeyDown(Activity parent, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			// TODO ����� ���� ����
			G.debug_ = (G.debug_ + 1) % DEBUG_LEVEL;
			return true;
		}
		return false;
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
			pause(true);
		else if (G.state == STATE_PAUSED)
			pause(false);
	}
	
	public void pause(boolean set) {
		if (set && G.state == STATE_PLAYING) {
			// �Ͻ� ����
			G.state = STATE_PAUSED;
		}
		else if (! set && G.state == STATE_PAUSED) {
			// �ٽ� ����
			G.baseTime = G.timestamp;
			G.state = STATE_PLAYING;
		}
	}
	
	public boolean isPaused() {
		return G.state == STATE_PAUSED;
	}

	public void makeMockWorld() {
		stage = Stage.fromData("[mapsize],800,800/[s],100,400/[f],750,750/[b],400,300,700,500/" +
								"[a],200,620/[c],256,93/[c],640,142/[c],380,621/[c],684,386/");
	}
	
//	/**
//	 * �׽�Ʈ �޼���; �ƹ� ���̳� �߰���
//	 */
//	private void makeRandomWorld() {
//		// 10�� �ʺ�� �սô�.
//		width = 160 * 10;
//		height = 160 * 10;
//		
//		Random r = new Random();
//		obstacles.clear();
//		// �� 30�� ������ ������ ���� �ʰھ��.
//		for (int i = 0; i < 30; ++i) {
//			int width = 30 + r.nextInt(170);
//			int x = r.nextInt((int) this.width);
//			int y = r.nextInt((int) this.height);
//			obstacles.add(new Obstacle(x, y, x + width, y + (6000 / width)));
//		}
//		
//		ball = new Ball(ballBitmap, 0, 0, 20);
//		startPoint = new Portal(r.nextInt((int) width), r.nextInt((int) height), 20);
//		goal = new Portal(r.nextInt((int) width), r.nextInt((int) height), 20);
//	}
//	
//	private void makeWorld1() {
//		width = 160 * 5;
//		height = 160 * 5;
//		
//		obstacles.clear();
//		obstacles.add(new Obstacle(400, 300, 700, 500));
//		ball = new Ball(ballBitmap, 0, 0, 30);
//		startPoint = new Portal(100, 400, 30);
//		goal = new Portal(750, 750, 40);
//	}
	
	private void resetView(Canvas canvas) {
		canvas.setMatrix(null);
		canvas.scale(G.scaleFactor, G.scaleFactor);
	}
	
	/**
	 * ���带 �׷��ݴϴ�.
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
		// �ƹ��͵� �� ��
	}

	private void onRenderPaused(Canvas canvas) {
		// ���� ȭ���� �׷��ְ�...
		onRenderPlaying(canvas);
		
		// �޴��� �׷���� ��.
		resetView(canvas);
		canvas.drawColor(0xa0000000);
		canvas.drawText("Paused", G.screenWidth / 2 - 13,
				G.screenHeight / 2 + 3, debugText);
	}

	private void onRenderPlaying(Canvas canvas) {
		// ��� �����
		resetView(canvas);
		canvas.drawColor(Color.BLACK);
		
		// ����
		lookAt.set(ball.pos.x - G.screenWidth / 2,
				ball.pos.y - G.screenHeight / 2);
		canvas.translate(-lookAt.x, -lookAt.y);
		
		if (G.debug_ > 1) {
			float s = G.scaleFactor / stage.width * SCALE_UNIT * 0.99f;
			canvas.setMatrix(null);
			canvas.scale(s, s);
		}
		
		// ���� ǥ�� ��ƼŬ
		for (Particle p : particles) {
			if (p != null) {
				p.draw(canvas);
			}
		}
		
		float border = BORDER_THICK / 2;
		canvas.drawRect(-border, -border,
				stage.width + border, stage.height + border,
				borderPaint);
		
		// ������ / �� �׸��� (�ӽ�)
		Paint portalPaint = new Paint();
		portalPaint.setColor(0xfff9ae4a);
		canvas.drawCircle(stage.start.pos.x, stage.start.pos.y, stage.start.radius, portalPaint);
		portalPaint.setColor(0xff9afa5a);
		canvas.drawCircle(stage.goal.pos.x, stage.goal.pos.y, stage.goal.radius, portalPaint);
		
		// ���� �׸���
		for (Attractor att: stage.attractors) {
			att.draw(canvas);
		}
	
		// ��ֹ� �׸���
		for (Obstacle ob : stage.obstacles) {
			ob.draw(canvas);
		}
		
		// sentry �׸���
		for (Sentry sen : stage.sentries) {
			sen.draw(canvas);
		}
		
		// �� �׸���
		ball.draw(canvas);
	}

	private void onRenderCompleted(Canvas canvas) {
		// TODO onRenderCompleted
		// ���� ȭ���� �׷��ְ�...
		onRenderPlaying(canvas);
		
		resetView(canvas);
		canvas.drawColor(0x809090a0);
		canvas.drawText("Win", G.screenWidth / 2 - 8,
				G.screenHeight / 2 + 3, debugText);
	}

	private void onRenderDead(Canvas canvas) {
		// TODO onRenderDead
	}

	/**
	 * ��ü���� �����Դϴ�.
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
		
		updateTick();
		
		// TODO Debug
		if (G.debug_ > 0) {
			resetView(debugCanvas);
			
			float fps = 1000.f / (G.delta * TIME_UNIT);
			GameUtil.drawTextMultiline(debugCanvas,
					"Tick: " + G.tick + " / " + (G.playTime) + "ms (fps: " + fps + ")"
					+ "\nScreen = (" + G.screenWidth + ", " + G.screenHeight + ")"
//					+ "\nAzimuth = " + G.azimuth
//					+ "\nPitch = " + G.pitch
//					+ "\nRoll = " + G.roll
					+ "\nMap = (" + stage.width + ", " + stage.height + ")"
					+ "\nPos = (" + ball.pos.x + ", " + ball.pos.y + ")"
					, 0, 0, debugText);
			
			float centerX = G.screenWidth / 2;
			float centerY = G.screenHeight / 2;
			float offsetX = FloatMath.cos(G.gravityDirection) * 50;
			float offsetY = FloatMath.sin(G.gravityDirection) * 50;
			GameUtil.drawArrow(debugCanvas, centerX, centerY,
					centerX + offsetX, centerY - offsetY, debugRed);
			
		}
	}

	private void onFrameReady() {
		ball = new Ball(ballBitmap, stage.start.pos.x, stage.start.pos.y, 20);
		G.timestamp = System.currentTimeMillis();
		
		G.playTime = 0;
		G.baseTime = G.timestamp;
		
		G.state = STATE_PLAYING;
	}

	private void onFramePaused() {
		G.timestamp = System.currentTimeMillis();
	}

	private void onFramePlaying(float delta) {
		// ���� �ð� ������Ʈ
		G.playTime += (G.timestamp - G.baseTime);
		G.baseTime = G.timestamp;
		
		// �߷� �� ���� ����
		ball.acc.x = FloatMath.cos(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.y = -FloatMath.sin(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.add(ball.velo, -AIR_FRICTION_COEFFICIENT);
		
		// ���� ������ ó��
		for (Attractor att : stage.attractors) {
			attractBall(ball, att);
		}

		// �� �ӵ�
		ball.velo.add(ball.acc, delta);
		
		// �̵��� ��ġ�� ����մϴ�.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		// �� �浹 ó��
		collisionBallWithBorder(ball, beforePos, afterPos);
		
		// ��ֹ� �浹 ó��
		for (Obstacle ob : stage.obstacles) {
			collisionBallWithObstacle(ball, ob, beforePos, afterPos);
		}
		
		if (G.debug_ > 0) {
			// �� ���� ǥ��
			Vector2D dir = Vector2D.subtract(afterPos, beforePos).setLength(50);
			GameUtil.drawArrow(debugCanvas, beforePos.x, beforePos.y,
					beforePos.x + dir.x, beforePos.y + dir.y, debugBlue);
//			GameUtil.drawArrow(debugCanvas, beforePos.x, beforePos.y,
//					afterPos.x, afterPos.y, debugBlue);
		}
		
		// ���� �� ��ġ�� Ȯ���մϴ�.
		ball.pos.set(afterPos);
		
		// sentry ó��
		for (Sentry sen : stage.sentries) {
			traceSentries(ball, sen);
		}
		
		// ��� ��ƼŬ ó��
		moveParticles(beforePos, afterPos);
		
		// �� �����ߴ��� �˻��մϴ�.
		if (Vector2D.distance(ball.pos, stage.goal.pos) < ball.radius + stage.goal.radius) {
			G.state = STATE_COMPLETED;
		}
	}

	private void onFrameCompleted() {
		// TODO onFrameCompleted
	}

	private void onFrameDead() {
		// TODO onFrameDead
	}

	private void updateTick() {
		// ������ ������ �ƹ��� Ŀ�� 0.5�� �̻� �Ѿ�� �ʵ��� �մϴ�.
		long now = System.currentTimeMillis();
		G.delta = Math.min((now - G.timestamp) / TIME_UNIT, TIME_UNIT * 500);
		G.timestamp = now;
		G.tick++;
	}

	private void moveParticles(Vector2D beforePos, Vector2D afterPos) {
		// ������ ǥ���ϴ� ��ƼŬ�� ó���մϴ�.
		float dx = afterPos.x - beforePos.x;
		float dy = afterPos.y - beforePos.y;
		float halfWidth = G.screenWidth / 2;
		float halfHeight = G.screenHeight / 2;
		
		for (int i = 0; i < particles.length; ++i) {
			if (particles[i] == null) {
				// �� �ٹ濡 �ƹ� ��ġ���� �����
				float offsetX = random.nextFloat() * G.screenWidth - halfWidth;
				float offsetY = random.nextFloat() * G.screenHeight - halfHeight;
				float lifetime = random.nextFloat() * 60 + 30;
				particles[i] = new Particle(afterPos.x + offsetX, afterPos.y + offsetY, lifetime);
			}
			
			Particle p = particles[i];
			if (p.isDead())
				particles[i] = null;
			else if (!p.isInBound(afterPos.x - halfWidth, afterPos.y - halfHeight,
					afterPos.x + halfWidth, afterPos.y + halfHeight)) {
				// ȭ�� ����� ���� ���δ�
				particles[i] = null;
			}
			else {
				// �������� �ݴ� �������� ���� �߰�
				p.age(G.delta);
				p.addTrail(-dx * .7f, -dy * .7f);
			}
		}
	}

	private void traceSentries(Ball ball, Sentry sen) {
		float r = Vector2D.distance(ball.pos, sen.pos);
		if (r < sen.sight) {
			Vector2D dir = Vector2D.subtract(ball.pos, sen.pos);
			sen.dir.add(dir).setLength(Sentry.SENTRY_SPEED * r / sen.sight);
			sen.pos.add(sen.dir);
			
			if (G.debug_ > 0) {
				debugCanvas.drawCircle(sen.pos.x, sen.pos.y, sen.sight, debugGreen);
			}
		}
	}

	private void attractBall(Ball ball, Attractor att) {
		float r = Vector2D.distance(ball.pos, att.pos);
		if (r < att.influence) {
			// F = G * (m1 * m2) / r * r
			float r_sq = Math.max(att.power * 3, r * r);	// �̰� �� �ϸ� ������
			float g = att.power / r_sq / ball.mass;
			Vector2D force = Vector2D.subtract(att.pos, ball.pos)
					.setLength(g);
			ball.acc.add(force);
			
			if (G.debug_ > 0) {
				GameUtil.drawArrow(debugCanvas, ball.pos.x, ball.pos.y,
						ball.pos.x + force.x * 100, ball.pos.y + force.y * 100, debugOrange);
			}
		}
	}

	private void collisionBallWithObstacle(Ball ball, Obstacle ob,
			Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
		RectF bound = ob.getBounds();
		float radius = ball.radius;
		
		// �ٻ������� �ڳʵ� ó����
		
		// left
		edge1.set(bound.left - radius, bound.top - radius);
		edge2.set(bound.left - radius, bound.bottom + radius);
		normal.set(-1, 0);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// top
		edge1.set(bound.left - radius, bound.top - radius);
		edge2.set(bound.right + radius, bound.top - radius);
		normal.set(0, -1);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// right
		edge1.set(bound.right + radius, bound.top - radius);
		edge2.set(bound.right + radius, bound.bottom + radius);
		normal.set(1, 0);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(bound.left - radius, bound.bottom + radius);
		edge2.set(bound.right + radius, bound.bottom + radius);
		normal.set(0, 1);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
	}

	private void collisionBallWithBorder(Ball ball, Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
		float width = stage.width;
		float height = stage.height;
		
		// left
		edge1.set(ball.radius, 0);
		edge2.set(ball.radius, height);
		normal.set(1, 0);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);

		// top
		edge1.set(0, ball.radius);
		edge2.set(width, ball.radius);
		normal.set(0, 1);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// right
		edge1.set(width - ball.radius, 0);
		edge2.set(width - ball.radius, height + 10);
		normal.set(-1, 0);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(0, height - ball.radius);
		edge2.set(width + 10, height - ball.radius);
		normal.set(0, -1);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
	}

	private void collisionBallWithEdge(Ball ball, Vector2D beforePos,
			Vector2D afterPos, Vector2D edgeStart, Vector2D edgeStop,
			Vector2D velocity, Vector2D normal) {
		float coeffi = ball.restitution;
		Vector2D interPt = new Vector2D();
		
		// �켱 �浹 ������ ã�Ƴ���...
		if (Vector2D.findLineIntersection(beforePos, afterPos, edgeStart, edgeStop, interPt)) {
			if (G.debug_ > 0)
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
