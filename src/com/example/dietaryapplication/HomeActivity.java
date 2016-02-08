package com.example.dietaryapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class HomeActivity extends Activity {

	private User user;
	private ImageButton imgBtnRecommendation;
	private ImageButton imgBtnMilestone;
	private ImageButton imgBtnReset;
	private DataBaseHelper dbHelper;
	private SharedPreferences.Editor sharedPrefsEditor;
	private SharedPreferences sharedPrefs;
//	AlarmReceiver alarm =  new AlarmReceiver();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.home);
		
		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		user = getIntent().getParcelableExtra("user");
		sharedPrefsEditor = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();
		sharedPrefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
//		alarm.setAlarm(HomeActivity.this);
		
		
		imgBtnRecommendation = (ImageButton)findViewById(R.id.btnRecommendation);
		imgBtnMilestone = (ImageButton)findViewById(R.id.btnMilestones);
		imgBtnReset = (ImageButton)findViewById(R.id.btnReset);
		
		imgBtnRecommendation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, CalendarActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
		
		imgBtnMilestone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(HomeActivity.this, MilesStoneActivity.class);
				intent.putExtra("user", user);
				startActivity(intent);
			}
		});
		
		imgBtnReset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				confirmDialog(v);
			}
		});
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent a = new Intent(Intent.ACTION_MAIN);
		a.addCategory(Intent.CATEGORY_HOME);
		a.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(a);
	}
	
	private void confirmDialog(View view){
		 AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(HomeActivity.this);
		 alertDialogBuilder.setTitle("Reset Everything");		
		 alertDialogBuilder.setMessage("Are you sure?");
		 
		 alertDialogBuilder.setCancelable(true);
		 alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				try {
					dbHelper.openDataBase();
					dbHelper.resetDB();
					dbHelper.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				Toast.makeText(getApplicationContext(), "Reset Success.", Toast.LENGTH_SHORT).show();
				sharedPrefsEditor.clear();
				sharedPrefsEditor.commit();
				Intent intent = new Intent(HomeActivity.this, SplashScreen.class);	
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
		 
		 alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//Toast.makeText(getApplicationContext(), "NO", Toast.LENGTH_SHORT).show();
			}
		});		 
		 
		 AlertDialog alertDialog = alertDialogBuilder.create();
         alertDialog.show();

	}
	
	 
}
