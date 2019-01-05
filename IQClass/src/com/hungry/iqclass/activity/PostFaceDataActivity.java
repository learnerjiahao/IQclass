package com.hungry.iqclass.activity;

import java.io.ByteArrayOutputStream;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.hungry.iqclass.Config;
import com.hungry.iqclass.R;

public class PostFaceDataActivity extends Activity {
	/** Called when the activity is first created. */
	private Button btn_getPic;
	private Button btn_identify;
	private Button btn_gen_data;
	private ImageView view;
	private String fileName;
	private Bitmap img;
	private int faceCount;
	private String face_id;
	private HttpRequests httpRequests = new HttpRequests("c86213470260c2e3031ac0ea2e596028",
			"LhUOkbR_DBu3RBhg_IF08N_Xv-oWF5d5", true, true);
	private String userId = null;
	private String password = null;
	
	private boolean tag_is_two = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_post_face_data);
		btn_getPic = (Button) findViewById(R.id.btn_getPic);
		btn_identify = (Button) findViewById(R.id.btn_identify);
		btn_gen_data = (Button) findViewById(R.id.btn_gen_data);
		view = (ImageView) findViewById(R.id.iv_photo);
		userId = Config.getCachedUserId(this);
		password = Config.getCachedPassword(this);
		
		btn_getPic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				startActivityForResult(intent, 1);
				tag_is_two = false;
			}
		});
		
		findViewById(R.id.btn_con_return).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity((new Intent(PostFaceDataActivity.this,HomeActivity.class)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
				finish();
			}
		});
		
		//首先判断照片中是否存在人脸，不存在则提示重新拍照，存在则提示人脸识别成功，请点击生成数据按钮
		
		btn_identify.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(img == null) {
					Toast.makeText(PostFaceDataActivity.this, "请先拍照", Toast.LENGTH_SHORT).show();
					return;
				}
				if(tag_is_two){
					Toast.makeText(PostFaceDataActivity.this, "该照片重复，请重新拍照", Toast.LENGTH_SHORT).show();
					return;
				}
				//cacheBitmap(bitmap);
				//File file = new File(fileName);
				
				//System.out.println(fileName+"--->"+file.isFile()+file.canRead());
				//final ProgressDialog pd = ProgressDialog.show(PostFaceDataActivity.this,"连接中","连接服务器中,请稍候");
				FaceppDetect faceppDetect = new FaceppDetect();
				faceppDetect.setDetectCallback(new DetectCallback() {
					
					public void detectResult(final JSONObject rst) {
						Log.v("hhh", rst.toString());
						System.out.println(rst.toString());
						faceCount = 0;
						try {
							faceCount = rst.getJSONArray("face").length();
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							//pd.dismiss();
						}
						
						
						
						
						//use the red paint
						Paint paint = new Paint();
						paint.setColor(Color.RED);
						paint.setStrokeWidth(Math.max(img.getWidth(), img.getHeight()) / 100f);

						//create a new canvas
						Bitmap bitmap = Bitmap.createBitmap(img.getWidth(), img.getHeight(), img.getConfig());
						Canvas canvas = new Canvas(bitmap);
						canvas.drawBitmap(img, new Matrix(), null);
						
						
						try {
							//find out all faces
							for (int i = 0; i < faceCount; ++i) {
								float x, y, w, h;
								//get the center point
								x = (float)rst.getJSONArray("face").getJSONObject(i)
										.getJSONObject("position").getJSONObject("center").getDouble("x");
								y = (float)rst.getJSONArray("face").getJSONObject(i)
										.getJSONObject("position").getJSONObject("center").getDouble("y");

								//get face size
								w = (float)rst.getJSONArray("face").getJSONObject(i)
										.getJSONObject("position").getDouble("width");
								h = (float)rst.getJSONArray("face").getJSONObject(i)
										.getJSONObject("position").getDouble("height");
								
								//change percent value to the real size
								x = x / 100 * img.getWidth();
								w = w / 100 * img.getWidth() * 0.7f;
								y = y / 100 * img.getHeight();
								h = h / 100 * img.getHeight() * 0.7f;

								//draw the box to mark it out
								canvas.drawLine(x - w, y - h, x - w, y + h, paint);
								canvas.drawLine(x - w, y - h, x + w, y - h, paint);
								canvas.drawLine(x + w, y + h, x - w, y + h, paint);
								canvas.drawLine(x + w, y + h, x + w, y - h, paint);
							}
							
							img = bitmap;
							PostFaceDataActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									//pd.dismiss();
									if(faceCount == 0){
										Toast.makeText(PostFaceDataActivity.this, "未识别到人脸，可能是光线问题，请重新拍照", Toast.LENGTH_SHORT).show();
									}else if(faceCount != 1){
										Toast.makeText(PostFaceDataActivity.this, "识别到多张人脸，请重新拍照", Toast.LENGTH_SHORT).show();
									}else{
										try {
											face_id = rst.getJSONArray("face").getJSONObject(0).getString("face_id");
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										Toast.makeText(PostFaceDataActivity.this, "识别成功", Toast.LENGTH_SHORT).show();
									}
									view.setImageBitmap(getNewBitmap(img));// 将图片显示在ImageView里
									
								}
							});
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
						
					}
				});
				faceppDetect.detect(img);
				tag_is_two = true;
			}
		});
		
		
		//
		btn_gen_data.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				new Thread(new Runnable() {
					JSONObject result = null;
					boolean tag_is_person = false;
					boolean tag_is_group = false;
					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							
							try {
								result = httpRequests.infoGetGroupList();
								JSONArray groupArray = result.getJSONArray("group");
								for(int i=0;i<groupArray.length();i++){
									if(groupArray.getJSONObject(i).getString("group_name").equals("ustb_group")){
										tag_is_group = true;
										break;
									}
								}
								if(!tag_is_group){
									httpRequests.groupCreate(new PostParameters().setGroupName("ustb_group"));
								}
								
								result = httpRequests.infoGetPersonList(new PostParameters().setGroupName("ustb_group"));
								System.out.println("infoGetPersonList"+result);
								Log.i("hhh", result.toString());
								JSONArray arrayPerson= result.getJSONArray("person");
								for(int i=0;i<arrayPerson.length();i++){
									if(arrayPerson.getJSONObject(i).getString("person_name").
											equals("person_"+userId)){
										tag_is_person = true;
										break;
									}
								}
								System.out.println(Config.getCachedUserClass(PostFaceDataActivity.this)+"-->"
									+Config.getCachedUserName(PostFaceDataActivity.this));
								if(!tag_is_person){  //还未建立person
									httpRequests.personCreate(new PostParameters().setPersonName("person_"+userId).
											setTag(Config.getCachedUserClass(PostFaceDataActivity.this)+":"
									+Config.getCachedUserName(PostFaceDataActivity.this)));
									httpRequests.groupAddPerson(new PostParameters().
											setGroupName("ustb_group").setPersonName("person_"+userId));
								}
								
								
								
								if(face_id==null){
									PostFaceDataActivity.this.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(PostFaceDataActivity.this,
													"无新的人脸信息，请重新开始采集", Toast.LENGTH_SHORT).show();
										}
									});
									return;
								}
								Log.i("hhh", userId);
								httpRequests.personAddFace(new PostParameters().setPersonName("person_"+userId).setFaceId(face_id));
								PostFaceDataActivity.this.runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(PostFaceDataActivity.this,
												"该次脸部信息采集成功", Toast.LENGTH_SHORT).show();
									}
								});
								face_id = null;
								result = httpRequests.personGetInfo(new PostParameters().setPersonName("person_"+userId));
								Log.i("hhh", result.toString());
								JSONArray faceArray = result.getJSONArray("face");
								if(faceArray.length() > 5){   //每个用户的人脸有5张以上时，再添加用户，则需要移除一张最早
									JSONObject foreFace = faceArray.getJSONObject(0);
									for(int i=0;i<faceArray.length();i++){
										if(foreFace.getString("tag_is_person").
												compareTo(faceArray.getJSONObject(i).getString("tag_is_person"))>=0){
											foreFace = faceArray.getJSONObject(i);
										}
										
									}
									httpRequests.personRemoveFace(new PostParameters().setPersonName("person_"+userId).
											setFaceId(foreFace.getString("face_id")));
								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} catch (FaceppParseException e) {
							// TODO Auto-generated catch block
							Log.i("hhh", e.toString());
							PostFaceDataActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(PostFaceDataActivity.this,
											"网络异常，请确认后重试", Toast.LENGTH_SHORT).show();
								}
							});
						}
					}
				}).start();
			}
		});
		
		
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
//			String sdStatus = Environment.getExternalStorageState();
//			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
//				Log.i("TestFile",
//						"SD card is not avaiable/writeable right now.");
//				return;
//			}
			Bundle bundle = data.getExtras();
			img = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
			view.setImageBitmap(getNewBitmap(img));// 将图片显示在ImageView里
		}
	}
	
	
	
	private Bitmap getNewBitmap(Bitmap bitmap){
		// 获得图片的宽高
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		// 设置想要的大小
		//int newWidth = width*4;
		//int newHeight = 480;
		// 计算缩放比例
		//float scaleWidth = ((float) newWidth) / width;
		//float scaleHeight = ((float) newHeight) / height;
		// 取得想要缩放的matrix参数
		Matrix matrix = new Matrix();
		matrix.postScale(6, 6);
		// 得到新的图片
		Bitmap newbm = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbm;
	}
	
	
