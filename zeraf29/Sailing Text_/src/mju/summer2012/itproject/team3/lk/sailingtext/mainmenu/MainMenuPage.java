package mju.summer2012.itproject.team3.lk.sailingtext.mainmenu;

import mju.summer2012.itproject.team3.lk.sailing.text.R;
import mju.summer2012.itproject.team3.lk.sailingtext.MainActivity;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import android.widget.TextView;

public class MainMenuPage {
	private MainActivity mainActivity;
	private TextView textViewTitle;
	private TextView textViewBlankTitle;
	private TextView[] textViewArray;
	private TextView[] blankViewArray;

	private static final String[] textviewID	= {"MainmenutextViewStartgame","MainmenutextViewRanking","MainmenutextViewOption"};
	private static final String blankViewID= "MainmenuBlankView";
	private static final int MAINMENU_BLANKVIEW_COUNT	= 3;

	public MainActivity getMainActivity() {		return mainActivity;	}
	public void setMainActivity(MainActivity mainActivity) {		this.mainActivity = mainActivity;	}
	
	public MainMenuPage(MainActivity mainActivity){
		setMainActivity(mainActivity);
	}
	
	public void initiateMainmenuPage(){

		textViewTitle		= (TextView) getMainActivity().findViewById(R.id.MainmenutextViewTitle);
		textViewBlankTitle	= (TextView) getMainActivity().findViewById(R.id.MainmenuBlankView);

		LKAndroid.initTextViewShadow(textViewTitle, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		textViewTitle.setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		LKAndroid.initBlankView(textViewBlankTitle, getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.LARGE));

		textViewArray	= new TextView[textviewID.length];
		blankViewArray	= new TextView[MAINMENU_BLANKVIEW_COUNT];

		for(int i = 0 ; i < textviewID.length ; i++){
			textViewArray[i]	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", textviewID[i]));
			textViewArray[i].setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
			LKAndroid.initTextViewShadow(textViewArray[i], getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
			textViewArray[i].setOnClickListener(getMainActivity().getTextViewListener());
		}

		for(int j = 0 ; j < MAINMENU_BLANKVIEW_COUNT ; j++){
			blankViewArray[j]	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", blankViewID+j));
			LKAndroid.initBlankView(blankViewArray[j], getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		}
	}
}
