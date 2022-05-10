package com.sunc.cwy.manager;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.model.*;
import com.sunc.cwy.model.common.Condition;
import com.sunc.cwy.model.common.PageInfo;
import com.sunc.cwy.service.*;
import com.sunc.cwy.util.DateUtil;
import com.sunc.cwy.util.Md5;
import com.sunc.cwy.util.Param;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminManager {


    @Autowired
    private AttendService attendService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private HolidayService holidayService;


    /**
     * @param attend
     * @return List<Attend>
     * @Title: listAttends
     * @Description: 考勤查询
     */
    public List<Attend> listAttends(Attend attend, int[] sum) {

        if (sum != null) {
            sum[0] = attendService.listAttendsCount(attend);
        }
        List<Attend> attends = attendService.listAttends(attend);


        return attends;
    }

    /**
     * @param attend
     * @return List<Attend>
     * @Title: listAttendTemps
     * @Description: 考勤统计
     */
    public List<Attend> listAttendTemps(Attend attend) {

        List<Attend> attends = attendService.listAttendTemps(attend);

        return attends;
    }


    /**
     * @return void
     * @Title: checkAttendToday
     * @Description: 检查本日考勤是否存在
     */
    public String checkAttendToday() {
        String holiday_note = null;

        //检查本日是否为节假日
        Holiday _holiday = new Holiday();
        _holiday.setHolidayDate(DateUtil.getCurDate());
        //_holiday = holidayService.getHoliday(_holiday);
        if (_holiday == null) {
            //查询本日考勤是否存在
            Attend _attend = new Attend();
            _attend.setAttendDate(DateUtil.getDate(DateUtil.getCurDate()));
            _attend = attendService.getAttend(_attend);
            if (_attend == null) {
                //初始化本日考勤
                addAttendBatch();
            } else {
                User admin = (User) Param.getSession("admin");
                _attend = new Attend();
                _attend.setUserId(admin.getId());
                _attend.setAttendDate(DateUtil.getDate(DateUtil.getCurDate()));
                _attend = attendService.getAttend(_attend);
                if (_attend == null) {
                    //初始化本日考勤
                    addAttendBatchUser(DateUtil.getCurDate(), admin.getId());
                }
            }
        } else {
            holiday_note = _holiday.getHolidayNote();
        }

        return holiday_note;
    }

    /**
     * @return void
     * @Title: checkAttendEveryDay
     * @Description: 检查指定日期考勤是否存在
     */
    public String checkAttendEveryDay(String date) {
        String holiday_note = null;

        //检查本日是否为节假日
        Holiday _holiday = new Holiday();
        _holiday.setHolidayDate(date);
        //_holiday = holidayService.getHoliday(_holiday);
        if (_holiday == null) {
            //查询本日考勤是否存在
            Attend _attend = new Attend();
            _attend.setAttendDate(DateUtil.getDate(DateUtil.getCurDate()));
            _attend = attendService.getAttend(_attend);
            if (_attend == null) {
                //初始化本日考勤
                addAttendBatch(date);
            }
        } else {
            holiday_note = _holiday.getHolidayNote();
        }

        return holiday_note;
    }

    /**
     * @param date1
     * @param date2
     * @return void
     * @Title: checkAttends
     * @Description: 检查日期段考勤
     */
    public void checkAttends(String date1, String date2) {
        if (!StringUtil.isEmptyString(date1)) {
            Date curDate = DateUtil.getDate(DateUtil.getCurDate());
            Date startDate = DateUtil.getDate(date1);
            Date endDate = DateUtil.getDate(date2);
            if (DateUtil.compareDateStr(curDate, endDate) > 0) {
                endDate = curDate;
            }
            for (Date _date = startDate; DateUtil.compareDateStr(_date, endDate) >= 0; _date = DateUtil.getDateAfter(_date, 1)) {
                checkAttendEveryDay(DateUtil.dateToDateString(_date));
            }
        }
    }

    /**
     * @param attend
     * @return void
     * @Title: addAttend
     * @Description: 添加考勤
     */
    public void addAttend(Attend attend) {

        //查询本日考勤是否存在
        checkAttendToday();

        //打卡人
        User admin = (User) Param.getSession("admin");
        attend.setUserId(admin.getId());
        //打卡时间
        attend.setAttendTime(DateUtil.getDate(DateUtil.getCurDateTime()));
        //打卡日期
        attend.setAttendDate(DateUtil.getDate(DateUtil.getCurDate()));
        //上午or下午
        String attend_hour_second = attend.getAttendTimeDesc().substring(11, 16).replaceAll(":", "");
        attend.setAttendLesson(Integer.parseInt(attend_hour_second) > 1200 ? 2 : 1);
        //签到状态
        Config config = configService.getConfig();
        String config_date1 = config.getConfigDate1().replaceAll(":", "");
        String config_date2 = config.getConfigDate2().replaceAll(":", "");
        String config_date = config_date1;
        //上班签到判断
        if (attend.getAttendLesson() == 1) {
            attend.setAttendType(2);
            if (Integer.parseInt(attend_hour_second) > Integer.parseInt(config_date)) {
                attend.setAttendType(3);
            }
        }
        //下班签到判断
        else if (attend.getAttendLesson() == 2) {
            config_date = config_date2;
            attend.setAttendType(2);
            if (Integer.parseInt(attend_hour_second) < Integer.parseInt(config_date)) {
                attend.setAttendType(6);
            }
        }


        attendService.updateAttendType(attend);

    }

    /**
     * @return void
     * @Title: addAttendBatch
     * @Description: 初始化本日考勤
     */
    public void addAttendBatch() {

        //初始化本日考勤
        attendService.addAttendBatch();
        //根据请假记录更新本日考勤
        attendService.updateAttendTypeBatchLeave();
        //根据离岗记录更新本日考勤
        attendService.updateAttendTypeBatchPost();

    }

    public void addAttendBatch(String date) {

        //初始化本日考勤
        attendService.addAttendBatch(date, 1);
        //根据请假记录更新本日考勤
        attendService.updateAttendTypeBatchLeave(date);
        //根据离岗记录更新本日考勤
        attendService.updateAttendTypeBatchPost(date);

    }

    public void addAttendBatchUser(String date, int user_id) {

        //初始化本日考勤
        attendService.addAttendBatch(date, user_id, 1);
        //根据请假记录更新本日考勤
        attendService.updateAttendTypeBatchLeave(date, user_id);
        //根据离岗记录更新本日考勤
        attendService.updateAttendTypeBatchPost(date, user_id);

    }

    /**
     * @param attend
     * @return void
     * @Title: updateAttendType
     * @Description: 更新考勤
     */
    public void updateAttendType(Attend attend) {

        attendService.updateAttendType(attend);

    }

    /**
     * @param attend
     * @return void
     * @Title: updateAttendTypeBatchUser
     * @Description: 更新考勤
     */
    public void updateAttendTypeBatchUser(Attend attend) {

        attendService.updateAttendTypeBatchUser(attend);

    }

    /**
     * @param attend
     * @return void
     * @Title: delAttend
     * @Description: 删除考勤信息
     */
    public void delAttends(Attend attend) {

        attendService.delAttends(attend.getIds().split(","));

    }





    /**
     * @param fill
     * @return void
     * @Title: updateFill
     * @Description: 更新补签记录信息
     */
    public void updateFill(Fill fill) {

        //更新补签记录信息
        //fillService.updateFill(fill);
        if (fill.getFillFlag() != null && fill.getFillFlag() == 2) {
            //审核通过
            //fill = fillService.getFill(fill);
            Attend attend = new Attend();
            attend.setUserId(fill.getUser().getId());
            attend.setAttendDate(fill.getFillDate());
            attend.setAttendLesson(fill.getFillLesson());
            attend.setAttendType(2);
            attendService.updateAttendType(attend);
        }

    }


    /**
     * @param leave
     * @return void
     * @Title: updateLeave
     * @Description: 更新请假记录信息
     */
    public void updateLeave(Uleave leave) {

        //更新请假记录
        //leaveService.updateLeave(leave);
        if (leave.getLeaveFlag() != null && leave.getLeaveFlag() == 2) {
            //leave = leaveService.getLeave(leave);
            //审核通过
            Attend attend = new Attend();
            attend.setUserId(leave.getUser().getId());
            attend.setAttendDate1(leave.getLeaveDate1Desc().replaceAll("-", "") + leave.getLeaveLesson1());
            attend.setAttendDate2(leave.getLeaveDate2Desc().replaceAll("-", "") + leave.getLeaveLesson2());
            attend.setAttendType(4);
            attendService.updateAttendTypeBatchUser(attend);
        }

    }


    /**
     * @param holiday
     * @return List<Holiday>
     * @Title: listHolidays
     * @Description: 节假日查询
     */
    public List<Holiday> listHolidays(Holiday holiday, int[] sum) {

        if (sum != null) {
            //sum[0] = holidayService.listHolidaysCount(holiday);
        }
        List<Holiday> holidays = holidayService.listHolidays(null, 0, 0).getRecords();


        return holidays;
    }



}
