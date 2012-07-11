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

	private static final String[] textviewID	= {"textViewButtonStartgame","textViewButtonRanking","textViewButtonOption"};
	private static final String blankViewID= "blankView";
	private static final int LARGE	= 17;
	private static final int SMALL	= 25;
	private static final int PLAIN	= 40;
	private static final int MAINMENU_BLANKVIEW_COUNT	= 4;

	private static final int DEFAULT_COLOR = 0xff66ffff;
	
	
	private int prefer_to_window_text_size_title;
	private int prefer_to_window_text_size_button;
	private int prefer_to_window_text_size_plain;
	private int prefer_to_window_blank_size_large;
	private int prefer_to_window_blank_size_small;

	private ViewFlipper viewFlipper;

	BackgroundSurfaceView backgroundSurfaceView;
	
	public ListenerForButton getListenerForButton() {		return listenerForButton;	}
	public void setListenerForButton(ListenerForButton listenerForButton) {		this.listenerForButton = listenerForButton;	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		prefer_to_window_text_size_title	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / LARGE;
		prefer_to_window_text_size_button	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / SMALL;
		prefer_to_window_text_size_plain	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / PLAIN;
		prefer_to_window_blank_size_large	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / (LARGE*2);
		prefer_to_window_blank_size_small	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / (SMALL*2);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		viewFlipper	= (ViewFlipper) this.findViewById(R.id.viewFlipper);
		this.initiateMainmenuTextButton();
	}

	private void initiateMainmenuTextButton(){
		setListenerForButton(new ListenerForButton());
		TextView textViewTitle	= (TextView) this.findViewById(R.id.textViewTitleMainmenu);
		TextView[] blankViewArray	= new TextView[MAINMENU_BLANKVIEW_COUNT];
		this.initTextViewShadow(textViewTitle, prefer_to_window_text_size_title);
		textViewTitle.setTextSize(prefer_to_window_text_size_title);
		
		TextView[] textViewArray	= new TextView[textviewID.length];
		
		for(int i = 0 ; i < textviewID.length ; i++){
			try {
				textViewArray[i]	= (TextView) this.findViewById(R.id.class.getField(textviewID[i]).getInt(textviewID[i]));
				textViewArray[i].setTextSize(prefer_to_window_text_size_button);
				this.initTextViewShadow(textViewArray[i], prefer_to_window_text_size_button);
				textViewArray[i].setOnClickListener(listenerForButton);
			} catch (IllegalArgumentException e) {				e.printStackTrace();
			} catch (SecurityException e) {				e.printStackTrace();
			} catch (IllegalAccessException e) {				e.printStackTrace();
			} catch (NoSuchFieldException e) {				e.printStackTrace();
			}
		}
		
		for(int j = 0 ; j < 4 ; j++){
			try {
				blankViewArray[j]	= (TextView) this.findViewById(R.id.class.getField(blankViewID+j).getInt(blankViewID+j));
				initBlankView(blankViewArray[j], prefer_to_window_blank_size_small);
			} catch (IllegalArgumentException e) {				e.printStackTrace();
			} catch (SecurityException e) {				e.printStackTrace();
			} catch (IllegalAccessException e) {				e.printStackTrace();
			} catch (NoSuchFieldException e) {				e.printStackTrace();
			}
		}
	}
	
	private void initTextViewShadow(TextView textView, int textSize){
		textView.setShadowLayer((0.0f+textSize)/5, 0.0f, 0.0f, DEFAULT_COLOR);
	}
	
	private void initBlankView(TextView blankView, int blankSize){
		blankView.setHeight(blankSize);
	}
	
	private void initRankingTextButtons(){
		TextView textViewTitleRanking	= (TextView) this.findViewById(R.id.textViewTitleRanking);
		TextView textViewButtonSwap		= (TextView) this.findViewById(R.id.textViewButtonSwap);
		TextView textViewButtonReset	= (TextView) this.findViewById(R.id.textViewButtonReset);
		viewFlipper	= (ViewFlipper) this.findViewById(R.id.viewFlipperForListView);
		
		textViewTitleRanking.setTextSize(prefer_to_window_text_size_title);
		
		textViewButtonSwap.setTextSize(prefer_to_window_text_size_plain);
		textViewButtonReset.setTextSize(prefer_to_window_text_size_plain);
		
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
