package com.example.dietaryapplication;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity {
	
	private ImageButton btnConfirm;
	private EditText nameTF;
	private String name;//, age, gender;
	//private double pounds, kilograms, centimeters;
	//private int feet, inches, htInInches;
	//private RadioGroup genderGroup;
	//private EditText etPounds, etKilograms, etCentimeters, etFeet, etInches, etName, etAge;
	private User user;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.activity_main);
        
        
		btnConfirm = (ImageButton)findViewById(R.id.btnConfirm);
		//genderGroup = (RadioGroup)findViewById(R.id.genderGroup);
		//etPounds = (EditText)findViewById(R.id.poundsTF);
		//etKilograms = (EditText)findViewById(R.id.kilogramsTF);
		//etCentimeters = (EditText)findViewById(R.id.centimetersTF);
		//etFeet = (EditText)findViewById(R.id.feetTF);
		//etInches = (EditText)findViewById(R.id.inchesTF);
		nameTF = (EditText)findViewById(R.id.etName);
		//etAge = (EditText)findViewById(R.id.ageTF);
		
		btnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				try {
					name = nameTF.getText().toString();
		//			age = etAge.getText().toString();
			//		gender = genderGroup.getCheckedRadioButtonId()==R.id.radio0?"male":"female";
//					pounds = Double.parseDouble(etPounds.getText().toString());
				//	kilograms = Double.parseDouble(etKilograms.getText().toString());
					//feet = Integer.parseInt(etFeet.getText().toString());
					//inches = Integer.parseInt(etInches.getText().toString());
//					centimeters = Double.parseDouble(etCentimeters.getText().toString());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
				if(!name.equals("")) {//|| !age.equals("") || kilograms!=0 || inches<12){
					//htInInches = (feet*12)+inches;
					//user = new User(name, "", "", "", kilograms, htInInches, 0, 0);
					//user = new User(name);
					user = new User();
					user.setName(name);
					
					Intent i = new Intent(MainActivity.this, Age.class);
					i.putExtra("user", user);
					startActivity(i);	
				}else{
					Toast.makeText(MainActivity.this, "No input", Toast.LENGTH_SHORT).show();
				}
			}
		});
	
    }


//   	@Override
//       public boolean onCreateOptionsMenu(Menu menu) {
//           // Inflate the menu; this adds items to the action bar if it is present.
//           getMenuInflater().inflate(R.menu.main, menu);
//           return true;
//       }
//
//       @Override
//       public boolean onOptionsItemSelected(MenuItem item) {
//           // Handle action bar item clicks here. The action bar will
//           // automatically handle clicks on the Home/Up button, so long
//           // as you specify a parent activity in AndroidManifest.xml.
//           int id = item.getItemId();
//           if (id == R.id.action_settings) {
//               return true;
//           }
//           return super.onOptionsItemSelected(item);
//       }
   }

