package mju.summer2012.itproject.team3.lk.sailingtext.lkcustom;

import java.util.Random;

import mju.summer2012.itproject.team3.lk.sailingtext.R;
import android.util.Log;
import android.widget.TextView;

public class LKAndroid {
	public static final int DEFAULT_COLOR	= 0xff66ffff;

	/**
	 * Ŭ���� �̸��� Ŭ���� ���� ���ǵ� ���� �̸����� id���� ���մϴ�.
	 * @param className : R �ȿ� �ִ� Ŭ���� �̸��Դϴ�.
	 * @param fieldName : className �ȿ� ���ǵ� ���� �̸��Դϴ�.
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
	 * Ŭ���� �̸��� Ŭ���� ���� ���ǵ� ���� �̸����� id���� ���մϴ�.
	 * @param className : R �ȿ� �ִ� Ŭ���� �̸��Դϴ�.
	 * @param fieldName : className �ȿ� ���ǵ� ���� �̸��Դϴ�.
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
	/**
	 * 
	 * @param blankView : �ƹ��͵� ǥ������ ���� textView�� �޽��ϴ�.
	 * @param blankSize : textView�� ���̸� ����̽��� ���� �����մϴ�.
	 */
	public static void initBlankView(TextView blankView, int blankSize){
		blankView.setHeight(blankSize);
	}
	/**
	 * 
	 * @param seedColor(int) : ���� ������ �޽��ϴ�.
	 * @return (int) ���� �°� ������ ����� �����մϴ�.
	 */
	public static int setColorGradiantly(int seedColor){
		int colorR	= (seedColor<<8)>>>24;
		int colorG	= (seedColor<<16)>>>24;
		int colorB	= (seedColor<<24)>>>24;
		
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
	/**
	 * ���� ������ �� �߽��ϴ�.
	 * @param seedColor
	 * @return
	 */
	public static int setColorMultipleFive(int seedColor){
		switch (seedColor % 5) {
		case 0: seedColor	+= 0xff00ff00;break;
		case 1: seedColor	+= 0xff00ff04;break;
		case 2: seedColor	+= 0xff00ff03;break;
		case 3: seedColor	+= 0xff00ff02;break;
		case 4: seedColor	+= 0xff00ff01;break;
		}
		return seedColor;
	}
}
