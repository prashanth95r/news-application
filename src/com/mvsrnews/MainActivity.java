package com.mvsrnews;

//4IyikEjM4NwmDkp3

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

import mvsrnews.library.Database;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
//import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.mvsrnews.R.drawable;
import com.mvsrnews.background.AlarmGenerator;

public class MainActivity extends android.support.v4.app.Fragment {

	LinearLayout.LayoutParams params;

	ProgressDialog pdialog;
	// String loginURL = "http://mvsrec.site40.net/newsapp/index1.php";
	//String loginURL = "http://www.mvsrec.edu.in/mvsrnews/index1.php";
	String loginURL=	"http://www.ouworld.net76.net/mvsrnews/index1.php";
	InputStream is = null;
	String db_key_string = String.valueOf(0);
	int Server_lastrow_id = 0;
	int key = 0;
	String json = "";
	int no_internet_flag = 1;
	Button button;

	int print_table = 0;
	int flag = 0;
	int g;
	SharedPreferences pref1;
	Editor editor1;
	CustomizeDialog mCustomizeDialog;
	AlarmGenerator ag=new AlarmGenerator();

	public MainActivity() {
		// TODO Auto-generated constructor stub
	}

	@Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_main);
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.activity_main, container,
				false);
		button = (Button) rootView.findViewById(R.id.button);
		Database db = new Database(MainActivity.this.getActivity());
		g = db.getRowCount();

		ConnectivityManager cm = (ConnectivityManager) getActivity()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activenet = cm.getActiveNetworkInfo();
		
		
		pref1 = getActivity().getSharedPreferences("MVSRQUIZ", 0);
		
		
		
		if (db.getRowCount() == 0) {
			flag = 0;
			Server_lastrow_id = -1;

		} else {
			flag = 0;
			Server_lastrow_id = db.toprow_id();

		}

		if (activenet != null && activenet.isConnectedOrConnecting()) {

			flag = 0;
			new newsload().execute(Server_lastrow_id, print_table);

		} else {

			Toast.makeText(getActivity().getApplicationContext(),
					"No Internet Connection", Toast.LENGTH_LONG).show();
			if (db.getRowCount() == 0) {

				Intent ujwal = new Intent(
						getActivity().getApplicationContext(), NoInternet.class);

				startActivity(ujwal);
				android.os.Process.killProcess(android.os.Process.myPid());

			}

			no_internet_flag = 0;
			flag = 0;

			new newsload().execute(0, print_table);

		}

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Database db = new Database(MainActivity.this.getActivity());
				if (db.getRowCount() == 0) {
					Toast.makeText(getActivity().getApplicationContext(),
							"No News", Toast.LENGTH_LONG).show();
				} else if (print_table < db.lastrow_id()) {
					Toast.makeText(getActivity().getApplicationContext(),
							"No More News", Toast.LENGTH_LONG).show();

				} else {
					no_internet_flag = 0;
					flag = 1;
					new newsload().execute(0, print_table);

				}

			}
		});
		return rootView;
	}

	
	void notification(){
		
	}
	
	private class newsload extends AsyncTask<Integer, Integer, Integer> {

		DialogFragment dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// mCustomizeDialog = new
			// CustomizeDialog(MainActivity.this.getActivity());
			// mCustomizeDialog.show();

			dialog = ProgressDialogFragment.newInstance();
			dialog.setCancelable(false);
			dialog.show(getFragmentManager(), "dialog");

			/*
			 * pdialog = new ProgressDialog(MainActivity.this.getActivity());
			 * 
			 * pdialog.setMessage("loading news...");
			 */

			/*
			 * pdialog.setOnCancelListener(new OnCancelListener() {
			 * 
			 * @Override public void onCancel(DialogInterface dialog) {
			 * MainActivity.this.finish();
			 * 
			 * } });
			 */

			/*
			 * pdialog.setCancelable(false);
			 * 
			 * pdialog.show();
			 */

		}

		@Override
		protected Integer doInBackground(Integer... arg) {

			db_key_string = String.valueOf(arg[0]);

			if (no_internet_flag == 1) {

				try {
					List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
					// params.add(new BasicNameValuePair("tag", "keyvalue"));
					params.add(new BasicNameValuePair("key", db_key_string));

					DefaultHttpClient httpClient = new DefaultHttpClient();
					HttpPost httpPost = new HttpPost(loginURL);
					httpPost.setEntity(new UrlEncodedFormEntity(params));

					HttpResponse httpResponse = httpClient.execute(httpPost);
					HttpEntity httpEntity = httpResponse.getEntity();
					is = httpEntity.getContent();

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				try {
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is, "iso-8859-1"), 8);
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {
						sb.append(line + "\n");
					}

					is.close();
					json = sb.toString();
					Log.d("json",json);
				} catch (Exception e) {
				}

				if (!json.equals("null")) {

					Database db = new Database(getActivity()
							.getApplicationContext());

					try {

						JSONArray jArray = new JSONArray(json);

						for (int i = 0; i < jArray.length(); i++) {

							Bitmap bitmap = null;
							InputStream iStream = null;
							JSONObject json_data = jArray.getJSONObject(i);
							byte[] b = null;
							final int server_id = json_data.getInt("id");

							final String time_text = json_data
									.getString("addtime");
							final String headlines = json_data
									.getString("headlines");
							final String desc = json_data.getString("desp");
							final String imgname = json_data
									.getString("imagename");
							final String postedby = json_data
									.getString("postedby");
							final String branch = json_data.getString("Branch");

							String temp = "";
			//				String imageurl = "http://www.mvsrec.edu.in/mvsrnews/upload/"
			//						+ imgname;
							
							String imageurl ="http://www.ouworld.net76.net/mvsrnews/upload/"+ imgname;

							try {

								URL url = new URL(imageurl);

								HttpURLConnection urlConnection = (HttpURLConnection) url
										.openConnection();

								url.openConnection().connect();

								iStream = urlConnection.getInputStream();
								bitmap = BitmapFactory.decodeStream(iStream);

								ByteArrayOutputStream baos = new ByteArrayOutputStream();
								bitmap.compress(Bitmap.CompressFormat.JPEG, 90,
										baos);
								b = baos.toByteArray();
								temp = Base64.encodeToString(b, Base64.DEFAULT);

							} catch (Exception e) {

								new Thread() {
									@Override
									public void run() {
										MainActivity.this.getActivity()
												.runOnUiThread(new Runnable() {
													@Override
													public void run() {
														Toast.makeText(
																getActivity()
																		.getApplicationContext(),
																"Exception while downloading url",
																Toast.LENGTH_SHORT)
																.show();
													}
												});
									}
								}.start();
								temp="image_error";
							}

							db.addUser(server_id, headlines, desc, time_text,
									imgname, temp, postedby, branch);
							if(bitmap!=null)
							bitmap.recycle();
							System.gc();

						}
						
						Database db_notify= new Database(getActivity());
						db_notify.putnotifiedvalue(0);
						
						g = db.getRowCount();

					} catch (JSONException e) {

						new Thread() {
							@Override
							public void run() {
								MainActivity.this.getActivity().runOnUiThread(
										new Runnable() {
											@Override
											public void run() {
												Toast.makeText(
														getActivity()
																.getApplicationContext(),
														"No News Updates.",
														Toast.LENGTH_SHORT)
														.show();
											}
										});
							}
						}.start();

					}

					System.gc();

					if (flag == 1) {
						return arg[1];

					}
					if (flag == 0) {

						return db.toprow_id();

					}
				}
			}
			if (json.equals("null")) {

				new Thread() {
					@Override
					public void run() {
						MainActivity.this.getActivity().runOnUiThread(
								new Runnable() {
									@Override
									public void run() {
										Toast.makeText(
												getActivity()
														.getApplicationContext(),
												"No News Updates",
												Toast.LENGTH_SHORT).show();
									}
								});
					}
				}.start();

			}

			if (flag == 1) {
				return arg[1];

			}
			if (flag == 0) {
				Database db = new Database(MainActivity.this.getActivity());
				return db.toprow_id();

			}
			return 5;

		}

		@Override
		protected void onPostExecute(Integer po) {
			//

			super.onPostExecute(po);

			LinearLayout ll11;
			startalarm();

			ll11 = (LinearLayout) getView().findViewById(R.id.linear);
			Database db = new Database(getActivity().getApplicationContext());
			LinearLayout ll[] = new LinearLayout[db.getRowCount()];
			final Intent p[] = new Intent[db.getRowCount()];

			int row_check = po - 7;
			if (row_check < (Server_lastrow_id - db.getRowCount())) {
				row_check = (Server_lastrow_id - db.getRowCount());

			}

			if (row_check < 0) {
				row_check = 0;
			}

			for (int pi = po; pi > row_check; pi--) {

				g--;

				String image_code = "";
				byte[] image_byte = null;
				Bitmap image_bitmap = null;

				final int k = g;
				final HashMap<String, String> user = db.getUserDetails(pi);
				GradientDrawable shape=new GradientDrawable();
				shape.setCornerRadius(10);
			
				Typeface tb=Typeface.createFromAsset(getActivity().getAssets(),"TitilliumWeb-SemiBold.ttf");
				Typeface ti=Typeface.createFromAsset(getActivity().getAssets(),"TitilliumWeb-SemiBoldItalic.ttf");

				try {
					if (user.get("imagebyte").equals("image_error")) {

						image_bitmap = BitmapFactory.decodeResource(
								getResources(), R.drawable.logo3);
						image_byte = null;
					} else {
						image_code = user.get("imagebyte");
						image_byte = Base64.decode(image_code, Base64.DEFAULT);
						image_bitmap = BitmapFactory.decodeByteArray(
								image_byte, 0, image_byte.length);
					}

					ll[g] = new LinearLayout(getActivity()
							.getApplicationContext());
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

					LinearLayout ll1 = new LinearLayout(getActivity()
							.getApplicationContext());

					params = new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT);
					params.weight = 1.0f;
					ll1.setLayoutParams(params);
					ll1.setOrientation(LinearLayout.VERTICAL);

					ll1.setBackgroundColor(Color.parseColor("#ffffff"));

					ll1.setPadding(2, 0, 2, 0);

					ImageView imageview = new ImageView(getActivity()
							.getApplicationContext());
					params = new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					params.gravity = Gravity.CENTER;

					imageview.setLayoutParams(params);
					if (user.get("imagebyte").equals("image_error")) {
						imageview.setImageResource(R.drawable.logo3);
					} else {
						imageview.setImageBitmap(Bitmap.createScaledBitmap(
								image_bitmap, 250, 250, false));
					}

					ll1.addView(imageview);

					LinearLayout ll2 = new LinearLayout(getActivity()
							.getApplicationContext());
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

					TextView tv = new TextView(getActivity()
							.getApplicationContext());
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

					TextView tv1 = new TextView(getActivity()
							.getApplicationContext());

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
					final View vline1 = new View(getActivity()
							.getApplicationContext());
					vline1.setLayoutParams(new TableRow.LayoutParams(
							LayoutParams.MATCH_PARENT, 10));
					vline1.setBackgroundColor(Color.parseColor("#EDEFF4"));
					ll11.addView(vline1);

					ll[g].addView(ll1);
					ll[g].addView(ll2);
					ll11.addView(ll[g]);
					button.setVisibility(0);

					if (image_bitmap != null)
						image_bitmap.recycle();
					System.gc();
					// print_table--;

					po--;
					print_table = po;
				} catch (ArrayIndexOutOfBoundsException e) {
				}
			}

			// mCustomizeDialog.dismiss();
			dialog.dismiss();

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
	
	
	
	void startalarm()
	{
		if (pref1.getString("firsttime", null) == null) {
			editor1 = pref1.edit();
			editor1.putString("firsttime", "notify");
			editor1.commit();
           ag.setAlarm(getActivity());           
      
		}
		
	}

}
