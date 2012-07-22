package mju.t3rd.sailingtext.lk.option;

import mju.t3rd.sailingtext.lk.R;
import mju.t3rd.sailingtext.lk.lkcustom.LKAndroid;
import android.util.Log;
import android.widget.TextView;

public class OptionPage {
	
	private OptionAitivity optionAitivity;
	
	private TextView blankView0;
	private TextView textViewOptionTitle;
	private TextView blankView1;
	private TextView textViewTutorial;
	private TextView blankView2;
	private TextView textViewcredit;

	
	
	public OptionAitivity getOptionAitivity() {
		return optionAitivity;
	}

	public void setOptionAitivity(OptionAitivity optionAitivity) {
		this.optionAitivity = optionAitivity;
	}

	public TextView getBlankView0() {
		return blankView0;
	}

	public void setBlankView0(TextView blankView0) {
		this.blankView0 = blankView0;
	}

	public TextView getTextViewOptionTitle() {
		return textViewOptionTitle;
	}

	public void setTextViewOptionTitle(TextView textViewOptionTitle) {
		this.textViewOptionTitle = textViewOptionTitle;
	}

	public TextView getBlankView1() {
		return blankView1;
	}

	public void setBlankView1(TextView blankView1) {
		this.blankView1 = blankView1;
	}

	public TextView getTextViewTutorial() {
		return textViewTutorial;
	}

	public void setTextViewTutorial(TextView textViewTutorial) {
		this.textViewTutorial = textViewTutorial;
	}

	public TextView getBlankView2() {
		return blankView2;
	}

	public void setBlankView2(TextView blankView2) {
		this.blankView2 = blankView2;
	}

	public TextView getTextViewcredit() {
		return textViewcredit;
	}

	public void setTextViewcredit(TextView textViewcredit) {
		this.textViewcredit = textViewcredit;
	}

	public OptionPage(OptionAitivity optionAitivity){
		this.optionAitivity	= optionAitivity;
		initialize();
	}

	public void initialize(){
		initializeBlankView();
		initializeTextView();
	}
	
	private void initializeBlankView(){
		setBlankView0((TextView) getOptionAitivity().findViewById(R.id.OptionBlankView0));
		setBlankView1((TextView) getOptionAitivity().findViewById(R.id.OptionBlankView1));
		setBlankView2((TextView) getOptionAitivity().findViewById(R.id.OptionBlankView2));

		LKAndroid.initBlankView(getBlankView0(), getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.BLANK, OptionAitivity.LARGE));
		LKAndroid.initBlankView(getBlankView1(), getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.BLANK, OptionAitivity.LARGE));
		LKAndroid.initBlankView(getBlankView2(), getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.BLANK, OptionAitivity.SMALL));
	}
	
	private void initializeTextView(){
		setTextViewOptionTitle((TextView) getOptionAitivity().findViewById(R.id.OptionTitle));
		setTextViewTutorial((TextView) getOptionAitivity().findViewById(R.id.OptionTutorial));
		setTextViewcredit((TextView) getOptionAitivity().findViewById(R.id.OptionCredits));

		getTextViewTutorial().setTextSize(getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.TEXT, OptionAitivity.SMALL));
		getTextViewcredit().setTextSize(getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.TEXT, OptionAitivity.SMALL));
		getTextViewOptionTitle().setTextSize(getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.TEXT, OptionAitivity.LARGE));

		LKAndroid.initTextViewShadow(getTextViewOptionTitle(), getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.TEXT, OptionAitivity.LARGE));
		LKAndroid.initTextViewShadow(getTextViewcredit(), getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.TEXT, OptionAitivity.SMALL));
		LKAndroid.initTextViewShadow(getTextViewTutorial(), getOptionAitivity().getPreferTextSizeForWindow(OptionAitivity.TEXT, OptionAitivity.SMALL));

		getTextViewcredit().setOnClickListener(getOptionAitivity().getButtonListener());
		getTextViewTutorial().setOnClickListener(getOptionAitivity().getButtonListener());
	}
}
