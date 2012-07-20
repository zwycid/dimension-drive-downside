package mju.t3rd.sailingtext.zeraf29;

import mju.t3rd.sailingtext.R;
import mju.t3rd.sailingtext.ssidang.GameActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager.LayoutParams;
import android.widget.Toast;

public class Splash extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);

		// 화면 안 꺼짐 설정
		getWindow().setFlags(LayoutParams.FLAG_KEEP_SCREEN_ON,
				LayoutParams.FLAG_KEEP_SCREEN_ON);
		
		setContentView(R.layout.splash);
		new Handler().postDelayed(new SplashHandler(), 300);
	}
	
	class SplashHandler implements Runnable{
		public void run(){
//			startActivity(
//					new Intent(getApplication(),MainActivity.class));
//				Splash.this.finish();
			Toast.makeText(Splash.this, "PM님이 메뉴 파일을 안 주세요!", Toast.LENGTH_LONG).show();
			startActivity(new Intent(getApplication(), GameActivity.class));
			finish();
		}
		
	}
}
