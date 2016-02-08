package com.example.dietaryapplication;

import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Login extends Activity{
	
	private Button enter, exit;
	private DataBaseHelper dbHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		/**public void proceedNext(View v)
		{
			Intent intent = new Intent(this, MainActivity.class);
		}**/
		enter = (Button) findViewById(R.id.btnEnter);
		exit = (Button)findViewById(R.id.btnExit);
		//display = (TextView) findViewById(R.id.tvDisplay)
		
		
		
		enter.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View mainActivityView) {
				// TODO Auto-generated method stub
				
//				Intent openMainActivity = new Intent("com.example.dietaryapplication.MAINACTIVITY");
//				startActivity(openMainActivity);
//			Intent i = new Intent(Login.this, MainActivity.class);
//			startActivity(i);
				Intent i = new Intent(Login.this, MapTestActivity.class);
				startActivity(i);
				
				
			}
		});
		
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				
			}
		});
		
		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		
		try {
			dbHelper.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbHelper.openDataBase();
		
	}

}
