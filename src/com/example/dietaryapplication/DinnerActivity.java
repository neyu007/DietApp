package com.example.dietaryapplication;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class DinnerActivity extends Activity {

	private ImageButton imgBtnOK, imgBtnShuffle;
	private User user;
	private SimpleDateFormat sdf;
	private String selectedDate, currentDateStr;
	private Date currentDate;
	private DataBaseHelper dbHelper;
	private ArrayList<FoodItem> vegeList, fruitList, milkList, riceList, meatList, sugarList, oilList, eggList;
	private FoodItem vegie, fruit, milk, rice, meat, sugar, oil, egg;
	private TEA tea;
	private DailyRecommendation dr;
	private ArrayList<DailyRecommendation> drList;
	private TextView tvFirst, tvFirstServing, tv2nd, tv3rd, tv4th, tv2ndServing, tv3rdServing, tv4thServing; 
	private TextView tvNormal, tvVegetarian;
	private boolean isVegetarian = false, isRecommendEmpty = true;
	private DecimalFormat df = new DecimalFormat("#.##");	
	private LinearLayout layout4th;
	private int dateCompare=0;
	private SharedPreferences.Editor sharedPrefsEditor;
	private SharedPreferences sharedPrefs;
	private ImageButton btnPrice1;
	private ImageButton btnPrice2;
	private ImageButton btnPrice3;
	private ImageButton btnPrice4;
	private ProgressDialog progressDialog;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);			
		setContentView(R.layout.recommend_dinner);		
		

		initVars();
		//Toast.makeText(getApplicationContext(), "date: "+selectedDate , Toast.LENGTH_SHORT).show();
		
		if(dbQuery()){
			//with recommendation
//			Toast.makeText(getApplicationContext(), "date: "+selectedDate + "True" , Toast.LENGTH_SHORT).show();
			tea = dbHelper.queryTEAbyID(dr.getTEA_ID());
			isRecommendEmpty = false;
			
		}else{
			//without recommendation
//			Toast.makeText(getApplicationContext(), "date: "+selectedDate  + "False", Toast.LENGTH_SHORT).show();			
			double TEAProc = 0;
			TEAProc = user.getTEA()>user.getTEACurrent()?user.getTEACurrent()+100:user.getTEACurrent()-100;
			tea = dbHelper.queryTEA(TEAProc, user.isNormal());
			isRecommendEmpty = true;
		}
		
		initFoodItems(isRecommendEmpty);
		initButtons();
		initValues(isVegetarian);
	
		
	}
	@SuppressLint("SimpleDateFormat")
	private void initVars(){
		
		progressDialog = new ProgressDialog(DinnerActivity.this);
		progressDialog.setMessage("Loading, please wait...");		
		progressDialog.setCancelable(false);
		
		sharedPrefsEditor = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE).edit();
		sharedPrefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
		user = getIntent().getParcelableExtra("user");	
		selectedDate = sharedPrefs.getString(Constants.SHARED_PREFS_DATE, "");
		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		
		sdf = new SimpleDateFormat("yyyy-MM-dd");
