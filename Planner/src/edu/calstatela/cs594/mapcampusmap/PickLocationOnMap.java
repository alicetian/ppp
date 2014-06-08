package edu.calstatela.cs594.mapcampusmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;

import edu.calstatela.cs594.plannerplusplus.R;

public class PickLocationOnMap extends Activity implements OnMapClickListener {

	private Double lat;
	private Double lon;
	private String name;
	private static final double CAMERA_LNG = -118.168179;
	private static final double CAMERA_LAT = 34.065663;

	private int idx = 0;
	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapfrag);

		if (null != getIntent().getExtras()) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				name = extras.getString("name");
			}
		}

		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				CAMERA_LAT, CAMERA_LNG), 13.0f));
		mMap.setOnMapClickListener(this);
	}

	@Override
	public void onMapClick(LatLng point) {
		Intent i = new Intent(this, AddCoordinates.class);
		i.putExtra("name", name);
		i.putExtra("latitude", point.latitude);
		i.putExtra("longitude", point.longitude);
		startActivity(i);
	}

}
