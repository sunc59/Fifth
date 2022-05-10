package com.sunc.cwy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.service.DeptService;
import com.sunc.cwy.service.UserService;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author sunc
 * 部门信息管理控制器
 */
@Controller
public class DeptController extends BaseController {


    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;


    /**
     * 条件分页查询部门列表
     *
     * @param paramsDept
     * @param request
     * @return 参数：-pageNo -deptName
     */
    @RequestMapping("/admin/listDepts")
    public String listDepts(Dept paramsDept, HttpServletRequest request) {

        HttpSession session = request.getSession();

        String pageNoStr = request.getParameter("pageNo");

        int pageNo = 0;

        if (!StringUtil.isEmptyString(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }

        try {
            if (paramsDept == null) {
                paramsDept = new Dept();
            }

            // 设置分页信息
            int currPage = 0;
            int count = 0;

            if (pageNo != 0) {
                currPage = pageNo;
            }

            // 查询部门列表
            Page<Dept> page = deptService.listDepts(paramsDept, currPage, count);

            List<Dept> depts = page.getRecords();

            long totalCount = page.getTotal();
            long cPage = page.getCurrent();
            long pageCount = page.getPages();
            long size = page.getSize();

            // 将参数存入session
            session.setAttribute("depts", depts);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("pageNo", cPage);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("size", size);

            // 回填数据
            request.setAttribute("paramsDeptName", paramsDept.getDeptName());

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询部门异常", "main.jsp", session);
            return "infoTip";
        }

        return "deptShow";
    }


    /**
     * 跳转编辑部门页面
     *
     * @param id
     * @param request
     * @return 参数：-id
     */
    @RequestMapping("/admin/editDept")
    public String editDept(int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 根据id获取部门
            Dept dept = deptService.getDeptById(id);

            request.setAttribute("dept", dept);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询部门异常", "listDepts", session);
            return "infoTip";
        }

        return "deptEdit";
    }


    /**
     * 保存部门信息
     *
     * @param paramsDept
     * @param request
     * @return 参数：-id -deptName -deptNote
     */
    @RequestMapping("/admin/saveDept")
    public String saveDept(Dept paramsDept, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {

            // 检查部门是否存在
            Dept dept = deptService.getDeptByName(paramsDept.getDeptName());

            if (dept != null && dept.getId().intValue() != paramsDept.getId()) {
                String errTip = "失败，该部门已经存在！";
                request.setAttribute("dept", paramsDept);
                request.setAttribute("errTip", errTip);
                return "deptEdit";
            }

            // 保存编辑部门
            deptService.saveDept(paramsDept);

            setSuccessTip("编辑成功", "listDepts", session);

        } catch (Exception e) {

            String errTip = "编辑失败";
            request.setAttribute("errTip", errTip);
            // 回填数据
            request.setAttribute("dept", paramsDept);
            return "deptEdit";
        }
        return "infoTip";
    }

    /**
     * 批量删除部门
     *
     * @param ids
     * @param request
     * @return 参数：-ids
     */
    @RequestMapping("/admin/delDepts")
    public String delDepts(String ids, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 判断部门下是否有用户
            String[] deptIds = ids.split(",");
            for (String id : deptIds) {

                int count = userService.listUsersByDid(Integer.parseInt(id)).size();

                if (count > 0) {
                    setErrorTip("删除部门异常，部门下面还有用户存在", "listDepts", session);
                    return "infoTip";
                }
            }

            // 删除部门
            deptService.delDepts(deptIds);

            setSuccessTip("删除部门成功", "listDepts", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("删除部门异常", "listDepts", session);
            return "infoTip";
        }
        return "infoTip";
    }


    /**
     * 跳转新增部门页面
     *
     * @param request
     * @return 参数：-无参数
     */
    @RequestMapping("/admin/addDeptShow")
    public String addDeptShow(HttpServletRequest request) {

        return "deptEdit";
    }


    /**
     * 新增部门
     *
     * @param paramsDept
     * @param request
     * @return 参数：-deptName -deptNote
     */
    @RequestMapping("/admin/addDept")
    public String addDept(Dept paramsDept, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 检查部门名称是否存在
            Dept dept = deptService.getDeptByName(paramsDept.getDeptName());

            // 部门名称存在
            if (dept != null) {
                String errTip = "失败，该部门已经存在！";
                // 回填数据
                request.setAttribute("dept", paramsDept);
                // 错误信息
                request.setAttribute("errTip", errTip);
                return "deptEdit";
            }

            // 部门名称不存在，添加部门
            deptService.addDept(paramsDept);

            setSuccessTip("添加成功", "listDepts", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("添加部门异常", "listDepts", session);
            return "infoTip";
        }

        return "infoTip";
    }

}
