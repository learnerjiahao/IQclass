package com.hungry.iqclass;

import java.util.ArrayList;

import android.accounts.Account;
import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.telephony.TelephonyManager;
import android.util.Log;

public class Config {

	//public static final String SERVER_URL = "http://hungryjiahao.esy.es/server/main.php"; // 服务器域名
	//http://192.168.0.3/server/
	public static final String SERVER_URL = "http://192.168.0.3:80/server/main.php"; // 服务器域名
	//public static final String SERVER_URL = "http://192.168.0.3:8888/server/main.php"; // 服务器域名
//	public static final String DETECT_URL = "http://apicn.faceplusplus.com/v2/detection/detect?" +
//			"api_key=c86213470260c2e3031ac0ea2e596028$api_secret=LhUOkbR_DBu3RBhg_IF08N_Xv-oWF5d5"; // 服务器域名

	public static final String APP_ID = "com.hungry.iqclass"; // APP包名
	public static final String CHARSET = "utf-8"; // 编码方式

	public static final int RESULT_STATUS_FAIL = 0; // 服务器返回信息失败
	public static final int RESULT_STATUS_SUCCESS = 1; // 服务器返回信息成功
	public static final int RESULT_STATUS_ERR = 2; // 帐号密码错误
	public static final int RESULT_STATUS_SEMESTER_ERR = 3; // 教务系统无该学期课程

	public static final String KEY_USERID = "userId";
	public static final String KEY_PASSWORD = "password";
	public static final String KEY_USER_TYPE = "userType";
	private static final String KEY_IS_MEMORY = "isMemory";
	public static final String KEY_ACTION = "action";
	public static final String KEY_STATUS = "status";
	public static final String KEY_SEMESTER = "semester";
	public static final String KEY_INFO_OF_CLASSES = "info_of_classes";
	public static final String KEY_CLASS_ID = "class_id";
	public static final String KEY_CLASS_NAME = "class_name";
	public static final String KEY_CLASS_TYPE = "class_type";
	public static final String KEY_CLASS_PERIOD = "class_period";
	public static final String KEY_CLASS_CREDIT = "class_credit";
	public static final String KEY_CLASS_TIME_LOCATION = "class_time_location";
	public static final String KEY_CLASS_TEACHER_NAME = "class_teacher_name";
	public static final String KEY_CLASS_SEMESTER = "class_semester";
	
	public static final String KEY_USER_NAME = "user_name";
	public static final String KEY_USER_GENDER = "user_gender";
	public static final String KEY_USER_GRADE = "user_grade";
	public static final String KEY_USER_PROFESSION = "user_profession";
	public static final String KEY_USER_CLASS = "user_class";
	
	public static final String KEY_LONGITUDE = "longitude";
	public static final String KEY_LATITUDU = "latitude";
	public static final String KEY_CURRENT_WEEKTH = "current_weekth";
	
	
	
	public static final String KEY_LESSON = "lesson";
	public static final String KEY_API_KEY = "api_key";
	public static final String KEY_API_SECRET = "api_secret";
	public static final String API_KEY = "c86213470260c2e3031ac0ea2e596028";
	public static final String API_SECRET = "LhUOkbR_DBu3RBhg_IF08N_Xv-oWF5d5";

	public static final String STUDENT_TYPE = "undergraduate";
	public static final String TEACHER_TYPE = "teacher";

	public static final String ACTION_LOGIN = "login";
	public static final String ACTION_IMPORT_CLASS_INFO = "import_class_info";
	public static final String ACTION_UPDATE_LOCATION = "update_location";
	public static final String ACTION_GET_CLASS_LOCATION = "get_class_location";
	public static final String ACTION_GET_PERSONAL_RECORD = "get_personal_record";
	public static final String Action_GET_OTHER_CLASSES = "get_other_classes";
	public static final String ACTION_SEND_RESULT = "send_result";
	public static final String ACTION_GET_CLASS_RECORDS = "get_class_records";
	public static final String ACTION_GET_MY_GRADE = "get_my_grade";
	public static final String ACTION_GET_ASK_VOCATION = "ask_for_vocation";
	// public static final int RESULT_STATUS_ERROR_PHONE_IMEI = 2; //用户更换了手机
	// public static final int RESULT_STATUS_ERROR_INFO = 3;
	// //登录时:用户输入的手机号和学号有误或者还未注册
	// //注册时:表示学号或者电话号已经被注册过

