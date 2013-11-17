package mju.t3rd.sailingtext.lk.option;

import mju.t3rd.sailingtext.R;
import mju.t3rd.sailingtext.lk.option.credit.Credit;
import mju.t3rd.sailingtext.lk.option.tutorial.Tutorial;
import mju.t3rd.sailingtext.zeraf29.sound.SoundManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;

public class OptionActivity extends Activity {
	public static final int LARGE	= 10;
	public static final int SMALL	= 15;
	public static final int PLAIN	= 25;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;
	
	private SoundManager sManager;
	
	private ButtonListener buttonListener;
	private OptionPage optionPage;
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sManager.play(0);
		super.onBackPressed();
	}

	public ButtonListener getButtonListener() {
		return buttonListener;
	}

	public void setButtonListener(ButtonListener buttonListener) {
		this.buttonListener = buttonListener;
	}

	public OptionPage getOptionPage() {
		return optionPage;
	}

	public void setOptionPage(OptionPage optionPage) {
		this.optionPage = optionPage;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//TODO sound by jh
		sManager = SoundManager.getInstance();
		sManager.init(this);
		sManager.addSound(0,R.raw.click);
		
		setContentView(R.layout.activity_option);
		setButtonListener(new ButtonListener());
		setOptionPage(new OptionPage(this));
	}

	private class ButtonListener implements OnClickListener{

		public void onClick(View clickedView) {
			Intent intent;
			sManager.play(0);
			// TODO Auto-generated method stub
			if(clickedView.getId() == R.id.OptionCredits){
				intent	= new Intent(OptionActivity.this, Credit.class);
				startActivityForResult(intent, 1);
			}
			else if(clickedView.getId() == R.id.OptionTutorial){
				intent	= new Intent(OptionActivity.this, Tutorial.class);
				startActivityForResult(intent, 1);
			}
		}
		
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
}