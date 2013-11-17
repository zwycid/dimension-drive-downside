package mju.t3rd.sailingtext.lk.option;

import mju.t3rd.sailingtext.R;
import mju.t3rd.sailingtext.lk.custom.LKAndroid;
import android.widget.TextView;

public class OptionPage {
	
	private OptionActivity optionActivity;
	
	private TextView blankView0;
	private TextView textViewOptionTitle;
	private TextView blankView1;
	private TextView textViewTutorial;
	private TextView blankView2;
	private TextView textViewcredit;

	
	
	public OptionActivity getOptionActivity() {
		return optionActivity;
	}

	public void setOptionAitivity(OptionActivity optionActivity) {
		this.optionActivity = optionActivity;
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

	public OptionPage(OptionActivity optionActivity){
		this.optionActivity	= optionActivity;
		initialize();
	}

	public void initialize(){
		initializeBlankView();
		initializeTextView();
	}
	
	private void initializeBlankView(){
		setBlankView0((TextView) getOptionActivity().findViewById(R.id.OptionBlankView0));
		setBlankView1((TextView) getOptionActivity().findViewById(R.id.OptionBlankView1));
		setBlankView2((TextView) getOptionActivity().findViewById(R.id.OptionBlankView2));

		LKAndroid.initBlankView(getBlankView0(), getOptionActivity().getPreferTextSizeForWindow(OptionActivity.BLANK, OptionActivity.LARGE));
		LKAndroid.initBlankView(getBlankView1(), getOptionActivity().getPreferTextSizeForWindow(OptionActivity.BLANK, OptionActivity.LARGE));
		LKAndroid.initBlankView(getBlankView2(), getOptionActivity().getPreferTextSizeForWindow(OptionActivity.BLANK, OptionActivity.SMALL));
	}
	
	private void initializeTextView(){
		setTextViewOptionTitle((TextView) getOptionActivity().findViewById(R.id.OptionTitle));
		setTextViewTutorial((TextView) getOptionActivity().findViewById(R.id.OptionTutorial));
		setTextViewcredit((TextView) getOptionActivity().findViewById(R.id.OptionCredits));

		getTextViewTutorial().setTextSize(getOptionActivity().getPreferTextSizeForWindow(OptionActivity.TEXT, OptionActivity.SMALL));
		getTextViewcredit().setTextSize(getOptionActivity().getPreferTextSizeForWindow(OptionActivity.TEXT, OptionActivity.SMALL));
		getTextViewOptionTitle().setTextSize(getOptionActivity().getPreferTextSizeForWindow(OptionActivity.TEXT, OptionActivity.LARGE));

		LKAndroid.initTextViewShadow(getTextViewOptionTitle(), getOptionActivity().getPreferTextSizeForWindow(OptionActivity.TEXT, OptionActivity.LARGE));
		LKAndroid.initTextViewShadow(getTextViewcredit(), getOptionActivity().getPreferTextSizeForWindow(OptionActivity.TEXT, OptionActivity.SMALL));
		LKAndroid.initTextViewShadow(getTextViewTutorial(), getOptionActivity().getPreferTextSizeForWindow(OptionActivity.TEXT, OptionActivity.SMALL));

		getTextViewcredit().setOnClickListener(getOptionActivity().getButtonListener());
		getTextViewTutorial().setOnClickListener(getOptionActivity().getButtonListener());
	}
}
