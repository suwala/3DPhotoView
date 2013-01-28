package suwashimizu.test.glviewtest;

import android.util.Log;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class Gesture extends SimpleOnGestureListener{

	private GestureResponse response;
	private final int MOVE_X = 1;
	private final int MOVE_Y = 2;
	
	public Gesture(GestureResponse response) {
		this.response = response;
	}
	@Override
	public boolean onDoubleTap(MotionEvent e) {
		return super.onDoubleTap(e);
	}

	@Override
	public boolean onDoubleTapEvent(MotionEvent e) {
		return super.onDoubleTapEvent(e);
	}

	@Override
	public boolean onDown(MotionEvent e) {
		return super.onDown(e);
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		
		float moveX =  Math.abs(e1.getX()-e2.getX());
		float moveY = Math.abs(e1.getY() - e2.getY());
		
		if(moveX > moveY)
			response.responceCallBack(GestureResponse.ON_X_FLING,velocityX);
		else
			response.responceCallBack(GestureResponse.ON_Y_FLING,velocityY);
		return super.onFling(e1, e2, velocityX, velocityY);
	}

	@Override
	public void onLongPress(MotionEvent e) {
		super.onLongPress(e);
		Log.d("Gesture","LONGGGGGGG");
	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		return super.onScroll(e1, e2, distanceX, distanceY);
	}

	@Override
	public void onShowPress(MotionEvent e) {
		super.onShowPress(e);
		response.responceCallBack(GestureResponse.ON_SHOW_PRESS);
	}

	@Override
	public boolean onSingleTapConfirmed(MotionEvent e) {
		return super.onSingleTapConfirmed(e);
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		return super.onSingleTapUp(e);
	}
}
