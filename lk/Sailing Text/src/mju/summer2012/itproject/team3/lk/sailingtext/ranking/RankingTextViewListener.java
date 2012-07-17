package mju.summer2012.itproject.team3.lk.sailingtext.ranking;

import mju.summer2012.itproject.team3.lk.sailingtext.R;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class RankingTextViewListener implements OnClickListener {
	private RankingRelativeLayout rankingRelativeLayout;
	private TextView textViewSwap;
	private Spinner spinnerRanking;
	private ViewFlipper viewFlipper;

	public ViewFlipper getViewFlipper() {
		return viewFlipper;
	}
	public void setViewFlipper(ViewFlipper viewFlipper) {
		this.viewFlipper = viewFlipper;
	}
	public RankingRelativeLayout getRankingRelativeLayout() {
		return rankingRelativeLayout;
	}
	public void setRankingRelativeLayout(RankingRelativeLayout rankingRelativeLayout) {
		this.rankingRelativeLayout = rankingRelativeLayout;
	}
	public TextView getTextViewSwap() {
		return textViewSwap;
	}
	public void setTextViewSwap(TextView textViewSwap) {
		this.textViewSwap = textViewSwap;
	}
	public Spinner getSpinnerRanking() {
		return spinnerRanking;
	}
	public void setSpinnerRanking(Spinner spinnerRanking) {
		this.spinnerRanking = spinnerRanking;
	}
	
	public void onClick(View clickedView) {
		// TODO Auto-generated method stub
		Log.i("wtf", "RankingTextViewListener getviewflipper : " + getViewFlipper());
		if(clickedView.getId() == R.id.textViewButtonSwap){
			if(getViewFlipper().getCurrentView().getId() == R.id.listViewTotalRanking){
				spinnerRanking.setVisibility(Spinner.VISIBLE);
				textViewSwap.setText("Stage Ranking");
				getViewFlipper().showNext();
			}else if(getViewFlipper().getCurrentView().getId() == R.id.listViewStageRanking){
				spinnerRanking.setVisibility(Spinner.INVISIBLE);
				textViewSwap.setText("Total Ranking");
				getViewFlipper().showPrevious();
			}
		}else if(clickedView.getId() == R.id.textViewButtonReset){
			Toast.makeText(getRankingRelativeLayout().getContext(), "RESET", Toast.LENGTH_SHORT).show();

		}
	}
	public RankingTextViewListener(RankingRelativeLayout rankingRelativeLayout){
		this.setRankingRelativeLayout(rankingRelativeLayout);
		this.setTextViewSwap(getRankingRelativeLayout().getTextViewButtonSwap());
		this.setSpinnerRanking(getRankingRelativeLayout().getSpinnerStage());
		this.setViewFlipper(getRankingRelativeLayout().getViewFlipperForListView());
	}

}
