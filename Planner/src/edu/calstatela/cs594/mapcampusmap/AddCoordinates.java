package edu.calstatela.cs594.mapcampusmap;

import edu.calstatela.cs594.plannerplusplus.R;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AddCoordinates extends ActionBarActivity implements EntryView.OnItemAddedListener  {

//	private EditText addName;
//	private EditText addLon;
//	private EditText addLat;
//	private DBAdapterAddClass db;
	private DBAdapter db;

	private Double pointLat = 0.;
	private Double pointLon = 0.;
	private String pointName = "";

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_classroom);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
		
	    actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayHomeAsUpEnabled(true); 
	    
	    db = new DBAdapter(this);
		db.open();
		if (null != getIntent().getExtras()) {
			Bundle extras = getIntent().getExtras();
			if (extras != null) {
				pointName = extras.getString("name");
				pointLat = extras.getDouble("latitude");
				pointLon = extras.getDouble("longitude");
			}
			this.onLocationEntered(pointName, pointLat, pointLon);
		}
		MyListFragment mList = (MyListFragment) getFragmentManager()
				.findFragmentById(R.id.cr_listFragment);
		Drawable bk = getResources().getDrawable(R.drawable.border);
		mList.getView().setBackgroundDrawable(bk);
		/*
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		*/
	}
	@Override
	public Intent getSupportParentActivityIntent(){
		
		
		return new Intent(this, MainActivity.class);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_coordinates, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_add_coordinates,
					container, false);
			return rootView;
		}
	}
	 */
	
	@Override
	public void onLocationEntered(String name, Double lat, Double lon) {

		Log.i("Class Room", "Entry: " + name);
		MyListFragment mList = (MyListFragment) getFragmentManager()
				.findFragmentById(R.id.cr_listFragment);
		name = name.trim().isEmpty() ? "NameMissing" : name;
		String roomInfo = name  + "\t\t[show on map]";
		mList.getAdapter().add(roomInfo);
		mList.getAdapter().notifyDataSetChanged();
		db.insertClassRoom(name, lat, lon);
		Fragment inputFrag = (Fragment) getFragmentManager().findFragmentById(
				R.id.cr_entryFragment);
		EditText nameText = (EditText) inputFrag.getView().findViewById(
				R.id.nameText);
		EditText latText = (EditText) inputFrag.getView().findViewById(
				R.id.latText);
		EditText lonText = (EditText) inputFrag.getView().findViewById(
				R.id.lonText);
		String msg = "Location " + name + " has been recorded.";
		nameText.setHint(name);
		latText.setHint(lat.toString());
		lonText.setHint(lon.toString());
		Toast toast = Toast.makeText(getApplicationContext(), msg,
				Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP, 60, 125);
		toast.show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		db.close();
	}



}
