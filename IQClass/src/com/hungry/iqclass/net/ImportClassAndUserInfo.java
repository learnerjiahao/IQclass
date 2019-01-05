package com.hungry.iqclass.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.hungry.iqclass.Config;

public class ImportClassAndUserInfo {

	public ImportClassAndUserInfo(String userId,String password,String semester,String userType,final SuccessCallback successCallback,final FailCallback failCallback){
		
		
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.ACTION_IMPORT_CLASS_INFO);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_USERID, userId);
		postParams.put(Config.KEY_PASSWORD, password);
		postParams.put(Config.KEY_SEMESTER, semester);
		postParams.put(Config.KEY_USER_TYPE, userType);
		
		
		//Config.KEY_ACTION, Config.ACTION_IMPORT_CLASS_INFO,
		//Config.KEY_USERID,userId,
		//Config.KEY_PASSWORD,password,
		//Config.KEY_SEMESTER,semester,
		//Config.KEY_USER_TYPE,userType
		
		new NetConnection(Config.SERVER_URL,new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject(result);
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback != null){
							JSONObject objUserInfo = obj.getJSONObject("info_of_user");
							//String info_of_classes = obj.getString(Config.KEY_INFO_OF_CLASSES);
							JSONArray objArrayClasses = obj.getJSONArray(Config.KEY_INFO_OF_CLASSES);
							//System.out.println(objClasses);
							successCallback.onSuccess(objArrayClasses,objUserInfo);
						}
						break;
					case Config.RESULT_STATUS_SEMESTER_ERR:
						if(failCallback != null){
							System.out.println("--0i");
							failCallback.onFail(Config.RESULT_STATUS_SEMESTER_ERR);
						}
					case Config.RESULT_STATUS_ERR:
						if(failCallback != null){
							System.out.println("--0i0000");
							failCallback.onFail(Config.RESULT_STATUS_ERR);
						}
					default:
						if(failCallback != null){
							System.out.println("-0000-0i");
							failCallback.onFail(Config.RESULT_STATUS_FAIL);
						}
						break;
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					if(failCallback != null){
						System.out.println("null");
						failCallback.onFail(Config.RESULT_STATUS_FAIL);
					}
				}
			}
		}, new NetConnection.FailCallback() {
			
			@Override
			public void onFail() {
				// TODO Auto-generated method stub
				if(failCallback != null){
					System.out.println("-rffr-0i");
					failCallback.onFail(Config.RESULT_STATUS_FAIL);
				}
			}
		},postParams,null);
	}

	public static interface SuccessCallback{
		void onSuccess(JSONArray objArrayClasses,JSONObject objUserInfo);
	}
	public static interface FailCallback{
		void onFail(int errorStatus);
	}
}
