package com.example.dietaryapplication;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.format.DateFormat;
import android.util.Log;

public class DataBaseHelper extends SQLiteOpenHelper {

		 
	    //The Android's default system path of your application database.
	
		private static DataBaseHelper mInstance = null;
	
	    private static String DB_PATH;// = "/data/data/com.example.dietaryapplication/databases/";	    
	 
	    private static String DB_NAME = "diet.db";
	 
	    private SQLiteDatabase myDataBase; 
	 
	    private final Context myContext;
	    
	    private ArrayList<FoodItem> vegeList, fruitList, milkList, riceList, meatList, sugarList, oilList, eggList;
	    private FoodItem vegie, fruit, milk, rice, meat, sugar, oil, egg;
	    
	    private final String[] categories = new String[]{
				TeaTable.EGG, TeaTable.FRUIT, TeaTable.MEAT, TeaTable.MILK, TeaTable.OIL, TeaTable.RICE,
				TeaTable.SUGAR, TeaTable.VEGETABLE
		};
	    
	    private final String[] constraints = new String[]{
				"None", "Diabetes", "Highblood", "Both"
		};
	 
	    /**
	     * Constructor
	     * Takes and keeps a reference of the passed context in order to access to the application assets and 

resources.
	     * @param context
	     */
	    
	    public static DataBaseHelper getInstance(Context ctx) {

	        // Use the application context, which will ensure that you 
	        // don't accidentally leak an Activity's context.
	        // See this article for more information: http://bit.ly/6LRzfx
	        if (mInstance == null) {
	          mInstance = new DataBaseHelper(ctx.getApplicationContext());
	        }
	        return mInstance;
	      }
	    
	    private DataBaseHelper(Context context) {
	 
	    	super(context, DB_NAME, null, 1);
	        this.myContext = context;

	        DB_PATH = myContext.getDatabasePath(DB_NAME).getPath();
	    }	
	 
	  /**
	     * Creates a empty database on the system and rewrites it with your own database.
	     * */
	    public void createDataBase() throws IOException{
	 
	    	boolean dbExist = checkDataBase();
	 
	    	if(dbExist){
	    		//do nothing - database already exist
	    	}else{
	 
	    		//By calling this method and empty database will be created into the default system path
	               //of your application so we are gonna be able to overwrite that database with our database.
	        	this.getReadableDatabase();
	 
	        	try {
	 
	    			copyDataBase();
	 
	    		} catch (IOException e) {
	 
	        		throw new Error("Error copying database");
	 
	        	}
	    	}
	 
	    }
	 
	    /**
	     * Check if the database already exist to avoid re-copying the file each time you open the application.
	     * @return true if it exists, false if it doesn't
	     */
	    private boolean checkDataBase(){
	 
	    	SQLiteDatabase checkDB = null;
	 
	    	try{
	    		String myPath = DB_PATH; //+ DB_NAME;
	    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READWRITE);
	 
	    	}catch(SQLiteException e){
	 
	    		//database does't exist yet.
	 
	    	}
	 
	    	if(checkDB != null){
	 
	    		checkDB.close();
	 
	    	}
	 
