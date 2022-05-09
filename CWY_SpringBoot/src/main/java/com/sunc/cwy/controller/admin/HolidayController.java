package com.sunc.cwy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.manager.AdminManager;
import com.sunc.cwy.model.*;
import com.sunc.cwy.service.HolidayService;
import com.sunc.cwy.util.Param;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class HolidayController extends BaseController {


    @Autowired
    private HolidayService holidayService;


    /**
     * 查询节假日
     *
     * @param paramsHoliday
     * @param request
     * @return 参数：-holidayDate1 -holidayDate2 -holidayNote
     */
    @RequestMapping("/admin/listHolidays")
    public String listHolidays(Holiday paramsHoliday, HttpServletRequest request) {

        HttpSession session = request.getSession();

        String pageNoStr = request.getParameter("pageNo");

        int pageNo = 0;

        if (!StringUtil.isEmptyString(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }

        try {
            if (paramsHoliday == null) {
                paramsHoliday = new Holiday();
            }

            // 设置分页信息
            int currPage = 0;
            int count = 0;

            if (pageNo != 0) {
                currPage = pageNo;
            }

            // 查询节假日列表
            Page<Holiday> page = holidayService.listHolidays(paramsHoliday, currPage, count);

            List<Holiday> holidays = page.getRecords();

            long totalCount = page.getTotal();
            long cPage = page.getCurrent();
            long pageCount = page.getPages();
            long size = page.getSize();

            // 将参数存入域中
            request.setAttribute("holidays", holidays);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("pageNo", cPage);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("size", size);

            // 回填数据
            request.setAttribute("paramsHoliday", paramsHoliday);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询节假日异常", "main.jsp", session);
            return "infoTip";

        }

        return "holidayShow";
    }


    /**
     * 编辑节假日
     *
     * @param id
     * @param request
     * @return 参数：-id
     */
    @RequestMapping("/admin/editHoliday")
    public String editHoliday(int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 获得节假日
            Holiday holiday = holidayService.getHolidayById(id);
            // 传递要编辑的holiday数据
            request.setAttribute("holiday", holiday);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询节假日异常", "listHolidays", session);
            return "infoTip";
        }

        return "holidayEdit";
    }

    /**
     * 保存编辑节假日
     *
     * @param request
     * @return 参数：-id -holidayNote
     */
    @RequestMapping("/admin/saveHoliday")
    public String saveHoliday(Holiday paramsHoliday, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 保存编辑节假日
            holidayService.saveHoliday(paramsHoliday);
            setSuccessTip("编辑成功", "listHolidays", session);

        } catch (Exception e) {

            e.printStackTrace();
            String errTip = "编辑失败";
            request.setAttribute("errTip", errTip);
            // 回填数据
            request.setAttribute("holiday", paramsHoliday);
            return "holidayEdit";
        }

        return "infoTip";
    }


    /**
     * 删除节假日
     *
     * @param ids
     * @param request
     * @return 参数：-ids
     */
    @RequestMapping("/admin/delHolidays")
    public String delHolidays(String ids, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 删除节假日
            String[] holidayIds = ids.split(",");
            holidayService.delHolidays(holidayIds);
            setSuccessTip("删除节假日成功", "listHolidays", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("删除节假日异常", "listHolidays", session);
            return "infoTip";
        }

        return "infoTip";
    }


    /**
     * 跳转添加节假日页面
     *
     * @return 参数：无参数
     */
    @RequestMapping("/admin/addHolidayShow")
    public String addHolidayShow() {
        return "holidayEdit";
    }


    /**
     * 添加节假日
     *
     * @param paramsHoliday
     * @param request
     * @return 参数：-holidayDate1 -holidayDate2 -holidayNote
     */
    @RequestMapping("/admin/addHoliday")
    public String addHoliday(Holiday paramsHoliday, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 检查节假日是否存在
            String date1 = paramsHoliday.getHolidayDate1();
            String date2 = paramsHoliday.getHolidayDate2();
            List<Holiday> holidays = holidayService.getHoliday(date1, date2);

            if (holidays != null && holidays.size() > 0) {
                String errTip = "失败，该日期段内的节假日已经存在！";
                request.setAttribute("errTip", errTip);
                // 回填数据
                request.setAttribute("holiday", paramsHoliday);
                return "holidayEdit";
            }

            // 添加节假日
            holidayService.addHoliday(paramsHoliday);

            setSuccessTip("添加节假日成功", "listHolidays", session);

        } catch (Exception e) {
            e.printStackTrace();
            setErrorTip("添加节假日异常", "listHolidays", session);
            return "infoTip";
        }

        return "infoTip";
    }
}
