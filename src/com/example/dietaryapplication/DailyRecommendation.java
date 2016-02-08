package com.example.dietaryapplication;

import java.sql.Date;



public class DailyRecommendation {
	public static final int BREAKFAST = 1;
	public static final int LUNCH = 2;
	public static final int DINNER = 3;
	public static final String DATE_COL = "Date";
	public static final String MEALTYPE_COL = "MealType";
	public static final String TEA_ID_COL = "TEA_ID";
	public static final String VEGETABLE_ID_COL = "vegetable_id";
	public static final String FRUIT_ID_COL = "fruit_id";
	public static final String MILK_ID_COL = "milk_id";
	public static final String RICE_ID_COL = "rice_id";
	public static final String MEAT_ID_COL = "meat_id";
	public static final String EGG_ID_COL = "egg_id";
	public static final String SUGAR_ID_COL = "sugar_id";
	public static final String OIL_ID_COL = "oil_id";
	
	private int MealType;
	private String date;
	private int TEA_ID, vegetable_id, fruit_id, milk_id, rice_id, meat_id, egg_id, sugar_id, oil_id;
	private int rowid;
	
	public DailyRecommendation(int mealType, String date, int tEA_ID,
			int vegetable_id, int fruit_id, int milk_id, int rice_id,
			int meat_id, int egg_id, int sugar_id, int oil_id, int rowid) {
		super();
		MealType = mealType;
		this.date = date;
		TEA_ID = tEA_ID;
		this.vegetable_id = vegetable_id;
		this.fruit_id = fruit_id;
		this.milk_id = milk_id;
		this.rice_id = rice_id;
		this.meat_id = meat_id;
		this.egg_id = egg_id;
		this.sugar_id = sugar_id;
		this.oil_id = oil_id;
	}
	
	public DailyRecommendation() {
		// TODO Auto-generated constructor stub
	}

	public int getMealType() {
		return MealType;
	}
	public void setMealType(int mealType) {
		MealType = mealType;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getTEA_ID() {
		return TEA_ID;
	}
	public void setTEA_ID(int tEA_ID) {
		TEA_ID = tEA_ID;
	}
	public int getVegetable_id() {
		return vegetable_id;
	}
	public void setVegetable_id(int vegetable_id) {
		this.vegetable_id = vegetable_id;
	}
	public int getFruit_id() {
		return fruit_id;
	}
	public void setFruit_id(int fruit_id) {
		this.fruit_id = fruit_id;
	}
	public int getMilk_id() {
		return milk_id;
	}
	public void setMilk_id(int milk_id) {
		this.milk_id = milk_id;
	}
	public int getRice_id() {
		return rice_id;
	}
	public void setRice_id(int rice_id) {
		this.rice_id = rice_id;
	}
	public int getMeat_id() {
		return meat_id;
	}
	public void setMeat_id(int meat_id) {
		this.meat_id = meat_id;
	}
	public int getEgg_id() {
		return egg_id;
	}
	public void setEgg_id(int egg_id) {
		this.egg_id = egg_id;
	}
	public int getSugar_id() {
		return sugar_id;
	}
	public void setSugar_id(int sugar_id) {
		this.sugar_id = sugar_id;
	}
	public int getOil_id() {
		return oil_id;
	}
	public void setOil_id(int oil_id) {
		this.oil_id = oil_id;
	}

	public int getRowid() {
		return rowid;
	}

	public void setRowid(int rowid) {
		this.rowid = rowid;
	}
	
	
	
}