package com.mvsrnews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ImageDisplay extends Activity{
private WebView browser;
ProgressDialog pd;
Bundle bundle;
String URL="http://www.ouworld.net76.net/mvsrnews/cir/";
@Override		
protected void onCreate(Bundle savedInstanceState) {
   super.onCreate(savedInstanceState);
   setContentView(R.layout.image_display);
   browser = (WebView)findViewById(R.id.myWebView);
   browser.setWebViewClient(new MyBrowser());
   browser.getSettings().setLoadsImagesAutomatically(true);
   browser.getSettings().setJavaScriptEnabled(true);
	browser.getSettings().setBuiltInZoomControls(true);
   browser.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
   pd = new ProgressDialog(this);
   pd.setMessage("Loading image");
   pd.show();
   
  bundle=getIntent().getExtras();
  String imagename=bundle.getString("imgname");
  browser.loadUrl(URL+imagename);
}


private class MyBrowser extends WebViewClient {
   @Override
   public boolean shouldOverrideUrlLoading(WebView view, String url) {
      view.loadUrl(url);
      if (!pd.isShowing()) {
	        pd.show();
	    }
      return true;
   }
   
   
   @Override
   public void onPageFinished(WebView view, String url) { 
       if (pd.isShowing()) {
           pd.dismiss();
       }

   }
}

@Override
public boolean onCreateOptionsMenu(Menu menu) {
   // Inflate the menu; this adds items to the action bar if it is present.
   getMenuInflater().inflate(R.menu.main, menu);
   return true;
}


	

}
