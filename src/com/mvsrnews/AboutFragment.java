package com.mvsrnews;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class AboutFragment extends android.support.v4.app.Fragment{
	ImageView iv;
	TextView tv;
	int count=0;
	
	public AboutFragment(){}
	
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	            Bundle savedInstanceState) {
		 
		 	View rootView = inflater.inflate(R.layout.credits, container, false);
	        
	        iv=(ImageView)rootView.findViewById(R.id.imageView1);
	        tv=(TextView)rootView.findViewById(R.id.textView1);
			iv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://mvsrec.edu.in/mpp/"));
					startActivity(browserIntent);
					
				}
			});
			
			tv.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					count++;
					if(count==6)
					{
						Toast.makeText(getActivity().getApplicationContext(), "Developed By\n"+"PRASHANTH\n"+"2451-12-733-135\n"+"and\n"+"UJWAL\n"+"2451-12-733-141", Toast.LENGTH_LONG).show();
						count=0;
					}
					
				}
			});
	          
	        return rootView;
	    }

}
