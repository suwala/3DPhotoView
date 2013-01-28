package suwashimizu.test.glviewtest;

import android.util.Log;
import android.view.ScaleGestureDetector;
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener;

public class ScaleGesture extends SimpleOnScaleGestureListener{

	private GestureResponse response;
	
	public ScaleGesture(GestureResponse response) {
		this.response = response;
	}

	@Override
	public boolean onScale(ScaleGestureDetector detector) {
		Log.d("onScale","onScale");
		return true;
	}

	@Override
	public boolean onScaleBegin(ScaleGestureDetector detector) {
		Log.d("onScale","onScale11111111111111");
		return super.onScaleBegin(detector);
	}

	@Override
	public void onScaleEnd(ScaleGestureDetector detector) {
		Log.d("onScale","onScale22222222222222");
		super.onScale(detector);
	}

}
