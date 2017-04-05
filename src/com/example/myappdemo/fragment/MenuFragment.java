package com.example.myappdemo.fragment;

import com.example.myappdemo.R;
import com.example.myappdemo.Activity.Course;
import com.example.myappdemo.Activity.LoginActivity;
import com.example.myappdemo.Activity.NewsDetail;
import com.example.myappdemo.Activity.Score;
import com.example.myappdemo.Activity.Setting;
import com.example.myappdemo.Activity.UserInfoActivity;

import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class MenuFragment extends Fragment {

	private View currentview;
	private Button score,course,exit,userinfo;
	private TextView nametv ;
	
	public MenuFragment() {
		// TODO Auto-generated constructor stub
	}

	public View getcurrentview(){
		return currentview;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		currentview = inflater.inflate(R.layout.fragment_menu,container, false);
		exit = (Button)currentview.findViewById(R.id.exit);
		score = (Button)currentview.findViewById(R.id.score);
		course = (Button)currentview.findViewById(R.id.course);
		userinfo = (Button)currentview.findViewById(R.id.userinfo);
		exit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(),Setting.class);
//				Log.i("getActivity()", getActivity()+"");
				startActivity(intent);
			}

		});
		
		score.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(),Score.class);
//				Log.i("getActivity()", getActivity()+"");
				startActivity(intent);
			}
		});
		
		course.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(),Course.class);
//				Log.i("getActivity()", getActivity()+"");
				startActivity(intent);
			}
		});
		
		userinfo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(),UserInfoActivity.class);
//				Log.i("getActivity()", getActivity()+"");
				startActivity(intent);
			}
		});
		
		nametv = (TextView)currentview.findViewById(R.id.username);
		SharedPreferences sp = getActivity().getSharedPreferences("userinfo", 0);
		String name = sp.getString("1", "ÐÕÃû");
		nametv.setText(name);
		return currentview;
	}
	

}
