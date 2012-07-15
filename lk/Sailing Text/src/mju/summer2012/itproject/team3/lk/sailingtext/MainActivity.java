package mju.summer2012.itproject.team3.lk.sailingtext;

import mju.summer2012.itproject.team3.lk.sailingtext.R;
import mju.summer2012.itproject.team3.lk.sailingtext.mainmenu.MainPage;
import mju.summer2012.itproject.team3.lk.sailingtext.mainmenu.MainTextViewListener;
import mju.summer2012.itproject.team3.lk.sailingtext.ranking.RankingRelativeLayout;
import mju.summer2012.itproject.team3.lk.sailingtext.startgame.StartGameLinearLayout;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	public static final int LARGE	= 11;
	public static final int SMALL	= 15;
	public static final int PLAIN	= 25;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;

	private MainTextViewListener textViewListener;
	private MainPage mainMenuPage;
	private StartGameLinearLayout startGameLinearLayout;
	private RankingRelativeLayout rankingRelativeLayout;
	private ViewFlipper viewFlipper;

	public MainTextViewListener getTextViewListener() {		return textViewListener;	}
	public void setTextViewListener(MainTextViewListener textViewListener) {		this.textViewListener = textViewListener;	}
	public MainPage getMainMenuPage() {		return mainMenuPage;	}
	public void setMainMenuPage(MainPage mainMenuPage) {		this.mainMenuPage = mainMenuPage;	}
	public ViewFlipper getViewFlipper() {
		return viewFlipper;
	}
	public void setViewFlipper(ViewFlipper viewFlipper) {
		this.viewFlipper = viewFlipper;
	}
	public StartGameLinearLayout getStartGameLinearLayout() {
		return startGameLinearLayout;
	}
	public void setStartGameLinearLayout(StartGameLinearLayout startGameLinearLayout) {
		this.startGameLinearLayout = startGameLinearLayout;
	}
	public RankingRelativeLayout getRankingRelativeLayout() {
		return rankingRelativeLayout;
	}
	public void setRankingRelativeLayout(RankingRelativeLayout rankingRelativeLayout) {
		this.rankingRelativeLayout = rankingRelativeLayout;
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		setTextViewListener(new MainTextViewListener(this));
		this.setMainMenuPage(new MainPage(this));
		this.getMainMenuPage().initiateMainmenuPage();
		this.setStartGameLinearLayout((StartGameLinearLayout) this.findViewById(R.id.StartgameLinearLayout));
		this.setRankingRelativeLayout((RankingRelativeLayout) this.findViewById(R.id.RankingRelativeLayout));
	}

	@Override
	public void onBackPressed() {
		Log.i("wtf", "getCurrentView : " + getTextViewListener().getViewFlipper().getCurrentView().getId());
		if((getTextViewListener().getViewFlipper().getCurrentView().getId() == R.id.listViewTotalRanking) || 
				(getTextViewListener().getViewFlipper().getCurrentView().getId() == R.id.listViewStageRanking)){
			//return to main menu page
			getTextViewListener().setViewFlipper((ViewFlipper) this.findViewById(R.id.viewFlipper));
			getTextViewListener().getViewFlipper().showPrevious();
			getTextViewListener().getViewFlipper().showPrevious();
		}else if((getTextViewListener().getViewFlipper().getCurrentView().getId() == R.id.OptionLinearLayout)){
			getTextViewListener().getViewFlipper().showPrevious();
			getTextViewListener().getViewFlipper().showPrevious();
			getTextViewListener().getViewFlipper().showPrevious();
		}else if(getTextViewListener().getViewFlipper().getCurrentView().getId() == R.id.StartgameLinearLayout){
			getTextViewListener().getViewFlipper().showPrevious();
		}else if(getTextViewListener().getViewFlipper().getCurrentView().getId() == R.id.RankingRelativeLayout){
			getTextViewListener().getViewFlipper().showPrevious();
			getTextViewListener().getViewFlipper().showPrevious();
		}
		else
			super.onBackPressed();			
	}

	/**
	 * 스마트폰 화면 크기에 비례하여 적합한 텍스트 크기를 반환합니다.
	 * @param textType : TEXT	- textView나 drawText할 때 사용합니다. 함수는 보이는 글자를 넣는 경우로 인식합니다.
	 * 					 BLANK	- 함수는 textView인데 Blank로 사용할 때, 즉 빈칸을 넣는 경우로 인식합니다. 
	 * @param textSize : LARGE	- 큰 글씨(text button) / 큰 빈칸이 필요할 때 사용합니다.
	 * 					 SMALL	- 작은 글씨(text button) / 작은 빈칸이 필요할 때 사용합니다.
	 * 					 PLAIN	- 일반적인 글씨가 필요할 떄 사용합니다.
	 * @see 적절한 매개변수가 아니면 0을 반환하니 주의하시기 바랍니다.
	 * @return 윈도우 크기에 적합한 텍스트의 크기를 반환합니다.
	 */
	public int getPreferTextSizeForWindow(int textType, int textSize) {
		if(textType == BLANK){
			if(textSize == LARGE){
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / LARGE)*1.5);
			}else if(textSize == SMALL){	
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / SMALL)*1.5);
			}
		}else if(textType == TEXT){
			if(textSize == LARGE){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / LARGE;
			}else if(textSize == SMALL){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / SMALL;
			}else if(textSize == PLAIN){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / PLAIN;
			}
		}
		return 0;
	}
}
