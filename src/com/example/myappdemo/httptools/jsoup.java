package com.example.myappdemo.httptools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class jsoup {

	
	/**
	 * 解析成绩信息
	 * @param html
	 * @return
	 */
	public List<HashMap<Integer, String>> ParseScore(String html) {
		List<HashMap<Integer, String>> list = new ArrayList<HashMap<Integer, String>>();
		Document doc = Jsoup.parse(html);
		Elements elements = doc.select("table[class=table_main vtb]").select(
				"tr");
		HashMap<Integer, String> hashmap;
		for (Element element : elements) {
			Elements elements2 = element.select("td");
			hashmap = new HashMap<Integer, String>();
			//1:课程名称  2:课程成绩  3:学分    4:学年学期 
			for (int i = 1; i < elements2.size(); i++) {
				hashmap.put(i, elements2.get(i).text());
//				Log.i("element"+i, elements2.get(i).text());
			}
			list.add(hashmap);
		}

		return list;

	}
	
	public ArrayList<String> ParseUserInfo(String html1){
		Document doc1 = Jsoup.parse(html1);
		ArrayList<String> list = new ArrayList<String>();
//		
//		hashmap.put(elements.get(1).select("th").get(1).text(), elements.get(1).select("td").get(1).text());//身份证号
//		hashmap.put(elements.get(2).select("th").get(0).text(), elements.get(2).select("td").get(0).text());//民族
//		hashmap.put(elements.get(2).select("th").get(1).text(), elements.get(2).select("td").get(1).text());//政治面貌
//		hashmap.put(elements.get(3).select("th").get(0).text(), elements.get(3).select("td").get(0).text());//出生日期
//		hashmap.put(elements.get(3).select("th").get(1).text(), elements.get(3).select("td").get(1).text());//入学日期
//		hashmap.put(elements.get(5).select("th").get(0).text(), elements.get(5).select("td").get(0).text());//籍贯
		
//		Document doc2 = Jsoup.parse(html2);
//		Elements elements = doc2.select("table[width=98%]");
//		for(int i=0;i<elements.select("th").size();i++){
//			list.add(elements.select("strong").get(i).text());
//			list.add( elements.select("td").get(i).text());
//								学号
//								姓名
//								性别
//								所属年级
//								学院名称
//								专业方向
//								行政班级
//								教学班级
//		}
		
		Elements elements = doc1.select("table[class=table_main htb]").select("tr");
//		Log.i("tag--", elements.size()+"");
//		Log.i("tag--", elements.get(1).text());
//		Log.i("tag--", elements.get(1).select("th").size()+"");
//		Log.i("tag--", elements.get(1).select("th").get(1).text());
		Element ele = doc1.select("td[class=first]").first();
		list.add("姓名");
		list.add(ele.text());//姓名
		list.add(elements.get(1).select("th").get(1).text());
		list.add(elements.get(1).select("td").get(1).text());//身份证号
		list.add(elements.get(2).select("th").get(0).text());
		list.add(elements.get(2).select("td").get(0).text());//民族
		list.add(elements.get(2).select("th").get(1).text());
		list.add(elements.get(2).select("td").get(1).text());//政治面貌
		list.add(elements.get(3).select("th").get(0).text());
		list.add(elements.get(3).select("td").get(0).text());//出生日期
		list.add(elements.get(5).select("th").get(1).text());
		list.add(elements.get(5).select("td").get(1).text());//籍贯
		
		return list;
	}

}
