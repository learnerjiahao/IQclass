package com.hungry.iqclass.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;

import com.hungry.iqclass.R;
import com.hungry.iqclass.frament.ClassFragment;
import com.hungry.iqclass.frament.MessageFragment;
import com.hungry.iqclass.frament.MoreFragment;
import com.hungry.iqclass.frament.TabIndictorView;

public class HomeActivity extends FragmentActivity implements OnTabChangeListener{

	private static final String TAG_LESSONS = "IQ";
	private static final String TAG_MSG = "MSG";
	private static final String TAG_MORE = "MORE";
	
	
	private FragmentTabHost tabhost;
	private TabIndictorView classIndictor;
	private TabIndictorView messageIndictor;
	private TabIndictorView moreIndictor;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		
		//1.初始化tabHost
		tabhost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		tabhost.setup(this, getSupportFragmentManager(), 
					R.id.activity_home_container);
		
		//2.新建TabSpec
		TabSpec spec = tabhost.newTabSpec(TAG_LESSONS);
		spec.setIndicator("IQ");
		classIndictor = new TabIndictorView(this);
		spec.setIndicator(classIndictor);
		classIndictor.setTabTitle("IQ学堂");
		classIndictor.setTabIcon(R.drawable.tab_icon_chat_normal,R.drawable.tab_icon_chat_focus);
		//3.添加TabSpec
		tabhost.addTab(spec, ClassFragment.class, null);
		
		//2.新建TabSpec
		spec = tabhost.newTabSpec(TAG_MSG);
		spec.setIndicator("MSG");
		messageIndictor = new TabIndictorView(this);
		spec.setIndicator(messageIndictor);
		messageIndictor.setTabTitle("消息");
		messageIndictor.setTabIcon(R.drawable.tab_icon_discover_normal,R.drawable.tab_icon_discover_focus);
		//3.添加TabSpec
		tabhost.addTab(spec, MessageFragment.class, null);
		
		
		//2.新建TabSpec
	    spec = tabhost.newTabSpec(TAG_MORE);
		spec.setIndicator("MORE");
		moreIndictor = new TabIndictorView(this);
		spec.setIndicator(moreIndictor);
		moreIndictor.setTabTitle("更多");
		moreIndictor.setTabIcon(R.drawable.tab_icon_me_normal,R.drawable.tab_icon_me_focus);
		//3.添加TabSpec
		tabhost.addTab(spec, MoreFragment.class, null);
		
		//去掉分割线
		tabhost.getTabWidget().setDividerDrawable(android.R.color.white);
		tabhost.setCurrentTabByTag(TAG_LESSONS);
		classIndictor.setTabSelected(true);
		
		//点击切换事件,监听tabhost的选中事件;
		tabhost.setOnTabChangedListener(this);
		
		
	}

	@Override
	public void onTabChanged(String tag) {
		// TODO Auto-generated method stub
		
		classIndictor.setTabSelected(false);
		messageIndictor.setTabSelected(false);
		moreIndictor.setTabSelected(false);
		
		if(tag.equals(TAG_LESSONS)){
			classIndictor.setTabSelected(true);
		}else if(tag.equals(TAG_MORE)){
			moreIndictor.setTabSelected(true);
		}else{
			messageIndictor.setTabSelected(true);
		}
	}


}
