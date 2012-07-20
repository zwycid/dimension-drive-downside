/*
 * Copyright (C) 2010 Neil Davies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 * This code is base on the Android Gallery widget and was Created 
 * by Neil Davies neild001 'at' gmail dot com to be a Coverflow widget
 * 
 * @author Neil Davies
 */
package mju.t3rd.sailingtext.lk.startgame;

import mju.t3rd.sailingtext.zeraf29.db.*;
import mju.t3rd.sailingtext.R;
import mju.t3rd.sailingtext.lk.custom.LKAndroid;
import mju.t3rd.sailingtext.ssidang.GameActivity;
import mju.t3rd.sailingtext.zeraf29.db.Constants;
import mju.t3rd.sailingtext.zeraf29.db.EventsData;
import mju.t3rd.sailingtext.zeraf29.sound.SoundManager;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

/* CoverFlow Source Version 0.8
 * 
 * Update List
 * - completed to implement basic CoverFlow animation
 * - fixed so that can show reflected images of original images (shadow)
 * - modified parameters related to rotation angle, zoom level and image size, etc
 * - applied ViewStub (in order to show/hide album text. and it can also optimize CoverFlow)
 * - modified so that can enable to load views from XML layout file
 */

// Work Priority : H(High), M(Medium), L(Low)
// TODO : modify parameters (depending on customer's requirement) - M

// animation speed is too fast. but I think if this gallery applied to music phone main application,
// 애니메이션 속도가 매우 빠릅니다. 하지만 제 생각에 만약 겔러리가 폰에 올라가면 별로 안빠를꺼임
// speed will be not too fast (without optimizing. I think it will not be needed)
//(물론 최적화 안 했을 때의 얘기지만, 할 필요도 없을듯...)

public class CoverFlowExample extends Activity {
	/** Called when the activity is first created. */
	/** 엑티비티가 만들어질 때 호출됩니다.*/

	public static final int LARGE	= 11;
	public static final int SMALL	= 15;
	public static final int PLAIN	= 35;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;

	private int mSelectedPosition;//<-회면상 가장 왼쪽에 보이는 ImageView
	private View viewStubAlbumText;
	private TextView textviewTitle;
	private TextView textviewArtist;
	private TextView textviewScore;
	private TextView textviewTime;
	private TextView blankView0;
	private TextView blankView1;
	private TextView titleView;
	private TextView titleBlankView;
	private TextView coverflowBlankView;
	private String[] stageName	= {"Genesis","Exodus","Leviticus","Numbers","Deuteronomy","Penta teuch",
			"Sefer Y'hoshua","Esther","Job","Psalms","Proverbs","Ecclesiastes",	"Song of songs"
	};
	//TODO DB 객체 선
	private EventsData events;
	//TODO sound by jh
	SoundManager sManager;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coverflow_main);
		
		//TODO sound by jh
				sManager = SoundManager.getInstance();
				sManager.init(this);
				sManager.addSound(0,R.raw.click);
				
		//TODO DB파일 생성 부분 by jh
				events = new EventsData(this); 
				setContentView(R.layout.coverflow_main);
		
		CoverFlow coverFlow = (CoverFlow)findViewById(R.id.cover_flow);
		coverFlow.setAdapter(new ImageAdapter(this));
		coverFlow.setFadingEdgeLength(getPreferTextSizeForWindow(BLANK, LARGE));
		coverFlow.setVerticalFadingEdgeEnabled(true);
		ImageAdapter coverImageAdapter =  new ImageAdapter(this);

		mSelectedPosition = 0;
		int albumNumber = mSelectedPosition + 1;

		// set to show shadow images (we call it as reflected images)
		// shadow image를 설정합니다(우린 이걸 반사된 이미지들이라 부르지요)
		// this method doesn't work originally (maybe need to modify)
		//이 메소드는 원래대로라면 작동하지 않습니다.(수정이 필요할수도)
		coverImageAdapter.createReflectedImages(); // original method
		coverFlow.setAdapter(coverImageAdapter);

		// Sets the spacing between items(images) in a Gallery
		// 겔러리 내에 아이템(이미지)들 간의 거리를 설정합니다.
		/*
		 *  void android.widget.Gallery.setSpacing(int spacing)
		 *	public void setSpacing (int spacing) 
		 *  Since: API Level 1 
		 *  Sets the spacing between items in a Gallery
		 */
		//사실 이게 간격처럼 보이지만 실제로 해보니 간격이라기 보다는 직전 앞/뒤 이미지의 각도 도 포함하네요.
		//0으로 넣으면 완전 평면에 -90넣으면 90도로 스고, 90을 넣으면 화면당 한개만 나올 정도의 거리로 벌어지네요.
		
