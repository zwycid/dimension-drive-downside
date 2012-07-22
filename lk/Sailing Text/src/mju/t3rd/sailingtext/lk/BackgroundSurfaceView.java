package mju.t3rd.sailingtext.lk;

import java.util.Random;

import mju.t3rd.sailingtext.lk.lkcustom.LKAndroid;
import mju.t3rd.sailingtext.lk.lkcustom.LKBitmap;
import mju.t3rd.sailingtext.lk.lkcustom.LKColor;
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
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BackgroundSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private static final int FALLING_TEXT_LENGTH	= 30;
	private static final int FALLING_TEXT_MAX_SPEED	= 25;
	private static final int FALLING_TEXT_MIN_SPEED	= 5;
	private static final int MT_LENGTH				= 40;

	private Thread thread;
	private Bitmap[] textImageArray;/*공 여러개를 운영하기 위해서 배열로 만듭니다.*/
	private int[] textWidthArray;
	private int[] textHeightArray;
	private int[] mtArray;

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
	public int[] getMtArray() {		return mtArray;	}
	public void setMtArray(int[] mtArray) {		this.mtArray = mtArray;	}
	public int getMt(int index) {		return mtArray[index];	}
	public void setMt(int index, int mtArray) {		this.mtArray[index] = mtArray;	}
	
	
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
		Random random	= new Random();
		//현재 surface의 holder를 구한다.
		SurfaceHolder surfaceHolder	= this.getHolder();
		//화면에 뿌려줄 textimage 설정
		this.setTextImageArray(new Bitmap[FALLING_TEXT_LENGTH]);/*비트맵 배열을 초기화합니다. 비트맵도 int 같은 예약어인 것 같습니다.*/
		this.setTextWidthArray(new int[FALLING_TEXT_LENGTH]);
		this.setTextHeightArray(new int[FALLING_TEXT_LENGTH]);
		this.setMtArray(new int[FALLING_TEXT_LENGTH]);
		int tempMt;

		//비트뱁 배열 초기화...일일히 다 초기화해줍니다.
		for(int i = 0 ; i != this.getTextImageArray().length ; i++){
////////////배경 텍스트 번호 초기화 : 실제 출력 갯수보다 텍스트가 많으므로 겹치지 않게 랜덤으로 배치합니다.
			tempMt	= random.nextInt(MT_LENGTH);
			for(int j = 0 ; j < FALLING_TEXT_LENGTH ; j++){
				if(getMt(j) == tempMt){
					tempMt	= random.nextInt(MT_LENGTH);
					j = 0;
				}
			}
			setMt(i, tempMt);
////////////배경 텍스트 번호 초기화 완료			
			this.setTextImage(i, BitmapFactory.decodeResource(this.getResources(), LKAndroid.getID("drawable", "mt"+getMt(i))));

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

		int[] text_position_x	= new int[textImageArray.length];
		int[] text_speed_y	= new int[textImageArray.length];
		Point[] text_pt		= new Point[textImageArray.length];
		//lkcustom
////////////글자색 초기화			
		int colorCode	=LKColor.getSeedColor();
////////////글자색 초기화 완료
		
		SurfaceHolder holder	= this.getHolder();
		Canvas canvas;
		//src는 원래 이미지의 크기, dest는 실제 그릴 그림의 크기입니다.

		for(int i = 0 ; i < textImageArray.length ; i++){
			text_position_x[i]	= (getWidth() / FALLING_TEXT_LENGTH) * i;
			text_speed_y[i]	= Math.abs(random.nextInt() % FALLING_TEXT_MAX_SPEED) + FALLING_TEXT_MIN_SPEED;
			//속도조절용
//			text_speed_y[i]	= ((int) (Math.abs(random.nextInt() % FALLING_TEXT_MAX_SPEED) + FALLING_TEXT_MIN_SPEED));
			text_pt[i]	= new Point(text_position_x[i], -1 * textHeightArray[i]);
		}

		Bitmap background	= BitmapFactory.decodeResource(getResources(),LKAndroid.getID("drawable", "blackimage"));
		Rect rectangleForBGI	= new Rect(0, 0, getWidth(), getHeight());

		while(thread != null){
			//double buffering
			//allocate canvas of holder to variable canvas
			canvas	= holder.lockCanvas();
			canvas.drawBitmap(background, null, rectangleForBGI, null);

			for(int i = 0 ; i < textImageArray.length ; i++)
				canvas.drawBitmap(textImageArray[i], text_pt[i].x, text_pt[i].y, paint);

			//완료된 화변 canvas를 현재 화면(holder)에 할당
			holder.unlockCanvasAndPost(canvas);

			colorCode	= LKColor.setColorGradiantly(colorCode);
			
			for(int i = 0 ; i != textImageArray.length ; i++){
				if(text_pt[i].y > this.getHeight())
					text_pt[i].y	= -1 * textHeightArray[i];
				text_pt[i].y	+= text_speed_y[i];
				//텍스트마다 색상값을 중감시킵니다.
				//변화된 텍스트 값을 paint에 저장합니다.
				paint.setColorFilter(new LightingColorFilter(colorCode, 0));
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