package suwashimizu.test.glviewtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.opengl.GLUtils;

public class PictureTexture {

	private static final float[] UV ={
		0.0f,0.0f,0.0f,1.0f,
		1.0f,0.0f,1.0f,1.0f,
	};
	private static final float[] TURTLE_POS = {
		-0.5f,0.5f,0.0f
		-0.5f,-0.5f,0.0f,
		0.5f,0.5f,0.0f,
		0.5f,-0.5f,0.0f
	};
	
	public PictureTexture(Bitmap bitmap ,GL10 gl10, int w,int h) {
		gl10.glEnable(GL10.GL_TEXTURE_2D);
		int[] buffers = new int[1];
		gl10.glGenTextures(1, buffers,0);
		int texture = buffers[0];
		gl10.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		gl10.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		gl10.glEnable(GL10.GL_BLEND);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D,0,bitmap,0);
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl10.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
		bitmap.recycle();
	}
	
	public void draw(GL10 gl){
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(UV.length*4);
		byteBuffer.order(ByteOrder.nativeOrder());
		FloatBuffer floatBuffer = byteBuffer.asFloatBuffer();
		floatBuffer.put(UV);
		floatBuffer.position(0);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, floatBuffer);
		drawQuadrant(gl);
	}
	
	private void drawQuadrant(GL10 gl){
		//float w = (float)width / (float) viewRect.
	}
}
