package com.sunc.cwy.model;

import lombok.Data;

@Data
public class AttendTemp {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	
	private String attend_date; // 
	private String course_name; // 
	private String clazz_name; // 
	private int user_sex; // 
	private int attend_type; // 1-迟到 2-早退 3-请假 4-旷课
	private int attend_count; // 
	
	public String getUser_sexDesc(){
		switch (user_sex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "男";
		}
	}
	
	public String getAttend_typeDesc(){
		switch (attend_type) {
		case 1:
			return "迟到";
		case 2:
			return "早退";
		case 3:
			return "请假";
		case 4:
			return "旷课";
		default:
			return "";
		}
	}
}
