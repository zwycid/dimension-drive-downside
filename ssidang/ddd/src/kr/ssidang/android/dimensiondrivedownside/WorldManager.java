package kr.ssidang.android.dimensiondrivedownside;

import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class WorldManager {
	private static final int DEBUG_LEVEL = 3;
	
	private static final float SCALE_UNIT = 160.f;
	private static final float TIME_UNIT = 30.f;
	
	private static final float AIR_FRICTION_COEFFICIENT = .1f;
	private static final float GRAVITY_CONSTANT = .5f;
	
	private static final float BORDER_THICK = 20;
	private static final int MAX_PARTICLE = 30;
	private static final int MAX_BULLET = 100;
	
	private static final int STATE_READY = 0;
	private static final int STATE_PAUSED = 1;
	private static final int STATE_PLAYING = 2;
	private static final int STATE_COMPLETED = 3;
	private static final int STATE_DEAD = 4;
	
	// Stage data
	private Stage stage;
	private Ball ball;
	private Particle[] particles;
	private Bullet[] bullets;
	private int score;
	
	// ����(view)�� ���õ� ����
	private Vector2D lookAt;

	private Random random;
	private GameParams G;
	
	// ���� ���ҽ�.............
	private Paint borderPaint;
	private Bitmap ballBitmap;
	
	// ������...........
	Paint debug_text;
	Paint debug_blue;
	Paint debug_orange;
	Paint debug_red;
	Paint debug_green;
	
	///////////////////////////////////////////////////////////////////////////
	// GameData
	///////////////////////////////////////////////////////////////////////////
	class GameParams {
		int debug_;
		int state = STATE_READY;
		
		float screenWidth;
		float screenHeight;
		float scaleRatio;
		
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
		this.random = new Random();
		this.G = new GameParams();
		this.lookAt = new Vector2D();
		
		borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(0xff596691);
		borderPaint.setStrokeWidth(BORDER_THICK);
		
		ballBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.basic_ball);

		{
			// TODO ���� ����׿� ��ü �ʱ�ȭ
			debug_text = new Paint(Paint.ANTI_ALIAS_FLAG);
			debug_text.setColor(Color.WHITE);
			debug_text.setTextSize(8);
			
			debug_blue = new Paint();
			debug_blue.setColor(Color.BLUE);
			debug_blue.setStrokeWidth(7);
			debug_blue.setStrokeCap(Cap.ROUND);
			
			debug_orange = new Paint();
			debug_orange.setColor(0xffff8030);
			debug_orange.setStrokeWidth(5);
			debug_orange.setStrokeCap(Cap.ROUND);
			
			debug_red = new Paint(Paint.ANTI_ALIAS_FLAG);
			debug_red.setColor(Color.RED);
			debug_red.setStrokeWidth(3);
			debug_red.setStrokeCap(Cap.ROUND);
			
			debug_green = new Paint(Paint.ANTI_ALIAS_FLAG);
			debug_green.setColor(0x4030f030);
			debug_green.setStrokeWidth(3);
			debug_green.setStrokeCap(Cap.ROUND);
		}
	}
	
	public GameParams getGameParams() {
		return G;
	}
	
	void onScreenSize(int width, int height) {
		// ��ǥ�踦 ����ϴ�.
		int length = Math.min(width, height);
		
		G.scaleRatio = length / SCALE_UNIT;
		G.screenWidth = width / G.scaleRatio;
		G.screenHeight = height / G.scaleRatio;
	}
	
	void onSensorEvent(float azimuth, float pitch, float roll) {
		G.azimuth = azimuth;
		G.pitch = pitch;
		G.roll = roll;
		
		// �Ʒ� ���� ã��...
		float sign = (pitch >= 0 ? -1.f : 1.f);
		float rad = (float) Math.toRadians((-90 - roll) * sign);
		G.gravityDirection = (float) rad;
	}
	
	boolean onTouchEvent(Activity parent, MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			if (G.state == STATE_COMPLETED)
				parent.finish();
			else
				pause();
			return true;
		}
		return false;
	}
	
	boolean onKeyDown(Activity parent, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			G.debug_ = (G.debug_ + 1) % DEBUG_LEVEL;
			return true;
		}
		return false;
	}
	
	void onBackPressed(Activity parent) {
		if (G.state == STATE_COMPLETED) {
			parent.finish();
		}
		else if (G.state == STATE_DEAD) {
			parent.finish();
		}
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
		canvas.scale(G.scaleRatio, G.scaleRatio);
	}
	
	/**
	 * ���带 �׷��ݴϴ�.
	 * 
	 * @param canvas
	 */
	public void onRender(Canvas canvas) {
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
		
		if (G.debug_ > 0) {
			resetView(canvas);
			
			float fps = 1000.f / (G.delta * TIME_UNIT);
			GameUtil.drawTextMultiline(canvas,
					"Tick: " + G.tick + " / " + (G.playTime) + "ms (fps: " + fps + ")"
					+ "\nScreen = (" + G.screenWidth + ", " + G.screenHeight + ")"
//					+ "\nAzimuth = " + G.azimuth
//					+ "\nPitch = " + G.pitch
//					+ "\nRoll = " + G.roll
					+ "\nMap = (" + stage.width + ", " + stage.height + ")"
					+ "\nPos = (" + ball.pos.x + ", " + ball.pos.y + ")"
					+ "\nScore = " + score
					+ "\nHP = " + ball.hitpoint
					, 0, 0, debug_text);
			
			float centerX = G.screenWidth / 2;
			float centerY = G.screenHeight / 2;
			float offsetX = FloatMath.cos(G.gravityDirection) * 50;
			float offsetY = FloatMath.sin(G.gravityDirection) * 50;
			GameUtil.drawArrow(canvas, centerX, centerY,
					centerX + offsetX, centerY - offsetY, debug_red);
		}
	}

	/**
	 * ��ü���� �����Դϴ�.
	 * 
	 * @param delta
	 */
	public void onFrame() {
		updateTick();
		
		switch (G.state) {
		case STATE_READY:
			onFrameReady();
			break;
		case STATE_PAUSED:
			onFramePaused();
			break;
		case STATE_PLAYING:
			onFramePlaying(G.delta);
			break;
		case STATE_COMPLETED:
			onFrameCompleted();
			break;
		case STATE_DEAD:
			onFrameDead();
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
				G.screenHeight / 2 + 3, debug_text);
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
			// �� ��ü ����
			float s = G.scaleRatio / stage.width * SCALE_UNIT * 0.99f;
			canvas.setMatrix(null);
			canvas.scale(s, s);
		}
		
		// ���� ǥ�� ��ƼŬ
		for (Particle p : particles) {
			if (! p.isDead()) {
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
		
		if (G.debug_ > 0) {
			// sentry �þ� ǥ��
			for (Sentry sen : stage.sentries)
				if (sen.debug_inSight)
					canvas.drawCircle(sen.pos.x, sen.pos.y, sen.sight, debug_green);
		}
		
		// �� �׸���
		ball.draw(canvas);
		
		if (G.debug_ > 0) {
			// �� ���� ǥ��
			GameUtil.drawArrow(canvas, ball.debug_pos.x, ball.debug_pos.y,
					ball.debug_pos.x + ball.debug_dir.x,
					ball.debug_pos.y + ball.debug_dir.y, debug_blue);
			
			// ���� �� ǥ��
			for (Attractor att : stage.attractors)
				if (att.debug_inInfluence)
					GameUtil.drawArrow(canvas, ball.pos.x, ball.pos.y,
							ball.pos.x + att.debug_force.x * 100,
							ball.pos.y + att.debug_force.y * 100, debug_orange);
		}
		
		// �Ѿ� �׸���
		for (Bullet b : bullets) {
			if (! b.isDead())
				b.draw(canvas);
		}
		
		// sentry �׸���
		for (Sentry sen : stage.sentries) {
			sen.draw(canvas);
		}
	}

	private void onRenderCompleted(Canvas canvas) {
		onRenderPlaying(canvas);
		
		resetView(canvas);
		canvas.drawColor(0xcc0909a0);
		canvas.drawText("Win", G.screenWidth / 2 - 8,
				G.screenHeight / 2 + 3, debug_text);
	}

	private void onRenderDead(Canvas canvas) {
		onRenderPlaying(canvas);
		
		resetView(canvas);
		canvas.drawColor(0xcca00909);
		canvas.drawText("Dead", G.screenWidth / 2 - 8,
				G.screenHeight / 2 + 3, debug_text);
	}

	private void onFrameReady() {
		// ��ƼŬ, �Ѿ� ����� �̸� �����Ӵϴ�.
		particles = new Particle[MAX_PARTICLE];
		bullets = new Bullet[MAX_BULLET];
		for (int i = 0; i < particles.length; ++i)
			particles[i] = new Particle();
		for (int i = 0; i < bullets.length; ++i)
			bullets[i] = new Bullet();
		
		// ���� �������� �Ӵϴ�.
		ball = new Ball(ballBitmap, stage.start.pos.x, stage.start.pos.y, 20);
		
		// �ð��� �ʱ�ȭ�մϴ�.
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
		
		// ���� ó��
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
		
		// �� ���� ���
		ball.debug_pos.set(beforePos);
		ball.debug_dir.set(afterPos);
		ball.debug_dir.subtract(beforePos).setLength(50);
		
		// ���� �� ��ġ�� Ȯ���մϴ�.
		ball.pos.set(afterPos);
		
		// sentry ó��
		for (Sentry sen : stage.sentries) {
			sen.chargeWeapon();
			traceSentries(ball, sen);
		}
		
		// �Ѿ� ó��
		moveBullets(ball);
		
		// ��� ��ƼŬ ó��
		moveParticles(beforePos, afterPos);
		
		// ���� �׾����� �˻��մϴ�.
		if (ball.isDead()) {
			G.state = STATE_DEAD;
		}
		
		// �� �����ߴ��� �˻��մϴ�.
		if (checkPortalReached(ball, stage.goal)) {
			G.state = STATE_COMPLETED;
		}
	}

	private boolean checkPortalReached(Ball ball, Portal portal) {
		return Vector2D.distance(ball.pos, portal.pos)
				< ball.radius + portal.radius;
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
		
		for (Particle p : particles) {
			if (p.isDead()) {
				// �� �ٹ濡 �ƹ� ��ġ���� �����
				float offsetX = random.nextFloat() * G.screenWidth - halfWidth;
				float offsetY = random.nextFloat() * G.screenHeight - halfHeight;
				float lifetime = random.nextFloat() * 60 + 30;
				p.reset(afterPos.x + offsetX, afterPos.y + offsetY, lifetime);
			}
			
			if (!p.isInBound(afterPos.x - halfWidth, afterPos.y - halfHeight,
					afterPos.x + halfWidth, afterPos.y + halfHeight)) {
				// ȭ�� ����� ���� ���δ�
				p.kill();
			}
			else {
				// �������� �ݴ� �������� ���� �߰�
				p.age(G.delta);
				p.addTrail(-dx * .7f, -dy * .7f);
			}
		}
	}
	
	private void moveBullets(Ball ball) {
		for (Bullet b : bullets) {
			if (! b.isDead()) {
				b.trace();
				
				// �Ѿ��� ���� �¾����� �Ѿ� ���ְ� �� ������.
				if (b.isHit(ball)) {
					// TODO Sound: �´¼Ҹ�
					ball.giveDamage(1);
					b.kill();
				}
				else
					b.age(G.delta);
			}
		}
	}

	private void traceSentries(Ball ball, Sentry sen) {
		float r = Vector2D.distance(ball.pos, sen.pos);
		if (r < sen.sight) {
			sen.trace(ball, r);
			sen.tryShootTarget(bullets, ball.pos);
		}

		sen.debug_inSight = (r < sen.sight);
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
			att.debug_force.set(force);
		}
		
		att.debug_inInfluence = (r < att.influence);
	}

	private void collisionBallWithObstacle(Ball ball, Obstacle ob,
			Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
		float radius = ball.radius;
		
		// �ٻ������� �ڳʵ� ó����
		
		// left
		edge1.set(ob.left - radius, ob.top - radius);
		edge2.set(ob.left - radius, ob.bottom + radius);
		normal.set(-1, 0);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// top
		edge1.set(ob.left - radius, ob.top - radius);
		edge2.set(ob.right + radius, ob.top - radius);
		normal.set(0, -1);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// right
		edge1.set(ob.right + radius, ob.top - radius);
		edge2.set(ob.right + radius, ob.bottom + radius);
		normal.set(1, 0);
		collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(ob.left - radius, ob.bottom + radius);
		edge2.set(ob.right + radius, ob.bottom + radius);
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
