package edu.calstatela.cs594.worldweather;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;



public class DisplayCurrentWeather extends ListActivity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener, LocationListener {

	private static final long POLLING_FREQ = 1000 * 3600;
	private static final long FASTES_UPDATE_FREQ = 1000 * 600;
	// Define an object that holds accuracy and frequency parameters
	LocationRequest mLocationRequest;

	TextView titleBar;
	ImageView currentWeatherView;
	TextView tempView;
	TextView descView;
	Button mapButton;
	private LocationClient mLocationClient;

	Double lat = 35.;
	Double lon = 139.;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_currentweather);
		titleBar = (TextView)findViewById(R.id.cityinfo);
		currentWeatherView = (ImageView) findViewById(R.id.currentWeatherView);
		tempView = (TextView) findViewById(R.id.tempView);
		descView = (TextView) findViewById(R.id.descView);
		mapButton = (Button) findViewById(R.id.mapbutton);
		
		if (!servicesAvailable()) 
			finish();

		mapButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivity(new Intent(DisplayCurrentWeather.this,
						Map.class));
			}
		});

		
		// Create new Location Client. This class will handle callbacks
		mLocationClient = new LocationClient(this, this, this);

		// Create and define the LocationRequest
		mLocationRequest = LocationRequest.create();

		// Use high accuracy
		mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

		// Update every 1 hour
		mLocationRequest.setInterval(POLLING_FREQ);

		// Receive updates no more often than every 10 minutes
		mLocationRequest.setFastestInterval(FASTES_UPDATE_FREQ);


		// Update with the current weather data
//		new HttpGetTask2().execute();
		// Update with the forecast data
