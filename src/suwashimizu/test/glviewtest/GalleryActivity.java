package suwashimizu.test.glviewtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class GalleryActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_SEND_MULTIPLE);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}


}
