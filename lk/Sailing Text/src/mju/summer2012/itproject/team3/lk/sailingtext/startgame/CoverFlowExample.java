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
package mju.summer2012.itproject.team3.lk.sailingtext.startgame;

import mju.summer2012.itproject.team3.lk.sailingtext.R;
import mju.summer2012.itproject.team3.lk.sailingtext.lkcustom.LKAndroid;
import android.app.Activity;
import android.content.Context;
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
import android.util.Log;
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
// �ִϸ��̼� �ӵ��� �ſ� �����ϴ�. ������ �� ������ ���� �ַ����� ���� �ö󰡸� ���� �Ⱥ�������
// speed will be not too fast (without optimizing. I think it will not be needed)
//(���� ����ȭ �� ���� ���� �������, �� �ʿ䵵 ������...)

public class CoverFlowExample extends Activity {
	/** Called when the activity is first created. */
	/** ��Ƽ��Ƽ�� ������� �� ȣ��˴ϴ�.*/

	public static final int LARGE	= 11;
	public static final int SMALL	= 15;
	public static final int PLAIN	= 35;
	public static final int BLANK	= 0;
	public static final int TEXT	= 1;

	private int mSelectedPosition;//<-ȸ��� ���� ���ʿ� ���̴� ImageView
	private View viewStubAlbumText;
	private TextView textviewTitle;
	private TextView textviewArtist;
	private TextView textviewScore;
	private TextView textviewTime;
	private TextView blankView0;
	private TextView blankView1;
	private TextView titleView;
	private String[] stageName	= {
			"â����","Ż���","������","�μ���","�Ÿ���","����","���",
			"����","���","���ҷ�","�ư�","������","��ȸ��","�ü�","����"
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.coverflow_main);

		CoverFlow coverFlow = (CoverFlow)findViewById(R.id.cover_flow);
		coverFlow.setAdapter(new ImageAdapter(this));
		ImageAdapter coverImageAdapter =  new ImageAdapter(this);

		mSelectedPosition = 10;
		int albumNumber = mSelectedPosition + 1;

		// set to show shadow images (we call it as reflected images)
		// shadow image�� �����մϴ�(�츰 �̰� �ݻ�� �̹������̶� �θ�����)
		// this method doesn't work originally (maybe need to modify)
		//�� �޼ҵ�� ������ζ�� �۵����� �ʽ��ϴ�.(������ �ʿ��Ҽ���)
		coverImageAdapter.createReflectedImages(); // original method
		coverFlow.setAdapter(coverImageAdapter);

		// Sets the spacing between items(images) in a Gallery
		// �ַ��� ���� ������(�̹���)�� ���� �Ÿ��� �����մϴ�.
		/*
		 *  void android.widget.Gallery.setSpacing(int spacing)
		 *	public void setSpacing (int spacing) 
		 *  Since: API Level 1 
		 *  Sets the spacing between items in a Gallery
		 */
		//��� �̰� ����ó�� �������� ������ �غ��� �����̶�� ���ٴ� ���� ��/�� �̹����� ���� �� �����ϳ׿�.
		//0���� ������ ���� ��鿡 -90������ 90���� ����, 90�� ������ ȭ��� �Ѱ��� ���� ������ �Ÿ��� �������׿�.
		
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
				
