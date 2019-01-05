package com.hungry.iqclass.activity;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.facepp.error.FaceppParseException;
import com.facepp.http.HttpRequests;
import com.facepp.http.PostParameters;
import com.hungry.iqclass.Config;
import com.hungry.iqclass.R;
import com.hungry.iqclass.net.SendIsHereResult;

public class CameraActivity extends Activity {
	SurfaceView sView;
	SurfaceHolder surfaceHolder;
	int screenWidth, screenHeight;
	// 定义系统所用的照相机
	Camera camera;
	// 是否在预览中
	boolean isPreview = false;
	private HttpRequests httpRequests = new HttpRequests(
			"c86213470260c2e3031ac0ea2e596028",
			"LhUOkbR_DBu3RBhg_IF08N_Xv-oWF5d5", true, true);
	private String result_am_here;

	private String class_id;
	private int startHour;
	private int startMin;
	private int endHour;
	private int endMin;
	private String class_name;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置全屏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_camera);
		
		Intent i = getIntent();
		class_id = i.getStringExtra(Config.KEY_CLASS_ID);
		class_name = i.getStringExtra(Config.KEY_CLASS_NAME);
		startHour = i.getIntExtra("startHour",0);
		startMin = i.getIntExtra("startMin",0);
		endHour = i.getIntExtra("endHour",0);
		endMin = i.getIntExtra("endMin",0);

		// 获取窗口管理器
		WindowManager wm = getWindowManager();
		Display display = wm.getDefaultDisplay();
		DisplayMetrics metrics = new DisplayMetrics();
		// 获取屏幕的宽和高
		display.getMetrics(metrics);
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;
		// 获取界面中SurfaceView组件
		sView = (SurfaceView) findViewById(R.id.sv_show);

		// 获得SurfaceView的SurfaceHolder
		surfaceHolder = sView.getHolder();
		// 为surfaceHolder添加一个回调监听器
		surfaceHolder.addCallback(new Callback() {
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// 打开摄像头
				initCamera();
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// 如果camera不为null ,释放摄像头
				if (camera != null) {
					if (isPreview)
						camera.stopPreview();
					camera.release();
					camera = null;
				}
			}
		});

		findViewById(R.id.btn_return_to_iqclass).setOnClickListener(
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						startActivity(new Intent(CameraActivity.this,
								HomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
						finish();
					}
				});

		findViewById(R.id.btn_start).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				camera.startPreview();
				isPreview = true;
				//System.out.println("class_id---"+ class_id);
				if (camera != null) {
					// 控制摄像头自动对焦后才拍照
					// camera.autoFocus(new MyAutoFocusCallback()); // ④
					camera.takePicture(new ShutterCallback() {
						public void onShutter() {
							// 按下快门瞬间会执行此处代码
						}
					}, new PictureCallback() {
						public void onPictureTaken(byte[] data, Camera c) {
							// 此处代码可以决定是否需要保存原始照片信息
							System.out.println("picture--PictureCallback");
						}
					}, myJpegCallback); // ⑤
				}
			}
		});
	}

	private void initCamera() {
		if (!isPreview) {
			// 此处默认打开后置摄像头。
			// 通过传入参数可以打开前置摄像头
			camera = Camera.open(1); // ①/
			camera.setDisplayOrientation(90);
		}
		if (camera != null && !isPreview) {
			try {
				Camera.Parameters parameters = camera.getParameters();
				// 设置预览照片的大小
				parameters.setPreviewSize(screenWidth, screenHeight);
				// 设置预览照片时每秒显示多少帧的最小值和最大值
				parameters.setPreviewFpsRange(4, 10);
				// 设置图片格式
				parameters.setPictureFormat(ImageFormat.JPEG);
				// 设置JPG照片的质量
				parameters.set("jpeg-quality", 85);
				// 设置照片的大小
				parameters.setPictureSize(screenWidth, screenHeight);
				// 通过SurfaceView显示取景画面
				camera.setPreviewDisplay(surfaceHolder); // ②
				// 开始预览
				camera.startPreview(); // ③
			} catch (Exception e) {
				e.printStackTrace();
			}
			isPreview = true;
		}
	}

	PictureCallback myJpegCallback = new PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, final Camera camera) {
			// 根据拍照所得的数据创建位图
			Bitmap img = BitmapFactory.decodeByteArray(data, 0, data.length);

			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			float scale = Math.min(1,
					Math.min(600f / img.getWidth(), 600f / img.getHeight()));
			Matrix matrix = new Matrix();
			//matrix.postScale(scale, scale);
			matrix.setRotate(-90, scale, scale);
			Bitmap imgSmall = Bitmap.createBitmap(img, 0, 0, img.getWidth(),
					img.getHeight(), matrix, false);
//			((ImageView) CameraActivity.this.findViewById(R.id.iv))
//					.setImageBitmap(imgSmall);
			imgSmall.compress(Bitmap.CompressFormat.JPEG, 100, stream);
			final byte[] array = stream.toByteArray();
			// 重新浏览
			camera.stopPreview();
			new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					// 对group进行训练
					try {
						JSONObject result = httpRequests
								.trainIdentify(new PostParameters()
										.setGroupName("ustb_group"));
						System.out.println("trainIdentify---" + result);
						String identify_session_id = result
								.getString("session_id");
						String identify_status = null;
						do {
							result = httpRequests
									.infoGetSession(new PostParameters()
											.setSessionId(identify_session_id));
							System.out.println("infoGetSession---" + result);
							identify_status = result.getString("status");
						} while (identify_status.equals("INQUEUE"));

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						CameraActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(CameraActivity.this,
										"网络异常，请确认后重试", Toast.LENGTH_SHORT)
										.show();
								camera.startPreview();
							}
						});
						
						return;
					} catch (FaceppParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						CameraActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(CameraActivity.this,
										"网络异常，请确认后重试", Toast.LENGTH_SHORT)
										.show();
								camera.startPreview();
							}
						});
						
						return;
					}
					result_am_here = null;
					try {
						JSONObject result_of_identify = null;
						result_of_identify = httpRequests
								.recognitionIdentify(new PostParameters()
										.setImg(array).setGroupName(
												"ustb_group").setMode("oneface"));
						System.out.println("recognitionIdentify---"
								+ result_of_identify);
						Log.i("hhh", "recognitionIdentify---"
								+ result_of_identify);
						JSONArray array_face = result_of_identify
								.getJSONArray("face");
						if (array_face.length() == 0) {
							CameraActivity.this.runOnUiThread(new Runnable() {
								public void run() {
									Toast.makeText(CameraActivity.this,
											"该次拍照未识别到人脸,请在光线良好的地方重试", Toast.LENGTH_SHORT)
											.show();
									camera.startPreview();
								}
							});
							
							return;
						}
//						result_am_here = "该次拍照共识别到" + array_face.length()
//								+ "张人脸。";
//						for (int i = 0; i < array_face.length(); i++) {
							JSONObject face = array_face.getJSONObject(0);
							JSONArray candidate = face
									.getJSONArray("candidate");
							if (candidate.length() == 0) {
								
								result_am_here = "无法识别你的身份";
								CameraActivity.this.runOnUiThread(new Runnable() {
									public void run() {
										Toast.makeText(CameraActivity.this,
												result_am_here, Toast.LENGTH_SHORT)
												.show();
										camera.startPreview();
									}
								});
								return;
								
//								result_am_here = result_am_here + "第" + (i + 1)
//										+ "位同学无法识别其身份;";
								//continue;
							}else{
								String confidence = candidate.getJSONObject(0)
										.getString("confidence");
								if(Double.parseDouble(confidence)<50){
									result_am_here = "无法识别你的身份";
									CameraActivity.this.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(CameraActivity.this,
													result_am_here, Toast.LENGTH_SHORT)
													.show();
											camera.startPreview();
										}
									});
									return;
								}
								
								  String person_name = candidate.getJSONObject(0)
										.getString("person_name");
								
								  String user_id = person_name.split("_")[1];
								
								if(!user_id.equals(Config.getCachedUserId(CameraActivity.this))){
									
									result_am_here = "识别出的同学学号为"+user_id+",与当前登录用户学号不符，无法签到";
									CameraActivity.this.runOnUiThread(new Runnable() {
										public void run() {
											Toast.makeText(CameraActivity.this,
													result_am_here, Toast.LENGTH_SHORT)
													.show();
											camera.startPreview();
										}
									});
									return;
									
								}else{
									String person_tag = candidate.getJSONObject(0)
											.getString("tag");
										//判断上课时间与当前时间的关系
									//result_am_here = person_tag+"签到成功";
									
									Calendar c = Calendar.getInstance();
									int nowHour = c.get(Calendar.HOUR_OF_DAY);
									int nowMin = c.get(Calendar.MINUTE);
									int nowTime = nowHour*60 + nowMin;
									int startTime = startHour*60 + startMin;
									int endTime = endHour*60 + endMin;
									
									int tag_am_here = 0;
									
									if(!class_id.equals(Config.getCachedCurrentClassId(CameraActivity.this))){
										Config.cacheOnceTime(CameraActivity.this, 0);
										Config.cachetwiceTime(CameraActivity.this, 0);
										Config.cacheThrceTime(CameraActivity.this, 0);
										Config.cacheCurrentClassId(CameraActivity.this, class_id);
									}
									
									if(Config.getCachedOnceTime(CameraActivity.this)==0){
										Config.cacheOnceTime(CameraActivity.this, 1);
										if(nowTime>startTime-10&&nowTime<startTime+5){
											//按时来上了课
											result_am_here = person_tag+"上课签到成功";
											tag_am_here = 1 ; //上课签到，按时，需要将缺勤次数减1，早退次数加1,个人记录加上早退记录
										}else if(nowTime>=startTime+5&&nowTime<startTime+50){
											//迟到
											
											result_am_here = person_tag+"迟到"+(nowTime-startTime)+"分钟";
											tag_am_here = 2 ; //迟到， 将缺勤次数减1 ，迟到次数加1，早退次数加1，个人记录加上早退记录和迟到记录
										}else{
											//缺勤
											result_am_here = person_tag+"迟到时间过长，按缺勤处理，后续两次签到您将无法进行";
											tag_am_here = 5;  //缺勤，个人记录更新为迟到时间过长，视为缺勤，将缺勤消息修改为：迟到时间过长，视为缺勤
											Config.cachetwiceTime(CameraActivity.this, 1);
											Config.cacheThrceTime(CameraActivity.this, 1);
										}
										
									}else if(Config.getCachedtwiceTime(CameraActivity.this) == 0){
										if(nowTime<startTime+45){
											result_am_here = "未到该堂课第二次签到的时间，请等待";
										}else if(nowTime>=startTime+45&&nowTime<startTime+90){
											result_am_here = person_tag+"中场签到成功";
											tag_am_here = 3;    //早退次数减1，个人记录删去该条早退记录
											Config.cachetwiceTime(CameraActivity.this, 1);
										}else{
											result_am_here = "中场签到时间已过，按早退处理,后续一次签到"+person_tag+"将无法进行";
											tag_am_here = 6;     //不进行任何操作,该早退记录更新为：未中场签到，视为早退
											Config.cachetwiceTime(CameraActivity.this, 1);
											Config.cacheThrceTime(CameraActivity.this, 1);
										}
									}else if(Config.getCachedThrceTime(CameraActivity.this) == 0){
										if(nowTime<startTime+90){
											result_am_here = "未到该堂课第三次签到的时间，请等待";
										}else{
											result_am_here = person_tag+"下课签到成功";
											tag_am_here = 4; // 早退次数减1，删去个人记录中的早退记录
											Config.cacheThrceTime(CameraActivity.this, 1);
										}
									}else{
										result_am_here = person_tag+"已经完成签到流程，重复无效";
										CameraActivity.this.runOnUiThread(new Runnable() {
											public void run() {
												Toast.makeText(CameraActivity.this,
														result_am_here, Toast.LENGTH_SHORT)
														.show();
												camera.startPreview();
											}
										});
										return;
									}
									
									//上传数据
									new SendIsHereResult(tag_am_here, nowTime-startTime, class_id, class_name,Config.getCachedUserId(CameraActivity.this),
											new SendIsHereResult.SuccessCallback() {
												
												@Override
												public void onSuccess() {
													// TODO Auto-generated method stub
													CameraActivity.this.runOnUiThread(new Runnable() {
														public void run() {
															Toast.makeText(CameraActivity.this,
																	result_am_here, Toast.LENGTH_SHORT)
																	.show();
															camera.startPreview();
														}
													});
												}
											}, new SendIsHereResult.FailCallback() {
												
												@Override
												public void onFail(int errorStatus) {
													// TODO Auto-generated method stub
													CameraActivity.this.runOnUiThread(new Runnable() {
														public void run() {
															Toast.makeText(CameraActivity.this,
																	"网络异常，请确认后重试", Toast.LENGTH_SHORT)
																	.show();
															camera.startPreview();
														}
													});
												}
											});
									
								}
								
							}

					} catch (FaceppParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						CameraActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(CameraActivity.this,
										"网络异常，请确认后重试", Toast.LENGTH_SHORT)
										.show();
								camera.startPreview();
							}
						});
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						CameraActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								Toast.makeText(CameraActivity.this,
										"网络异常，请确认后重试", Toast.LENGTH_SHORT)
										.show();
								camera.startPreview();
							}
						});
						e.printStackTrace();
					}
				}

			}).start();
			
		}
	};

}
