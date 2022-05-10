package com.sunc.cwy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sunc.cwy.util.DateUtil;
import lombok.Data;

import java.util.Date;

@Data
public class Uleave {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;

	@TableId(type = IdType.AUTO)
	private Integer id; //

	private int userId; //

	private Date leaveDate; //

	private String leaveDate1; //
	private Integer leaveLesson1; //1:上午 2:下午

	private String leaveDate2; //
	private Integer leaveLesson2; //1:上午 2:下午

	private Integer leaveType; //1:年假 2:病假 3:事假

	private String leaveReason;

	private Integer leaveFlag; // 1:待审核 2:审核通过 3:审核未通过


	@TableField(exist = false)
	private User user; //

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
	private String ids;

	@TableField(exist = false)
	private String random;
	
	public String getLeaveDateDesc(){
		if (leaveDate !=null) {
			return DateUtil.dateToDateString(leaveDate);
		}else {
			return null;
		}
	}
	
	public String getLeaveDate1Desc(){
		if (leaveDate1 !=null) {
			return leaveDate1;
		}else {
			return null;
		}
	}
	
	public String getLeaveDate2Desc(){
		if (leaveDate2 !=null) {
			return leaveDate2;
		}else {
			return null;
		}
	}
	
	public String getUserSexDesc(){
		switch (userSex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "";
		}
	}
	
	public String getLeaveLesson1Desc(){
		switch (leaveLesson1) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getLeaveLesson2Desc(){
		switch (leaveLesson2) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getLeaveTypeDesc(){
		switch (leaveType) {
		case 1:
			return "年假";
		case 2:
			return "病假";
		case 3:
			return "事假";
		default:
			return "";
		}
	}
	
	public String getLeaveFlagDesc(){
		switch (leaveFlag) {
		case 1:
			return "待审核";
		case 2:
			return "审核通过";
		case 3:
			return "审核未通过"; 
		default:
			return "";
		}
	}
	
}
