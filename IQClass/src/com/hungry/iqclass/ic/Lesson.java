package com.hungry.iqclass.ic;

import java.io.Serializable;

public class Lesson implements Serializable {

	private String class_id;
	private String class_name;
	private String class_teacher_name;
	private String class_period;
	private String class_credit;
	private String class_type;
	
	private String class_time;
	private String class_location;
	private String class_segmenceth;
	private String class_time_location;
	private String class_weeks;
	private String class_weekth;

	public Lesson() {
		super();
	}

	public Lesson(String class_id, String class_name,
			String class_teacher_name, String class_period,
			String class_credit, String class_type, String class_time,
			String class_location, String class_segmenceth,String class_weeks,String weekth) {
		super();
		this.class_id = class_id;
		this.class_name = class_name;
		this.class_teacher_name = class_teacher_name;
		this.class_period = class_period;
		this.class_credit = class_credit;
		this.class_type = class_type;
		this.class_time = class_time;
		this.class_location = class_location;
		this.class_segmenceth = class_segmenceth;
		this.class_weeks = class_weeks;
		this.class_weekth = weekth;
	}

	public String getClass_id() {
		return class_id;
	}

	public String getClass_name() {
		return class_name;
	}

	public String getClass_teacher_name() {
		return class_teacher_name;
	}

	public String getClass_period() {
		return class_period;
	}

	public String getClass_credit() {
		return class_credit;
	}

	public String getClass_type() {
		return class_type;
	}

	public String getClass_time() {
		return class_time;
	}

	public String getClass_location() {
		return class_location;
	}

	public String getClass_segmenceth() {
		return class_segmenceth;
	}

	public String GetClass_weeks() {
		return class_weeks;
	}
	
	public String GetClass_weekth() {
		return class_weekth;
	}



}
