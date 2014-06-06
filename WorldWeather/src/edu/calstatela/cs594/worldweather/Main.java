package edu.calstatela.cs594.worldweather;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

public class Main extends Activity {

	private GestureDetector mGestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		mGestureDetector = new GestureDetector(this,
				new GestureDetector.SimpleOnGestureListener() {
					@Override
					public boolean onFling(MotionEvent e1, MotionEvent e2,
							float velocityX, float velocityY) {
						Intent i2 = new Intent(Main.this, DisplayCurrentWeather.class);
						if (velocityX < -4.0f) {
							startActivity(i2);
							overridePendingTransition( R.anim.slide_in_rtl, R.anim.slide_out_rtl );
						}
						if (velocityX > 4.0f) {
							startActivity(i2);
							overridePendingTransition( R.anim.slide_in_ltr, R.anim.slide_out_ltr );
						}
						return true;
					}
				});
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		return mGestureDetector.onTouchEvent(event);
	}

}
