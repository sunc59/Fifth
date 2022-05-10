package com.sunc.cwy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

@Data
//@TableName("user")
public class User {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -460922993085630428L;
	@TableId(type = IdType.AUTO)
	private Integer id; //

	private String userName; //
	private String userPass; //
	private String userNo; //
	private String realName; //
	private Integer userSex; // 1：男  2：女
	private Integer userType; // 1:员工 2:管理员
	private int deptId; // 部门id

	@TableField(exist = false)
	private Dept dept; //

	@TableField(exist = false)
	private String deptName; //

	@TableField(exist = false)
	private String ids;

	@TableField(exist = false)
	private String random;

	public String getUserSexDesc(){
		switch (userSex) {
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "男";
		}
	}
	
	public String getUserTypeDesc(){
		switch (userType) {
		case 1:
			return "员工";
		case 2:
			return "管理员";
		default:
			return "";
		}
	}


}
