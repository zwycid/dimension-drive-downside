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
        
        // ȭ�� ����:
        // android:screenOrientation="portrait"
        // �� ����. (activity�� ����)
        
        setContentView(R.layout.activity_main);
        
        // ���� ���� ������
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
