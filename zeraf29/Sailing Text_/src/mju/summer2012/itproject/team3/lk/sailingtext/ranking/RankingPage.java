package mju.summer2012.itproject.team3.lk.sailingtext.ranking;

import mju.summer2012.itproject.team3.lk.sailing.text.R;
import mju.summer2012.itproject.team3.lk.sailingtext.MainActivity;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class RankingPage {
	private MainActivity mainActivity;
	
	private TextView textViewTitleRanking;
	private TextView textViewButtonSwap;
	private TextView textViewButtonReset;
	private TextView textViewRankingBlankTitle;
	private TextView textViewRankingBlank;
	private Spinner spinnerRanking;
	
	public MainActivity getMainActivity() {		return mainActivity;	}
	public void setMainActivity(MainActivity mainActivity) {		this.mainActivity = mainActivity;	}
	public TextView getTextViewTitleRanking() {		return textViewTitleRanking;	}
	public void setTextViewTitleRanking(TextView textViewTitleRanking) {		this.textViewTitleRanking = textViewTitleRanking;	}
	public TextView getTextViewButtonSwap() {		return textViewButtonSwap;	}
	public void setTextViewButtonSwap(TextView textViewButtonSwap) {		this.textViewButtonSwap = textViewButtonSwap;	}
	public TextView getTextViewButtonReset() {		return textViewButtonReset;	}
	public void setTextViewButtonReset(TextView textViewButtonReset) {		this.textViewButtonReset = textViewButtonReset;	}
	public TextView getTextViewRankingBlankTitle() {		return textViewRankingBlankTitle;	}
	public void setTextViewRankingBlankTitle(TextView textViewRankingBlankTitle) {		this.textViewRankingBlankTitle = textViewRankingBlankTitle;	}
	public TextView getTextViewRankingBlank() {		return textViewRankingBlank;	}
	public void setTextViewRankingBlank(TextView textViewRankingBlank) {		this.textViewRankingBlank = textViewRankingBlank;	}
	public Spinner getSpinnerRanking() {		return spinnerRanking;	}
	public void setSpinnerRanking(Spinner spinnerRanking) {		this.spinnerRanking = spinnerRanking;	}

	public RankingPage(MainActivity mainActivity){
		setMainActivity(mainActivity);
	}

	public void initRankingPage(){
		textViewTitleRanking		= (TextView) getMainActivity().findViewById(R.id.RankingtextViewTitle);
		textViewButtonSwap			= (TextView) getMainActivity().findViewById(R.id.textViewButtonSwap);
		textViewButtonReset		= (TextView) getMainActivity().findViewById(R.id.textViewButtonReset);
		textViewRankingBlankTitle	= (TextView) getMainActivity().findViewById(R.id.RankingBlankView0);
		textViewRankingBlank		= (TextView) getMainActivity().findViewById(R.id.RankingBlankView1);
		spinnerRanking				= (Spinner) getMainActivity().findViewById(R.id.spinnerStage);

		LKAndroid.initBlankView(textViewRankingBlankTitle, getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.LARGE));
		LKAndroid.initBlankView(textViewRankingBlank, getMainActivity().getPreferTextSizeForWindow(MainActivity.BLANK, MainActivity.SMALL));

		getMainActivity().getTextViewListener().setViewFlipper((ViewFlipper) getMainActivity().findViewById(R.id.viewFlipperForListView));

		textViewTitleRanking.setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));
		LKAndroid.initTextViewShadow(textViewTitleRanking, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.LARGE));

		textViewButtonSwap.setText("Total Ranking");
		textViewButtonSwap.setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.PLAIN));
		LKAndroid.initTextViewShadow(textViewButtonSwap, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.PLAIN));

		textViewButtonReset.setTextSize(getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.PLAIN));
		LKAndroid.initTextViewShadow(textViewButtonReset, getMainActivity().getPreferTextSizeForWindow(MainActivity.TEXT, MainActivity.PLAIN));

		textViewButtonSwap.setOnClickListener(getMainActivity().getTextViewListener());
		textViewButtonReset.setOnClickListener(getMainActivity().getTextViewListener());

		spinnerRanking.setVisibility(Spinner.INVISIBLE);
	}
}
