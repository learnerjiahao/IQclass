package com.hungry.iqclass.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

import android.os.AsyncTask;
import android.util.Log;

public class NetConnection {

	public NetConnection(final String url,
			final SuccessCallback successCallback,
			final FailCallback failCallback,
			final Map<String, String> postParams, final Map<String, File> files) {

		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {
				
				try {
					String BOUNDARY = java.util.UUID.randomUUID().toString();
					String PREFIX = "--", LINEND = "\r\n";
					String MULTIPART_FROM_DATA = "multipart/form-data";
					String CHARSET = "UTF-8";
					URL uri = new URL(url);
					HttpURLConnection conn = (HttpURLConnection) uri
							.openConnection();
					conn.setReadTimeout(10 * 1000);
					conn.setDoInput(true);// 允许输入
					conn.setDoOutput(true);// 允许输出
					conn.setUseCaches(false);
					conn.setRequestMethod("POST"); // Post方式
					conn.setRequestProperty("connection", "keep-alive");
					conn.setRequestProperty("Charsert", "UTF-8");
					conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
							+ ";boundary=" + BOUNDARY);
					// 首先组拼文本类型的参数
					StringBuilder sb = new StringBuilder();
					for (Map.Entry<String, String> entry : postParams
							.entrySet()) {
						sb.append(PREFIX);
						sb.append(BOUNDARY);
						sb.append(LINEND);
						sb.append("Content-Disposition: form-data; name=\""
								+ entry.getKey() + "\"" + LINEND);
						sb.append("Content-Type: text/plain; charset="
								+ CHARSET + LINEND);
						sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
						sb.append(LINEND);
						sb.append(entry.getValue());
						sb.append(LINEND);
					}
					DataOutputStream outStream = new DataOutputStream(
							conn.getOutputStream());
					outStream.write(sb.toString().getBytes());
					Log.i("hhh", sb.toString());
					
					// 发送文件数据
					if (files != null)
						for (Map.Entry<String, File> file : files.entrySet()) {
							StringBuilder sb1 = new StringBuilder();
							sb1.append(PREFIX);
							sb1.append(BOUNDARY);
							sb1.append(LINEND);
							sb1.append("Content-Disposition: form-data; name=\"file\"; filename=\""
									+ file.getKey() + "\"" + LINEND);
							sb1.append("Content-Type: multipart/form-data; charset="
									+ CHARSET + LINEND);
							sb1.append(LINEND);
							outStream.write(sb1.toString().getBytes());
							InputStream is = new FileInputStream(
									file.getValue());
							byte[] buffer = new byte[1024];
							int len = 0;
							while ((len = is.read(buffer)) != -1) {
								outStream.write(buffer, 0, len);
							}
							
							Log.i("hhh", sb1.toString());
							is.close();
							outStream.write(LINEND.getBytes());
						}
					// 请求结束标志
					byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND)
							.getBytes();
					outStream.write(end_data);
					outStream.flush();
					
					
					//获得响应
					
					InputStream in = conn.getInputStream();
					InputStreamReader isReader = new InputStreamReader(in);
					BufferedReader bufReader = new BufferedReader(isReader);
					String line = null;
					String result = "";
					while ((line = bufReader.readLine()) != null)
						result += line;
					outStream.close();
					conn.disconnect();
					Log.i("hhh", "result:"+result);
					return result;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result) {
				// TODO Auto-generated method stub

				if (result != null) {
					if (successCallback != null) {
						successCallback.onSuccess(result);
					}
				} else {
					if (failCallback != null) {
						failCallback.onFail();
					}
				}
				super.onPostExecute(result);
			}
		}.execute();
	}

	public static interface SuccessCallback {
		void onSuccess(String result);
	}

	public static interface FailCallback {
		void onFail();
	}

}
