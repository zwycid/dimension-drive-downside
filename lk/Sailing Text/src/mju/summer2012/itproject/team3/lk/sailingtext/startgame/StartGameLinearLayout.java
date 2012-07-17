package mju.summer2012.itproject.team3.lk.sailingtext.startgame;

import mju.summer2012.itproject.team3.lk.sailingtext.MainActivity;
import mju.summer2012.itproject.team3.lk.sailingtext.R;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class StartGameLinearLayout extends LinearLayout {

	private TextView StartgameBlankView;
	private TextView StartgameTextViewTitle;
	private TextView StartgameBlankView0;
	private TextView StartgameTextViewNewgame;
	private TextView StartgameBlankView1;
	private TextView StartgameTextViewLoadgame;
	private StartgameTextViewListener startgameTextViewListener;
	
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
	public StartgameTextViewListener getStartgameTextViewListener() {
		return startgameTextViewListener;
	}
	public void setStartgameTextViewListener(
			StartgameTextViewListener startgameTextViewListener) {
		this.startgameTextViewListener = startgameTextViewListener;
	}

	public StartGameLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initStartGameLayout();
	}
	
	public StartGameLinearLayout(Context context) {
		super(context);
		initStartGameLayout();
	}
	
	public void initStartGameLayout(){
		initializingViews();
		initializingBlankViews();
		initializingTextViews();
		setSizeOfTextViews();
		setTextButtonListenerOnViews();
	}
	
	private void initializingViews(){
		LayoutInflater.from(getContext()).inflate(R.layout.startgamelinearlayout, (ViewGroup) this.findViewById(this.getId()));

		this.setStartgameTextViewTitle((TextView) this.findViewById(R.id.StartgameTextViewTitle));
		this.setStartgameTextViewNewgame((TextView) this.findViewById(R.id.StartgameTextViewNewgame));
		this.setStartgameTextViewLoadgame((TextView) this.findViewById(R.id.StartgameTextViewLoadgame));
		this.setStartgameBlankView((TextView) this.findViewById(R.id.StartgameBlankView));
		this.setStartgameBlankView0((TextView) this.findViewById(R.id.StartgameBlankView0));
		this.setStartgameBlankView1((TextView) this.findViewById(R.id.StartgameBlankView1));
	}
	
	private void initializingBlankViews(){
		LKAndroid.initBlankView(getStartgameBlankView(), ((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.LARGE));
		LKAndroid.initBlankView(getStartgameBlankView0(), ((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.LARGE));
		LKAndroid.initBlankView(getStartgameBlankView1(), ((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.SMALL));
	}
	
	private void initializingTextViews(){
		LKAndroid.initTextViewShadow(getStartgameTextViewTitle(), ((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		LKAndroid.initTextViewShadow(getStartgameTextViewNewgame(), ((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		LKAndroid.initTextViewShadow(getStartgameTextViewLoadgame(), ((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
	}
	private void setSizeOfTextViews(){
		getStartgameTextViewTitle().setTextSize(((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		getStartgameTextViewNewgame().setTextSize(((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
		getStartgameTextViewLoadgame().setTextSize(((MainActivity) getContext()).getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.SMALL));
	}
	
	private void setTextButtonListenerOnViews(){
		setStartgameTextViewListener(new StartgameTextViewListener(this));
		getStartgameTextViewNewgame().setOnClickListener(this.getStartgameTextViewListener());
		getStartgameTextViewLoadgame().setOnClickListener(this.getStartgameTextViewListener());
	}
}
