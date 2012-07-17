package kr.ssidang.android.dimensiondrivedownside;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.FloatMath;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements
		SurfaceHolder.Callback,
		RenderThread.Renderable {
	
	public static final float SCALE_UNIT = 160.f;
	public static final float TIME_UNIT = 30.f;
	
	private float width;
	private float height;
	private float scaleFactor;
	private long timestamp;
	
	private RenderThread renderer;
	private WorldManager world;
	private WorldManager.GameParams G;
	
	// 그리기 데이터
	private int tick;
	private Paint textPaint;
	private Paint linePaint;
	private Paint rectPaint;
	
	///////////////////////////////////////////////////////////////////////////
	// 생성자
	///////////////////////////////////////////////////////////////////////////
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GameView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		getHolder().addCallback(this);
		
		world = new WorldManager();
		G = world.getGameParams();
		world.makeMockWorld();
	}
	
	public WorldManager.GameParams getGameParams() {
		return G;
	}
	
	private void resetView(Canvas canvas) {
		canvas.setMatrix(null);
		canvas.scale(scaleFactor, scaleFactor);
	}
	
	///////////////////////////////////////////////////////////////////////////
	// SurfaceHolder
	///////////////////////////////////////////////////////////////////////////
	
	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("ddd", "surfaceCreated()");
		renderer = new RenderThread(holder, this);
		Log.d("ddd", "Renderer created.");
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("ddd", "surfaceChanged(" + width + ", " + height + ")");

		// 좌표계를 맞춥니다.
		int length = Math.min(width, height);
		scaleFactor = length / SCALE_UNIT;
		this.width = width / scaleFactor;
		this.height = height / scaleFactor;

		// 렌더링 시작.
		renderer.start();
		Log.d("ddd", "Renderer started.");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("ddd", "surfaceDestroyed()");
		renderer.shutdown();
		renderer = null;
		Log.d("ddd", "Renderer shutdowned.");
	}
	
	///////////////////////////////////////////////////////////////////////////
	// RenderThread
	///////////////////////////////////////////////////////////////////////////

	public void onRenderBegin() {
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(8);
		
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		linePaint.setColor(Color.RED);
		linePaint.setStrokeWidth(3);
		
		rectPaint = new Paint();
		rectPaint.setColor(0xff108810);
		
		timestamp = System.currentTimeMillis();
	}

	public void onRender(Canvas canvas) {
		long now = System.currentTimeMillis();
		G.delta = (now - timestamp) / TIME_UNIT;
		
		tick++;
		// 배경 지우기
		resetView(canvas);
		canvas.drawColor(Color.BLACK);
		
		// 아래 방향
		float length = 50;
		float sign = (G.pitch >= 0 ? -1.f : 1.f);
//		float sign = (float) Math.cos(Math.toRadians(G.pitch));
		float rad = (float) Math.toRadians((-90 - G.roll) * sign);
		float offsetX = FloatMath.cos(rad) * length;
		float offsetY = FloatMath.sin(rad) * length;
		
		G.gravityDirection = (float) rad;
		world.draw(canvas);
		world.moveObjects(canvas, G.delta);
		
		if (G.debug_) {
			// TODO Debug
			resetView(canvas);
			
			float fps = 1000.f / (G.delta * TIME_UNIT);
			GameUtil.drawTextMultiline(canvas,
					"Tick: " + tick + " (fps: " + fps + ")"
					+ "\nScreen = (" + width + ", " + height + ")"
					+ "\nAzimuth = " + G.azimuth
					+ "\nPitch = " + G.pitch
					+ "\nRoll = " + G.roll
					, 0, 0, textPaint);
			
			float centerX = width / 2;
			float centerY = height / 2;
			GameUtil.drawArrow(canvas, centerX, centerY,
					centerX + offsetX, centerY - offsetY, linePaint);
			
		}
		
		timestamp = now;
	}

	public void onRenderEnd() {
		textPaint = null;
		linePaint = null;
		rectPaint = null;
	}

}
