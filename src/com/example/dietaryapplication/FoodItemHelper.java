package com.example.dietaryapplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class FoodItemHelper {
	
	private FoodItem foodItem;
	private JSONObject foodItemJSON;
	private String id=""; 

	public String getParseID(Context ctx, long rowid){	
//		JSONObject jo = new JSONObject();
		JSONObject whereClauseJSON = new JSONObject();
		String url = "";		
		
		try {
			whereClauseJSON.put("foodItemId", rowid);
//			jo.put("where", whereClauseJSON);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			url = Constants.URL_PARSE_PREFIX + Constants.URL_PARSE_CLASS_FOODITEM +"?where="+ URLEncoder.encode(whereClauseJSON.toString(),"UTF-8");
			Log.i("HELPER", url);
			Log.i("Helper", whereClauseJSON.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AsyncTaskToGet asyncTaskToGet = new AsyncTaskToGet(ctx, url);
		asyncTaskToGet.execute();
		asyncTaskToGet.setMainAsyncTaskListener(new  AsyncTaskListener() {
			
			@Override
			public void asyncTaskSuccessful(String data) {
				// TODO Auto-generated method stub
				JSONArray ja;
				JSONObject resultObj;
				try {
					resultObj = new JSONObject(data);
					ja = resultObj.getJSONArray("results");
					foodItemJSON = ja.getJSONObject(0);
					id = foodItemJSON.getString("objectId");
					Log.i("FIHELPER", "FoodItemID: "+ id);
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void asyncTaskFailed(String data) {
				// TODO Auto-generated method stub
				id="";				
			}
		});
		
		return id;
	}

}
