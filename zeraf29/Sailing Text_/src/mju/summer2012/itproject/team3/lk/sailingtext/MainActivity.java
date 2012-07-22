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
	 * ����Ʈ�� ȭ�� ũ�⿡ ����Ͽ� ������ �ؽ�Ʈ ũ�⸦ ��ȯ�մϴ�.
	 * @param textType : TEXT	- textView�� drawText�� �� ����մϴ�. �Լ��� ���̴� ���ڸ� �ִ� ���� �ν��մϴ�.
	 * 					 BLANK	- �Լ��� textView�ε� Blank�� ����� ��, �� ��ĭ�� �ִ� ���� �ν��մϴ�. 
	 * @param textSize : LARGE	- ū �۾�(text button) / ū ��ĭ�� �ʿ��� �� ����մϴ�.
	 * 					 SMALL	- ���� �۾�(text button) / ���� ��ĭ�� �ʿ��� �� ����մϴ�.
	 * 					 PLAIN	- �Ϲ����� �۾��� �ʿ��� �� ����մϴ�.
	 * @see ������ �Ű������� �ƴϸ� 0�� ��ȯ�ϴ� �����Ͻñ� �ٶ��ϴ�.
	 * @return ������ ũ�⿡ ������ �ؽ�Ʈ�� ũ�⸦ ��ȯ�մϴ�.
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