//		coverFlow.setSpacing(-25); // original value
//		coverFlow.setSpacing(-50); // when not showing reflected images
		coverFlow.setSpacing(-15); // custom value

		// Set Selected Images at starting Gallery (number is index of selection)
		//     coverFlow.setSelection(4, true); // original method
		coverFlow.setSelection(mSelectedPosition, true); // original method
		//     coverFlow.setSelection(8, true);

		// Set persistence time of animation
		//     coverFlow.setAnimationDuration(1000); // original value
		coverFlow.setAnimationDuration(2000); // original value

		// set Zoom level
//		coverFlow.setMaxZoom(-120); // original value
		coverFlow.setMaxZoom(-90); // custom value

		// set Rotation angle of images
		coverFlow.setMaxRotationAngle(180); // optimal value
		//     coverFlow.setMaxRotationAngle(240); // test value (deprecated)

		coverFlow.setOnItemSelectedListener(new OnItemSelectedListener(){
			public void onItemSelected(
					AdapterView<?> parent, View view, int position, long id){

				mSelectedPosition = position;
				int albumNumber = mSelectedPosition + 1;

				textviewArtist.setText("Stage" + albumNumber);
				textviewTitle.setText(stageName[albumNumber]);
				
				//TODO : 여기에 DB에서 값을 가져와 넣으면 됨!!
				//Worning : 아래에 똩같은 내용이 있는데, 항상 같아야 합니다!!(바뀔쌔 쓰이는 메소드들)
//				textviewScore.setText("Top Score : " + albumNumber);
//				textviewTime.setText("Top Time : "+stageName[albumNumber]);
				long value = 0;
//				value = getEvent(Constants.P_TIME,albumNumber);
//				textviewScore.setText("Top Score : " + ((value==-1)?" No data " : value+" Points"));
//				value = getEvent(Constants.SCORE,albumNumber);
//				textviewTime.setText("Top Time : " + ((value==-1)?" No data " : value+" Sec"));
				value = getEvent(Constants.P_TIME,albumNumber);
				textviewScore.setText("Top Score : 3100 Points");
				value = getEvent(Constants.SCORE,albumNumber);
				textviewTime.setText("Top Time : 02:05 Sec");
			}

			public void onNothingSelected(AdapterView<?> parent){}
		});
		
		coverFlow.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
