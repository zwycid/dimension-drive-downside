package mju.summer2012.itproject.team3.lk.sailingtext;

import mju.summer2012.itproject.team3.lk.sailing.text.R;
import mju.summer2012.itproject.team3.lk.sailingtext.mainmenu.MainMenuPage;
import mju.summer2012.itproject.team3.lk.sailingtext.ranking.RankingPage;
import mju.summer2012.itproject.team3.lk.sailingtext.startgame.StartGamePage;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	public static final int LARGE	= 12;
	public static final int SMALL	= 17;
	public static final int PLAIN	= 25;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;

	private LKTextViewListener textViewListener;
	private MainMenuPage mainMenuPage;
	private RankingPage rankingPage;
	private StartGamePage startGamePage;

	public LKTextViewListener getListenerForButton() {		return textViewListener;	}
	public void setListenerForButton(LKTextViewListener textViewListener) {		this.textViewListener = textViewListener;	}
	public LKTextViewListener getTextViewListener() {		return textViewListener;	}
	public void setTextViewListener(LKTextViewListener textViewListener) {		this.textViewListener = textViewListener;	}
	public MainMenuPage getMainMenuPage() {		return mainMenuPage;	}
	public void setMainMenuPage(MainMenuPage mainMenuPage) {		this.mainMenuPage = mainMenuPage;	}
	public RankingPage getRankingPage() {		return rankingPage;	}
	public void setRankingPage(RankingPage rankingPage) {		this.rankingPage = rankingPage;	}
	public StartGamePage getStartGamePage() {		return startGamePage;	}
	public void setStartGamePage(StartGamePage startGamePage) {		this.startGamePage = startGamePage;	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_main);
		setListenerForButton(new LKTextViewListener(this));
		this.setMainMenuPage(new MainMenuPage(this));
		this.getMainMenuPage().initiateMainmenuPage();
		this.setStartGamePage(new StartGamePage(this));
		this.setRankingPage(new RankingPage(this));
	}

	@Override
	public void onBackPressed() {

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
				return this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / (LARGE);
			}else if(textSize == SMALL){
				return this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / (SMALL);
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
