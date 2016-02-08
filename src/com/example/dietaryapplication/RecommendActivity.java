package com.example.dietaryapplication;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecommendActivity extends Activity {
	
	private User user;
	private DataBaseHelper dbHelper;
	private Button btnHome, btnExit;
	private TEA tea;
	private TextView tvVeg, tvVegServe, tvFruit, tvFruitServe, tvMilk, tvMilkServe, tvRice, tvRiceServe;
	private TextView tvVegLunch, tvVegServeLunch, tvFruitLunch, tvFruitServeLunch;
	private TextView tvMilkLunch, tvMilkServeLunch, tvRiceLunch, tvRiceServeLunch;
	private TextView tvVegDinner, tvVegServeDinner, tvFruitDinner, tvFruitServeDinner;
	private TextView tvMilkDinner, tvMilkServeDinner, tvRiceDinner, tvRiceServeDinner;
	private TextView tvMeat, tvMeatServe, tvSugar, tvSugarServe, tvOil, tvOilServe, tvEgg, tvEggServe;
	private TextView tvMeatLunch, tvMeatServeLunch, tvSugarLunch, tvSugarServeLunch;
	private TextView tvOilLunch, tvOilServeLunch, tvEggLunch, tvEggServeLunch;
	private TextView tvMeatDinner, tvMeatServeDinner, tvSugarDinner, tvSugarServeDinner;
	private TextView tvOilDinner, tvOilServeDinner, tvEggDinner, tvEggServeDinner;
	private TextView tvRec1,tvRecVeg;
	private LinearLayout vegeLayout, fruitLayout, milkLayout, riceLayout, meatLayout, sugarLayout, oilLayout, eggLayout;
	private LinearLayout vegeLayoutLunch, fruitLayoutLunch, milkLayoutLunch, riceLayoutLunch;
	private LinearLayout meatLayoutLunch, sugarLayoutLunch, oilLayoutLunch, eggLayoutLunch;
	private LinearLayout vegeLayoutDinner, fruitLayoutDinner, milkLayoutDinner, riceLayoutDinner;
	private LinearLayout meatLayoutDinner, sugarLayoutDinner, oilLayoutDinner, eggLayoutDinner;
	private DecimalFormat df;
	private FoodItem vegieB, fruitB, milkB, riceB, meatB, sugarB, oilB, eggB;
	private FoodItem vegieD, fruitD, milkD, riceD, meatD, sugarD, oilD, eggD;
	private FoodItem vegieL, fruitL, milkL, riceL, meatL, sugarL, oilL, eggL;
	private String selectedDate;
	private ArrayList<DailyRecommendation> drList;
//	private DailyRecommendation dr;
	private boolean isVegetarian=false;
	private SharedPreferences.Editor sharedPrefsEditor;
	private SharedPreferences sharedPrefs;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_recommend);
		double a =5.2;
		Log.i("test", "5.2 % 3 = "+a%3);
		
		initVar();
		initView();
		recommendationOption(isVegetarian);	
		
	}
	
	private void initVar(){
		
		sharedPrefsEditor = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();
		sharedPrefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
		user = getIntent().getParcelableExtra("user");		
		tea = new TEA();
		df = new DecimalFormat("#.##");	
		selectedDate = sharedPrefs.getString(Constants.SHARED_PREFS_DATE, "");
	
		
		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		
		vegieB = new FoodItem();
		fruitB= new FoodItem();
		milkB= new FoodItem();
		riceB= new FoodItem();
		meatB= new FoodItem();
		sugarB= new FoodItem();
		oilB= new FoodItem();
		eggB= new FoodItem();
		
		vegieL = new FoodItem();
		fruitL= new FoodItem();
		milkL= new FoodItem();
		riceL= new FoodItem();
		meatL= new FoodItem();
		sugarL= new FoodItem();
		oilL= new FoodItem();
		eggL= new FoodItem();
		
		vegieD = new FoodItem();
		fruitD= new FoodItem();
		milkD= new FoodItem();
		riceD= new FoodItem();
		meatD= new FoodItem();
		sugarD= new FoodItem();
		oilD= new FoodItem();
		eggD= new FoodItem();
		
		
		dbHelper.openDataBase();
		
		if(dbQuery()){
			//with recommendation
//			Toast.makeText(getApplicationContext(), "date: "+selectedDate + "True" , Toast.LENGTH_SHORT).show();
					
		}else{
			//without recommendation
			Toast.makeText(getApplicationContext(), "Database Error!", Toast.LENGTH_SHORT).show();		
		}
//		double TEAProc = 0;
//		TEAProc = user.getTEA()>user.getTEACurrent()?user.getTEACurrent()+100:user.getTEACurrent()-100;
//		
//		tea = dbHelper.queryTEA(TEAProc, user.isNormal());

		dbHelper.close();

		
	}
	
	private boolean dbQuery(){
		
		boolean query = false;
		dbHelper.openDataBase();
		drList = new ArrayList<DailyRecommendation>();
		
		
		drList = dbHelper.getDailyRecommendation(selectedDate);
		Log.i("DRLISTSIZE", "DRListSize: "+ drList.size());
		if(drList.size()>0){
			tea = dbHelper.queryTEAbyID(drList.get(0).getTEA_ID());
			for (DailyRecommendation DailyR : drList ) {
					
				if(DailyR.getMealType()==DailyRecommendation.BREAKFAST){

					vegieB = dbHelper.queryFoodItemByID(DailyR.getVegetable_id());
					fruitB = dbHelper.queryFoodItemByID(DailyR.getFruit_id());
					milkB = dbHelper.queryFoodItemByID(DailyR.getMilk_id());
					riceB = dbHelper.queryFoodItemByID(DailyR.getRice_id());
					meatB = dbHelper.queryFoodItemByID(DailyR.getMeat_id());
					sugarB = dbHelper.queryFoodItemByID(DailyR.getSugar_id());
					oilB = dbHelper.queryFoodItemByID(DailyR.getOil_id());
					eggB = dbHelper.queryFoodItemByID(DailyR.getEgg_id());
					
				}else if(DailyR.getMealType()==DailyRecommendation.LUNCH){
					vegieL = dbHelper.queryFoodItemByID(DailyR.getVegetable_id());
					fruitL = dbHelper.queryFoodItemByID(DailyR.getFruit_id());
					milkL = dbHelper.queryFoodItemByID(DailyR.getMilk_id());
					riceL = dbHelper.queryFoodItemByID(DailyR.getRice_id());
					meatL = dbHelper.queryFoodItemByID(DailyR.getMeat_id());
					sugarL = dbHelper.queryFoodItemByID(DailyR.getSugar_id());
					oilL = dbHelper.queryFoodItemByID(DailyR.getOil_id());
					eggL = dbHelper.queryFoodItemByID(DailyR.getEgg_id());
					
				}else if(DailyR.getMealType()==DailyRecommendation.DINNER){
					vegieD = dbHelper.queryFoodItemByID(DailyR.getVegetable_id());
					fruitD = dbHelper.queryFoodItemByID(DailyR.getFruit_id());
					milkD = dbHelper.queryFoodItemByID(DailyR.getMilk_id());
					riceD = dbHelper.queryFoodItemByID(DailyR.getRice_id());
					meatD = dbHelper.queryFoodItemByID(DailyR.getMeat_id());
					sugarD = dbHelper.queryFoodItemByID(DailyR.getSugar_id());
					oilD = dbHelper.queryFoodItemByID(DailyR.getOil_id());
					eggD = dbHelper.queryFoodItemByID(DailyR.getEgg_id());
				}
			}
			
			
//			dr = drList.get(0);
//			Log.i("DRLIST", "DR: "+ dr.getDate() + " "+ dr.getEgg_id());
			query = true;
		}else{
			Log.i("RA DRLIST", "No recommendation");
			query = false;
		}		
		
		dbHelper.close();
		
		return query;
	}
	
	
	private void initView(){

		btnExit = (Button)findViewById(R.id.buttonExit);
		btnHome = (Button)findViewById(R.id.buttonHome);
		
		vegeLayout = (LinearLayout)findViewById(R.id.vegLayout);
		fruitLayout = (LinearLayout)findViewById(R.id.fruitLayout);
		milkLayout = (LinearLayout)findViewById(R.id.milkLayout);
		riceLayout = (LinearLayout)findViewById(R.id.riceLayout);
		meatLayout = (LinearLayout)findViewById(R.id.meatLayout);
		sugarLayout = (LinearLayout)findViewById(R.id.sugarLayout);
		oilLayout = (LinearLayout)findViewById(R.id.oilLayout);
		eggLayout = (LinearLayout)findViewById(R.id.eggLayout);
		
		vegeLayoutLunch = (LinearLayout)findViewById(R.id.vegLayoutLunch);
		fruitLayoutLunch = (LinearLayout)findViewById(R.id.fruitLayoutLunch);
		milkLayoutLunch = (LinearLayout)findViewById(R.id.milkLayoutLunch);
		riceLayoutLunch = (LinearLayout)findViewById(R.id.riceLayoutLunch);
		meatLayoutLunch = (LinearLayout)findViewById(R.id.meatLayoutLunch);
		sugarLayoutLunch = (LinearLayout)findViewById(R.id.sugarLayoutLunch);
		oilLayoutLunch = (LinearLayout)findViewById(R.id.oilLayoutLunch);
		eggLayoutLunch = (LinearLayout)findViewById(R.id.eggLayoutLunch);
		
		vegeLayoutDinner = (LinearLayout)findViewById(R.id.vegLayoutDinner);
		fruitLayoutDinner = (LinearLayout)findViewById(R.id.fruitLayoutDinner);
		milkLayoutDinner = (LinearLayout)findViewById(R.id.milkLayoutDinner);
		riceLayoutDinner = (LinearLayout)findViewById(R.id.riceLayoutDinner);
		meatLayoutDinner = (LinearLayout)findViewById(R.id.meatLayoutDinner);
		sugarLayoutDinner = (LinearLayout)findViewById(R.id.sugarLayoutDinner);
		oilLayoutDinner = (LinearLayout)findViewById(R.id.oilLayoutDinner);
		eggLayoutDinner = (LinearLayout)findViewById(R.id.eggLayoutDinner);

		tvVeg = (TextView)findViewById(R.id.textViewVeg);
		tvVegServe = (TextView)findViewById(R.id.TextViewVegServe);
		tvFruit = (TextView)findViewById(R.id.textViewFruit);
		tvFruitServe = (TextView)findViewById(R.id.textViewFruitServ);
		tvMilk = (TextView)findViewById(R.id.textViewMilk);
		tvMilkServe = (TextView)findViewById(R.id.textViewMilkServ);
		tvRice = (TextView)findViewById(R.id.textViewRice);
		tvRiceServe = (TextView)findViewById(R.id.textViewRiceServ);
		tvMeat = (TextView)findViewById(R.id.textViewMeat);
		tvMeatServe = (TextView)findViewById(R.id.textViewMeatServ);
		tvSugar = (TextView)findViewById(R.id.textViewSugar);
		tvSugarServe = (TextView)findViewById(R.id.textViewSugarServ);
		tvOil = (TextView)findViewById(R.id.textViewOil);
		tvOilServe = (TextView)findViewById(R.id.textViewOilServ);
		tvEgg = (TextView)findViewById(R.id.textViewEgg);
		tvEggServe = (TextView)findViewById(R.id.textViewEggServ);
		
		tvVegLunch = (TextView)findViewById(R.id.textViewVegLunch);
		tvVegServeLunch = (TextView)findViewById(R.id.TextViewVegServeLunch);
		tvFruitLunch = (TextView)findViewById(R.id.textViewFruitLunch);
		tvFruitServeLunch = (TextView)findViewById(R.id.textViewFruitServLunch);
		tvMilkLunch = (TextView)findViewById(R.id.textViewMilkLunch);
		tvMilkServeLunch = (TextView)findViewById(R.id.textViewMilkServLunch);
		tvRiceLunch = (TextView)findViewById(R.id.textViewRiceLunch);
		tvRiceServeLunch = (TextView)findViewById(R.id.textViewRiceServLunch);
		tvMeatLunch = (TextView)findViewById(R.id.textViewMeatLunch);
		tvMeatServeLunch = (TextView)findViewById(R.id.textViewMeatServLunch);
		tvSugarLunch = (TextView)findViewById(R.id.textViewSugarLunch);
		tvSugarServeLunch = (TextView)findViewById(R.id.textViewSugarServLunch);
		tvOilLunch = (TextView)findViewById(R.id.textViewOilLunch);
		tvOilServeLunch = (TextView)findViewById(R.id.textViewOilServLunch);
		tvEggLunch = (TextView)findViewById(R.id.textViewEggLunch);
		tvEggServeLunch = (TextView)findViewById(R.id.textViewEggServLunch);
		
		tvVegDinner = (TextView)findViewById(R.id.textViewVegDinner);
		tvVegServeDinner = (TextView)findViewById(R.id.TextViewVegServeDinner);
		tvFruitDinner = (TextView)findViewById(R.id.textViewFruitDinner);
		tvFruitServeDinner = (TextView)findViewById(R.id.textViewFruitServDinner);
		tvMilkDinner = (TextView)findViewById(R.id.textViewMilkDinner);
		tvMilkServeDinner = (TextView)findViewById(R.id.textViewMilkServDinner);
		tvRiceDinner = (TextView)findViewById(R.id.textViewRiceDinner);
		tvRiceServeDinner = (TextView)findViewById(R.id.textViewRiceServDinner);
		tvMeatDinner = (TextView)findViewById(R.id.textViewMeatDinner);
		tvMeatServeDinner = (TextView)findViewById(R.id.textViewMeatServDinner);
		tvSugarDinner = (TextView)findViewById(R.id.textViewSugarDinner);
		tvSugarServeDinner = (TextView)findViewById(R.id.textViewSugarServDinner);
		tvOilDinner = (TextView)findViewById(R.id.textViewOilDinner);
		tvOilServeDinner = (TextView)findViewById(R.id.textViewOilServDinner);
		tvEggDinner = (TextView)findViewById(R.id.textViewEggDinner);
		tvEggServeDinner = (TextView)findViewById(R.id.textViewEggServDinner);
		
	
		
		tvRec1 = (TextView)findViewById(R.id.tvRec1);
		tvRecVeg = (TextView)findViewById(R.id.tvVeg);
		
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_MAIN);
				intent.addCategory(Intent.CATEGORY_HOME);	
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});
		
		btnHome.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(RecommendActivity.this, HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("user", user);
				startActivity(intent);
				finish();
			}
		});
		
		tvRec1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isVegetarian = false;
				recommendationOption(isVegetarian);
			}
		});
		
		tvRecVeg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				isVegetarian = true;
				recommendationOption(isVegetarian);
			}
		});
		
		
		
	}
	
	private void RecommendInit(boolean isVege){	

		
//			vegie = recFoodList.get(option).getVegetables();
//			egg = recFoodList.get(option).getEgg();	
//			oil = recFoodList.get(option).getOil();
//			sugar = recFoodList.get(option).getSugar();
//			meat = recFoodList.get(option).getMeat();
//			rice = recFoodList.get(option).getRice();
//			milk = recFoodList.get(option).getMilk();
//			fruit = recFoodList.get(option).getFruit();
			
			vegeLayout.setVisibility(View.GONE);
			vegeLayoutLunch.setVisibility(View.VISIBLE);
			vegeLayoutDinner.setVisibility(View.VISIBLE);
			
			eggLayout.setVisibility(View.GONE);
			eggLayoutLunch.setVisibility(View.GONE);
			eggLayoutDinner.setVisibility(View.GONE);
			
			oilLayout.setVisibility(View.GONE);
			oilLayoutLunch.setVisibility(View.GONE);
			oilLayoutDinner.setVisibility(View.GONE);

			sugarLayout.setVisibility(View.GONE);
			sugarLayoutLunch.setVisibility(View.GONE);
			sugarLayoutDinner.setVisibility(View.GONE);
			
			meatLayout.setVisibility(View.GONE);
			meatLayoutLunch.setVisibility(View.GONE);
			meatLayoutDinner.setVisibility(View.GONE);

			riceLayout.setVisibility(View.VISIBLE);
			riceLayoutLunch.setVisibility(View.VISIBLE);
			riceLayoutDinner.setVisibility(View.VISIBLE);

			milkLayout.setVisibility(View.VISIBLE);
			milkLayoutLunch.setVisibility(View.GONE);
			milkLayoutDinner.setVisibility(View.GONE);
			
			fruitLayout.setVisibility(View.VISIBLE);
			fruitLayoutLunch.setVisibility(View.VISIBLE);
			fruitLayoutDinner.setVisibility(View.VISIBLE);
			
			if(!isVege){
				meatLayout.setVisibility(View.VISIBLE);
				meatLayoutLunch.setVisibility(View.VISIBLE);
				meatLayoutDinner.setVisibility(View.VISIBLE);
				vegeLayout.setVisibility(View.GONE);
				
			}else{
				
				meatLayout.setVisibility(View.GONE);
				meatLayoutLunch.setVisibility(View.GONE);
				meatLayoutDinner.setVisibility(View.GONE);
				vegeLayout.setVisibility(View.VISIBLE);
				vegeLayoutLunch.setVisibility(View.VISIBLE);
				vegeLayoutDinner.setVisibility(View.VISIBLE);
			}
			
			initValues();

	}
	
		
	private void recommendationOption(boolean isVege){
		if(isVege){
			tvRecVeg.setTextColor(Color.BLUE);	
			tvRecVeg.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			tvRec1.setPaintFlags(0);			
			tvRec1.setTextColor(Color.BLACK);			
			meatLayoutDinner.setVisibility(View.GONE);
			meatLayout.setVisibility(View.GONE);
			meatLayoutLunch.setVisibility(View.GONE);
			RecommendInit(isVege);
		}else{
			tvRecVeg.setTextColor(Color.BLACK);
			tvRec1.setTextColor(Color.BLUE);
			
			tvRecVeg.setPaintFlags(0);
			tvRec1.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
			
			RecommendInit(isVege);
		}
	}
	
	private void initValues(){
		//breakfast
		try {
			if(!fruitB.getName().equals("") && !fruitB.getName().equals("null")){
				tvFruit.setText(fruitB.getName());
				tvFruitServe.setText(df.format(fruitB.getMeasurement()*tea.getFruit()/3) +" "+ fruitB.getUnit());
				tvRice.setText(riceB.getName());
				tvRiceServe.setText(df.format(riceB.getMeasurement()*tea.getRice()/3) +" "+ riceB.getUnit());
				tvVeg.setText(vegieB.getName());
				if(isVegetarian){
					tvVegServe.setText(df.format(vegieB.getMeasurement()*(tea.getVegetable()+tea.getMeat())/3) +" "+vegieB.getUnit());
				}else{
					tvVegServe.setText(df.format(vegieB.getMeasurement()*tea.getVegetable()/3) +" "+vegieB.getUnit());
				}		
				tvMilk.setText(milkB.getName());
				tvMilkServe.setText(milkB.getMeasurement()*tea.getMilk() +" "+ milkB.getUnit());
				tvMeat.setText(meatB.getName());
				tvMeatServe.setText(meatB.getMeasurement()*tea.getMeat() +" "+ meatB.getUnit());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		//lunch
		try {
			if(!fruitL.getName().equals("") && !fruitL.getName().equals("null")){
				tvFruitLunch.setText(fruitL.getName());
				tvFruitServeLunch.setText(df.format(fruitL.getMeasurement()*tea.getFruit()/3) +" "+ fruitL.getUnit());
				tvRiceLunch.setText(riceL.getName());
				tvRiceServeLunch.setText(df.format(riceL.getMeasurement()*tea.getRice()/3) +" "+ riceL.getUnit());
				tvVegLunch.setText(vegieL.getName());
				if(isVegetarian){
					tvVegServeLunch.setText(df.format(vegieL.getMeasurement()*(tea.getVegetable()+tea.getMeat())/3) 
							+" "+vegieL.getUnit());
				}else{
					tvVegServeLunch.setText(df.format(vegieL.getMeasurement()*tea.getVegetable()/3) +" "+vegieL.getUnit());
				}
				tvMilkLunch.setText(milkL.getName());
				tvMilkServeLunch.setText(milkL.getMeasurement()*tea.getMilk() +" "+ milkL.getUnit());
				tvMeatLunch.setText(meatL.getName());
				tvMeatServeLunch.setText(meatL.getMeasurement()*tea.getMeat() +" "+ meatL.getUnit());
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		//DINNER	
		try {
			if(!fruitD.getName().equals("") && !fruitD.getName().equals("null")){
				tvFruitDinner.setText(fruitD.getName());
				tvFruitServeDinner.setText(df.format(fruitD.getMeasurement()*tea.getFruit()/3) +" "+ fruitD.getUnit());
				tvRiceDinner.setText(riceD.getName());
				tvRiceServeDinner.setText(df.format(riceD.getMeasurement()*tea.getRice()/3) +" "+ riceD.getUnit());
				tvVegDinner.setText(vegieD.getName());
				if(isVegetarian){
					tvVegServeDinner.setText(df.format(vegieD.getMeasurement()*(tea.getVegetable()+tea.getMeat())/3) 
							+" "+vegieD.getUnit());
				}else{
					tvVegServeDinner.setText(df.format(vegieD.getMeasurement()*tea.getVegetable()/3) +" "+vegieD.getUnit());
				}
				
				tvMilkDinner.setText(milkD.getName());
				tvMilkServeDinner.setText(milkD.getMeasurement()*tea.getMilk() +" "+ milkD.getUnit());
				tvMeatDinner.setText(meatD.getName());
				tvMeatServeDinner.setText(meatD.getMeasurement()*tea.getMeat() +" "+ meatD.getUnit());
		
//		(tea.getVegetable()+tea.getMeat())
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
	}
	
}
