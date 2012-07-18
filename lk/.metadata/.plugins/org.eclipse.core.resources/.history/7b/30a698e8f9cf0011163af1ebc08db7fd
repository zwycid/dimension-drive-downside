package mju.summer2012.itproject.team3.lk.sailingtext.mainmenu;

import mju.summer2012.itproject.team3.lk.sailingtext.R;
import mju.summer2012.itproject.team3.lk.sailingtext.MainActivity;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import android.widget.TextView;

public class MainPage {
	private MainActivity mainActivity;
	private TextView textViewTitle;
	private TextView textViewBlankTitle;
	private TextView textViewBlank;
	private TextView[] textViewArray;
	private TextView[] blankViewArray;

	private static final String[] textviewID	= {"MainTextViewStartgame","MainTextViewRanking","MainTextViewOption"};
	private static final String blankViewID= "MainBlankView";
	private static final int MAINMENU_BLANKVIEW_COUNT	= 3;

	public MainActivity getMainActivity() {		return mainActivity;	}
	public void setMainActivity(MainActivity mainActivity) {		this.mainActivity = mainActivity;	}
	
	public MainPage(MainActivity mainActivity){
		setMainActivity(mainActivity);
		initiateMainmenuPage();
	}
	
	public void initiateMainmenuPage(){

		textViewTitle		= (TextView) getMainActivity().findViewById(R.id.MainTextViewTitle);
		textViewBlankTitle	= (TextView) getMainActivity().findViewById(R.id.MainBlankView);
		textViewBlank		= (TextView) getMainActivity().findViewById(R.id.MainBlankView0);

		LKAndroid.initTextViewShadow(textViewTitle, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		textViewTitle.setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));

		LKAndroid.initBlankView(textViewBlankTitle, getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.LARGE));
		LKAndroid.initBlankView(textViewBlank, getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.LARGE));

		textViewArray	= new TextView[textviewID.length];
		blankViewArray	= new TextView[MAINMENU_BLANKVIEW_COUNT];

		for(int i = 0 ; i < textviewID.length ; i++){
			textViewArray[i]	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", textviewID[i]));
			textViewArray[i].setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
			LKAndroid.initTextViewShadow(textViewArray[i], getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
			textViewArray[i].setOnClickListener(getMainActivity().getTextViewListener());
		}

		for(int j = 1 ; j < MAINMENU_BLANKVIEW_COUNT ; j++){
			blankViewArray[j]	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", blankViewID+j));
			LKAndroid.initBlankView(blankViewArray[j], getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		}
	}
}
