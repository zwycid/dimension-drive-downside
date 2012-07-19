package kr.ssidang.android.dimensiondrivedownside;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;

/**
 * 주로 디버그 용도로 화면에 부가 데이터를 그려줍니다.
 * 
 * @author ssidang
 */
public class Vis {
	public static final int OFF = 0;
	public static final int SIMPLE = 1;
	public static final int VERBOSE = 2;
	
	private static final int LEVELS = 3;
	private static int visLevel;
	
	// 디버깅용...........
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
	
	public static void turnOff() {
		visLevel = OFF;
	}
	
	/**
	 * Visualizer가 켜져 있는지 확인합니다.
	 * 
	 * @param level
	 * 		0 = off
	 * 		1 = simple
	 * 		2 = verbose
	 */
	public static boolean isEnabled(int level) {
		return (visLevel <= level);
	}
	
	public static boolean isEnabled() {
		return isEnabled(SIMPLE);
	}
}
