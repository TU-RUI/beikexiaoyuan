package com.example.myappdemo.Activity;

import java.util.ArrayList;

import com.example.myappdemo.R;
import com.example.myappdemo.DataLoader.DataLoader;
import com.example.myappdemo.fragment.FirstFragment;
import com.example.myappdemo.fragment.LibSearchFragment;
import com.example.myappdemo.fragment.MenuFragment;
import com.example.myappdemo.fragment.SecondFragment;
import com.example.myappdemo.fragment.ThirdFragment;
import com.example.myappdemo.httptools.xinximenhuhttp;
import com.example.myappdemo.Activity.NewsDetail;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {

	public static MainActivity instance = null;
	private ViewPager viewpager;
	private FirstFragment FirstFragment;
	private SecondFragment SecondFragment;
	private ThirdFragment ThirdFragment;
	private MenuFragment menufragment;
	private LibSearchFragment libsearchfragment;
	private ArrayList<Fragment> mfragmentlist;
	private final ArrayList<String> mtitlelist = new ArrayList<String>();
	private PagerTabStrip pagertabstrip;
	private final DisplayMetrics displayMetrics = new DisplayMetrics();
	private final xinximenhuhttp xinximenhu = new xinximenhuhttp();
	String username, password ,Name;
	private ArrayList<String> CardData, LibData, NewsData, LibhisData, NetData ,UserinfoData;
	String books;
	final MyData mydata = new MyData();
	private DataLoader dataloader;
	private MyHandler handler;
	private boolean doubleBackToExitPressedOnce = false;
	private SharedPreferences sp;
	private TextView nametv;
	/**
	 * @author tr
	 */
	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
		getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		setContentView(R.layout.activity_main);
		handler = new MyHandler();
		instance = this;
		sp = getSharedPreferences("userinfo", 0);
		username = sp.getString("username", "");
		password = sp.getString("password", "");
		dataloader = new DataLoader();
		dataloader.setContext(MainActivity.this);
		viewpagerinit();//初始化viewpager
		LoadHisData();
		new Thread(new httpthread()).start();//下载数据
		// slidingpanelayout =
		// (SlidingPaneLayout)findViewById(R.id.slidingpanelayout);
		/*
		 * maxMargin = displayMetrics.heightPixels / 10;
		 * slidingpanelayout.setPanelSlideListener(new PanelSlideListener() {
		 * 
		 * @Override public void onPanelSlide(View panel, float slideOffset) {
		 * // TODO Auto-generated method stub int contentMargin = (int)
		 * (slideOffset * maxMargin); ViewPager.LayoutParams contentParams =
		 * (LayoutParams) viewpager.getLayoutParams();
		 * //contentParams.setMargins(0, contentMargin, 0, contentMargin);
		 * viewpager.setLayoutParams(contentParams); float scale = 1 - ((1 -
		 * slideOffset) * maxMargin * 2) / (float) displayMetrics.heightPixels;
		 * MenuFragment.getcurrentview().setScaleX(scale);// 设置缩放的基准点
		 * MenuFragment.getcurrentview().setScaleY(scale);// 设置缩放的基准点
		 * MenuFragment.getcurrentview().setPivotX(0);// 设置缩放和选择的点
		 * MenuFragment.getcurrentview().setPivotY(displayMetrics.heightPixels /
		 * 2); MenuFragment.getcurrentview().setAlpha(100); }
		 * 
		 * @Override public void onPanelClosed(View arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * }
		 * 
		 * @Override public void onPanelOpened(View arg0) { // TODO
		 * Auto-generated method stub
		 * 
		 * } });
		 */
		
	}

	/**
	 * 初始化viewpager
	 */
	private void viewpagerinit() {
		// TODO Auto-generated method stub
		viewpager = (ViewPager) this.findViewById(R.id.viewpager);
		pagertabstrip = (PagerTabStrip) findViewById(R.id.tabstrip);
		pagertabstrip.setBackgroundColor(0XFFf89090);// 粉红
		pagertabstrip.setTabIndicatorColor(0xfff7f890);
		pagertabstrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
		FirstFragment = new FirstFragment();
		SecondFragment = new SecondFragment();
		ThirdFragment = new ThirdFragment();
		MenuFragment menufragment = (MenuFragment) getFragmentManager().findFragmentById(R.id.sildingpane_menu);
		nametv = (TextView) menufragment.getView().findViewById(R.id.username);
		libsearchfragment = new LibSearchFragment();
		mfragmentlist = new ArrayList<Fragment>();
		mfragmentlist.add(FirstFragment);
		mfragmentlist.add(SecondFragment);
		mfragmentlist.add(ThirdFragment);
		mfragmentlist.add(libsearchfragment);
		mtitlelist.add("校园动态");
		mtitlelist.add("图书馆");
		mtitlelist.add("消费记录");
		mtitlelist.add("图书查询");
		viewpager.setAdapter(new MyViewPagerAdapter(getSupportFragmentManager()));
		viewpager.setOffscreenPageLimit(3);
	}

	/**
	 * 获取校园卡记录数据,图书馆借阅数据,获取完后加载
	 * 
	 * @author tr
	 * 
	 */
	/*public class MyTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub

		}

		@Override
		protected Boolean doInBackground(final String... params) {
			// TODO Auto-generated method stub
			if (xinximenhu.post(params[0], params[1])) {
				CardData = xinximenhu.ParseHtml(
						xinximenhu.gethtml(xinximenhu.cardurl), 0);
				LibData = xinximenhu.ParseHtml(
						xinximenhu.gethtml(xinximenhu.liburl), 2);
				NewsData = xinximenhu.ParseHtml(
						xinximenhu.gethtmlgb2312(xinximenhu.newsurl), 4);
				NetData = xinximenhu.ParseHtml(
						xinximenhu.gethtml(xinximenhu.netinfourl), 3);
				books = xinximenhu.gethtml(xinximenhu.libhistoryurl);// 不可删除,从信息门户进入图书馆检索系统,获得cookie
				books = xinximenhu.gethtml(xinximenhu.libhistoryurl2);// 带着上面获取的cookie安全进入图书馆
				LibhisData = xinximenhu.ParseHtml(books, 5);
				return true;
			} else {
				return false;
			}
		}

		@Override
		protected void onPostExecute(final Boolean result) {
			// TODO Auto-generated method stub
			if (result) {			
//				dataloader.LoadLibData(SecondFragment.getCurrentView(),
//						LibData, LibhisData);
//				dataloader.LoadCardData(ThirdFragment.getCurrentView(),
//						CardData, NetData);
				//dataloader.LoadNewsData(FirstFragment.getCurrentView(), NewsData);
				FirstFragment.loadData(NewsData);
				SecondFragment.loadData(LibData, LibhisData);
				ThirdFragment.loadData(CardData, NetData);
				//将数据存入数据库
				SaveCurrentData();
			} else {
				final Toast toast = Toast.makeText(MainActivity.this, "网络连接失败",
						Toast.LENGTH_SHORT);
				toast.show();
				LoadHisData();
			}
		}

	}*/



	/**
	 * viewpager适配器
	 * 
	 * @author tr
	 * 
	 */
	public class MyViewPagerAdapter extends FragmentPagerAdapter {

		public MyViewPagerAdapter(final FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(final int arg0) {
			// TODO Auto-generated method stub
			return mfragmentlist.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mfragmentlist.size();
		}

		public CharSequence getPageTitle(final int position) {
			return mtitlelist.get(position);

		}

	}


	public MainActivity getcontext(){
		return this;
	}

	/**
	 * 加载上次的数据
	 */
	private void LoadHisData() {
		// TODO Auto-generated method stub
		//		CardData = MyData.getCardData();
		//		LibData = MyData.getLibData();
		//		LibhisData = MyData.getLibHisData();
		//		NewsData = MyData.getNewsData();
		//		NetData = MyData.getNetData();
		SQLiteDatabase db = openOrCreateDatabase("mydatabase.db", 0, null);
		String sql = "create table if not exists newsdata(_id integer primary key autoincrement,title text,href text,time text)";
		db.execSQL(sql);
		Cursor cursor = db.query("newsdata", null, "_id>?", new String[]{"0"}, null, null, null);
		NewsData = new ArrayList<String>();
		LibData = new ArrayList<String>();
		LibhisData = new ArrayList<String>();
		CardData = new ArrayList<String>();
		NetData = new ArrayList<String>();
		if(cursor!=null){
			while(cursor.moveToNext()){
				NewsData.add(cursor.getString(1));
				NewsData.add(cursor.getString(2));
				NewsData.add(cursor.getString(3));
				Log.i("newsdataold", cursor.getString(1));
				Log.i("newsdataold", cursor.getString(2));
				Log.i("newsdataold", cursor.getString(3));
			}
		}
		MyData.setNewsData(NewsData);
		//////////FirstFragment.loadData(NewsData);
		//dataloader.LoadNewsData(FirstFragment.getCurrentView(), NewsData);
		sql = "create table if not exists libdata(_id integer primary key autoincrement,name text,author text,time1 text,time2 text)";
		db.execSQL(sql);
		cursor = db.query("libdata", null, "_id>?", new String[]{"0"}, null, null, null);
		if(cursor!=null){
			while(cursor.moveToNext()){
				LibData.add(cursor.getString(1));
				LibData.add(cursor.getString(2));
				LibData.add(cursor.getString(3));
				LibData.add(cursor.getString(4));
			//	Log.i("libdatainfo", cursor.getString(1));
				//Log.i("libdatainfo", cursor.getString(2));
				//Log.i("libdatainfo", cursor.getString(3));
				//Log.i("libdatainfo", cursor.getString(4));
			}
		}
		MyData.setLibData(LibData);
		sql = "create table if not exists libhisdata(_id integer primary key autoincrement,name text,author text)";
		db.execSQL(sql);
		cursor = db.query("libhisdata", null, "_id>?", new String[]{"0"}, null, null, null);
		if(cursor!=null){
			while(cursor.moveToNext()){
				LibhisData.add(cursor.getString(1));
				LibhisData.add(cursor.getString(2));
				//Log.i("info", cursor.getString(1));
				//Log.i("info", cursor.getString(2));
			}
		}
		MyData.setLibHisData(LibhisData);
		///////SecondFragment.loadData(LibData, LibhisData);
		//dataloader.LoadLibData(SecondFragment.getCurrentView(),LibData, LibhisData);
		sql = "create table if not exists carddata(_id integer primary key autoincrement,time text,adress text,sum text,remain text)";
		db.execSQL(sql);
		cursor = db.query("carddata", null, "_id>?", new String[]{"0"}, null, null, null);
		if(cursor!=null){
			while(cursor.moveToNext()){
				CardData.add(cursor.getString(1));
				CardData.add(cursor.getString(2));
				CardData.add(cursor.getString(3));
				CardData.add(cursor.getString(4));
				//Log.i("info", cursor.getString(1));
				//Log.i("info", cursor.getString(2));
				//Log.i("info", cursor.getString(3));
				//Log.i("info", cursor.getString(4));
			}
		}
		MyData.setCardData(CardData);
		sql = "create table if not exists netdata(_id integer primary key autoincrement,title text,content text)";
		db.execSQL(sql);
		cursor = db.query("netdata", null, "_id>?", new String[]{"0"}, null, null, null);
		if(cursor!=null){
			while(cursor.moveToNext()){
				NetData.add(cursor.getString(1));
				NetData.add(cursor.getString(2));
				//Log.i("info", cursor.getString(1));
				//Log.i("info", cursor.getString(2));
			}
		}
		MyData.setNetData(NetData);
		/////////ThirdFragment.loadData(CardData, NetData);
		//dataloader.LoadCardData(ThirdFragment.getCurrentView(),CardData, NetData);
		cursor.close();
		db.close();	
	}

	/**
	 * 保存本次数据
	 */
	private void SaveCurrentData(){
		SQLiteDatabase db = openOrCreateDatabase("mydatabase.db", 0, null);
		db.execSQL("DROP TABLE IF EXISTS newsdata");
		String sql = "create table if not exists newsdata(_id integer primary key autoincrement,title text,href text,time text)";
		db.execSQL(sql);
		ContentValues values = new ContentValues();
		for(int i = 0;i<NewsData.size();i+=3){
			values.put("title",NewsData.get(i) );
			values.put("href",NewsData.get(i+1));
			values.put("time",NewsData.get(i+2));
			db.insert("newsdata", null, values);
			values.clear();
		}
		db.execSQL("DROP TABLE IF EXISTS libdata");
		sql = "create table if not exists libdata(_id integer primary key autoincrement,name text,author text,time1 text,time2 text)";
		db.execSQL(sql);
		for(int i = 0;i<LibData.size();i+=4){
			values.put("name", LibData.get(i));
			values.put("author", LibData.get(i+1));
			values.put("time1", LibData.get(i+2));
			Log.i("time111", LibData.get(i+2));
			values.put("time2", LibData.get(i+3));
			db.insert("libdata", null, values);
			values.clear();
		}
		db.execSQL("DROP TABLE IF EXISTS libhisdata");
		sql = "create table if not exists libhisdata(_id integer primary key autoincrement,name text,author text)";
		db.execSQL(sql);
		for(int i = 0;i<LibhisData.size();i+=2){
			values.put("name", LibhisData.get(i));
			values.put("author", LibhisData.get(i+1));
			db.insert("libhisdata", null, values);
			values.clear();
		}
		db.execSQL("DROP TABLE IF EXISTS carddata");
		sql = "create table if not exists carddata(_id integer primary key autoincrement,time text,adress text,sum text,remain text)";
		db.execSQL(sql);
		for(int i = 0;i<CardData.size();i+=4){
			values.put("time", CardData.get(i));
			values.put("adress", CardData.get(i+1));
			values.put("sum", CardData.get(i+2));
			values.put("remain", CardData.get(i+3));
			db.insert("carddata", null, values);
			values.clear();
		}
		db.execSQL("DROP TABLE IF EXISTS netdata");		
		sql = "create table if not exists netdata(_id integer primary key autoincrement,title text,content text)";
		db.execSQL(sql);
		for(int i = 0;i<NetData.size();i+=2){
			values.put("title", NetData.get(i));
			values.put("content", NetData.get(i+1));
			db.insert("netdata", null, values);
			values.clear();
		}
		db.close();
	}

	/**
	 * 下载全部数据线程
	 * @author tr
	 *
	 */
	public class httpthread implements Runnable{

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Intent intent = getIntent();
			if(intent.getBooleanExtra("loginresult", false)){
				NewsData = xinximenhu.ParseHtml(xinximenhu.gethtmlgb2312(xinximenhu.newsurl), 4);
				Message msg0 = Message.obtain();
				msg0.what = 0;//newsData
				handler.sendMessage(msg0);
//				LibData = xinximenhu.ParseHtml(
//						xinximenhu.gethtml(xinximenhu.liburl), 2);
//				System.out.println("当前借阅数据"+LibData);
				books = xinximenhu.gethtml(xinximenhu.libhistoryurl);// 不可删除,从信息门户进入图书馆检索系统,获得cookie
				books = xinximenhu.gethtml(xinximenhu.liburl1);// 带着上面获取的cookie安全进入图书馆
				LibData = xinximenhu.ParseHtml(books, 2);//获得当前借阅信息
				System.out.println("当前借阅数据"+LibData);
				books = xinximenhu.gethtml(xinximenhu.libhistoryurl2);
				LibhisData = xinximenhu.ParseHtml(books, 5);//获得历史借阅信息
				Message msg1 = Message.obtain();
				msg1.what = 1;//libData
				handler.sendMessage(msg1);
				CardData = xinximenhu.ParseHtml(
						xinximenhu.gethtml(xinximenhu.cardurl), 0);
				NetData = xinximenhu.ParseHtml(
						xinximenhu.gethtml(xinximenhu.netinfourl), 3);
				Message msg2 = Message.obtain();
				msg2.what = 2;//cardData and netdata
				handler.sendMessage(msg2);
				SaveCurrentData();
//				UserinfoData = xinximenhu.ParseHtml(
//						xinximenhu.gethtml(xinximenhu.userinfourl), 6);
//				Message msg3 = Message.obtain();
//				msg3.what = 6;//cardData and netdata
//				handler.sendMessage(msg3);
				
			}else{
				
				Message msg3 = Message.obtain();
				msg3.what = 3;//登录失败
				handler.sendMessage (msg3);
				//handler.sendMessage(msg3);
			}
		}
	}
	
	public class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch(msg.what){
			case 0:
				FirstFragment.loadData(NewsData);
				break;
			case 1:
				SecondFragment.loadData(LibData, LibhisData);
				break;
			case 2:
				ThirdFragment.loadData(CardData, NetData);
				break;
			case 3:
				Toast toast = Toast.makeText(MainActivity.this, "网络连接失败",
						Toast.LENGTH_SHORT);
				toast.show();
				break;
//			case 6:
//				Editor editor = sp.edit();
//				for(int j = 0;j<UserinfoData.size();j++){
//					editor.putString(""+j, UserinfoData.get(j));
//				}
//				editor.commit();
//				Name = UserinfoData.get(1);
//				nametv.setText(Name);
//				break;
//				//LoadHisData();
			}
		}
	}
	/*
	 * (non-Javadoc) 点返回键两次退出
	 * @see android.support.v4.app.FragmentActivity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
	    if (doubleBackToExitPressedOnce) {
	        super.onBackPressed();
	        return;
	    }

	    this.doubleBackToExitPressedOnce = true;
	    Toast.makeText(this, "双击返回键退出", Toast.LENGTH_SHORT).show();

	    new Handler().postDelayed(new Runnable() {

	        @Override
	        public void run() {
	            doubleBackToExitPressedOnce=false;                       
	        }
	    }, 2000);
	} 
}
