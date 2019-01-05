package com.hungry.iqclass.activity;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hungry.iqclass.ClassRecordListViewAdapter;
import com.hungry.iqclass.R;
import com.hungry.iqclass.net.GetClassRecords;

public class ClassRecordActivity extends Activity {
	
	private ListView lv_class_record;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_record);
		Intent intent = getIntent();
		((TextView)findViewById(R.id.tv_class_record_class_name)).setText(intent.getStringExtra("class_name"));
		lv_class_record = (ListView) findViewById(R.id.lv_class_record);
		findViewById(R.id.bt_return_from_class_record).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(ClassRecordActivity.this,
								HomeActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						finish();
					}
				});

		
		final ProgressDialog pd = ProgressDialog.show(this,"连接中","连接服务器中,请稍候"); 
		new GetClassRecords(intent.getStringExtra("class_id"),
				new GetClassRecords.SuccessCallback() {

					@Override
					public void onSuccess(
							List<HashMap<String, String>> classRecords) {
						// TODO Auto-generated method stub
						pd.dismiss();
						ClassRecordListViewAdapter myAdapter = new ClassRecordListViewAdapter(classRecords, ClassRecordActivity.this);
						lv_class_record.setAdapter(myAdapter);

					}
				}, new GetClassRecords.FailCallback() {

					@Override
					public void onFail(int errorStatus) {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(ClassRecordActivity.this,"无法链接服务器,检查您的网络", Toast.LENGTH_LONG).show();

					}
				});

	}
}
