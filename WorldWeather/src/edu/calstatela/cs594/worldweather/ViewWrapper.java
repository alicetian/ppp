package edu.calstatela.cs594.worldweather;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewWrapper {
		View base;
		TextView label=null;
		ImageView image = null;
		
		ViewWrapper(View base) {
			this.base=base;
		}
		
		ImageView getImageView() {
			if (image==null) {
				image = (ImageView) base.findViewById(R.id.imageview1);
			}
			return image;
		}
		
		TextView getLabel() {
			if (label==null) {
				label=(TextView)base.findViewById(R.id.textView1);
			}
			return(label);
		}

}