package com.example.myappdemo.ViewOverride;

import com.example.myappdemo.R;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class AboutDialog extends Dialog implements
		android.view.View.OnClickListener {
	int layoutRes;

	Context context;

	private Button confirmBtn;

	public static final int TOAST_TIME = 1000;

	public AboutDialog(Context context) {
		super(context);
		this.context = context;
	}

	/**
	 * 
	 * 
	 * @param context
	 * @param resLayout
	 */
	public AboutDialog(Context context, int resLayout) {
		super(context);
		this.context = context;
		this.layoutRes = resLayout;
	}

	/**
	 * 
	 * 
	 * @param context
	 * @param theme
	 * @param resLayout
	 */
	public AboutDialog(Context context, int theme, int resLayout) {
		super(context, theme);
		this.context = context;
		this.layoutRes = resLayout;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(layoutRes);

		confirmBtn = (Button) findViewById(R.id.confirm_btn);

		confirmBtn.setTextColor(0xff1E90FF);

		confirmBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		AboutDialog.this.cancel();
			

	}
}