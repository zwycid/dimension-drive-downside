package mju.t3rd.sailingtext.ssidang;

import mju.t3rd.sailingtext.R;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.WindowManager.LayoutParams;

public class GameActivity extends Activity implements
		SensorEventListener {
	
	private SensorManager sensorManager;
	private Sensor orientSensor;
	
	private GameView gameView;

	///////////////////////////////////////////////////////////////////////////
	// Activity
	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// NoTitle, Fullscreen은 manifest에서 지정
		// 화면 안 꺼짐 설정
		getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON,
				LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.game_layout);
		gameView = (GameView) findViewById(R.id.gameView);

		// 방향 센서
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		orientSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		gameView.onResume();
		
        sensorManager.registerListener(this, orientSensor,
        		SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(this, orientSensor,
//        		SensorManager.SENSOR_DELAY_GAME);
        Log.d("ddd", "Sensor acquired.");
	}

	@Override
	protected void onPause() {
		super.onPause();
		gameView.onPause();
		
        sensorManager.unregisterListener(this, orientSensor);
        Log.d("ddd", "Sensor unacquired.");
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (gameView.onKeyDown(this, keyCode, event))
			return true;
		else
			return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gameView.onTouchEvent(this, event))
			return true;
		else
			return super.onTouchEvent(event);
	}

	@Override
	public void onBackPressed() {
		gameView.onBackPressed(this);
	}

	///////////////////////////////////////////////////////////////////////////
	// SensorEventListener
	///////////////////////////////////////////////////////////////////////////

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		gameView.onSensorEvent(event.values[0], event.values[1], event.values[2]);
	}

}
