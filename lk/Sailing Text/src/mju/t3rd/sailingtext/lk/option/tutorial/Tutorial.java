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
