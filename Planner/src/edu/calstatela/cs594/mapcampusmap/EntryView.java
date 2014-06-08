package edu.calstatela.cs594.mapcampusmap;

import edu.calstatela.cs594.plannerplusplus.R;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class EntryView extends Fragment {

	public interface OnItemAddedListener {
		public void onLocationEntered(String name, Double lat, Double lon);
	}
	
	private Activity mActivity;
	OnItemAddedListener mCallback;
	Button enterButton;
	Button mapButton;
	EditText nameText;
	EditText latText;
	EditText lonText;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.entry_classroom_layout,
				container, true);
//		view.setBackgroundColor(Color.CYAN);
		enterButton = (Button) view.findViewById(R.id.enterButton);
		mapButton = (Button) view.findViewById(R.id.mapButton);
		nameText = (EditText) view.findViewById(R.id.nameText);
		latText = (EditText) view.findViewById(R.id.latText);
		lonText = (EditText) view.findViewById(R.id.lonText);

		enterButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name = nameText.getText().toString();
				String latString = latText.getText().toString();
				String lonString = lonText.getText().toString();
				Double lat = Double.parseDouble(latString.isEmpty() ? "0." : latString);
				Double lon = Double.parseDouble(lonString.isEmpty() ? "0." : lonString);

				mCallback.onLocationEntered(name, lat, lon);

			}
		});

		mapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String name = nameText.getText().toString();
				Intent i = new Intent(mActivity, PickLocationOnMap.class);
				i.putExtra("name", name);
				mActivity.startActivity(i);
			}
		});

		return view;

	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		mActivity = activity;

		try {
			mCallback = (OnItemAddedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnItemAddedListener");
		}

	}

}
