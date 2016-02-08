package com.example.dietaryapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RadioGroup;

public class Constraints extends Activity {

	private User user;
	private RadioGroup constraintsRG;
	
	private ImageButton btnConfirm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.constraints_layout);
		
		user = getIntent().getParcelableExtra("user");
		
		constraintsRG = (RadioGroup)findViewById(R.id.radioGroupConstraints);
		
		btnConfirm = (ImageButton)findViewById(R.id.btnConfirmConstraints);
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(constraintsRG.getCheckedRadioButtonId()==R.id.radioNone){
					user.setConstraints("None");
					user.setNormal(true);
				}else if(constraintsRG.getCheckedRadioButtonId()==R.id.radioDiabetes){
					user.setConstraints("Diabetes");
					user.setNormal(false);
				}else if(constraintsRG.getCheckedRadioButtonId()==R.id.radioHighblood){
					user.setConstraints("Highblood");
					user.setNormal(false);
				}
				
				Intent i = new Intent(Constraints.this, ActivityFactor.class);
				i.putExtra("user", user);
				startActivity(i);
				
			}
		});
		
		
	
	}
	

}
