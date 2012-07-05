package kr.ssidang.android;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class MainActivity extends Activity implements
		RenderThread.Renderable,
		SurfaceHolder.Callback {
	private SensorManager sensorManager;
	private Sensor oriSensor;
	private SensorEventListener sensorEvent;
	
	private SurfaceView surface;
	private float width;
	private float height;
	
	private int tick;
	private Paint textPaint;
	private Paint linePaint;
	private Paint rectPaint;
	
	private float azimuth;
	private float pitch;
	private float roll;

	private RenderThread renderThread;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Remove notification bar
        // Don't turn off screen
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON,
        		LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        /*
	        ȭ�� ����:
	        android:screenOrientation="portrait"
	        �� ����. (activity�� ����)
	        
	        // ȭ�� �� ������ ��
	        // FLAG_KEEP_SCREEN_ON���� ��ü; WAKE_LOCK �۹̼� �ʿ� ����
	        PowerManager powerMan = (PowerManager) getSystemService(POWER_SERVICE);
	        wakeLock = powerMan.newWakeLock(PowerManager.FULL_WAKE_LOCK, "kr.ssidang.android");
        */
        
        setContentView(R.layout.activity_main);
        surface = (SurfaceView) findViewById(R.id.SurfaceView1);
        surface.getHolder().addCallback(this);
        
        // ���� ���� ������
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        oriSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorEvent = new OrientationListener();
        
        // ������ ������: surfaceCreated() ����
    }
    
	@Override
	protected void onDestroy() {
		Log.d("ssidang", "onDestroy()");
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		Log.d("ssidang", "onResume()");
        sensorManager.registerListener(sensorEvent, oriSensor,
        		SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}
	
	@Override
	protected void onPause() {
		Log.d("ssidang", "onPause()");
		sensorManager.unregisterListener(sensorEvent, oriSensor);
		super.onPause();
	}

    @Override
	protected void onRestart() {
		Log.d("ssidang", "onRestart()");
		super.onRestart();
	}

	@Override
	protected void onStop() {
		Log.d("ssidang", "onStop()");
		super.onStop();
	}

	@Override
	public void onBackPressed() {
		// �׽�Ʈ �� �� �����Ƽ� ��� ����
		new AlertDialog.Builder(this)
			.setIcon(android.R.drawable.ic_dialog_alert)
			.setTitle("�����?")
			.setMessage("��¥ �����?")
			.setPositiveButton(android.R.string.yes, new OnClickListener() {
				public void onClick(DialogInterface dialog, int which) {
//					finish(); // ��
					MainActivity.super.onBackPressed();
				}
			})
			.setNegativeButton(android.R.string.no, null)
			.show();
//		super.onBackPressed();
	}

	private class OrientationListener implements SensorEventListener {
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
		}

		public void onSensorChanged(SensorEvent event) {
			// '������ ��ǥ��'
			// X��: ������ ����
			// Y��: ���� ����
			// Z��: ���� ����
			//
			// Azimuth = ���ʰ� Y�� ������ ����. Z�� �ֺ� (0 ~ 359), 0=��, 90=��, 180=��, 270=��
			// Pitch = X�� ȸ�� (-180 ~ 180), ������ �� 0, ����� -90, �������� 180, ������ ����� 90
			// Roll = Y�� ȸ�� (-90 ~ 90), ����� 0, ���� 90, ������ -90, �������� 0
			azimuth = event.values[0];
			pitch = event.values[1];
			roll = event.values[2];
		}
    }

	public void onRenderBegin() {
		textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.WHITE);
		textPaint.setTextSize(8);
		
		linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
//		linePaint = new Paint();
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
		
		canvas.drawText("Tick: " + tick, 0, lineTop, textPaint);
		canvas.drawText("Screen = (" + width + ", " + height + ")", 0, lineTop + lineHeight * 1, textPaint);
		canvas.drawText("Azimuth = " + azimuth, 0, lineTop + lineHeight * 2, textPaint);
		canvas.drawText("Pitch = " + pitch, 0, lineTop + lineHeight * 3, textPaint);
		canvas.drawText("Roll = " + roll, 0, lineTop + lineHeight * 4, textPaint);
		
//		canvas.drawLine(0, 0, 80, 80, linePaint);
		float centerX = width / 2;
		float centerY = height / 2;
		float length = 50;
		double rad = Math.toRadians(pitch);
		float offsetX = (float) (Math.cos(rad) * length);
		float offsetY = (float) (Math.sin(rad) * length);
		canvas.drawLine(centerX, centerY, centerX + offsetX, centerY - offsetY, linePaint);
		
		canvas.drawRect(80, 133, 120, 266.7f, rectPaint);
	}

	public void onRenderEnd() {
		textPaint = null;
		linePaint = null;
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("ssidang", "surfaceCreated()");
		
		// �� ������ �����带 ����ϴ�.
        renderThread = new RenderThread(holder, this);
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("ssidang", "surfaceChanged(" + width + ", " + height + ")");
		
		// 160 ��ǥ��� �������մϴ�.
		// ȭ���� ª�� ���� 160���� �մϴ�.
		// ������ ����.
		int length = Math.min(width, height);
		float factor = length / 160.f;
		this.width = width / factor;
		this.height = height / factor;
		
		renderThread.setScaleFactor(factor);
        renderThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("ssidang", "surfaceDestroyed()");
		
		// ������ �����带 ���Դϴ�.
		renderThread.shutdown();
		this.renderThread = null;
	}

}
