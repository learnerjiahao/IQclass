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

public class GetMessage {

	
	public GetMessage(String phoneIMEI,String phoneNumber,String studyNumber,
			final SuccessCallback successCallback,final FailCallback failCallback){
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.ACTION_GET_MESSAGE);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_PHONE_IMEI, phoneIMEI);
		postParams.put(Config.KEY_PHONE_NUMBER, phoneNumber);
		postParams.put(Config.KEY_STUDY_NUMBER, studyNumber);
		
		
		//Config.KEY_ACTION, Config.ACTION_GET_MESSAGE,
		//Config.KEY_PHONE_IMEI, phoneIMEI, 
		//Config.KEY_PHONE_NUMBER,phoneNumber,
		//Config.KEY_STUDY_NUMBER,studyNumber
		new NetConnection(Config.SERVER_URL, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject(result);
					
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback != null){
							List<Message> msgs = new ArrayList<Message>();
							JSONArray msgJsonArray = obj.getJSONArray(Config.KEY_MESSAGE);
							JSONObject msgObj = null;
							for (int i = 0; i < msgJsonArray.length(); i++) {
								msgObj = msgJsonArray.getJSONObject(i);
								msgs.add(new Message(msgObj.getString(Config.KEY_LESSON_NAME)
										,msgObj.getString(Config.KEY_MESSAGE)));
							}
							
							successCallback.onSuccess(msgs);
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
		void onSuccess(List<Message> messages);
	}
	public static interface FailCallback{
		void onFail(int errorCode);
	}
}

