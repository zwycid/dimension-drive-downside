package mju.t3rd.sailingtext.ssidang;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements
		SurfaceHolder.Callback,
		RenderThread.Renderable {
	
	private RenderThread renderer;
	private WorldManager world;
	
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
		
		GameActivity activity = (GameActivity) context;
		
		// TODO 여기서 스테이지 넘어온 거 받든가 해야 함
		world = new WorldManager(context);
		world.makeMockWorld(activity.stageNumber);
	}
	
	public void onPause() {
		world.pause(true);
	}
	
	public void onResume() {
		// pass
	}
	
	public void onSensorEvent(float azimuth, float pitch, float roll) {
		world.onSensorEvent(azimuth, pitch, roll);
	}
	
	public boolean onTouchEvent(Activity parent, MotionEvent event) {
		return world.onTouchEvent(parent, event);
	}
	
	public boolean onKeyDown(Activity parent, int keyCode, KeyEvent event) {
		return world.onKeyDown(parent, keyCode, event);
	}
	
	public void onBackPressed(Activity parent) {
		world.onBackPressed(parent);
	}
	
	///////////////////////////////////////////////////////////////////////////
	// SurfaceHolder
	///////////////////////////////////////////////////////////////////////////
	
	public void surfaceCreated(SurfaceHolder holder) {
		renderer = new RenderThread(holder, this);
		Log.d("ddd", "surfaceCreated(), Renderer created.");
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("ddd", "surfaceChanged(" + width + ", " + height + ")");
		world.onScreenSize(width, height);

		// 렌더링 시작.
		renderer.start();
		Log.d("ddd", "Renderer started.");
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		renderer.shutdown();
		renderer = null;
		Log.d("ddd", "surfaceDestroyed(), Renderer shutdowned.");
	}
	
	///////////////////////////////////////////////////////////////////////////
	// RenderThread
	///////////////////////////////////////////////////////////////////////////

	public void onRenderBegin() {
	}

	public void onRender(Canvas canvas) {
		world.onFrame();
		world.onRender(canvas);
	}

	public void onRenderEnd() {
	}

}
