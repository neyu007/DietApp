package com.example.dietaryapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.app.Activity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class WeightHeight extends Activity implements View.OnClickListener{
	
	private ImageButton ibtnConfirm;
	private EditText etWeight, etInches, etFeet;
	ImageButton btnkilograms;
	private ImageButton btnpounds;
	public double weight, finalweight;
	private int feet, inches, finalheight;
	private User user;	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.weight_height);
		
		ibtnConfirm = (ImageButton)findViewById(R.id.ibtnConfirm);
		etWeight = (EditText)findViewById(R.id.weightTF);
		etInches = (EditText)findViewById(R.id.inchesTF);
		etFeet = (EditText)findViewById(R.id.feetTF);
		btnkilograms = (ImageButton)findViewById(R.id.btnkilograms);
		btnpounds = (ImageButton)findViewById(R.id.btnpounds);
		
		btnkilograms.setOnClickListener(this);
		btnpounds.setOnClickListener(this);
		ibtnConfirm.setOnClickListener(this);
		
		user = getIntent().getParcelableExtra("user");		
	}
		
	//public void onClick(View v){
	//	switch(v.getId()){
	//		case R.id.btnkilograms:
	//			unit = "kilograms";
	//			break;
	//		case R.id.btnpounds:
	//			unit = "pounds";
	//			break;
	//	}
	//}
	


	public void onClick(View v) {
		// TODO Auto-generated method stub
		
			
		try {
			
			weight = Double.parseDouble(etWeight.getText().toString());
			feet = Integer.parseInt(etFeet.getText().toString());
			inches = Integer.parseInt(etInches.getText().toString());
			
			switch(v.getId()){
			case R.id.btnkilograms:
				//unit = "kilograms";
				finalweight = weight;
				break;
			case R.id.btnpounds:
				//unit = "pounds";
				finalweight = weight / 2.2;
				break;
			case R.id.ibtnConfirm:
				finalweight=weight; //ADDED
				Log.i("WEIGHT", "Weight: "+ weight);
				if(inches<12 && inches >= 0){
					finalheight = (feet*12)+inches;
					user.setHeight(finalheight);
					user.setWeight(finalweight);
					if(finalweight>0 && finalweight<80){
						Intent i = new Intent(WeightHeight.this, Constraints.class);
						i.putExtra("user", user);
						startActivity(i);
					}else{
						Toast.makeText(WeightHeight.this, "Weight must be 0 to 79 kgs.", Toast.LENGTH_SHORT).show();
					}	
				}
				else{
					Toast.makeText(WeightHeight.this, "Invalid height input.", Toast.LENGTH_SHORT).show();
				}
				break;
			}				
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	
}
