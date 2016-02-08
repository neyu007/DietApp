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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Gender extends Activity {
	
	private ImageButton ibtnConfirm;
	private RadioButton rbtnMale, rbtnFemale;
	private String gender;
	private User user;
	//private ForGenderUser forGenderUser;
	private RadioGroup genderGroup;
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.gender);
        
        
		ibtnConfirm = (ImageButton)findViewById(R.id.imageButton1);
		rbtnMale = (RadioButton)findViewById(R.id.rbtnMale);
		rbtnFemale = (RadioButton)findViewById(R.id.rbtnFemale);
		genderGroup = (RadioGroup)findViewById(R.id.genderGroup);
	
		user = getIntent().getParcelableExtra("user");
		
		ibtnConfirm.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			
				try {
					gender = genderGroup.getCheckedRadioButtonId()==R.id.rbtnMale?"male":"female";

				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				
					//forGenderUser = new ForGenderUser(gender);
					user.setGender(gender);
				
					Intent i = new Intent(Gender.this, WeightHeight.class);
					i.putExtra("user", user);
					startActivity(i);	
				
					//ssToast.makeText(Gender.this, "Invalid input", Toast.LENGTH_SHORT).show();
				
			}
		});
	


    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
