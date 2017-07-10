package com.raveeef.practiceBitmap;

import com.raveeef.practiceBitmap.MyBitmap;
import com.raveeef.practiceBitmap.MyDialogFragment;
import android.app.*;
import android.os.*;
import android.hardware.*;
import android.content.*;
import android.graphics.*;
import android.view.inputmethod.InputMethodManager;
import android.view.*;
import android.util.*;
import android.widget.*;
import android.text.*;

public class MainActivity extends Activity 
{
	Button rotateButton;
	MyBitmap myBitmap;
	EditText rotationAngle;
	TextView textIntro;
	MyDialogFragment dialogFragment;
	LinearLayout mainLayout;
	InputMethodManager imm;
	Float inputValue;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		myBitmap = (MyBitmap)findViewById(R.id.my_bitmap);
		rotateButton = (Button)findViewById(R.id.button_rotate);
		rotationAngle = (EditText)findViewById(R.id.edittext_degrees);
		textIntro = (TextView)findViewById(R.id.textview_intro);
		inputValue = new Float(-1);
		
	 	mainLayout = (LinearLayout)findViewById(R.id.layout_main);
		mainLayout.requestFocus();

		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
		
		rotateButton.setOnClickListener(new View.OnClickListener(){
			public void onClick(View v){
				
				dialogFragment = MyDialogFragment.newInstance("Invalid rotation angle'", "Please enter some value between 0° and 360°.");
				
				String inputString = rotationAngle.getText().toString();
				if(inputString.matches(""))
				{
					dialogFragment.show(getFragmentManager(), "nullValue");
					inputValue.valueOf(-1);
				} else {
					inputValue = Float.valueOf(inputString);
					if(inputValue < 0.0 || inputValue > 360.0)
					{
						dialogFragment.show(getFragmentManager(), "invalidValue");
						inputValue = Float.valueOf(-1);
					}
				}
				
				Toast.makeText(v.getContext(),""+inputValue, Toast.LENGTH_SHORT).show();
				myBitmap.angle = inputValue;
				/*if(inputValue == -1)
					myBitmap.angle = -1;
				else
				{
					imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),  0);//  mainLayout.getWindowToken(), 0)
					myBitmap.angle = inputValue;
					mainLayout.requestFocus();
				}*/
				
				//myBitmap.rotateText();
				
			}
			
		});
		
	
    }
}

	 
		 

