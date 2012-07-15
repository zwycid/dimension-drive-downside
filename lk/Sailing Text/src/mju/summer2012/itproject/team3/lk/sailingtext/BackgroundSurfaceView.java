package mju.summer2012.itproject.team3.lk.sailingtext;

import java.util.Random;

import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKBitmap;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class BackgroundSurfaceView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private static final int THE_NUMBER_OF_FALLING_TEXT	= 10;
	private static final int FALLING_TEXT_MAX_SPEED	= 20;
	private static final int FALLING_TEXT_MIN_SPEED	= 5;

	private Thread thread;
	private Bitmap[] textImageArray;/*�� �������� ��ϱ� ���ؼ� �迭�� ����ϴ�.*/
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
		//���� surface�� holder�� ���Ѵ�.
		SurfaceHolder surfaceHolder	= this.getHolder();
		//ȭ�鿡 �ѷ��� textimage ����
		this.setTextImageArray(new Bitmap[THE_NUMBER_OF_FALLING_TEXT]);/*��Ʈ�� �迭�� �ʱ�ȭ�մϴ�. ��Ʈ�ʵ� int ���� ������� �� �����ϴ�.*/
		this.setTextWidthArray(new int[THE_NUMBER_OF_FALLING_TEXT]);
		this.setTextHeightArray(new int[THE_NUMBER_OF_FALLING_TEXT]);

		//��Ʈ�� �迭 �ʱ�ȭ...������ �� �ʱ�ȭ���ݴϴ�.
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
		Random random			= new Random();
		Paint paint				= new Paint();

		int[] text_speed_x		= new int[textImageArray.length];
		int[] text_speed_y		= new int[textImageArray.length];
		Point[] text_pt			= new Point[textImageArray.length];
		
		SurfaceHolder holder	= this.getHolder();
		Canvas canvas;
		//src�� ���� �̹����� ũ��, dest�� ���� �׸� �׸��� ũ���Դϴ�.

		for(int i = 0 ; i < textImageArray.length ; i++){
			//�ϴ� ��ħ ���� ��µǴ� �ɷ�
			text_speed_x[i]	= (this.getWidth() / THE_NUMBER_OF_FALLING_TEXT) * i;
			text_speed_y[i]	= Math.abs(random.nextInt() % FALLING_TEXT_MAX_SPEED) + FALLING_TEXT_MIN_SPEED;

			text_pt[i]	= new Point(text_speed_x[i], -1 * textHeightArray[i]);
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

			//�Ϸ�� ȭ�� canvas�� ���� ȭ��(holder)�� �Ҵ�
			holder.unlockCanvasAndPost(canvas);

			for(int i = 0 ; i != textImageArray.length ; i++){
				if(text_pt[i].y > this.getHeight())
					text_pt[i].y	= -1 * textHeightArray[i];
				text_pt[i].y	+= text_speed_y[i];
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