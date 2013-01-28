package suwashimizu.test.glviewtest;

import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TableLayout;

public class GLviewActivity extends Activity implements GestureResponse{

	private GLSurfaceView glView;
	private boolean drawFlg=true;
	private Handler handler;
	private Handler stopHandler;
	private static final int  REQUEST_GALLERY = 1;
	public static Bitmap textureImg;
	private int index = 0;
	private GestureDetector detector;
	private ScaleGestureDetector scaleDetector;
	private GLRenderer glRenderer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		LinearLayout layout = new LinearLayout(this);
		layout.setOrientation(LinearLayout.VERTICAL);
		setContentView(layout);

		glRenderer = new GLRenderer();
		
		glView = new GLSurfaceView(this);
		glView.setRenderer(glRenderer);
		glView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);//反応型描画
		glView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				detector.onTouchEvent(event);
				scaleDetector.onTouchEvent(event);
				return true;
			}
		});
		
		Gesture gesture = new Gesture(this);
		detector = new GestureDetector(glView.getContext(), gesture);
		scaleDetector = new ScaleGestureDetector(glView.getContext(), new ScaleGesture(this));

		LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
		param1.weight = 1.0f;
		layout.addView(glView, param1);

		Button btn = new Button(this);
		btn.setText("写真を追加");
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				startActivityForResult(intent,REQUEST_GALLERY);
			}
		});
		LinearLayout.LayoutParams param2 = new LinearLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
		
		param2.gravity = Gravity.CENTER;
		layout.addView(btn, param2);

		textureImg = Bitmap.createBitmap(1024, 128, Config.ARGB_8888);

		createImgs();

	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK){

			Bitmap b =  ImageLoader.uriCreateImage(data.getData(),this);
			Canvas canvas = new Canvas(textureImg);
			canvas.drawBitmap(b, index*128, 0, null);
			b.recycle();
			index++;
			if(index > 5)
				index = 0;
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		handler = new Handler();
		handler.post(GLdraw);
		stopHandler = new Handler();
	}


	@Override
	protected void onPause() {
		super.onPause();
	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.glview, menu);
		return true;
	}

	private void createImgs(){

		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inScaled = false;
		opts.inPreferredConfig = Config.ARGB_8888;
		Canvas canvas = new Canvas(textureImg);

		Bitmap[] b = new Bitmap[6];
		b[0] = BitmapFactory.decodeResource(getResources(), R.drawable.suwa,opts);
		canvas.drawBitmap(b[0], 0, 0,null);
		b[1] = BitmapFactory.decodeResource(getResources(), R.drawable.pachu8,opts);
		b[2] = BitmapFactory.decodeResource(getResources(), R.drawable.remi8,opts);
		b[3] = BitmapFactory.decodeResource(getResources(), R.drawable.marisa8,opts);
		b[4] = BitmapFactory.decodeResource(getResources(), R.drawable.suwa4,opts);
		b[5] = BitmapFactory.decodeResource(getResources(), R.drawable.suwa,opts);

		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.eito);
		canvas.drawBitmap(bmp, 128, 0,null);
		Log.d("bmp",""+textureImg.getWidth());
	}

	private Runnable GLdraw = new Runnable() {

		@Override
		public synchronized void run() {
			glView.requestRender();
			handler.postDelayed(GLdraw,20);
		}
	};

	private Runnable DrawStop = new Runnable() {

		@Override
		public void run() {
			handler.removeCallbacks(GLdraw);
		}
	};

	@Override
	public void responceCallBack(int responceCode,float... event) {
		//短いタッチで描画のONOFF
		//if(responceCode == GestureResponse.ON_SHOW_PRESS){
		if(responceCode == 100){
				drawFlg = !drawFlg;
			drawFlg = !drawFlg;
			if(drawFlg){
				handler = new Handler();
				handler.post(GLdraw);
			}else
				stopHandler.post(DrawStop);
		}
		
		if(responceCode == GestureResponse.ON_X_FLING){
			float velocity = -event[0];		
			glRenderer.onTouchEvent(responceCode,velocity);
		}
		if(responceCode == GestureResponse.ON_Y_FLING){
			float velocity = -event[0];		
			glRenderer.onTouchEvent(responceCode,velocity);
		}
	}



}
