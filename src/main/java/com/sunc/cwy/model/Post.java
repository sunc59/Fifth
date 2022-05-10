package com.sunc.cwy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sunc.cwy.util.DateUtil;
import lombok.Data;

import java.util.Date;

@Data
public class Post {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;

	@TableId(type = IdType.AUTO)
	private Integer id; //

	@TableField(exist = false)
	private User user; //

	private Date postDate; //

	private String postDate1; //
	private Integer postLesson1; //1:上午 2:下午
	private String postDate2; //
	private Integer postLesson2; //1:上午 2:下午
	private String postReason;
	private Integer postFlag; // 1:待审核 2:审核通过 3:审核未通过

	private int userId; //

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
	
	public String getPostDateDesc(){
		if (postDate !=null) {
			return DateUtil.dateToDateString(postDate);
		}else {
			return null;
		}
	}
	
	public String getPostDate1Desc(){
		if (postDate1 !=null) {
			//return DateUtil.dateToDateString(postDate1);
			return postDate1;
		}else {
			return null;
		}
	}
	
	public String getPostDate2Desc(){
		if (postDate2 !=null) {
			//return DateUtil.dateToDateString(postDate2);
			return postDate2;
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
	
	public String getPostLesson1Desc(){
		switch (postLesson1) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getPostLesson2Desc(){
		switch (postLesson2) {
		case 1:
			return "上午";
		case 2:
			return "下午";
		default:
			return "";
		}
	}
	
	public String getPostFlagDesc(){
		switch (postFlag) {
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
