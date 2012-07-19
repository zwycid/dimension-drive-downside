package mju.summer2012.itproject.team3.lk.sailingtext;

import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class GlowingTextView extends TextView {

	public GlowingTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public GlowingTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public GlowingTextView(Context context) {
		super(context);
	}
	public void initialize(String string, int size){
		this.setText(string);
		this.setTextSize(size);
		this.setShadowLayer(0.0f + getTextSize() / 3, 0, 0, LKAndroid.DEFAULT_COLOR);
		this.setVisibility(GlowingTextView.INVISIBLE);
	}

}
