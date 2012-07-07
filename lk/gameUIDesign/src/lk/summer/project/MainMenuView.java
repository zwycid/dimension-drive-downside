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
	private Bitmap[] textImages;/*공 여러개를 운영하기 위해서 배열로 만듭니다.*/
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
		//현재 surface의 holder를 구한다.
		SurfaceHolder surfaceHolder	= this.getHolder();
		//화면에 뿌려줄 ball 설정
		textImages		= new Bitmap[TEXT_NUMBER];/*비트맵 배열을 초기화합니다. 비트맵도 int 같은 예약어인 것 같습니다.*/
		textWidth	= new ArrayList<Integer>();
		textHeight	= new ArrayList<Integer>();
		//비트뱁 배열 초기화...일일히 다 초기화해줍니다.
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
		//src는 원래 이미지의 크기, dest는 실제 그릴 그림의 크기입니다.

		for(int i = 0 ; i < textImages.length ; i++){
			//			text_speed_x[i]	= (random.nextInt()>>1) % MAX_SPEED + MIN_SPEED;
			//일단 겹침 없이 출력되는 걸로
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


			//완료된 화변 canvas를 현재 화면(holder)에 할당
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