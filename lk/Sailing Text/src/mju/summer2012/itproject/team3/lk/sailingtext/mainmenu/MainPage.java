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
	private TextView textViewStargame;
	private TextView blankView1;
	private TextView textViewOption;
	private TextView blankView2;

	private static final String blankViewID= "MainBlankView";

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


		textViewStargame	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", "MainTextViewStartgame"));
		textViewStargame.setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		LKAndroid.initTextViewShadow(textViewStargame, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		textViewStargame.setOnClickListener(getMainActivity().getTextViewListener());

		blankView1	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", blankViewID+1));
		LKAndroid.initBlankView(blankView1, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));

		textViewOption	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", "MainTextViewOption"));
		textViewOption.setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		LKAndroid.initTextViewShadow(textViewOption, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		textViewOption.setOnClickListener(getMainActivity().getTextViewListener());

		blankView2	= (TextView) getMainActivity().findViewById(LKAndroid.getID("id", blankViewID+1));
		LKAndroid.initBlankView(blankView2, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
}
}
