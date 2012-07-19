package kr.ssidang.android.dimensiondrivedownside;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements
		SurfaceHolder.Callback,
		RenderThread.Renderable {
	
	private RenderThread renderer;
	private WorldManager world;
	private WorldManager.GameParams G;
	
	///////////////////////////////////////////////////////////////////////////
	// 생성자
	///////////////////////////////////////////////////////////////////////////
	
	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public GameView(Context context) {
		super(context);
		init(context);
	}
	
	private void init(Context context) {
		getHolder().addCallback(this);
		
		world = new WorldManager(context);
		G = world.getGameParams();
		world.makeMockWorld();
	}
	
	public WorldManager.GameParams getGameParams() {
		return G;
	}
	
	public void onTouchEvent(Activity parent, MotionEvent event) {
		world.onTouchEvent(parent, event);
	}
	
	public void onBackPressed(Activity parent) {
		world.onBackPressed(parent);
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
		G.scaleFactor = length / WorldManager.SCALE_UNIT;
		G.screenWidth = width / G.scaleFactor;
		G.screenHeight = height / G.scaleFactor;

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
		G.timestamp = System.currentTimeMillis();
	}

	public void onRender(Canvas canvas) {
		long now = System.currentTimeMillis();
		G.delta = (now - G.timestamp) / WorldManager.TIME_UNIT;
		G.tick++;
		
		// 아래 방향
		float sign = (G.pitch >= 0 ? -1.f : 1.f);
		float rad = (float) Math.toRadians((-90 - G.roll) * sign);
		G.gravityDirection = (float) rad;
		
		world.onRender(canvas);
		world.onFrame(G.delta);
		
		G.timestamp = now;
	}

	public void onRenderEnd() {
	}

}
