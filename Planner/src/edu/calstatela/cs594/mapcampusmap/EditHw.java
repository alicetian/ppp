package edu.calstatela.cs594.mapcampusmap;

import edu.calstatela.cs594.model.Classes;
import edu.calstatela.cs594.model.Homework;
import edu.calstatela.cs594.plannerplusplus.R;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class EditHw extends ActionBarActivity {

	private EditText editClass;
	private EditText editDesc;
	private Button editTime;
	private Button editDate;
	private String className;
	private int classId;
	private String classDesc;
	private String classTime;
	private String classDate;
	private DBAdapterAddClass db;
	public static final int passedId = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_hw);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
		actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayHomeAsUpEnabled(true);
		db = new DBAdapterAddClass(this);
		db.open();
		editClass = (EditText) findViewById(R.id.editClass);
	    editDesc = (EditText) findViewById(R.id.editAssignment);
	    editTime = (Button) findViewById(R.id.editTimeDue);
	    editDate = (Button) findViewById(R.id.editDateDue);
		
		Intent intent = getIntent();
        className = intent.getStringExtra("hw_name");
        classId = intent.getIntExtra("hw_id", passedId);
        classDesc = intent.getStringExtra("hw_desc");
        classTime = intent.getStringExtra("hw_time");
        classDate = intent.getStringExtra("hw_date");
        
        editClass.setText(className);
        editDesc.setText(classDesc);
        editTime.setText(classTime);
        editDate.setText(classDate);
        

		
	}
	@Override
	public Intent getSupportParentActivityIntent(){
		
		
		return new Intent(this, ShowCalendar.class);
	    
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_class, menu);
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
public void updateHw(View view){
		
		editClass = (EditText) findViewById(R.id.editClass);
	    editDesc = (EditText) findViewById(R.id.editAssignment);
	    editTime = (Button) findViewById(R.id.editTimeDue);
	    editDate = (Button) findViewById(R.id.editDateDue);
	    String addClass1 = editClass.getText().toString();
	    String addDesc1 = editDesc.getText().toString();
	    String addTime1 = editTime.getText().toString();
	    String addDate1 = editDate.getText().toString();
	    Homework hw = new Homework(classId, addClass1, addDesc1, addTime1, addDate1);
		
		switch(view.getId()) {
		case R.id.editHwButton:

			// insertUser() method will insert a user and return a row ID
			long id = db.updateHw(hw);
			
			// if the row ID is -1 there was some error, otherwise it was successful
			if (id != -1)
				displayMessage(addClass1 + " has been changed!");
			else
				displayMessage(addClass1 + " was not changed?"); 
			
			db.close();

			break;
		
	}
		
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		
	    
		
	}

public void deleteHw(View view){
	
	switch(view.getId()) {
	case R.id.button1:

		
		int id = db.deleteHw(classId);
		
		
		if (id != -1)
			displayMessage(" has been deleted!");
		else
			displayMessage(" hasn't been deleted"); 
		
		db.close();

		break;
	
}
	Intent intent = new Intent(this, MainActivity.class);
	startActivity(intent);
}


	
	private void displayMessage(String msg) {
	Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
}
}
