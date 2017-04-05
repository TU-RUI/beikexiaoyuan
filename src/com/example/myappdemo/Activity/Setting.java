package com.example.myappdemo.Activity;

import com.example.myappdemo.R;
import com.example.myappdemo.ViewOverride.AboutDialog;
import com.example.myappdemo.ViewOverride.CustomDialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Setting extends Activity {

	private Button changeuser,about;
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		changeuser = (Button) findViewById(R.id.changeuser);
		changeuser.setOnClickListener(onclick);
		about = (Button)findViewById(R.id.about);
		about.setOnClickListener(onclick);
	}
	
	public OnClickListener onclick = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			switch(v.getId()){
			case R.id.changeuser:
//				EraseData();
//				Intent intent = new Intent();
//				intent.setClass(Setting.this, LoginActivity.class);
//				startActivity(intent);
//				Setting.this.finish();
//				MainActivity.instance.finish();
				CustomDialog dialog = new CustomDialog(Setting.this,R.style.mystyle,R.layout.customdialog);
				dialog.show();
				break;
			case R.id.about:
//				Intent intent1 = new Intent();
//				intent1.setClass(Setting.this,About.class);
//				startActivity(intent1);
				AboutDialog dialog1 = new AboutDialog(Setting.this, R.style.mystyle,R.layout.about_dialog);
				dialog1.show();
				break;
			}
		}
	};
	
	private void EraseData(){
		deleteDatabase("mydatabase.db");
		SharedPreferences sp = getSharedPreferences("userinfo", 0);
		Editor editor = sp.edit();
		editor.clear().commit();
	}
}
