package lk.summer.project;

import lk.summer.project.gameuidesign.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	/** Called when the activity is first created. */
	private ListenerForButton listenerForButton;

	private TextView tvTitle;
	private TextView tvStartgame;
	private TextView tvRanking;
	private TextView tvOption;
	
	private static final String[] mainText = {"Start Game","Ranking","Option"};
	
	MainMenuView mainMenuView;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		this.initiateTextButton();
	}

	private void initiateTextButton(){

		setListenerForButton(new ListenerForButton());
		Log.i("wtf", "wtf");
		
		setTvTitle((TextView) this.findViewById(R.id.textViewTitle));
		getTvTitle().setText("sailing text");
		
		getTvTitle().setText("sailing text");
		
		setTvStartgame((TextView) this.findViewById(R.id.textViewStartgame));
		getTvStartgame().setText(mainText[0]);
		setTvRanking((TextView) this.findViewById(R.id.textViewRanking));
		getTvRanking().setText(mainText[1]);
		setTvOption((TextView) this.findViewById(R.id.textViewOption));
		getTvOption().setText(mainText[2]);
		
		tvStartgame.setOnClickListener(listenerForButton);
		tvRanking.setOnClickListener(listenerForButton);
		tvOption.setOnClickListener(listenerForButton);
	}
	
	public ListenerForButton getListenerForButton() {		return listenerForButton;	}
	public void setListenerForButton(ListenerForButton listenerForButton) {		this.listenerForButton = listenerForButton;	}

	public TextView getTvTitle() {		return tvTitle;	}
	public void setTvTitle(TextView tvTitle) {		this.tvTitle = tvTitle;	}	public TextView getTvStartgame() {		return tvStartgame;	}
	public void setTvStartgame(TextView tvStartgame) {		this.tvStartgame = tvStartgame;	}
	public TextView getTvRanking() {		return tvRanking;	}
	public void setTvRanking(TextView tvRanking) {		this.tvRanking = tvRanking;	}
	public TextView getTvOption() {		return tvOption;	}
	public void setTvOption(TextView tvOption) {		this.tvOption = tvOption;	}

	private class ListenerForButton implements OnClickListener{

		public void onClick(View clickedView) {
			// TODO Auto-generated method stub
			if(clickedView.getId() == R.id.textViewStartgame)
				Toast.makeText(MainActivity.this, "start game", Toast.LENGTH_SHORT).show();
			else if(clickedView.getId() == R.id.textViewRanking)
				Toast.makeText(MainActivity.this, "ranking", Toast.LENGTH_SHORT).show();
			else if(clickedView.getId() == R.id.textViewOption)
				Toast.makeText(MainActivity.this, "option", Toast.LENGTH_SHORT).show();
		}
	}
}
