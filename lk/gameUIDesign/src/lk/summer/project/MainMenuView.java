package lk.summer.project;

import java.util.ArrayList;
import java.util.Random;

import lk.summer.project.gameuidesign.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MainMenuView extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	private final int TEXT_NUMBER	= 10;
	private final int MAX_SPEED	= 20;
	private final int MIN_SPEED	= 5;

	private Thread thread;
	private Bitmap[] textImages;/*�� �������� ��ϱ� ���ؼ� �迭�� ����ϴ�.*/
	private ArrayList<Integer> textWidth;
	private ArrayList<Integer> textHeight;

	int[] buttonImageId	= {R.drawable.button_startgame_black, 
			R.drawable.button_ranking_black, 
			R.drawable.button_options_black};


	public MainMenuView(Context context) {
		super(context);

		this.initiateBackground();
	}

	public MainMenuView(Context context, AttributeSet attrs) {

		super( context, attrs );
		this.initiateBackground();
	}

	public MainMenuView(Context context, AttributeSet attrs, int defStyle) {

		super( context, attrs, defStyle );
		this.initiateBackground();
	}

	private void initiateBackground(){
		Bitmap bitmap;
		//���� surface�� holder�� ���Ѵ�.
		SurfaceHolder surfaceHolder	= this.getHolder();
		//ȭ�鿡 �ѷ��� ball ����
		textImages		= new Bitmap[TEXT_NUMBER];/*��Ʈ�� �迭�� �ʱ�ȭ�մϴ�. ��Ʈ�ʵ� int ���� ������� �� �����ϴ�.*/
		textWidth	= new ArrayList<Integer>();
		textHeight	= new ArrayList<Integer>();
		//��Ʈ�� �迭 �ʱ�ȭ...������ �� �ʱ�ȭ���ݴϴ�.
		for(int i = 0 ; i != textImages.length ; i++){
			try {
				bitmap	= BitmapFactory.decodeResource(
						this.getResources(), R.drawable.class.getField("mt"+i).getInt("mt"+i));

				textWidth.add(Integer.valueOf(bitmap.getWidth()));
				textHeight.add(Integer.valueOf(bitmap.getHeight()));

				textImages[i]	= allocateBitmap(bitmap, textWidth.get(i), textHeight.get(i));
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SecurityException e) {
				// TODO Auto-genera lted catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		surfaceHolder.addCallback(this);
		surfaceHolder.setFixedSize(this.getWidth(), this.getHeight());
	}

	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread	= new Thread(this);
		thread.start();
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread	= null;
	}

	public void run() {
		// TODO Auto-generated method stub
		Random random	= new Random();
		Paint paint		= new Paint();

		int[] text_speed_x	= new int[textImages.length];
		int[] text_speed_y	= new int[textImages.length];
		Point[] text_pt		= new Point[textImages.length];

		SurfaceHolder holder	= this.getHolder();
		Canvas canvas;
		Bitmap bitmap;
		Rect src, dest;
		//src�� ���� �̹����� ũ��, dest�� ���� �׸� �׸��� ũ���Դϴ�.

		for(int i = 0 ; i < textImages.length ; i++){
			//			text_speed_x[i]	= (random.nextInt()>>1) % MAX_SPEED + MIN_SPEED;
			//�ϴ� ��ħ ���� ��µǴ� �ɷ�
			text_speed_x[i]	= (this.getWidth() / TEXT_NUMBER) * i;
			text_speed_y[i]	= Math.abs(random.nextInt() % MAX_SPEED) + MIN_SPEED;

			text_pt[i]	= new Point(text_speed_x[i], -1 * textHeight.get(i));
		}

		bitmap	= BitmapFactory.decodeResource(getResources(), R.drawable.bg2);
		src		= new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		dest	= new Rect(0, 0, getWidth(), getHeight());

		while(thread != null){
			//double buffering
			//allocate canvas of holder to variable canvas
			canvas	= holder.lockCanvas();
			canvas.drawBitmap(bitmap, src, dest, null);

			for(int i = 0 ; i < textImages.length ; i++)
				canvas.drawBitmap(textImages[i], text_pt[i].x, text_pt[i].y, paint);


			//�Ϸ�� ȭ�� canvas�� ���� ȭ��(holder)�� �Ҵ�
			holder.unlockCanvasAndPost(canvas);

			for(int i = 0 ; i != textImages.length ; i++){
				if(text_pt[i].y > this.getHeight())
					text_pt[i].y	= -1 * textHeight.get(i);
				text_pt[i].y	+= text_speed_y[i];

			}
		}
	}	

	private Bitmap allocateBitmap(Bitmap bitmap, int width, int height){
		int[] pixels	= new int[width * height];
		bitmap.getPixels(pixels, 0, width, 0, 0, width, height);

		for(int i = 0 ; i != pixels.length ; i++){
			if(pixels[i] == Color.WHITE){
				pixels[i]	= Color.TRANSPARENT;
			}
		}

		return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.ARGB_8888);
	}

}