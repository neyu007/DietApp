package com.example.dietaryapplication;

import android.util.Log;

public class DietaryHelper {
	
	public static double DBWinKGS(int years, String gender, int htInches){
		
		double kgs = 0;
		double lbs = 0;
		
		Log.d("Height", "Height: "+ htInches);
		Log.d("Gender", "Gender: "+ gender);
		Log.d("Age", "Age: "+ years);
		if(years<19){
			kgs = (years*2)+8;			
		}else if(years>18){
			if(gender.equals("male")){
				lbs = (112+(htInches-60)*4);
			}else{
				lbs = (106+(htInches-60)*4);
			}
			Log.d("POUNDS", "Pounds: "+ lbs);
			kgs = lbs/2.2;
		}
		
		return kgs;
	}	
	
	
	public static double TEA(double DBWinKG, double PhysicalFactor){
		double tea=0;
		tea = DBWinKG*PhysicalFactor;		
		return tea;		
	}
	
	public static double TEAcurrent(double currentWeightInKG, double PhysicalFactor){
		double tea=0;
		tea = currentWeightInKG*PhysicalFactor;		
		return tea;		
	}
}