//		new HttpGetTask().execute();
		
			}
	
	@Override
	protected void onStart() {
		super.onStart();
		mLocationClient.connect();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
		if (status != ConnectionResult.SUCCESS) {
			Toast.makeText(this, "Install Google Play Services.",
					Toast.LENGTH_SHORT).show();
			finish();
		}else{
		
			
		}
	}
	
	
	@Override
	protected void onPause() {
		// Stop updates
		mLocationClient.removeLocationUpdates(this);
		super.onPause();
	}

	@Override
	protected void onStop() {
		// Stop updates
		mLocationClient.removeLocationUpdates(this);

		// Disconnect from LocationServices
		mLocationClient.disconnect();

		super.onStop();
	}


	@Override
	public void onConnected(Bundle arg0) {
		mLocationClient.requestLocationUpdates(mLocationRequest, this);
		Location mCurrentLocation = mLocationClient.getLastLocation();
		lat = mCurrentLocation.getLatitude();
		lon = mCurrentLocation.getLongitude();
		// Update with the current weather data
		new HttpGetTask2().execute();
		// Update with the forecast data
		new HttpGetTask().execute();
		
	}

	@Override
	public void onLocationChanged(Location location) {
		lat = location.getLatitude();
		lon = location.getLongitude();
		// Update with the current weather data
		new HttpGetTask2().execute();
		// Update with the forecast data
		new HttpGetTask().execute();
	}
	
	@Override
	public void onDisconnected() {
		// TODO Auto-generated method stub
		
	}

	private boolean servicesAvailable() {

		// Check that Google Play Services are available
		int resultCode = GooglePlayServicesUtil
				.isGooglePlayServicesAvailable(this);

		// If Google Play services is available

		return (ConnectionResult.SUCCESS == resultCode);

	}

	private class HttpGetTask2 extends AsyncTask<Void, Void, List<String>> {

//		private static final String URL = "http://api.openweathermap.org/data/2.5/weather?lat=35&lon=139";
		private String URL = "http://api.openweathermap.org/data/2.5/weather?lat="
				+ lat + "&lon=" + lon;

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<String> doInBackground(Void... params) {
			HttpGet request = new HttpGet(URL);
			JSONResponseHandler2 responseHandler = new JSONResponseHandler2();
			try {
				return mClient.execute(request, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			if (null != mClient)
				mClient.close();
			
			String weatherDescription = result.get(0).toLowerCase();
			String temperature = result.get(1);
			Double tempInC = Double.parseDouble(temperature) - 273.15;
			Double tempInF = Double.parseDouble(temperature) * 9./5. - 459.67;
			tempInC = (int)(tempInC*100)/100.;
			tempInF = (int)(tempInF*100) / 100.;
			descView.setText(weatherDescription);
//			String loc = String.valueOf(lat) + ", " + String.valueOf(lon);
//			descView.setText(loc);
			final String DEGREE  = "\u00b0";
			tempView.setText(String.valueOf(tempInC) + " " + DEGREE +"C" + " / " + String.valueOf(tempInF) + " " + DEGREE +"F");
			
			
			int iconId = R.drawable.cloudy;
			if(weatherDescription.contains("clear")) iconId = R.drawable.sunny;
			else if (weatherDescription.contains("rain")) iconId = R.drawable.rain;
			else if (weatherDescription.contains("snow")) iconId = R.drawable.snow;
			currentWeatherView.setImageResource(iconId);
		}
	}

	private class JSONResponseHandler2 implements ResponseHandler<List<String>> {
		@Override
		public List<String> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			List<String> result = new ArrayList<String>();
			String JSONResponse = new BasicResponseHandler()
					.handleResponse(response);
			try {

				JSONObject responseObject = (JSONObject) new JSONTokener(
						JSONResponse).nextValue();
				// Iterate over list items
				// Extract weather info
				JSONArray entries = responseObject
						.getJSONArray("weather");
				JSONObject entry = (JSONObject) entries.get(0);
				String weather = entry.getString("description");
				result.add(weather);
				// Extract temperature info
				JSONObject mainInfo = responseObject.getJSONObject("main");
				String temp = mainInfo.getString("temp");
				result.add(temp);

			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}
	}


	private class HttpGetTask extends AsyncTask<Void, Void, List<String>> {

		private static final String TAG = "HttpGetTask";

//		private static final String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat=35&lon=139&cnt=10&mode=json";
		private String URL = "http://api.openweathermap.org/data/2.5/forecast/daily?lat="
				+ lat
				+ "&lon="
				+ lon
				+ "&cnt=10&mode=json";

		AndroidHttpClient mClient = AndroidHttpClient.newInstance("");

		@Override
		protected List<String> doInBackground(Void... params) {
			HttpGet request = new HttpGet(URL);
			JSONResponseHandler responseHandler = new JSONResponseHandler();
			try {
				return mClient.execute(request, responseHandler);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<String> result) {
			if (null != mClient)
				mClient.close();
			titleBar.setText(result.get(0));
			
			List<RowState> rowlist = new ArrayList<RowState>();
			for(int i = 1; i < result.size(); i++) {
				String s = result.get(i);
				rowlist.add( new RowState(s) );
			}
			setListAdapter(new MyAdapter(DisplayCurrentWeather.this, R.layout.row, rowlist));
			/*result.remove(0);
			setListAdapter(new ArrayAdapter<String>(
					NetworkingAndroidHttpClientJSONActivity.this,
					R.layout.list_item, result));*/
		}
	}

	private class JSONResponseHandler implements ResponseHandler<List<String>> {


		@Override
		public List<String> handleResponse(HttpResponse response)
				throws ClientProtocolException, IOException {
			List<String> result = new ArrayList<String>();
			String JSONResponse = new BasicResponseHandler()
					.handleResponse(response);
			try {

				JSONObject responseObject = (JSONObject) new JSONTokener(
						JSONResponse).nextValue();
				// Extract location info
				JSONObject cityInfo = responseObject.getJSONObject("city");
				String city = cityInfo.getString("name");
				String country = cityInfo.getString("country");
				result.add(city + ", " + country);
				// Iterate over list items
				JSONArray entries = responseObject
						.getJSONArray("list");

				for (int idx = 0; idx < entries.length(); idx++) {

					// Get single earthquake data - a Map
					JSONObject entry = (JSONObject) entries.get(idx);
					JSONObject weatherEntry = (JSONObject) entry.getJSONArray("weather").get(0);

					result.add(weatherEntry.getString("description"));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return result;
		}
	}
	
	private RowState getState( int position ) {
		return (RowState)(((MyAdapter)getListAdapter()).getItem(position));
	}
	
	private class MyAdapter extends ArrayAdapter{
		private List<RowState> states;
		private Context context;
		
		public MyAdapter(Context context, int resource, List<RowState> objects) {
			super(context, resource, objects);
			states = objects;
			this.context = context;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewWrapper wrapper;
			
			//reuse convertView if you can to speed up scrolling on listview
			if(convertView == null){
				//inflate the listview
				LayoutInflater inflator = ((Activity)context).getLayoutInflater();
				convertView = inflator.inflate(R.layout.row, parent, false);
				wrapper = new ViewWrapper(convertView);
				convertView.setTag(wrapper);
			}
			else {
				wrapper = (ViewWrapper) convertView.getTag();
			}

			RowState state = getState(position);
			wrapper.getLabel().setText(state.label);
			wrapper.getLabel().setTextSize(state.getSize());
//			wrapper.getLabel().setTextColor(state.getColor());
			wrapper.getImageView().setImageResource(state.getImageId());
			
			
			return convertView;
		}

	}
	
	private class RowState {
		String label;
		
		RowState (String s) {
			label = s;
		}
		
		public int getImageId() {
			if(label.toLowerCase().contains("clear"))
				return R.drawable.sunny;
			else if (label.toLowerCase().contains("rain"))
				return R.drawable.rain;
			else if (label.toLowerCase().contains("snow"))
				return R.drawable.snow;
			else
				return R.drawable.cloudy;
		}
		
		public int getColor() {
			if(label.toLowerCase().contains("clear"))
				return Color.YELLOW;
			else if (label.toLowerCase().contains("rain"))
				return Color.GREEN;
			else if (label.toLowerCase().contains("snow"))
				return Color.CYAN;
			else
				return Color.GRAY;
		}
		
		public float getSize() {
				return 30.0f;
		}
	}


	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
		// TODO Auto-generated method stub
		
	}

}
