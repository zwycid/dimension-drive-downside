package mju.summer2012.itproject.team3.lk.sailingtext;

import mju.summer2012.itproject.team3.lk.sailingtext.mainmenu.MainPage;
import mju.summer2012.itproject.team3.lk.sailingtext.startgame.CoverFlowExample;
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

		setContentView(R.layout.activity_main);
		setTextViewListener(new MainTextViewListener());
		this.setMainMenuPage(new MainPage(this));
		this.getMainMenuPage().initiateMainmenuPage();
	}

	@Override
	public void onBackPressed() {
		DialogBox();
	}

	/**
	 * ����Ʈ�� ȭ�� ũ�⿡ ����Ͽ� ������ �ؽ�Ʈ ũ�⸦ ��ȯ�մϴ�.
	 * @param textType : TEXT	- textView�� drawText�� �� ����մϴ�. �Լ��� ���̴� ���ڸ� �ִ� ���� �ν��մϴ�.
	 * 					 BLANK	- �Լ��� textView�ε� Blank�� ����� ��, �� ��ĭ�� �ִ� ���� �ν��մϴ�. 
	 * @param textSize : LARGE	- ū �۾�(text button) / ū ��ĭ�� �ʿ��� �� ����մϴ�.
	 * 					 SMALL	- ���� �۾�(text button) / ���� ��ĭ�� �ʿ��� �� ����մϴ�.
	 * 					 PLAIN	- �Ϲ����� �۾��� �ʿ��� �� ����մϴ�.
	 * @see ������ �Ű������� �ƴϸ� 0�� ��ȯ�ϴ� �����Ͻñ� �ٶ��ϴ�.
	 * @return ������ ũ�⿡ ������ �ؽ�Ʈ�� ũ�⸦ ��ȯ�մϴ�.
	 */
	public int getPreferTextSizeForWindow(int textType, int textSize) {
		if(textType == BLANK){
			if(textSize == LARGE){
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / LARGE)*1.5);
			}else if(textSize == SMALL){	
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / SMALL)*1.5);
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
								MainActivity.this.finish();
								dialog.cancel();
							}
						}).setNegativeButton(
								"No" , new AlertDialog.OnClickListener() {

									public void onClick(DialogInterface dialog, int which) {
										dialog.cancel();
									}
								});

		AlertDialog alertDialog	= alertDialogBuilder.create();
		alertDialog.setTitle("Quit App");
		alertDialog.setIcon(R.drawable.ic_launcher);
		alertDialog.show();

	}

	private class MainTextViewListener implements View.OnClickListener {

		public void onClick(View clickedView) {
			if(clickedView.getId() == R.id.MainTextViewStartgame){
				Intent intent	= new Intent(MainActivity.this, CoverFlowExample.class);
				startActivityForResult(intent, 1);
			}
		}
	}

}
