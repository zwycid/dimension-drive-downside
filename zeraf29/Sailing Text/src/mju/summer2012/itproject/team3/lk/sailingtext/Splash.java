package mju.summer2012.itproject.team3.lk.sailingtext;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

public class Splash extends Activity{
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		Handler h = new Handler();
		h.postDelayed(new splashhandler(), 3000);
	}
	class splashhandler implements Runnable{
		public void run(){
			System.out.println(111);
			startActivity(
					new Intent(getApplication(),MainActivity.class));
				Splash.this.finish();
		}
		
	}
}
