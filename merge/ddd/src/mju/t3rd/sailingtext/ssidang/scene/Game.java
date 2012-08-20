package mju.t3rd.sailingtext.ssidang.scene;

import java.util.Random;

import mju.t3rd.sailingtext.R;
import mju.t3rd.sailingtext.ssidang.engine.GameUtil;
import mju.t3rd.sailingtext.ssidang.engine.Vector2D;
import mju.t3rd.sailingtext.ssidang.engine.Vis;
import mju.t3rd.sailingtext.ssidang.game.Stage;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;
import android.os.SystemClock;
import android.util.FloatMath;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class Game {
	private SceneManager mScene;
	private GameData mData;
	
	public Game(Resources res) {
		mData = new GameData();
		mScene = new SceneManager(this, mData);
		
		// 게임 초기 설정
		mData.random = new Random();
		mData.lookAt = new Vector2D();
		
		Paint borderPaint = new Paint();
		borderPaint.setStyle(Style.STROKE);
		borderPaint.setColor(0xff596691);
		borderPaint.setStrokeWidth(GameData.BORDER_THICK);
		mData.borderPaint = borderPaint;
		
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(0x60ffffff);
		textPaint.setTextSize(12);
		mData.textPaint = textPaint;
		
		Paint barPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		barPaint.setStrokeWidth(5);
		barPaint.setStrokeCap(Cap.ROUND);
		mData.barPaint = barPaint;
		
		// 리소스 로드
		mData.ballBitmap = BitmapFactory.decodeResource(res, R.drawable.power_ring);
		mData.swirlBitmap = BitmapFactory.decodeResource(res, R.drawable.swirl);
		mData.sentryBitmap = BitmapFactory.decodeResource(res, R.drawable.sentry);
		mData.markerBitmap = BitmapFactory.decodeResource(res, R.drawable.marker);
		mData.goalBitmap = BitmapFactory.decodeResource(res, R.drawable.goal);
		mData.coinBitmap = BitmapFactory.decodeResource(res, R.drawable.coin);
		
		// 게임 시작 준비
		mScene.pushScene(GameData.READY);
	}
	
	public void onScreenSize(int width, int height) {
		// 좌표계를 맞춥니다.
		int shortLength = Math.min(width, height);
		int sideLength = (int) FloatMath.sqrt(width * width + height * height);
		
		mData.scaleRatio = shortLength / GameData.SCALE_UNIT;
		mData.screenWidth = width / mData.scaleRatio;
		mData.screenHeight = height / mData.scaleRatio;
		
		mData.backBuffer = Bitmap.createBitmap(sideLength, sideLength, Config.ARGB_8888);
		mData.offscreen = new Canvas(mData.backBuffer);
	}
	
	public void onSensorEvent(float azimuth, float pitch, float roll) {
		mData.azimuth = azimuth;
		mData.pitch = pitch;
		mData.roll = roll;
		
		// 아래 방향 찾기...
		float sign = (pitch >= 0 ? -1.f : 1.f);
		float rad = (float) Math.toRadians((-90 - roll) * sign);
		mData.gravityDirection = (float) rad;
	}
	
	public boolean onTouchEvent(Activity parent, MotionEvent event) {
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			if (event.getY() < 50)
				Vis.toggleFrameMode();
			else {
				if (mScene.currentScene() == GameData.STAGE_CLEAR) {
//					parent.finish();
					finishGame(parent, "goal", (int) mData.playTime, mData.score);
				}
				else
					pause();
			}
			return true;
		}
		return false;
	}
	
	public boolean onKeyDown(Activity parent, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			Vis.escalateLevel();
			return true;
		}
		return false;
	}
	
	// TODO on~어쩌고 다 Scene으로 가야 한다
	public void onBackPressed(Activity parent) {
		if (mScene.currentScene() == GameData.STAGE_CLEAR) {
			finishGame(parent, "goal", (int) mData.playTime, mData.score);
		}
		else if (mScene.currentScene() == GameData.GAME_OVER) {
			finishGame(parent, "dead", (int) mData.playTime, mData.score);
		}
		else if (isPaused()) {
			finishGame(parent, "quit", (int) mData.playTime, mData.score);
		}
		else {
			pause(true);
		}
	}
	
	public void finishGame(Activity parent, String result, int time, int score) {
		Intent intent = new Intent();
		intent.putExtra("result", result);
		intent.putExtra("time", time);
		intent.putExtra("score", score);

		parent.setResult(Activity.RESULT_OK, intent);
		parent.finish();
	}
	
	public void pause() {
		if (mScene.currentScene() == GameData.PLAYING)
			pause(true);
		else if (mScene.currentScene() == GameData.PAUSED)
			pause(false);
	}
	
	public void pause(boolean set) {
		if (set && mScene.currentScene() == GameData.PLAYING) {
			// 일시 정지
			mScene.pushScene(GameData.PAUSED);
		}
		else if (! set && mScene.currentScene() == GameData.PAUSED) {
			// 다시 시작
			mData.baseTime = mData.timestamp;
			mScene.popScene();
		}
	}
	
	public boolean isPaused() {
		return (mScene.currentScene() == GameData.PAUSED);
	}

	public void makeMockWorld(int stageNumber) {
//		stage = Stage.fromData("[mapsize],800,800/[s],100,400/[f],700,700/[b],400,300,700,500/" +
//								"[a],200,620/[c],256,93/[c],640,142/[c],380,621/[c],684,386/");
		if (stageNumber == 0)
			mData.stage = Stage.fromData(Stage.STAGE_0);
		else if (stageNumber == 1)
			mData.stage = Stage.fromData(Stage.STAGE_1);
		else
			mData.stage = Stage.fromData(Stage.STAGE_2);
	}
	
	/**
	 * 월드를 그려줍니다.
	 * 
	 * @param canvas
	 */
	public void onRender(Canvas canvas) {
		mScene.doRender(canvas);
		
		if (Vis.isEnabled()) {
			resetCamera(canvas, mData.scaleRatio);
			
			float fps = 1000.f / (mData.delta * GameData.TIME_UNIT);
			GameUtil.drawTextMultiline(canvas,
					"Tick: " + mData.tick + " / " + (mData.playTime) + "ms (fps: " + fps + ")"
					+ "\nScreen = (" + mData.screenWidth + ", " + mData.screenHeight + ")"
//					+ "\nAzimuth = " + G.azimuth
//					+ "\nPitch = " + G.pitch
//					+ "\nRoll = " + G.roll
					+ "\nMap = (" + mData.stage.width + ", " + mData.stage.height + ")"
					+ "\nPos = (" + mData.ball.pos.x + ", " + mData.ball.pos.y + ")"
					+ "\nScore = " + mData.score
					+ "\nHP = " + mData.ball.hitpoint
					, 0, 0, Vis.white);
			
			float centerX = mData.screenWidth / 2;
			float centerY = mData.screenHeight / 2;
			float offsetX = FloatMath.cos(mData.gravityDirection) * 50;
			float offsetY = FloatMath.sin(mData.gravityDirection) * 50;
			GameUtil.drawArrow(canvas, centerX, centerY,
					centerX + offsetX, centerY - offsetY, Vis.red);
		}
		
		canvas.drawBitmap(mData.backBuffer, 0, 0, null);
	}

	/**
	 * 물체들을 움직입니다.
	 * 
	 * @param delta
	 */
	public void onFrame() {
		updateTick();
		mScene.doFrame();
	}
	
	private final void updateTick() {
		long now = SystemClock.uptimeMillis();
		
		// 프레임 간격이 아무리 커도 0.5초 이상 넘어가지 않도록 합니다.
		mData.delta = Math.min((now - mData.timestamp) / GameData.TIME_UNIT,
				GameData.TIME_UNIT * 500);
		mData.timestamp = now;
		mData.tick++;
	}
	
	static final void resetCamera(Canvas canvas, float scaleRatio) {
		canvas.setMatrix(null);
		canvas.scale(scaleRatio, scaleRatio);
	}

}

