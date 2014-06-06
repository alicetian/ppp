package com.example.gridview;

import edu.calstatela.cs594.plannerplusplus.R;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class AddCoordinates extends ActionBarActivity {

	private EditText addName;
	private EditText addLon;
	private EditText addLat;
	private DBAdapterAddClass db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_coordinates);
		
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
		
	    actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayHomeAsUpEnabled(true); 
	    db = new DBAdapterAddClass(this);
		db.open();
		
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
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
	 */
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
	
public void addCoordinates(View view){
		
		addName = (EditText) findViewById(R.id.editText1);
	    addLon = (EditText) findViewById(R.id.editText3);
	    addLat = (EditText) findViewById(R.id.editText2);
	    
	    String addName2 = addName.getText().toString();
	    Double longitude = Double.parseDouble(addLon.getText().toString());
	    Double latitude = Double.parseDouble(addLat.getText().toString());
	    
	    
		
		switch(view.getId()) {
		case R.id.button1:

			// insertUser() method will insert a user and return a row ID
			long id = db.insertCOO(addName2, longitude, latitude);
			
			// if the row ID is -1 there was some error, otherwise it was successful
			if (id != -1)
				displayMessage(addName2 + " inserted!");
			else
				displayMessage(addName2 + " wasn't inserted?"); 
			
			db.close();

			break;
		
	}
		
		
		finish();
		
	    
		
	}

	private void displayMessage(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
	}

}
