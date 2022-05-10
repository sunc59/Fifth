package com.sunc.cwy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.manager.AdminManager;
import com.sunc.cwy.model.*;
import com.sunc.cwy.service.DeptService;
import com.sunc.cwy.service.LeaveService;
import com.sunc.cwy.service.UserService;
import com.sunc.cwy.util.Param;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class LeaveController extends BaseController {


    @Autowired
    private LeaveService leaveService;

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;



    /**
     * 条件分页查询请假记录
     *
     * @param paramsLeave
     * @param request
     * @return 数据：-leaveDate1 -leaveDate2 -realName -deptId -leaveFlag
     */
    @RequestMapping("/admin/listLeaves")
    public String listLeaves(Uleave paramsLeave, HttpServletRequest request) {

        HttpSession session = request.getSession();

        String pageNoStr = request.getParameter("pageNo");

        int pageNo = 0;

        if (!StringUtil.isEmptyString(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }


        try {
            if (paramsLeave == null) {
                paramsLeave = new Uleave();
            }

            // 设置分页信息
            int currPage = 0;
            int count = 0;

            if (pageNo != 0) {
                currPage = pageNo;
            }

            // 用户身份限制 1是普通用户 2是管理员
            User admin = getAdmin(session);
            if (admin.getUserType() == 1) {
                // 普通用户只能查询自己的请假申请
                paramsLeave.setUserId(admin.getId());
            }

            // 查询请假申请列表
            Page<Uleave> page = leaveService.listLeaves(paramsLeave, currPage, count);

            List<Uleave> leaves = page.getRecords();
            // 填充用户和部门信息
            for (Uleave leave : leaves) {
                int userId = leave.getUserId();
                User user = userService.getUserById(userId);
                leave.setUser(user);
                Dept dept = deptService.getDeptById(user.getDeptId());
                leave.setDeptName(dept.getDeptName());
            }

            long totalCount = page.getTotal();
            long cPage = page.getCurrent();
            long pageCount = page.getPages();
            long size = page.getSize();

            // 将参数存入域中
            request.setAttribute("leaves", leaves);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("pageNo", cPage);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("size", size);

            // 回填数据
            request.setAttribute("paramsLeave", paramsLeave);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询请假申请异常", "main.jsp", session);
            return "infoTip";
        }

        return "leaveShow";
    }



    /**
     * 审核请假申请
     * @param paramsLeave
     * @param request
     * @return
     * 数据：-id -leaveFlag
     */
    @RequestMapping("/admin/assessLeave")
    public String assessLeave(Uleave paramsLeave,HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 审核请假申请
            leaveService.assessLeave(paramsLeave);

            setSuccessTip("审核成功", "listLeaves", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("审核异常", "listLeaves", session);
            return "infoTip";
        }

        return "infoTip";
    }


    /**
     * 跳转编辑请假申请的页面
     *
     * @param id
     * @param request
     * @return 数据：id
     */
    @RequestMapping("/admin/editLeave")
    public String editLeave(int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 得到请假申请
            Uleave leave = leaveService.getLeaveById(id);
            // 传递要编辑的leave数据
            request.setAttribute("leave", leave);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询请假申请异常", "listLeaves", session);
            return "infoTip";
        }

        return "leaveEdit";
    }

    /**
     * 保存编辑请假申请
     *
     * @param paramsLeave
     * @param request
     * @return 数据：-id -leaveDate1 -leaveLesson1 -leaveDate2 -leaveLesson2 -leaveReason -leaveType
     */
    @RequestMapping("/admin/saveLeave")
    public String saveLeave(Uleave paramsLeave, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 保存编辑请假申请
            Uleave leave = leaveService.getLeaveById(paramsLeave.getId());
            paramsLeave.setUserId(leave.getUserId());
            paramsLeave.setLeaveFlag(leave.getLeaveFlag());

            leaveService.saveLeave(paramsLeave);
            setSuccessTip("编辑成功", "listLeaves", session);

        } catch (Exception e) {
            e.printStackTrace();
            String errTip = "编辑失败";
            request.setAttribute("errTip", errTip);
            // 回填数据
            request.setAttribute("leave", paramsLeave);
            return "leaveEdit";
        }

        return "infoTip";
    }


    /**
     * 删除请假申请
     *
     * @param ids
     * @param request
     * @return 参数：ids
     */
    @RequestMapping("/admin/delLeaves")
    public String delLeaves(String ids, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 删除请假申请
            String[] leaveIds = ids.split(",");
            leaveService.delLeaves(leaveIds);
            setSuccessTip("删除请假申请成功", "listLeaves", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("删除请假申请异常", "listLeaves", session);
            return "infoTip";
        }

        return "infoTip";
    }


    /**
     * 跳转添加请假的页面
     *
     * @return 参数：无参数
     */
    @RequestMapping("/admin/addLeaveShow")
    public String addLeaveShow() {
        return "leaveEdit";
    }

    /**
     * 添加请假申请
     *
     * @param paramsLeave
     * @param request
     * @return 参数：-id -leaveType -leaveDate1 -leaveLesson1 -leaveDate2 -leaveLesson2 -leaveReason
     */
    @RequestMapping("/admin/addLeave")
    public String addLeave(Uleave paramsLeave, HttpServletRequest request) {

        HttpSession session = request.getSession();

        User user = getAdmin(session);

        // 校验请假数据是否已经被请假过

        if (user == null) {
            String errTip = "申请请假失败，请联系管理员！";
            request.setAttribute("errTip", errTip);
            // 回填数据
            request.setAttribute("leave", paramsLeave);
            return "leaveEdit";
        }

        paramsLeave.setUserId(user.getId());
        paramsLeave.setUser(user);

        try {
            // 添加请假申请
            leaveService.addLeave(paramsLeave);

            setSuccessTip("添加请假申请成功，请等待审核", "listLeaves", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("添加请假申请异常", "listLeaves", session);
            return "infoTip";
        }

        return "infoTip";
    }
}
