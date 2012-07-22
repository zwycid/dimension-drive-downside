package mju.t3rd.sailingtext.lk;

import mju.t3rd.sailingtext.R;
import mju.t3rd.sailingtext.lk.mainmenu.MainPage;
import mju.t3rd.sailingtext.lk.option.OptionActivity;
import mju.t3rd.sailingtext.lk.startgame.CoverFlowExample;
import mju.t3rd.sailingtext.zeraf29.sound.SoundManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ViewFlipper;

public class MainActivity extends Activity {

	public static final int LARGE	= 11;
	public static final int SMALL	= 15;
	public static final int PLAIN	= 25;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;

	private MainTextViewListener textViewListener;
	private MainPage mainMenuPage;
	private ViewFlipper viewFlipper;
	
	//TODO sound by jh
	SoundManager sManager;

	public MainTextViewListener getTextViewListener() {		return textViewListener;	}
	public void setTextViewListener(MainTextViewListener textViewListener) {		this.textViewListener = textViewListener;	}
	public MainPage getMainMenuPage() {		return mainMenuPage;	}
	public void setMainMenuPage(MainPage mainMenuPage) {		this.mainMenuPage = mainMenuPage;	}
	public ViewFlipper getViewFlipper() {		return viewFlipper;	}
	public void setViewFlipper(ViewFlipper viewFlipper) {		this.viewFlipper = viewFlipper;	}


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//TODO sound by jh
		sManager = SoundManager.getInstance();
		sManager.init(this);
		sManager.addSound(0,R.raw.click);

		setContentView(R.layout.activity_main);
		setTextViewListener(new MainTextViewListener());
		this.setMainMenuPage(new MainPage(this));
	}

	@Override
	public void onBackPressed() {
		sManager.play(0);
		DialogBox();
	}

	/**
	 * 스마트폰 화면 크기에 비례하여 적합한 텍스트 크기를 반환합니다.
	 * @param textType : TEXT	- textView나 drawText할 때 사용합니다. 함수는 보이는 글자를 넣는 경우로 인식합니다.
	 * 					 BLANK	- 함수는 textView인데 Blank로 사용할 때, 즉 빈칸을 넣는 경우로 인식합니다. 
	 * @param textSize : LARGE	- 큰 글씨(text button) / 큰 빈칸이 필요할 때 사용합니다.
	 * 					 SMALL	- 작은 글씨(text button) / 작은 빈칸이 필요할 때 사용합니다.
	 * 					 PLAIN	- 일반적인 글씨가 필요할 떄 사용합니다.
	 * @see 적절한 매개변수가 아니면 0을 반환하니 주의하시기 바랍니다.
	 * @return 윈도우 크기에 적합한 텍스트의 크기를 반환합니다.
	 */
	public int getPreferTextSizeForWindow(int textType, int textSize) {
		if(textType == BLANK){
			if(textSize == LARGE){
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / LARGE)*0.7);
			}else if(textSize == SMALL){	
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / SMALL));
			}else if(textSize == SMALL){	
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / PLAIN)*1.2);
			}
		}else if(textType == TEXT){
			if(textSize == LARGE){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / LARGE;
			}else if(textSize == SMALL){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / SMALL;
			}else if(textSize == PLAIN){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / PLAIN;
			}
		}
		return 0;
	}

	private void DialogBox(){
		AlertDialog.Builder alertDialogBuilder	= new AlertDialog.Builder(this);

		alertDialogBuilder.setMessage(
				"Do you want to close this Application?").setCancelable(false).setPositiveButton(
						"yes", new AlertDialog.OnClickListener() {

							public void onClick(DialogInterface dialog, int which) {
								sManager.play(0);
								MainActivity.this.finish();
								dialog.cancel();
							}
						}).setNegativeButton(
								"No" , new AlertDialog.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										sManager.play(0);
										dialog.cancel();
									}
								});

		AlertDialog alertDialog	= alertDialogBuilder.create();
		alertDialog.setTitle("Quit App");
		alertDialog.setIcon(R.drawable.sailing_text);
		alertDialog.show();

	}

	private class MainTextViewListener implements View.OnClickListener {
		Intent intent;
		public void onClick(View clickedView) {
			sManager.play(0);
			if(clickedView.getId() == R.id.MainTextViewStartgame){
				intent	= new Intent(MainActivity.this, CoverFlowExample.class);
				startActivityForResult(intent, 1);
			}else if(clickedView.getId() == R.id.MainTextViewOption){
				intent	= new Intent(MainActivity.this, OptionActivity.class);
				startActivityForResult(intent, 1);
			}
		}
	}

}
