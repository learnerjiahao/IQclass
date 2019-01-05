package com.hungry.iqclass.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.ic.Message;

public class GetMyGrade {
	
	public GetMyGrade(String userId,String userPassword,final String peroidTag,final SuccessCallback successCallback,final FailCallback failCallback){
		
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.ACTION_GET_MY_GRADE);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_USERID, userId);
		postParams.put(Config.KEY_PASSWORD, userPassword);
		
		
		//Config.KEY_ACTION,Config.ACTION_GET_MY_GRADE,
		//Config.KEY_USERID,userId,
		//Config.KEY_PASSWORD,userPassword
		
		new NetConnection(Config.SERVER_URL,new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
				try {
					JSONObject obj = new JSONObject(result);
					String whole_grade = obj.getString("whole_grade");
					String other_grade = obj.getString("other_grade");
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback != null){
							Log.i("hhh", peroidTag);
							JSONArray records = obj.getJSONArray("my_grade");
							List<HashMap<String, String>> myGrade = new ArrayList<HashMap<String, String>>();
							for(int i=0;i<records.length();i++){
								JSONObject gradeObj = records.getJSONObject(i);
								if(!gradeObj.getString("class_semester").equals(peroidTag)&&
										!peroidTag.equals("all")) continue;
								
								HashMap<String, String> gradeMap = new HashMap<String, String>();
								gradeMap.put("class_semester", gradeObj.getString("class_semester"));
								gradeMap.put("class_name", gradeObj.getString("class_name"));
								gradeMap.put("class_type", gradeObj.getString("class_type"));
								gradeMap.put("class_grade", gradeObj.getString("class_grade"));
								gradeMap.put("my_grade", gradeObj.getString("my_grade"));
								myGrade.add(gradeMap);
							}
							successCallback.onSuccess(myGrade,whole_grade,other_grade);
						}
						break;
					default:
						if(failCallback != null){
							failCallback.onFail(Config.RESULT_STATUS_FAIL);
						}
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(failCallback != null){
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
				}
				
			}
		}, new NetConnection.FailCallback() {
			
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				if(failCallback != null){
					failCallback.onFail(Config.RESULT_STATUS_FAIL);
				}
				
			}
		}, postParams,null);
	}

	public static interface SuccessCallback{
		void onSuccess(List<HashMap<String, String>> myGrade,String whole_grade,String other_grade);
	}
	public static interface FailCallback{
		void onFail(int errorCode);
	}
	
}
