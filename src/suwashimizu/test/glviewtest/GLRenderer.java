package suwashimizu.test.glviewtest;


import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLUtils;
import android.opengl.GLSurfaceView.Renderer;
import android.util.Log;

public class GLRenderer implements Renderer{

	private Triangle triangle;
	private int size = 0x10000;
	private boolean renderFlg= true;
	
	public GLRenderer() {
		
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {

		//画面をクリア  
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT); 
		
		gl.glEnable(GL10.GL_DEPTH_TEST);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glLoadIdentity(); // 現在選択されている行列に単位行列をロードする。  
		
		//移動(初期カメラ位置  
		gl.glTranslatef(0, 0, -3.0f); 
		//頂点アレイを設定  
		//gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);  
		//カラーアレイを設定  
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);  
		//カリングの設定  
		//gl.glFrontFace(GL10.GL_CW); 
		
		triangle.draw(gl);
		if(triangle.isStop())
			renderFlg= false;
	}
	
	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glMatrixMode(GL10.GL_PROJECTION);
		
		gl.glViewport(0, 0, width, height);//Viewの大きさ
		float ratio = (float) width / height;  
		gl.glMatrixMode(GL10.GL_PROJECTION); 
		//gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
		
		  
		// 現在選択されている行列に単位行列をロードする。  
		gl.glLoadIdentity();  
		  
		/** 
		* ビューボリューム(視体積)を指定します。 
		* 引数は視体積の左、右、下、上、近クリップ面、遠クリップ面の座標を定義 
		*/  
		gl.glFrustumf(-ratio, ratio, -1, 1, 1, 100);  
		triangle = new Triangle(0, size,gl);
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		/** 
		*  DITHERをオフにします。 
		*  DITHERとは量子化の誤差を最小にするべく 
		*  サンプルデータに意図的に追加される誤った信号・データのこと。 
		*/ 
		gl.glDisable(GL10.GL_DITHER); 
		
		  
		// OpenGLにスムージングを設定  
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);  
		  
		// 背景色  
		gl.glClearColor(0,1,1,1);  
		//スムースシェーディング：平面のポリゴンを曲面に見せかける処理。  
		gl.glShadeModel(GL10.GL_SMOOTH);  
		
		/** 
		* 多角形に影を付けるには、各多角形の前後関係を決定する必要がある。 
		* これをするのが、デプステストです。 
		* このデプステストを有効にします。 
		*/  
		gl.glEnable(GL10.GL_DEPTH_TEST);
	}
	
	public void onTouchEvent(int eventType,float f){
		renderFlg = true;
		triangle.onTouchEvent(eventType,f);
	}
}
