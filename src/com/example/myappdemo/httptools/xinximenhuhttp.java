package com.example.myappdemo.httptools;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class xinximenhuhttp {
	static HttpClient httpclient;
	private List<Cookie> cookies;
	private List<Cookie> cookies2;
	private HttpResponse httpresponse;
	public String cookievalue;
	public final String cardurl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p381";
//	public final String userinfourl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p1564";
//	public final String liburl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p387";
	public final String netinfourl = "http://e.ustb.edu.cn/index.portal?.pn=p378_p388";
	public final String liburl1 = "http://libmis.ustb.edu.cn:8080/reader/book_lst.php";
	public final String libhistoryurl2 = "http://libmis.ustb.edu.cn:8080/reader/book_hist.php";
	public final String libhistoryurl = "http://libmis.ustb.edu.cn:8080/reader/hwthau.php";
	// public final String libsearchurl =
	// "http://lib.ustb.edu.cn:8080/opac/openlink.php?strSearchType=title&match_flag=forward&historyCount=1&strText=photoshop&doctype=ALL&displaypg=20&showmode=list&sort=CATA_DATE&orderby=desc&dept=ALL";
	// action=bulletinsMoreView&isTabPage=false&.ia=false&.f=f1428&pageSize=&.pmn=view&.pen=pe621&groupid=18";
	public final String newsurl = "http://www.ustb.edu.cn/AnnounceList.asp";
	//public final String newsurl = "http://e.ustb.edu.cn/index.portal";

	/**
	 * 创建静态client对象,提交表单
	 * 
	 * @param username
	 *            :提交的用户名(信息门户)
	 * @param password
	 *            :提交的密码(信息门户)
	 * @return (true:登录成功;false:登录失败)
	 */
	public boolean post(final String username, final String password) {
		boolean loginresult = false;
		String cookievalue = "";
		final String url1 = "http://e.ustb.edu.cn/userPasswordValidate.portal";
		final HttpPost httppost = new HttpPost(url1);
		BasicHttpParams httpParams = new BasicHttpParams();  
	    HttpConnectionParams.setConnectionTimeout(httpParams, 4000);  
	    HttpConnectionParams.setSoTimeout(httpParams, 4000);
		httpclient = new DefaultHttpClient(httpParams);
		final List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("Login.Token1", username));
		params.add(new BasicNameValuePair("Login.Token2", password));
		params.add(new BasicNameValuePair("goto",
				"http://e.ustb.edu.cn/loginSuccess.portal"));
		params.add(new BasicNameValuePair("gotoOnFail",
				"http://e.ustb.edu.cn/loginFailure.portal"));
		//httpclient.getParams().setParameter(
			//	CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		// TODO Auto-generated method stub
		try {
//			Log.i("--TAG--","--1--");
			final HttpEntity entity = new UrlEncodedFormEntity(params, "gb2312");
			httppost.setEntity(entity);
			httpresponse = httpclient.execute(httppost);
			final String loginresultstr = EntityUtils.toString(httpresponse
					.getEntity());
			//Log.i("login", loginresultstr + "---->");
			if (httpresponse.getStatusLine().getStatusCode() == 200) { // 返回值正常
				if (loginresultstr.contains("false")) {
					loginresult = false;
				} else if (loginresultstr.contains("LoginSuccessed")) { // 获取返回的cookie
					cookies = ((AbstractHttpClient) httpclient)
							.getCookieStore().getCookies();
					cookievalue = cookies.get(0).getValue();
				//	Log.i(cookies.get(0).getName(), cookievalue + "---->");
					loginresult = true;
				} else {
					Log.i("--TAG--","--2--");
				}
			} else {
				Log.i("--TAG--","--3--");
			}
		} catch (final MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loginresult;
	};

	/**
	 * 获取html页面,utf-8编码
	 * 
	 * @param url
	 *            :需要抓取的网页地址
	 * @return 网页源码
	 */
	public String gethtml(final String url) {
		String html = "";
		HttpGet httpget = new HttpGet(url);
		// httpget.setHeader("iPlanetDirectoryPro",cookievalue);
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);
		try {
			httpresponse = httpclient.execute(httpget);
			html = EntityUtils.toString(httpresponse.getEntity());
			// html = EntityUtils.toString(httpresponse.getEntity(),"gb2312");
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.i("html", html + "---->");
		return html;
	}

	/**
	 * 获取html页面 ,gb2312编码
	 * 
	 * @param url
	 *            :需要抓取的网页地址
	 * @return 网页源码
	 */
	public String gethtmlgb2312(final String url) {
		String html = "";
		final HttpGet httpget = new HttpGet(url);
		// httpget.setHeader("iPlanetDirectoryPro",cookievalue);
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);
		try {
			httpresponse = httpclient.execute(httpget);
			html = EntityUtils.toString(httpresponse.getEntity(), "gb2312");
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.i("html", html + "---->");
		return html;
	}

	/**
	 * 带cookie获取html界面,并获取新cookie
	 * 
	 * @param url 
	 *            :需要抓取的网页地址
	 * @return 网页源码
	 */
	public String gethtml2(final String url) {
		String html = "";
		final HttpGet httpget = new HttpGet(url);
		httpget.setHeader("iPlanetDirectoryPro", cookievalue);
		httpclient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 4000);
		try {
			httpresponse = httpclient.execute(httpget);
			html = EntityUtils.toString(httpresponse.getEntity(), "utf-8");
			cookies2 = ((AbstractHttpClient) httpclient).getCookieStore()
					.getCookies();
			cookievalue = cookies2.get(0).getValue();
			//Log.i(cookies2.get(0).getName(), cookievalue + "---->");
			// html = EntityUtils.toString(httpresponse.getEntity(),"gb2312");
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Log.i("html", html + "---->");
		return html;
	}

	/**
	 * 解析html字符串
	 * 
	 * @param html
	 *            需要解析的网页源码
	 * @param operation
	 *            解析操作(0:校园卡消费记录 , 1:用户信息 , 2:图书馆当前借阅 , 3:网费 , 4:新闻 ,
	 *            5:图书馆借阅历史,需转码 ,6:用户信息)
	 * @return 存放结果的ArrayList<String>
	 */
	public ArrayList<String> ParseHtml(final String html, final int operation) {

		final ArrayList<String> list = new ArrayList<String>();
		final Document doc = Jsoup.parse(html);
		Elements elements = null;
		switch (operation) {
		case 0: // 校园卡记录
			elements = doc.select("td[align=center]");
			for (final Element element : elements) {
				list.add(element.text());
				//Log.i("TAG1", element.text());
			}
			list.remove(0);
			break;
		case 1: // 用户信息
			elements = doc.select("td[align=center]");
			for (final Element element : elements) {
				list.add(element.text());
				//Log.i("TAG1", element.text());
			}
			break;
		case 2: // 图书馆当前借阅
			elements = doc.select("table[bgcolor=#CCCCCC]").select("tr");
			for (int i = 1;i<elements.size();i++) {
//				list.add(elements.get(i).select("td").get(index));
//				Log.i("图书馆当前借阅: ", element.text());
				Elements es = elements.get(i).select("td");
				String s = es.get(1).text();
				String[] ss = s.split("/");
				list.add(ss[0]);
				list.add(ss[1]);
				list.add(es.get(2).text());
				list.add(es.get(3).text());
			}
			System.out.println("list: "+list);
			break;
		case 3: // 网费
			elements = doc.select("th[align=right]");
			final Elements elements2 = doc.select("td[align=left]");
			int i = 0;
			for (i = 0; i < elements.size(); i++) {
				list.add(elements.get(i).text());
				list.add(elements2.get(i).text());
			//	Log.i("TAG1", elements.get(i).text());
				//Log.i("TAG1", elements2.get(i).text());
			}
			final Elements elements3 = doc.select("td[align=right]");
			list.add(elements3.get(0).text());
			list.add(elements2.get(i).text());
			break;
		case 4: // 新闻
			elements = doc.select("a[target=_self]");// 新闻标题,链接
			// Elements elements2 = doc.select("a[target=_self]");//新闻链接
			final Elements elements4 = doc.select("td[align=right]");// 日期
//			Log.i("news", html);
			for (int j = 0; j < 10; j++) {
				list.add(elements.get(j).text());
				list.add(elements.get(j).attr("href"));
				list.add(elements4.get(j).text());
//				Log.i("news" + j, elements.get(j).text());
//				Log.i("news" + j, elements.get(j).attr("href"));
//				Log.i("news" + j, elements4.get(j).text());
			}
			break;
		case 5:// 图书馆历史借阅
			elements = doc.select("td[bgcolor=#FFFFFF]");
			for (int k = 0; k < elements.size(); k += 7) {
				 String s1 = new String(elements.get(k + 2).text());// 书名
				 String s2 = new String(elements.get(k + 3).text());// 编者
				 String temp= elements.get(k + 2).select("a").attr("href");
				 String s3 = temp.substring(1 + temp.lastIndexOf('='));//书号
//				 Log.i("book_no0",temp);
//				 Log.i("book_no", s1+":"+s2+":"+s3);
				//localHashMap1.put("book_searchno", str2.substring(1 + str2.lastIndexOf('=')));
				list.add(s1);
				list.add(s2);
				list.add(s3);
			}
//			Log.i("list.size",list.size()+"");
			break;
//		case 6: //用户信息
//			elements = doc.select("td[align=center]");
//			for(Element element : elements){
//				list.add(element.text());
//			}
		}
		return list;
	}

	/**
	 * 解析新闻详情
	 * 
	 * @param html
	 *            :用于解析的网页地址
	 * @return 新闻详情:String类型
	 */
	public String ParseHtml(final String html) {

		String result = "";
		final Document doc = Jsoup.parse(html);
		Elements elements = null;
		elements = doc.select("p");// 新闻标题,链接
		for (final Element element : elements) {
			result = result + element.text();
		}
		return result;
	}

	/**
	 * 
	 */

}