	public static final int TEACHER_TAG = 1;
	public static final int STUDENT_TAG = 0;

	public static final int MALE_TAG = 1;
	public static final int FEMALE_TAG = 0;

	public static final String KEY_SEX_TAG = "sexTag";
	public static final String KEY_ACCOUNT_TAG = "accountTag";
	public static final String KEY_PHONE_NUMBER = "phoneNumber";
	public static final String KEY_STUDY_NUMBER = "studyNumber";
	public static final String KEY_PHONE_IMEI = "phoneIMEI";
	public static final String KEY_PERSONAL_NAME = "personalName";
	public static final String KEY_SCHOOL_NAME = "schoolName";
	public static final String KEY_ACADEMY_NAME = "academyName";
	public static final String KEY_ACCOUNT = "account";
	public static final String KEY_MESSAGE = "message";
	public static final String KEY_LESSON_NAME = "lessonName";
	public static final String KEY_LESSON_START_TIME = "lessonStartTime";
	public static final String KEY_LESSON_STOP_TIME = "lessonStopTime";
	public static final String KEY_LESSON_DAY = "lessonDay";
	public static final String KEY_LESSON_NUMBER = "lessonNumber";
	
	public static final String KEY_TEACHER_NAME = "teacherName";
	public static final String KEY_LESSON_TH = "lessonTh";
	public static final String KEY_LESSON_LOCATION = "lessonLocation";

	public static final String ACTION_RESIGTER = "resigter";
	public static final String ACTION_GET_MESSAGE = "getMessage";
	public static final String ACTION_ADD_LESSON = "addLesson";

	

	

	


	

