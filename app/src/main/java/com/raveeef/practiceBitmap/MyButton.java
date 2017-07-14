package com.raveeef.practiceBitmap;

//import com.raveeef.practiceBitmap.MyEditText;
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
import android.view.inputmethod.*;

public class MyButton extends Button
{
	public MyButton(Context context){
		super(context);
	}

	public MyButton(Context context, AttributeSet attrs){
		super(context, attrs);
	}

	public MyButton(Context context, AttributeSet attrs, int defaultStyle){
		super(context, attrs, defaultStyle);
	}
	
	MainActivity mainActivity;
	MyEditText editRotationAngle;
	MyBitmap myBitmap;
	MyDialogFragment dialogFragment;
	LinearLayout mainLayout;
	InputMethodManager imm;
	
	@Override
	public void setOnClickListener(OnClickListener l)
	{
		mainActivity = (MainActivity)getContext();
		editRotationAngle = (MyEditText)mainActivity.findViewById(R.id.my_edittext);
		myBitmap = (MyBitmap)mainActivity.findViewById(R.id.my_bitmap);
		mainLayout = (LinearLayout)mainActivity.findViewById(R.id.layout_main);
		imm = (InputMethodManager)mainActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
		
		l = new OnClickListener(){
			@Override
			public void onClick(View view)
			{
				boolean dialogShown = false;
				Float inputValue = null;
				String inputString = editRotationAngle.getText().toString();

				if(inputString.endsWith("°"))
					inputString = inputString.substring(0, (inputString.length() - 1));
				
				if(!inputString.matches("")){
					inputValue = Float.valueOf(inputString);

					if(myBitmap.angle.equals(inputValue)){
						dialogFragment = MyDialogFragment.newInstance("Equal rotation angle!", "To rotate the text, enter a value different from the one shown in the textbox.");
						dialogFragment.show(mainActivity.getFragmentManager(), "equalValue");
						dialogShown = true;
					} else if(inputValue < 0.0f || inputValue > 360.0f) {
						dialogFragment = MyDialogFragment.newInstance("Invalid rotation angle!", "Please enter some value between 0° and 360°.");
						dialogFragment.show(mainActivity.getFragmentManager(), "invalidValue");
						inputValue = Float.valueOf(0.0f);
						dialogShown = true;
					}
				} else {
					dialogFragment = MyDialogFragment.newInstance("Invalid rotation angle!", "Please enter some value between 0° and 360°.");
					dialogFragment.show(mainActivity.getFragmentManager(), "nullValue");
					inputValue = Float.valueOf(0.0f);
					dialogShown = true;
				} 

				if(!dialogShown){
					myBitmap.angle = inputValue;
					myBitmap.invalidate();
					editRotationAngle.setHint(String.valueOf(myBitmap.angle.intValue()) + "°"); 
					imm.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), 0);
					mainLayout.requestFocus();
				}	

				if(!keyboardShown(editRotationAngle.getRootView()))
					mainLayout.requestFocus();
			}
		};
		super.setOnClickListener(l);
	}
	
	public boolean keyboardShown(View rootView){
		final int SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD = 128;
		Rect r = new Rect();
		rootView.getWindowVisibleDisplayFrame(r);
		DisplayMetrics dm = rootView.getResources().getDisplayMetrics();
		int hDiff = rootView.getBottom() - r.bottom;
		if(hDiff > (SOFT_KEYBOARD_HEIGHT_DP_THRESHOLD * dm.density))
			return true;
		return false;	
	}
	
	public void afterDialog(){
		if(dialogFragment.getTag().equals("invalidValue") || dialogFragment.getTag().equals("equalValue"))
			editRotationAngle.setText("");
	
		editRotationAngle.requestFocus();
		if(!keyboardShown(editRotationAngle.getRootView()))
			imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
	}
}
