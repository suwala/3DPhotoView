package suwashimizu.test.glviewtest;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

public class ImageLoader {

	private static final int OUT_SIZE = 128;
	
	public static Bitmap uriCreateImage(Uri uri,Context context){

		try {
			InputStream in;
			in = context.getContentResolver().openInputStream(uri);

			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inJustDecodeBounds = true;
			Bitmap img = BitmapFactory.decodeStream(in, null, opts);
			in.close();
			
			int scale = opts.outWidth > opts.outHeight ? opts.outWidth/OUT_SIZE: opts.outHeight/OUT_SIZE;
			opts.inSampleSize = scale;
			
			in = context.getContentResolver().openInputStream(uri);
	        opts.inJustDecodeBounds = false;
			img = BitmapFactory.decodeStream(in,null,opts);
			
			img = Bitmap.createScaledBitmap(img, OUT_SIZE, OUT_SIZE, false);
			in.close();
			return img;
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
