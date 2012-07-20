package mju.t3rd.sailingtext.lk.lkcustom;

import java.util.Random;

import android.util.Log;

public class LKColor {
	public static final int DEFAULT_COLOR	= 0xff66ffff;
	private static int seed	= 0;
	
	
	public static int getSeed() {
		return seed;
	}


	public static void setSeed(int seed) {
		LKColor.seed = seed;
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
	 * �õ� ���� �����մϴ�. ���� ��κ��� ���� ���� �־��ٰ� ������ ����Ƿ� 6���� �� �� �ϳ��� ��ȯ�մϴ�..
	 * @param seedColor
	 * @return
	 */
	public static int getSeedColor(){
		Random random	= new Random();
		setSeed(Math.abs(random.nextInt()));
		int seedColor	= 0;
		switch (getSeed() % 6) {
		case 0: seedColor	= 0xff00ff00;break;
		case 1: seedColor	= 0xff00ffff;break;
		case 2: seedColor	= 0xff0000ff;break;
		case 3: seedColor	= 0xffff00ff;break;
		case 4: seedColor	= 0xffff0000;break;
		case 5: seedColor	= 0xffffff00;break;
		}
		return seedColor;
	}
}
