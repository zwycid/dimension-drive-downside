package mju.summer2012.itproject.team3.lk.sailingtext.lkcustom;

import java.util.Random;

import mju.summer2012.itproject.team3.lk.sailingtext.R;
import android.util.Log;
import android.widget.TextView;

public class LKAndroid {
	public static final int DEFAULT_COLOR	= 0xff66ffff;

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
		textView.setShadowLayer((0.0f+textSize)/5, 0.0f, 0.0f, DEFAULT_COLOR);
	}

	public static void initBlankView(TextView blankView, int blankSize){
		blankView.setHeight(blankSize);
	}
	public static int setColorGradiantly(int seedColor){
		int colorR	= (seedColor<<8)>>>24;
		int colorG	= (seedColor<<16)>>>24;
		int colorB	= (seedColor<<24)>>>24;
		
		Log.i("COLOR", "R : " + colorR + " G : " + colorG + " B : " + colorB);
		
		     if((colorR == 0x000000ff) && (colorB == 0x00000000) && (colorG >= 0x00000000) && (colorG < 0x000000ff))
			seedColor	+= 0x00000500;
		else if((colorG == 0x000000ff) && (colorB == 0x00000000) && (colorR <= 0x000000ff) && (colorR > 0x00000000))
			seedColor	-= 0x00050000;
		else if((colorR == 0x00000000) && (colorG == 0x000000ff) && (colorB >= 0x00000000) && (colorB < 0x000000ff))
			seedColor	+= 0x00000005;
		else if((colorR == 0x00000000) && (colorB == 0x000000ff) && (colorG <= 0x000000ff) && (colorG > 0x00000000))
			seedColor	-= 0x00000500;
		else if((colorG == 0x00000000) && (colorB == 0x000000ff) && (colorR >= 0x00000000) && (colorR < 0x000000ff))
			seedColor	+= 0x00050000;
		else if((colorR == 0x000000ff) && (colorG == 0x00000000) && (colorB <= 0x000000ff) && (colorB > 0x00000000))
			seedColor	-= 0x00000005;

		     return seedColor;
	}
}
