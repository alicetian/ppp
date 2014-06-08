package edu.calstatela.cs594.mapcampusmap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MyListFragment extends ListFragment {

	private Activity mActivity;
	DBAdapter db;

	ArrayAdapter<String> adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		db = new DBAdapter(mActivity);
		db.open();
		List<ClassRoom> classRooms = db.getClassRooms();
		List<String> rooms = new ArrayList<String>();
		for(int i = 0; i < classRooms.size(); i++)
			rooms.add(classRooms.get(i).getName()+"\t\t[show on map]");
		
		adapter = new ArrayAdapter<String>(inflater.getContext(),
				android.R.layout.simple_list_item_1, 0, rooms);
		setListAdapter(adapter);
		return super.onCreateView(inflater, container, savedInstanceState);

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		String s = adapter.getItem(position);
		ClassRoom c = db.getClassRoomByName(s.split("\t")[0]);
		
		Toast.makeText(mActivity, c.toString(), Toast.LENGTH_LONG).show();
		
		Intent i = new Intent(mActivity, ShowLocationOnMapActivity.class);
		i.putExtra("latitude", c.getLat());
		i.putExtra("longitude", c.getLon());
		i.putExtra("name", c.getName());
		mActivity.startActivity(i);
	}
	
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		db.close();
	}

	public ArrayAdapter<String> getAdapter() {
		return adapter;
	}

	public void setAdapter(ArrayAdapter<String> adapter) {
		this.adapter = adapter;
	}
	
}
