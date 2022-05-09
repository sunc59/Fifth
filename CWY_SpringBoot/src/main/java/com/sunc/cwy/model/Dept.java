package com.sunc.cwy.model;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class Dept{
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = 1770185824735782580L;
	private Integer id; //
	private String deptName; //
	private String deptNote; //

	@TableField(exist = false)
	private String ids;

}
