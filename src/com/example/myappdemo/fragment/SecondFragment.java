package com.example.myappdemo.fragment;

import java.util.ArrayList;

import com.example.myappdemo.R;
import com.example.myappdemo.Activity.MyData;
import com.example.myappdemo.DataLoader.DataLoader;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SecondFragment extends Fragment {

	private View currentview;
	private MyData mydata = new MyData();
	public SecondFragment() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentview = inflater.inflate(R.layout.tushuguan, container, false);
//		Log.i("creatview2", "creatview2");
		DataLoader dataloader = new DataLoader();
		dataloader.setContext(getActivity());
		dataloader.LoadLibData(currentview, mydata.getLibData(), mydata.getLibHisData());
		return currentview;
	}

	public View getCurrentView(){
		return currentview;
	}

	public void loadData(ArrayList<String> Data1,ArrayList<String> Data2){
		DataLoader dataloader = new DataLoader();
		dataloader.setContext(getActivity());
		dataloader.LoadLibData(currentview, Data1, Data2);
	};
}
