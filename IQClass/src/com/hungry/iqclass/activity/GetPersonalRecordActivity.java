package com.hungry.iqclass.activity;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.PersonalRecordListViewAdapter;
import com.hungry.iqclass.R;
import com.hungry.iqclass.net.GetPersonalRecord;




public class GetPersonalRecordActivity extends Activity {
	
	private ListView myRecordListView;
	private Spinner sp;
	private ArrayAdapter record_sp_adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_personal_record);
		
		myRecordListView = (ListView)findViewById(R.id.lv_get_personal_record);
		Button bt = (Button) findViewById(R.id.bt_return_from_get_personal_record);
		sp = (Spinner) findViewById(R.id.sp_peroid_record);
		
		
		record_sp_adapter = ArrayAdapter.createFromResource(
				this, R.array.peroid_record,
				android.R.layout.simple_spinner_item);
		record_sp_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		sp.setVisibility(View.VISIBLE);
		
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity((new Intent(GetPersonalRecordActivity.this,HomeActivity.class)).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				finish();
			}
		});
		
		
		sp.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				final ProgressDialog pd = ProgressDialog.show(GetPersonalRecordActivity.this,"连接中","连接服务器中,请稍候");
				new GetPersonalRecord(Config.getCachedUserId(GetPersonalRecordActivity.this), position+"", new GetPersonalRecord.SuccessCallback() {
					
					@Override
					public void onSuccess(List<HashMap<String, String>> myRecords) {
						// TODO Auto-generated method stub
						pd.dismiss();
						if(myRecords.size()<1) {
							Toast.makeText(GetPersonalRecordActivity.this, "该时期内，您无任何考勤违纪记录。", Toast.LENGTH_SHORT).show();
							return;
						}
						Adapter myRecordAdapter = new PersonalRecordListViewAdapter(myRecords,GetPersonalRecordActivity.this);
						myRecordListView.setAdapter((ListAdapter) myRecordAdapter);
						
						Toast.makeText(GetPersonalRecordActivity.this, "考勤记录获取成功", Toast.LENGTH_SHORT).show();
						
					}
				}, new GetPersonalRecord.FailCallback() {
					
					@Override
					public void onFail(int errorCode) {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(GetPersonalRecordActivity.this, "网络异常，请确认后重试", Toast.LENGTH_SHORT).show();
					}
				});
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
			}
		});
		
		
		sp.setAdapter(record_sp_adapter);
		
	
	}
}
