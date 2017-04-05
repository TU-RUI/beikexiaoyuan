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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

public class UserInfoActivity extends Activity {

	private TableLayout tablelayout;
	private xinximenhuhttp test;
	private jsoup jsoup;
	private ProgressBar pb;
	private String username, password;
	private static boolean flag = true;
	private String s1, s2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_userinfo);
		tablelayout = (TableLayout) findViewById(R.id.tablelayout);
		pb = (ProgressBar) findViewById(R.id.pb);
		if (flag) {
			new mytask().execute();
			flag = false;
		} else {
			LoadOldData();
		}
	}

	public class mytask extends AsyncTask<Void, Integer, ArrayList<String>> {

		@Override
		protected ArrayList<String> doInBackground(Void... params) {
			// TODO Auto-generated method stub
			test = new xinximenhuhttp();
			jsoup = new jsoup();
			publishProgress(20);
			if (test.post(username, password)) {
				String s = test.gethtml2("http://i.ustb.edu.cn/");
				if (s.equals("")) {
					return null;
				}
				publishProgress(40);
				s1 = test.gethtml("http://i.ustb.edu.cn/Profile/Resume");
				if (s.equals("")) {
					return null;
				}
//				publishProgress(60);
//				s2 = test.gethtml(test.userinfourl);

				publishProgress(80);
				ArrayList<String> result = jsoup.ParseUserInfo(s1);
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
		protected void onPostExecute(ArrayList<String> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result == null) {
				// LoadOldData();
				return;
			} else {
				SaveCurrentData(result);
				for (int i = 0; i < result.size(); i += 2) {
					TableRow tablerow = new TableRow(UserInfoActivity.this);
						TextView tv = new TextView(UserInfoActivity.this);
						tv.setText(result.get(i)+"  ");
						tv.setTextColor(UserInfoActivity.this.getResources()
								.getColor(R.color.black));
						tv.setTextSize(18);
						tv.setPadding(0, 5, 0, 5);
						tablerow.addView(tv);
						TextView tv1 = new TextView(UserInfoActivity.this);
						tv1.setText(result.get(i + 1));
						tv1.setTextColor(UserInfoActivity.this.getResources()
								.getColor(R.color.black));
						tv1.setTextSize(18);
						tv1.setPadding(10, 5, 0, 5);
						tablerow.addView(tv1);
					View view = new View(UserInfoActivity.this);
					LayoutParams params = new LayoutParams(
							ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
					view.setLayoutParams(params);
					view.setBackgroundResource(R.color.blue);
					tablelayout.addView(tablerow);
					tablelayout.addView(view);
					publishProgress(80 + (i / (result.size() - 1)) * 20);
				}
			}
			publishProgress(100);
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

	private void LoadOldData() {
		SQLiteDatabase db = openOrCreateDatabase("mydatabase.db", 0, null);
		String sql = "create table if not exists userinfo(_id integer primary key autoincrement, key text, value text)";
		db.execSQL(sql);
		Cursor cursor = db.query("userinfo", null, "_id>?",
				new String[] { "0" }, null, null, null);
		ArrayList<String> olddata = new ArrayList<String>();
		if (cursor != null) {
			while (cursor.moveToNext()) {
				olddata.add(cursor.getString(1));
				olddata.add(cursor.getString(2));
			}
		}
		cursor.close();
		db.close();
		for (int i = 0; i < olddata.size(); i += 2) {
			TableRow tablerow = new TableRow(UserInfoActivity.this);
			TextView tv = new TextView(UserInfoActivity.this);
			tv.setText(olddata.get(i)+"  ");
			tv.setTextColor(UserInfoActivity.this.getResources()
					.getColor(R.color.black));
			tv.setTextSize(18);
			tv.setPadding(0, 5, 0, 5);
			tablerow.addView(tv);
			TextView tv1 = new TextView(UserInfoActivity.this);
			tv1.setText(olddata.get(i + 1));
			tv1.setTextColor(UserInfoActivity.this.getResources()
					.getColor(R.color.black));
			tv1.setTextSize(18);
			tv1.setPadding(10, 5, 0, 5);
			tablerow.addView(tv1);
			View view = new View(UserInfoActivity.this);
			LayoutParams params = new LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
			view.setLayoutParams(params);
			view.setBackgroundResource(R.color.blue);
			tablelayout.addView(tablerow);
			tablelayout.addView(view);
		}
	}

	private void SaveCurrentData(ArrayList<String> result) {
//		Log.i("result.size",result.size()+"");
		SQLiteDatabase db = openOrCreateDatabase("mydatabase.db", 0, null);
//		db.execSQL("DROP TABLE IF EXISTS userinfo");
		String sql = "create table if not exists userinfo(_id integer primary key autoincrement, key text, value text)";
		db.execSQL(sql);
		ContentValues cv = new ContentValues();
		for (int i = 0; i < result.size(); i+=2) {
//			Log.i("result.get"+i,result.get(i));
			cv.put("key", result.get(i));
			cv.put("value", result.get(i+1));
			cv.clear();
		}
		db.close();
	}
}
