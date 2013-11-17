package mju.t3rd.sailingtext.ssidang.scene;

import java.util.Random;

import mju.t3rd.sailingtext.ssidang.engine.Vector2D;
import mju.t3rd.sailingtext.ssidang.game.Ball;
import mju.t3rd.sailingtext.ssidang.game.Bullet;
import mju.t3rd.sailingtext.ssidang.game.Marker;
import mju.t3rd.sailingtext.ssidang.game.Particle;
import mju.t3rd.sailingtext.ssidang.game.Stage;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * ���� ������: �����͸� ��� Ŭ����
 */
public class GameData {
	static final float SCALE_UNIT = 160.f;
	static final float TIME_UNIT = 30.f;

	static final float AIR_FRICTION_COEFFICIENT = .1f;
	static final float GRAVITY_CONSTANT = .7f;

	static final float BORDER_THICK = 20;		// ��ü�� ��
	static final int MAX_PARTICLE = 30;
	static final int MAX_BULLET = 100;

	static final Scene READY = new ReadyScene();
	static final Scene PLAYING = new PlayingScene();
	static final Scene PAUSED = new PausedScene();
	static final Scene GAME_OVER = new GameOverScene();
	static final Scene STAGE_CLEAR = new StageClearScene();


	Random random;

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

	// Stage data
	Stage stage;
	Ball ball;
	Marker marker;
	Particle[] particles;
	Bullet[] bullets;
	int score;

	// ����(view)�� ���õ� ����
	Canvas offscreen;
	Bitmap backBuffer;
	Vector2D lookAt;
	float rotation;

	// ���� ���ҽ�.............
	Paint borderPaint;
	Paint textPaint;
	Paint barPaint;
	Bitmap ballBitmap;
	Bitmap swirlBitmap;
	Bitmap sentryBitmap;
	Bitmap markerBitmap;
	Bitmap goalBitmap;
	Bitmap coinBitmap;
}
