package com.example.myappdemo.Activity;

import com.example.myappdemo.R;
import com.example.myappdemo.httptools.xinximenhuhttp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class Cover extends Activity {

	private String username,password; 
	private xinximenhuhttp httputil = new xinximenhuhttp();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//		requestWindowFeature(Window.FEATURE_NO_TITLE);  
		//        //设置全屏  
		//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   
		//                WindowManager.LayoutParams.FLAG_FULLSCREEN);  
		setContentView(R.layout.activity_cover);  
		SharedPreferences sp = getSharedPreferences("userinfo", 0);
		username = sp.getString("username", "");
		password = sp.getString("password", "");
		if(username.equals("")||password.equals("")){
			Message message = Message.obtain();
			message.what = 0;//无用户名密码
			mhandler.sendMessage(message);
		}else{
			new Thread(new thread()).start();				
		}
	}

	public class thread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = Message.obtain();
			
			if(httputil.post(username, password)){
				message.what = 1;//登录成功
				mhandler.sendMessage(message);
			}else{
				message.what = -1;//登录失败
				mhandler.sendMessage(message);
			}
		}
		
	}
	
	Handler mhandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 0://无用户名密码
				Intent intent = new Intent();
				intent.setClass(Cover.this, LoginActivity.class);
				startActivity(intent);
				Cover.this.finish();
				break;
			case 1://登录成功
				Intent intent1 = new Intent();
				intent1.setClass(Cover.this, MainActivity.class);
				intent1.putExtra("loginresult", true);
				startActivity(intent1);
				Cover.this.finish();
				break;
			case -1://登录失败
				Intent intent11 = new Intent();
				intent11.setClass(Cover.this, MainActivity.class);
				intent11.putExtra("loginresult", false);
				startActivity(intent11);
				Cover.this.finish();
				break;
			}
			
		}

	};

}