//	private void cacheBitmap(Bitmap bitmap){
//		new DateFormat();
//		String name = DateFormat.format("yyyyMMdd_hhmmss",
//				Calendar.getInstance(Locale.CHINA))
//				+ ".jpg";
//		FileOutputStream b = null;
//		File file = new File("/sdcard/Image/");
//		file.mkdirs();// 创建文件夹
//		fileName = "/sdcard/Image/" + name;
//
//		try {
//			b = new FileOutputStream(fileName);
//			bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} finally {
//			try {
//				b.flush();
//				Toast.makeText(this, "该照片的存储目录为："+fileName, Toast.LENGTH_LONG).show();
//				b.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				Toast.makeText(this, "失败，请重试", Toast.LENGTH_LONG).show();
//			}
//		}
//		
//	}
	
	
	 private class FaceppDetect {
	    	DetectCallback callback = null;
	    	
	    	public void setDetectCallback(DetectCallback detectCallback) { 
	    		callback = detectCallback;
	    	}

	    	public void detect(final Bitmap image) {
	    		
	    		new Thread(new Runnable() {
					
					public void run() {
			    		ByteArrayOutputStream stream = new ByteArrayOutputStream();
			    		float scale = Math.min(1, Math.min(600f / img.getWidth(), 600f / img.getHeight()));
			    		Matrix matrix = new Matrix();
			    		matrix.postScale(scale, scale);

			    		Bitmap imgSmall = Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, false);
			    		
			    		imgSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			    		byte[] array = stream.toByteArray();
			    		
			    		try {
			    			//detect
							JSONObject result = httpRequests.detectionDetect(new PostParameters().setImg(array).
									setTag(new Date().getTime()+""));
							//finished , then call the callback function
							if (callback != null) {
								callback.detectResult(result);
							}
						} catch (FaceppParseException e) {
							e.printStackTrace();
							PostFaceDataActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(PostFaceDataActivity.this,
											"网络异常，请确认后重试", Toast.LENGTH_SHORT).show();
								}
							});
						}
						
					}
				}).start();
	    	}
	    }

	    interface DetectCallback {
	    	void detectResult(JSONObject rst);
		}
	
}
