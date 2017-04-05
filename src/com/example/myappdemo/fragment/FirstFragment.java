package com.example.myappdemo.fragment;

import java.util.ArrayList;

import com.example.myappdemo.R;
import com.example.myappdemo.Activity.MainActivity;
import com.example.myappdemo.Activity.MyData;
import com.example.myappdemo.Activity.NewsDetail;
import com.example.myappdemo.Activity.Setting;
import com.example.myappdemo.DataLoader.DataLoader;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;

public class FirstFragment extends Fragment {

	private View currentview;
	private MyData mydata = new MyData();
	public FirstFragment() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
			final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentview = inflater.inflate(R.layout.xiaoyuandongtai, container,
				false);
		DataLoader dataloader = new DataLoader();
		dataloader.setContext(getActivity());
//		Log.i("currentview", ""+currentview);
//		Log.i("newsdata", ""+mydata.getNewsData()+"");
		dataloader.LoadNewsData(currentview,mydata.getNewsData());
		return currentview;
	}

	public View getCurrentView() {
		// TODO Auto-generated method stub
		return currentview;
	}

	public void loadData(ArrayList<String> Data){
		DataLoader dataloader = new DataLoader();
		dataloader.setContext(getActivity());
//		Log.i("currentview", ""+currentview);
		dataloader.LoadNewsData(currentview, Data);
	}

}
