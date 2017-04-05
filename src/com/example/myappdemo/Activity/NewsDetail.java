package com.example.myappdemo.Activity;

import com.example.myappdemo.R;
import com.example.myappdemo.httptools.xinximenhuhttp;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class NewsDetail extends Activity {
	private String link , result;
	private TextView tv;
	private xinximenhuhttp httpget;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newsdetails);
		Intent intent = getIntent();
		link = intent.getStringExtra("newsurl");
		tv = (TextView) this.findViewById(R.id.tv1);
		new mytask().execute(link);
	}
	
	public class mytask extends AsyncTask<String, Void, String>{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			httpget = new xinximenhuhttp();
			
			result = httpget.ParseHtml(httpget.gethtmlgb2312(params[0]));
			return result;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			if(!result.equals("")){
				tv.setText(result);
				tv.setTextSize(20);
			}else{
				Toast toast = Toast.makeText(NewsDetail.this, "Õ¯¬Á¡¨Ω” ß∞‹", Toast.LENGTH_SHORT);
				toast.show();
			}
		}
		
	}

}
