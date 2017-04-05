package com.example.myappdemo.DataLoader;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.example.myappdemo.R;
import com.example.myappdemo.Activity.BookDetails;
import com.example.myappdemo.Activity.MainActivity;
import com.example.myappdemo.Activity.NewsDetail;
import com.example.myappdemo.Activity.Setting;

public class DataLoader {

	private MainActivity MainActivity = new MainActivity().getcontext();
	/**
	 * 
	 */
	public Context context;

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	/**
	 * 加载校园卡消费记录和网费信息
	 * 
	 * @param v
	 *            :当前加载的view
	 * @param Data1
	 *            :校园卡消费记录
	 * @param Data2
	 *            :网费信息
	 */
	public void LoadCardData(View v, ArrayList<String> Data1,
			ArrayList<String> Data2) {
		TableLayout tablelayout = (TableLayout) v
				.findViewById(R.id.tablelayout1);
		tablelayout.setStretchAllColumns(true);
		if (tablelayout.getChildCount() > 3) {
			tablelayout.removeViewsInLayout(3, tablelayout.getChildCount() - 3);
		}
		if (!((Data2 == null)||Data2.equals(""))) {
			for (int row = 0; row <= Data1.size() - 4; row += 4) {
				TableRow tablerow = new TableRow(context);
				for (int col = 0; col < 4; col++) {
					TextView textview = new TextView(context);
					textview.setText(Data1.get(row + col));
					textview.setTextColor(context.getResources().getColor(
							R.color.black));
					tablerow.addView(textview);
//					Log.i("TAG2", Data1.get(row + col));
				}
				View view = new View(context);
				LayoutParams params = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
				params.setMargins(0, 5, 0, 5);
				view.setLayoutParams(params);
				view.setBackgroundColor(context.getResources().getColor(
						R.color.red));
				tablelayout.addView(tablerow);
				tablelayout.addView(view);
			}
		}
		TableLayout tablelayout2 = (TableLayout) v
				.findViewById(R.id.tablelayout2);
		tablelayout2.setStretchAllColumns(true);

		if (tablelayout2.getChildCount() > 2) {
			tablelayout2.removeViewsInLayout(2,
					tablelayout2.getChildCount() - 2);
		}
		if (!((Data2 == null)||Data2.equals(""))) {
			for (int row = 0; row < Data2.size(); row += 2) {
				TableRow tablerow = new TableRow(context);
				for (int col = 0; col < 2; col++) {
					TextView textview = new TextView(context);
					textview.setText(Data2.get(row + col));
					textview.setTextColor(context.getResources().getColor(
							R.color.black));
					tablerow.addView(textview);
//					Log.i("TAG2", Data2.get(row + col));
				}
				View view = new View(context);
				LayoutParams params = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
				params.setMargins(0, 5, 0, 5);
				view.setLayoutParams(params);
				view.setBackgroundColor(context.getResources().getColor(
						R.color.red));
				tablelayout2.addView(tablerow);
				tablelayout2.addView(view);
			}
		}
	}