	    	return checkDB != null ? true : false;
	    }
	 
	    /**
	     * Copies your database from your local assets-folder to the just created empty database in the
	     * system folder, from where it can be accessed and handled.
	     * This is done by transfering bytestream.
	     * */
	    private void copyDataBase() throws IOException{
	 
	    	//Open your local db as the input stream
	    	InputStream myInput = myContext.getAssets().open(DB_NAME);
	 
	    	// Path to the just created empty db
	    	String outFileName = DB_PATH;// + DB_NAME;
	 
	    	//Open the empty db as the output stream
	    	OutputStream myOutput = new FileOutputStream(outFileName);
	 
	    	//transfer bytes from the inputfile to the outputfile
	    	byte[] buffer = new byte[1024];
	    	int length;
	    	while ((length = myInput.read(buffer))>0){
	    		myOutput.write(buffer, 0, length);
	    	}
	 
	    	//Close the streams
	    	myOutput.flush();
	    	myOutput.close();
	    	myInput.close();
	 
	    }
	 
	    public void openDataBase() throws SQLException{
	 
	    	//Open the database
	        String myPath = DB_PATH;// + DB_NAME;
	    	myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.NO_LOCALIZED_COLLATORS|SQLiteDatabase.OPEN_READWRITE);
	 
	    }
	 
	    @Override
		public synchronized void close() {
	 
	    	    if(myDataBase != null)
	    		    myDataBase.close();
	 
	    	    super.close();
	 
		}
	 
		@Override
		public void onCreate(SQLiteDatabase db) {
	 
		}
	 
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	 
		}
		
		public long insertRecordUser(String name, String gender){
			long id = 0;
			try {
				
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues initialValues = new ContentValues();
				//initialValues.put("user_id", 1);
				initialValues.put("name", name);
				initialValues.put("gender", gender);
				//initialValues.put("age", age);
				//initialValues.put("date", DateFormat.format(date, inDate));


				Log.i("db path", db.getPath());
				
				id = db.insert("User", null, initialValues);
				db.close();
			
				//return true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
				//return false;
			}
			return id;
		}
		
		public long insertUserDetails(long user_id, int age, String constraints, double height, double weight, int isNormal, 
				double activity_factor,	double DBW, double TEA, double TEACurrent){
			long id = 0;
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			java.util.Date date = new java.util.Date();
			
			try {
				
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues initialValues = new ContentValues();
				initialValues.put("user_id", user_id);
				initialValues.put("age", age);
				initialValues.put("constraints", constraints);
				initialValues.put("height", height);
				initialValues.put("weight", weight);
				initialValues.put("isNormal", isNormal);
				initialValues.put("activity_factor", activity_factor);
				initialValues.put("DBW", DBW);
				initialValues.put("TEA", TEA);
				initialValues.put("TEACurrent", TEACurrent);

				Log.i("db path", db.getPath());
				
				id = db.insert("UserDetails", null, initialValues);
				Log.i("USERDETAILS", "rowID: "+ id);
				db.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();				
			}
			return id;
		}
		
		public User userQuery(){
			User currentUser = new User();
			SQLiteDatabase myDB = getReadableDatabase();
			Cursor c = myDB.rawQuery("SELECT * FROM User ORDER BY rowid DESC", null);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			if (c.getCount() > 0 && c.moveToFirst()){
				try {					
					currentUser.setName(c.getString(c.getColumnIndexOrThrow("name")));
					currentUser.setGender(c.getString(c.getColumnIndexOrThrow("gender")));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			Cursor cc = myDB.rawQuery("SELECT * FROM UserDetails ORDER BY rowid DESC", null);
			if (cc.getCount() > 0 && cc.moveToFirst()){
				try {
					currentUser.setId(cc.getInt(cc.getColumnIndexOrThrow("user_id")));
					currentUser.setAge(cc.getString(cc.getColumnIndexOrThrow("age")));
					currentUser.setConstraints(cc.getString(cc.getColumnIndexOrThrow("constraints")));
					currentUser.setHeight(cc.getDouble(cc.getColumnIndexOrThrow("height")));
					currentUser.setWeight(cc.getDouble(cc.getColumnIndexOrThrow("weight")));
					currentUser.setNormal(cc.getInt(cc.getColumnIndexOrThrow("isNormal"))==1?true:false);
					currentUser.setActivityFactor(cc.getDouble(cc.getColumnIndexOrThrow("activity_factor")));
					currentUser.setDBW(cc.getDouble(cc.getColumnIndexOrThrow("DBW")));
					currentUser.setTEA(cc.getDouble(cc.getColumnIndexOrThrow("TEA")));
					currentUser.setTEACurrent(cc.getDouble(cc.getColumnIndexOrThrow("TEACurrent")));
					currentUser.setUpdateDate(cc.getString(cc.getColumnIndexOrThrow("date")));
//					Log.i("DB DATE", "DATE: "+ cc.getString(cc.getColumnIndexOrThrow("date")));
//					java.util.Date d1 = sdf.parse(cc.getString(cc.getColumnIndexOrThrow("date")));
//					java.util.Date d2= sdf.parse("2016-01-11");
//					long i = d1.getTime() - d2.getTime();
//					Log.i("DB DATE", "DATE difference: "+ i/(24*60*60*1000));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			return currentUser;
		}
		
		public ArrayList<GraphV> graphQuery(){

			ArrayList<GraphV> gvList = new ArrayList<GraphV>();
			GraphV gv = new GraphV();
//			User currentUser = new User();
			SQLiteDatabase myDB = getReadableDatabase();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			Cursor cc = myDB.rawQuery("SELECT * FROM UserDetails ORDER BY rowid ASC", null);
			if (cc.moveToFirst()){
				
				do {
					try {	
						gv = new GraphV();
//						currentUser.setId(cc.getInt(cc.getColumnIndexOrThrow("user_id")));
//						currentUser.setAge(cc.getString(cc.getColumnIndexOrThrow("age")));
//						currentUser.setConstraints(cc.getString(cc.getColumnIndexOrThrow("constraints")));
//						currentUser.setHeight(cc.getDouble(cc.getColumnIndexOrThrow("height")));
//						currentUser.setWeight(cc.getDouble(cc.getColumnIndexOrThrow("weight")));
//						currentUser.setNormal(cc.getInt(cc.getColumnIndexOrThrow("isNormal"))==1?true:false);
//						currentUser.setActivityFactor(cc.getDouble(cc.getColumnIndexOrThrow("activity_factor")));
//						currentUser.setDBW(cc.getDouble(cc.getColumnIndexOrThrow("DBW")));
//						currentUser.setTEA(cc.getDouble(cc.getColumnIndexOrThrow("TEA")));
//						currentUser.setTEACurrent(cc.getDouble(cc.getColumnIndexOrThrow("TEACurrent")));
//						currentUser.setUpdateDate(cc.getString(cc.getColumnIndexOrThrow("date")));
						Log.i("DB DATE", "DATE: "+ cc.getString(cc.getColumnIndexOrThrow("date")));
						
						gv.setDate(sdf.parse(cc.getString(cc.getColumnIndexOrThrow("date"))));
						gv.setDbw(cc.getDouble(cc.getColumnIndexOrThrow("DBW")));
						gv.setWeight(cc.getDouble(cc.getColumnIndexOrThrow("weight")));
						gvList.add(gv);				
						
						Log.i("DB", "GVlist Weight: "+ gv.getWeight());
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				} while (cc.moveToNext()); 
				
				
			}
			
			return gvList;
		}
		
		public int updateCurrentRecommendation(int option_id, int veg, int fruit, int milk, int rice, 
				int meat, int sugar, int oil, int egg){
		
			int id = 0;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			java.util.Date datec = new java.util.Date();
			try {
				
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues initialValues = new ContentValues();
				initialValues.put("updateDate", dateFormat.format(datec));
				initialValues.put(FoodCategory.VEGETABLES, veg);
				initialValues.put(FoodCategory.FRUIT, fruit);
				initialValues.put(FoodCategory.MILK, milk);
				initialValues.put(FoodCategory.RICE, rice);
				initialValues.put(FoodCategory.MEAT, meat);
				initialValues.put(FoodCategory.SUGAR, sugar);
				initialValues.put(FoodCategory.OIL, oil);
				initialValues.put(FoodCategory.EGG, egg);
				

				Log.i("db path", db.getPath());
				
				//id = db.insert("CurrentRecommendation", null, initialValues);
				db.update("CurrentRecommendation", initialValues, "option_id=?", new String[]{Integer.toString(option_id)});
				Log.i("CurrentRecommendation", "rowID: "+ id);
				db.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();				
			}
			return id;
			
			
		}
		
		public ArrayList<RecommendedFood> currentRecommendationQuery(){
			
			ArrayList<RecommendedFood> currentRecommendation =  new ArrayList<RecommendedFood>();
			SQLiteDatabase myDB = getReadableDatabase();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			java.util.Date datc = new java.util.Date();
			String datcd = sdf.format(datc);	
			RecommendedFood recFood;
			
			vegeList = new ArrayList<FoodItem>();
			fruitList= new ArrayList<FoodItem>();
			milkList= new ArrayList<FoodItem>();
			riceList= new ArrayList<FoodItem>();
			meatList= new ArrayList<FoodItem>();
			sugarList= new ArrayList<FoodItem>();
			oilList= new ArrayList<FoodItem>();
			eggList= new ArrayList<FoodItem>();
			
//			Cursor c = myDB.rawQuery("SELECT * FROM CurrentRecommendation WHERE updateDate >= date('now', '-1 day')",null);
			Cursor c = myDB.rawQuery("SELECT * FROM CurrentRecommendation WHERE updateDate >= ? ORDER BY option_id", new String[] {datcd});
			
			if (c.getCount() > 0 && c.moveToFirst()){
				try {					
//					currentUser.setName(c.getString(c.getColumnIndexOrThrow("name")));
//					currentUser.setGender(c.getString(c.getColumnIndexOrThrow("gender")));					
					for(int i=0; i<3; i++){
						
						vegie = new FoodItem();
						fruit = new FoodItem();
						milk = new FoodItem();
						rice = new FoodItem();
						meat = new FoodItem();
						sugar = new FoodItem();
						oil = new FoodItem();
						egg = new FoodItem();
						
				
						
						try {
							vegeList = getFoodListByCategory(FoodCategory.VEGETABLES);
							fruitList = getFoodListByCategory(FoodCategory.FRUIT);
							milkList = getFoodListByCategory(FoodCategory.MILK);
							riceList = getFoodListByCategory(FoodCategory.RICE);
							meatList = getFoodListByCategory(FoodCategory.MEAT);
							sugarList = getFoodListByCategory(FoodCategory.SUGAR);
							oilList = getFoodListByCategory(FoodCategory.OIL);
							eggList = getFoodListByCategory(FoodCategory.EGG);
//							
							if(!vegeList.isEmpty()){						
								vegie = vegeList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.VEGETABLES)));						
							}
							if(!fruitList.isEmpty()){
								fruit = fruitList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.FRUIT)));
							}
							if(!milkList.isEmpty()){
								milk = milkList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.MILK)));
							}
							if(!riceList.isEmpty()){
								rice = riceList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.RICE)));
							}
							if(!meatList.isEmpty()){
								meat = meatList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.MEAT)));
							}
							if(!sugarList.isEmpty()){
								sugar = sugarList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.SUGAR)));
							}
							if(!oilList.isEmpty()){
								oil = oilList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.OIL)));
							}
							if(!eggList.isEmpty()){
								egg = eggList.get(c.getInt(c.getColumnIndexOrThrow(FoodCategory.EGG)));
							}
							
							
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
				
						}
						
						recFood = new RecommendedFood();			
						recFood.setEgg(egg);
						recFood.setOil(oil);
						recFood.setSugar(sugar);
						recFood.setMeat(meat);
						recFood.setRice(rice);
						recFood.setMilk(milk);
						recFood.setFruit(fruit);
						recFood.setVegetables(vegie);
						currentRecommendation.add(i, recFood);
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				c.moveToNext();
			}
			
			
			
			return currentRecommendation;
			
		}
		
