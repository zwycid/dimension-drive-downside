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


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Transformation;
import android.widget.Gallery;
import android.widget.ImageView;

@SuppressLint({ "NewApi", "NewApi" })
public class CoverFlow extends Gallery {

	/**
	 * Graphics Camera used for transforming the matrix of ImageViews
	 * ImageView의 매트릭스를 변형하기 위한 그레픽스 카메라
	 */
	private Camera mCamera = new Camera();

	/**
	 * The maximum angle the Child ImageView will be rotated by
	 * Galley에 포함된 ImageView가 뒤집힐 최대 각도
	 */    
	//	need more test in order to device optimal angle;
	//	장치별 적절 각도를 찾아야될 필요성이 있음.
	//	to adjust angle, use setter and getter method
	//  각도를 바꿀꺼면 getter랑 setter를 쓸 것.
	private int mMaxRotationAngle;
	//	private int mMaxRotationAngle = 60; // original value
	//	private int mMaxRotationAngle = 180;

	/**
	 * The maximum zoom on the centre Child
	 */
	// in order to adjust zoom level, also have to consider layout's size
	// zoom 의 깊이를 조정하기 위해선 레이아웃의 크기를 꼭 생각해야 합니다.
	//양수면 작아지고 음수면 커짐.
	private int mMaxZoom = -120; // original value

	/**
	 * The Centre of the Coverflow 
	 */   
	private int mCoveflowCenter;

	public CoverFlow(Context context) {
		super(context);
		this.setStaticTransformationsEnabled(true);
	}

	public CoverFlow(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.setStaticTransformationsEnabled(true);
	}

	public CoverFlow(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.setStaticTransformationsEnabled(true);   
	}

	/**
	 * Get the max rotational angle of the image
	 * @return the mMaxRotationAngle
	 */
	public int getMaxRotationAngle() {		return mMaxRotationAngle;	}

	/**
	 * Set the max rotational angle of each image
	 * @param maxRotationAngle the mMaxRotationAngle to set
	 */
	public void setMaxRotationAngle(int maxRotationAngle) {		mMaxRotationAngle = maxRotationAngle;	}

	/**
	 * Get the Max zoom of the centre image
	 * @return the mMaxZoom
	 */
	public int getMaxZoom() {		return mMaxZoom;	}

	/**
	 * Set the max zoom of the centre image
	 * @param maxZoom the mMaxZoom to set
	 */
	public void setMaxZoom(int maxZoom) {		mMaxZoom = maxZoom;	}

	/**
	 * Get the Centre of the Coverflow
	 * @return The centre of this Coverflow.
	 */
	private int getCenterOfCoverflow() {
		return (getWidth() - getPaddingLeft() - getPaddingRight()) / 2 + getPaddingLeft();
	}

	/**
	 * Get the Centre of the View
	 * @return The centre of the given view.
	 */
	private static int getCenterOfView(View view) {
		return view.getLeft() + view.getWidth() / 2;
	}  
	/**
	 * {@inheritDoc}
	 *
	 * @see #setStaticTransformationsEnabled(boolean) 
	 */ 
	protected boolean getChildStaticTransformation(View child, Transformation transFormation) {

		final int childCenter = getCenterOfView(child);
		final int childWidth = child.getWidth() ;
		int rotationAngle = 0;

		transFormation.clear();
		transFormation.setTransformationType(Transformation.TYPE_MATRIX);

		if (childCenter == mCoveflowCenter) {
			transformImageBitmap((ImageView) child, transFormation, 0);
		} else {      
			rotationAngle = (int) (((float) (mCoveflowCenter - childCenter)/ childWidth) *  mMaxRotationAngle);
			if (Math.abs(rotationAngle) > mMaxRotationAngle) {
				rotationAngle = (rotationAngle < 0) ? -mMaxRotationAngle : mMaxRotationAngle;   
			}
			transformImageBitmap((ImageView) child, transFormation, rotationAngle);         
		}    

		return true;
	}

	/**
	 * This is called during layout when the size of this view has changed. If
	 * you were just added to the view hierarchy, you're called with the old
	 * values of 0.
	 *
	 * @param w Current width of this view.
	 * @param h Current height of this view.
	 * @param oldw Old width of this view.
	 * @param oldh Old height of this view.
	 */
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		mCoveflowCenter = getCenterOfCoverflow();
		super.onSizeChanged(w, h, oldw, oldh);
	}

	/**
	 * Transform the Image Bitmap by the Angle passed 
	 * 
	 * @param imageView ImageView the ImageView whose bitmap we want to rotate
	 * @param t transformation 
	 * @param rotationAngle the Angle by which to rotate the Bitmap
	 */
	private void transformImageBitmap(ImageView child, Transformation t, int rotationAngle) {            
		mCamera.save();
		final Matrix imageMatrix = t.getMatrix();;
		final int imageHeight = child.getLayoutParams().height;
		final int imageWidth = child.getLayoutParams().width;
		final int rotation = Math.abs(rotationAngle);

		mCamera.translate(0.0f, 0.0f, 100.0f);

		//As the angle of the view gets less, zoom in     
		if ( rotation < mMaxRotationAngle ) {
//			float zoomAmount = (float) (mMaxZoom +  (rotation * 1.5)); // original code
			float zoomAmount = (float) (mMaxZoom +  (rotation * 0.5)); // custom code (good)
//			float zoomAmount = (float) (mMaxZoom +  (rotation * 0.75)); // custom code (good)
			mCamera.translate(0.0f, 0.0f, zoomAmount);          
		} 

		mCamera.rotateY(rotationAngle);
		mCamera.getMatrix(imageMatrix);               
		imageMatrix.preTranslate(-(imageWidth/2), -(imageHeight/2)); 
		imageMatrix.postTranslate((imageWidth/2), (imageHeight/2));
		mCamera.restore();
	}
}