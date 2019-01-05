package com.hungry.iqclass.activity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.R;
import com.hungry.iqclass.net.UpdateMyNotePicture;

public class AskVocationActivity extends Activity {

	private EditText et_count_absent;
	private EditText et_vocation_start_time;
	private EditText et_vocation_end_time;
	private EditText et_vocation_note_picture;
	private String filePath;
	private String class_id;

	private int mYear;
	private int mMonth;
	private int mDay;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ask_vocation);
		Intent intent = getIntent();
		class_id  =intent.getStringExtra(Config.KEY_CLASS_ID);
		((EditText) findViewById(R.id.et_class_name_vocation)).setText(intent
				.getStringExtra(Config.KEY_CLASS_NAME));
		et_count_absent = (EditText) findViewById(R.id.et_count_absent);
		et_vocation_note_picture = (EditText) findViewById(R.id.et_note_picture);
		et_vocation_start_time = (EditText) findViewById(R.id.et_vocation_start_time);
		et_vocation_end_time = (EditText) findViewById(R.id.et_vocation_end_time);

		findViewById(R.id.bt_return_from_ask_vocation).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(AskVocationActivity.this,
								HomeActivity.class));
						finish();
					}
				});

		// 初始化Calendar日历对象
		Calendar mycalendar = Calendar.getInstance(Locale.CHINA);
		Date mydate = new Date(); // 获取当前日期Date对象
		mycalendar.setTime(mydate);// //为Calendar对象设置时间为当前日期

		mYear = mycalendar.get(Calendar.YEAR); // 获取Calendar对象中的年
		mMonth = mycalendar.get(Calendar.MONTH);// 获取Calendar对象中的月
		mDay = mycalendar.get(Calendar.DAY_OF_MONTH);// 获取这个月的第几天

		et_vocation_start_time.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
		et_vocation_end_time.setText(mYear + "-" + (mMonth + 1) + "-"
				+ (mDay + 1));
		et_vocation_start_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog dpd = new DatePickerDialog(
						AskVocationActivity.this, new myOnDateSetListener(v
								.getId()), mYear, mMonth, mDay);
				dpd.show();
			}
		});

		et_vocation_end_time.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				DatePickerDialog dpd = new DatePickerDialog(
						AskVocationActivity.this, new myOnDateSetListener(v
								.getId()), mYear, mMonth, mDay + 1);
				dpd.show();

			}
		});

		et_vocation_note_picture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_PICK);
				intent.setType("image/*");
				//以需要返回值的模式开启一个Activity
				startActivityForResult(intent, 0);

			}
		});
		
		
		findViewById(R.id.bt_summit_vocation_note).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(TextUtils.isEmpty(et_count_absent.getText())||new Integer(et_count_absent.getText().toString())<1){
					Toast.makeText(AskVocationActivity.this, "缺课次数不能为空，且必须为大于0的纯数字", Toast.LENGTH_SHORT).show();
					return;
				}
				SimpleDateFormat format = new SimpleDateFormat("yyyy-M-dd HH:mm:ss");
				//d = format.parse(string)
				try {
					if((format.parse(et_vocation_end_time.getText().toString())).getTime()<=(format.parse(et_vocation_start_time.getText().toString())).getTime()){
						Toast.makeText(AskVocationActivity.this, "开始请假日期不能晚于结束请假时期，请检查后重试", Toast.LENGTH_SHORT).show();
						return;
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(TextUtils.isEmpty(et_vocation_note_picture.getText())){
					Toast.makeText(AskVocationActivity.this, "您还未选择假条照片，请选择好后重试", Toast.LENGTH_SHORT).show();
					return;
				}
				
				File updateFile = new File(et_vocation_note_picture.getText().toString());
				
				Map<String,File> files = new HashMap<String, File>();
				String fileName = class_id+"_"+Config.getCachedUserId(AskVocationActivity.this)+
						et_vocation_note_picture.getText().toString();
				//System.out.println(fileName);
				files.put(fileName, updateFile);
				
				//files.put("hh.JPG", new File(sdcardPath + "/test/hh.JPG"));
				final ProgressDialog pd = ProgressDialog.show(AskVocationActivity.this,"连接中","连接服务器中,请稍候"); 
				new UpdateMyNotePicture(files,new UpdateMyNotePicture.SuccessCallback() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(AskVocationActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
					}
				}, new UpdateMyNotePicture.FailCallback() {
					
					@Override
					public void onFail(int errorStatus) {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(AskVocationActivity.this, "网络异常，请确认后重试", Toast.LENGTH_SHORT).show();
					}
				});
				
				
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Uri uri = data.getData();
		uri.getPath();
//		System.out.println("hhh");
//		System.out.println(uri.getPath());
//		System.out.println(uri.getEncodedPath());
//		System.out.println(uri.toString());
		
		
		 String[] proj = {MediaStore.Images.Media.DATA};

		 

         //好像是Android多媒体数据库的封装接口，具体的看Android文档

		 Cursor cursor = AskVocationActivity.this.getContentResolver().query( uri,
				 proj, // Which columns to return
				 null, // WHERE clause; which rows to return (all rows)
				 null, // WHERE clause selection arguments (none)
				 null); // Order-by clause (ascending by name); 

         //按我个人理解 这个是获得用户选择的图片的索引值

         int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

         //将光标移至开头 ，这个很重要，不小心很容易引起越界
         cursor.moveToFirst();
         //最后根据索引值获取图片路径
         filePath = cursor.getString(column_index);
         Log.i("hhh", filePath);
         et_vocation_note_picture.setText(filePath);
         

	}

	private class myOnDateSetListener implements OnDateSetListener {

		private int tag;

		public myOnDateSetListener(int viewTag) {
			tag = viewTag;
		}

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			// TODO Auto-generated method stub
			mYear = year;
			mMonth = monthOfYear;
			mDay = dayOfMonth;
			switch (tag) {
			case R.id.et_vocation_start_time:
				et_vocation_start_time.setText(mYear + "-" + (mMonth + 1) + "-"
						+ (mDay));
				break;

			default:
				et_vocation_end_time.setText(mYear + "-" + (mMonth + 1) + "-"
						+ (mDay));
				break;
			}
		}

	}
}
