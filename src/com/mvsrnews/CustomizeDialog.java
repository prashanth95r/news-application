package com.mvsrnews;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ProgressBar;

public class CustomizeDialog extends Dialog {
	Context mContext;
	View v = null;
	ProgressBar progressBar;

	public CustomizeDialog(Context context) {
		super(context);
		  mContext = context;
		    /** 'Window.FEATURE_NO_TITLE' - Used to hide the mTitle */
		    requestWindowFeature(Window.FEATURE_NO_TITLE);
		    /** Design the dialog in main.xml file */
		    setContentView(R.layout.progress_dialog);
		    v = getWindow().getDecorView();
		    v.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
		    v.setBackgroundResource(android.R.color.transparent);
		    progressBar = (ProgressBar) findViewById(R.id.progressBar1);
	}
	
	

}
