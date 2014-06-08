package edu.calstatela.cs594.mapcampusmap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.calstatela.cs594.plannerplusplus.R;

public class ShowLocationOnMapActivity extends Activity implements
		OnMapClickListener {
	Double lat = 0.;
	Double lon = 0.;
	String name = "";

	private GoogleMap mMap;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapfrag);
		mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
				.getMap();

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			name = extras.getString("name");
			lat = extras.getDouble("latitude");
			lon = extras.getDouble("longitude");
		}
		mMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon))
				.title(name));
		mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon),
				18.0f));
		mMap.setOnMapClickListener(this);
	}

	@Override
	public void onMapClick(LatLng arg0) {
		Intent i = new Intent(this, AddCoordinates.class);
		startActivity(i);
	}

}
