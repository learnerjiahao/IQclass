package com.hungry.iqclass.frament;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.IqClassListViewAdapter;
import com.hungry.iqclass.R;
import com.hungry.iqclass.activity.IQClassActivity;
import com.hungry.iqclass.activity.ImportClassInfoActivity;
import com.hungry.iqclass.ic.Lesson;

public class ClassFragment extends Fragment {

	private ImageView cursor;// 动画图片
	private TextView t1, t2, t3, t4, t5, t6, t7;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private Calendar c = Calendar.getInstance();

	private int currWeekth = 0;
	private int choose_weekth = 0;
	private int currWeek = 0;

	private ViewPager mPager;// 页卡内容
	private MyPagerAdapter pagerAdapter;
	private MyOnPageChangeListener myPageLin;
	private View view;
	private LayoutInflater inflater;

	private String semester = null;

	private int[] weekInt = { 6, 0, 1, 2, 3, 4, 5 };
	private Spinner sp_week;
	private ArrayAdapter weeks_spinner_adapter = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// TODO Auto-generated method stub

		currWeek = Integer.valueOf(c.get(Calendar.DAY_OF_WEEK)) - 1;
		System.out.println("currWeek----->" + currWeek);

		this.inflater = inflater;
		view = inflater.inflate(R.layout.frament_myclass, container, false);

		currWeekth = Config.getCachedCurrentWeekth(view.getContext());
		// Log.i("hhh", "currWeekth-------"+currWeekth);

		long time = Config.getCachedNowTime(view.getContext());
		long now_time = (new Date()).getTime();
		if (currWeekth != 0 && time != 0) {
			long abs_time = now_time - time;
			long day = abs_time / (1000 * 60 * 60 * 24);
			// Log.i("hhh",
			// "day--"+day+"---weekInt[currWeek]--"+weekInt[currWeek]);
			currWeekth = currWeekth + (int) ((day + weekInt[currWeek]) / 7);
		}

		Config.cacheCurrentWeekth(view.getContext(), currWeekth, now_time);

		// Log.i("hhh", "currWeekth--"+currWeekth+"---week--"+currWeek);

		choose_weekth = currWeekth;

		// currIndex = weekInt[currWeek] - 1;

