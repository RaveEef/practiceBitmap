package com.raveeef.practiceBitmap;

import com.raveeef.practiceBitmap.*;
import android.app.*;
import android.os.*;
import android.hardware.*;
import android.content.*;
import android.graphics.*;
import android.view.inputmethod.*;
import android.view.*;
import android.util.*;
import android.widget.*;
import android.text.*;
import android.content.res.*;

public class MainActivity extends Activity
{
	MyButton rotateButton;
	MyEditText editRotationAngle;
	LinearLayout mainLayout;
	ScrollView scrollView;
	
    @Override
	protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		rotateButton = (MyButton)findViewById(R.id.mybutton_rotate);
		editRotationAngle = (MyEditText)findViewById(R.id.my_edittext);
	 	mainLayout = (LinearLayout)findViewById(R.id.layout_main);
		scrollView = (ScrollView)findViewById(R.id.mainScrollView);
		
		mainLayout.requestFocus();
		
		TextWatcher watcher = null;
		View.OnFocusChangeListener focusListener = null;
		TextView.OnEditorActionListener actionListener = null;
		editRotationAngle.addTextChangedListener(watcher);
		editRotationAngle.setOnFocusChangeListener(focusListener);
		editRotationAngle.setOnEditorActionListener(actionListener);
		
		View.OnClickListener clickListener = null;
		rotateButton.setOnClickListener(clickListener);

		scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener(){
			@Override
			public void onScrollChange(View v, int left, int top, int oldLeft, int oldTop){
				if(v.getHeight() == mainLayout.getHeight()){
					if(getCurrentFocus() == editRotationAngle)
						mainLayout.requestFocus();
				}	
			}
		});
	}
}

	 
		 