//				Toast.makeText(CoverFlowExample.this, "adapterView : "+adapterView.toString()+
//						", View : "+ view.toString() + ", position : "+ position + ", id : "+id, 
//						Toast.LENGTH_SHORT).show();
				if (position > 1) {
					Toast.makeText(CoverFlowExample.this, "정식 버전이 아닙니다.",
							Toast.LENGTH_LONG).show();
				}
				else {
					// TODO 여기서 게임을 고고고고
					Intent intent = new Intent(CoverFlowExample.this, GameActivity.class);
					intent.putExtra("stage", position);
					startActivityForResult(intent, 1);
				}
			}
		});

		viewStubAlbumText = ((ViewStub) findViewById(R.id.album_hidden)).inflate();
		textviewArtist = (TextView) findViewById(R.id.album_artist);
		textviewTitle = (TextView) findViewById(R.id.album_title);
		textviewArtist.setTextSize(getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.PLAIN));
		textviewTitle.setTextSize((float) ((getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.SMALL))*0.7));
		LKAndroid.initTextViewShadow(textviewTitle, (int) textviewTitle.getTextSize());
		LKAndroid.initTextViewShadow(textviewArtist, (int) textviewArtist.getTextSize());
		textviewArtist.setText("Stage" + albumNumber);
		textviewTitle.setText(stageName[albumNumber]);
		viewStubAlbumText.setVisibility(View.VISIBLE);

		textviewScore= (TextView) findViewById(R.id.album_Score);
		textviewTime = (TextView) findViewById(R.id.album_Time);
		textviewScore.setTextSize(getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.PLAIN));
		textviewTime.setTextSize(getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.PLAIN));
		LKAndroid.initTextViewShadow(textviewScore, (int) textviewScore.getTextSize());
		LKAndroid.initTextViewShadow(textviewTime, (int) textviewTime.getTextSize());

		//TODO : 여기에 DB에서 값을 가져와 넣으면 됨!!
		//Worning : 위에 똩같은 내용이 있는데, 항상 같아야 합니다!!(처음 보여줄 때 쓰는 메소드들)
		/*
		textviewScore.setText("Top Score : "+ albumNumber);
		textviewTime.setText("Top Time : "+stageName[albumNumber]);
		*/
		/*
		long value = 0;
		value = getEvent(Constants.P_TIME,albumNumber);
		textviewScore.setText("Top Score : " + ((value==-1)?" No data " : value+" Points"));
		value = getEvent(Constants.SCORE,albumNumber);
		textviewTime.setText("Top Time : " + ((value==-1)?" No data " : value+" Sec"));
		*/
		long value = 0;
		value = getEvent(Constants.P_TIME,albumNumber);
		textviewScore.setText("Top Score : 3100 Points");
		value = getEvent(Constants.SCORE,albumNumber);
		textviewTime.setText("Top Time : 02:05 Sec");
		
		titleBlankView	= (TextView) findViewById(R.id.CoverFlowExampleBlankView);
		LKAndroid.initBlankView(titleBlankView, getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.LARGE));
		
		titleView	= (TextView) findViewById(R.id.cover_flowTitleView);
		titleView.setTextSize(getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.LARGE));
		LKAndroid.initTextViewShadow(titleView, (int) titleView.getTextSize());
		
		coverflowBlankView	= (TextView) findViewById(R.id.CoverFlowExampleBlankView1);
		LKAndroid.initBlankView(coverflowBlankView, (int) (
				getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.LARGE)+
				getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.SMALL)));
		
		blankView0	= (TextView) findViewById(R.id.cover_flowBlankView0);
		LKAndroid.initBlankView(blankView0, (int) (titleView.getTextSize()+
				getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.LARGE)+
				getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.SMALL)));
		blankView1	= (TextView) findViewById(R.id.cover_flowBlankView1);
		LKAndroid.initBlankView(blankView1, getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.SMALL));
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		String result = data.getStringExtra("result");
		int time = data.getIntExtra("time", 0);
		int score = data.getIntExtra("score", 0);
		
		if (result.equals("goal")) {
			// TODO DB 연동
//			EventsData events = new EventsData(this);
			SQLiteDatabase db = events.getWritableDatabase();
	    	ContentValues values = new ContentValues();
	    	
//	    	String sqlString = "INSERT INTO "+Constants.TABLE_NAME+"("+Constants.P_TIME+","+Constants.SCORE+") VALUES('"+time+"','"+score+"');";
//	    	db.execSQL(sqlString);
	    	
	    	values.put(Constants.P_TIME, time);
	    	values.put(Constants.SCORE, score);
	    	try {
	    		db.insertOrThrow(Constants.TABLE_NAME, null, values);
	    	}
	    	catch (Exception e) {
	    		e.printStackTrace();
	    	}
	    	
		}
	}

	public class ImageAdapter extends BaseAdapter {

		int mGalleryItemBackground;
		private Context mContext;

		// TODO externalize
		private Integer[] mImageIds = {
				R.drawable.thumbnail_sizeup_map0,
				R.drawable.thumbnail_sizeup_map1,
				R.drawable.thumbnail_sizeup_map2,
				R.drawable.thumbnail_sizeup_map3,
				R.drawable.thumbnail_sizeup_map4,
				R.drawable.thumbnail_sizeup_map5,
				R.drawable.thumbnail_sizeup_map6,
				R.drawable.thumbnail_sizeup_map7,
				R.drawable.thumbnail_sizeup_map8,
				R.drawable.thumbnail_sizeup_map9,
				R.drawable.thumbnail_sizeup_map10,
				R.drawable.thumbnail_sizeup_map11
		};

		private ImageView[] mImages;

		public ImageAdapter(Context c) {
			super();
			mContext = c;
			mImages = new ImageView[mImageIds.length];
		}
		public boolean createReflectedImages() {
			//The gap we want between the reflection and the original image
			final int reflectionGap = 60;

			int index = 0;
			for (int imageId : mImageIds) {
				Bitmap originalImage = BitmapFactory.decodeResource(getResources(), 
						imageId);
				int width = originalImage.getWidth();
				int height = originalImage.getHeight();

//This will not scale but will flip on the Y axis
				Matrix matrix = new Matrix();
				matrix.preScale(1, -1); // original code

//Create a Bitmap with the flip matrix applied to it.
//We only want the bottom half of the image
				Bitmap reflectionImage = Bitmap.createBitmap(originalImage, 0, height/2, width, height/2, matrix, false);


//Create a new bitmap with same width but taller to fit reflection
				Bitmap bitmapWithReflection = Bitmap.createBitmap(width
						, (height + height/2), Config.ARGB_8888);

//Create a new Canvas with the bitmap that's big enough for
//the image plus gap plus reflection
				Canvas canvas = new Canvas(bitmapWithReflection);
				Paint deafaultPaint = new Paint();
				deafaultPaint.setColor(0x00000000);
//Draw in the gap on top(위쪽 갭을 그립니다)
			canvas.drawRect(0, 0, width, reflectionGap, deafaultPaint);
//Draw in the original image(원래 비트맵 이미지를 그림미다)
				canvas.drawBitmap(originalImage, 0, reflectionGap+10, null);
//				canvas.drawBitmap(originalImage, 0, 0, null);//origin code
//Draw in the gap on bottom avobe reflaction(아래쪽 갭을 그리는데, 이 아래쪽 갭은 그림자 위임미다)
//				canvas.drawRect(0, height+reflectionGap, width, height + reflectionGap*2, deafaultPaint);
//				canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);//origin code
//Draw in the reflection
				canvas.drawBitmap(reflectionImage,0, height + reflectionGap, null);

//Create a shader that is a linear gradient that covers the reflection
				Paint paint = new Paint(); 
				LinearGradient shader = new LinearGradient(0, originalImage.getHeight(), 0, 
						bitmapWithReflection.getHeight() + reflectionGap, 0x70ffffff, 0x00ffffff, 
						TileMode.CLAMP); 
//Set the paint to use this shader (linear gradient)
				paint.setShader(shader); 
//Set the Transfer mode to be porter duff and destination in
				paint.setXfermode(new PorterDuffXfermode(Mode.DST_IN)); 
//Draw a rectangle using the paint with our linear gradient
//				canvas.drawRect(0, height, width, 
//						bitmapWithReflection.getHeight() + reflectionGap, paint); //origin code
				canvas.drawRect(0, height+reflectionGap, width, 
						bitmapWithReflection.getHeight() + reflectionGap, paint); 

				ImageView imageView = new ImageView(mContext);
				imageView.setImageBitmap(bitmapWithReflection);

				// set size of Images
				imageView.setLayoutParams(new CoverFlow.LayoutParams((int) (width*0.7), (int) (height*0.7))); // 내가 넣음ㅋ
//				imageView.setLayoutParams(new CoverFlow.LayoutParams(120, 180)); // original value
//				imageView.setLayoutParams(new CoverFlow.LayoutParams(180, 180)); // custom value
//				imageView.setScaleType(ScaleType.MATRIX); // original code (deprecated)

// set aspect of Images (this setting is needed in order to use reflected images (!)
// 이미지의 국면을 정합니다(이 설정은 반사된 이미지들을 사용하기 위해 필요합니다!)
				imageView.setScaleType(ScaleType.CENTER_INSIDE); // custom code
				mImages[index++] = imageView;

			}
			return true;
		}

		public int getCount() {
			return mImageIds.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {

			//Use this code if you want to load from resources
			/*         ImageView i = new ImageView(mContext);
            i.setImageResource(mImageIds[position]);

            // set size of Image
//            i.setLayoutParams(new CoverFlow.LayoutParams(130, 130)); // original value
            i.setLayoutParams(new CoverFlow.LayoutParams(120, 180)); // custom code
            i.setScaleType(ImageView.ScaleType.CENTER_INSIDE); // original code 

            //Make sure we set anti-aliasing otherwise we get jaggies
            BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
            drawable.setAntiAlias(true);
            return i;*/

			return mImages[position];
		}
		/** Returns the size (0.0f to 1.0f) of the views 
		 * depending on the 'offset' to the center. */ 
		public float getScale(boolean focused, int offset) { 
			/* Formula: 1 / (2 ^ offset) */ 
			return Math.max(0, 1.0f / (float)Math.pow(2, Math.abs(offset))); 
		} 
	}
	
	//TODO DB 저장값 호출
		//column : order by 값
		//mapid : mapid 
		private long getEvent(String column, int mapid){
	    	SQLiteDatabase db = events.getReadableDatabase();
	    	String columns[] ={column};
	    	String mid[] = {Integer.toString(mapid)};
	    	//Cursor cursor = db.rowquery(TABLE_NAME, columns, MAP_ID, mid, null, null, column+ORDER_BY);
	    	String sql = "SELECT "+column+" FROM "+Constants.TABLE_NAME+" WHERE "+column+"=? ORDER BY "+column+" DESC;";
	    	Cursor cursor = db.rawQuery(sql, mid);
	    	startManagingCursor(cursor);
	    	long value = 0 ;
	    	while(cursor.moveToNext()){
	    		value = cursor.getLong(0);
	    	}
	    	System.out.println(value);
	    	if(value==0)
	    		value=-1;
	    	return value;
	    }
	/**
	 * 스마트폰 화면 크기에 비례하여 적합한 텍스트 크기를 반환합니다.
	 * @param textType : TEXT	- textView나 drawText할 때 사용합니다. 함수는 보이는 글자를 넣는 경우로 인식합니다.
	 * 					 BLANK	- 함수는 textView인데 Blank로 사용할 때, 즉 빈칸을 넣는 경우로 인식합니다. 
	 * @param textSize : LARGE	- 큰 글씨(text button) / 큰 빈칸이 필요할 때 사용합니다.
	 * 					 SMALL	- 작은 글씨(text button) / 작은 빈칸이 필요할 때 사용합니다.
	 * 					 PLAIN	- 일반적인 글씨가 필요할 떄 사용합니다.
	 * @see 적절한 매개변수가 아니면 0을 반환하니 주의하시기 바랍니다.
	 * @return 윈도우 크기에 적합한 텍스트의 크기를 반환합니다.
	 */
	public int getPreferTextSizeForWindow(int textType, int textSize) {
		if(textType == BLANK){
			if(textSize == LARGE){
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / LARGE)*0.7);
			}else if(textSize == SMALL){	
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / SMALL));
			}else if(textSize == SMALL){	
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / PLAIN)*1.2);
			}
		}else if(textType == TEXT){
			if(textSize == LARGE){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / LARGE;
			}else if(textSize == SMALL){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / SMALL;
			}else if(textSize == PLAIN){
				return this.getWindow().getWindowManager().getDefaultDisplay().getWidth() / PLAIN;
			}
		}
		return 0;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		sManager.play(0);
		super.onBackPressed();
	}
	
}