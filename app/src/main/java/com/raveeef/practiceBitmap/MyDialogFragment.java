package com.raveeef.practiceBitmap;

import com.raveeef.practiceBitmap.MainActivity;
import android.app.*;
import android.os.*;
import android.hardware.*;
import android.content.*;
import android.graphics.*;
import android.view.*;
import android.util.*;
import android.widget.*;

public class MyDialogFragment extends DialogFragment
{
		public MyDialogFragment() {
			// Empty constructor required for DialogFragment
		}
		
		public static MyDialogFragment newInstance(String title, String message){
			MyDialogFragment fragment = new MyDialogFragment();
			Bundle args = new Bundle();
			args.putString("title", title);
			args.putString("message", message);
			
			fragment.setArguments(args);
			return fragment;
		}
		
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			String title = getArguments().getString("title");
			String message = getArguments().getString("message");
			AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
			alertDialogBuilder.setTitle(title);
			alertDialogBuilder.setMessage(message);
			
			alertDialogBuilder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			return alertDialogBuilder.create();
		}
		

	@Override
	public void onDismiss(DialogInterface dialog){
		MyButton mButton = (MyButton)((MainActivity)getContext()).findViewById(R.id.mybutton_rotate);
		mButton.afterDialog();
	}
}
