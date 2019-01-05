package com.hungry.iqclass.frament;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hungry.iqclass.R;

public class TabIndictorView extends RelativeLayout {

	private ImageView ivTabIcon;
	private TextView tvTabHint;
	private TextView tvTabUnRead;
	
	private int normalIconId;
	private int focusIconId;
	
	public TabIndictorView(Context context) {
		this(context,null);
		// TODO Auto-generated constructor stub
	}

	public TabIndictorView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		//将布局文件和代码绑定
		
		View.inflate(context, R.layout.tab_indictor, this);
		//System.out.println("yes");
		
		ivTabIcon = (ImageView) findViewById(R.id.tab_indicator_icon);
		tvTabHint = (TextView) findViewById(R.id.tab_indicator_hint);
		tvTabUnRead = (TextView) findViewById(R.id.tab_indicator_unread);
		
		setTabUnreadCount(0);
	}
	//设置标题
	public void setTabTitle(String title){
		tvTabHint.setText(title);
	}
	//设置TAB标题ID
	public void setTabTitle(int titleId){
		tvTabHint.setText(titleId);
	}
	//设置不同状态的TAB的不同按钮ID
	public void setTabIcon(int normalIconId,int focusIconId){
		this.focusIconId = focusIconId;
		this.normalIconId = normalIconId;
		
		ivTabIcon.setImageResource(normalIconId);
	}
	//设置消息数及其TAB显示效果
	public void setTabUnreadCount(int unreadCount){
		if(unreadCount <= 0){
			tvTabUnRead.setVisibility(View.GONE);
		}else{
			if(unreadCount <= 99){
			tvTabUnRead.setText(unreadCount + "");
				}else{
					tvTabUnRead.setText("99+");
				}
			tvTabUnRead.setVisibility(View.VISIBLE);
		}
	}
	//设置TAB选中与否的不同效果
	public void setTabSelected(boolean selected){
		setSelected(selected);
		if(selected){
			ivTabIcon.setImageResource(focusIconId);
		}else{
			ivTabIcon.setImageResource(normalIconId);
		}
	}

}
