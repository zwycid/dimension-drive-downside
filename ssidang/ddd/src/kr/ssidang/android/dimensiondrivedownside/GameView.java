package kr.ssidang.android.dimensiondrivedownside;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements
		SurfaceHolder.Callback,
		RenderThread.Renderable {
	
	public static final float SCALE_UNIT = 160.f;
	
	public boolean debug_;
	
	private float width;
	private float height;
	
	private RenderThread renderer;
	
	// 그리기 데이터
	private int tick;
	private Paint textPaint;
	private Paint linePaint;
	private Paint rectPaint;
	
	private GameParams G;

	///////////////////////////////////////////////////////////////////////////
	// GameData
	///////////////////////////////////////////////////////////////////////////
	class GameParams {
		float azimuth;
		float pitch;
		float roll;
	}
	
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
		G = new GameParams();
		getHolder().addCallback(this);
	}
	
	public GameParams getGameParams() {
		return G;
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
		float factor = length / SCALE_UNIT;
		this.width = width / factor;
		this.height = height / factor;

		// 렌더링 시작.
		renderer.setScaleFactor(factor);
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
	}

	public void onRender(Canvas canvas) {

		float lineTop = -textPaint.ascent();
		float lineHeight = (-textPaint.ascent() + textPaint.descent()) * 1.1f;
		
		tick++;
		canvas.drawColor(Color.BLACK);
		
		if (debug_) {
			canvas.drawText("Tick: " + tick, 0, lineTop, textPaint);
			canvas.drawText("Screen = (" + width + ", " + height + ")", 0, lineTop + lineHeight * 1, textPaint);
			canvas.drawText("Azimuth = " + G.azimuth, 0, lineTop + lineHeight * 2, textPaint);
			canvas.drawText("Pitch = " + G.pitch, 0, lineTop + lineHeight * 3, textPaint);
			canvas.drawText("Roll = " + G.roll, 0, lineTop + lineHeight * 4, textPaint);
		}
		
		// 사각형 그려보기
		canvas.drawRect(80, 133, 120, 266.7f, rectPaint);
		
		// 선 그려보기
		float centerX = width / 2;
		float centerY = height / 2;
		float length = 50;
		double rad = Math.toRadians(G.pitch);
		float offsetX = (float) (Math.cos(rad) * length);
		float offsetY = (float) (Math.sin(rad) * length);
		canvas.drawLine(centerX, centerY, centerX + offsetX, centerY - offsetY, linePaint);
	}

	public void onRenderEnd() {
		textPaint = null;
		linePaint = null;
		rectPaint = null;
	}

}
