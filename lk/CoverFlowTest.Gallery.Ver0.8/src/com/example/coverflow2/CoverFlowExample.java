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
package com.example.coverflow2;

import java.io.FileInputStream;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;

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
// speed will be not too fast (without optimizing. I think it will not be needed)


public class CoverFlowExample extends Activity {
    /** Called when the activity is first created. */
	
    private int mSelectedPosition;
    private View mAlbumText;
    private TextView mTitle;
    private TextView mArtist;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
     super.onCreate(savedInstanceState);
     setContentView(R.layout.main);
     
     CoverFlow coverFlow = (CoverFlow)findViewById(R.id.cover_flow);
     coverFlow.setAdapter(new ImageAdapter(this));
     ImageAdapter coverImageAdapter =  new ImageAdapter(this);
     
     mSelectedPosition = 4;
     int albumNumber = mSelectedPosition + 1;
     
     // set to show shadow images (we call it as reflected images)
     // this method doesn't work originally (maybe need to modify)
     coverImageAdapter.createReflectedImages(); // original method
     coverFlow.setAdapter(coverImageAdapter);
     
    // Sets the spacing between items(images) in a Gallery
//     coverFlow.setSpacing(-25); // original value
//     coverFlow.setSpacing(-50); // when not showing reflected images
     coverFlow.setSpacing(-15); // custom value

     // Set Selected Images at starting Gallery (number is index of selection)
//     coverFlow.setSelection(4, true); // original method
     coverFlow.setSelection(mSelectedPosition, true); // original method
//     coverFlow.setSelection(8, true);
     
     // Set persistence time of animation
//     coverFlow.setAnimationDuration(1000); // original value
     coverFlow.setAnimationDuration(2000); // original value
     
     // set Zoom level
     coverFlow.setMaxZoom(-120); // original value
//     coverFlow.setMaxZoom(-200); // custom value
     
     // set Rotation angle of images
     coverFlow.setMaxRotationAngle(180); // optimal value
//     coverFlow.setMaxRotationAngle(240); // test value (deprecated)
     
     coverFlow.setOnItemSelectedListener(new OnItemSelectedListener()
	{
		public void onItemSelected(
                AdapterView<?> parent,
                View view,
                int position,
                long id)
        {
			mSelectedPosition = position;
			int albumNumber = mSelectedPosition + 1;
			
			mArtist.setText("Artist" + albumNumber);
			mTitle.setText("Title" + albumNumber);
        }

		public void onNothingSelected(AdapterView<?> parent)
        {
        }
	});
     
     	mAlbumText = ((ViewStub) findViewById(R.id.album_hidden)).inflate();
     	mArtist = (TextView) findViewById(R.id.album_artist);
     	mTitle = (TextView) findViewById(R.id.album_title);
     	mAlbumText.setVisibility(View.VISIBLE);
     	mArtist.setText("Artist" + albumNumber);
		mTitle.setText("Title" + albumNumber);
    }
    
    public class ImageAdapter extends BaseAdapter {
   	 
   	 
        int mGalleryItemBackground;
        private Context mContext;

        private FileInputStream fis;
           
        private Integer[] mImageIds = {
//       		 R.drawable.kasabian_kasabian,
                R.drawable.starssailor_silence_is_easy,
                R.drawable.killers_day_and_age,
                R.drawable.garbage_bleed_like_me,
                R.drawable.death_cub_for_cutie_the_photo_album,
       		 R.drawable.kasabian_kasabian,
                R.drawable.massive_attack_collected,
                R.drawable.muse_the_resistance,
                R.drawable.starssailor_silence_is_easy
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
              Bitmap bitmapWithReflection = Bitmap.createBitmap(width 
                , (height + height/2), Config.ARGB_8888);
            
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
             imageView.setLayoutParams(new CoverFlow.LayoutParams(120, 180)); // original value
//             imageView.setLayoutParams(new CoverFlow.LayoutParams(180, 180)); // custom value
//             imageView.setScaleType(ScaleType.MATRIX); // original code (deprecated)
             
             // set aspect of Images (this setting is needed in order to use reflected images (!)
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
}