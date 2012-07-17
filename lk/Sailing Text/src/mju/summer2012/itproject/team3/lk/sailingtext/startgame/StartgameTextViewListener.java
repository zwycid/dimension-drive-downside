package mju.summer2012.itproject.team3.lk.sailingtext.startgame;

import mju.summer2012.itproject.team3.lk.sailingtext.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class StartgameTextViewListener implements OnClickListener {
	private StartGameLinearLayout startGameLinearLayout;

	public StartGameLinearLayout getStartGameLinearLayout() {
		return startGameLinearLayout;
	}
	public void setStartGameLinearLayout(StartGameLinearLayout startGameLinearLayout) {
		this.startGameLinearLayout = startGameLinearLayout;
	}
	
	public void onClick(View clickedView) {
		if(clickedView.getId() == R.id.StartgameTextViewNewgame){
			Toast.makeText(getStartGameLinearLayout().getContext(), "New game", Toast.LENGTH_SHORT).show();
		}else if(clickedView.getId() == R.id.StartgameTextViewLoadgame){
			Toast.makeText(getStartGameLinearLayout().getContext(), "Load game", Toast.LENGTH_SHORT).show();
		}
	}
	public StartgameTextViewListener(StartGameLinearLayout startGameLinearLayout){
		this.setStartGameLinearLayout(startGameLinearLayout);
	}
}