//		int dateCompare=0;
		currentDate = new Date();
		currentDateStr = sdf.format(currentDate);
		
		try {
		 dateCompare=sdf.parse(currentDateStr).compareTo(sdf.parse(selectedDate));
		 Log.i("DATECOMP", "DateComp: "+ dateCompare);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		tea = new TEA();
		
		vegie = new FoodItem();
		fruit = new FoodItem();
		milk = new FoodItem();
		rice = new FoodItem();
		meat = new FoodItem();
		sugar = new FoodItem();
		oil = new FoodItem();
		egg = new FoodItem();
		
		vegeList = new ArrayList<FoodItem>();
		fruitList= new ArrayList<FoodItem>();
		milkList= new ArrayList<FoodItem>();
		riceList= new ArrayList<FoodItem>();
		meatList= new ArrayList<FoodItem>();
		sugarList= new ArrayList<FoodItem>();
		oilList= new ArrayList<FoodItem>();
		eggList= new ArrayList<FoodItem>();
		
		vegeList = dbHelper.getFoodListByCategory(FoodCategory.VEGETABLES);
		fruitList = dbHelper.getFoodListByCategory(FoodCategory.FRUIT);
		milkList = dbHelper.getFoodListByCategory(FoodCategory.MILK);
		riceList = dbHelper.getFoodListByCategory(FoodCategory.RICE);
		meatList = dbHelper.getFoodListByCategory(FoodCategory.MEAT);
		sugarList = dbHelper.getFoodListByCategory(FoodCategory.SUGAR);
		oilList = dbHelper.getFoodListByCategory(FoodCategory.OIL);
		eggList = dbHelper.getFoodListByCategory(FoodCategory.EGG);		
	}
	
	private void initFoodItems(boolean isEmpty){
		
		dbHelper.openDataBase();
		
		if(isEmpty){			
				
			
			Random ran = new Random();
			int v=0, f=0, mi=0, r=0, me=0, s=0,o=0,e=0 ;
			if(!vegeList.isEmpty()){
				v = ran.nextInt(vegeList.size());
				vegie = vegeList.get(v);			
			}
			if(!fruitList.isEmpty()){
				f = ran.nextInt(fruitList.size());
				fruit = fruitList.get(f);			
			}
			if(!milkList.isEmpty()){
				mi = ran.nextInt(milkList.size());
				milk = milkList.get(mi);			
			}
			if(!riceList.isEmpty()){
				r = ran.nextInt(riceList.size());
				rice = riceList.get(0);			
			}
			if(!meatList.isEmpty()){
				me = ran.nextInt(meatList.size());
				meat = meatList.get(me);			
			}
			if(!sugarList.isEmpty()){
				s = ran.nextInt(sugarList.size());
				sugar = sugarList.get(s);			
			}
			if(!oilList.isEmpty()){
				o = ran.nextInt(oilList.size());
				oil = oilList.get(o);			
			}
			if(!eggList.isEmpty()){
				e = ran.nextInt(eggList.size());
				egg = eggList.get(e);			
			}
			dr = new DailyRecommendation(DailyRecommendation.DINNER, selectedDate, (int)tea.getTEAid(),
					(int)vegie.getRowid(), (int)fruit.getRowid(), 
					(int)milk.getRowid(), (int)rice.getRowid(), (int)meat.getRowid(), (int)egg.getRowid(),
					(int)sugar.getRowid(), (int)oil.getRowid(), -1);
		}else{
			
			vegie = dbHelper.queryFoodItemByID(dr.getVegetable_id());
			fruit = dbHelper.queryFoodItemByID(dr.getFruit_id());
			milk = dbHelper.queryFoodItemByID(dr.getMilk_id());
			rice = dbHelper.queryFoodItemByID(dr.getRice_id());
			meat = dbHelper.queryFoodItemByID(dr.getMeat_id());
			sugar = dbHelper.queryFoodItemByID(dr.getSugar_id());
			oil = dbHelper.queryFoodItemByID(dr.getOil_id());
			egg = dbHelper.queryFoodItemByID(dr.getEgg_id());
			
		}
		
		dbHelper.close();
	}
	
	
	
	
	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();		
		initVars();
		//Toast.makeText(getApplicationContext(), "date: "+selectedDate , Toast.LENGTH_SHORT).show();
		
		if(dbQuery()){
			//with recommendation
//			Toast.makeText(getApplicationContext(), "date: "+selectedDate + "True" , Toast.LENGTH_SHORT).show();
			tea = dbHelper.queryTEAbyID(dr.getTEA_ID());
			isRecommendEmpty = false;
		}else{
//			without recommendation
//			Toast.makeText(getApplicationContext(), "date: "+selectedDate  + "False", Toast.LENGTH_SHORT).show();			
			double TEAProc = 0;
			TEAProc = user.getTEA()>user.getTEACurrent()?user.getTEACurrent()+100:user.getTEACurrent()-100;
			tea = dbHelper.queryTEA(TEAProc, user.isNormal());
			isRecommendEmpty = true;
		}
		
		initFoodItems(isRecommendEmpty);
		initButtons();
		initValues(isVegetarian);
		
	}





	private boolean dbQuery(){
		
		boolean query = false;
		dbHelper.openDataBase();
		drList = new ArrayList<DailyRecommendation>();
		
		
		drList = dbHelper.getDailyRecommendation(selectedDate, DailyRecommendation.DINNER);
		
		if(drList.size()>0){
			dr = drList.get(0);
			Log.i("DRLIST", "DR: "+ dr.getDate() + " "+ dr.getEgg_id());
			query =true;
		}else{
			Log.i("DRLIST", "No recommendation");
			query = false;
		}		
		
		dbHelper.close();
		
		return query;
	}
	
	private void initButtons(){
		
		layout4th = (LinearLayout)findViewById(R.id.layout4th);
		tvFirst = (TextView)findViewById(R.id.tvFirst);
		tvFirstServing = (TextView)findViewById(R.id.tvFirstServing);
		tv2nd = (TextView)findViewById(R.id.tv2nd);
		tv2ndServing = (TextView)findViewById(R.id.tv2ndServing);
		tv3rd = (TextView)findViewById(R.id.tv3rd);
		tv3rdServing = (TextView)findViewById(R.id.tv3rdServing);
		tv4th = (TextView)findViewById(R.id.tv4th);
		tv4thServing = (TextView)findViewById(R.id.tv4thServing);		
		tvVegetarian = (TextView)findViewById(R.id.tvVegetarian);
		tvNormal = (TextView)findViewById(R.id.tvNormal);
		
		btnPrice1 = (ImageButton)findViewById(R.id.pricebutton1);
		btnPrice2 = (ImageButton)findViewById(R.id.pricebutton2);
		btnPrice3 = (ImageButton)findViewById(R.id.pricebutton3);
		btnPrice4 = (ImageButton)findViewById(R.id.pricebutton4);
		btnPrice1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(DinnerActivity.this, PriceActivity.class);
//				i.putExtra("user", user);
//				i.putExtra("id",fruit.getRowid());
//				startActivity(i);
//				finish();
				
				saveFirst(fruit.getRowid());
			}
		});
		
		btnPrice2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(DinnerActivity.this, PriceActivity.class);
