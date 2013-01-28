package suwashimizu.test.glviewtest;

public interface GestureResponse {
	
	public final int ON_DOWN = 1;
	public final int ON_SINGLE_TAP = 2;
	public final int ON_SHOW_PRESS = 3;
	public final int ON_X_FLING = 4;
	public final int ON_Y_FLING = 5;
	public final int ON_D_TAP = 6;

	public final int PINCH_IN = 7;
	public final int PINCH_OUT = 8;
	
	public void responceCallBack(int responceCode,float... event);
}
