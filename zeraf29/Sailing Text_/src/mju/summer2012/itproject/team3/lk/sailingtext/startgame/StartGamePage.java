package mju.summer2012.itproject.team3.lk.sailingtext.startgame;

import mju.summer2012.itproject.team3.lk.sailing.text.R;
import mju.summer2012.itproject.team3.lk.sailingtext.MainActivity;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import android.widget.TextView;

public class StartGamePage {
	private MainActivity mainActivity;
	private TextView StartgameBlankView;
	private TextView StartgameTextViewTitle;
	private TextView StartgameBlankView0;
	private TextView StartgameTextViewNewgame;
	private TextView StartgameBlankView1;
	private TextView StartgameTextViewLoadgame;
	
	public MainActivity getMainActivity() {		return mainActivity;	}
	public void setMainActivity(MainActivity mainActivity) {		this.mainActivity = mainActivity;	}
	public TextView getStartgameBlankView() {		return StartgameBlankView;	}
	public void setStartgameBlankView(TextView startgameBlankView) {		StartgameBlankView = startgameBlankView;	}
	public TextView getStartgameTextViewTitle() {		return StartgameTextViewTitle;	}
	public void setStartgameTextViewTitle(TextView startgameTextViewTitle) {		StartgameTextViewTitle = startgameTextViewTitle;	}
	public TextView getStartgameBlankView0() {		return StartgameBlankView0;	}
	public void setStartgameBlankView0(TextView startgameBlankView0) {		StartgameBlankView0 = startgameBlankView0;	}
	public TextView getStartgameTextViewNewgame() {		return StartgameTextViewNewgame;	}
	public void setStartgameTextViewNewgame(TextView startgameTextViewNewgame) {		StartgameTextViewNewgame = startgameTextViewNewgame;	}
	public TextView getStartgameBlankView1() {		return StartgameBlankView1;	}
	public void setStartgameBlankView1(TextView startgameBlankView1) {		StartgameBlankView1 = startgameBlankView1;	}
	public TextView getStartgameTextViewLoadgame() {		return StartgameTextViewLoadgame;	}
	public void setStartgameTextViewLoadgame(TextView startgameTextViewLoadgame) {		StartgameTextViewLoadgame = startgameTextViewLoadgame;	}

	public StartGamePage(MainActivity mainActivity){
		setMainActivity(mainActivity);
	}
	
	public void initStartGamePage(){
		initializingViews();
		initializingBlankViews();
		initializingTextViews();
		setSizeOfTextViews();
		setTextButtonListenerOnViews();
	}
	
	private void initializingViews(){
		this.setStartgameTextViewTitle((TextView) getMainActivity().findViewById(R.id.StartgameTextViewTitle));
		this.setStartgameTextViewNewgame((TextView) getMainActivity().findViewById(R.id.StartgameTextViewNewgame));
		this.setStartgameTextViewLoadgame((TextView) getMainActivity().findViewById(R.id.StartgameTextViewLoadgame));
		this.setStartgameBlankView((TextView) getMainActivity().findViewById(R.id.StartgameBlankView));
		this.setStartgameBlankView0((TextView) getMainActivity().findViewById(R.id.StartgameBlankView0));
		this.setStartgameBlankView1((TextView) getMainActivity().findViewById(R.id.StartgameBlankView1));
	}
	
	private void initializingBlankViews(){
		LKAndroid.initBlankView(getStartgameBlankView(), getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.LARGE));
		LKAndroid.initBlankView(getStartgameBlankView0(), getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.SMALL));
		LKAndroid.initBlankView(getStartgameBlankView1(), getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.SMALL));
	}
	
	private void initializingTextViews(){
		LKAndroid.initTextViewShadow(getStartgameTextViewTitle(), getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		LKAndroid.initTextViewShadow(getStartgameTextViewNewgame(), getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		LKAndroid.initTextViewShadow(getStartgameTextViewLoadgame(), getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
	}
	private void setSizeOfTextViews(){
		getStartgameTextViewTitle().setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		getStartgameTextViewNewgame().setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		getStartgameTextViewLoadgame().setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
	}
	private void setTextButtonListenerOnViews(){
		getStartgameTextViewNewgame().setOnClickListener(getMainActivity().getTextViewListener());
		getStartgameTextViewLoadgame().setOnClickListener(getMainActivity().getTextViewListener());
	}
}
