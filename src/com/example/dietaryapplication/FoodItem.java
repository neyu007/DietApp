package com.example.dietaryapplication;

public class FoodItem {
	
	private String name, category, constraints, unit;
	private double availability, priority, measurement;
	private int mealCategory;
	private long rowid;
	private String parseId;
	private double original_price=0, user_input_price=0;
	
	public FoodItem(){
		
	}
	
	
	public long getRowid() {
		return rowid;
	}
	public void setRowid(long rowid) {
		this.rowid = rowid;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getConstraints() {
		return constraints;
	}
	public void setConstraints(String constraints) {
		this.constraints = constraints;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public double getAvailability() {
		return availability;
	}
	public void setAvailability(double availability) {
		this.availability = availability;
	}
	public double getPriority() {
		return priority;
	}
	public void setPriority(double priority) {
		this.priority = priority;
	}
	public double getMeasurement() {
		return measurement;
	}
	public void setMeasurement(double measurement) {
		this.measurement = measurement;
	}

	public int getMealCategory() {
		return mealCategory;
	}

	public void setMealCategory(int mealCategory) {
		this.mealCategory = mealCategory;
	}


	public String getParseId() {
		return parseId;
	}


	public void setParseId(String parseId) {
		this.parseId = parseId;
	}


	public double getOriginal_price() {
		return original_price;
	}


	public void setOriginal_price(double original_price) {
		this.original_price = original_price;
	}


	public double getUser_input_price() {
		return user_input_price;
	}


	public void setUser_input_price(double user_input_price) {
		this.user_input_price = user_input_price;
	}
	
	
	

}
