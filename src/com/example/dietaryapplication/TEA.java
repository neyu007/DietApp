package com.example.dietaryapplication;

public class TEA {
	private double kcal, vegetable, fruit, milk, rice, meat, egg, oil, sugar;
	private boolean isNormal=true;
	private long TEAid;
	

	public TEA() {
		
	}

	
	public long getTEAid() {
		return TEAid;
	}


	public void setTEAid(long tEAid) {
		TEAid = tEAid;
	}


	public double getKcal() {
		return kcal;
	}

	public void setKcal(double kcal) {
		this.kcal = kcal;
	}

	public double getVegetable() {
		return vegetable;
	}

	public void setVegetable(double vegetable) {
		this.vegetable = vegetable;
	}

	public double getFruit() {
		return fruit;
	}

	public void setFruit(double fruit) {
		this.fruit = fruit;
	}

	public double getMilk() {
		return milk;
	}

	public void setMilk(double milk) {
		this.milk = milk;
	}

	public double getRice() {
		return rice;
	}

	public void setRice(double rice) {
		this.rice = rice;
	}

	public double getMeat() {
		return meat;
	}

	public void setMeat(double meat) {
		this.meat = meat;
	}

	public double getEgg() {
		return egg;
	}

	public void setEgg(double egg) {
		this.egg = egg;
	}

	public double getOil() {
		return oil;
	}

	public void setOil(double oil) {
		this.oil = oil;
	}

	public double getSugar() {
		return sugar;
	}

	public void setSugar(double sugar) {
		this.sugar = sugar;
	}

	public boolean isNormal() {
		return isNormal;
	}

	public void setNormal(boolean isNormal) {
		this.isNormal = isNormal;
	}

	
	

}
