package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;

/**
 * �ַ� ����� �뵵�� ȭ�鿡 �ΰ� �����͸� �׷��ݴϴ�.
 * 
 * @author ssidang
 */
public class Vis {
	public static final int OFF = 0;
	public static final int SIMPLE = 1;
	public static final int VERBOSE = 2;
	
	private static final int LEVELS = 3;
	private static int visLevel;
	private static boolean frame;
	
	// ������...........
	static final Paint white;
	static final Paint blue;
	static final Paint orange;
	static final Paint red;
	static final Paint green;
	static {
		white = new Paint(Paint.ANTI_ALIAS_FLAG);
		white.setColor(Color.WHITE);
		white.setTextSize(8);
		
		blue = new Paint();
		blue.setColor(Color.BLUE);
		blue.setStrokeWidth(7);
		blue.setStrokeCap(Cap.ROUND);
		
		orange = new Paint();
		orange.setColor(0xffff8030);
		orange.setStrokeWidth(5);
		orange.setStrokeCap(Cap.ROUND);
		
		red = new Paint(Paint.ANTI_ALIAS_FLAG);
		red.setColor(Color.RED);
		red.setStrokeWidth(3);
		red.setStrokeCap(Cap.ROUND);
		
		green = new Paint(Paint.ANTI_ALIAS_FLAG);
		green.setColor(0x4030f030);
		green.setStrokeWidth(3);
		green.setStrokeCap(Cap.ROUND);
	}
	
	public static void escalateLevel() {
		visLevel = (visLevel + 1) % LEVELS;
	}
	
	public static void toggleFrameMode() {
		frame = !frame;
	}
	
	public static void setLevel(int level) {
		visLevel = level;
	}
	
	public static void setFrameMode(boolean mode) {
		frame = mode;
	}
	
	/**
	 * Visualizer�� ���� �ִ��� Ȯ���մϴ�.
	 * 
	 * @param level
	 */
	public static boolean isEnabled(int level) {
		return (visLevel >= level);
	}
	
	public static boolean isEnabled() {
		return isEnabled(SIMPLE);
	}
	
	public static boolean isFrameMode() {
		return frame;
	}
}
