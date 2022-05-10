package com.sunc.cwy.controller.admin;

import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.manager.AdminManager;
import com.sunc.cwy.model.*;
import com.sunc.cwy.service.AttendService;
import com.sunc.cwy.util.DateUtil;
import com.sunc.cwy.util.Param;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AttendController extends BaseController {


    @Autowired(required = false)
    private AdminManager adminManager;

    @Autowired
    private AttendService attendService;




    /**
     * 管理员查询所有用户的今日考勤情况或者用户查看个人的考勤情况
     * @param paramsAttend
     * @param request
     * @return
     * 数据：-realName -deptId -attendType
     */
    @RequestMapping("/listAttendToday")
    public String listAttendToday(Attend paramsAttend,HttpServletRequest request){

        HttpSession session = request.getSession();

        String pageNoStr = request.getParameter("pageNo");

        int pageNo = 0;

        if (!StringUtil.isEmptyString(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }

        try {
            if (paramsAttend == null) {
                paramsAttend = new Attend();
            }

            // 设置分页信息
            int currPage = 0;
            int count = 0;

            if (pageNo != 0) {
                currPage = pageNo;
            }




        }catch (Exception e){

            e.printStackTrace();
            setErrorTip("查询考勤异常", "main.jsp", session);
            return "infoTip";
        }


        return "attendTodayShow";
    }

    public String listAttendToday(Attend paramsAttend,HttpSession session) {
        try {
            if (paramsAttend == null) {
                paramsAttend = new Attend();
            }
            //设置分页信息
            //setPagination(paramsAttend);
            //总的条数
            int[] sum = {0};
            //设置日期为本日
            paramsAttend.setAttendDate(DateUtil.getDate(DateUtil.getCurDate()));
            //用户身份限制
            User admin = (User) Param.getSession("admin");
            /*if (admin.getUser_type()==1) {
                paramsAttend.setUser_id(admin.getId());
            }*/
            //查询本日考勤是否存在
            String holiday_note = adminManager.checkAttendToday();
            Param.setAttribute("holiday_note", holiday_note);
            //查询考勤列表
            List<Attend> attends = adminManager.listAttends(paramsAttend, sum);
            Param.setAttribute("attends", attends);
            //setTotalCount(sum[0]);

            //查询部门
            Dept dept = new Dept();
            //dept.setStart(-1);
            //List<Dept> depts = adminManager.listDepts(dept, 0,0);
            //Param.setAttribute("depts", depts);

            //查询当前时间为上午or下午
            int hour = DateUtil.getHour(new Date());
            int min = DateUtil.getMin(new Date());
            if (hour >= 12 && min > 0) {
                Param.setAttribute("ap", 2);
            } else {
                Param.setAttribute("ap", 1);
            }


        } catch (Exception e) {
            e.printStackTrace();
            setErrorTip("查询考勤异常", "main.jsp", session);
            return "infoTip";
        }

        return "attendTodayShow";
    }

    /**
     * @return String
     * @Title: addAttend
     * @Description: 本日考勤打卡
     */
    public String addAttend(Attend paramsAttend,HttpSession session) {
        try {
            //本日考勤打卡
            adminManager.addAttend(paramsAttend);

            setSuccessTip("签到成功", "Admin_listAttendTodays.action", session);
        } catch (Exception e) {
            e.printStackTrace();
            setErrorTip("签到异常，后台服务器繁忙", "Admin_listAttendTodays.action", session);
        }

        return "infoTip";
    }

    /**
     * @return String
     * @Title: listAttends
     * @Description: 查询考勤记录
     */
    public String listAttends(Attend paramsAttend,HttpSession session) {
        try {
            if (paramsAttend == null) {
                paramsAttend = new Attend();
            }
            //设置分页信息
            //setPagination(paramsAttend);
            //总的条数
            int[] sum = {0};
            //用户身份限制
            User admin = (User) Param.getSession("admin");
            if (admin.getUserType() == 1) {
                paramsAttend.setUserId(admin.getId());
            }
            //考勤检测
            //adminManager.checkAttends(paramsAttend.getAttend_date1(),paramsAttend.getAttend_date2());
            //查询考勤列表
            List<Attend> attends = adminManager.listAttends(paramsAttend, sum);

            Param.setAttribute("attends", attends);
            //setTotalCount(sum[0]);

            //查询部门
            Dept dept = new Dept();
            //dept.setStart(-1);
            //List<Dept> depts = adminManager.listDepts(dept, 0,0);
            //Param.setAttribute("depts", depts);

        } catch (Exception e) {
            setErrorTip("查询考勤记录异常", "main.jsp", session);
            return "infoTip";
        }

        return "attendShow";
    }

    /**
     * @return String
     * @Title: listAttendTempsShow
     * @Description: 显示考勤信息统计页面
     */
    public String listAttendTempsShow() {
        //查询部门
        Dept dept = new Dept();
        //dept.setStart(-1);
        //List<Dept> depts = adminManager.listDepts(dept, 0,0);
        //Param.setAttribute("depts", depts);

        return "attendTempShow";
    }

    /**
     * @return String
     * @Title: listAttendTemps
     * @Description: 考勤信息统计
     */
    //@SuppressWarnings("unused")
    public String listAttendTemps(Attend paramsAttend) {
        try {
            //用户身份限制
            User admin = (User) Param.getSession("admin");
            /*if (admin.getUser_type()==1) {
                paramsAttend.setUser_id(admin.getId());
            }*/
            //考勤检测
            String _attend_date = paramsAttend.getAttendMonth();
            String _lastDay = "02,04,06,09,11";
            String start_date = _attend_date + "-01";
            String end_date = _attend_date + "-31";
            if (_attend_date.equals(DateUtil.getCurDateMonth())) {
                end_date = DateUtil.getCurDate();
            } else if (_lastDay.indexOf(_attend_date.substring(5)) != -1) {
                end_date = _attend_date + "-30";
            }
            //adminManager.checkAttends(start_date,end_date);
            //查询 考勤信息统计
            List<Attend> attendTemps = adminManager.listAttendTemps(paramsAttend);
            if (attendTemps == null || attendTemps.size() == 0) {
                //setErrorReason("暂无考勤数据");
                return "error2";
            }
            //setResult("attendTemps", attendTemps);

            //查询单个用户月视图考勤统计
            if (attendTemps.size() == 1) {
                Map<String, String> attendsMap = new HashMap<String, String>();
                Map<String, String> holidaysMap = new HashMap<String, String>();
                //查询本月用户考勤统计
                String attend_date = paramsAttend.getAttendMonth();
                paramsAttend.setAttendDate(null);
                paramsAttend.setAttendDate1(attend_date + "-01");
                paramsAttend.setAttendDate2(attend_date + "-31");
                String lastDay = "02,04,06,09,11";
                int week_middle = 31;
                if (lastDay.indexOf(attend_date.substring(5)) != -1) {
                    paramsAttend.setAttendDate2(attend_date + "-30");
                    week_middle = 30;
                }
                //paramsAttend.setStart(-1);
                List<Attend> attends = adminManager.listAttends(paramsAttend, null);
                for (Attend attend : attends) {
                    attendsMap.put(attend.getAttendDateDesc().substring(8) + "_1", attend.getAttendType1Desc());
                    attendsMap.put(attend.getAttendDateDesc().substring(8) + "_2", attend.getAttendType2Desc());
                }
                //setResult("attendsMap", attendsMap);

                //处理月视图前后空白的天数
                int week_first = DateUtil.getWeek(DateUtil.getDate(paramsAttend.getAttendDate1()));
                week_first = week_first == 1 ? 7 : week_first - 1;
                int week_last = DateUtil.getWeek(DateUtil.getDate(paramsAttend.getAttendDate2()));
                week_last = week_last == 1 ? 7 : week_last - 1;
                //setResult("week_before", week_first-1);
                //setResult("week_middle", week_middle);
                //setResult("week_after", 7-week_last);

                //节假日信息查询
                Holiday holiday = new Holiday();
                //holiday.setStart(-1);
                holiday.setHolidayDate1(paramsAttend.getAttendDate1());
                holiday.setHolidayDate2(paramsAttend.getAttendDate2());
                List<Holiday> holidays = adminManager.listHolidays(holiday, null);
                if (holidays != null) {
                    for (Holiday holiday2 : holidays) {
                        holidaysMap.put(holiday2.getHolidayDate().substring(8), holiday2.getHolidayNote());
                        attendsMap.put(holiday2.getHolidayDate().substring(8), "节假日");
                    }
                }
                //setResult("holidaysMap", holidaysMap);
            }

            //计算考勤天数统计图数据
            String[] x = {"未签到天数", "已签到天数", "迟签到天数", "早退天数", "请假天数", "离岗天数"};
            double[] y = new double[6];
            for (Attend attendTemp : attendTemps) {
                y[0] += attendTemp.getAttendType1Days();
                y[1] += attendTemp.getAttendType2Days();
                y[2] += attendTemp.getAttendType3Days();
                y[3] += attendTemp.getAttendType6Days();
                y[4] += attendTemp.getAttendType4Days();
                y[5] += attendTemp.getAttendType5Days();
            }
            //setResult("x", x);
            //setResult("y", y);

        } catch (Exception e) {
            e.printStackTrace();
            //setErrorReason("后台服务器繁忙，请稍后重试");
            return "error2";
        }

        return "success";
    }

    /**
     * @return String
     * @Title: delAttends
     * @Description: 删除考勤
     */
    public String delAttends(Attend paramsAttend,HttpSession session) {
        try {
            //删除考勤
            adminManager.delAttends(paramsAttend);

            setSuccessTip("删除考勤成功", "Admin_listAttends.action", session);
        } catch (Exception e) {
            setErrorTip("删除考勤异常", "Admin_listAttends.action", session);
        }

        return "infoTip";
    }

}