				//TODO : ���⿡ DB���� ���� ������ ������ ��!!
				//Worning : �Ʒ��� �k���� ������ �ִµ�, �׻� ���ƾ� �մϴ�!!(�ٲ�� ���̴� �޼ҵ��)
				textviewScore.setText("Top Score : " + "���� �������� ��ȣ�� " + albumNumber);
				textviewTime.setText("Top Time : " + "���� ���������� "+stageName[albumNumber]);
			}

			public void onNothingSelected(AdapterView<?> parent){}
		});
		
		coverFlow.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterView, View view, int position,
					long id) {
				Toast.makeText(CoverFlowExample.this, "adapterView : "+adapterView.toString()+
						", View : "+ view.toString() + ", position : "+ position + ", id : "+id, 
						Toast.LENGTH_SHORT).show();
			}
		});

		viewStubAlbumText = ((ViewStub) findViewById(R.id.album_hidden)).inflate();
		textviewArtist = (TextView) findViewById(R.id.album_artist);
		textviewTitle = (TextView) findViewById(R.id.album_title);
		textviewArtist.setTextSize(getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.PLAIN));
		textviewTitle.setTextSize(getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.PLAIN));
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

		//TODO : ���⿡ DB���� ���� ������ ������ ��!!
		//Worning : ���� �k���� ������ �ִµ�, �׻� ���ƾ� �մϴ�!!(ó�� ������ �� ���� �޼ҵ��)
		textviewScore.setText("Top Score : " + "���� �������� ��ȣ�� " + albumNumber);
		textviewTime.setText("Top Time : " + "���� ���������� "+stageName[albumNumber]);
		
		titleView	= (TextView) findViewById(R.id.cover_flowTitleView);
		titleView.setTextSize(getPreferTextSizeForWindow(CoverFlowExample.TEXT, CoverFlowExample.LARGE));
		LKAndroid.initTextViewShadow(titleView, (int) titleView.getTextSize());
		
		blankView0	= (TextView) findViewById(R.id.cover_flowBlankView0);
		LKAndroid.initBlankView(blankView0, (int) (getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.LARGE)*1.2));
		
		blankView1	= (TextView) findViewById(R.id.cover_flowBlankView1);
		LKAndroid.initBlankView(blankView1, getPreferTextSizeForWindow(CoverFlowExample.BLANK, CoverFlowExample.SMALL));
	}

	public class ImageAdapter extends BaseAdapter {

		int mGalleryItemBackground;
		private Context mContext;

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
			final int reflectionGap = 4;

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
//              Bitmap bitmapWithReflection = Bitmap.createBitmap(width 
//                      , (height + height/2), Config.ARGB_8888);
				Bitmap bitmapWithReflection = Bitmap.createBitmap(width 
						, (height), Config.ARGB_8888);

//Create a new Canvas with the bitmap that's big enough for
//the image plus gap plus reflection
				Canvas canvas = new Canvas(bitmapWithReflection);
//Draw in the original image
				canvas.drawBitmap(originalImage, 0, 0, null);
//Draw in the gap
				Paint deafaultPaint = new Paint();
				canvas.drawRect(0, height, width, height + reflectionGap, deafaultPaint);
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
				canvas.drawRect(0, height, width, 
						bitmapWithReflection.getHeight() + reflectionGap, paint); 

				ImageView imageView = new ImageView(mContext);
				imageView.setImageBitmap(bitmapWithReflection);

// set size of Images
				imageView.setLayoutParams(new CoverFlow.LayoutParams((int) (width*0.4), (int) (height*0.4))); // ���� ������
//           imageView.setLayoutParams(new CoverFlow.LayoutParams(120, 180)); // original value
//           imageView.setLayoutParams(new CoverFlow.LayoutParams(180, 180)); // custom value
//           imageView.setScaleType(ScaleType.MATRIX); // original code (deprecated)

// set aspect of Images (this setting is needed in order to use reflected images (!)
// �̹����� ������ ���մϴ�(�� ������ �ݻ�� �̹������� ����ϱ� ���� �ʿ��մϴ�!)
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
	/**
	 * ����Ʈ�� ȭ�� ũ�⿡ ����Ͽ� ������ �ؽ�Ʈ ũ�⸦ ��ȯ�մϴ�.
	 * @param textType : TEXT	- textView�� drawText�� �� ����մϴ�. �Լ��� ���̴� ���ڸ� �ִ� ���� �ν��մϴ�.
	 * 					 BLANK	- �Լ��� textView�ε� Blank�� ����� ��, �� ��ĭ�� �ִ� ���� �ν��մϴ�. 
	 * @param textSize : LARGE	- ū �۾�(text button) / ū ��ĭ�� �ʿ��� �� ����մϴ�.
	 * 					 SMALL	- ���� �۾�(text button) / ���� ��ĭ�� �ʿ��� �� ����մϴ�.
	 * 					 PLAIN	- �Ϲ����� �۾��� �ʿ��� �� ����մϴ�.
	 * @see ������ �Ű������� �ƴϸ� 0�� ��ȯ�ϴ� �����Ͻñ� �ٶ��ϴ�.
	 * @return ������ ũ�⿡ ������ �ؽ�Ʈ�� ũ�⸦ ��ȯ�մϴ�.
	 */
	public int getPreferTextSizeForWindow(int textType, int textSize) {
		if(textType == BLANK){
			if(textSize == LARGE){
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / LARGE)*1.5);
			}else if(textSize == SMALL){	
				return (int) ((this.getWindow().getWindowManager().getDefaultDisplay().getHeight() / SMALL)*1.5);
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
}