package com.hungry.iqclass.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hungry.iqclass.Config;

public class SendIsHereResult {
	public SendIsHereResult(int tag,int lateTime, String class_id,String class_name,String user_id,final SuccessCallback successCallback,final FailCallback failCallback){
		
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.ACTION_SEND_RESULT);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_CLASS_ID, class_id);
		postParams.put(Config.KEY_CLASS_NAME, class_name);
		postParams.put(Config.KEY_USERID, user_id);
		postParams.put("tag", tag+"");
		postParams.put("lateTime", lateTime+"");
		
		
		//Config.KEY_ACTION,Config.ACTION_SEND_RESULT,
		//"tag",tag+"",
		//"lateTime",lateTime+"",
		//Config.KEY_CLASS_ID,class_id,
		//Config.KEY_CLASS_NAME,class_name,
		//Config.KEY_USERID,user_id
		
		new NetConnection(Config.SERVER_URL, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject(result);
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback != null){
							successCallback.onSuccess();
						}
						break;
					default:
						if(failCallback != null){
							failCallback.onFail(Config.RESULT_STATUS_FAIL);
						}
						break;
					}
				}catch (JSONException e) {
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
		},postParams,null);
		
	}
	
	
	public static interface SuccessCallback{
		void onSuccess();
	}
	public static interface FailCallback{
		void onFail(int errorStatus);
	}
	
}
