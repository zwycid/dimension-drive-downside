package mju.t3rd.sailingtext.lk.lkcustom;

import java.util.Random;

import mju.t3rd.sailingtext.lk.R;
import android.util.Log;
import android.widget.TextView;

public class LKAndroid {

	/**
	 * 클래스 이름과 클래스 내에 정의된 변수 이름으로 id값을 구합니다.
	 * @param className : R 안에 있는 클래스 이름입니다.
	 * @param fieldName : className 안에 정의된 변수 이름입니다.
	 * @return
	 */
	public static int getID(String className, String fieldName){
		int id	= 0x00000000;
		
		for(id = 0 ; (id < R.class.getClasses().length) && (className != R.class.getClasses()[id].getSimpleName()) ; id++);
		
		try {
			id	= R.class.getClasses()[id].getField(fieldName).getInt(fieldName);
		} catch (IllegalArgumentException e) {			e.printStackTrace();
		} catch (SecurityException e) {			e.printStackTrace();
		} catch (IllegalAccessException e) {			e.printStackTrace();
		} catch (NoSuchFieldException e) {			e.printStackTrace();
		}
		return id;
	}

	/**
	 * 클래스 이름과 클래스 내에 정의된 변수 이름으로 id값을 구합니다.
	 * @param className : R 안에 있는 클래스 이름입니다.
	 * @param fieldName : className 안에 정의된 변수 이름입니다.
	 * @return
	 */
	public static int getView(String className, String fieldName){
		int id	= 0x00000000;
		
		for(id = 0 ; (id < R.class.getClasses().length) && (className != R.class.getClasses()[id].getSimpleName()) ; id++);
		
		try {
			id	= R.class.getClasses()[id].getField(fieldName).getInt(fieldName);
		} catch (IllegalArgumentException e) {			e.printStackTrace();
		} catch (SecurityException e) {			e.printStackTrace();
		} catch (IllegalAccessException e) {			e.printStackTrace();
		} catch (NoSuchFieldException e) {			e.printStackTrace();
		}
		return id;
	}
	public static void initTextViewShadow(TextView textView, int textSize){
		textView.setShadowLayer((0.0f+textSize)/5, 0.0f, 0.0f, LKColor.DEFAULT_COLOR);
	}
	/**
	 * 
	 * @param blankView : 아무것도 표시하지 않을 textView를 받습니다.
	 * @param blankSize : textView의 높이를 디바이스에 맞춰 리턴합니다.
	 */
	public static void initBlankView(TextView blankView, int blankSize){
		blankView.setHeight(blankSize);
	}
}
