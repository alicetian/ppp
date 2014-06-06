package edu.calstatela.cs594.worldweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;


public class Map extends Activity implements OnMapClickListener {
	// The Map Object
	private GoogleMap mMap;
	
	private static final double CAMERA_LNG = -118.168;
	private static final double CAMERA_LAT = 34.012;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.worldmap);
		
		
		mMap = ((MapFragment) getFragmentManager().findFragmentById(
				R.id.map)).getMap();

		mMap.moveCamera( CameraUpdateFactory.newLatLngZoom(new LatLng(
				CAMERA_LAT, CAMERA_LNG) , 10.0f) );

		mMap.setMyLocationEnabled(false); // false to disable
		mMap.setOnMapClickListener(this);
	}

	@Override
	public void onMapClick(LatLng point) {
		mMap.animateCamera( CameraUpdateFactory.newLatLngZoom(point , 17.0f) );
		Intent i = new Intent(Map.this, WeatherAtSelectedPoint.class);
		i.putExtra("latitude", point.latitude);
		i.putExtra("longitude", point.longitude);
		startActivity(i);
	}

}
