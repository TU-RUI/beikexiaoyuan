package com.example.myappdemo.Activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hamcrest.core.AnyOf;

import com.example.myappdemo.R;
import com.example.myappdemo.httptools.jsoup;
import com.example.myappdemo.httptools.xinximenhuhttp;

import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable.Creator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class Score extends Activity {

	private TableLayout tablelayout;
	private xinximenhuhttp test;
	private jsoup jsoup;
	private ProgressBar pb;
	private String username,password;
	private static boolean flag = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_score);
		tablelayout = (TableLayout) findViewById(R.id.tablelayout);
		pb = (ProgressBar) findViewById(R.id.pb);
		if(flag){
			new mytask().execute();		
			flag = false;
		}else{
			LoadOldData();
		}
	}

	public class mytask extends AsyncTask<Void, Integer, List<HashMap<Integer, String>>> {

		@Override
		protected List<HashMap<Integer, String>> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			test = new xinximenhuhttp();
			jsoup = new jsoup();
			publishProgress(20);
			// Log.i("tag","---html->"+s);
			if(test.post(username, password)){
				String s = test.gethtml2("http://i.ustb.edu.cn/");
				if(s.equals("")){
					return null;
				}
				publishProgress(40);
				s = test.gethtml("http://i.ustb.edu.cn/Profile/Score");
				if(s.equals("")){
					return null;
				}
				publishProgress(60);
				List<HashMap<Integer, String>> result = jsoup.ParseScore(s);
				publishProgress(80);
				return result;

			}
			return null;

		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			pb.setProgress(values[0]);
		}

		@Override
		protected void onPostExecute(List<HashMap<Integer, String>> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(result==null){
				LoadOldData();
			}else{
				SaveCurrentData(result);
				for (int i = 0; i < result.size(); i++) {
					TableRow tablerow = new TableRow(Score.this);
					for (int j = 1; j < result.get(i).size() + 1; j++) {
						TextView tv = new TextView(Score.this);
						tv.setText(result.get(i).get(j));
						tv.setTextColor(Score.this.getResources().getColor(
								R.color.black));
						tv.setTextSize(18);
						tv.setPadding(0, 5, 0, 5);
						tablerow.addView(tv);

					}
					View view = new View(Score.this);
					LayoutParams params = new LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
					view.setLayoutParams(params);
					view.setBackgroundResource(R.color.blue);
					tablelayout.addView(view);
					tablelayout.addView(tablerow);
					publishProgress(80+(i/(result.size()-1))*20);
				}
			}
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pb.setProgress(0);
			SharedPreferences sp = getSharedPreferences("userinfo", 0);
			username = sp.getString("username", "");
			password = sp.getString("password", "");
		}
	}


	private  void LoadOldData (){
		SQLiteDatabase db = openOrCreateDatabase("mydatabase.db",0, null);
		String sql = "create table if not exists score(_id integer primary key autoincrement, name text, score text, weight text,time text)";
		db.execSQL(sql);
		Cursor cursor = db.query("score", null, "_id>?", new String[]{"0"}, null, null, null);
		List<HashMap<Integer, String>> olddata = new ArrayList<HashMap<Integer, String>>();
		HashMap<Integer, String> map;
		if(cursor!=null){
			while(cursor.moveToNext()){
				map = new HashMap<Integer, String>();
				map.put(1, cursor.getString(1));
				map.put(2, cursor.getString(2));
				map.put(3, cursor.getString(3));
				map.put(4, cursor.getString(4));
				olddata.add(map);
			}
		}
		cursor.close();
		db.close();
		for (int i = 0; i < olddata.size(); i++) {
			TableRow tablerow = new TableRow(Score.this);
			for (int j = 1; j < olddata.get(i).size() + 1; j++) {
				TextView tv = new TextView(Score.this);
				tv.setText(olddata.get(i).get(j));
				tv.setTextColor(Score.this.getResources().getColor(
						R.color.black));
				tv.setTextSize(18);
				tv.setPadding(0, 5, 0, 5);
				tablerow.addView(tv);

			}
			View view = new View(Score.this);
			LayoutParams params = new LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
			view.setLayoutParams(params);
			view.setBackgroundResource(R.color.blue);
			tablelayout.addView(view);
			tablelayout.addView(tablerow);
		}
	}

	private void SaveCurrentData(List<HashMap<Integer, String>> result){
		SQLiteDatabase db = openOrCreateDatabase("mydatabase.db", 0, null);
		db.execSQL("DROP TABLE IF EXISTS score");
		String sql = "create table if not exists score(_id integer primary key autoincrement, name text, score text, weight text,time text)";
		db.execSQL(sql);
		ContentValues cv = new ContentValues();
		for(int i = 0; i <result.size();i++){
			cv.put("name", result.get(i).get(1));
			cv.put("score", result.get(i).get(2));
			cv.put("weight", result.get(i).get(3));
			cv.put("time", result.get(i).get(4));
			db.insert("score", null, cv);
			cv.clear();
		}
		db.close();
	}
}
