package com.example.myappdemo.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.myappdemo.DataLoader.DataLoader;
import com.example.myappdemo.httptools.jsoup;
import com.example.myappdemo.httptools.xinximenhuhttp;

import android.test.AndroidTestCase;
import android.util.Log;

public class testcase extends AndroidTestCase {

	public final String cardurl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p381";
	public final String userinfourl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p1564";
	public final String liburl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p387";
	public final String netinfourl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p388";
	public final String libhistoryurl = "http://libmis.ustb.edu.cn:8080/reader/book_hist.php";

	// public final String newsurl =
	// "http://e.ustb.edu.cn/detach.portal?action=bulletinsMoreView&isTabPage=false&.ia=false&.f=f1428&pageSize=&.pmn=view&.pen=pe621&groupid=18";

	public testcase() {
		// TODO Auto-generated constructor stub

	}

	public void test() {
		DataLoader test = new DataLoader();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		// 字串轉回日期
		try {
			Date returndate = simpleDateFormat.parse("2015-11-02"); // 归还时间

			Date curtime = new Date(System.currentTimeMillis());
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String curtimestr = sDateFormat.format(curtime);
			curtime = sDateFormat.parse(curtimestr);
			int l = test.compareTime(curtime, returndate);
			Log.i("---l---", "---" + l + "---");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.i("end", "end");
	}

}
