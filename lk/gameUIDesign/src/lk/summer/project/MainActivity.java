package lk.summer.project;

import lk.summer.project.gameuidesign.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	private ListenerForButton listenerForButton;

	private final String[] textviewID	= {"textViewButtonStartgame","textViewButtonRanking","textViewButtonOption"};
	
	private int prefer_to_window_text_size_large;
	private int prefer_to_window_text_size_small;

	private ViewFlipper viewFlipper;

	BackgroundSurfaceView backgroundSurfaceView;
	
	public ListenerForButton getListenerForButton() {		return listenerForButton;	}
	public void setListenerForButton(ListenerForButton listenerForButton) {		this.listenerForButton = listenerForButton;	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		prefer_to_window_text_size_large	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / 17;
		prefer_to_window_text_size_small	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / 25;
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		viewFlipper	= (ViewFlipper) this.findViewById(R.id.viewFlipper);
		this.initiateTextButton();
	}

	private void initiateTextButton(){
		setListenerForButton(new ListenerForButton());
		TextView textViewTitle	= (TextView) this.findViewById(R.id.textViewTitleMainmenu);
		textViewTitle.setTextSize(prefer_to_window_text_size_large);
		TextView[] textViewArray	= new TextView[textviewID.length];
		
		for(int i = 0 ; i < textviewID.length ; i++){
			try {
				textViewArray[i]	= (TextView) this.findViewById(R.id.class.getField(textviewID[i]).getInt(textviewID[i]));
				textViewArray[i].setTextSize(prefer_to_window_text_size_small);
				textViewArray[i].setOnClickListener(listenerForButton);
			} catch (IllegalArgumentException e) {				e.printStackTrace();
			} catch (SecurityException e) {				e.printStackTrace();
			} catch (IllegalAccessException e) {				e.printStackTrace();
			} catch (NoSuchFieldException e) {				e.printStackTrace();
			}
		}
	}
	
	private void initRankingTextButtons(){
		TextView textViewTitleRanking	= (TextView) this.findViewById(R.id.textViewTitleRanking);
		TextView textViewButtonSwap		= (TextView) this.findViewById(R.id.textViewButtonSwap);
		TextView textViewButtonReset	= (TextView) this.findViewById(R.id.textViewButtonReset);
		viewFlipper	= (ViewFlipper) this.findViewById(R.id.viewFlipperForListView);
		
		textViewTitleRanking.setTextSize(prefer_to_window_text_size_large);
		
		textViewButtonSwap.setTextSize(prefer_to_window_text_size_small);
		textViewButtonReset.setTextSize(prefer_to_window_text_size_small);
		
		textViewButtonSwap.setOnClickListener(listenerForButton);
		textViewButtonReset.setOnClickListener(listenerForButton);
	}
	
	private class ListenerForButton implements OnClickListener{

		public void onClick(View clickedView) {
			// TODO Auto-generated method stub
			if(clickedView.getId() == R.id.textViewButtonStartgame){
			}else if(clickedView.getId() == R.id.textViewButtonRanking){
				//xml에 등록된 view라면 바로 그냥 shownext해도 됨.
				viewFlipper.showNext();
				initRankingTextButtons();
			}else if(clickedView.getId() == R.id.textViewButtonOption){
				//두 번 연속으로 호출하면 그냥 맨 마지막 view가 보임.
				viewFlipper.showNext();
				viewFlipper.showNext();
			}else if(clickedView.getId() == R.id.textViewButtonSwap){
				if(viewFlipper.getCurrentView().getId() == R.id.listViewTotalRanking){
					Toast.makeText(MainActivity.this, "stage", Toast.LENGTH_SHORT).show();
					viewFlipper.showNext();
				}else{
					Toast.makeText(MainActivity.this, "total", Toast.LENGTH_SHORT).show();
					viewFlipper.showPrevious();
				}
			}else if(clickedView.getId() == R.id.textViewButtonReset){
				Toast.makeText(MainActivity.this, "swap", Toast.LENGTH_SHORT).show();
			}
		}
	}
}
