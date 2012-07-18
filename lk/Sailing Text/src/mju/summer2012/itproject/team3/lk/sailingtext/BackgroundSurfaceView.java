package mju.summer2012.itproject.team3.lk.sailingtext;

import java.util.Random;

import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BackgroundSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private static final int THE_NUMBER_OF_FALLING_TEXT	= 10;
	private static final int FALLING_TEXT_MAX_SPEED	= 25;
	private static final int FALLING_TEXT_MIN_SPEED	= 5;

	private Thread thread;
	private Bitmap[] textImageArray;/*공 여러개를 운영하기 위해서 배열로 만듭니다.*/
	private int[] textWidthArray;
	private int[] textHeightArray;

	public Thread getThread() {		return thread;	}
	public void setThread(Thread thread) {		this.thread = thread;	}
	public Bitmap[] getTextImageArray() {		return textImageArray;	}
	public void setTextImageArray(Bitmap[] textImages) {		this.textImageArray = textImages;	}
	public void setTextImage(int index, Bitmap textImages) {		this.textImageArray[index] = textImages;	}
	public int[] getTextWidthArray() {		return textWidthArray;	}
	public void setTextWidthArray(int[] textWidth) {		this.textWidthArray = textWidth;	}
	public void setATextWidthOfArray(int index, int textWidth) {		this.textWidthArray[index] = textWidth;	}
	public int[] getTextHeightArray() {		return textHeightArray;	}
	public int getTextHeight(int index) {		return textHeightArray[index];	}
	public void setTextHeightArray(int[] textHeight) {		this.textHeightArray = textHeight;	}
	public void setTextHeight(int index, int textHeight) {		this.textHeightArray[index] = textHeight;	}

	public BackgroundSurfaceView(Context context) {
		super(context);
		this.initiateBackground();
	}

	public BackgroundSurfaceView(Context context, AttributeSet attrs) {
		super( context, attrs );
		this.initiateBackground();
	}

	public BackgroundSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super( context, attrs, defStyle );
		this.initiateBackground();
	}

	private void initiateBackground(){
		//현재 surface의 holder를 구한다.
		SurfaceHolder surfaceHolder	= this.getHolder();
		//화면에 뿌려줄 textimage 설정
		this.setTextImageArray(new Bitmap[THE_NUMBER_OF_FALLING_TEXT]);/*비트맵 배열을 초기화합니다. 비트맵도 int 같은 예약어인 것 같습니다.*/
		this.setTextWidthArray(new int[THE_NUMBER_OF_FALLING_TEXT]);
		this.setTextHeightArray(new int[THE_NUMBER_OF_FALLING_TEXT]);

		//비트뱁 배열 초기화...일일히 다 초기화해줍니다.
		for(int i = 0 ; i != this.getTextImageArray().length ; i++){
			this.setTextImage(i, BitmapFactory.decodeResource(this.getResources(), LKAndroid.getID("drawable", "mt"+i)));

			this.setATextWidthOfArray(i, getTextImageArray()[i].getWidth());
			this.setTextHeight(i, getTextImageArray()[i].getHeight());

			this.setTextImage(i, LKBitmap.transparentBitmap(this.getTextImageArray()[i], textWidthArray[i], textHeightArray[i]));
		}

		surfaceHolder.addCallback(this);
		surfaceHolder.setFixedSize(this.getWidth(), this.getHeight());
	}

	public void run() {
		Random random		= new Random();
		Paint paint			= new Paint();

		int[] text_speed_x	= new int[textImageArray.length];
		int[] text_speed_y	= new int[textImageArray.length];
		Point[] text_pt		= new Point[textImageArray.length];
		//lkcustom
		int[] colorCode		= new int[textImageArray.length];
		
		SurfaceHolder holder	= this.getHolder();
		Canvas canvas;
		//src는 원래 이미지의 크기, dest는 실제 그릴 그림의 크기입니다.

		for(int i = 0 ; i < textImageArray.length ; i++){
			//일단 겹침 없이 출력되는 걸로
			text_speed_x[i]	= (this.getWidth() / THE_NUMBER_OF_FALLING_TEXT) * i;
//			text_speed_y[i]	= Math.abs(random.nextInt() % FALLING_TEXT_MAX_SPEED) + FALLING_TEXT_MIN_SPEED;
			text_speed_y[i]	= ((int) (Math.abs(random.nextInt() % FALLING_TEXT_MAX_SPEED) + FALLING_TEXT_MIN_SPEED)/5);
//			text_speed_y[i]	= ((int) (FALLING_TEXT_MAX_SPEED + FALLING_TEXT_MIN_SPEED )/10);
			
			colorCode[i]	= 0xffff0000;
			
			switch (colorCode[i] % 5) {
			case 0: colorCode[i]	+= 0xff00ff00;break;
			case 1: colorCode[i]	+= 0xff00ff04;break;
			case 2: colorCode[i]	+= 0xff00ff03;break;
			case 3: colorCode[i]	+= 0xff00ff02;break;
			case 4: colorCode[i]	+= 0xff00ff01;break;
			}
			
			text_pt[i]	= new Point(text_speed_x[i], -1 * textHeightArray[i]);
		}

		Bitmap background	= BitmapFactory.decodeResource(getResources(),LKAndroid.getID("drawable", "blackimage"));
		Rect rectangleForBGI	= new Rect(0, 0, getWidth(), getHeight());
		

		while(thread != null){
			//double buffering
			//allocate canvas of holder to variable canvas
			canvas	= holder.lockCanvas();
			canvas.drawBitmap(background, null, rectangleForBGI, null);

			//lkcustom
			
			for(int i = 0 ; i < textImageArray.length ; i++)
				canvas.drawBitmap(textImageArray[i], text_pt[i].x, text_pt[i].y, paint);

			//완료된 화변 canvas를 현재 화면(holder)에 할당
			holder.unlockCanvasAndPost(canvas);

			for(int i = 0 ; i != textImageArray.length ; i++){
				if(text_pt[i].y > this.getHeight())
					text_pt[i].y	= -1 * textHeightArray[i];
				text_pt[i].y	+= text_speed_y[i];
				colorCode[i]	= LKAndroid.setColorGradiantly(colorCode[i]);
				paint.setColorFilter(new LightingColorFilter(colorCode[i], 0));
			}
		}
	}

	public void surfaceCreated(SurfaceHolder holder) {
		thread	= new Thread(this);
		thread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, 			int format, int width, int height) {	}
	public void surfaceDestroyed(SurfaceHolder holder) {		thread	= null;	}
}