package com.mvsrnews.background;

import java.util.ArrayList;
import java.util.List;

import mvsrnews.library.Database;
import mvsrnews.library.JSON_Data;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;


import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.StrictMode;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.util.Log;


import com.mvsrnews.MainActivityND;

public class QuizTimer extends IntentService {
	public QuizTimer() {
		super("Quiz");
	}

	SharedPreferences pref;
	Editor editor;
	ConnectivityManager cm;
	NetworkInfo activenet;
	Intent intent;
	public static final String BROADCAST_ACTION = "com.mvsrnews";

	long timeInMilliseconds = 0L;
	JSON_Data json_data;
	JSONObject jobj;
	List<BasicNameValuePair> params;
	Database db;
	Vibrator mVibrator;
	Uri soundUri;
	long[] pattern = { 300, 300 };
	NotificationManager mNotificationManager;

	@Override
	protected void onHandleIntent(Intent intent) {

		super.onCreate();
		db = new Database(getApplicationContext());
		cm = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		activenet = cm.getActiveNetworkInfo();

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		DisplayLoggingInfo();
		AlarmGenerator.completeWakefulIntent(intent);
	}

	private void DisplayLoggingInfo() {

		db = new Database(getApplicationContext());
		int notify_value;
		if (activenet != null && activenet.isConnectedOrConnecting()) {
			json_data = new JSON_Data();

			if (db.getnotifiedvalue() == 0)
				notify_value = db.toprow_id();
			else
				notify_value = db.getnotifiedvalue();

			Log.d("notify value", String.valueOf(notify_value));

			params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("tag", "notify"));
			params.add(new BasicNameValuePair("notifyval", String
					.valueOf(notify_value)));
			jobj = json_data.getjson(params);
			try {
				String top_headL = jobj.getString("head");
				String notifyval = jobj.getString("id");

				Log.d("top_head", top_headL);
				if (!top_headL.equals("false") && !top_headL.equals(null)) {
					sendNotification(top_headL);
					db.putnotifiedvalue(Integer.parseInt(notifyval));

				}
			}

			catch (Exception e) {
				Log.d("QuizTimer", "Exam Status Error");
			}
		} else {
			Log.d("No internet", "disp log info");
			// Toast.makeText(getApplicationContext(),"No Internet Connection",
			// Toast.LENGTH_SHORT).show();
		}

	}

	private void sendNotification(String msg) {
		Log.d("inside notifiation", msg);

		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				this).setSmallIcon(com.mvsrnews.R.drawable.logo3)
				.setContentTitle("MVSRNews").setContentText(msg);

		mNotificationManager = (NotificationManager) this
				.getSystemService(Context.NOTIFICATION_SERVICE);

		PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
				new Intent(this, MainActivityND.class), 0);

		mBuilder.setContentIntent(contentIntent);
		mNotificationManager.notify(11, mBuilder.build());
	}

	/*
	 * @Override public void onDestroy() { super.onDestroy(); // db.endExam();d
	 * }
	 */

}
