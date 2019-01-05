package com.hungry.iqclass.ic;

public class PersonalRecord {

	private String recordDate;
	private String recordLessoName;
	private String recordDescribe;
	
	public PersonalRecord(String recordDate,String recordLessoName,String recordDescribe) {
		// TODO Auto-generated constructor stub
		this.recordDate = recordDate;
		this.recordLessoName = recordLessoName;
		this.recordDescribe = recordDescribe;
	}

	public String getRecordDate() {
		return recordDate;
	}

	public String getRecordLessoName() {
		return recordLessoName;
	}

	public String getRecordDescribe() {
		return recordDescribe;
	}
	
	
}
