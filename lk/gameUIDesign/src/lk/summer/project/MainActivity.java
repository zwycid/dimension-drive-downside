package lk.summer.project;

import lk.summer.project.gameuidesign.R;
import lk.summer.project.lkcustom.LKAndroid;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {
	private ListenerForButton listenerForButton;

	private static final String[] textviewID	= {"textViewButtonStartgame","textViewButtonRanking","textViewButtonOption"};
	private static final String blankViewID= "blankView";
	private static final int LARGE	= 12;
	private static final int SMALL	= 17;
	private static final int PLAIN	= 25;
	private static final int MAINMENU_BLANKVIEW_COUNT	= 4;

	public static int prefer_to_window_text_size_title;
	public static int prefer_to_window_text_size_button;
	public static int prefer_to_window_text_size_plain;
	public static int prefer_to_window_blank_size_large;
	public static int prefer_to_window_blank_size_small;

	private ViewFlipper viewFlipper;

	BackgroundSurfaceView backgroundSurfaceView;

	public ListenerForButton getListenerForButton() {		return listenerForButton;	}
	public void setListenerForButton(ListenerForButton listenerForButton) {		this.listenerForButton = listenerForButton;	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		prefer_to_window_text_size_title	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / LARGE;
		prefer_to_window_text_size_button	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / SMALL;
		prefer_to_window_text_size_plain	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / PLAIN;
		prefer_to_window_blank_size_large	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / (SMALL);
		prefer_to_window_blank_size_small	= 
				this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / (LARGE);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		viewFlipper	= (ViewFlipper) this.findViewById(R.id.viewFlipper);
		this.initiateMainmenuPage();
	}

	private void initiateMainmenuPage(){
		setListenerForButton(new ListenerForButton());

		TextView textViewTitle		= (TextView) this.findViewById(R.id.textViewTitleMainmenu);
		TextView textViewBlankTitle	= (TextView) this.findViewById(R.id.blankView0);

		LKAndroid.initTextViewShadow(textViewTitle, prefer_to_window_text_size_title);
		textViewTitle.setTextSize(prefer_to_window_text_size_title);
		LKAndroid.initBlankView(textViewBlankTitle, prefer_to_window_blank_size_large);

		TextView[] textViewArray	= new TextView[textviewID.length];
		TextView[] blankViewArray	= new TextView[MAINMENU_BLANKVIEW_COUNT];

		for(int i = 0 ; i < textviewID.length ; i++){
				textViewArray[i]	= (TextView) this.findViewById(LKAndroid.getID("id", textviewID[i]));
				textViewArray[i].setTextSize(prefer_to_window_text_size_button);
				LKAndroid.initTextViewShadow(textViewArray[i], prefer_to_window_text_size_button);
				textViewArray[i].setOnClickListener(listenerForButton);
		}

		for(int j = 1 ; j < MAINMENU_BLANKVIEW_COUNT ; j++){
			try {
				blankViewArray[j]	= (TextView) this.findViewById(R.id.class.getField(blankViewID+j).getInt(blankViewID+j));
				LKAndroid.initBlankView(blankViewArray[j], prefer_to_window_blank_size_small);
			} catch (IllegalArgumentException e) {				e.printStackTrace();
			} catch (SecurityException e) {				e.printStackTrace();
			} catch (IllegalAccessException e) {				e.printStackTrace();
			} catch (NoSuchFieldException e) {				e.printStackTrace();
			}
		}
	}
	
	private void initRankingPage(){
		TextView textViewTitleRanking		= (TextView) this.findViewById(R.id.textViewTitleRanking);
		TextView textViewButtonSwap			= (TextView) this.findViewById(R.id.textViewButtonSwap);
		TextView textViewButtonReset		= (TextView) this.findViewById(R.id.textViewButtonReset);
		TextView textViewRankingBlankTitle	= (TextView) this.findViewById(R.id.blankViewRanking0);
		TextView textViewRankingBlank		= (TextView) this.findViewById(R.id.blankViewRanking1);
		Spinner spinnerRanking				= (Spinner) this.findViewById(R.id.spinnerStage);

		LKAndroid.initBlankView(textViewRankingBlankTitle, prefer_to_window_blank_size_large);
		LKAndroid.initBlankView(textViewRankingBlank, prefer_to_window_blank_size_small);

		viewFlipper	= (ViewFlipper) this.findViewById(R.id.viewFlipperForListView);

		textViewTitleRanking.setTextSize(prefer_to_window_text_size_title);
		LKAndroid.initTextViewShadow(textViewTitleRanking, prefer_to_window_text_size_title);

		textViewButtonSwap.setText("Total Ranking");
		textViewButtonSwap.setTextSize(prefer_to_window_text_size_plain);
		LKAndroid.initTextViewShadow(textViewButtonSwap, prefer_to_window_text_size_button);

		textViewButtonReset.setTextSize(prefer_to_window_text_size_plain);
		LKAndroid.initTextViewShadow(textViewButtonReset, prefer_to_window_text_size_button);

		textViewButtonSwap.setOnClickListener(listenerForButton);
		textViewButtonReset.setOnClickListener(listenerForButton);

		spinnerRanking.setVisibility(Spinner.INVISIBLE);
	}

	private class ListenerForButton implements OnClickListener{

		public void onClick(View clickedView) {
			if(clickedView.getId() == R.id.textViewButtonStartgame){
			}else if(clickedView.getId() == R.id.textViewButtonRanking){
				//xml에 등록된 view라면 바로 그냥 shownext해도 됨.
				viewFlipper.showNext();
				initRankingPage();
			}else if(clickedView.getId() == R.id.textViewButtonOption){
				//두 번 연속으로 호출하면 그냥 맨 마지막 view가 보임.
				viewFlipper.showNext();
				viewFlipper.showNext();
			}else if(clickedView.getId() == R.id.textViewButtonSwap){
				TextView textViewSwap	= (TextView) MainActivity.this.findViewById(R.id.textViewButtonSwap);
				Spinner spinnerRanking	= (Spinner) MainActivity.this.findViewById(R.id.spinnerStage);
				if(viewFlipper.getCurrentView().getId() == R.id.listViewTotalRanking){
					spinnerRanking.setVisibility(Spinner.VISIBLE);
					textViewSwap.setText("Stage Ranking");
					viewFlipper.showNext();
				}else{
					spinnerRanking.setVisibility(Spinner.INVISIBLE);
					textViewSwap.setText("Total Ranking");
					viewFlipper.showPrevious();
				}
			}else if(clickedView.getId() == R.id.textViewButtonReset){
				Toast.makeText(MainActivity.this, "RESET", Toast.LENGTH_SHORT).show();
			}
		}
	}

	@Override
	public void onBackPressed() {
		if((viewFlipper.getCurrentView().getId() == R.id.listViewTotalRanking) || (viewFlipper.getCurrentView().getId() == R.id.listViewStageRanking)){
			//return to main menu page
			viewFlipper = (ViewFlipper) this.findViewById(R.id.viewFlipper);
			viewFlipper.showPrevious();
		}else if((viewFlipper.getCurrentView().getId() == R.id.OptionLinearLayout)){
			viewFlipper	= (ViewFlipper) this.findViewById(R.id.viewFlipper);
			viewFlipper.showPrevious();
			viewFlipper.showPrevious();
		}else
			super.onBackPressed();			
	}
}
