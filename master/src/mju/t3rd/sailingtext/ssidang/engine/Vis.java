package mju.t3rd.sailingtext.ssidang.engine;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Style;

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
	private static boolean frame;
	
	// 디버깅용...........
	public static final Paint white;
	public static final Paint blue;
	public static final Paint orange;
	public static final Paint red;
	public static final Paint green;
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
		orange.setStyle(Style.STROKE);
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
	 * Visualizer가 켜져 있는지 확인합니다.
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
