package mju.t3rd.sailingtext.zeraf29;

import mju.t3rd.sailingtext.R;
import mju.t3rd.sailingtext.lk.MainActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

public class Splash extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// È­¸é ¾È ²¨Áü ¼³Á¤
		getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON,
				LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.splash);
		new Handler().postDelayed(new SplashHandler(), 2000);
	}
	
	class SplashHandler implements Runnable{
		public void run(){
			Toast.makeText(Splash.this, "SailingText!", Toast.LENGTH_SHORT).show();
			startActivity(new Intent(getApplication(), MainActivity.class));
			finish();
		}
		
	}
}
