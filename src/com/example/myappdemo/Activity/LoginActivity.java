package com.example.myappdemo.Activity;

import com.example.myappdemo.R;
import com.example.myappdemo.httptools.xinximenhuhttp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText numet, psdet;
	private Button loginbtn;
	private String number, password;
	xinximenhuhttp http = new xinximenhuhttp();

	@Override
	protected void onCreate(final Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loginbtn = (Button) this.findViewById(R.id.post);
		loginbtn.setOnClickListener(listener);
		numet = (EditText) this.findViewById(R.id.number);
		psdet = (EditText) this.findViewById(R.id.password);
	}

	public OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(final View v) {
			// TODO Auto-generated method stub
			number = numet.getText().toString();
			password = psdet.getText().toString();
			final String[] params = { number, password };
			new PostTask().execute(params);
		}
	};

	public class PostTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(final String... params) {
			// TODO Auto-generated method stub
			final Boolean loginresult = http.post(params[0], params[1]);
			return loginresult;
		}

		@Override
		protected void onPostExecute(final Boolean result) {
			// TODO Auto-generated method stub
			if (result) {
				final SharedPreferences sp = getSharedPreferences("userinfo", 0);
				final Editor editor = sp.edit();
				editor.putString("username", number);
				editor.putString("password", password);
				editor.commit();
				final Intent intent = new Intent();
				intent.setClass(LoginActivity.this, MainActivity.class);
				intent.putExtra("loginresult", true);
				startActivity(intent);
				LoginActivity.this.finish();
			} else {
				Toast.makeText(LoginActivity.this, "µÇÂ¼Ê§°Ü", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

}
