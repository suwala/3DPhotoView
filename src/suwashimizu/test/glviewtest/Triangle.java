package suwashimizu.test.glviewtest;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;
import javax.microedition.khronos.opengles.GL11Ext;

import android.graphics.Color;
import android.opengl.GLUtils;
import android.util.Log;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;

public class Triangle {

	private FloatBuffer   mVertexBuffer;  
	private FloatBuffer   mColorBuffer;  
	private ShortBuffer  mIndexBuffer; 
	private FloatBuffer mTextureBuffer;
	private FloatBuffer mNormalBuffer;

	private float xSpeed = 0;
	private float ySpeed = 0;
	private float zSpeed = 0;
	
	private float angleX = 0;
	private float angleY = 0;
	private float angleZ = 0;

	private int textureId = 0;

	private static final float x = 1;
	private static final float y = 1;
	private static final float z = 1;	


	public Triangle(int p , int size ,GL10 gl){  
		//座標リスト
		float[] vertices = {  
				x, y, z,  -x, y, z,  -x,-y, z,   x,-y, z,  // v0-v1-v2-v3 front
				x, y, z,   x,-y, z,   x,-y,-z,   x, y,-z,  // v0-v3-v4-v5 right
				x, y, z,   x, y,-z,  -x, y,-z,  -x, y, z,  // v0-v5-v6-v1 top
				-x, y, z,  -x, y,-z,  -x,-y,-z,  -x,-y, z,  // v1-v6-v7-v2 left
				-x,-y,-z,   x,-y,-z,   x,-y, z,  -x,-y, z,  // v7-v4-v3-v2 bottom
				x,-y,-z,  -x,-y,-z,  -x, y,-z,   x, y,-z   // v4-v7-v6-v5 back
		};  

		//頂点の描画順インデックス  
		byte[] indices2 = {  
				0,1,2,2,3,0,
				3,2,5,3,5,4,
				1,7,5,1,5,2,				
				0,1,7,0,7,6,
				0,6,4,0,4,3,
				6,7,5,6,5,4,

		};
		short[] indices = {
				0, 1, 2,   0, 2, 3,   // front
				4, 5, 6,   4, 6, 7,   // right
				8, 9,10,   8,10,11,   // top
				12,13,14,  12,14,15,   // left
				16,17,18,  16,18,19,   // bottom
				20,21,22,  20,22,23 // back
		};

		//テクスチャUV座標
		float[] textures = {
				0,1.0f, 0.125f,1, 0.125f,0, 0,0,
				0.125f,1,  0.25f,1, 0.25f,0, 0.125f,0,
				0.25f,1, 0.375f,1, 0.375f,0, 0.25f,0,
				0.375f,1, 0.5f,1, 0.5f,0, 0.375f,0,
				0.5f,1, 0.625f,1, 0.625f,0, 0.5f,0,
				0.625f,1, 0.75f,1, 0.75f,0, 0.625f,0,
		};

		//法線ベクトル
		float[] normals = {
				0, 0, 1,   0, 0, 1,   0, 0, 1,   0, 0, 1,   // v0-v1-v2-v3 front
				0, 1, 0,   0, 1, 0,   0, 1, 0,   0, 1, 0,   // v0-v5-v6-v1 top
				-1, 0, 0,  -1, 0, 0,  -1, 0, 0,  -1, 0, 0,   // v1-v6-v7-v2 left
				0,-1, 0,   0,-1, 0,   0,-1, 0,   0,-1, 0,   // v7-v4-v3-v2 bottom
				0, 0,-1,   0, 0,-1,   0, 0,-1,   0, 0,-1   // v4-v7-v6-v5 back
		};

		float[] colors = {  


				1,1,1,1,
				1,1,1,1,
				1,1,1,1,
				1,1,1,1,

		};

		mVertexBuffer = makeFloatBuffer(vertices);
		mNormalBuffer = makeFloatBuffer(normals);
		mIndexBuffer = makeShortBuffer(indices);
		mTextureBuffer = makeFloatBuffer(textures);
		mColorBuffer = makeFloatBuffer(colors);		
		textureId = loadTexture(gl);

	}



	public static final FloatBuffer makeFloatBuffer(float[] arr){
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
		bb.order(ByteOrder.nativeOrder());
		FloatBuffer fb = bb.asFloatBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}
	public static final ShortBuffer makeShortBuffer(short[] arr) {
		ByteBuffer bb = ByteBuffer.allocateDirect(arr.length * 4);
		bb.order(ByteOrder.nativeOrder());
		ShortBuffer fb = bb.asShortBuffer();
		fb.put(arr);
		fb.position(0);
		return fb;
	}

	public static final int loadTexture(GL10 gl){
		int[] textures = new int[1];

		gl.glGenTextures(1, textures,0);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, GLviewActivity.textureImg, 0);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, 0);

		//GLviewActivity.texture.recycle();
		return textures[0];
	}

	//描画  
	public void draw(GL10 gl){

		gl.glEnable(GL10.GL_BLEND);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textureId);
		
		angleX += xSpeed;
		angleY += ySpeed;
		angleZ += zSpeed;
		
		gl.glRotatef(angleX , 0, 1, 0);
		gl.glRotatef(angleY , 1, 0, 0);
		gl.glRotatef(angleZ , 0, 0, 1);
		
		//多分減産処理if()
		xSpeed *= 0.9;
		ySpeed *= 0.9;
		//zSpeed-= 1.0f;

		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexBuffer);
		gl.glNormalPointer(GL10.GL_FLOAT,0,mNormalBuffer);
		gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
		gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);
		gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);

		gl.glDrawElements(GL10.GL_TRIANGLES, 36, GL10.GL_UNSIGNED_SHORT, mIndexBuffer);

	} 

	public void onTouchEvent(int eventType,float f){
		switch (eventType) {
		case GestureResponse.ON_X_FLING:
			XRotate(f);
			break;

		case GestureResponse.ON_Y_FLING:
			YRotate(f);
			break;
		}
	}

	private void XRotate(float f){
		if(Math.abs(f) > 100)
			xSpeed = 100;
		xSpeed = f;
	}
	private void YRotate(float f){
		if(Math.abs(f) > 100)
			ySpeed = 100;
		ySpeed = f;
	}
	private void ZRotate(float f){

	}
	
	public boolean isStop(){
		if(xSpeed == 0 && ySpeed == 0 && zSpeed == 0)
					return true;
		return false;
	}
}
