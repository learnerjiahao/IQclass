 package com.hungry.iqclass.activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.FileResListViewAdapter;
import com.hungry.iqclass.R;
import com.hungry.iqclass.ic.Lesson;
import com.hungry.iqclass.net.GetClassLocation;
import com.hungry.iqclass.net.UpdateLocation;
import com.hungry.iqclass.toots.GetDistance;
import com.hungry.iqclass.toots.GetLocation;

public class IQClassActivity extends Activity {
	private ViewPager mPager;// 页卡内容
	private List<View> listViews; // Tab页面列表
	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3,t4;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private MyPagerAdapter adapter;
	private LayoutInflater mInflater;
	private Button bt_am_here;
	private Button bt_summit_see;
	private Button bt_ask_vocation;
	private Lesson lesson;
	private int[] weekInt = { 6, 0, 1, 2, 3, 4, 5 };
	private int currWeek = 0;
	private int class_week = 0;
	private Calendar c = null;
	private String endTimeStr ;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_iqclass);
		Log.i("Viewpage","--onCreate--");
		initImageView();
		initTextView();
		initPageView();
		
		
		
		Intent intent = getIntent();
		lesson = (Lesson) intent.getSerializableExtra(Config.KEY_LESSON);
		
		((TextView)findViewById(R.id.title_lesson)).setText(lesson.getClass_name());
		
		
		showResShare();
		
		
		class_week = lesson.GetClass_weekth().charAt(1)-49;
		//Log.i("hhh", "class_week----"+class_week+"-----currWeek---"+currWeek);
		
		if(lesson != null){
			showLessonInfo(lesson);
		}
		
		findViewById(R.id.btReturn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(IQClassActivity.this,HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				finish();
			}
		});
		
	}
	
	private void showLessonInfo(final Lesson lesson){
		final View view = listViews.get(0);
		((TextView)view.findViewById(R.id.tv_class_type)).setText(lesson.getClass_type());
		((TextView)view.findViewById(R.id.tv_class_credit)).setText(lesson.getClass_credit());
		((TextView)view.findViewById(R.id.tvLessonName)).setText(lesson.getClass_name());
		((TextView)view.findViewById(R.id.tvLessonNumber)).setText(lesson.getClass_id());
		((TextView)view.findViewById(R.id.tvLessonDay)).setText(lesson.GetClass_weekth()+"   "+lesson.GetClass_weeks());
		((TextView)view.findViewById(R.id.tvLessonTime)).setText(lesson.getClass_time());
		((TextView)view.findViewById(R.id.tvTeacherName)).setText(lesson.getClass_teacher_name());
		((TextView)view.findViewById(R.id.tv_class_location)).setText(lesson.getClass_location());
		
		bt_am_here = (Button)view.findViewById(R.id.bt_am_here);
		bt_summit_see = (Button)view.findViewById(R.id.bt_summit_see);
		bt_ask_vocation = (Button)view.findViewById(R.id.bt_ask_vocation);
		
		final String userType = Config.getCachedUserType(view.getContext());
		//final String userType = "teacher"; 
		if(userType.equals("teacher")){
			bt_am_here.setText("班级考勤");
			bt_summit_see.setText("情况查询");
			bt_ask_vocation.setVisibility(View.GONE);
		}
		
		bt_summit_see.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//若身份，为教师，点击则查看该班级所有成员的考勤情况，同时可以对个别同学的考勤记录进行修正
				if(userType.equals("teacher")){
					Intent i = new Intent(IQClassActivity.this,ClassRecordActivity.class);
					i.putExtra("class_id", lesson.getClass_id());
					i.putExtra("class_name", lesson.getClass_name());
					startActivity(i);
					finish();
					return;
				}
				
				
				//若身份为学生，点击则提交课堂作业，以照片的形式上传，以后再弄
				
				
			}
		});
		
		bt_am_here.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				c = Calendar.getInstance();
				
				currWeek = weekInt[Integer.valueOf(c.get(Calendar.DAY_OF_WEEK)) - 1];
				
				if(class_week != currWeek){
					Toast.makeText(IQClassActivity.this, "今天是星期"+(currWeek+1)+"，不是该节课的上课时间，无法进行操作!", Toast.LENGTH_SHORT).show();
					return;
				}
				int nowHour = c.get(Calendar.HOUR_OF_DAY);
				int nowMin = c.get(Calendar.MINUTE);
				
				String[] times = lesson.getClass_time().split("-");
				String[] startTime = times[0].split(":");
				String[] endTime = times[1].split(":");
				
				final int startHour = Integer.parseInt(startTime[0]);
				final int startMin = Integer.parseInt(startTime[1]);
				
				final int endHour = Integer.parseInt(endTime[0]);
				final int endMin = Integer.parseInt(endTime[1]);
				
				endTimeStr = endHour*60+endMin+"";
				
