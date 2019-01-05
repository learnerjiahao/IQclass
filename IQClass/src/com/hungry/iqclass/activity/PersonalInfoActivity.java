package com.hungry.iqclass.activity;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class PersonalInfoActivity extends Activity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_person_info);
		
		((TextView)findViewById(R.id.tv_user_id)).setText(Config.getCachedUserId(this));
		((TextView)findViewById(R.id.tv_user_name)).setText(Config.getCachedUserName(this));
		((TextView)findViewById(R.id.tv_user_grade)).setText(Config.getCachedUserGrade(this));
		((TextView)findViewById(R.id.tv_user_profession)).setText(Config.getCachedUserProfession(this));
		((TextView)findViewById(R.id.tv_user_class)).setText(Config.getCachedUserClass(this));
		if(Config.getCachedUserGender(this).equals("0")){
			((TextView)findViewById(R.id.tv_user_gender)).setText("男");
		}else{
			((TextView)findViewById(R.id.tv_user_gender)).setText("女");
		}
		
		findViewById(R.id.bt_return_from_person_info).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity((new Intent(PersonalInfoActivity.this,HomeActivity.class)).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				finish();
			}
		});
		
	}

}
