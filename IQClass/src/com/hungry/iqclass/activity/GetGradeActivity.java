package com.hungry.iqclass.activity;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.MyGradeListViewAdapter;
import com.hungry.iqclass.R;
import com.hungry.iqclass.net.GetMyGrade;

public class GetGradeActivity extends Activity {

	private Spinner sp_get_grade;
	private ListView lv_my_grade;
	private TextView tv_whole_grade;
	private TextView tv_other_grade;
	private EditText et_grade_search;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_grade);
		sp_get_grade = (Spinner) findViewById(R.id.sp_grade_chose_peroid);
		lv_my_grade = (ListView) findViewById(R.id.lv_my_grade);
		tv_other_grade = (TextView) findViewById(R.id.tv_other_grade);
		tv_whole_grade = (TextView) findViewById(R.id.tv_whole_grade);
		et_grade_search = (EditText) findViewById(R.id.et_search_grade_password);

		findViewById(R.id.bt_return_from_get_grade).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						startActivity(new Intent(GetGradeActivity.this,
								HomeActivity.class)
								.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
						finish();
					}
				});

		findViewById(R.id.bt_search_grade).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String password = et_grade_search.getText().toString();
						if (TextUtils.isEmpty(password)) {
							Toast.makeText(GetGradeActivity.this, "密码不能为空",
									Toast.LENGTH_SHORT).show();
							return;
						}
						if (!password.equals(Config
								.getCachedPassword(GetGradeActivity.this))) {
							Toast.makeText(GetGradeActivity.this,
									"与您用于登录的密码不符，请确认后重试", Toast.LENGTH_SHORT)
									.show();
							return;
						}
						final ProgressDialog pd = ProgressDialog.show(
								GetGradeActivity.this, "连接中", "连接服务器中,请稍候");
						new GetMyGrade(Config
								.getCachedUserId(GetGradeActivity.this),
								password, getGradeSemester(sp_get_grade.getSelectedItemPosition()),
								new GetMyGrade.SuccessCallback() {

									@Override
									public void onSuccess(
											List<HashMap<String, String>> myGrade,
											String whole_grade,
											String other_grade) {
										// TODO Auto-generated method stub
										pd.dismiss();
										tv_whole_grade.setText(whole_grade);
										tv_other_grade.setText(other_grade);
										MyGradeListViewAdapter mAdapter = new MyGradeListViewAdapter(
												myGrade, GetGradeActivity.this);
										lv_my_grade.setAdapter(mAdapter);
										if(myGrade.size()<1){
											Toast.makeText(GetGradeActivity.this,
													"本科教学网中暂无查询到您当前学期的课程成绩", Toast.LENGTH_SHORT)
													.show();
											return;
										}
										Toast.makeText(GetGradeActivity.this,
												"查询成功", Toast.LENGTH_SHORT)
												.show();

									}
								}, new GetMyGrade.FailCallback() {

									@Override
									public void onFail(int errorCode) {
										// TODO Auto-generated method stub
										pd.dismiss();
										Toast.makeText(GetGradeActivity.this,
												"网络异常，请确认后重试",
												Toast.LENGTH_SHORT).show();

									}
								});
					}
				});

	}

	private String getGradeSemester(int tag) {
		String grade_SemString = null;
		switch (tag) {
		case 1:
			grade_SemString = "2015-2016-2 ";
			break;
		case 2:
			grade_SemString = "2015-2016-1 ";
			break;
		case 3:
			grade_SemString = "2014-2015-3 ";
			break;
		case 4:
			grade_SemString = "2014-2015-2 ";
			break;
		case 5:
			grade_SemString = "2014-2015-1 ";
			break;
		case 6:
			grade_SemString = "2013-2014-3 ";
			break;
		case 7:
			grade_SemString = "2013-2014-2 ";
			break;
		case 8:
			grade_SemString = "2013-2014-1 ";
			break;
		default:
			grade_SemString = "all";
			break;
		}
		return grade_SemString;
	}
}
