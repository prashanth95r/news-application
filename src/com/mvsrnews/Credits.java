package com.mvsrnews;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Credits extends Activity{
 ImageView iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.credits);
		iv=(ImageView)findViewById(R.id.imageView1);
		iv.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mvsrec.edu.in/mpp/"));
				startActivity(browserIntent);
				
			}
		});
		
	}

	@Override
	public void onBackPressed() {
	
		super.onBackPressed();
		this.finish();
		
	}
	
	
	

}
