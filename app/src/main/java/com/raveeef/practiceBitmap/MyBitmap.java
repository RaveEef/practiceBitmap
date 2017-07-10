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
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
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
	}

	public MyBitmap(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	public MyBitmap(Context context, AttributeSet attrs, int defaultStyle){
		super(context, attrs, defaultStyle);
	}

	Bitmap canvasBitmap, emptyCanvasBitmap, textBitmap, emptyTextBitmap;
	Canvas textCanvas, canvasCanvas;;
	Paint textPaint;
	float left, right, top, bottom, r, currentAngle = -1;
	public float angle;
	public boolean doDraw;
	int size, initialState;
	
	private void Tshow(CharSequence text, int repeat)
	{
		Context c = (MainActivity)getContext();
		for(int i = 0; i < repeat; i++)
			Toast.makeText(c, text, Toast.LENGTH_LONG).show();
	}
	
	@Override
	protected void onLayout(boolean changed, int left, int top, int right, int bottom)
	{
		super.onLayout(changed, left, top, right, bottom);
		
		textPaint = new Paint();
		textPaint.setAntiAlias(true);
		textPaint.setTextAlign(Paint.Align.CENTER);
		textPaint.setFakeBoldText(true);
		textPaint.setTextSize(30);
		
		int mWidth = this.getWidth() - this.getPaddingLeft() - getPaddingRight();
		int mHeight = this.getHeight() - this.getPaddingTop() - this.getPaddingBottom();

		size = Math.min(mWidth, mHeight);
		
		
		canvasBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		emptyCanvasBitmap = Bitmap.createBitmap(canvasBitmap.getWidth(), canvasBitmap.getHeight(), canvasBitmap.getConfig());
		textBitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
		emptyTextBitmap = Bitmap.createBitmap(textBitmap.getWidth(), textBitmap.getHeight(), textBitmap.getConfig());
		
		textCanvas = new Canvas(textBitmap);
		canvasCanvas = new Canvas(canvasBitmap);
		
		float textHeight = textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent;
		r = size/2 - (textPaint.getFontMetrics().descent - textPaint.getFontMetrics().ascent)/2;
		this.left = textHeight; //mWidth/2 + this.getPaddingLeft() - r;
		this.right = size - textHeight;
		this.top = textHeight; //mHeight/2 + this.getPaddingTop() - r;
		this.bottom = size - textHeight;
		
	}
	
	@Override
	public void draw(Canvas canvas)
	{
		if(textBitmap.sameAs(emptyTextBitmap))
		{
			Tshow("creating textbitmap",1);
			textBitmap = CreateWindDirections();
			super.draw(canvas);
			
		}
		//canvas.drawBitmap(textBitmap, 0, 0, textPaint);
		
	}
	
	@Override
	protected void onDraw(Canvas canvas)
	{
		Tshow("ondraw", 1);
			

		if(currentAngle == angle)
			Tshow("angle = currentAngle", 1);
		if(currentAngle != angle)
		{
			Tshow("rotating", 1);
			rotateText();
		}
			
		/*Runtime runtime = Runtime.getRuntime();
		long usedMemInMB=(runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
		long maxHeapSizeInMB=runtime.maxMemory() / 1048576L;
		long availHeapSizeInMB = maxHeapSizeInMB - usedMemInMB;
		Tshow("Out of memory\nusedMB: " + usedMemInMB + "\nmaxMB: " + maxHeapSizeInMB + "\navailMB: " + availHeapSizeInMB, 1);
		*/
		canvas.drawBitmap(canvasBitmap, this.getPaddingLeft(), this.getPaddingTop(), null);
		//textBitmap.recycle();
	}
	
	public void rotateText()
	{
		Tshow("angle: " + angle +"\ncurrentAngle: " + currentAngle, 1);	
		currentAngle = angle;
		canvasCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		canvasCanvas.save();
		canvasCanvas.rotate(angle, size/2, size/2);
		canvasCanvas.drawBitmap(textBitmap, 0, 0, textPaint);
		canvasCanvas.restore();
		canvasCanvas.drawBitmap(textBitmap, 0, 0, textPaint);
	}
	
	private Bitmap CreateWindDirections()
	{
		textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(30);
		
		String[] compassPoints = new String[]{">N<", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};
        float degreesPerSegment = 360 / (float)compassPoints.length;
		float dNorthStart = 270 - degreesPerSegment / 2;
		
		Path p = new Path();
        for (int i = 0; i < compassPoints.length; i++) {
            if (i == 1)
                textPaint.setColor(Color.BLACK);
            p.addArc(left, top, right, bottom, (dNorthStart + ( i * degreesPerSegment)), degreesPerSegment);
			textCanvas.drawTextOnPath(compassPoints[i], p, 0.0f, -1.0f, textPaint);
            p.reset();
        }
		
		return textBitmap;
	}
}
