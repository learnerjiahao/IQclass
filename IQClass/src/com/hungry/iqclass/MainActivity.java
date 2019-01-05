package com.hungry.iqclass;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.hungry.iqclass.activity.HomeActivity;
import com.hungry.iqclass.activity.LoginActivity;

public class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);  
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();  
        if(networkInfo == null || !networkInfo.isAvailable())  
        {  
        	startActivity(new Intent(this,HomeActivity.class));//当前无可用网络 ,可以直接查看课表 
       	 	finish();
          
        }  
        else   
        {  
        	 startActivity(new Intent(this,LoginActivity.class));  //用户联网,需登录成功才能访问主页面
             finish();
        }  
	}
}