//		@SuppressLint("SimpleDateFormat")
//		public String testDate(){
//			SQLiteDatabase myDB = getReadableDatabase();
//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//			java.util.Date datc = new java.util.Date();
//			String datcd = sdf.format(datc);	
////			Cursor c = myDB.rawQuery("SELECT * FROM CurrentRecommendation WHERE updateDate >= date('now', '-1 day')",null);
//			Cursor c = myDB.rawQuery("SELECT * FROM CurrentRecommendation WHERE updateDate >= ?", new String[] {datcd});
//			String dateC = "";
//			if (c.getCount() > 0 && c.moveToFirst()){
//				try {					
////					currentUser.setName(c.getString(c.getColumnIndexOrThrow("name")));
////					currentUser.setGender(c.getString(c.getColumnIndexOrThrow("gender")));
//					dateC = "Test" + c.getString(c.getColumnIndex("updateDate"));
//					Log.i("DATE", "DATE: "+c.getCount());
//				} catch (Exception e) {
//					// TODO: handle exception
//					e.printStackTrace();
//				}
//			}
//			
//			
//			return dateC;
//		}
		
		
		public TEA queryTEA(double kcal, boolean isNormal){
			TEA tea = new TEA();
			
			double remainder = kcal%100;
			double rounddown = kcal-remainder;
			
			double kk = remainder>49?rounddown+100:rounddown;

			String[] selectionArgs = new String[]{String.valueOf(kk)};
			String[] columns = new String[]{"*","rowid"};
			Log.i("DB", "kcals : "+ selectionArgs[0]);
			SQLiteDatabase myDB = getReadableDatabase();
			
			Cursor c = myDB.query(
									TeaTable.TABLE_NAME, //table
									columns, //columns
									TeaTable.KCAL+"=?", //selection
									selectionArgs, //selectionArgs
									null, //groupBy
									null, //having
									null //orderBy
					); 
			
//			c.moveToFirst();
			
			if(c.moveToFirst()){
				try {
					
					tea.setKcal(kk);
					tea.setTEAid(c.getLong(c.getColumnIndexOrThrow("rowid")));
					tea.setEgg(c.getDouble(c.getColumnIndexOrThrow(TeaTable.EGG)));
					tea.setFruit(c.getDouble(c.getColumnIndexOrThrow(TeaTable.FRUIT)));
					tea.setKcal(c.getDouble(c.getColumnIndexOrThrow(TeaTable.KCAL)));
					tea.setMeat(c.getDouble(c.getColumnIndexOrThrow(TeaTable.MEAT)));
					tea.setMilk(c.getDouble(c.getColumnIndexOrThrow(TeaTable.MILK)));
					tea.setNormal(c.getInt(c.getColumnIndexOrThrow(TeaTable.ISNORMAL))>0);
					tea.setOil(c.getDouble(c.getColumnIndexOrThrow(TeaTable.OIL)));
					tea.setRice(c.getDouble(c.getColumnIndexOrThrow(TeaTable.RICE)));
					tea.setSugar(c.getDouble(c.getColumnIndexOrThrow(TeaTable.SUGAR)));
					tea.setVegetable(c.getDouble(c.getColumnIndexOrThrow(TeaTable.VEGETABLE)));
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			
			return tea;
			
		}
		
		public TEA queryTEAbyID(int rowid){
			TEA tea = new TEA();			
			

			String[] selectionArgs = new String[]{Integer.toString(rowid)};
			String[] columns = new String[]{"*","rowid"};
			Log.i("DB", "kcals : "+ selectionArgs[0]);
			SQLiteDatabase myDB = getReadableDatabase();
			
			Cursor c = myDB.query(
									TeaTable.TABLE_NAME, //table
									columns, //columns
									"rowid = ?", //selection
									selectionArgs, //selectionArgs
									null, //groupBy
									null, //having
									null //orderBy
					); 
			
//			c.moveToFirst();
			
			if(c.moveToFirst()){
				try {
					
					tea.setKcal(c.getInt(c.getColumnIndexOrThrow(TeaTable.KCAL)));
					tea.setTEAid(c.getLong(c.getColumnIndexOrThrow("rowid")));
					tea.setEgg(c.getDouble(c.getColumnIndexOrThrow(TeaTable.EGG)));
					tea.setFruit(c.getDouble(c.getColumnIndexOrThrow(TeaTable.FRUIT)));
					tea.setKcal(c.getDouble(c.getColumnIndexOrThrow(TeaTable.KCAL)));
					tea.setMeat(c.getDouble(c.getColumnIndexOrThrow(TeaTable.MEAT)));
					tea.setMilk(c.getDouble(c.getColumnIndexOrThrow(TeaTable.MILK)));
					tea.setNormal(c.getInt(c.getColumnIndexOrThrow(TeaTable.ISNORMAL))>0);
					tea.setOil(c.getDouble(c.getColumnIndexOrThrow(TeaTable.OIL)));
					tea.setRice(c.getDouble(c.getColumnIndexOrThrow(TeaTable.RICE)));
					tea.setSugar(c.getDouble(c.getColumnIndexOrThrow(TeaTable.SUGAR)));
					tea.setVegetable(c.getDouble(c.getColumnIndexOrThrow(TeaTable.VEGETABLE)));
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			
			return tea;
			
		}
		
		private FoodItem queryFoodItem(String category){
			FoodItem fi = new FoodItem();
			String[] selectionArgs = new String[]{category};
			
			SQLiteDatabase myDB = getReadableDatabase();
			
			Cursor c = myDB.query(
									FoodItemTable.TABLE_NAME, //table
									null, //columns
									FoodItemTable.CATEGORY +"=?", //selection
									selectionArgs, //selectionArgs
									null, //groupBy
									null, //having
									FoodItemTable.PRIORITY+" asc" //orderBy
					); 
			
//			c.moveToFirst();
			if(c.moveToFirst()){
				try {
					
					fi.setName(c.getString(c.getColumnIndexOrThrow(FoodItemTable.NAME)));				
					fi.setCategory(c.getString(c.getColumnIndexOrThrow(FoodItemTable.CATEGORY)));
					fi.setConstraints(constraints[c.getInt(c.getColumnIndexOrThrow(FoodItemTable.CONSTRAINTS))]);
					fi.setMeasurement(c.getDouble(c.getColumnIndexOrThrow(FoodItemTable.MEASUREMENT)));
					fi.setPriority(c.getDouble(c.getColumnIndexOrThrow(FoodItemTable.PRIORITY)));
					fi.setUnit(c.getString(c.getColumnIndexOrThrow(FoodItemTable.UNIT)));
					fi.setMealCategory(c.getInt(c.getColumnIndexOrThrow(FoodItemTable.MEAL_CATEGORY)));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			return fi;
		}
		
		public FoodItem queryFoodItemByID(int rowid){
			FoodItem fi = new FoodItem();
			String[] selectionArgs = new String[]{Integer.toString(rowid)};
			String[] columns = new String[]{"*","rowid"};
			
			SQLiteDatabase myDB = getReadableDatabase();
			
			Cursor c = myDB.query(
									FoodItemTable.TABLE_NAME, //table
									columns, //columns
									"rowid=?", //selection
									selectionArgs, //selectionArgs
									null, //groupBy
									null, //having
									null //orderBy
					); 
			
//			c.moveToFirst();
			if(c.moveToFirst()){
				try {
					fi.setRowid(c.getLong(c.getColumnIndexOrThrow("rowid")));
					fi.setName(c.getString(c.getColumnIndexOrThrow(FoodItemTable.NAME)));
					fi.setCategory(c.getString(c.getColumnIndexOrThrow(FoodItemTable.CATEGORY)));
					fi.setConstraints(constraints[c.getInt(c.getColumnIndexOrThrow(FoodItemTable.CONSTRAINTS))]);
					fi.setMeasurement(c.getDouble(c.getColumnIndexOrThrow(FoodItemTable.MEASUREMENT)));
					fi.setPriority(c.getDouble(c.getColumnIndexOrThrow(FoodItemTable.PRIORITY)));
					fi.setUnit(c.getString(c.getColumnIndexOrThrow(FoodItemTable.UNIT)));
					fi.setMealCategory(c.getInt(c.getColumnIndexOrThrow(FoodItemTable.MEAL_CATEGORY)));
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
			
			return fi;
		}
		
		private ArrayList<FoodItem> getFoodItemList(){
			ArrayList<FoodItem> foodItemLists= new ArrayList<FoodItem>();
			
			
			
			for(int x=0;x<categories.length;x++){
				FoodItem foodItemCategory  = new FoodItem();
				foodItemCategory = queryFoodItem(categories[x]);
				foodItemLists.add(foodItemCategory);
				
			}
			
			return foodItemLists;
		}
		
		public Recommendation getRecommendation(){
			
			Recommendation rec = new Recommendation();
			ArrayList<FoodItem> foodItemList = new ArrayList<FoodItem>();
			
			foodItemList = getFoodItemList();
			
			for(int x =0;x<foodItemList.size();x++){
				String cat = "";
				cat = categories[x];
				
				if(cat.equals(TeaTable.VEGETABLE)){
				
					rec.setVegetableName(foodItemList.get(x).getName());
					rec.setVegetableMeasure(foodItemList.get(x).getMeasurement());
					rec.setVegetableServing(foodItemList.get(x).getUnit());
					
				}
				else if(cat.equals(TeaTable.FRUIT)){					
				
					rec.setFruitName(foodItemList.get(x).getName());
					rec.setFruitMeasure(foodItemList.get(x).getMeasurement());
					rec.setFruitServing(foodItemList.get(x).getUnit());					
					
				}
				
				else if(cat.equals(TeaTable.EGG)){
					rec.setEggName(foodItemList.get(x).getName());
					rec.setEggMeasure(foodItemList.get(x).getMeasurement());
					rec.setEggServing(foodItemList.get(x).getUnit());					
				}
					
				else if(cat.equals(TeaTable.MEAT)){
					rec.setMeatName(foodItemList.get(x).getName());
					rec.setMeatMeasure(foodItemList.get(x).getMeasurement());
					rec.setMeatServing(foodItemList.get(x).getUnit());					
				}
				
				else if(cat.equals(TeaTable.MILK)){			
					rec.setMilkName(foodItemList.get(x).getName());
					rec.setMilkMeasure(foodItemList.get(x).getMeasurement());
					rec.setMilkServing(foodItemList.get(x).getUnit());					
				}
					
				else if(cat.equals(TeaTable.OIL)){
					rec.setOilName(foodItemList.get(x).getName());
					rec.setOilMeasure(foodItemList.get(x).getMeasurement());
					rec.setOilServing(foodItemList.get(x).getUnit());					
				}
				else if(cat.equals(TeaTable.RICE)){
					rec.setRiceName(foodItemList.get(x).getName());
					rec.setRiceMeasure(foodItemList.get(x).getMeasurement());
					rec.setRiceServing(foodItemList.get(x).getUnit());					
				}

				else if(cat.equals(TeaTable.SUGAR)){
					rec.setSugarName(foodItemList.get(x).getName());
					rec.setSugarMeasure(foodItemList.get(x).getMeasurement());
					rec.setSugarServing(foodItemList.get(x).getUnit());					
				}
				
			}
			
			return rec;
		}
		
		public ArrayList<MealCategory> getMealCategoryList(){
			
			ArrayList<MealCategory> mcList = new ArrayList<MealCategory>();
			
			
			String query = "SELECT * FROM "+ MealCategory.TABLE_NAME;
			
			SQLiteDatabase myDB = getReadableDatabase();
			
//			Cursor c = myDB.query(
//									MealCategory.TABLE_NAME, //table
//									null, //columns
//									null, //selection
//									null, //selectionArgs
//									null, //groupBy
//									null, //having
//									MealCategory.COL_ID+" asc" //orderBy
//					); 
			
			Cursor c = myDB.rawQuery(query, null);
			
//			c.moveToFirst();
			if(c.moveToFirst()){
				
				do {
					
					try {
						String ID="", mealCode="", description="";
						int divisor=1;
						
						ID = c.getString(c.getColumnIndex(MealCategory.COL_ID));
						mealCode = c.getString(c.getColumnIndex(MealCategory.COL_MEAL_CODE));
						divisor = c.getInt(c.getColumnIndex(MealCategory.COL_DIVISOR));
						description = c.getString(c.getColumnIndex(MealCategory.COL_DESCRIPTION));
						
						MealCategory mc = new MealCategory(ID, mealCode, divisor, description);
						
						mcList.add(mc);
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}					
					
				} while (c.moveToNext());
				
			}
			
			
			
			return mcList;
		}
		
		public ArrayList<FoodItem> getFoodListByCategory(final String category){
			
			ArrayList<FoodItem> FoodList = new ArrayList<FoodItem>();
			
			FoodItem fi = new FoodItem();
			String[] selectionArgs = new String[]{category};
			String[] columns = new String[]{"*","rowid"};
			
			SQLiteDatabase myDB = getReadableDatabase();
			
			Cursor c = myDB.query(
									FoodItemTable.TABLE_NAME, //table
									columns, //columns
									FoodItemTable.CATEGORY +"=?", //selection
									selectionArgs, //selectionArgs
									null, //groupBy
									null, //having
									FoodItemTable.PRIORITY+" asc" //orderBy
					); 
			
			
			if(c.moveToFirst()){
				
				do {
					fi = new FoodItem();
					try {
						fi.setRowid(c.getLong(c.getColumnIndexOrThrow("rowid")));
						fi.setName(c.getString(c.getColumnIndexOrThrow(FoodItemTable.NAME)));
						//fi.setAvailability(c.getDouble(c.getColumnIndexOrThrow(FoodItemTable.AVAILABILTY)));
						fi.setCategory(c.getString(c.getColumnIndexOrThrow(FoodItemTable.CATEGORY)));
						fi.setConstraints(constraints[c.getInt(c.getColumnIndexOrThrow(FoodItemTable.CONSTRAINTS))]);
						fi.setMeasurement(c.getDouble(c.getColumnIndexOrThrow(FoodItemTable.MEASUREMENT)));
						fi.setPriority(c.getDouble(c.getColumnIndexOrThrow(FoodItemTable.PRIORITY)));
						fi.setUnit(c.getString(c.getColumnIndexOrThrow(FoodItemTable.UNIT)));
						fi.setMealCategory(c.getInt(c.getColumnIndexOrThrow(FoodItemTable.MEAL_CATEGORY)));
						
						FoodList.add(fi);
//						Log.i("SWABE", "FoodID: "+ fi.getRowid());
						
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}					
					
				} while (c.moveToNext());
				
			}
			
			
//			Log.i("DB","Size:" +FoodList.size());
			
			return FoodList;
		}
		
		
		public ArrayList<DailyRecommendation> getDailyRecommendation(String date, int MealType){
			ArrayList<DailyRecommendation> dailyRecommendationList = new ArrayList<DailyRecommendation>();
			DailyRecommendation dr;
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
//			java.util.Date datec = new java.util.Date();
			
			String[] columns = new String[]{"*","rowid"};
			String[] selectionArgs = new String[]{date, Integer.toString(MealType)};
			SQLiteDatabase myDB = getReadableDatabase();
			
			Cursor c = myDB.query(
									"DailyRecommendations", //table
									columns, //columns
									DailyRecommendation.DATE_COL+"=? AND "+ DailyRecommendation.MEALTYPE_COL + "=?", //selection
									selectionArgs, //selectionArgs
									null, //groupBy
									null, //having
									null //orderBy
					); 
//			Cursor c = myDB.query(
//					"DailyRecommendations", //table
//					null, //columns
//					null, //selection
//					null, //selectionArgs
//					null, //groupBy
//					null, //having
//					null //orderBy
//					); 
			
			if(c.moveToFirst()){
				
				do {
					dr =  new DailyRecommendation();
					
					try {
						dr.setRowid(c.getInt(c.getColumnIndexOrThrow("rowid")));
						dr.setDate(c.getString(c.getColumnIndexOrThrow(DailyRecommendation.DATE_COL)));						
						dr.setMealType(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.MEALTYPE_COL)));
						dr.setEgg_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.EGG_ID_COL)));
						dr.setFruit_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.FRUIT_ID_COL)));
						dr.setMeat_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.MEAT_ID_COL)));
						dr.setMilk_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.MILK_ID_COL)));
						dr.setRice_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.RICE_ID_COL)));
						dr.setSugar_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.SUGAR_ID_COL)));
						dr.setTEA_ID(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.TEA_ID_COL)));
						dr.setVegetable_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.VEGETABLE_ID_COL)));
						dr.setOil_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.OIL_ID_COL)));
						
						dailyRecommendationList.add(dr);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}					
					
				} while (c.moveToNext());
				
			}
			
			return dailyRecommendationList;
		}
		
		public ArrayList<DailyRecommendation> getDailyRecommendation(String date){
			ArrayList<DailyRecommendation> dailyRecommendationList = new ArrayList<DailyRecommendation>();
			DailyRecommendation dr;
//			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
//			java.util.Date datec = new java.util.Date();
			
			String[] columns = new String[]{"*","rowid"};
			String[] selectionArgs = new String[]{date};
			SQLiteDatabase myDB = getReadableDatabase();
			
			Cursor c = myDB.query(
									"DailyRecommendations", //table
									columns, //columns
									DailyRecommendation.DATE_COL+"=?", //selection
									selectionArgs, //selectionArgs
									null, //groupBy
									null, //having
									null //orderBy
					); 
//			Cursor c = myDB.query(
//					"DailyRecommendations", //table
//					null, //columns
//					null, //selection
//					null, //selectionArgs
//					null, //groupBy
//					null, //having
//					null //orderBy
//					); 
			
			if(c.moveToFirst()){
				
				do {
					dr =  new DailyRecommendation();
					
					try {
						dr.setRowid(c.getInt(c.getColumnIndexOrThrow("rowid")));
						dr.setDate(c.getString(c.getColumnIndexOrThrow(DailyRecommendation.DATE_COL)));						
						dr.setMealType(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.MEALTYPE_COL)));
						dr.setEgg_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.EGG_ID_COL)));
						dr.setFruit_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.FRUIT_ID_COL)));
						dr.setMeat_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.MEAT_ID_COL)));
						dr.setMilk_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.MILK_ID_COL)));
						dr.setRice_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.RICE_ID_COL)));
						dr.setSugar_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.SUGAR_ID_COL)));
						dr.setTEA_ID(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.TEA_ID_COL)));
						dr.setVegetable_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.VEGETABLE_ID_COL)));
						dr.setOil_id(c.getInt(c.getColumnIndexOrThrow(DailyRecommendation.OIL_ID_COL)));
						
						dailyRecommendationList.add(dr);
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}					
					
				} while (c.moveToNext());
				
			}
			
			return dailyRecommendationList;
		}
		
		public long insertDailyRecommendation(DailyRecommendation dailyRec){
			
			long id = 0;
			try {
				
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues initialValues = new ContentValues();
				//initialValues.put("user_id", 1);
				initialValues.put(DailyRecommendation.DATE_COL, dailyRec.getDate());
				initialValues.put(DailyRecommendation.MEALTYPE_COL, dailyRec.getMealType());
				initialValues.put(DailyRecommendation.EGG_ID_COL, dailyRec.getEgg_id());
				initialValues.put(DailyRecommendation.FRUIT_ID_COL, dailyRec.getFruit_id());
				initialValues.put(DailyRecommendation.MEAT_ID_COL, dailyRec.getMeat_id());
				initialValues.put(DailyRecommendation.MILK_ID_COL, dailyRec.getMilk_id());
				initialValues.put(DailyRecommendation.RICE_ID_COL, dailyRec.getRice_id());
				initialValues.put(DailyRecommendation.SUGAR_ID_COL, dailyRec.getSugar_id());
				initialValues.put(DailyRecommendation.TEA_ID_COL, dailyRec.getTEA_ID());
				initialValues.put(DailyRecommendation.VEGETABLE_ID_COL, dailyRec.getVegetable_id());
				initialValues.put(DailyRecommendation.OIL_ID_COL, dailyRec.getOil_id());
				

				Log.i("db path", db.getPath());
				
				id = db.insert("DailyRecommendations", null, initialValues);
				db.close();
			
				//return true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
				//return false;
			}
			return id;
		}
		
		public int updateDailyRecommendation(DailyRecommendation dailyRec){
			
			int id = 0;
			try {
				
				SQLiteDatabase db = this.getWritableDatabase();
				ContentValues initialValues = new ContentValues();
				//initialValues.put("user_id", 1);
				initialValues.put(DailyRecommendation.DATE_COL, dailyRec.getDate());
				initialValues.put(DailyRecommendation.MEALTYPE_COL, dailyRec.getMealType());
				initialValues.put(DailyRecommendation.EGG_ID_COL, dailyRec.getEgg_id());
				initialValues.put(DailyRecommendation.FRUIT_ID_COL, dailyRec.getFruit_id());
				initialValues.put(DailyRecommendation.MEAT_ID_COL, dailyRec.getMeat_id());
				initialValues.put(DailyRecommendation.MILK_ID_COL, dailyRec.getMilk_id());
				initialValues.put(DailyRecommendation.RICE_ID_COL, dailyRec.getRice_id());
				initialValues.put(DailyRecommendation.SUGAR_ID_COL, dailyRec.getSugar_id());
				initialValues.put(DailyRecommendation.TEA_ID_COL, dailyRec.getTEA_ID());
				initialValues.put(DailyRecommendation.VEGETABLE_ID_COL, dailyRec.getVegetable_id());
				initialValues.put(DailyRecommendation.OIL_ID_COL, dailyRec.getOil_id());
				

				Log.i("db path", db.getPath());
				
				id = db.update("DailyRecommendations", initialValues, "rowid=?", new String[]{Integer.toString(dailyRec.getRowid())} );
				db.close();
			
				//return true;
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				
				//return false;
			}
			return id;
		}
		
		
		public void resetDB(){
			
			SQLiteDatabase db = this.getWritableDatabase();
			try {
				  db.execSQL("delete from DailyRecommendations");
				  db.execSQL("delete from User");
				  db.execSQL("delete from UserDetails");				 
			} catch (Exception e) {
				// TODO: handle exception
			}
			  
		}
	

}
