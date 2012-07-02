package kr.ssidang.android;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

public class MainActivity extends Activity {
	private SensorManager sensorManager;
	private Sensor oriSensor;
	private SensorEventListener sensorEvent;
	
//	private WakeLock wakeLock;
        
	private TextView sensorXLabel;
	private TextView sensorYLabel;
	private TextView sensorZLabel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Remove notification bar
        // Don't turn off screen
        getWindow().setFlags(LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON,
        		LayoutParams.FLAG_FULLSCREEN | LayoutParams.FLAG_KEEP_SCREEN_ON);
        
        // 화면 고정:
        // android:screenOrientation="portrait"
        // 를 참고. (activity에 있음)
        
        // 화면 안 꺼지게 함
        // FLAG_KEEP_SCREEN_ON으로 대체; WAKE_LOCK 퍼미션 필요 없음
//        PowerManager powerMan = (PowerManager) getSystemService(POWER_SERVICE);
//        wakeLock = powerMan.newWakeLock(PowerManager.FULL_WAKE_LOCK, "kr.ssidang.android");
        
        setContentView(R.layout.activity_main);
        sensorXLabel = (TextView) findViewById(R.id.sensorXLabel);
        sensorYLabel = (TextView) findViewById(R.id.sensorYLabel);
        sensorZLabel = (TextView) findViewById(R.id.sensorZLabel);
        
        
        // 방향 센서 가져옴
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        oriSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorEvent = new OrientationListener();
    }
    
	@Override
	protected void onResume() {
//		wakeLock.acquire();
        sensorManager.registerListener(sensorEvent, oriSensor,
        		SensorManager.SENSOR_DELAY_NORMAL);
		super.onResume();
	}
	
    @Override
	protected void onPause() {
//		wakeLock.release();
		sensorManager.unregisterListener(sensorEvent, oriSensor);
		super.onPause();
	}

	private class OrientationListener implements SensorEventListener {
		public void onAccuracyChanged(Sensor arg0, int arg1) {
			
		}

		public void onSensorChanged(SensorEvent event) {
			// '오른손 좌표계'
			// X축: 오른쪽 방향
			// Y축: 위쪽 방향
			// Z축: 앞쪽 방향
			//
			// Azimuth = 북쪽과 Y축 사이의 각도. Z축 주변 (0 ~ 359), 0=북, 90=동, 180=남, 270=서
			// Pitch = X축 회전 (-180 ~ 180), 누웠을 때 0, 세우면 -90, 뒤집으면 180, 뒤집어 세우면 90
			// Roll = Y축 회전 (-90 ~ 90), 세우면 0, 왼쪽 90, 오른쪽 -90, 뒤집으면 0
			sensorXLabel.setText("Azimuth = " + event.values[0]);
			sensorYLabel.setText("Pitch = " + event.values[1]);
			sensorZLabel.setText("Roll = " + event.values[2]);
		}
    }

    
}
