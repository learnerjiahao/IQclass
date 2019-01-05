package com.hungry.iqclass.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.hungry.iqclass.Config;

public class GetOtherClasses {

	public GetOtherClasses(String semester,final Context context,final SuccessCallback successCallback,final FailCallback failCallback){
		
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.Action_GET_OTHER_CLASSES);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_SEMESTER, semester);
		// Config.KEY_ACTION,Config.Action_GET_OTHER_CLASSES,
		//Config.KEY_SEMESTER,semester
		
		new NetConnection(Config.SERVER_URL, new NetConnection.SuccessCallback() {
			
			@Override
			public void onSuccess(String result) {
				// TODO Auto-generated method stub
				try {
					JSONObject obj = new JSONObject(result);
					switch (obj.getInt(Config.KEY_STATUS)) {
					case Config.RESULT_STATUS_SUCCESS:
						if(successCallback != null){
							
							SQLiteDatabase mySQLiteDatabase = context.openOrCreateDatabase("am_here_datebase",
											Context.MODE_PRIVATE, null);
							Cursor selectCursor = null;
							JSONArray otherLessons = obj.getJSONArray("other_lessons");
							List<HashMap<String, String>> myOtherLessons = new ArrayList<HashMap<String, String>>();
							for(int i=0;i<otherLessons.length();i++){
								HashMap<String, String> lessonMap = new HashMap<String, String>();
								JSONObject lessonObj = otherLessons.getJSONObject(i);
								selectCursor = mySQLiteDatabase
										.query("my_class",null,"class_id="+ lessonObj.getString(Config.KEY_CLASS_ID),
												null, null, null, null,null);
								if(selectCursor.getCount()>=1) continue;
								lessonMap.put("other_class_teacher_name", lessonObj.getString("class_teacher_name"));
								lessonMap.put("other_class_name", lessonObj.getString("class_name"));
								lessonMap.put("other_class_time_location", lessonObj.getString("class_time_location"));
								myOtherLessons.add(lessonMap);
							}
							selectCursor.close();
							mySQLiteDatabase.close();
							
							successCallback.onSuccess(myOtherLessons);
						}
						break;
					case Config.RESULT_STATUS_ERR:
						if(failCallback != null){
							failCallback.onFail(Config.RESULT_STATUS_ERR);
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
		},postParams,null);
	}
	
	
	public static interface SuccessCallback{
		void onSuccess(List<HashMap<String, String>> myRecords);
	}
	public static interface FailCallback{
		void onFail(int errTag);
	}
	
}
