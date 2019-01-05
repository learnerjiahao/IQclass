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
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.OtherLessonsListViewAdapter;
import com.hungry.iqclass.R;
import com.hungry.iqclass.net.GetOtherClasses;

public class GetOtherLessonsActivity extends Activity{

	ListView lv_get_other_lessons;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_other_lessons);
		
		findViewById(R.id.bt_return_from_get_other_lessons).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity((new Intent(GetOtherLessonsActivity.this,HomeActivity.class)).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				finish();
			}
		});
		lv_get_other_lessons = (ListView)findViewById(R.id.lv_get_other_classes);
		
		final ProgressDialog pd = ProgressDialog.show(this,"连接中","连接服务器中,请稍候");
		new GetOtherClasses(Config.getCachedSemesterChoice(this),GetOtherLessonsActivity.this, new GetOtherClasses.SuccessCallback() {
			
			@Override
			public void onSuccess(List<HashMap<String, String>> otherLessons) {
				// TODO Auto-generated method stub
				pd.dismiss();
				if(otherLessons.size()<1){
					Toast.makeText(GetOtherLessonsActivity.this, "数据库中暂无您课程外的课程信息，请等待其他用户加入！", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Toast.makeText(GetOtherLessonsActivity.this, "您课表外的课程查询成功", Toast.LENGTH_SHORT).show();
				OtherLessonsListViewAdapter myOtherLessonsAdapter = new OtherLessonsListViewAdapter(otherLessons, GetOtherLessonsActivity.this);
				lv_get_other_lessons.setAdapter(myOtherLessonsAdapter);
				
			}
		}, new GetOtherClasses.FailCallback() {
			
			@Override
			public void onFail(int errTag) {
				// TODO Auto-generated method stub
				pd.dismiss();
				Toast.makeText(GetOtherLessonsActivity.this, "网络异常，请确认后重试", Toast.LENGTH_SHORT).show();
				
				
			}
		});
		
	}
}