	public static String getCachedUserId(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_USERID, null);
	}

	public static String getCachedPassword(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_PASSWORD, null);
	}

	public static String getCachedUserType(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_USER_TYPE, null);
	}

	public static int getCachedIsMemory(Context context) {
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getInt(KEY_IS_MEMORY, 0);
	}

	public static String getCachedSemesterChoice(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_SEMESTER, "2015-2016-2");
	}
	public static String getCachedUserName(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_USER_NAME, null);
	}
	public static String getCachedUserClass(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_USER_CLASS,null);
	}
	public static String getCachedUserProfession(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_USER_PROFESSION,null);
	}
	public static String getCachedUserGrade(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_USER_GRADE, null);
	}
	public static String getCachedUserGender(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString(KEY_USER_GENDER, null);
	}
	public static int getCachedCurrentWeekth(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getInt(KEY_CURRENT_WEEKTH, 0);
	}
	
	public static long getCachedNowTime(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getLong("now_time", 0);
	}
	
	
	public static void cacheTagAndTagTime(Context context,int tag,long time_tag){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putInt("tag", tag);
		e.putLong("time_tag", time_tag);
		e.commit();
	}
	
	public static void cacheCurrentClassId(Context context,String current_class_id){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString("current_class_id", current_class_id);
		e.commit();
	}
	
	public static void cacheOnceTime(Context context,int once_time){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putInt("once_time", once_time);
		e.commit();
	}
	
	public static void cachetwiceTime(Context context,int twice_time){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putInt("twice_time", twice_time);
		e.commit();
	}
	
	public static void cacheThrceTime(Context context,int thrce_time){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putInt("thrce_time", thrce_time);
		e.commit();
	}
	
	public static String getCachedCurrentClassId(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getString("current_class_id", null);
	}
	
	public static int getCachedOnceTime(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getInt("once_time", 0);
	}
	public static int getCachedtwiceTime(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getInt("twice_time", 0);
	}
	public static int getCachedThrceTime(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getInt("thrce_time", 0);
	}
	
	public static int getCachedTag(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getInt("tag", 0);
	}
	public static long getCachedTimeTag(Context context){
		return context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.getLong("time_tag", 0);
	}
	
	public static void cacheCurrentWeekth(Context context,int current_weekth,long now_time){
		
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putInt(Config.KEY_CURRENT_WEEKTH, current_weekth);
		e.putLong("now_time", now_time);
		e.commit();
		//e.apply();
	}
	
	public static void cacheUserInfo(Context context, String userId,
			String password, String userType, int isMemory) {
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(Config.KEY_USERID, userId);
		e.putString(Config.KEY_PASSWORD, password);
		e.putString(Config.KEY_USER_TYPE, userType);
		e.putInt(KEY_IS_MEMORY, isMemory);
		e.commit();
	}
	
	public static void cacheSemesterChoice(Context context,String semester){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(Config.KEY_SEMESTER, semester);
		e.commit();
	}
	
	public static void cachedUserMoreInfo(Context context,String user_name,String user_gender,
			String user_grade,String user_profession,String user_class){
		Editor e = context.getSharedPreferences(APP_ID, Context.MODE_PRIVATE)
				.edit();
		e.putString(Config.KEY_USER_NAME, user_name);
		e.putString(Config.KEY_USER_GENDER, user_gender);
		e.putString(Config.KEY_USER_GRADE, user_grade);
		e.putString(Config.KEY_USER_PROFESSION, user_profession);
		e.putString(Config.KEY_USER_CLASS, user_class);
		e.commit();
	}
	
	


	public static String getClassTime(String segemenceth) {
		char segemence = segemenceth.charAt(1);
		String time = null;
		switch (segemence) {
		case '1':
			time = "8:00-9:35";
			break;
		case '2':
			time = "9:55-11:30";
			break;
		case '3':
			time = "13:30-15:05";
			break;
		case '4':
			time = "15:20-16:55";
			break;
		case '5':
			time = "17:10-18:45";
			break;
		default:
			time = "19:30-21:05";
			break;
		}
		return time;
	}
	
	
	public static ArrayList<ArrayList<String>> parseClassTimeLocation(String class_time_location){
		ArrayList<String> lesson = new ArrayList<String>();
		ArrayList<String> operate = new ArrayList<String>();
		ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();
		//System.out.println(class_time_location+"-----");
		if(class_time_location.equals("+")){
			
			return null;
		}
		if(class_time_location.indexOf("+") == class_time_location.length()-1){
			StringBuffer buffer = new StringBuffer(class_time_location);
			do{
				String delBuffer = buffer.substring(buffer.indexOf("(周")+1, buffer.indexOf(") "));
				System.out.println(delBuffer);
				lesson.add(delBuffer);
				buffer = buffer.delete(buffer.indexOf("(周"), buffer.indexOf(") ")+1);
				//System.out.println("buffer====>"+buffer);
			}while(buffer.indexOf("(")>=0);
			arrayList.add(lesson);
			//System.out.println(class_time_location+"-----"+lesson+"---"+operate);
			return arrayList;
		}
		
		String[] lesson_operation = class_time_location.split("\\+");
		
		StringBuffer lessBuffer = new StringBuffer(lesson_operation[0]);
		do{
			String delBuffer = lessBuffer.substring(lessBuffer.indexOf("(周")+1, lessBuffer.indexOf(") "));
			lesson.add(delBuffer);
			lessBuffer = lessBuffer.delete(lessBuffer.indexOf("(周"), lessBuffer.indexOf(") ")+1);
			//System.out.println(delBuffer+"----"+lessBuffer.length()+"----"+lessBuffer+lesson);
		}while(lessBuffer.indexOf("(")>=0);
		
		StringBuffer operBuffer = new StringBuffer(lesson_operation[1]);
		do{
			String delBuffer = operBuffer.substring(operBuffer.indexOf("(周")+1, operBuffer.indexOf(") "));
			operate.add(delBuffer);
			operBuffer = operBuffer.delete(operBuffer.indexOf("(周"), operBuffer.indexOf(") ")+1);
		}while(operBuffer.indexOf("(")>=0);
		//System.out.println(class_time_location+"-----"+lesson+"---"+operate);
		arrayList.add(lesson);
		arrayList.add(operate);
		return arrayList;
	}
}
