package com.example.dietaryapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

public class Age extends Activity{
	
	private ImageButton btnConfirm;
	private EditText ageTF;
	private String age;
	//private ForAgeUser forAgeUser;
	private User user;
	
	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	        setContentView(R.layout.age);
	        
	        
			btnConfirm = (ImageButton)findViewById(R.id.btnConfirm);
			ageTF = (EditText)findViewById(R.id.etAge);
		
			user = getIntent().getParcelableExtra("user");
			btnConfirm.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					int ageInt = 0;
					try {
						age = ageTF.getText().toString();
						ageInt = Integer.parseInt(age);

					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
					
					if(!age.equals("") && ageInt<=60 && ageInt>=0) {
						//htInInches = (feet*12)+inches;
						//user = new User(name, age, gender, "", kilograms, htInInches, 0, 0);
						//forAgeUser = new ForAgeUser(age);
						//user = new User("", age, "", "", 0, 0, 0, 0);
						//user = new User("", age);
						
						user.setAge(age);
						Intent i = new Intent(Age.this, Gender.class);
						i.putExtra("user", user);
						startActivity(i);	
					}else{
						Toast.makeText(Age.this, "Age must be 0-60 years old.", Toast.LENGTH_SHORT).show();
					}
				}
			});
		


	    }


//		@Override
//	    public boolean onCreateOptionsMenu(Menu menu) {
//	        // Inflate the menu; this adds items to the action bar if it is present.
//	        getMenuInflater().inflate(R.menu.main, menu);
//	        return true;
//	    }
//
//	    @Override
//	    public boolean onOptionsItemSelected(MenuItem item) {
//	        // Handle action bar item clicks here. The action bar will
//	        // automatically handle clicks on the Home/Up button, so long
//	        // as you specify a parent activity in AndroidManifest.xml.
//	        int id = item.getItemId();
//	        if (id == R.id.action_settings) {
//	            return true;
//	        }
//	        return super.onOptionsItemSelected(item);
//	    }

}

