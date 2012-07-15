package mju.summer2012.itproject.team3.lk.sailingtext.ranking;

import mju.summer2012.itproject.team3.lk.sailing.text.R;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class RankingRelativeLayout extends RelativeLayout {
	private TextView RankingBlankView0;
	private TextView RankingBlankView1;
	private TextView textViewTitleRanking;
	private Spinner spinnerStage;
	private TextView textViewButtonSwap;
	private TextView textViewButtonReset;
	private ViewFlipper viewFlipperForListView;
	private ListView listViewTotalRanking;
	private ListView listViewStageRanking;
	private TextView RankingBlankView2;
	private TextView RankingBlankView3;

	public TextView getTextViewTitleRanking() {		return textViewTitleRanking;	}
	public void setTextViewTitleRanking(TextView textViewTitleRanking) {		this.textViewTitleRanking = textViewTitleRanking;	}
	public TextView getTextViewButtonSwap() {		return textViewButtonSwap;	}	
	public void setTextViewButtonSwap(TextView textViewButtonSwap) {		this.textViewButtonSwap = textViewButtonSwap;	}
	public TextView getTextViewButtonReset() {		return textViewButtonReset;	}
	public void setTextViewButtonReset(TextView textViewButtonReset) {		this.textViewButtonReset = textViewButtonReset;	}
	public Spinner getSpinnerStage() {		return spinnerStage;	}
	public void setSpinnerStage(Spinner spinnerStage) {		this.spinnerStage = spinnerStage;	}
	public ViewFlipper getViewFlipperForListView() {		return viewFlipperForListView;	}
	public void setViewFlipperForListView(ViewFlipper viewFlipperForListView) {		this.viewFlipperForListView = viewFlipperForListView;	}
	public ListView getListViewTotalRanking() {		return listViewTotalRanking;	}
	public void setListViewTotalRanking(ListView listViewTotalRanking) {		this.listViewTotalRanking = listViewTotalRanking;	}
	public ListView getListViewStageRanking() {		return listViewStageRanking;	}
	public void setListViewStageRanking(ListView listViewStageRanking) {		this.listViewStageRanking = listViewStageRanking;	}
	public TextView getRankingBlankView0() {		return RankingBlankView0;	}
	public void setRankingBlankView0(TextView rankingBlankView0) {		RankingBlankView0 = rankingBlankView0;	}
	public TextView getRankingBlankView1() {		return RankingBlankView1;	}
	public void setRankingBlankView1(TextView rankingBlankView1) {		RankingBlankView1 = rankingBlankView1;	}
	public TextView getRankingBlankView2() {		return RankingBlankView2;	}
	public void setRankingBlankView2(TextView rankingBlankView2) {		RankingBlankView2 = rankingBlankView2;	}
	public TextView getRankingBlankView3() {		return RankingBlankView3;	}
	public void setRankingBlankView3(TextView rankingBlankView3) {		RankingBlankView3 = rankingBlankView3;	}

	public RankingRelativeLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public RankingRelativeLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public RankingRelativeLayout(Context context) {
		super(context);
	}
	
	public void initRankingLayout(){

		setTextViewButtonReset((TextView) this.findViewById(R.id.textViewButtonReset));
		setTextViewButtonSwap((TextView) this.findViewById(R.id.textViewButtonSwap));
		setTextViewTitleRanking((TextView) this.findViewById(R.id.RankingtextViewTitle));
		setRankingBlankView0((TextView) this.findViewById(R.id.RankingBlankView0));
		setRankingBlankView1((TextView) this.findViewById(R.id.RankingBlankView1));
		setRankingBlankView2((TextView) this.findViewById(R.id.RankingBlankView2));
		setRankingBlankView3((TextView) this.findViewById(R.id.RankingBlankView3));		
		setSpinnerStage((Spinner) this.findViewById(R.id.spinnerStage));
		setViewFlipperForListView((ViewFlipper) this.findViewById(R.id.viewFlipperForListView));
		setListViewTotalRanking((ListView) this.findViewById(R.id.listViewTotalRanking));
		setListViewStageRanking((ListView) this.findViewById(R.id.listViewStageRanking));
	}

}