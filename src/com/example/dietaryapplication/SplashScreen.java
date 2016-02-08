package com.example.dietaryapplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;


public class SplashScreen extends Activity {
	
	private boolean backbtnPress;
	private static final int SPLASH_DURATION = 5000;
	private Handler myHandler;
	private DataBaseHelper dbHelper;
	private User user;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//	private AlarmManager alarmMgr;
//	private Object alarmIntent;
//	AlarmReceiver alarm =  new AlarmReceiver();
	
	private Intent notificationIntent;
	private PendingIntent pendingIntent;
	private SharedPreferences.Editor sharedPrefsEditor;
	private SharedPreferences sharedPrefs;
	public static final int dummyMin = 19;
	public static final int dummyHour = 13;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		//this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splashscreen);
		sharedPrefsEditor = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();
		sharedPrefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
//		alarm.setAlarm(SplashScreen.this);
		//dbHelper.openDataBase();
		//dbHelper.checkDataBase();
		
		
		myHandler = new Handler();
		
		myHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
				
				if(!backbtnPress)
				{
					
					if(user.getName()!=null){
						Log.v("USER", "name: " + user.getName());
						Log.v("USER", "age: " + user.getAge());
						Log.v("USER", "gender: " + user.getGender());
						Log.v("USER", "activityFactor: " + user.getActivityFactor());
						Log.v("USER", "height: " + user.getHeight());
						Log.v("USER", "weight: " + user.getWeight());
						Log.v("USER", "Normal: " + user.isNormal());
						Log.v("USER", "Cosntraints: " + user.getConstraints());
					}
					if(user.getName()!=null){
						Calendar cal = Calendar.getInstance();
						String dateStr = "";
						dateStr = sdf.format(new Date());
						Date d1 = new Date();
						Date d2 = new Date();
						try {
							d1 = sdf.parse(dateStr);
							d2 = sdf.parse(user.getUpdateDate());
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						long dif = (d1.getTime()-d2.getTime())/(24*60*60*1000);
						Log.i("DATEtest", "Difference: " +dif);
						
						if(dif>30){//number of days
							
							
							Intent intent = new Intent(SplashScreen.this, MainActivity.class);
							SplashScreen.this.startActivity(intent);
							intent.putExtra("user", user);
							startActivity(intent);
						}else{
							Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
							intent.putExtra("user", user);
							startActivity(intent);
						}
					}else{
						Intent intent = new Intent(SplashScreen.this, MainActivity.class);
						intent.putExtra("user", user);
						startActivity(intent);
					}
						
				}
				
			}
		}, SPLASH_DURATION);
		
	}
	
//	private void scheduleNotification(Notification notification, int delay) {
//		
//		// Set the alarm to start at approximately 6:00 a.m.
//		Calendar calendar = Calendar.getInstance();
//		calendar.setTimeInMillis(System.currentTimeMillis());
//		calendar.set(Calendar.HOUR_OF_DAY, 6);
//		
//		alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
//		Intent intent = new Intent(context, AlarmReceiver.class);
//		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//
//		// With setInexactRepeating(), you have to use one of the AlarmManager interval
//		// constants--in this case, AlarmManager.INTERVAL_DAY.
//		alarmMgr.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
//		        AlarmManager.INTERVAL_DAY, alarmIntent);
//
//        Intent notificationIntent = new Intent(this, NotificationPublisher.class);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
//        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
////
////        long futureInMillis = SystemClock.elapsedRealtime() + delay;
////        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
////        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
//    }
//
//    private Notification getNotification(String content) {
//        Notification.Builder builder = new Notification.Builder(this);
//        builder.setContentTitle("Scheduled Notification");
//        builder.setContentText(content);
//        builder.setSmallIcon(R.drawable.ic_launcher);
//        return builder.build();
//    }
	
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		backbtnPress = true;
		super.onBackPressed();
	}
	
	private void scheduleNotification(Notification notification, long delay) {

        notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);        
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        
       
        	Log.i("","pendingIntent:" +pendingIntent.getCreatorPackage());
        	
        	Date updateDate = new Date();
        	if (sharedPrefs.getBoolean("firstrun", true)) {
                // Do first run stuff here then set 'firstrun' as false
                // using the following line to edit/commit prefs
//            	scheduleNotification(getNotification("Meal Recommendations."), 1000);
        		
                sharedPrefsEditor.putBoolean("firstrun", false).commit();
            }
        	
        	
        	 Calendar calendar = Calendar.getInstance();
//	 	        calendar.setTimeInMillis(System.currentTimeMillis());
	 	        calendar.setTime(updateDate);
	 	        long time1=calendar.getTimeInMillis();
	 	        
	 	        Log.i("TIME", "time1: "+calendar.toString());
//	 	        calendar.set(Calendar.HOUR_OF_DAY, dummyHour);
//	 	        calendar.set(Calendar.MINUTE, dummyMin);
//	 	        calendar.add(Calendar.DAY_OF_MONTH, 30);
	 	    // Set the alarm's trigger time to 6:00 a.m.
	 	        
	 	        calendar.set(Calendar.HOUR_OF_DAY, 6);
	 	        
	 	       long time2=calendar.getTimeInMillis();
	 	       if(time1>time2){
	 	    	  Log.i("TIME", "time1 is greater ");	 	    	  
	 	    	  time2 = time2+86400000;
	 	       }else{
	 	    	  Log.i("TIME", "time2 is greater ");
	 	       }
	 	    	   
	 	        Log.i("TIME", "time2: "+calendar.toString());
	 	        long calTime = time2+delay;
//	 	        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//	 	        Log.i("TIME", "Ftime: "+futureInMillis);
	 	        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//	 	        alarmManager.cancel(pendingIntent);
//	 	        alarmManager.set(AlarmManager.RTC_WAKEUP, calTime, pendingIntent);
	 	        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calTime, AlarmManager.INTERVAL_DAY, pendingIntent);
//	 	        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,  
//	                 calTime, 
//	                 AlarmManager.INTERVAL_DAY, 
//	                 pendingIntent);
      	        
       
    }
	
	
	
	

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("HappyFit");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_happy_fit);
        builder.setAutoCancel(true);
        Intent resultIntent = new Intent(this, BreakfastActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        resultIntent.putExtra("Broadcast", true);
        resultIntent.putExtra("user", user);
	     // Adds the back stack for the Intent (but not the Intent itself)
	    stackBuilder.addParentStack(HomeActivity.class);
	     // Adds the Intent that starts the Activity to the top of the stack
	    stackBuilder.addNextIntent(resultIntent);
	    PendingIntent resultPendingIntent =
	             stackBuilder.getPendingIntent(
	                 0,
	                 PendingIntent.FLAG_UPDATE_CURRENT
	             );
	    builder.setContentIntent(resultPendingIntent);
        return builder.build();       
        
    }
    
    

    @Override
    protected void onResume() {
        super.onResume();
        
        dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		
		
		
		
		try {
			
			
			dbHelper.createDataBase();
			dbHelper.openDataBase();
			user = dbHelper.userQuery();						
			
			
			dbHelper.close();
		} catch (Exception e) {
			// TODO: handle exception			
			e.printStackTrace();
		}
		
        if (sharedPrefs.getBoolean("firstrun", true)) {
            // Do first run stuff here then set 'firstrun' as false
            // using the following line to edit/commit prefs
        	scheduleNotification(getNotification("Meal Recommendations."), 1000);

//            sharedPrefsEditor.putBoolean("firstrun", false).commit();
        }

    }

}
