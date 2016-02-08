package com.example.dietaryapplication;

public class MealCategory {
	
	public static final String COL_ID = "meal_id";
	public static final String COL_MEAL_CODE = "meal_code";
	public static final String COL_DESCRIPTION = "description";
	public static final String COL_DIVISOR = "divisor";
	public static final String TABLE_NAME = "MealCategory";
	
	private String ID = "";
	private String MealCode = "";
	private int divisor = 1;
	private String description = "";
	
	
	public MealCategory(String iD, String mealCode, int divisor,
			String description) {
		super();
		ID = iD;
		MealCode = mealCode;
		this.divisor = divisor;
		this.description = description;
	}
	
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getMealCode() {
		return MealCode;
	}
	public void setMealCode(String mealCode) {
		MealCode = mealCode;
	}
	public int getDivisor() {
		return divisor;
	}
	public void setDivisor(int divisor) {
		this.divisor = divisor;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
