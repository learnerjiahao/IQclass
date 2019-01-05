package com.hungry.iqclass.net;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.hungry.iqclass.Config;

public class GetClassLocation {

	public GetClassLocation(String class_id,
			final SuccessCallback successCallback,
			final FailCallback failCallback) {
		Map<String, String> postParams = new HashMap<String, String>();
		postParams.put(Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION);  //Config.KEY_ACTION, Config.ACTION_GET_CLASS_LOCATION
		postParams.put(Config.KEY_CLASS_ID, class_id);
		new NetConnection(Config.SERVER_URL,
				new NetConnection.SuccessCallback() {

					@Override
					public void onSuccess(String result) {
						// TODO Auto-generated method stub
						try {
							JSONObject obj = new JSONObject(result);
							switch (obj.getInt(Config.KEY_STATUS)) {
							case Config.RESULT_STATUS_SUCCESS:

								if (successCallback != null) {
									successCallback.onSuccess(
											obj.getString("class_longitude"),
											obj.getString("class_latitude"));
								}
								break;
							case Config.RESULT_STATUS_SEMESTER_ERR:

								if (failCallback != null) {
									failCallback
											.onFail(Config.RESULT_STATUS_SEMESTER_ERR);
								}
								break;
							default:
								if (failCallback != null) {
									failCallback
											.onFail(Config.RESULT_STATUS_FAIL);
								}
								break;
							}

						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
							if (failCallback != null) {
								failCallback.onFail(Config.RESULT_STATUS_FAIL);
							}
						}
					}
				}, new NetConnection.FailCallback() {

					@Override
					public void onFail() {
						// TODO Auto-generated method stub
						if (failCallback != null) {
							failCallback.onFail(Config.RESULT_STATUS_FAIL);
						}

					}
				}, postParams,
				null);
	}

	public static interface SuccessCallback {
		void onSuccess(String longitude, String latitude);
	}

	public static interface FailCallback {
		void onFail(int errorStatus);
	}

}
