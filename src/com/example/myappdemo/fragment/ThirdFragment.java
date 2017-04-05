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

public class ThirdFragment extends Fragment {

	private View currentview;
	private MyData mydata = new MyData();
	public ThirdFragment() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentview = inflater.inflate(R.layout.xiaofeijilu, container, false);
//		Log.i("creatview3", "creatview3");
		DataLoader dataloader = new DataLoader();
		dataloader.setContext(getActivity());
		dataloader.LoadCardData(currentview, mydata.getCardData(), mydata.getNetData());
		//LoadCardData(currentview, mydata.GetCardData());
		return currentview;
	}

	public View getCurrentView(){
		return currentview;
	}

	public void loadData(ArrayList<String> Data1, ArrayList<String> Data2){
		DataLoader dataloader = new DataLoader();
		dataloader.setContext(getActivity());
		dataloader.LoadCardData(currentview, Data1, Data2);
	};
}