//				i.putExtra("user", user);
//				i.putExtra("id",rice.getRowid());
//				startActivity(i);
//				finish();
				
				saveFirst(rice.getRowid());
			}
		});
		
		btnPrice3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(DinnerActivity.this, PriceActivity.class);
//				i.putExtra("user", user);
				if(isVegetarian){
					saveFirst(vegie.getRowid());
//					i.putExtra("id",vegie.getRowid());
				}else{
					saveFirst(meat.getRowid());
//					i.putExtra("id",meat.getRowid());
				}
				
//				startActivity(i);
//				finish();
			}
		});
		btnPrice4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Intent i = new Intent(DinnerActivity.this, PriceActivity.class);
//				i.putExtra("user", user);
				if(!isVegetarian){
//					i.putExtra("id",vegie.getRowid());
					saveFirst(fruit.getRowid());
				}
//				startActivity(i);
//				finish();
			}
		});
		
		tvNormal.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(isVegetarian){
					isVegetarian = false;
					initValues(isVegetarian);
				}
			}
		});
		
		tvVegetarian.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(!isVegetarian){
					isVegetarian = true;
					initValues(isVegetarian);
				}
			}
		});
		
		imgBtnOK = (ImageButton)findViewById(R.id.okbutton1);
		imgBtnOK.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(dateCompare<1){
					if(isRecommendEmpty){
						long ii = dbHelper.insertDailyRecommendation(dr);
						if(ii>0){
							dr.setRowid((int)ii);
							Toast.makeText(getApplicationContext(), "Meal Recommendation successfully saved.", Toast.LENGTH_SHORT).show();
							isRecommendEmpty = false;
						}else{
							Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
						}
						
					}else{
						int u = dbHelper.updateDailyRecommendation(dr);
						if(u>0){
							Toast.makeText(getApplicationContext(), "Meal Recommendation successfully updated.", Toast.LENGTH_SHORT).show();
						}else{
							Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
						}
					}
				}
				
				
				
				Intent i = new Intent(DinnerActivity.this, RecommendActivity.class);
				i.putExtra("user", user);
				i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(i);
				finish();
				
			}
		});
		
		imgBtnShuffle = (ImageButton)findViewById(R.id.btnShuffle);
		imgBtnShuffle.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(dateCompare>0){
					Toast.makeText(getApplicationContext(), "You cannot change previous recommendations.", 
							Toast.LENGTH_SHORT).show();
				}else{
					shuffle();
				}
				
			}
		});
}
	
	private void initValues(boolean isVegetarian){
		if(!isRecommendEmpty || dateCompare<=0){
			if(isVegetarian){
				tvVegetarian.setTextColor(Color.BLUE);	
				tvVegetarian.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
				tvNormal.setTextColor(Color.BLACK);	
				tvNormal.setPaintFlags(0);
				
				tvFirst.setText(fruit.getName());
				tvFirstServing.setText(df.format(fruit.getMeasurement()*tea.getFruit()/3) +" "+ fruit.getUnit());
				tv2nd.setText(rice.getName());
				tv2ndServing.setText(df.format(rice.getMeasurement()*tea.getRice()/3) +" "+ rice.getUnit());
				tv3rd.setText(vegie.getName());
				tv3rdServing.setText(df.format(vegie.getMeasurement()*(tea.getVegetable()+tea.getMeat())/3) +" "+vegie.getUnit());
				tv4th.setText(milk.getName());
				tv4thServing.setText(df.format(milk.getMeasurement()*tea.getMilk()) +" "+ milk.getUnit());
				layout4th.setVisibility(View.GONE);
			}else{
				tvVegetarian.setTextColor(Color.BLACK);	
				tvVegetarian.setPaintFlags(0);
				tvNormal.setTextColor(Color.BLUE);	
				tvNormal.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
				
				tvFirst.setText(fruit.getName());
				tvFirstServing.setText(df.format(fruit.getMeasurement()*tea.getFruit()/3) +" "+ fruit.getUnit());
				tv2nd.setText(rice.getName());
				tv2ndServing.setText(df.format(rice.getMeasurement()*tea.getRice()/3) +" "+ rice.getUnit());
				tv3rd.setText(meat.getName());
				tv3rdServing.setText(df.format(meat.getMeasurement()*tea.getMeat()/3) +" "+ meat.getUnit());
				tv4th.setText(vegie.getName());
				tv4thServing.setText(df.format(vegie.getMeasurement()*tea.getVegetable()/3)+" "+ vegie.getUnit());
				layout4th.setVisibility(View.VISIBLE);
			}
		}else{
			Toast.makeText(getApplicationContext(), 
					"No recommendations recorded for this date.", Toast.LENGTH_LONG).show();
		}
	}
	
	private void shuffle(){
		Random ran = new Random();
		int v=0, f=0, mi=0, r=0, me=0, s=0,o=0,e=0 ;
		if(!vegeList.isEmpty()){
			v = ran.nextInt(vegeList.size());
			vegie = vegeList.get(v);			
		}
		if(!fruitList.isEmpty()){
			f = ran.nextInt(fruitList.size());
			fruit = fruitList.get(f);			
		}
		if(!milkList.isEmpty()){
			mi = ran.nextInt(milkList.size());
			milk = milkList.get(mi);			
		}
		if(!riceList.isEmpty()){
			r = ran.nextInt(riceList.size());
			rice = riceList.get(0);			
		}
		if(!meatList.isEmpty()){
			me = ran.nextInt(meatList.size());
			meat = meatList.get(me);			
		}
		if(!sugarList.isEmpty()){
			s = ran.nextInt(sugarList.size());
			sugar = sugarList.get(s);			
		}
		if(!oilList.isEmpty()){
			o = ran.nextInt(oilList.size());
			oil = oilList.get(o);			
		}
		if(!eggList.isEmpty()){
			e = ran.nextInt(eggList.size());
			egg = eggList.get(e);			
		}
		
		dr.setEgg_id((int)egg.getRowid());
		dr.setOil_id((int)oil.getRowid());
		dr.setSugar_id((int)sugar.getRowid());
		dr.setMeat_id((int)meat.getRowid());
		dr.setRice_id((int)rice.getRowid());
		dr.setMilk_id((int)milk.getRowid());
		dr.setFruit_id((int)fruit.getRowid());
		dr.setVegetable_id((int)vegie.getRowid());
		
		initValues(isVegetarian);
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent i = new Intent(DinnerActivity.this, LunchActivity.class);
		i.putExtra("user", user);				
		i.putExtra("date", selectedDate);
		i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(i);
		finish();
	}
	
	private void saveFirst(long foodId){		
		
		if(!isRecommendEmpty || dateCompare<=0){
			if(dateCompare<1){
				if(isRecommendEmpty){
					long ii = dbHelper.insertDailyRecommendation(dr);
					if(ii>0){
						dr.setRowid((int)ii);
						//Toast.makeText(getApplicationContext(), "Meal Recommendation successfully saved.", Toast.LENGTH_SHORT).show();
						isRecommendEmpty = false;
						if (progressDialog.isShowing() && progressDialog != null)
							progressDialog.dismiss();
					}else{
						if (progressDialog.isShowing() && progressDialog != null)
							progressDialog.dismiss();
						Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();						
					}
					
				}else{
					int u = dbHelper.updateDailyRecommendation(dr);
					if(u>0){
						if (progressDialog.isShowing() && progressDialog != null)
							progressDialog.dismiss();
						//Toast.makeText(getApplicationContext(), "Meal Recommendation successfully updated.", Toast.LENGTH_SHORT).show();
					}else{
						if (progressDialog.isShowing() && progressDialog != null)
							progressDialog.dismiss();
						Toast.makeText(getApplicationContext(), "Database Error", Toast.LENGTH_SHORT).show();
					}
				}
			}
			if (progressDialog.isShowing() && progressDialog != null)
				progressDialog.dismiss();
			
			Intent i = new Intent(DinnerActivity.this, PriceActivity.class);
			i.putExtra("user", user);
			i.putExtra("id",foodId);
			startActivity(i);
			
		}else{
			Toast.makeText(getApplicationContext(), "Price not available.", Toast.LENGTH_SHORT).show();
		}
	}
	
}
