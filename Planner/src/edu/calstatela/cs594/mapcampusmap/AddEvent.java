package edu.calstatela.cs594.mapcampusmap;



import java.util.Calendar;

import edu.calstatela.cs594.plannerplusplus.R;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class AddEvent extends ActionBarActivity {

	private int hour;
    private int minute;
    
	
	private int year;
	private int month;
	private int day;
	static final int DATE_DIALOG_ID = 999;
	static final int TIME_DIALOG_ID = 1000;
	EditText addClass;
	EditText addAssignment;
	private Button setTime;
	private Button setDate;
	private DBAdapterAddClass db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_event);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
		actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayHomeAsUpEnabled(true); 
	    Button redirectBtn = (Button) findViewById(R.id.redirectButton);
	    setTime = (Button) findViewById(R.id.timeDue);
	    setDate = (Button) findViewById(R.id.dateDue);
	    

	    // set current date into datepicker
	    
	    db = new DBAdapterAddClass(this);
	    db.open();
	    
        setTime.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(TIME_DIALOG_ID);
			}
		});
        
setDate.setOnClickListener(new View.OnClickListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);
			}
		});
	    
	}
	@Override
	public Intent getSupportParentActivityIntent(){
		
		
		return new Intent(this, MainActivity.class);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.add_event, menu);
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
			View rootView = inflater.inflate(R.layout.fragment_add_event,
					container, false);
			return rootView;
		}
	}

	@Override
	
	    protected Dialog onCreateDialog(int id) {

	        if (id == TIME_DIALOG_ID){
	
	       
	
	            // set time picker as current time
	            
	            return new TimePickerDialog(this, timePickerListener, hour, minute,false);	
	        }
	        
	        else if (id == DATE_DIALOG_ID){
			   // set date picker as current date
	        	Calendar c = Calendar.getInstance();
	        	year = c.get(Calendar.YEAR);
	        	month = c.get(Calendar.MONTH);
	        	day = c.get(Calendar.DAY_OF_MONTH); 
	        	return new DatePickerDialog(this, datePickerListener, 
	                         year, month,day);
			
	        }
	        return null;
	
	}
	
	 
	
	    private TimePickerDialog.OnTimeSetListener timePickerListener =  new TimePickerDialog.OnTimeSetListener() {
	
	        public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
	
	            hour = selectedHour;
	
	            minute = selectedMinute;

	            
	            if (minute <= 9){
	            	
	            	if(hour > 12){
	            		int hour1 = hour - 12;
	            		
	            		if(hour1 == 0)
	            			setTime.setText(12 + ":0" + minute + "pm");
	            		else
	            			setTime.setText(hour1 + ":0" + minute + "pm");
	            			
	            	}
	            		
	            	else{
	            		
	            		if(hour == 0)
	            			setTime.setText(12 + ":0" + minute + "am");
	            		else
	            			setTime.setText(hour + ":0" + minute + "am");
	            		
	            	}
	            	
	            }
	            else {
	            	
	            	if(hour >= 12){
	            		
	            		int hour1 = hour - 12;
	            		
	            		if(hour1 == 0)
	            			setTime.setText(12 + ":"+ minute + "pm");
	            		else
	            			setTime.setText(hour1 + ":"+ minute + "pm");
	            	}
	            	else{
	            		
	            		if(hour == 0)
	            			setTime.setText(12 + ":" + minute + "am");
	            		else
	            			setTime.setText(hour + ":" + minute + "am");
	            		
	            		
	            	}
	            	
	            }
	            	
	
	         
	        }
	
	    };
	    
	    private static String padding_str(int c) {
	    	
	    	        if (c >= 10)
	    	
	    	           return String.valueOf(c);
	    	
	    	        else
	    	
	    	           return "0" + String.valueOf(c);
	    	
	    	    }
	    
	    private DatePickerDialog.OnDateSetListener datePickerListener 
        = new DatePickerDialog.OnDateSetListener() {

// when dialog box is closed, below method will be called.
	public void onDateSet(DatePicker view, int selectedYear,
			int selectedMonth, int selectedDay) {
		year = selectedYear;
		month = selectedMonth;
		day = selectedDay;
	
		setDate.setText(year + "-" + (month + 1) + "-" + day);
	
	
	}
	};
	public void addEvent(View view){
	
		addClass = (EditText) findViewById(R.id.addClass);
	    addAssignment = (EditText) findViewById(R.id.addAssignment);
	    setTime = (Button) findViewById(R.id.timeDue);
	    setDate = (Button) findViewById(R.id.dateDue);
	    String message = addClass.getText().toString();
	    String message1 = addAssignment.getText().toString();
	    String time = setTime.getText().toString();
	    String date = setDate.getText().toString();
		
		switch(view.getId()) {
		case R.id.redirectButton:
	
			// insertUser() method will insert a user and return a row ID
			long id = db.insertHW(message, message1, time, date);
			
			// if the row ID is -1 there was some error, otherwise it was successful
			if (id != -1)
				displayMessage(message + " inserted!");
			else
				displayMessage(message + " wasn't inserted?"); 
			
			db.close();
	
			break;
		
	}
	
	finish();

    
	
}

private void displayMessage(String msg) {
	Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
}

}
