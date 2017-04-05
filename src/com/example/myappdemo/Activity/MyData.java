package com.example.myappdemo.Activity;

import java.util.ArrayList;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.provider.OpenableColumns;

public class MyData{

	private static ArrayList<String> CardData, LibData, LibHisData,NewsData,NetData;
	
	public MyData(){

	}
	public  ArrayList<String> getCardData() {
		return CardData;
	}

	public static  void setCardData(ArrayList<String> cardData) {
		CardData = cardData;
	}

	public  ArrayList<String> getLibData() {
		return LibData;
	}

	public static  void setLibData(ArrayList<String> libData) {
		LibData = libData;
	}

	public  ArrayList<String> getLibHisData() {
		return LibHisData;
	}

	public static  void setLibHisData(ArrayList<String> libHisData) {
		LibHisData = libHisData;
	}

	public  ArrayList<String> getNewsData() {
		return NewsData;
	}

	public static void setNewsData(ArrayList<String> newsData) {
		NewsData = newsData;
	}

	public  ArrayList<String> getNetData() {
		return NetData;
	}

	public static  void setNetData(ArrayList<String> netData) {
		NetData = netData;
	}
}
