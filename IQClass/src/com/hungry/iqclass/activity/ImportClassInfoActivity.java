package com.hungry.iqclass.activity;

import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.R;
import com.hungry.iqclass.net.ImportClassAndUserInfo;

public class ImportClassInfoActivity extends Activity {

	private Button bt_return_from_add = null;
	private Button bt_add_class = null;
	private Spinner sp_school = null;
	private Spinner sp_semeter = null;
	private Spinner sp_choose_weekth = null;
	private Context mContext = null;

	private ArrayAdapter semeter_spinner_adapter = null;
	private ArrayAdapter weeks_spinner_adapter = null;
	private String semester;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_lesson);
		mContext = this;
		bt_return_from_add = (Button) findViewById(R.id.bt_return_from_add);
		bt_add_class = (Button) findViewById(R.id.bt_add);
		sp_school = (Spinner) findViewById(R.id.sp_school);
		sp_semeter = (Spinner) findViewById(R.id.sp_semeter);
		sp_choose_weekth = (Spinner) findViewById(R.id.sp_choose_weeks);

		semeter_spinner_adapter = ArrayAdapter.createFromResource(this,
				R.array.semeters, android.R.layout.simple_spinner_item);
		semeter_spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		weeks_spinner_adapter = ArrayAdapter.createFromResource(this,
				R.array.weeks, android.R.layout.simple_spinner_item);
		weeks_spinner_adapter
		.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		
		
		
		sp_semeter.setVisibility(View.VISIBLE);
		sp_semeter.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				semester = (String) semeter_spinner_adapter.getItem(position);
				Config.cacheSemesterChoice(mContext, semester);
				System.out.println(semester);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				semester = (String) semeter_spinner_adapter.getItem(0);
				Config.cacheSemesterChoice(mContext, semester);
				System.out.println(semester);
			}
		});
		sp_choose_weekth.setVisibility(View.VISIBLE);
		
		final Date time = new Date();		
		sp_choose_weekth.setOnItemSelectedListener(new OnItemSelectedListener() {
			
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int current_week = position;
				Config.cacheCurrentWeekth(mContext, current_week+1,time.getTime());
				Log.i("hhh", "current_week" + (current_week+1));
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				Config.cacheCurrentWeekth(mContext, 1, time.getTime());
			}
		});

		bt_return_from_add.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(ImportClassInfoActivity.this,
						HomeActivity.class));
				//.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
				finish();
			}
		});

		bt_add_class.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final ProgressDialog pd = ProgressDialog.show(mContext,"连接中","连接服务器中,请稍候");
				new ImportClassAndUserInfo(Config.getCachedUserId(mContext), Config
						.getCachedPassword(mContext), semester, Config
						.getCachedUserType(mContext),
						new ImportClassAndUserInfo.SuccessCallback() {

							@Override
							public void onSuccess(JSONArray objArrayClasses,JSONObject objUserInfo) {
								// TODO Auto-generated method stub
								// System.out.println(objArrayClasses);
								pd.dismiss();
								if(!Config.getCachedUserType(mContext).equals("teacher")){
									
									try {
										Config.cachedUserMoreInfo(mContext, objUserInfo.getString(Config.KEY_USER_NAME),
												objUserInfo.getString(Config.KEY_USER_GENDER), objUserInfo.getString(Config.KEY_USER_GRADE),
												objUserInfo.getString(Config.KEY_USER_PROFESSION),objUserInfo.getString(Config.KEY_USER_CLASS));
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									
								}
								SQLiteDatabase mySQLiteDatabase = ImportClassInfoActivity.this
										.openOrCreateDatabase(
												"am_here_datebase",
												MODE_PRIVATE, null);
//								mySQLiteDatabase
//										.execSQL("create table if not exists my_class(class_id,class_name,class_teacher_name"
//												+ ",class_credit,class_time_location,class_period,class_type,class_semester)");
								JSONObject objClass = null;
								for (int i = 0; i < objArrayClasses.length(); i++) {
									try {
										objClass = (JSONObject) objArrayClasses
												.get(i);
										//System.out.println(objClass);
										Cursor selectCursor = mySQLiteDatabase
												.query("my_class",
														null,
														"class_id="
																+ objClass
																		.getString(Config.KEY_CLASS_ID),
														null, null, null, null,
														null);
										if (selectCursor.getCount() <= 0) {
											mySQLiteDatabase.execSQL("insert into my_class(class_id,class_name,class_teacher_name "
													+ ",class_credit,class_type,class_period,class_time_location,class_semester) "
													+ " values ("
													+ objClass
															.getString(Config.KEY_CLASS_ID)
													+ ",'"
													+ objClass
															.getString(Config.KEY_CLASS_NAME)
													+ "','"
													+ objClass
															.getString(Config.KEY_CLASS_TEACHER_NAME)
													+ "',"
													+ objClass
															.getString(Config.KEY_CLASS_CREDIT)
													+ ",'"
													+ objClass
															.getString(Config.KEY_CLASS_TYPE)
													+ "',"
													+ objClass
															.getString(Config.KEY_CLASS_PERIOD)
													+ ",'"
													+ objClass
															.getString(Config.KEY_CLASS_TIME_LOCATION)
													+ "','"
													+ objClass
															.getString(Config.KEY_CLASS_SEMESTER)
													+ "')");
										} else {
											mySQLiteDatabase.execSQL("update my_class set class_name='"
													+ objClass
															.getString(Config.KEY_CLASS_NAME)
													+ "',class_teacher_name='"
													+ objClass
															.getString(Config.KEY_CLASS_TEACHER_NAME)
													+ "',class_credit="
													+ objClass
															.getString(Config.KEY_CLASS_CREDIT)
													+ ",class_type='"
													+ objClass
															.getString(Config.KEY_CLASS_TYPE)
													+ "',class_period="
													+ objClass
															.getString(Config.KEY_CLASS_PERIOD)
													+ ",class_time_location='"
													+ objClass
															.getString(Config.KEY_CLASS_TIME_LOCATION)
													+ "',class_semester='"
													+ objClass
															.getString(Config.KEY_CLASS_SEMESTER)
													+ "' where class_id = "
													+ objClass
															.getInt(Config.KEY_CLASS_ID));
										}

									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								mySQLiteDatabase.close();
								Toast.makeText(ImportClassInfoActivity.this,
										"导入课表成功", Toast.LENGTH_LONG).show();
								
								startActivity(new Intent(ImportClassInfoActivity.this,HomeActivity.class));
								finish();

							}
						}, new ImportClassAndUserInfo.FailCallback() {

							@Override
							public void onFail(int errorStatus) {
								// TODO Auto-generated method stub
								pd.dismiss();
								switch (errorStatus) {
								case Config.RESULT_STATUS_SEMESTER_ERR:
									Toast.makeText(
											ImportClassInfoActivity.this,
											"教务系统暂无该学期的课程内容", Toast.LENGTH_LONG)
											.show();
									break;
								case Config.RESULT_STATUS_ERR:
									Toast.makeText(
											ImportClassInfoActivity.this,
											"帐号或者密码错误，请确认后重试",
											Toast.LENGTH_LONG).show();
									break;
								default:
									Toast.makeText(
											ImportClassInfoActivity.this,
											"无法链接服务器,检查您的网络", Toast.LENGTH_LONG)
											.show();
									break;
								}
							}
						});
			}
		});

	}
}
