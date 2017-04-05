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
	 * �����ɼ���Ϣ
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
			//1:�γ�����  2:�γ̳ɼ�  3:ѧ��    4:ѧ��ѧ�� 
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
//		hashmap.put(elements.get(1).select("th").get(1).text(), elements.get(1).select("td").get(1).text());//���֤��
//		hashmap.put(elements.get(2).select("th").get(0).text(), elements.get(2).select("td").get(0).text());//����
//		hashmap.put(elements.get(2).select("th").get(1).text(), elements.get(2).select("td").get(1).text());//������ò
//		hashmap.put(elements.get(3).select("th").get(0).text(), elements.get(3).select("td").get(0).text());//��������
//		hashmap.put(elements.get(3).select("th").get(1).text(), elements.get(3).select("td").get(1).text());//��ѧ����
//		hashmap.put(elements.get(5).select("th").get(0).text(), elements.get(5).select("td").get(0).text());//����
		
//		Document doc2 = Jsoup.parse(html2);
//		Elements elements = doc2.select("table[width=98%]");
//		for(int i=0;i<elements.select("th").size();i++){
//			list.add(elements.select("strong").get(i).text());
//			list.add( elements.select("td").get(i).text());
//								ѧ��
//								����
//								�Ա�
//								�����꼶
//								ѧԺ����
//								רҵ����
//								�����༶
//								��ѧ�༶
//		}
		
		Elements elements = doc1.select("table[class=table_main htb]").select("tr");
//		Log.i("tag--", elements.size()+"");
//		Log.i("tag--", elements.get(1).text());
//		Log.i("tag--", elements.get(1).select("th").size()+"");
//		Log.i("tag--", elements.get(1).select("th").get(1).text());
		Element ele = doc1.select("td[class=first]").first();
		list.add("����");
		list.add(ele.text());//����
		list.add(elements.get(1).select("th").get(1).text());
		list.add(elements.get(1).select("td").get(1).text());//���֤��
		list.add(elements.get(2).select("th").get(0).text());
		list.add(elements.get(2).select("td").get(0).text());//����
		list.add(elements.get(2).select("th").get(1).text());
		list.add(elements.get(2).select("td").get(1).text());//������ò
		list.add(elements.get(3).select("th").get(0).text());
		list.add(elements.get(3).select("td").get(0).text());//��������
		list.add(elements.get(5).select("th").get(1).text());
		list.add(elements.get(5).select("td").get(1).text());//����
		
		return list;
	}

}
