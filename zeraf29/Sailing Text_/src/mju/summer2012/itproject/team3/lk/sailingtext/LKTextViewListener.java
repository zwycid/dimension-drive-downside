package mju.summer2012.itproject.team3.lk.sailingtext;

import mju.summer2012.itproject.team3.lk.sailing.text.R;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class LKTextViewListener implements OnClickListener {
	private MainActivity mainActivity;
	private ViewFlipper viewFlipper;

	public MainActivity getMainActivity() {		return mainActivity;	}
	public void setMainActivity(MainActivity mainActivity) {		this.mainActivity = mainActivity;	}
	public ViewFlipper getViewFlipper() {		return viewFlipper;	}
	public void setViewFlipper(ViewFlipper viewFlipper) {		this.viewFlipper = viewFlipper;	}

	public void onClick(View clickedView) {
		// TODO Auto-generated method stub
		if(clickedView.getId() == R.id.MainmenutextViewStartgame){
			getViewFlipper().showNext();
			getMainActivity().getStartGamePage().initStartGamePage();
		}else if(clickedView.getId() == R.id.MainmenutextViewRanking){
			//xml에 등록된 view라면 바로 그냥 shownext해도 됨.
			getViewFlipper().showNext();
			getViewFlipper().showNext();
			getMainActivity().getRankingPage().initRankingPage();
		}else if(clickedView.getId() == R.id.MainmenutextViewOption){
			//두 번 연속으로 호출하면 그냥 맨 마지막 view가 보임.
			getViewFlipper().showNext();
			getViewFlipper().showNext();
			getViewFlipper().showNext();
		}else if(clickedView.getId() == R.id.textViewButtonSwap){
			TextView textViewSwap	= (TextView) getMainActivity().findViewById(R.id.textViewButtonSwap);
			Spinner spinnerRanking	= (Spinner) getMainActivity().findViewById(R.id.spinnerStage);
			if(getViewFlipper().getCurrentView().getId() == R.id.listViewTotalRanking){
				spinnerRanking.setVisibility(Spinner.VISIBLE);
				textViewSwap.setText("Stage Ranking");
				getViewFlipper().showNext();
			}else{
				spinnerRanking.setVisibility(Spinner.INVISIBLE);
				textViewSwap.setText("Total Ranking");
				getViewFlipper().showPrevious();
			}
		}else if(clickedView.getId() == R.id.textViewButtonReset){
			Toast.makeText(getMainActivity(), "RESET", Toast.LENGTH_SHORT).show();
		}
	}

	public LKTextViewListener(MainActivity mainActivity){
		this.mainActivity	= mainActivity;
		viewFlipper			= (ViewFlipper) mainActivity.findViewById(R.id.viewFlipper);
	}
}
