package kr.ssidang.android;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity {

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
    }

    
}
