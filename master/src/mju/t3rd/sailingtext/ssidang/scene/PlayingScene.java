package mju.t3rd.sailingtext.ssidang.scene;

import mju.t3rd.sailingtext.ssidang.engine.GameUtil;
import mju.t3rd.sailingtext.ssidang.engine.Vector2D;
import mju.t3rd.sailingtext.ssidang.engine.Vis;
import mju.t3rd.sailingtext.ssidang.game.Attractor;
import mju.t3rd.sailingtext.ssidang.game.Ball;
import mju.t3rd.sailingtext.ssidang.game.Bullet;
import mju.t3rd.sailingtext.ssidang.game.Coin;
import mju.t3rd.sailingtext.ssidang.game.Obstacle;
import mju.t3rd.sailingtext.ssidang.game.Particle;
import mju.t3rd.sailingtext.ssidang.game.Sentry;
import mju.t3rd.sailingtext.ssidang.game.Stage;
import mju.t3rd.sailingtext.zeraf29.sound.SoundManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Path;
import android.util.FloatMath;

public class PlayingScene extends Scene {

	public void onFrame() {
		float delta = mData.delta;
		Stage stage = mData.stage;
		Ball ball = mData.ball;
		
		// 게임 시간 업데이트
		mData.playTime += (mData.timestamp - mData.baseTime);
		mData.baseTime = mData.timestamp;
		
		// 공이 죽었는지 검사합니다.
		if (mData.ball.isDead()) {
			mManager.pushScene(GameData.GAME_OVER);
			return;
		}
		
		// 중력 및 공기 저항
		ball.acc.x = FloatMath.cos(mData.gravityDirection) * GameData.GRAVITY_CONSTANT;
		ball.acc.y = -FloatMath.sin(mData.gravityDirection) * GameData.GRAVITY_CONSTANT;
		ball.acc.add(ball.velo, -GameData.AIR_FRICTION_COEFFICIENT);
		
		// 끌개 처리
		for (final Attractor att : stage.attractors) {
			att.rotate(delta);
			attractBall(ball, att);
		}
		
		// 동전 처묵
		for (Coin coin : stage.coins) {
			coin.rotate(delta);
			if (! coin.isEaten() && coin.isHit(ball)) {
				mData.score += Coin.COIN_SCORE;
				coin.eat();
				SoundManager.getInstance().play(0);
			}
		}

		// 공 속도 및 방향
		ball.velo.add(ball.acc, delta);
		ball.rotation = mData.gravityDirection;
		
		// 이동할 위치를 계산합니다.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		boolean f = false;

		// 벽 충돌 처리
		f = f || collisionBallWithBorder(ball, beforePos, afterPos,
						stage.width, stage.height);
		
		// 장애물 충돌 처리
		for (Obstacle ob : stage.obstacles) {
			f = f || collisionBallWithObstacle(ball, ob, beforePos, afterPos);
		}
		if (f)
			SoundManager.getInstance().play(4);
		
		// 공 방향 계산
		ball.debug_pos.set(beforePos);
		ball.debug_dir.set(afterPos);
		ball.debug_dir.subtract(beforePos).setLength(50);
		
		// 공의 새 위치를 확정합니다.
		ball.pos.set(afterPos);
		
		// 골 방향을 알려줍니다.
		mData.marker.setDirection(ball.pos, stage.goal.pos);
		
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
			SoundManager.getInstance().play(5);
			mManager.pushScene(GameData.STAGE_CLEAR);
		}
	}

	public void onRender(Canvas canvas) {
		Vector2D lookAt = mData.lookAt;
		Ball ball = mData.ball;
		Stage stage = mData.stage;
		
		// 배경 지우기
		Game.resetCamera(canvas, mData.scaleRatio);
		canvas.drawColor(Color.BLACK);
		
		// 시점
		lookAt.set(ball.pos.x - mData.screenWidth / 2,
				ball.pos.y - mData.screenHeight / 2);
		canvas.translate(-lookAt.x, -lookAt.y);
//		canvas.rotate(rotation);
//		rotation += 1;
		
		if (Vis.isEnabled(Vis.VERBOSE)) {
			// 맵 전체 보기
			float s = mData.scaleRatio / stage.width * GameData.SCALE_UNIT * 0.99f;
			canvas.setMatrix(null);
			canvas.scale(s, s);
			canvas.translate(-lookAt.x * .3f, -lookAt.y * .3f);
		}
		
		// 방향 표시 파티클
		for (Particle p : mData.particles) {
			if (! p.isDead()) {
				p.draw(canvas);
			}
		}
		
		// 골 그리기
		stage.goal.draw(canvas, mData.goalBitmap);
		
		// 끌개 그리기
		for (Attractor att : stage.attractors) {
			att.draw(canvas, mData.swirlBitmap);
		}
	
		// 장애물 그리기
		for (Obstacle ob : stage.obstacles) {
			ob.draw(canvas);
		}
		
		// 테두리
		float border = GameData.BORDER_THICK / 2;
		canvas.drawRect(-border, -border,
				stage.width + border, stage.height + border,
				mData.borderPaint);
		
		if (Vis.isEnabled()) {
			// sentry 시야 표시
			for (Sentry sen : stage.sentries)
				if (sen.debug_inSight)
					canvas.drawCircle(sen.pos.x, sen.pos.y, sen.sight, Vis.green);
		}
		
		// 동전 그리기
		for (Coin coin : stage.coins) {
			if (! coin.isEaten())
				coin.draw(canvas, mData.coinBitmap);
		}
		
		// 공 그리기
		ball.draw(canvas, mData.ballBitmap);
		
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
			canvas.drawRect(ball.pos.x - mData.screenWidth / 2,
					ball.pos.y - mData.screenHeight / 2,
					ball.pos.x + mData.screenWidth / 2,
					ball.pos.y + mData.screenHeight / 2, Vis.orange);
		}
		
		// 총알 그리기
		for (Bullet b : mData.bullets) {
			if (! b.isDead())
				b.draw(canvas);
		}
		
		// sentry 그리기
		for (Sentry sen : stage.sentries) {
			sen.draw(canvas, mData.sentryBitmap);
		}
		
		// 골 방향 marker 그리기
		mData.marker.draw(canvas, mData.markerBitmap);
		
		// 게임 시간, 점수 표시
		drawStatus(canvas);
	}
	

	// TODO 이거 좀......
	private final void drawStatus(Canvas canvas) {
		String text = new StringBuilder(32)
			.append(String.format("%02d", (int) mData.playTime / 60000))
			.append(":")
			.append(String.format("%02d", (int) mData.playTime / 1000 % 60))
			.append(" / ")
			.append(mData.score)
			.toString();

		// 하하하핳!
		Vector2D dir = new Vector2D().fromDirection(
				(float) (mData.gravityDirection + Math.PI / 2),
				20);
		Vector2D side = new Vector2D().fromDirection(
				(float) (mData.gravityDirection), 25);
		dir.y = -dir.y;	// 좌표 보정
		side.y = -side.y;
		
		float x1 = mData.ball.pos.x;
		float y1 = mData.ball.pos.y;
		float x2 = mData.ball.pos.x;
		float y2 = mData.ball.pos.y;
		
		mData.barPaint.setColor(0x80ff3000);
		canvas.drawLine(x1 - dir.x + side.x, y1 - dir.y + side.y,
				x2 + dir.x + side.x, y2 + dir.y + side.y, mData.barPaint);
		mData.barPaint.setColor(0x5000ff30);
		dir.setLength(20.f * mData.ball.hitpoint / mData.ball.maxHitpoint);
		canvas.drawLine(x1 - dir.x + side.x, y1 - dir.y + side.y,
				x2 + dir.x + side.x, y2 + dir.y + side.y, mData.barPaint);
		
		Path path = new Path();
		dir.setLength(mData.textPaint.measureText(text) / 2);
		side.scale(1.7f);
		path.moveTo(x1 - dir.x + side.x, y1 - dir.y + side.y);
		path.lineTo(x2 + dir.x + side.x, y2 + dir.y + side.y);
		canvas.drawTextOnPath(text, path, 0, 0, mData.textPaint);
	}

	private final void moveParticles(Vector2D beforePos, Vector2D afterPos) {
		// 방향을 표시하는 파티클을 처리합니다.
		float dx = afterPos.x - beforePos.x;
		float dy = afterPos.y - beforePos.y;
		float halfWidth = mData.screenWidth / 2;
		float halfHeight = mData.screenHeight / 2;
		
		for (Particle p : mData.particles) {
			if (p.isDead()) {
				// 공 근방에 아무 위치에나 만들기
				float offsetX = mData.random.nextFloat() * mData.screenWidth - halfWidth;
				float offsetY = mData.random.nextFloat() * mData.screenHeight - halfHeight;
				float lifetime = mData.random.nextFloat() * 60 + 30;
				p.reset(afterPos.x + offsetX, afterPos.y + offsetY, lifetime);
			}
			
			if (! p.isInBound(afterPos.x - halfWidth, afterPos.y - halfHeight,
					afterPos.x + halfWidth, afterPos.y + halfHeight)) {
				// 화면 벗어나면 빨리 죽인다
				p.kill();
			}
			else {
				// 움직임의 반대 방향으로 꼬리 추가
				p.age(mData.delta);
				p.addTrail(-dx * .7f, -dy * .7f);
			}
		}
	}
	
	private final void moveBullets(Ball ball) {
		for (Bullet b : mData.bullets) {
			if (! b.isDead()) {
				b.trace();
				
				// 총알이 공에 맞았으면 총알 없애고 공 아프게.
				if (b.isHit(ball)) {
					// TODO Sound: 맞는소리
					SoundManager.getInstance().play(3);
					ball.giveDamage(1);
					b.kill();
				}
				else
					b.age(mData.delta);
			}
		}
	}

	private final void traceSentries(Ball ball, Sentry sen) {
		float r = Vector2D.distance(ball.pos, sen.pos);
		if (r < sen.sight) {
			sen.trace(ball, r);
			if (sen.tryShootTarget(mData.bullets, ball.pos))
				// TODO 히히히 발싸!
				SoundManager.getInstance().play(1);
		}

		sen.debug_inSight = (r < sen.sight);
	}

	private final void attractBall(Ball ball, Attractor att) {
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

	private static final boolean collisionBallWithObstacle(Ball ball, Obstacle ob,
			Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
		float radius = ball.radius;
		
		// 근사적으로 코너도 처리함
		boolean f = false;
		
		// left
		edge1.set(ob.left - radius, ob.top - radius);
		edge2.set(ob.left - radius, ob.bottom + radius);
		normal.set(-1, 0);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// top
		edge1.set(ob.left - radius, ob.top - radius);
		edge2.set(ob.right + radius, ob.top - radius);
		normal.set(0, -1);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// right
		edge1.set(ob.right + radius, ob.top - radius);
		edge2.set(ob.right + radius, ob.bottom + radius);
		normal.set(1, 0);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(ob.left - radius, ob.bottom + radius);
		edge2.set(ob.right + radius, ob.bottom + radius);
		normal.set(0, 1);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		return f;
	}

	private static final boolean collisionBallWithBorder(Ball ball, Vector2D beforePos,
			Vector2D afterPos, float stageWidth, float stageHeight) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
		boolean f = false;
		
		// left
		edge1.set(ball.radius, 0);
		edge2.set(ball.radius, stageHeight);
		normal.set(1, 0);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);

		// top
		edge1.set(0, ball.radius);
		edge2.set(stageWidth, ball.radius);
		normal.set(0, 1);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// right
		edge1.set(stageWidth - ball.radius, 0);
		edge2.set(stageWidth - ball.radius, stageHeight + 10);
		normal.set(-1, 0);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		// bottom
		edge1.set(0, stageHeight - ball.radius);
		edge2.set(stageWidth + 10, stageHeight - ball.radius);
		normal.set(0, -1);
		f = f || collisionBallWithEdge(ball, beforePos, afterPos, edge1, edge2, ball.velo, normal);
		
		return f;
	}

	private static final boolean collisionBallWithEdge(Ball ball, Vector2D beforePos,
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
			return true;
		}
		return false;
	}

}
