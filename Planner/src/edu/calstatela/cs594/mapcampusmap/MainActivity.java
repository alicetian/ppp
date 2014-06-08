package edu.calstatela.cs594.mapcampusmap;



import edu.calstatela.cs594.plannerplusplus.R;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

	@Override

		public void onCreate(Bundle savedInstanceState) {
		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_main);
		    ActionBar actionBar = getSupportActionBar();
			actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
		    GridView gridview = (GridView) findViewById(R.id.gridview);
		    gridview.setAdapter(new ImageAdapter(this));

		    
		    
		    gridview.setOnItemClickListener(new OnItemClickListener() {
		        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		        	Intent intent = null;
			     	   
		     	    
		            if (position == 0){
		            	intent = new Intent(MainActivity.this, AddEvent.class);
		            }
		            
		            else if (position == 1){
		            	intent = new Intent(MainActivity.this, ShowCalendar.class);
		            }
		            
		            else if (position == 3){
		            	intent = new Intent(MainActivity.this, ViewClasses.class);
		            }
		            
		            else if (position == 2){
		            	intent = new Intent(MainActivity.this, AddClass.class);
		            }
		            
		            else if (position == 4){
		            	intent = new Intent(MainActivity.this, AddCoordinates.class);
		            }
		            startActivity(intent);
		        }
		    });
		}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
	

}
