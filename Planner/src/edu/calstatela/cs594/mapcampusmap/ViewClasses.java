package edu.calstatela.cs594.mapcampusmap;


import java.util.List;

import edu.calstatela.cs594.model.Classes;
import edu.calstatela.cs594.plannerplusplus.R;
import android.app.ListActivity;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ViewClasses extends ActionBarActivity {

	
	
	private DBAdapterAddClass db;
	private static List<Classes> classSchedule;
	ListView list;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.list_classes);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0000ff")));
	    actionBar.setDisplayUseLogoEnabled(false);
	    actionBar.setDisplayHomeAsUpEnabled(true); 
		
		list = (ListView) findViewById(R.id.listview);

	    
		db = new DBAdapterAddClass(this);
		db.open();
		
		
	    
		
	    classSchedule = db.getAllClasses();
		
		list.setAdapter(new ArrayAdapter<Classes>(this, android.R.layout.simple_list_item_1, classSchedule));

		
		//listView.setTextFilterEnabled(true);

		list.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    // When clicked, show a toast with the TextView text
				
				Classes classes = (Classes)parent.getAdapter().getItem(position);
			    Intent intent = new Intent(ViewClasses.this, EditClass.class);
			    intent.putExtra("class_id", classes.getId());
			    intent.putExtra("class_name", classes.getClassName());
			    intent.putExtra("class_desc", classes.getDescription());
			    intent.putExtra("class_times", classes.getTime());
			    intent.putExtra("class_days", classes.getDays());
			    intent.putExtra("class_roomNumber", classes.getRoomNumber());
			    startActivity(intent);
			    
			    
			}
		});

	}
	
	@Override
	public Intent getSupportParentActivityIntent(){
		
		
		return new Intent(this, MainActivity.class);
	    
	}
	public void onResume(){
		super.onResume();
		db.open();
		classSchedule = db.getAllClasses();
		
		list.setAdapter(new ArrayAdapter<Classes>(this, android.R.layout.simple_list_item_1, classSchedule));
		
		
	}
	protected void onPause() {
	    super.onPause();
	    	
	    db.close();
	  }
}