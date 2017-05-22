package com.mvsrnews.background;



import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.WakefulBroadcastReceiver;

public class AlarmGenerator extends WakefulBroadcastReceiver{
	private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;
long start_time= 1000,repeat_time=1000*60;
	@Override
	public void onReceive(Context context, Intent intent) {
		
		Intent service = new Intent(context, QuizTimer.class);
        startWakefulService(context, service);
		
	}

	 public void setAlarm(Context context) {
	        alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
	        Intent intent = new Intent(context, AlarmGenerator.class);
	        alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

	      
	        
	        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,  
	               start_time,repeat_time, alarmIntent);
	        
	        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
	        PackageManager pm = context.getPackageManager();

	        pm.setComponentEnabledSetting(receiver,
	                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
	                PackageManager.DONT_KILL_APP);     
	 }
	 
	 public void cancelAlarm(Context context) {
	        if (alarmMgr!= null) {
	            alarmMgr.cancel(alarmIntent);
	        }
	        
	        ComponentName receiver = new ComponentName(context, SampleBootReceiver.class);
	        PackageManager pm = context.getPackageManager();

	        pm.setComponentEnabledSetting(receiver,
	                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
	                PackageManager.DONT_KILL_APP);
	    }
}
