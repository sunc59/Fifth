package com.sunc.cwy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sunc.cwy.util.DateUtil;
import lombok.Data;

import java.util.Date;

@Data
public class Attend {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;

	@TableId(type = IdType.AUTO)
	private Integer id; //

	private int userId; //

	private Date attendTime; //

	private Date attendDate; //

	private Integer attendLesson; //1:上午 2:下午

	private Integer attendType; // 1:未签到 2:已签到 3:迟签到 4:请假 5:离岗 6:早退

	@TableField(exist = false)
	private User user; //

	@TableField(exist = false)
	private Integer attendWeek;//签到星期

	@TableField(exist = false)
	private String attendDate1; //

	@TableField(exist = false)
	private String attendDate2; //

	@TableField(exist = false)
	private Integer attendType1; //上午签到情况

	@TableField(exist = false)
	private Integer attendType2; //下班签到情况

	@TableField(exist = false)
	private String userNo; //

	@TableField(exist = false)
	private String realName; //

	@TableField(exist = false)
	private Integer userSex; //

	@TableField(exist = false)
	private int deptId; //

	@TableField(exist = false)
	private String deptName; //

	@TableField(exist = false)
	private String attendMonth; //

	@TableField(exist = false)
	private double attendType1Days; // 未签到天数

	@TableField(exist = false)
	private double attendType2Days; // 已签到天数

	@TableField(exist = false)
	private double attendType3Days; // 迟签到天数

	@TableField(exist = false)
	private double attendType4Days; // 请假天数

	@TableField(exist = false)
	private double attendType5Days; // 离岗天数

	@TableField(exist = false)
	private double attendType6Days; // 早退天数


	@TableField(exist = false)
	private String ids;

	@TableField(exist = false)
	private String random;
	
	public String getAttendTimeDesc(){
		if (attendTime !=null) {
			return DateUtil.dateToDateString(attendTime);
		}else {
			return null;
		}
	}
	
	public String getAttendDateDesc(){
		if (attendDate !=null) {
			return DateUtil.dateToDateString(attendDate);
		}else {
			return null;
		}
	}
	
	public String getUserSexDesc(){
		switch (userSex ==null?0: userSex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "";
		}
	}
	
	public String getAttendLessonDesc(){
		switch (attendLesson ==null?0: attendLesson) {
		case 1:
			return "上班";
		case 2:
			return "下班";
		default:
			return "";
		}
	}
	
	public String getAttendTypeDesc(){
		switch (attendType ==null?0: attendType) {
		case 1:
			return "未签到";
		case 2:
			return "已签到";
		case 3:
			return "迟签到";
		case 4:
			return "请假";
		case 5:
			return "离岗";
		case 6:
			return "早退";
		default:
			return "";
		}
	}
	
	public String getAttendType1Desc(){
		switch (attendType1 ==null?0: attendType1) {
		case 1:
			return "未签到";
		case 2:
			return "已签到";
		case 3:
			return "迟签到";
		case 4:
			return "请假";
		case 5:
			return "离岗";
		default:
			return "";
		}
	}
	
	public String getAttendType2Desc(){
		switch (attendType2 ==null?0: attendType2) {
		case 1:
			return "未签到";
		case 2:
			return "已签到";
		case 3:
			return "迟签到";
		case 4:
			return "请假";
		case 5:
			return "离岗";
		case 6:
			return "早退";
		default:
			return "";
		}
	}
	
	public String getAttendType1Color(){
		switch (attendType1 ==null?0: attendType1) {
		case 1:
			return "red";
		case 2:
			return "green";
		case 3:
			return "orange";
		case 4:
			return "silver";
		case 5:
			return "silver";
		default:
			return "";
		}
	}
	
	public String getAttendType2Color(){
		switch (attendType2 ==null?0: attendType2) {
		case 1:
			return "red";
		case 2:
			return "green";
		case 3:
			return "orange";
		case 4:
			return "silver";
		case 5:
			return "silver";
		case 6:
			return "orange";
		default:
			return "";
		}
	}
	
	public String getAttendWeekDesc() {
		String[] weeks = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		if (getAttendDate()!=null) {
			return weeks[DateUtil.getWeek(getAttendDate())];
		}
		return "";
	}
}