//				Log.i("hhh", "kkkk---"+nowHour+"----"+nowMin);
				
				//判断课是否开始，提前十分钟
//				if(nowHour*60+nowMin <= startHour*60+startMin-10){
//					Toast.makeText(IQClassActivity.this, "上课十分钟前才能进行操作，请稍后再试。", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				//判断课是否结束。
//				if(nowHour*60+nowMin >= endHour*60+endMin+5){
//					Toast.makeText(IQClassActivity.this, "该堂课已经下课，无法进行操作。", Toast.LENGTH_SHORT).show();
//					return;
//				}
				
				if(userType.equals("undergraduate")){
					
					new GetClassLocation(lesson.getClass_id(), new GetClassLocation.SuccessCallback() {
						
						@Override
						public void onSuccess(String longitude, String latitude) {
							// TODO Auto-generated method stub
							Log.i("hhh", longitude+"---"+latitude);
							GetLocation mLocation = new GetLocation(IQClassActivity.this);
							double myLongitude = mLocation.getLongitude();
							double myLatitude = mLocation.getLatitude();
							
							//System.out.println(myLongitude+"-----"+myLatitude);
							
							Log.i("hhh", Double.valueOf(myLongitude).toString()+"---"+Double.valueOf(myLatitude).toString());
							
							double diatance = GetDistance.getDistance(Double.valueOf(latitude), Double.valueOf(longitude)
									, myLatitude, myLongitude);
							
							if(diatance >= 800){
								Toast.makeText(IQClassActivity.this, "你和老师的距离为"+Double.valueOf(diatance)+"，无法签到", Toast.LENGTH_LONG).show();
								return;
							}
							Intent startI = new Intent(view.getContext(),CameraActivity.class);
							startI.putExtra(Config.KEY_CLASS_ID, lesson.getClass_id());
							startI.putExtra(Config.KEY_CLASS_NAME, lesson.getClass_name());
							startI.putExtra("startHour", startHour);
							startI.putExtra("startMin", startMin);
							startI.putExtra("endHour", endHour);
							startI.putExtra("endMin", endMin);
							startActivity(startI);
							//Log.i("hhh", Double.valueOf(diatance).toString());
							
						}
					}, new GetClassLocation.FailCallback() {
						
						@Override
						public void onFail(int errorStatus) {
							// TODO Auto-generated method stub
							
							switch (errorStatus) {
							case Config.RESULT_STATUS_FAIL:
								Toast.makeText(IQClassActivity.this,"无法链接服务器,检查您的网络", Toast.LENGTH_LONG).show();
								break;
							case Config.RESULT_STATUS_SEMESTER_ERR:
								Toast.makeText(IQClassActivity.this,"老师未开始考勤，请等待老师开启考勤后，再重试", Toast.LENGTH_LONG).show();
								break;
							}
							
						}
					});
					return;
				}
				
				GetLocation location = new GetLocation(view.getContext());
				final ProgressDialog pd = ProgressDialog.show(IQClassActivity.this,"连接中","连接服务器中,请稍候");
				new UpdateLocation(lesson.getClass_id(), location.getLongitude(), location.getLatitude(), endTimeStr,lesson.getClass_name(),
						new UpdateLocation.SuccessCallback() {
					
					@Override
					public void onSuccess() {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(IQClassActivity.this,"考勤成功开启，请记得在该堂课结束时，再次按下此按钮来结束考勤", Toast.LENGTH_LONG).show();
					}
				}, new UpdateLocation.FailCallback() {
					@Override
					public void onFail(int errorStatus) {
						// TODO Auto-generated method stub
						pd.dismiss();
						Toast.makeText(IQClassActivity.this,"无法链接服务器,检查您的网络", Toast.LENGTH_LONG).show();
					}
				});
			}
		});
		
		//请假
		
		bt_ask_vocation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i = new Intent(IQClassActivity.this,AskVocationActivity.class);
				i.putExtra(Config.KEY_CLASS_ID, lesson.getClass_id());
				i.putExtra(Config.KEY_CLASS_NAME, lesson.getClass_name());
				startActivity(i);
				finish();
			}
		});
		
	}
	
	// 显示资源分享的界面
	private void showResShare(){
		ListView resListView = (ListView) listViews.get(3).findViewById(R.id.lv_lesson_res);
		FileResListViewAdapter myAdapter = new FileResListViewAdapter(IQClassActivity.this, lesson.getClass_id());
		HashMap<String, String> fileInfo = new HashMap<String, String>();
		ArrayList<HashMap<String, String>> fileInfos = new ArrayList<HashMap<String,String>>();
		fileInfos.add(fileInfo);
		fileInfos.add(fileInfo);
		fileInfos.add(fileInfo);
		fileInfos.add(fileInfo);
		fileInfos.add(fileInfo);
		fileInfos.add(fileInfo);
		myAdapter.addList(fileInfos);
		resListView.setAdapter(myAdapter);
	}
	
	private void initPageView() {
		mInflater = getLayoutInflater();
		listViews = new ArrayList<View>();
		listViews.add(mInflater.inflate(R.layout.lessoninfo, null));
		listViews.add(mInflater.inflate(R.layout.lessonpublicmsg, null));
		listViews.add(mInflater.inflate(R.layout.lessonforum, null));
		listViews.add(mInflater.inflate(R.layout.lessonres, null));
		
		adapter = new MyPagerAdapter(listViews);
		mPager = (ViewPager) findViewById(R.id.vPager);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(0);
		mPager.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	private void initTextView() {
		t1 = (TextView) findViewById(R.id.tvLessonInfo);
		t2 = (TextView) findViewById(R.id.tvPublicMsg);
		t3 = (TextView) findViewById(R.id.tvlessonForum);
		t4 = (TextView) findViewById(R.id.tvLessonRes);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		t4.setOnClickListener(new MyOnClickListener(3));
	}

	

	private class MyOnClickListener implements View.OnClickListener {
		private int index = 0;

		public MyOnClickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			mPager.setCurrentItem(index);
		}
	}

	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		public void destroyItem(View arg0, int arg1, Object arg2) {
			((ViewPager) arg0).removeView(mListViews.get(arg1));
		}

		public void finishUpdate(View arg0) {
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public Object instantiateItem(View arg0, int arg1) {
			((ViewPager) arg0).addView(mListViews.get(arg1), 0);
			return mListViews.get(arg1);
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == (arg1);
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
		}

		@Override
		public Parcelable saveState() {
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
		}
	}

	private void initImageView() {
		cursor = (ImageView) findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.slide)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 4 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
        int three = one * 3;  //页卡1 -> 页卡4 偏移量
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}else if(currIndex == 3){
					animation = new TranslateAnimation(three, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
				}
				break;
			case 3:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, three, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
				}else if (currIndex == 2) {
					animation = new TranslateAnimation(one, three, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
}