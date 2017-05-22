package com.mvsrnews;

import java.util.HashMap;
import java.util.StringTokenizer;

import com.mvsrnews.R.drawable;

import mvsrnews.library.Database;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class BranchNews extends android.support.v4.app.Fragment{
	Database db;
	String branch1;
	LinearLayout.LayoutParams params;
	Button button;
	int Server_lastrow_id = 0;
	int print_table = 0;
	int g;
	int rowId[];
	 ProgressDialog pdialog;
	View rootView;
	
	public BranchNews(){}

	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
		 Database db = new Database(getActivity().getApplicationContext());
	  
	        rootView = inflater.inflate(R.layout.activity_main, container, false);
	        button=(Button)rootView.findViewById(R.id.button);
	       branch1= getArguments().getString("branch1");												
	       pdialog=new ProgressDialog(getActivity());
	        pdialog.show();
	       
			g=db.getRowCount();
			
	       print(db.toprow_id(),rootView);  
	       
	       pdialog.cancel();
	       
	       
	        
	       button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					Database db = new Database(BranchNews.this.getActivity());
					if (db.getRowCount() == 0) {
						Toast.makeText(getActivity().getApplicationContext(), "No News",
								Toast.LENGTH_LONG).show();
					} else if (print_table < db.lastrow_id()) {
						Toast.makeText(getActivity().getApplicationContext(), "No More News",
								Toast.LENGTH_LONG).show();
					} 
					else {
						print(print_table,rootView);
						pdialog.show();

					}

				}
			});
	          
	        return rootView;
	    }
	 
	 
	 void print(int po,View rootView)
	 {
	
	 LinearLayout ll11;

		ll11 = (LinearLayout)rootView.findViewById(R.id.linear);
		Database db = new Database(getActivity().getApplicationContext());
		LinearLayout ll[] = new LinearLayout[db.getRowCount()];
		final Intent p[] = new Intent[db.getRowCount()];

	/*	int row_check = po - 7;
		if (row_check < (db.toprow_id() - db.getRowCount())) {
			row_check = (db.toprow_id() - db.getRowCount());

		}

		if (row_check < 0){
			row_check=0;
		}
		
		
		
		for (int pi = po; pi > row_check; pi--) {
			Log.d("row check",String.valueOf(row_check));
	  
	  */
	 
		
		int topid=db.toprow_id();
		int lastid=db.lastrow_id();
		for(int i=topid;i>=lastid;i--)
		{
			final HashMap<String, String> user = db.getUserDetails(i);			//pi
			if(user.get("branch").equalsIgnoreCase(branch1))
			{
				g--;

				String image_code = "";
				byte[] image_byte = null;
				Bitmap image_bitmap = null;

				final int k = g;
				GradientDrawable shape=new GradientDrawable();
				shape.setCornerRadius(10);
			
				Typeface tb=Typeface.createFromAsset(getActivity().getAssets(),"TitilliumWeb-SemiBold.ttf");
				Typeface ti=Typeface.createFromAsset(getActivity().getAssets(),"TitilliumWeb-SemiBoldItalic.ttf");
				

				try {

					if(user.get("imagebyte").equals("image_error")){
						
						image_bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.logo3);
						image_byte=null;
					}
					else{
					image_code = user.get("imagebyte");
					image_byte = Base64.decode(image_code, Base64.DEFAULT);
					image_bitmap = BitmapFactory.decodeByteArray(
							image_byte, 0, image_byte.length);
					}

					ll[g] = new LinearLayout(getActivity().getApplicationContext());
					ll[g].setLayoutParams(new LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT)); //
					ll[g].setBackgroundResource(drawable.shadows);
					ll[g].setOrientation(LinearLayout.HORIZONTAL);
					//
					ll[g].setWeightSum(3.0f);

					p[k] = new Intent(getActivity().getApplicationContext(),
							Detailview.class);
					p[k].putExtra("time", textmodify(user.get("time")));
					p[k].putExtra("headlines", user.get("headlines"));
					p[k].putExtra("desc", user.get("desc"));
					p[k].putExtra("postby", user.get("postby"));

					p[k].putExtra("image_byte", image_byte);

					p[k].putExtra("imagename", user.get("image"));
					//

					ll[g].setOnClickListener(new View.OnClickListener() {

						@Override
						public void onClick(View arg0) {

							startActivity(p[k]);

						}
					});

					LinearLayout ll1 = new LinearLayout(
							getActivity().getApplicationContext());

					params = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					params.weight = 1.0f;
					ll1.setLayoutParams(params);
					ll1.setOrientation(LinearLayout.VERTICAL);

					ll1.setBackgroundColor(Color.parseColor("#ffffff"));

					ll1.setPadding(2, 0, 2, 0);

					ImageView imageview = new ImageView(
							getActivity().getApplicationContext());
					params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.gravity = Gravity.CENTER;

					imageview.setLayoutParams(params);
					if(user.get("imagebyte").equals("image_error")){
						imageview.setImageResource(R.drawable.logo3);
					}else{
					imageview.setImageBitmap(Bitmap.createScaledBitmap(
							image_bitmap, 250, 250, false));
					}

					ll1.addView(imageview);

					LinearLayout ll2 = new LinearLayout(
							getActivity().getApplicationContext());
					params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.MATCH_PARENT);
					//
					params.weight = 2.0f;

					ll2.setLayoutParams(params);
					ll2.setBackgroundColor(Color.parseColor("#ffffff"));
					ll2.setOrientation(LinearLayout.VERTICAL);
					ll2.setPadding(3, 2, 2, 2);
					ll2.setWeightSum(5.0f);
					
					TextView tv = new TextView(getActivity().getApplicationContext());
					tv.setTypeface(tb);
					params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.weight = 4.0f;

					tv.setLayoutParams(params);
					tv.setText(user.get("headlines"));
					tv.setTextColor(Color.BLACK);
					tv.setTextSize(15);
					tv.setPadding(3, 0, 2, 0);
					tv.setGravity(Gravity.CENTER_VERTICAL);

					ll2.addView(tv);

					TextView tv1 = new TextView(getActivity().getApplicationContext());

					params = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					params.weight = 1.0f;
					params.gravity = Gravity.BOTTOM;

					tv1.setLayoutParams(params);
					tv1.setTextColor(Color.parseColor("#666666"));
					tv1.setTypeface(ti);
					tv1.setText(textmodify(user.get("time")));
					tv1.setTextSize(10);

					ll2.addView(tv1);
					final View vline1 = new View(getActivity().getApplicationContext());
					vline1.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.MATCH_PARENT, 10));
					vline1.setBackgroundColor(Color.parseColor("#EDEFF4"));
					ll11.addView(vline1);

					ll[g].addView(ll1);
					ll[g].addView(ll2);
					ll11.addView(ll[g]);
		//			button.setVisibility(0);
					if (image_bitmap != null)
						image_bitmap.recycle();
					System.gc();
					// print_table--;

					
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}
			po--;
			print_table = po;
		}
		

	}



