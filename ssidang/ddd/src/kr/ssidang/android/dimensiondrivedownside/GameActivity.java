package kr.ssidang.android.dimensiondrivedownside;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager.LayoutParams;

public class GameActivity extends Activity implements
		SensorEventListener {
	
	private SensorManager sensorManager;
	private Sensor orientSensor;
	
	private GameView gameView;
	private WorldManager.GameParams params;

	///////////////////////////////////////////////////////////////////////////
	// Activity
	///////////////////////////////////////////////////////////////////////////
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 제목 없애고 전체 화면으로, 화면 안 꺼지도록
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(
			LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON,
			LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.game_layout);
		gameView = (GameView) findViewById(R.id.gameView);
		params = gameView.getGameParams();

		// 방향 센서
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		orientSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		Log.d("ddd", "Sensor acquired.");
        sensorManager.registerListener(this, orientSensor,
        		SensorManager.SENSOR_DELAY_NORMAL);
//        sensorManager.registerListener(this, orientSensor,
//        		SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	protected void onPause() {
		super.onPause();
		
		Log.d("ddd", "Sensor unacquired.");
        sensorManager.unregisterListener(this, orientSensor);
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_MENU) {
			// TODO 디버깅 데이터
			gameView.getGameParams().debug_ = !gameView.getGameParams().debug_;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		gameView.onTouchEvent(this, event);
		return super.onTouchEvent(event);
	}

	@Override
	public void onBackPressed() {
		if (! gameView.onBackPressed(this))
			super.onBackPressed();
	}

	///////////////////////////////////////////////////////////////////////////
	// SensorEventListener
	///////////////////////////////////////////////////////////////////////////

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
	}

	public void onSensorChanged(SensorEvent event) {
		params.azimuth = event.values[0];
		params.pitch = event.values[1];
		params.roll = event.values[2];
	}

}
