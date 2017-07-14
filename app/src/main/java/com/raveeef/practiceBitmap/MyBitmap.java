package com.raveeef.practiceBitmap;

import android.app.*;
import android.os.*;
import android.hardware.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.util.*;
import android.widget.*;

public class MyBitmap extends View{
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		int desiredWidth = 2000; 
		int desiredHeight = 2000; 

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		if (widthMode == MeasureSpec.EXACTLY)
			width = widthSize; //Must be this size
		else if (widthMode == MeasureSpec.AT_MOST)
			width = Math.min(desiredWidth, widthSize); //Can't be bigger than..
		else 
			width = desiredWidth; //No limit

		if (heightMode == MeasureSpec.EXACTLY) 
			height = heightSize;
		else if (heightMode == MeasureSpec.AT_MOST) 
			height = Math.min(desiredHeight, heightSize);
		else
			height = desiredHeight;

		super.setMeasuredDimension(Math.min(width, height), Math.min(width, height));
	} 
	
	public MyBitmap(Context context){
		super(context);
		init();
	}

	public MyBitmap(Context context, AttributeSet attrs){
		super(context, attrs);
		init();
	}

	public MyBitmap(Context context, AttributeSet attrs, int defaultStyle){
		super(context, attrs, defaultStyle);
		init();
	}

	Bitmap initialBitmap, rotatedBitmap;
	Paint myPaint;
	int size;
	float left, right, top, bottom, r;
	Integer currentState = null, doDraw;
	Float currentAngle = new Float(0.0f);
	public Float angle = new Float(0.0f);
	boolean valuesSet = false, 
			initialBitmapSet = false,
			rotatedBitmapSet = false;
	
	@Override
	protected void onDraw(Canvas canvas){
		if(!angle.equals(currentAngle))
			rotatedBitmapSet = false;
			
		setContent();
		canvas.drawBitmap(rotatedBitmap, this.getPaddingLeft(), this.getPaddingRight(), null);
		super.onDraw(canvas);
	}
	
	public void init(){
		myPaint = new Paint();
		myPaint.setAntiAlias(true);
		myPaint.setTextAlign(Paint.Align.CENTER);
		myPaint.setFakeBoldText(true);
		myPaint.setTextSize(30);
	}
	
	private void setContent(){
		if(!valuesSet)
			setValues();
			
		if(!initialBitmapSet)
			setInitialBitmap();
			
		if(!rotatedBitmapSet)
			setRotatedBitmap();
		
	}
	
	private void setValues(){
		int mWidth = this.getWidth() - this.getPaddingLeft() - getPaddingRight();
		int mHeight = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();
		size = Math.min(mWidth, mHeight);
		
		float textHeight = myPaint.getFontMetrics().descent - myPaint.getFontMetrics().ascent;
		r = (size - textHeight)/2;
		left = textHeight;
		right = size - textHeight;
		top = textHeight;
		bottom = size - textHeight;

		initialBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		rotatedBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		
		valuesSet = true;
	}
		
	private void setInitialBitmap(){
		myPaint.setColor(Color.GREEN);
        myPaint.setTextSize(30);

		String[] compassPoints = new String[]{">N<", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        float degreesPerSegment = 360 / (float)compassPoints.length;
		float dNorthStart = 270 - degreesPerSegment / 2;

		Path p = new Path();
		Canvas canvas = new Canvas(initialBitmap);
        for (int i = 0; i < compassPoints.length; i++) {
            if (i == 1)
                myPaint.setColor(Color.BLACK);
            p.addArc(left, top, right, bottom, (dNorthStart + ( i * degreesPerSegment)), degreesPerSegment);
			canvas.drawTextOnPath(compassPoints[i], p, 0.0f, -1.0f, myPaint);
            p.reset();
        }
		
		initialBitmapSet = true;
	}
	
	private void setRotatedBitmap(){
		if(!initialBitmapSet){
			return;
		} else {
			if(angle.equals(currentAngle) && !rotatedBitmapSet){
				rotatedBitmap = initialBitmap.copy(initialBitmap.getConfig(), true);
			} else if(angle.equals(0.0f) && !angle.equals(currentAngle)){
				rotatedBitmap = initialBitmap.copy(initialBitmap.getConfig(), true);
				currentAngle = angle;
			} else if(angle != currentAngle){
				Canvas canvas = new Canvas(rotatedBitmap);
				canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
				canvas.save();
				canvas.rotate(angle, size/2, size/2); 
				canvas.drawBitmap(initialBitmap, 0, 0, null);
				canvas.restore();
				currentAngle = angle;
			}
			rotatedBitmapSet = true;
		}
	}
}
