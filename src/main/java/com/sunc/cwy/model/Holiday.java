package com.sunc.cwy.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sunc.cwy.util.DateUtil;
import lombok.Data;

@Data
public class Holiday {


	@TableId(type = IdType.AUTO)
	private Integer id; //
	private String holidayDate; //
	private String holidayNote; //

	@TableField(exist = false)
	private String holidayDate1; //

	@TableField(exist = false)
	private String holidayDate2; //

	@TableField(exist = false)
	private String ids;

	public String getHolidayWeek() {
		String[] weeks = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
		return weeks[DateUtil.getWeek(DateUtil.getDate(holidayDate))];
	}


}