String textmodify(String time) {
	String date = "";
	String Time = "";
	String year = "";
	String month = "";
	String day = "";
	String hours = "";
	String minutes = "";
	String midday = "";

	StringTokenizer st = new StringTokenizer(time, " ");
	date = st.nextToken();
	Time = st.nextToken();

	StringTokenizer date_st = new StringTokenizer(date, "-");
	year = date_st.nextToken();
	month = date_st.nextToken();
	day = date_st.nextToken();

	StringTokenizer Time1_st = new StringTokenizer(Time, ":");
	hours = Time1_st.nextToken();
	minutes = Time1_st.nextToken();
	HashMap<String, String> hm = new HashMap<String, String>();
	hm.put("01", "Jan");
	hm.put("02", "Feb");
	hm.put("03", "Mar");
	hm.put("04", "Apr");
	hm.put("05", "May");
	hm.put("06", "Jun");
	hm.put("07", "Jul");
	hm.put("08", "Aug");
	hm.put("09", "Sep");
	hm.put("10", "Oct");
	hm.put("11", "Nov");
	hm.put("12", "Dec");
	int hours_int = Integer.parseInt(hours);
	if (hours_int > 12) {
		midday = "pm";
		hours_int = hours_int - 12;
		hours = String.valueOf(hours_int);

	} else {
		midday = "am";
	}

	return hours + ":" + minutes + midday + " " + day + " " + hm.get(month)
			+ "," + year;
}

	
}
