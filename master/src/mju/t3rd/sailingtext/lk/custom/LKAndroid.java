package mju.t3rd.sailingtext.lk.custom;

import mju.t3rd.sailingtext.R;
import android.widget.TextView;

public class LKAndroid {

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
		textView.setShadowLayer((0.0f+textSize)/5, 0.0f, 0.0f, LKColor.DEFAULT_COLOR);
	}
	/**
	 * 
	 * @param blankView : �ƹ��͵� ǥ������ ���� textView�� �޽��ϴ�.
	 * @param blankSize : textView�� ���̸� ����̽��� ���� �����մϴ�.
	 */
	public static void initBlankView(TextView blankView, int blankSize){
		blankView.setHeight(blankSize);
	}
}