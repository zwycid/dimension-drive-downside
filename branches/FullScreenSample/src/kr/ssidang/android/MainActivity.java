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
        
        
        // ���� ���� ������
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

    
}
