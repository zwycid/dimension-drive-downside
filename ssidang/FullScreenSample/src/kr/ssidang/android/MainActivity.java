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
import android.widget.TextView;

public class MainActivity extends Activity implements
		Runnable,
		SurfaceHolder.Callback {
	private SensorManager sensorManager;
	private Sensor oriSensor;
	private SensorEventListener sensorEvent;
	
	private TextView sensorXLabel;
	private TextView sensorYLabel;
	private TextView sensorZLabel;
	private SurfaceView surface;
	private SurfaceHolder holder;
	
	private boolean quitFlags;
	private int tick;

	private Thread renderThread;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Remove notification bar
        // Don't turn off screen
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON,
        		LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // ȭ�� ����:
        // android:screenOrientation="portrait"
        // �� ����. (activity�� ����)
        
        // ȭ�� �� ������ ��
        // FLAG_KEEP_SCREEN_ON���� ��ü; WAKE_LOCK �۹̼� �ʿ� ����
//        PowerManager powerMan = (PowerManager) getSystemService(POWER_SERVICE);
//        wakeLock = powerMan.newWakeLock(PowerManager.FULL_WAKE_LOCK, "kr.ssidang.android");
        
        setContentView(R.layout.activity_main);
        sensorXLabel = (TextView) findViewById(R.id.sensorXLabel);
        sensorYLabel = (TextView) findViewById(R.id.sensorYLabel);
        sensorZLabel = (TextView) findViewById(R.id.sensorZLabel);
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
			sensorXLabel.setText("Azimuth = " + event.values[0]);
			sensorYLabel.setText("Pitch = " + event.values[1]);
			sensorZLabel.setText("Roll = " + event.values[2]);
		}
    }

	public void run() {
		Paint textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(30);
		
		while (! quitFlags) {
			Canvas canvas = holder.lockCanvas();
			try {
				tick++;
				canvas.drawColor(Color.GREEN);
				canvas.drawText(String.valueOf(tick), 0, -textPaint.ascent(), textPaint);
			}
			finally {
				holder.unlockCanvasAndPost(canvas);
			}
		}
	}
	
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		Log.d("ssidang", "surfaceChanged()");
	}

	public void surfaceCreated(SurfaceHolder holder) {
		Log.d("ssidang", "surfaceCreated()");
		
		// �� ������ �����带 ����ϴ�.
		this.holder = holder;
        quitFlags = false;
        renderThread = new Thread(this);
        renderThread.start();
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("ssidang", "surfaceDestroyed()");
		
		// ������ �����带 ���Դϴ�.
		try {
			quitFlags = true;
			renderThread.join();
		}
		catch (InterruptedException e) {
		}
		this.renderThread = null;
		this.holder = null;
	}

}