	/**
	 * 加载图书馆借阅数据
	 * 
	 * @param v
	 *            :当前加载的view
	 * @param Data1
	 *            :当前借阅的数据
	 * @param Data2
	 *            :历史借阅的数据
	 */
	public void LoadLibData(View v, ArrayList<String> Data1,
			ArrayList<String> Data2) {
		// 加载当前借阅
		TableLayout tablelayout1 = (TableLayout) v
				.findViewById(R.id.tablelayout1);
		tablelayout1.setShrinkAllColumns(true);
		Date curtime = new Date(System.currentTimeMillis());
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String curtimestr = sDateFormat.format(curtime); 
		try {
			curtime = sDateFormat.parse(curtimestr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	//当前日期
		if (tablelayout1.getChildCount() > 2) {
			tablelayout1.removeViews(2, tablelayout1.getChildCount() - 2);
		}
		if (!((Data1 == null)||Data1.equals(""))) {
			for (int row = 0; row < Data1.size(); row += 4) {
				TableRow tablerow = new TableRow(context);
				for (int col = 0; col < 4; col++) {
					TextView textview = new TextView(context);
					textview.setMaxEms(12);
					if(col == 3){
						
						SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
						//字串轉回日期
						try {
							Date returndate = simpleDateFormat.parse(Data1.get(row + col));	//归还时间
							int b = compareTime(curtime,returndate);
							if(b>5){
								textview.setTextColor(context.getResources().getColor(
										R.color.black));
							}else if(b<=0){
								textview.setTextColor(context.getResources().getColor(
										R.color.red));
							}else{
								textview.setTextColor(context.getResources().getColor(
										R.color.yellow));
							}	
						} catch (ParseException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						textview.setMaxEms(4);
						textview.setText(Data1.get(row + col).trim());
					}else if (col == 2) {
						textview.setTextColor(context.getResources().getColor(
								R.color.black));
						textview.setMaxEms(4);
						textview.setText(Data1.get(row + col));
					}else {
						textview.setTextColor(context.getResources().getColor(
								R.color.black));
						textview.setMaxEms(12);
						textview.setText(Data1.get(row + col).trim());
						
					}
					tablerow.addView(textview);
//					Log.i("Lib2", Data1.get(row + col));
				}
				View view = new View(context);
				LayoutParams params = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
				params.setMargins(0, 5, 0, 5);
				view.setLayoutParams(params);
				view.setBackgroundColor(context.getResources().getColor(
						R.color.red));
				tablelayout1.addView(view);
				tablelayout1.addView(tablerow);
			}
		} else {
			Log.v("data1=", "null");
		}
		// 加载历史借阅
		TableLayout tablelayout2 = (TableLayout) v
				.findViewById(R.id.tablelayout2);
		tablelayout2.setShrinkAllColumns(true);
		if (tablelayout2.getChildCount() > 2) {
			tablelayout2.removeViewsInLayout(2,
					tablelayout2.getChildCount() - 2);
		}
		if (!((Data2 == null)||Data2.equals(""))) {
//			Log.i("Data2.size", Data2.size()+"");
			for (int row = 0; row < Data2.size(); row += 3) {
				TableRow tablerow = new TableRow(context);
				for (int col = 0; col < 2; col++) {
					TextView textview = new TextView(context);
					textview.setText(Data2.get(row + col).trim());
					textview.setTextColor(context.getResources().getColor(
							R.color.black));
					tablerow.addView(textview);
//					Log.i("Lib2", Data2.get(row + col) + "TAG");
				}
				View view = new View(context);
				LayoutParams params = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
				params.setMargins(0, 5, 0, 5);
				view.setLayoutParams(params);
				view.setBackgroundColor(context.getResources().getColor(
						R.color.red));
				tablerow.setBackground(context.getResources().getDrawable(R.drawable.tablerow_click));
				tablerow.setOnClickListener(new BookClick(Data2.get(row+2)));
//				Log.i("bookclick:"+row, Data2.get(row+2));
				tablelayout2.addView(view);
				tablelayout2.addView(tablerow);
			}
		}
	}

	/**
	 * 加载新闻
	 * 
	 * @param v
	 *            :当前的view
	 * @param data
	 *            :用于加载的数据
	 */
	public void LoadNewsData(View v, ArrayList<String> data) {
		TableLayout TL = (TableLayout) v.findViewById(R.id.tablelayout);
		if (TL.getChildCount() > 2) {
			TL.removeViewsInLayout(2, TL.getChildCount() - 2);
		}
		if (!data.isEmpty()) {
			for (int i = 0; i <= 27; i += 3) {
				TableRow tr = new TableRow(context);
				TextView tv1 = new TextView(context);
				tv1.setText(data.get(i)+"    --"+data.get(i + 2));
				// tv1.setWidth();
//				Log.i("data" + i, data.get(i));
				tv1.setTextColor(context.getResources()
						.getColor(R.color.myblue));
				tv1.setTextSize(18);
				tv1.setClickable(true);
				tv1.setBackground(context.getResources().getDrawable(R.drawable.tablerow_click));
				tv1.setOnClickListener(new NewsClick(data.get(i + 1)));
//				TextView tv2 = new TextView(context);
//				tv2.setText(data.get(i + 2));
//				tv2.setTextColor(context.getResources().getColor(R.color.beige));
//				tv2.setTextSize(18);
				View view = new View(context);
				LayoutParams params = new LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 1, 0);
				params.setMargins(0, 20, 0, 20);
				view.setLayoutParams(params);
				view.setBackgroundResource(R.color.blue);
//				Log.i("data", data.get(i + 1));
				tr.addView(tv1);
//				tr.addView(tv2);
				
				TL.addView(tr);
				TL.addView(view);
			}
		}
	}

	public class NewsClick implements OnClickListener {
		private final String link;
		String url = "http://www.ustb.edu.cn/";

		public NewsClick(String string) {
			// TODO Auto-generated constructor stub
			link = url + string;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// TextView tv = (TextView) v;
			// Log.i("textview", ""+v);
			// tv.setTextColor(MainActivity.this.getResources().getColor(R.color.white));
			Intent intent = new Intent();
			intent.setClass(context, NewsDetail.class);
			intent.putExtra("newsurl", link);
			context.startActivity(intent);
		}
	}
	
	public class BookClick implements OnClickListener {
		private String book_searchno;

		public BookClick(String string) {
			// TODO Auto-generated constructor stub
			book_searchno = string;
//			Log.i("book_searchno", book_searchno);
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			// TextView tv = (TextView) v;
			// Log.i("textview", ""+v);
			// tv.setTextColor(MainActivity.this.getResources().getColor(R.color.white));
			Intent intent = new Intent();
			intent.setClass(context, BookDetails.class);
			intent.putExtra("book_searchno", book_searchno);
			context.startActivity(intent);
		}
	}
	
	
	/**
	 * 比较时间
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public int compareTime(Date beginTime,Date endTime){
		int result = 0;
		long l = endTime.getTime() - beginTime.getTime();
		long day = l/(24*60*60*1000);
		result = (int)day;
		return result;
	}

}
