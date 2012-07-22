package mju.t3rd.sailingtext.lk.option;

import mju.t3rd.sailingtext.lk.R;
import mju.t3rd.sailingtext.lk.option.credit.Credit;
import mju.t3rd.sailingtext.lk.option.tutorial.Tutorial;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class OptionAitivity extends Activity {
	public static final int LARGE	= 10;
	public static final int SMALL	= 15;
	public static final int PLAIN	= 25;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;
	
	private ButtonListener buttonListener;
	private OptionPage optionPage;
	
	
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
		setContentView(R.layout.activity_option);
		setButtonListener(new ButtonListener());
		setOptionPage(new OptionPage(this));
	}

	private class ButtonListener implements OnClickListener{

		public void onClick(View clickedView) {
			Intent intent;
			// TODO Auto-generated method stub
			if(clickedView.getId() == R.id.OptionCredits){
				intent	= new Intent(OptionAitivity.this, Credit.class);
				startActivityForResult(intent, 1);
			}
			else if(clickedView.getId() == R.id.OptionTutorial){
				intent	= new Intent(OptionAitivity.this, Tutorial.class);
				startActivityForResult(intent, 1);
			}
		}
		
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
}
