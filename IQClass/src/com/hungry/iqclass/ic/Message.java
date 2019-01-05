package com.hungry.iqclass.ic;

public class Message {
	private String msg = null;
	private int lessonId;
	private String lessonName;
	private String publicTime;
	public Message(String lessonName, String msg) {
		this.msg = msg;
		this.lessonName = lessonName;
	}


	public String getMsg() {
		return this.msg;
		
	}
	
	public String getlessonName(){
		return this.lessonName;
	}
}
