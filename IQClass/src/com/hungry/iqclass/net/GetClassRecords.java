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

public class GetClassRecords {
	
	public GetClassRecords(String class_id,final SuccessCallback successCallback,final FailCallback failCallback){
		
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.ACTION_GET_CLASS_RECORDS);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_CLASS_ID, class_id);
		//Config.KEY_ACTION,Config.ACTION_GET_CLASS_RECORDS,
		//Config.KEY_CLASS_ID,class_id
		new NetConnection(Config.SERVER_URL,new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				
				try {
					JSONObject obj = new JSONObject(result);
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback != null){
							JSONArray class_records = obj.getJSONArray("class_records");
							List<HashMap<String, String>> records = new ArrayList<HashMap<String, String>>();
							for(int i=0;i<class_records.length();i++){
								HashMap<String, String> recordMap = new HashMap<String, String>();
								JSONObject recordObj = class_records.getJSONObject(i);
								recordMap.put("member_id", recordObj.getString("member_id"));
								recordMap.put("member_name", recordObj.getString("member_name"));
								recordMap.put("absent_times", recordObj.getString("absent_times"));
								recordMap.put("late_times", recordObj.getString("late_times"));
								recordMap.put("leave_early_times", recordObj.getString("leave_early_times"));
								recordMap.put("note_times", recordObj.getString("note_times"));
								records.add(recordMap);
							}
							successCallback.onSuccess(records);
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
		},postParams,
		null);
	}

	public static interface SuccessCallback{
		void onSuccess(List<HashMap<String, String>> classRecords);
	}
	public static interface FailCallback{
		void onFail(int errorCode);
	}
	
}
