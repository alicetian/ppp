package edu.calstatela.cs594.mapcampusmap;

import java.util.List;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.Toast;
import edu.calstatela.cs594.model.Homework;
import edu.calstatela.cs594.plannerplusplus.R;

public class ShowCalendar extends ActionBarActivity {
	
	private ListView list;
	private DBAdapterAddClass db;
	private CalendarView cal;
	private List<Homework> values1;
	private ArrayAdapter<Homework> adapter;
	private String date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_show_calendar);
		db = new DBAdapterAddClass(this);
	    db.open();
	    
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
	    actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayHomeAsUpEnabled(true); 
		cal = (CalendarView) findViewById(R.id.calendarView);
		list = (ListView) findViewById(R.id.listView);
		String[] values = new String[] { "Pick a date"
		        };
		
		
		cal.setOnDateChangeListener(new OnDateChangeListener() {
			
		@Override
		public void onSelectedDayChange(CalendarView view, int year, int month,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			int month1 = month + 1;
			
			date = year + "-" + month1 + "-" + dayOfMonth;
			values1 = db.getAssignments(date);
			
			Toast.makeText(getBaseContext(),"Selected Date is\n\n"
				+dayOfMonth+" : "+month1+" : "+year ,
				Toast.LENGTH_LONG).show();
			
			adapter = new ArrayAdapter<Homework>(ShowCalendar.this,
					android.R.layout.simple_list_item_1, values1);
			list.setAdapter(adapter);
		}
	});
		
	
	
		
		if(values1 == null){
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
					android.R.layout.simple_list_item_1, values);
			list.setAdapter(adapter);
			
			
		}
		
		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
				
				Homework hw = (Homework)parent.getAdapter().getItem(position);
			    Intent intent = new Intent(ShowCalendar.this, EditHw.class);
			    intent.putExtra("hw_id", hw.getId());
			    intent.putExtra("hw_name", hw.getClassName());
			    intent.putExtra("hw_desc", hw.getDescription());
			    intent.putExtra("hw_time", hw.getTime());
			    intent.putExtra("hw_date", hw.getDate());
			    startActivity(intent);
			    
			    
			}
		});
		
		
		
		
		
	}
	
	@Override
	public Intent getSupportParentActivityIntent(){
		
		
		return new Intent(this, MainActivity.class);
	    
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.show_calendar, menu);
	    return super.onCreateOptionsMenu(menu);
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
		else if (id == R.id.action_add){
			
			Intent intent = new Intent(this, AddEvent.class);
			startActivity(intent);
			
			
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	
	

}