		view.findViewById(R.id.ivAddLesson).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						startActivity((new Intent(view.getContext(),
								ImportClassInfoActivity.class))
								.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
					}
				});

		sp_week = (Spinner) view.findViewById(R.id.sp_weeks);
		MyOnItemSelectedListener mOnItemSelectedListener = new MyOnItemSelectedListener();
		weeks_spinner_adapter = ArrayAdapter.createFromResource(
				view.getContext(), R.array.weeks,
				android.R.layout.simple_spinner_item);
		weeks_spinner_adapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		sp_week.setVisibility(View.VISIBLE);
		sp_week.setOnItemSelectedListener(mOnItemSelectedListener);
		sp_week.setAdapter(weeks_spinner_adapter);
		sp_week.setSelection(currWeekth - 1);

		initImageView();
		initTextView();
		// initPageView();
		return view;
	}

	// @Override
	// public void onPause() {
	// // TODO Auto-generated method stub
	// super.onPause();
	// currIndex = weekInt[currWeek]-1;
	// }

	private class MyOnItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			//Log.i("hhh", "position---" + position);
			choose_weekth = position;
			//currIndex = weekInt[currWeek] - 1;
			initPageView();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

		}

	}

	private void initPageView() {

		semester = Config.getCachedSemesterChoice(getActivity());
		List<View> listViews = new ArrayList<View>();
		List<ListView> classListViews = new ArrayList<ListView>();
		List<IqClassListViewAdapter> classAdapterListViews = new ArrayList<IqClassListViewAdapter>();
		Map<String, ArrayList<Lesson>> mapListLessons = new HashMap<String, ArrayList<Lesson>>();
		for (int i = 0; i < 7; i++) {
			listViews.add(inflater.inflate(R.layout.myclasslistview, null));
			classListViews.add((ListView) ((listViews.get(i))
					.findViewById(R.id.classListView)));
			classAdapterListViews.add(new IqClassListViewAdapter(listViews.get(
					i).getContext()));
			mapListLessons.put("" + i, new ArrayList<Lesson>());
		}

		pagerAdapter = new MyPagerAdapter(listViews);
		mPager = (ViewPager) view.findViewById(R.id.viewpager);
		mPager.setAdapter(pagerAdapter);

		myPageLin = new MyOnPageChangeListener();
//		currIndex = weekInt[currWeek] - 1;
//		Log.i("hhh", "currIndex---" + currIndex);
//		myPageLin.onPageSelected(weekInt[currWeek]);
//		mPager.setCurrentItem(weekInt[currWeek]);// 默认显示页
		mPager.setOnPageChangeListener(myPageLin);

		SQLiteDatabase mySQLiteDatabase = inflater.getContext()
				.openOrCreateDatabase("am_here_datebase", Context.MODE_PRIVATE,
						null);
		mySQLiteDatabase
				.execSQL("create table if not exists my_class(class_id,class_name,class_teacher_name"
						+ ",class_credit,class_time_location,class_period,class_type,class_semester)");
		Cursor cursor = mySQLiteDatabase
				.query("my_class", null, "class_semester=?",
						new String[] { semester }, null, null, null);
		if (cursor.getCount() < 1) {
			Toast.makeText(inflater.getContext(), "您还未导入当前学期的课程信息，请添加!",
					Toast.LENGTH_SHORT).show();
			return;
		}
		while (cursor.moveToNext()) {
			String class_id = cursor.getString(cursor
					.getColumnIndex(Config.KEY_CLASS_ID));
			String class_name = cursor.getString(cursor
					.getColumnIndex(Config.KEY_CLASS_NAME));
			String class_teacher_name = cursor.getString(cursor
					.getColumnIndex(Config.KEY_CLASS_TEACHER_NAME));
			String class_type = cursor.getString(cursor
					.getColumnIndex(Config.KEY_CLASS_TYPE));
			String class_period = cursor.getString(cursor
					.getColumnIndex(Config.KEY_CLASS_PERIOD));
			String class_credit = cursor.getString(cursor
					.getColumnIndex(Config.KEY_CLASS_CREDIT));
			String class_time_location = cursor.getString(cursor
					.getColumnIndex(Config.KEY_CLASS_TIME_LOCATION));
			// System.out.println(Config.parseClassTimeLocation(class_time_location));
			ArrayList<ArrayList<String>> arrayList = Config
					.parseClassTimeLocation(class_time_location);
			if (arrayList == null)
				continue;
			ArrayList<String> arrayListLesson = null;
			for (int k = 0; k < arrayList.size(); k++) {
				arrayListLesson = arrayList.get(k);
				for (int i = 0; i < arrayListLesson.size(); i++) {
					String className = null;
					if (k > 0) {
						className = class_name + "(实验)";
					} else {
						className = class_name;
					}
					String weekth = arrayListLesson.get(i).substring(0,
							arrayListLesson.get(i).indexOf(","));
					String class_segmenceth = arrayListLesson.get(i).substring(
							arrayListLesson.get(i).indexOf("第"),
							arrayListLesson.get(i).indexOf("节") + 1);
					String weeks = arrayListLesson.get(i).substring(
							arrayListLesson.get(i).indexOf("节,") + 2,
							arrayListLesson.get(i).indexOf("周 ") + 1);
					String class_location = arrayListLesson.get(i).substring(
							arrayListLesson.get(i).indexOf(" ") + 1,
							arrayListLesson.get(i).length());
					//Log.i("hhh", "weekth---"+weekth);
					Lesson lesson = new Lesson(class_id, className,
							class_teacher_name, class_period, class_credit,
							class_type, Config.getClassTime(class_segmenceth),
							class_location, class_segmenceth, weeks ,weekth);
					if (isShow(choose_weekth + 1, weeks)) {
						int j = weekth.charAt(1) - 49;
						mapListLessons.get("" + j).add(lesson);
					}

				}
			}

		}

		for (int i = 0; i < 7; i++) {
			classAdapterListViews.get(i).addList(mapListLessons.get("" + i));
			classListViews.get(i).setAdapter(classAdapterListViews.get(i));
			(classListViews.get(i))
					.setOnItemClickListener(new myOnItemClickListener());
		}

	}

	private boolean isShow(int currWeek, String weeks) {

		// Log.i("hhh", weeks);
		if (weeks.contains(",")) {
			String[] strs1 = weeks.split(",");
			for (int i = 0; i < strs1.length; i++) {
				if (strs1[i].contains("-")) {
					String strs2[] = strs1[i].split("-");
					// Log.i("hhh", strs2[0] + "------" + strs2[1]);
					int a = new Integer(strs2[0]);
					int b = 0;
					if (strs2[1].contains("周")) {
						b = new Integer(strs2[1].substring(0,
								strs2[1].length() - 1));
					} else {
						b = new Integer(strs2[1]);
					}

					if (a <= currWeek && b >= currWeek) {
						return true;
					}

				} else {
					if (strs1[i].contains("" + currWeek)) {
						return true;
					}
				}
			}
		} else if (weeks.contains("-")) {
			String strs2[] = weeks.split("-");
			int a = new Integer(strs2[0]);
			int b = 0;
			if (strs2[1].contains("周")) {
				b = new Integer(strs2[1].substring(0, strs2[1].length() - 1));
			} else {
				b = new Integer(strs2[1]);
			}
			if (a <= currWeek && b >= currWeek) {
				return true;
			}
		} else {
			if (weeks.contains("" + currWeek)) {
				return true;
			}
		}

		return false;
	}

	private void initTextView() {
		t1 = (TextView) view.findViewById(R.id.tvMonday);
		t2 = (TextView) view.findViewById(R.id.tvTuesday);
		t3 = (TextView) view.findViewById(R.id.tvWednesday);
		t4 = (TextView) view.findViewById(R.id.tvThursday);
		t5 = (TextView) view.findViewById(R.id.tvFriday);
		t6 = (TextView) view.findViewById(R.id.tvSaturday);
		t7 = (TextView) view.findViewById(R.id.tvSunday);
		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		t4.setOnClickListener(new MyOnClickListener(3));
		t5.setOnClickListener(new MyOnClickListener(4));
		t6.setOnClickListener(new MyOnClickListener(5));
		t7.setOnClickListener(new MyOnClickListener(6));
	}

	private void initImageView() {
		cursor = (ImageView) view.findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.slide)
				.getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) view.getContext()).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 7 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {

		int one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
		int two = one * 2;// 页卡1 -> 页卡3 偏移量
		int three = one * 3; // 页卡1 -> 页卡4 偏移量
		int four = one * 4;
		int five = one * 5;
		int six = one * 6;
		int zero = 0;

		@Override
		public void onPageSelected(int arg0) {
			
			Animation animation = null;
			if (currIndex == arg0) {
				animation = new TranslateAnimation(zero, 0, 0, 0);
			}

			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, 0, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, 0, 0, 0);
				} else if (currIndex == 5) {
					animation = new TranslateAnimation(five, 0, 0, 0);
				} else if (currIndex == 6) {
					animation = new TranslateAnimation(six, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, one, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, one, 0, 0);
				} else if (currIndex == 5) {
					animation = new TranslateAnimation(five, one, 0, 0);
				} else if (currIndex == 6) {
					animation = new TranslateAnimation(six, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, two, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, two, 0, 0);
				} else if (currIndex == 5) {
					animation = new TranslateAnimation(five, two, 0, 0);
				} else if (currIndex == 6) {
					animation = new TranslateAnimation(six, two, 0, 0);
				}
				break;
			case 3:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, three, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, three, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, three, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, three, 0, 0);
				} else if (currIndex == 5) {
					animation = new TranslateAnimation(five, three, 0, 0);
				} else if (currIndex == 6) {
					animation = new TranslateAnimation(six, three, 0, 0);
				}
				break;
			case 4:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, four, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, four, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, four, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, four, 0, 0);
				} else if (currIndex == 5) {
					animation = new TranslateAnimation(five, four, 0, 0);
				} else if (currIndex == 6) {
					animation = new TranslateAnimation(six, four, 0, 0);
				}
				break;
			case 5:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, five, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, five, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, five, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, five, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, five, 0, 0);
				} else if (currIndex == 6) {
					animation = new TranslateAnimation(six, five, 0, 0);
				}
				break;
			case 6:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, six, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, six, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, six, 0, 0);
				} else if (currIndex == 3) {
					animation = new TranslateAnimation(three, six, 0, 0);
				} else if (currIndex == 4) {
					animation = new TranslateAnimation(four, six, 0, 0);
				} else if (currIndex == 5) {
					animation = new TranslateAnimation(five, six, 0, 0);
				}
				break;
			}

			currIndex = arg0;

			Log.i("hhh", "arg0----" + arg0);
			Log.i("hhh", "currIndex==arg0----" + currIndex);
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

	public class myOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			IqClassListViewAdapter adapter = (IqClassListViewAdapter) arg0
					.getAdapter();
			Lesson lesson = (Lesson) adapter.getItem(arg2);
			Intent i = new Intent(arg1.getContext(), IQClassActivity.class);
			Bundle bundle = new Bundle();
			bundle.putSerializable(Config.KEY_LESSON, lesson); // 传递对象
			i.putExtras(bundle);
			startActivity(i);
		}

	}

	public class MyPagerAdapter extends PagerAdapter {
		public List<View> mListViews;

		public MyPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
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
}
