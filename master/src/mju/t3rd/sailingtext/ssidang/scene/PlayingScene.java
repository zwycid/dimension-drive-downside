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
		
		// ���� �ð� ������Ʈ
		mData.playTime += (mData.timestamp - mData.baseTime);
		mData.baseTime = mData.timestamp;
		
		// ���� �׾����� �˻��մϴ�.
		if (mData.ball.isDead()) {
			mManager.pushScene(GameData.GAME_OVER);
			return;
		}
		
		// �߷� �� ���� ����
		ball.acc.x = FloatMath.cos(mData.gravityDirection) * GameData.GRAVITY_CONSTANT;
		ball.acc.y = -FloatMath.sin(mData.gravityDirection) * GameData.GRAVITY_CONSTANT;
		ball.acc.add(ball.velo, -GameData.AIR_FRICTION_COEFFICIENT);
		
		// ���� ó��
		for (final Attractor att : stage.attractors) {
			att.rotate(delta);
			attractBall(ball, att);
		}
		
		// ���� ó��
		for (Coin coin : stage.coins) {
			coin.rotate(delta);
			if (! coin.isEaten() && coin.isHit(ball)) {
				mData.score += Coin.COIN_SCORE;
				coin.eat();
				SoundManager.getInstance().play(0);
			}
		}

		// �� �ӵ� �� ����
		ball.velo.add(ball.acc, delta);
		ball.rotation = mData.gravityDirection;
		
		// �̵��� ��ġ�� ����մϴ�.
		Vector2D beforePos = new Vector2D(ball.pos);
		Vector2D afterPos = Vector2D.add(beforePos, ball.velo, delta);
		
		boolean f = false;

		// �� �浹 ó��
		f = f || collisionBallWithBorder(ball, beforePos, afterPos,
						stage.width, stage.height);
		
		// ��ֹ� �浹 ó��
		for (Obstacle ob : stage.obstacles) {
			f = f || collisionBallWithObstacle(ball, ob, beforePos, afterPos);
		}
		if (f)
			SoundManager.getInstance().play(4);
		
		// �� ���� ���
		ball.debug_pos.set(beforePos);
		ball.debug_dir.set(afterPos);
		ball.debug_dir.subtract(beforePos).setLength(50);
		
		// ���� �� ��ġ�� Ȯ���մϴ�.
		ball.pos.set(afterPos);
		
		// �� ������ �˷��ݴϴ�.
		mData.marker.setDirection(ball.pos, stage.goal.pos);
		
		// sentry ó��
		for (Sentry sen : stage.sentries) {
			sen.chargeWeapon();
			traceSentries(ball, sen);
		}
		
		// �Ѿ� ó��
		moveBullets(ball);
		
		// ��� ��ƼŬ ó��
		moveParticles(beforePos, afterPos);
		
		// �� �����ߴ��� �˻��մϴ�.
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
		
		// ��� �����
		Game.resetCamera(canvas, mData.scaleRatio);
		canvas.drawColor(Color.BLACK);
		
		// ����
		lookAt.set(ball.pos.x - mData.screenWidth / 2,
				ball.pos.y - mData.screenHeight / 2);
		canvas.translate(-lookAt.x, -lookAt.y);
