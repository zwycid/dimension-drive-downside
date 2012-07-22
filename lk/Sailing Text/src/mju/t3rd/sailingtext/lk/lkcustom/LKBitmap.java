package mju.t3rd.sailingtext.lk.lkcustom;

import android.graphics.Bitmap;
import android.graphics.Color;

public class LKBitmap {

	public static Bitmap transparentBitmap(Bitmap bitmap, int width, int height){
		int[] pixels	= new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		for(int i = 0 ; i != pixels.length ; i++){
			if(pixels[i] == Color.BLACK)
				pixels[i]	= Color.TRANSPARENT;
		}

		return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
	}
}
