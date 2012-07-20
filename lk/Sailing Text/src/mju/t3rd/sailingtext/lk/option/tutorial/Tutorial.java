package mju.t3rd.sailingtext.lk.option.tutorial;

import mju.t3rd.sailingtext.lk.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import mju.t3rd.sailingtext.lk.lkcustom.LKAndroid;
import mju.t3rd.sailingtext.lk.option.tutorial.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class Tutorial extends Activity {
	public static final int LARGE	= 11;
	public static final int SMALL	= 15;
	public static final int PLAIN	= 25;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;
	
	private TextView title;
	private TextView blank;
	private ListView listView;
	private ListAdapter listAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tutorial);
		init();
	}
	
	public void init(){
		blank	= (TextView) this.findViewById(R.id.OptionTutorialTextviewBlank);
		title	= (TextView) this.findViewById(R.id.OptionTutorialTextviewTitle);
		listView	= (ListView) this.findViewById(R.id.OptionTutorialListView);
		listAdapter	= new ListAdapter(this);
		
		LKAndroid.initBlankView(blank, getPreferTextSizeForWindow(TEXT, LARGE));
		
		title.setTextSize(getPreferTextSizeForWindow(TEXT, LARGE));
		LKAndroid.initTextViewShadow(title, (int) title.getTextSize());
		
		listView.setAdapter(listAdapter);
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