//		canvas.rotate(rotation);
//		rotation += 1;
		
		if (Vis.isEnabled(Vis.VERBOSE)) {
			// �� ��ü ����
			float s = mData.scaleRatio / stage.width * GameData.SCALE_UNIT * 0.99f;
			canvas.setMatrix(null);
			canvas.scale(s, s);
			canvas.translate(-lookAt.x * .3f, -lookAt.y * .3f);
		}
		
		// ���� ǥ�� ��ƼŬ
		for (Particle p : mData.particles) {
			if (! p.isDead()) {
				p.draw(canvas);
			}
		}
		
		// �� �׸���
		stage.goal.draw(canvas, mData.goalBitmap);
		
		// ���� �׸���
		for (Attractor att : stage.attractors) {
			att.draw(canvas, mData.swirlBitmap);
		}
	
		// ��ֹ� �׸���
		for (Obstacle ob : stage.obstacles) {
			ob.draw(canvas);
		}
		
		// �׵θ�
		float border = GameData.BORDER_THICK / 2;
		canvas.drawRect(-border, -border,
				stage.width + border, stage.height + border,
				mData.borderPaint);
		
		if (Vis.isEnabled()) {
			// sentry �þ� ǥ��
			for (Sentry sen : stage.sentries)
				if (sen.debug_inSight)
					canvas.drawCircle(sen.pos.x, sen.pos.y, sen.sight, Vis.green);
		}
		
		// ���� �׸���
		for (Coin coin : stage.coins) {
			if (! coin.isEaten())
				coin.draw(canvas, mData.coinBitmap);
		}
		
		// �� �׸���
		ball.draw(canvas, mData.ballBitmap);
		
		if (Vis.isEnabled()) {
			// �� ���� ǥ��
			GameUtil.drawArrow(canvas, ball.debug_pos.x, ball.debug_pos.y,
					ball.debug_pos.x + ball.debug_dir.x,
					ball.debug_pos.y + ball.debug_dir.y, Vis.blue);
			
			// ���� �� ǥ��
			for (Attractor att : stage.attractors)
				if (att.debug_inInfluence)
					GameUtil.drawArrow(canvas, ball.pos.x, ball.pos.y,
							ball.pos.x + att.debug_force.x * 100,
							ball.pos.y + att.debug_force.y * 100, Vis.orange);
		}
		
		if (Vis.isEnabled(Vis.VERBOSE)) {
			// ���̴� �׵θ� �׸���
			canvas.drawRect(ball.pos.x - mData.screenWidth / 2,
					ball.pos.y - mData.screenHeight / 2,
					ball.pos.x + mData.screenWidth / 2,
					ball.pos.y + mData.screenHeight / 2, Vis.orange);
		}
		
		// �Ѿ� �׸���
		for (Bullet b : mData.bullets) {
			if (! b.isDead())
				b.draw(canvas);
		}
		
		// sentry �׸���
		for (Sentry sen : stage.sentries) {
			sen.draw(canvas, mData.sentryBitmap);
		}
		
		// �� ���� marker �׸���
		mData.marker.draw(canvas, mData.markerBitmap);
		
		// ���� �ð�, ���� ǥ��
		drawStatus(canvas);
	}
	

	// TODO �̰� ��......
	private final void drawStatus(Canvas canvas) {
		String text = new StringBuilder(32)
			.append(String.format("%02d", (int) mData.playTime / 60000))
			.append(":")
			.append(String.format("%02d", (int) mData.playTime / 1000 % 60))
			.append(" / ")
			.append(mData.score)
			.toString();

		// �������K!
		Vector2D dir = new Vector2D().fromDirection(
				(float) (mData.gravityDirection + Math.PI / 2),
				20);
		Vector2D side = new Vector2D().fromDirection(
				(float) (mData.gravityDirection), 25);
		dir.y = -dir.y;	// ��ǥ ����
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
		// ������ ǥ���ϴ� ��ƼŬ�� ó���մϴ�.
		float dx = afterPos.x - beforePos.x;
		float dy = afterPos.y - beforePos.y;
		float halfWidth = mData.screenWidth / 2;
		float halfHeight = mData.screenHeight / 2;
		
		for (Particle p : mData.particles) {
			if (p.isDead()) {
				// �� �ٹ濡 �ƹ� ��ġ���� �����
				float offsetX = mData.random.nextFloat() * mData.screenWidth - halfWidth;
				float offsetY = mData.random.nextFloat() * mData.screenHeight - halfHeight;
				float lifetime = mData.random.nextFloat() * 60 + 30;
				p.reset(afterPos.x + offsetX, afterPos.y + offsetY, lifetime);
			}
			
			if (! p.isInBound(afterPos.x - halfWidth, afterPos.y - halfHeight,
					afterPos.x + halfWidth, afterPos.y + halfHeight)) {
				// ȭ�� ����� ���� ���δ�
				p.kill();
			}
			else {
				// �������� �ݴ� �������� ���� �߰�
				p.age(mData.delta);
				p.addTrail(-dx * .7f, -dy * .7f);
			}
		}
	}
	
	private final void moveBullets(Ball ball) {
		for (Bullet b : mData.bullets) {
			if (! b.isDead()) {
				b.trace();
				
				// �Ѿ��� ���� �¾����� �Ѿ� ���ְ� �� ������.
				if (b.isHit(ball)) {
					// TODO Sound: �´¼Ҹ�
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
				// TODO ������ �߽�!
				SoundManager.getInstance().play(1);
		}

		sen.debug_inSight = (r < sen.sight);
	}

	private final void attractBall(Ball ball, Attractor att) {
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

	private static final boolean collisionBallWithObstacle(Ball ball, Obstacle ob,
			Vector2D beforePos, Vector2D afterPos) {
		Vector2D edge1 = new Vector2D();
		Vector2D edge2 = new Vector2D();
		Vector2D normal = new Vector2D();
		
		float radius = ball.radius;
		
		// �ٻ������� �ڳʵ� ó����
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
			return true;
		}
		return false;
	}

}
