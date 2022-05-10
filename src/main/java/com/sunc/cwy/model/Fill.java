package com.sunc.cwy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import com.sunc.cwy.util.DateUtil;
import lombok.Data;

import java.util.Date;

@Data
public class Fill {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;

	private Integer id; //

	private int userId; //

	private Date fillDate; //
	private Integer fillLesson; //1:上午 2:下午
	private String fillReason;
	private Integer fillFlag; // 1:待审核 2:审核通过 3:审核未通过

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
	
	public String getFillDateDesc(){
		if (fillDate !=null) {
			return DateUtil.dateToDateString(fillDate);
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
	
	public String getFillLessonDesc(){
		switch (fillLesson) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getFillFlagDesc(){
		switch (fillFlag) {
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
