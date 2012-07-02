package kr.ssidang.android;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {
	private SensorManager sensorManager;
	private Sensor oriSensor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Remove title bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        
        // Remove notification bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        // 화면 고정:
        // android:screenOrientation="portrait"
        // 를 참고. (activity에 있음)
        
        setContentView(R.layout.activity_main);
        
        // 방향 센서 가져옴
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        oriSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
//        sensorManager.getOrientation(R, values)
        sensorManager.registerListener(new OrientationListener(), oriSensor,
        		SensorManager.SENSOR_DELAY_GAME);
    }
    
    private class OrientationListener implements SensorEventListener {
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}

		@Override
		public void onSensorChanged(SensorEvent event) {
			
		}
    }

    
}
