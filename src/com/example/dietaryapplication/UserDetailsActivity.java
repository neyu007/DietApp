package com.example.dietaryapplication;

import java.io.IOException;
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
import android.util.Log;
import android.view.View;
import android.view.WindowInsets;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class UserDetailsActivity extends Activity {
	
	private User user;
	private TextView nameTV, ageTV, genderTV, weightTV, heightTV; 
	private TextView physicalActivityTV, constraintsTV, dbwTV, teaTV, teaTargetTV;
	private String physAct = "";
	private ImageButton buttonRecommendation;
	private DataBaseHelper dbHelper;
	private long id=0;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Intent notificationIntent;
	private PendingIntent pendingIntent;
	private SharedPreferences.Editor sharedPrefsEditor;
	private SharedPreferences sharedPrefs;
	private Button btnHome;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_details);
		
		
		sharedPrefsEditor = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();
		sharedPrefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
		nameTV = (TextView)findViewById(R.id.textViewName);
		ageTV = (TextView)findViewById(R.id.TextViewAge);
		genderTV = (TextView)findViewById(R.id.TextViewGender);
		weightTV = (TextView)findViewById(R.id.TextViewWeights);
		heightTV = (TextView)findViewById(R.id.TextViewHeights);
		physicalActivityTV = (TextView)findViewById(R.id.TextViewPhysicalActivity);
		constraintsTV = (TextView)findViewById(R.id.TextViewConstraints); 
		dbwTV = (TextView)findViewById(R.id.TextViewDBW);
		teaTV = (TextView)findViewById(R.id.TextViewTEAcurrent);
		teaTargetTV = (TextView)findViewById(R.id.TextViewTEATarget);
		btnHome = (Button)findViewById(R.id.btnHome);
		buttonRecommendation = (ImageButton)findViewById(R.id.button_recommendation);
		
		
		user = getIntent().getParcelableExtra("user");
		
		if(user.getActivityFactor()==27.5){
			physAct= "Bed Rest";
		}else if(user.getActivityFactor()==30){
			physAct= "Very Light Active";
		}else if(user.getActivityFactor()==35){
			physAct= "Light Active";
		}else if(user.getActivityFactor()==40){
			physAct= "Moderate Active";
		}else if(user.getActivityFactor()==45){
			physAct= "Heavy Active";
		}
		
		initDetails();
		
		scheduleNotification2(getNotification2("Update your details"), 2000);
		
		buttonRecommendation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
//				Intent i= new Intent(UserDetailsActivity.this, RecommendActivity.class);
				Intent i= new Intent(UserDetailsActivity.this, CalendarActivity.class);
				i.putExtra("user", user);
				startActivity(i);
				finish();
			}
		});
		
		btnHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i= new Intent(UserDetailsActivity.this, HomeActivity.class);
				i.putExtra("user", user);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
			}
		});
		
	}	
	
	private void initDetails(){

		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		long udid = 0;
		try {
			dbHelper.createDataBase();
			dbHelper.openDataBase();
			
			 id=dbHelper.insertRecordUser(user.getName(), user.getGender());
			 udid = dbHelper.insertUserDetails(id, Integer.parseInt(user.getAge()), 
					user.getConstraints(), user.getHeight(), 
					user.getWeight(), user.isNormal()?1:0, 
					user.getActivityFactor(), user.getDBW(), 
					user.getTEA(), user.getTEACurrent());
			 Log.i("USER ID", "ID:" +id);
			 Log.i("USER DeTAILS", "OUTPUT:" +udid);
			 user.setId(id);
			
			dbHelper.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		int ft=0, in =0;
		ft = (int)user.getHeight()/12;
		in = (int)user.getHeight()%12;
		nameTV.setText(user.getName());
		ageTV.setText(user.getAge());
		genderTV.setText(user.getGender());
		weightTV.setText(""+user.getWeight()+ " kgs");
		heightTV.setText(ft+" ft and "+ in + " inches");
		physicalActivityTV.setText(physAct);
		constraintsTV.setText(user.getConstraints());
		dbwTV.setText(""+(int)user.getDBW()+ " kgs");
		teaTV.setText(""+(int)user.getTEACurrent() + " kcal/day");
		teaTargetTV.setText(""+(int)user.getTEA()  + " kcal/day");

		
		
	}
	
	private Notification getNotification2(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("HappyFit");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_happy_fit);
        builder.setAutoCancel(true);
        Intent resultIntent = new Intent(this, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
	     // Adds the back stack for the Intent (but not the Intent itself)
	    stackBuilder.addParentStack(MainActivity.class);
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
	
	private void scheduleNotification2(Notification notification, long delay) {

        notificationIntent = new Intent(this, NotificationPublisher.class);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, 2);
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification);
        pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_ONE_SHOT);
        
       
        	Log.i("UDschedNotifs","pendingIntent:" +pendingIntent.getCreatorPackage());
        	
        	Date updateDate = new Date();
//        	if (sharedPrefs.getBoolean("firstrun", true)) {
//                // Do first run stuff here then set 'firstrun' as false
//                // using the following line to edit/commit prefs
////            	scheduleNotification(getNotification("Meal Recommendations."), 1000);
//        		
//                sharedPrefsEditor.putBoolean("firstrun", false).commit();
//            }else{
//            	try {
//            		
//    				updateDate = sdf.parse(user.getUpdateDate());				
//    				updateDate.getTime();
//    			} catch (ParseException e) {
//    				// TODO Auto-generated catch block
//    				e.printStackTrace();
//    			}

            	
            	Calendar calendar = Calendar.getInstance();
	 	        calendar.setTimeInMillis(System.currentTimeMillis());
//	 	        calendar.setTime(updateDate);
	 	        long time1=calendar.getTimeInMillis();
	 	        // Set the alarm's trigger time to 6:00 a.m.
	 	        Log.i("UDTIME", "time1: "+calendar.toString());
//	 	        calendar.set(Calendar.HOUR_OF_DAY, SplashScreen.dummyHour);
//	 	        calendar.set(Calendar.MINUTE, SplashScreen.dummyMin);
	 	        calendar.add(Calendar.DAY_OF_MONTH, 30);
	 	       long time2=calendar.getTimeInMillis();
	 	       if(time1>time2){
	 	    	  Log.i("UDTIME", "time1 is greater ");	 	    	  
//	 	    	  time2 = time2+86400000;
	 	       }else{
	 	    	  Log.i("UDTIME", "time2 is greater ");
	 	       }
	 	    	   
	 	        Log.i("UDTIME", "time2: "+calendar.toString());
	 	        long calTime = time2+delay;
//	 	        long futureInMillis = SystemClock.elapsedRealtime() + delay;
//	 	        Log.i("TIME", "Ftime: "+futureInMillis);
	 	        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
//	 	        alarmManager.cancel(pendingIntent);
	 	        alarmManager.set(AlarmManager.RTC, calTime, pendingIntent);
//	 	        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP,  
//	                 calTime, 
//	                 AlarmManager.INTERVAL_DAY, 
//	                 pendingIntent);
      	        
            }
        	
        	
    
	

}
