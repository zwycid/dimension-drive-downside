package lk.summer.project;

import lk.summer.project.gameuidesign.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	private ListenerForButton listenerForButton;

	private ImageView imageTitle;
	private ImageView ibStartgame;
	private ImageView ibRanking;
	private ImageView ibOption;

	MainMenuView mainMenuView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//        Window window	= this.getWindow(); 

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		this.initiateForeground();
		//     window.setFormat(PixelFormat.TRANSLUCENT);//ȭ���� �ȼ��� �������� ����
//		moveImageView	= new CopyOfMoveImageView(this);
	}
	
	private void initiateForeground(){

		listenerForButton	= new ListenerForButton();

		imageTitle	= (ImageView) this.findViewById(R.id.imageViewTitle);
		Log.i("logcat", imageTitle.toString());
		
		ibStartgame	= (ImageView) this.findViewById(R.id.imageViewStartgame);
		ibRanking	= (ImageView) this.findViewById(R.id.imageViewRanking);
		ibOption	= (ImageView) this.findViewById(R.id.imageViewOptions);
		
		ibStartgame.setOnClickListener(listenerForButton);
		ibRanking.setOnClickListener(listenerForButton);
		ibOption.setOnClickListener(listenerForButton);
	}

	private class ListenerForButton implements OnClickListener	{

		public void onClick(View event) {
			// TODO Auto-generated method stub
			if(event.getId() == R.id.imageViewStartgame){
				Toast.makeText(MainActivity.this, "start game!", Toast.LENGTH_SHORT).show();
			}if(event.getId() == R.id.imageViewRanking){
				Toast.makeText(MainActivity.this, "see Ranking", Toast.LENGTH_SHORT).show();
			}if(event.getId() == R.id.imageViewOptions){
				Toast.makeText(MainActivity.this, "choose option", Toast.LENGTH_SHORT).show();
			}
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    
}