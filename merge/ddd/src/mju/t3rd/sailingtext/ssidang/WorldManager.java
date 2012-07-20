package mju.t3rd.sailingtext.ssidang;

import java.util.Random;

import mju.t3rd.sailingtext.R;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class WorldManager {
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
	private Marker marker;
	private Particle[] particles;
	private Bullet[] bullets;
	private int score;
	
	// 시점(view)에 관련된 사항
	private Vector2D lookAt;

	private Random random;
	private GameParams G;
	
	// 게임 리소스.............
	private Paint borderPaint;
	private Bitmap ballBitmap;
	private Bitmap swirlBitmap;
	private Bitmap sentryBitmap;
	private Bitmap markerBitmap;
	private Bitmap goalBitmap;
	private Bitmap coinBitmap;

	
	///////////////////////////////////////////////////////////////////////////
	// GameData
	///////////////////////////////////////////////////////////////////////////
	class GameParams {
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
		
		// 리소스 로드
		Resources res = context.getResources();
		ballBitmap = BitmapFactory.decodeResource(res, R.drawable.basic_ball);
		swirlBitmap = BitmapFactory.decodeResource(res, R.drawable.swirl);
		sentryBitmap = BitmapFactory.decodeResource(res, R.drawable.sentry);
		markerBitmap = BitmapFactory.decodeResource(res, R.drawable.marker);
		goalBitmap = BitmapFactory.decodeResource(res, R.drawable.goal);
		coinBitmap = BitmapFactory.decodeResource(res, R.drawable.coin);
	}
	
	public GameParams getGameParams() {
		return G;
	}
	
	void onScreenSize(int width, int height) {
		// 좌표계를 맞춥니다.
		int length = Math.min(width, height);
		
		G.scaleRatio = length / SCALE_UNIT;
		G.screenWidth = width / G.scaleRatio;
		G.screenHeight = height / G.scaleRatio;
	}
	
	void onSensorEvent(float azimuth, float pitch, float roll) {
		G.azimuth = azimuth;
		G.pitch = pitch;
		G.roll = roll;
		
		// 아래 방향 찾기...
		float sign = (pitch >= 0 ? -1.f : 1.f);
		float rad = (float) Math.toRadians((-90 - roll) * sign);
		G.gravityDirection = (float) rad;
	}
	
	boolean onTouchEvent(Activity parent, MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			if (event.getY() < 50)
				Vis.toggleFrameMode();
			else {
				if (G.state == STATE_COMPLETED)
					parent.finish();
				else
					pause();
			}
			return true;
		}
		return false;
	}
	
	boolean onKeyDown(Activity parent, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Vis.escalateLevel();
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
			// 일시 정지
			G.state = STATE_PAUSED;
		}
		else if (! set && G.state == STATE_PAUSED) {
			// 다시 시작
			G.baseTime = G.timestamp;
			G.state = STATE_PLAYING;
		}
	}
	
	public boolean isPaused() {
		return G.state == STATE_PAUSED;
	}

	public void makeMockWorld() {
//		stage = Stage.fromData("[mapsize],800,800/[s],100,400/[f],700,700/[b],400,300,700,500/" +
//								"[a],200,620/[c],256,93/[c],640,142/[c],380,621/[c],684,386/");
		stage = Stage.fromData("[mapsize],600,1200/[b],69,76,114,290/[b],184,80,229,283/[b],106,163,199,200/" +
					"[b],101,245,219,283/[b],299,78,329,282/[b],323,156,385,178/[b],378,83,415,283/[b],136,365,385,391/" +
					"[b],354,376,378,521/[b],233,458,271,553/[b],136,541,410,571/[b],125,638,360,662/[b],188,655,214,747/" +
					"[b],269,657,313,741/[b],140,730,353,757/[b],399,623,423,808/[b],418,695,498,721/[f],79,1116/" +
					"[c],86,940/[c],129,958/[c],180,996/[c],196,1026/[c],215,1068/[c],228,1100/[c],236,1124/[c],241,1147/" +
					"[a],74,458/[a],518,245/[a],510,521/[a],519,816/[s],146,27/[i],256,77/[i],274,105/[i],273,133/" +
					"[i],269,167/[i],265,200/[i],266,252/[i],268,298/[i],101,331/[i],161,332/[i],215,323/[i],306,325/" +
					"[i],360,323/[i],414,311/[i],453,308/[i],150,418/[i],215,418/[i],256,418/[i],315,417/[i],309,455/" +
					"[i],308,484/[i],168,481/[i],134,508/[i],84,535/[i],80,590/[i],133,597/[i],188,595/[i],233.5,592/" +
					"[i],278,591/[i],313,590/[i],351,587/[i],391,590/[i],433,591/[i],444,535/[i],435,492/[i],435,441/" +
					"[i],439,398/[i],445,370/[i],460,93/[i],460,127/[i],460,162/[i],465,201/[i],461,237/[i],456,271/" +
					"[i],351,206/[i],354,240/[i],355,288/[i],360,125.5/[i],356,90/[i],354,48/[i],411,51/[i],443,54/" +
					"[i],300,51/[i],146,78/[i],146,112/[i],146,137/[i],65,37/[i],39,53/[i],33,90/[i],34,123/[i],35,153/" +
					"[i],31,188.5/[i],35.5,230/[i],40,272/[i],43,307/[i],140,679/[i],105,683/[i],79,687/[i],85,746/" +
					"[i],94,787/[i],156,788/[i],198,789/[i],225,789/[i],264.5,794/[i],305,783/[i],355,783/[i],355,693/" +
					"[i],383,639/[i],386,668/[i],384,706/[i],376,751/[i],448,653/[i],504,668/[i],508,731/[i],475,741/" +
					"[i],449,749/[i],451,799/");
	}
	
	private void resetView(Canvas canvas) {
		canvas.setMatrix(null);
		canvas.scale(G.scaleRatio, G.scaleRatio);
	}
	
	/**
	 * 월드를 그려줍니다.
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
		
		if (Vis.isEnabled()) {
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
					, 0, 0, Vis.white);
			
			float centerX = G.screenWidth / 2;
			float centerY = G.screenHeight / 2;
			float offsetX = FloatMath.cos(G.gravityDirection) * 50;
			float offsetY = FloatMath.sin(G.gravityDirection) * 50;
			GameUtil.drawArrow(canvas, centerX, centerY,
					centerX + offsetX, centerY - offsetY, Vis.red);
		}
	}

	/**
	 * 물체들을 움직입니다.
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
		// 아무것도 안 함
	}

	private void onRenderPaused(Canvas canvas) {
		// 게임 화면을 그려주고...
		onRenderPlaying(canvas);
		
		// 메뉴를 그려줘야 함.
		resetView(canvas);
		canvas.drawColor(0xa0000000);
		canvas.drawText("Paused", G.screenWidth / 2 - 13,
				G.screenHeight / 2 + 3, Vis.white);
	}

	private void onRenderPlaying(Canvas canvas) {
		// 배경 지우기
		resetView(canvas);
		canvas.drawColor(Color.BLACK);
		
		// 시점
		lookAt.set(ball.pos.x - G.screenWidth / 2,
				ball.pos.y - G.screenHeight / 2);
		canvas.translate(-lookAt.x, -lookAt.y);
		
		if (Vis.isEnabled(Vis.VERBOSE)) {
			// 맵 전체 보기
			float s = G.scaleRatio / stage.width * SCALE_UNIT * 0.99f;
			canvas.setMatrix(null);
			canvas.scale(s, s);
			canvas.translate(-lookAt.x * .3f, -lookAt.y * .3f);
		}
		
		// 방향 표시 파티클
		for (Particle p : particles) {
			if (! p.isDead()) {
				p.draw(canvas);
			}
		}
		
		// 골 그리기
		stage.goal.draw(canvas, goalBitmap);
		
		// 끌개 그리기
		for (Attractor att : stage.attractors) {
			att.draw(canvas, swirlBitmap);
		}
	
		// 장애물 그리기
		for (Obstacle ob : stage.obstacles) {
			ob.draw(canvas);
		}
		
		// 테두리
		float border = BORDER_THICK / 2;
		canvas.drawRect(-border, -border,
				stage.width + border, stage.height + border,
				borderPaint);
		
		if (Vis.isEnabled()) {
			// sentry 시야 표시
			for (Sentry sen : stage.sentries)
				if (sen.debug_inSight)
					canvas.drawCircle(sen.pos.x, sen.pos.y, sen.sight, Vis.green);
		}
		
		// 동전 그리기
		for (Coin coin : stage.coins) {
			if (! coin.isEaten())
				coin.draw(canvas, coinBitmap);
		}
		
		// 공 그리기
		ball.draw(canvas, ballBitmap);
		
		if (Vis.isEnabled()) {
			// 공 방향 표시
			GameUtil.drawArrow(canvas, ball.debug_pos.x, ball.debug_pos.y,
					ball.debug_pos.x + ball.debug_dir.x,
					ball.debug_pos.y + ball.debug_dir.y, Vis.blue);
			
			// 끌개 힘 표시
			for (Attractor att : stage.attractors)
				if (att.debug_inInfluence)
					GameUtil.drawArrow(canvas, ball.pos.x, ball.pos.y,
							ball.pos.x + att.debug_force.x * 100,
							ball.pos.y + att.debug_force.y * 100, Vis.orange);
		}
		
		if (Vis.isEnabled(Vis.VERBOSE)) {
			// 보이는 테두리 그리기
			canvas.drawRect(ball.pos.x - G.screenWidth / 2,
					ball.pos.y - G.screenHeight / 2,
					ball.pos.x + G.screenWidth / 2,
					ball.pos.y + G.screenHeight / 2, Vis.orange);
		}
		
		// 총알 그리기
		for (Bullet b : bullets) {
			if (! b.isDead())
				b.draw(canvas);
		}
		
		// sentry 그리기
		for (Sentry sen : stage.sentries) {
			sen.draw(canvas, sentryBitmap);
		}
		
		// 골 방향 marker 그리기
		marker.draw(canvas, markerBitmap);
		
		// 게임 시간, 점수 표시
		drawStatus(canvas);
	}

	private void onRenderCompleted(Canvas canvas) {
		onRenderPlaying(canvas);
		
		resetView(canvas);
		canvas.drawColor(0xcc0909a0);
		canvas.drawText("Goal", G.screenWidth / 2 - 8,
				G.screenHeight / 2 + 3, Vis.white);
	}

	private void onRenderDead(Canvas canvas) {
		onRenderPlaying(canvas);
		
		resetView(canvas);
		canvas.drawColor(0xcca00909);
		canvas.drawText("Dead", G.screenWidth / 2 - 8,
				G.screenHeight / 2 + 3, Vis.white);
	}

	private void onFrameReady() {
		// 파티클, 총알 목록을 미리 만들어둡니다.
		particles = new Particle[MAX_PARTICLE];
		bullets = new Bullet[MAX_BULLET];
		for (int i = 0; i < particles.length; ++i)
			particles[i] = new Particle();
		for (int i = 0; i < bullets.length; ++i)
			bullets[i] = new Bullet();
		
		// 공을 시작점에 둡니다.
		ball = new Ball(stage.start.pos.x, stage.start.pos.y, 20);
		marker = new Marker(-G.screenWidth / 2, -G.screenHeight / 2,
				G.screenWidth / 2, G.screenHeight / 2);
		
		// 시간을 초기화합니다.
		G.timestamp = System.currentTimeMillis();
		G.playTime = 0;
		G.baseTime = G.timestamp;
		
		G.state = STATE_PLAYING;
	}

	private void onFramePaused() {
		G.timestamp = System.currentTimeMillis();
	}

	private void onFramePlaying(float delta) {
		// 게임 시간 업데이트
		G.playTime += (G.timestamp - G.baseTime);
		G.baseTime = G.timestamp;
		
		// 공이 죽었는지 검사합니다.
		if (ball.isDead()) {
			G.state = STATE_DEAD;
			return;
		}
		
		// 중력 및 공기 저항
		ball.acc.x = FloatMath.cos(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.y = -FloatMath.sin(G.gravityDirection) * GRAVITY_CONSTANT;
		ball.acc.add(ball.velo, -AIR_FRICTION_COEFFICIENT);
		
		// 끌개 처리
		for (Attractor att : stage.attractors) {
			att.rotate(delta);
			attractBall(ball, att);
		}
		
		// 동전 처묵
		for (Coin coin : stage.coins) {
			coin.rotate(delta);
			if (! coin.isEaten() && coin.isHit(ball)) {
				score += Coin.COIN_SCORE;
				coin.eat();
			}
		}

		// 공 속도 및 방향
		ball.velo.add(ball.acc, delta);
		ball.rotation = G.gravityDirection;
		
		// 이동할 위치를 계산합니다.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		// 벽 충돌 처리
		collisionBallWithBorder(ball, beforePos, afterPos);
		
		// 장애물 충돌 처리
		for (Obstacle ob : stage.obstacles) {
			collisionBallWithObstacle(ball, ob, beforePos, afterPos);
		}
		
		// 공 방향 계산
		ball.debug_pos.set(beforePos);
		ball.debug_dir.set(afterPos);
		ball.debug_dir.subtract(beforePos).setLength(50);
		
		// 공의 새 위치를 확정합니다.
		ball.pos.set(afterPos);
		
		// 골 방향을 알려줍니다.
		marker.setDirection(ball.pos, stage.goal.pos);
		
		// sentry 처리
		for (Sentry sen : stage.sentries) {
			sen.chargeWeapon();
			traceSentries(ball, sen);
		}
		
		// 총알 처리
		moveBullets(ball);
		
		// 배경 파티클 처리
		moveParticles(beforePos, afterPos);
		
		// 골에 도달했는지 검사합니다.
		stage.goal.rotate(delta);
		if (stage.goal.isOverlapped(ball) < -stage.goal.radius * .7f) {
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
		// 프레임 간격이 아무리 커도 0.5초 이상 넘어가지 않도록 합니다.
		long now = System.currentTimeMillis();
		G.delta = Math.min((now - G.timestamp) / TIME_UNIT, TIME_UNIT * 500);
		G.timestamp = now;
		G.tick++;
	}

	private void drawStatus(Canvas canvas) {
		String text = new StringBuilder(32)
			.append(String.format("%02d", (int) G.playTime / 60000))
			.append(":")
			.append(String.format("%02d", (int) G.playTime / 1000 % 60))
			.append(" / ")
			.append(score)
			.toString();
		
		Vector2D dir = new Vector2D().fromDirection(G.gravityDirection, 20);
		
		Path path = new Path();
		path.moveTo(ball.pos.x, ball.pos.y);
		path.lineTo(ball.pos.x + dir.x, ball.pos.y - dir.y);
		canvas.drawTextOnPath(text, path, 0, 0, Vis.white);
	}

	private void moveParticles(Vector2D beforePos, Vector2D afterPos) {
		// 방향을 표시하는 파티클을 처리합니다.
		float dx = afterPos.x - beforePos.x;
		float dy = afterPos.y - beforePos.y;
		float halfWidth = G.screenWidth / 2;
		float halfHeight = G.screenHeight / 2;
		
		for (Particle p : particles) {
			if (p.isDead()) {
				// 공 근방에 아무 위치에나 만들기
				float offsetX = random.nextFloat() * G.screenWidth - halfWidth;
				float offsetY = random.nextFloat() * G.screenHeight - halfHeight;
				float lifetime = random.nextFloat() * 60 + 30;
				p.reset(afterPos.x + offsetX, afterPos.y + offsetY, lifetime);
			}
			
			if (! p.isInBound(afterPos.x - halfWidth, afterPos.y - halfHeight,
					afterPos.x + halfWidth, afterPos.y + halfHeight)) {
				// 화면 벗어나면 빨리 죽인다
				p.kill();
			}
			else {
				// 움직임의 반대 방향으로 꼬리 추가
				p.age(G.delta);
				p.addTrail(-dx * .7f, -dy * .7f);
			}
		}
	}
	
	private void moveBullets(Ball ball) {
		for (Bullet b : bullets) {
			if (! b.isDead()) {
				b.trace();
				
				// 총알이 공에 맞았으면 총알 없애고 공 아프게.
				if (b.isHit(ball)) {
					// TODO Sound: 맞는소리
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
			float r_sq = Math.max(att.power * 3, r * r);	// 이거 안 하면 무서움
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
		
		// 근사적으로 코너도 처리함
		
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
		
		// 우선 충돌 지점을 찾아내고...
		if (Vector2D.findLineIntersection(beforePos, afterPos, edgeStart, edgeStop, interPt)) {
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
