package com.hungry.iqclass.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.hungry.iqclass.Config;
import com.hungry.iqclass.ic.Message;

public class GetPersonalRecord {
	
	public GetPersonalRecord(String userId,String peroidTag,final SuccessCallback successCallback,final FailCallback failCallback){
		
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.ACTION_GET_PERSONAL_RECORD);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_USERID, userId);
		postParams.put("peroidTag", peroidTag);
		
		//Config.KEY_ACTION,Config.ACTION_GET_PERSONAL_RECORD,
		//Config.KEY_USERID,userId,
		//"peroidTag",peroidTag
		
		new NetConnection(Config.SERVER_URL, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
				try {
					JSONObject obj = new JSONObject(result);
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback != null){
							JSONArray records = obj.getJSONArray("records");
							List<HashMap<String, String>> myRecords = new ArrayList<HashMap<String, String>>();
							for(int i=0;i<records.length();i++){
								HashMap<String, String> recordMap = new HashMap<String, String>();
								JSONObject recordObj = records.getJSONObject(i);
								recordMap.put("record_date", recordObj.getString("record_date"));
								recordMap.put("record_describe", recordObj.getString("record_describe"));
								recordMap.put("class_name", recordObj.getString("class_name"));
								myRecords.add(recordMap);
							}
							successCallback.onSuccess(myRecords);
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
		void onSuccess(List<HashMap<String, String>> myRecords);
	}
	public static interface FailCallback{
		void onFail(int errorCode);
	}
	
}
