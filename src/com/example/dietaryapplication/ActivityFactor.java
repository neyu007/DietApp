package com.example.dietaryapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class ActivityFactor extends Activity {

	private RadioGroup rgPhysicalActivity;
	private User user;
	private ImageButton btnnConfirm; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_factor);
		
		user = getIntent().getParcelableExtra("user");
		rgPhysicalActivity = (RadioGroup)findViewById(R.id.radioGroupPhysicalActivity);
		btnnConfirm = (ImageButton)findViewById(R.id.btnConfirmActivityFactor);
		btnnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				double dbw = 0, tea=0, teaCurrent =0;
				dbw = DietaryHelper.DBWinKGS(Integer.parseInt(user.getAge()), user.getGender(), (int)user.getHeight());
				
				if(rgPhysicalActivity.getCheckedRadioButtonId()==R.id.RBbedRest){
					
					user.setActivityFactor(27.5);	
					
				}else if(rgPhysicalActivity.getCheckedRadioButtonId()==R.id.RBveryLightActive){
					
					user.setActivityFactor(30);
					
				}else if(rgPhysicalActivity.getCheckedRadioButtonId()==R.id.RBlightActive){
					
					user.setActivityFactor(35);
					
				}else if(rgPhysicalActivity.getCheckedRadioButtonId()==R.id.RBmoderateActive){
					
					user.setActivityFactor(40);
					
				}else if(rgPhysicalActivity.getCheckedRadioButtonId()==R.id.RBheavyActive){
					
					user.setActivityFactor(45);
					
				}
				
				tea = DietaryHelper.TEA(dbw, user.getActivityFactor());	
				teaCurrent = DietaryHelper.TEAcurrent(user.getWeight(), user.getActivityFactor());
				user.setTEACurrent(teaCurrent);
				user.setTEA(tea);
				user.setDBW(dbw);
				
				Intent i = new Intent(ActivityFactor.this, UserDetailsActivity.class);
				i.putExtra("user", user);
				startActivity(i);
				
			}
		});
		
		
	}
	
	
	

}
