package com.sunc.cwy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.manager.AdminManager;
import com.sunc.cwy.model.*;
import com.sunc.cwy.service.FillService;
import com.sunc.cwy.util.Param;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class FillController extends BaseController {


    @Autowired
    private FillService fillService;


    /**
     * 条件分页查询补签记录
     *
     * @param paramsFill
     * @param request
     * @return
     */
    @RequestMapping("/admin/listFills")
    public String listFills(Fill paramsFill, HttpServletRequest request) {

        HttpSession session = request.getSession();

        String pageNoStr = request.getParameter("pageNo");

        int pageNo = 0;

        if (!StringUtil.isEmptyString(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }


        try {
            if (paramsFill == null) {
                paramsFill = new Fill();
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
                // 普通用户只能查询自己的补签申请
                paramsFill.setUserId(admin.getId());
            }

            // 查询补签记录列表
            Page<Fill> page = fillService.listFills(paramsFill, currPage, count);

            List<Fill> fills = page.getRecords();

            long totalCount = page.getTotal();
            long cPage = page.getCurrent();
            long pageCount = page.getPages();
            long size = page.getSize();

            // 将参数存入域中
            request.setAttribute("fills", fills);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("pageNo", cPage);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("size", size);

            // 回填数据
            request.setAttribute("paramsFill", paramsFill);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询补签记录异常", "main.jsp", session);
            return "infoTip";
        }

        return "fillShow";
    }



    /**
     * 审核补签记录
     *
     * @param paramsFill
     * @param request
     * @return 数据：-id -fillFlag
     */
    @RequestMapping("/admin/assessFill")
    public String assessFill(Fill paramsFill, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 审核补签记录 审核通过的话，算是签到
            fillService.assessFill(paramsFill);

            setSuccessTip("审核成功", "listFills", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("审核异常", "listFills", session);
            return "infoTip";
        }

        return "infoTip";
    }


    /**
     * 跳转编辑补签记录界面
     *
     * @param id
     * @param request
     * @return 数据：-id
     */
    @RequestMapping("/admin/editFill")
    public String editFill(int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 得到补签记录
            Fill fill = fillService.getFillById(id);
            request.setAttribute("fill", fill);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询补签记录异常", "listFills", session);
            return "infoTip";
        }

        return "fillEdit";
    }

    /**
     * 保存编辑后的补签记录
     *
     * @param paramsFill
     * @param request
     * @return 数据：-id -fillDate -fillLesson -fillReason
     */
    @RequestMapping("/admin/saveFill")
    public String saveFill(Fill paramsFill, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 保存编辑补签记录
            fillService.saveFill(paramsFill);

            setSuccessTip("编辑成功", "listFills", session);

        } catch (Exception e) {

            e.printStackTrace();
            String errTip = "编辑失败";
            request.setAttribute("errTip", errTip);
            // 回填数据
            request.setAttribute("fill", paramsFill);
            return "fillEdit";
        }

        return "infoTip";
    }


    /**
     * 删除补签记录
     *
     * @param
     * @return 数据：ids
     */
    @RequestMapping("/admin/delFills")
    public String delFills(String ids, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {

            String[] fillIds = ids.split(",");
            // 删除补签记录
            fillService.delFills(fillIds);

            setSuccessTip("删除补签记录成功", "istFills", session);

        } catch (Exception e) {
            e.printStackTrace();
            setErrorTip("删除补签记录异常", "listFills", session);
            return "infoTip";
        }

        return "infoTip";
    }


    /**
     * 跳转添加补签记录页面
     *
     * @return
     */
    @RequestMapping("/admin/addFillShow")
    public String addFillShow() {
        return "fillEdit";
    }

    /**
     * 添加补签记录
     *
     * @param request
     * @return 数据：-fillDate -fillLesson -fillReason
     */
    @RequestMapping("/admin/addFill")
    public String addFill(Fill paramsFill, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {

            User admin = getAdmin(session);
            if (admin != null) {
                paramsFill.setUserId(admin.getId());
            }
            paramsFill.setFillFlag(1);

            // 添加补签记录
            fillService.addFill(paramsFill);

            setSuccessTip("添加补签申请成功，请等待审核", "listFills", session);

        } catch (Exception e) {
            e.printStackTrace();
            setErrorTip("添加补签记录异常", "listFills", session);
            return "infoTip";
        }

        return "infoTip";
    }

}
