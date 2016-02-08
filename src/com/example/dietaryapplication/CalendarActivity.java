package com.example.dietaryapplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.Toast;

public class CalendarActivity extends Activity {
	
	private CalendarView calendar;
	private Button btnNext;
	private User user;
	private Date date;
	private DataBaseHelper dbHelper;
	private SharedPreferences.Editor sharedPrefsEditor;
	private SharedPreferences sharedPrefs;
	//private String selectedDate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_calendar);
		
		user = getIntent().getParcelableExtra("user");
		
		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		
		sharedPrefsEditor = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();
		sharedPrefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
		
		initCalendar();
		initButtons();
		//dbQuery();
		
	}
	
	@SuppressLint("SimpleDateFormat")
	private void dbQuery(){
		dbHelper.openDataBase();
		ArrayList<DailyRecommendation> drList = new ArrayList<DailyRecommendation>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		drList = dbHelper.getDailyRecommendation(sdf.format(date = new Date(calendar.getDate())), 1);
		
		if(drList.size()>0){
			DailyRecommendation dr = drList.get(0);
			Log.i("DRLIST", "DR: "+ dr.getDate() + " "+ dr.getRowid());
		}else{
			Log.i("DRLIST", "No recommendation");
		}
			
		
		dbHelper.close();
	}
	
	
	private void initButtons(){
		
		btnNext = (Button)findViewById(R.id.btnNext);
		btnNext.setOnClickListener(new OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				date = new Date(calendar.getDate());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				sharedPrefsEditor.putString(Constants.SHARED_PREFS_DATE, sdf.format(date)).commit();
				
//				Toast.makeText(getApplicationContext(),"Date:  "+ sdf.format(date), Toast.LENGTH_SHORT).show();
//				dbHelper.openDataBase();
//				DailyRecommendation dailyRec = new DailyRecommendation(1, sdf.format(date), 1, 1, 1, 1, 1, 1, 1, 1, 1);
//				
//				long i =0;
//				i= dbHelper.insertDailyRecommendation(dailyRec);
//				Toast.makeText(getApplicationContext(),"INSERTDB:  "+ i, Toast.LENGTH_SHORT).show();
//				
//				dbHelper.close();
				Intent i = new Intent(CalendarActivity.this, BreakfastActivity.class);
				i.putExtra("user", user);
//				i.putExtra("date", sdf.format(date));
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(i);
				finish();
			}
		});
		
	}
	
	private void initCalendar(){
		calendar = (CalendarView)findViewById(R.id.calendarView1);
		
		
		calendar.setFirstDayOfWeek(1);
		
		//calendar.setSelectedWeekBackgroundColor(getResources().getColor(Color.GREEN));

		calendar.setOnDateChangeListener(new OnDateChangeListener() {
			
			@Override
			public void onSelectedDayChange(CalendarView view, int year, int month,
					int dayOfMonth) {
				// TODO Auto-generated method stub
//				Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_SHORT).show();
				//date = new GregorianCalendar(year, month, dayOfMonth);
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(CalendarActivity.this, HomeActivity.class);
		i.putExtra("user", user);i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}
	
	

}
