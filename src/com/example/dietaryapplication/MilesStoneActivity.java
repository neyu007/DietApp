package com.example.dietaryapplication;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

public class MilesStoneActivity extends Activity {

	private LineGraphSeries<DataPoint> series;
	private LineGraphSeries<DataPoint> series2;
	private User user;	
	private ArrayList<GraphV> gvList;
	private DataBaseHelper dbHelper;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_milestone);
		
		user = getIntent().getParcelableExtra("user");
		GraphView graph = (GraphView) findViewById(R.id.graph);
		Calendar date  = Calendar.getInstance();
		gvList =  new ArrayList<GraphV>();
		dbHelper = DataBaseHelper.getInstance(getApplicationContext());
		
		gvList = dbHelper.graphQuery();
		
		Date d1 = date.getTime();
		Date d2 = new Date();
		d2 = date.getTime();
		date.add(Calendar.DAY_OF_YEAR, 1);
		
		
		
		
		Viewport viewport = graph.getViewport();
		viewport.setYAxisBoundsManual(true);
		viewport.setMinY(0);
		viewport.setMaxY(150);
		viewport.setMinX(0);
		viewport.setMaxX(10);
		viewport.setScrollable(true);
		
		series = new LineGraphSeries<DataPoint>();
		series2 = new LineGraphSeries<DataPoint>();
	
//		try {
//			series.appendData(new DataPoint(sdf.parse(user.getUpdateDate()), user.getWeight()),true,10);
//			series2.appendData(new DataPoint(sdf.parse(user.getUpdateDate()), user.getDBW()),true,10);
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		series.appendData(new DataPoint(0, user.getWeight()),true,10);
//		series2.appendData(new DataPoint(0, user.getDBW()),true,10);
		Log.i("MSact", "GVLsize: "+gvList.size());
		for(int c = 0; c<gvList.size();c++){
			series.appendData(new DataPoint(c, gvList.get(c).getWeight()),true,10);
			Log.i("WEIGHT:", "weight: "+ gvList.get(c).getWeight());
			series2.appendData(new DataPoint(c, gvList.get(c).getDbw()),true,10);
			Log.i("DBW:", "DBW: "+ gvList.get(c).getDbw());
		}
		
		series.setDataPointsRadius(3);
		series.setTitle("Weight");
		series.setColor(Color.RED);
		series2.setDataPointsRadius(3);
		series2.setTitle("DBW");
		series2.setColor(Color.BLUE);
		
		graph.addSeries(series);
		graph.addSeries(series2);
		graph.getLegendRenderer().setVisible(true);
		graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
//		graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(MilesStoneActivity.this));
//		graph.getGridLabelRenderer().setNumHorizontalLabels(2); // only 4 because of the space

	}

}
