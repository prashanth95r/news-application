package com.mvsrnews;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Detailview extends Activity {

	String time, headlines, desc, image_name, postby;
	TextView headlines1, image_desc1, description, time1, postedby;
	ImageView image;
	byte[] image_data;
	ConnectivityManager cm;
	NetworkInfo activenet;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailview);

		cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		activenet = cm.getActiveNetworkInfo();

		headlines1 = (TextView) findViewById(R.id.headlines);
		// image_desc1 = (TextView) findViewById(R.id.image_desc);
		image = (ImageView) findViewById(R.id.imageView);
		time1 = (TextView) findViewById(R.id.date);
		description = (TextView) findViewById(R.id.desc);
		postedby = (TextView) findViewById(R.id.postby);

		image.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				if (activenet != null && activenet.isConnectedOrConnecting()) {
					Intent i = new Intent(getApplicationContext(),
							ImageDisplay.class);
					i.putExtra("imgname", image_name);
					startActivity(i);
				}
			}
		});
		
		Typeface tr=Typeface.createFromAsset(this.getAssets(),"TitilliumWeb-Regular.ttf");
		Typeface tb=Typeface.createFromAsset(this.getAssets(),"TitilliumWeb-SemiBold.ttf");
		Typeface ti=Typeface.createFromAsset(this.getAssets(),"TitilliumWeb-SemiBoldItalic.ttf");
		time1.setTypeface(ti);
		postedby.setTypeface(ti);
		description.setTypeface(tr);
		headlines1.setTypeface(tb);

		Bundle extras = getIntent().getExtras();

		if (extras != null) {	
			time = extras.getString("time");
			headlines = extras.getString("headlines");
			desc = extras.getString("desc");
			image_name = extras.getString("imagename");
			postby = extras.getString("postby");

			image_data = extras.getByteArray("image_byte");

		}

		if (image_data == null) {
			image.setImageResource(R.drawable.logo3);
		} else {
			Bitmap image_bitmap = BitmapFactory.decodeByteArray(image_data, 0,
					image_data.length);
			image.setImageBitmap(Bitmap.createScaledBitmap(image_bitmap, 500,
					350, false));

			image_bitmap.recycle();

		}

		time1.setText(time);
		headlines1.setText(headlines);
		description.setText(desc);
		postedby.setText("By " + postby);

	}

	@Override
	public void onBackPressed() {

		super.onBackPressed();
		this.finish();
	}

}
