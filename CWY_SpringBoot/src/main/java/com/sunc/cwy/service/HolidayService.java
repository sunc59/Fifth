package com.sunc.cwy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.mapper.HolidayMapper;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.Holiday;
import com.sunc.cwy.model.User;
import com.sunc.cwy.util.DateUtil;
import com.sunc.cwy.util.StringUtil;
import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author sunc
 */
@Service
public class HolidayService {

    @Autowired(required = false)
    private HolidayMapper holidayMapper;


    /**
     * 批量删除节假日信息
     *
     * @param ids
     */
    public void delHolidays(String[] ids) {
        holidayMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 查询两个日期中间的节假日信息
     *
     * @param holidayDate1
     * @param holidayDate2
     * @return
     */
    public List<Holiday> getHoliday(String holidayDate1, String holidayDate2) {

        QueryWrapper<Holiday> wrapper = new QueryWrapper<>();
        wrapper.between("holiday_date", holidayDate1, holidayDate2);
        List<Holiday> list = holidayMapper.selectList(wrapper);
        return list;

    }

    /**
     * 添加节假日
     *
     * @param holiday 参数：-holidayDate1 -holidayDate2 -holidayNote
     */
    public void addHoliday(Holiday holiday) {

        // 循环将节假日时间段插入数据库
        Date date1 = DateUtil.getDate(holiday.getHolidayDate1());
        Date date2 = DateUtil.getDate(holiday.getHolidayDate2());

        for (Date i = date1; DateUtil.compareDateStr(i, date2) >= 0; i = DateUtil.getDateAfter(i, 1)) {

            Holiday h = new Holiday();
            h.setHolidayNote(holiday.getHolidayNote());
            h.setHolidayDate(DateUtil.dateToDateString(i));
            holidayMapper.insert(h);
        }
    }

    /**
     * 根据id查询节假日
     *
     * @param id
     * @return 参数：-id
     */
    public Holiday getHolidayById(int id) {
        return holidayMapper.selectById(id);
    }

    /**
     * 保存编辑的节假日的信息
     *
     * @param paramsHoliday 参数：-id -holidayNote
     */
    public void saveHoliday(Holiday paramsHoliday) {
        Integer hid = paramsHoliday.getId();
        String holidayNote = paramsHoliday.getHolidayNote();
        Holiday holiday = holidayMapper.selectById(hid);
        if (!StringUtil.isEmptyString(holidayNote)) {
            holiday.setHolidayNote(holidayNote);
        }
        holidayMapper.updateById(holiday);
    }

    /**
     * 条件分页查询节假日列表
     *
     * @param paramsHoliday
     * @param currPage
     * @param count
     * @return 参数：-holidayDate1 -holidayDate2 -holidayNote
     */
    public Page<Holiday> listHolidays(Holiday paramsHoliday, int currPage, int count) {

        String holidayDate1 = paramsHoliday.getHolidayDate1();
        String holidayDate2 = paramsHoliday.getHolidayDate2();
        String holidayNote = paramsHoliday.getHolidayNote();

        QueryWrapper<Holiday> wrapper = new QueryWrapper<>();

        if (!StringUtil.isEmptyString(holidayDate1) && !StringUtil.isEmptyString(holidayDate2)) {

            wrapper.between("holiday_date", holidayDate1, holidayDate2);
        }

        if (!StringUtil.isEmptyString(holidayNote)) {
            wrapper.like("holiday_note", holidayNote);
        }

        if (currPage <= 0) {
            currPage = 1;
        }
        if (count <= 0) {
            count = 10;
        }

        Page<Holiday> page = new Page<>(currPage, count);

        holidayMapper.selectPage(page, wrapper);

        return page;
    }


}
