package com.example.dietaryapplication;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class PriceActivity extends Activity implements OnMarkerClickListener {
	
//	private final LatLng LOCATION_BURNABY = new LatLng(49.27645, -122.917587);
	private final LatLng LOCATION_ILOILO = new LatLng(10.7167, 122.5667);
	private long foodId;
	
	private String urlGEt = Constants.URL_PARSE_PREFIX + Constants.URL_PARSE_CLASS_PRICE;
	AsyncTaskToGet asyncToGet;
	private JSONObject jsonObject;
	private GoogleMap map;
	private User user;
	private String selectedDate, parseId;
	private EditText etStoreName, etPrice, etFoodName, etUnit, etStoreAddress;
	private Button btnSave;
	private JSONArray jArrayPrice;
	private Price price;
	private ArrayList<Price> priceList = new ArrayList<Price>();
	private ArrayList<Marker> markerList;
	private Marker marker;
//	private JSONObject jsonPutResult;
	private int indexOfMarker = 0;
	
//	private FoodItemHelper fiHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_pricing);
//
//		map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
//		
//		map.setMyLocationEnabled(true);
//		map.addMarker(new MarkerOptions().position(LOCATION_ILOILO).title("Find me here!"));
//		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(LOCATION_ILOILO, 14);
//		map.animateCamera(update);
		
		user = getIntent().getParcelableExtra("user");	
		selectedDate = getIntent().getStringExtra("date");
		foodId = getIntent().getLongExtra("id",0);
//		jsonPutResult = new JSONObject();
//		parseId =  FoodItemHelper.getParseID(PriceActivity.this, foodId);
//		fiHelper = new FoodItemHelper();
		getParseID(PriceActivity.this, foodId);
	}



	
	private void getParseID(Context ctx, long rowid){	
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
			
			private JSONObject foodItemJSON;

			@Override
			public void asyncTaskSuccessful(String data) {
				// TODO Auto-generated method stub
				JSONArray ja;
				JSONObject resultObj;
				try {
					resultObj = new JSONObject(data);
					ja = resultObj.getJSONArray("results");
					foodItemJSON = ja.getJSONObject(0);
					parseId = foodItemJSON.getString("objectId");
					Log.i("FIHELPER", "FoodItemID: "+ parseId);
					
					getPrice();
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
			@Override
			public void asyncTaskFailed(String data) {
				// TODO Auto-generated method stub
				parseId="";				
			}
		});

	}
	
	private void getPrice(){
		JSONObject whereClauseJSON = new JSONObject();
		JSONObject joPointer = new JSONObject();
//		JSONObject joFI = new JSONObject();
		String url = "";	
		priceList = new ArrayList<Price>();
		try {
			joPointer.put("__type","Pointer");
			joPointer.put("className","FoodItem");
			joPointer.put("objectId",parseId);	
//			joFI.put(Constants.PARSE_COLUMN_PRICE_FOODITEM_ID, joPointer);
			whereClauseJSON.put(Constants.PARSE_COLUMN_PRICE_FOODITEM_ID, joPointer);
//			jo.put("where", whereClauseJSON);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			url = Constants.URL_PARSE_PREFIX + Constants.URL_PARSE_CLASS_PRICE +"?where="+ URLEncoder.encode(whereClauseJSON.toString(),"UTF-8")
					+ "&include=storeId,fooditemId";
			Log.i("HELPER", url);
			Log.i("Helper", whereClauseJSON.toString());
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		AsyncTaskToGet asyncTaskPrice = new AsyncTaskToGet(PriceActivity.this, url);
		try {
			asyncTaskPrice.execute().get();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ExecutionException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		asyncTaskPrice.setMainAsyncTaskListener(new  AsyncTaskListener() {
			
//			private JSONObject foodItemJSON;
			
//			private JSONObject priceJSON;

			
			@Override
			public void asyncTaskSuccessful(String data) {
				// TODO Auto-generated method stub
				
				JSONObject resultObj, priceObj;
				
				
				try {
					resultObj = new JSONObject(data);
					priceObj = new JSONObject();
					jArrayPrice = resultObj.getJSONArray("results");
					
					Log.i("PriceActivity", "JArraySize: "+ jArrayPrice.length());
					
					for(int i =0;i<jArrayPrice.length();i++){
						priceObj = jArrayPrice.getJSONObject(i);						
						
						price = new Price();
						price.setFoodId(priceObj.getJSONObject("fooditemId").getString("objectId"));
						price.setFoodprice_original(priceObj.getDouble("foodprice_original"));
						price.setPriceId(priceObj.getString("objectId"));
						price.setStoreId(priceObj.getJSONObject("storeId").getString("objectId"));
						price.setStoreAddress(priceObj.getJSONObject("storeId").getString("address"));
						price.setLatitude(priceObj.getJSONObject("storeId").getJSONObject("coordinates").getDouble("latitude"));
						price.setLongitude(priceObj.getJSONObject("storeId").getJSONObject("coordinates").getDouble("longitude"));
						
						price.setStoreName(priceObj.getJSONObject("storeId").getString("store_name"));
						price.setFoodName(priceObj.getJSONObject("fooditemId").getString("foodName"));
						price.setUnit(priceObj.getString("unit"));
						
						priceList.add(i, price);
//						Log.i("StoreAddress", "Address: "+priceObj.getJSONObject("storeId").getString("address"));
					}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				initButtons();
				initValues(0);
				initMaps();
				
			}
			
			@Override
			public void asyncTaskFailed(String data) {
				// TODO Auto-generated method stub
				parseId="";				
			}
		});
	}
	
	private void initButtons(){
		etFoodName = (EditText)findViewById(R.id.etFoodName);
		etPrice = (EditText)findViewById(R.id.etPrice);
		etStoreName = (EditText)findViewById(R.id.etStoreName);
		etUnit = (EditText)findViewById(R.id.etUnit);
		etStoreAddress = (EditText)findViewById(R.id.etStoreAddress);
		btnSave = (Button)findViewById(R.id.btnSave);
		btnSave.setEnabled(true);
		
		
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnSave.setEnabled(false);
				updatePrice();
			}
		});
	}

	
	private void initValues(int c){
		if(priceList.size()>c){
			etFoodName.setText(priceList.get(c).getFoodName());
			etPrice.setText(" "+priceList.get(c).getFoodprice_original());
			etStoreName.setText(priceList.get(c).getStoreName());
			etUnit.setText(priceList.get(c).getUnit());
			etStoreAddress.setText(priceList.get(c).getStoreAddress());			
			
		}
	}

	
	@SuppressWarnings("deprecation")
	private void initMaps(){
		markerList = new ArrayList<Marker>();
		map  = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		map.setOnMarkerClickListener(this);
//		map.addMarker(new MarkerOptions().position(LOCATION_ILOILO).title("Find me here!"));
		for(int c = 0; c<priceList.size();c++){
			marker = map.addMarker(new MarkerOptions()
					.position(new LatLng(priceList.get(c).getLatitude(), priceList.get(c).getLongitude()))
					.title(priceList.get(c).getStoreName()));
			
			markerList.add(c, marker);			
		}
		
		
		
		map.setMyLocationEnabled(true);
		CameraUpdate update = CameraUpdateFactory.newLatLngZoom(new LatLng(priceList.get(0).getLatitude(),priceList.get(0).getLongitude()), 14);
		map.animateCamera(update);
	}




	@Override
	public boolean onMarkerClick(Marker marker) {
		// TODO Auto-generated method stub
//		int indexOfMark = 0;
//		Log.i("MARKER", "marker"+markerList.size());
//		for(int c=0;c<markerList.size();c++){
//			if(marker.equals(markerList.get(c))){
//				indexOfMark =c;
//			}
//			
//		}
//		Log.i("MARKER", "marker"+indexOfMark);
		
		indexOfMarker  = markerList.indexOf(marker);
		
		initValues(indexOfMarker);
		
		
		
		
//		if(indexOfMarker>0){
//			Toast.makeText(getApplicationContext(), "Marker: "+ indexOfMarker, Toast.LENGTH_LONG).show();
//			Log.i("MARKER", "marker");
//			return true;
//			
//		}
//		
//		if(marker.equals(markerList.get(indexOfMarker))){
//			Toast.makeText(getApplicationContext(), "Marker: "+ indexOfMarker, Toast.LENGTH_LONG).show();
//			Log.i("MARKER", "marker"+indexOfMarker);
//			return true;
//		}
		Log.i("MARKER", "marker"+indexOfMarker);
		
		
		return false;
	}
	
	private void updatePrice(){
		
		
		String priceId = priceList.get(indexOfMarker).getPriceId();
		String url ="";
		url = Constants.URL_PARSE_PREFIX + Constants.URL_PARSE_CLASS_PRICE + "/"+priceId;
		
		Log.i("URLpa", "URLtoPut: "+url);
		
		double newPrice = 0;
		String newUnit="";
		
		newPrice = Double.valueOf(etPrice.getText().toString());
		newUnit = etUnit.getText().toString();
		
		JSONObject joPut = new JSONObject();
		
		try {
			joPut.put(Constants.PARSE_COLUMN_PRICE, newPrice);
			joPut.put(Constants.PARSE_COLUMN_PRICE_UNIT, newUnit);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		AsyncTaskToPut asyncTaskToPut = new AsyncTaskToPut(PriceActivity.this, url, joPut);
		asyncTaskToPut.execute();
		asyncTaskToPut.setMainAsyncTaskListener(new AsyncTaskListener() {
			
			@Override
			public void asyncTaskSuccessful(String data) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Price updated successfully.", Toast.LENGTH_SHORT).show();
				btnSave.setEnabled(true);
			}
			
			@Override
			public void asyncTaskFailed(String data) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "Server error.", Toast.LENGTH_SHORT).show();
				btnSave.setEnabled(true);
			}
		});
	}
	
}
