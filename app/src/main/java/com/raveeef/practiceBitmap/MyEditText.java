package com.raveeef.practiceBitmap;

import android.app.*;
import android.os.*;
import android.hardware.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.util.*;
import android.widget.*;
import android.text.*;
import android.view.View.*;
import android.widget.TextView.*;

public class MyEditText extends EditText
{

	public MyEditText(Context context){
		super(context);
	}
	
	public MyEditText(Context context, AttributeSet attrs){
		super(context, attrs);
	}
	
	public MyEditText(Context context, AttributeSet attrs, int defaultStyle){
		super(context, attrs, defaultStyle);
	}
	
	MyBitmap myBitmap;
	MyButton rotateButton;

	@Override
	public void setOnEditorActionListener(TextView.OnEditorActionListener l)
	{
		rotateButton = (MyButton)((MainActivity)getContext()).findViewById(R.id.mybutton_rotate);
		l = new OnEditorActionListener(){
			@Override
			public boolean onEditorAction(TextView view, int actionId, KeyEvent event)
			{
				if(actionId == android.view.inputmethod.EditorInfo.IME_ACTION_DONE){
					rotateButton.callOnClick();
					return true;
				}
				return false;
			}
		};
		super.setOnEditorActionListener(l);
	}
	
	@Override
	public void addTextChangedListener(TextWatcher watcher)
	{
		myBitmap = (MyBitmap)((MainActivity)getContext()).findViewById(R.id.my_bitmap);
		
		watcher = new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence savedInstanceState, int start, int count, int after){
			}
			
			@Override
			public void afterTextChanged(Editable s){
				if(s.toString().startsWith("°")){
					setHint(String.valueOf(myBitmap.angle.intValue()) + "°");
					setText(s.toString().replaceAll("°",""));
				}	
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count){
				if(start == 0 && before == 0 && getText().length() == 1){
					setText((CharSequence)(getText() + "°"));
					setSelection(1);
				} 
			}
		};
		super.addTextChangedListener(watcher);
	}
	

	@Override
	protected void onSelectionChanged(int selStart, int selEnd)
	{
		if(selStart == getText().length() || selEnd == getText().length()){
			setCursorVisible(false);
			Runnable run = null;
			post(run);
		}
		super.onSelectionChanged(selStart, selEnd);
	}

	@Override
	public boolean post(Runnable action)
	{
		action = new Runnable(){
			@Override
			public void run(){
				if(getText().length() > 0)
					if(getSelectionEnd() ==  getText().length())
						setSelection(getText().length() - 1);
				setCursorVisible(true);
			}
		};
		return super.post(action);
	}
	
	@Override
	public void setOnFocusChangeListener(View.OnFocusChangeListener f)
	{
		f = new OnFocusChangeListener(){
			@Override
			public void onFocusChange(View view, boolean hasFocus)
			{
				if(hasFocus)
					onSelectionChanged(getSelectionStart(), getSelectionEnd());
			}
		};
		super.setOnFocusChangeListener(f);
	}
}
