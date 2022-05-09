package com.sunc.cwy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.manager.AdminManager;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.User;
import com.sunc.cwy.service.DeptService;
import com.sunc.cwy.service.PostService;
import com.sunc.cwy.service.UserService;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController extends BaseController {


    @Autowired(required = false)
    private UserService userService;

    @Autowired(required = false)
    private DeptService deptService;


    /**
     * 条件分页查询员工信息列表
     *
     * @param paramsUser
     * @param request
     * @return 参数：-userNo -realName -deptId -pageNo -size
     */
    @RequestMapping("/admin/listUsers")
    public String listUsers(User paramsUser, HttpServletRequest request) {

        String pageNoStr = request.getParameter("pageNo");

        HttpSession session = request.getSession();

        int pageNo = 0;

        if (!StringUtil.isEmptyString(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }

        try {
            if (paramsUser == null) {
                paramsUser = new User();
            }

            // 设置分页信息
            int currPage = 0;
            int count = 0;

            if (pageNo != 0) {
                currPage = pageNo;
            }

            // 分页条件查询
            Page<User> page = userService.listUsers(paramsUser, currPage, count);

            List<User> users = page.getRecords();
            // 填充部门信息
            for (User user : users) {
                Dept dept = deptService.getDeptById(user.getDeptId());
                user.setDept(dept);
            }

            long totalCount = page.getTotal();
            long cPage = page.getCurrent();
            long pageCount = page.getPages();
            long size = page.getSize();


            // 将参数存入request
            request.setAttribute("users", users);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("pageNo", cPage);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("size", size);

            // 回填数据
            request.setAttribute("paramsUser", paramsUser);


        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询用户异常", "main.jsp", session);
            return "infoTip";
        }

        return "userShow";
    }


    /**
     * 跳转编辑用户界面
     *
     * @param id
     * @param request
     * @return 参数：-id
     */
    @RequestMapping("/admin/editUser")
    public String editUser(int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 校验数据 id
            // TODO

            // 获得用户
            User user = userService.getUserById(id);
            request.setAttribute("user", user);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询用户异常", "listUsers", session);
            return "infoTip";
        }

        return "userEdit";
    }


    /**
     * 保存用户信息
     *
     * @param paramsUser
     * @param request
     * @return 参数：-id -userPass -realName -userSex -deptId
     */
    @RequestMapping("/admin/saveUser")
    public String saveUser(User paramsUser, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 校验数据 id等
            // TODO

            // 保存编辑用户
            User user = userService.saveUser(paramsUser);

            setSuccessTip("编辑成功", "listUsers", session);

        } catch (Exception e) {

            e.printStackTrace();
            String errTip = "编辑失败";
            request.setAttribute("errTip", errTip);
            request.setAttribute("paramsUser", paramsUser);

            return "userEdit";
        }

        return "infoTip";
    }


    /**
     * @return String
     * @Title: delUsers
     * @Description: 删除用户
     * 数据 -ids
     */
    @RequestMapping("/admin/delUsers")
    public String delUsers(String ids, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {

            // 参数校验，检查用户id是否正确，并且当前用户是否是管理员用户
            // TODO

            String[] userIds = ids.split(",");

            // 删除用户
            userService.delUsers(userIds);

            setSuccessTip("删除用户成功", "listUsers", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("删除用户异常", "listUsers", session);
            return "infoTip";
        }

        return "infoTip";
    }


    /**
     * 跳转添加用户页面
     *
     * @param request
     * @return 参数：-无参数
     */
    @RequestMapping("/admin/addUserShow")
    public String addUserShow(HttpServletRequest request) {

        return "userEdit";
    }


    /**
     * 添加用户
     *
     * @param paramsUser
     * @param request
     * @return 参数：-userNo -userName -userPass -realName -userSex -deptId
     */
    @RequestMapping("/admin/addUser")
    public String addUser(User paramsUser, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {

            // 检查员工编号是否存在
            User user = userService.getUserByNo(paramsUser.getUserNo());

            if (user != null) {
                String errTip = "失败，该员工编号已经存在！";
                request.setAttribute("errTip", errTip);
                // 回填参数
                request.setAttribute("user", paramsUser);

                return "userEdit";
            }

            // 检查用户名是否存在
            User user2 = userService.getUserByName(paramsUser.getUserName());

            if (user2 != null) {
                String errTip = "失败，该用户名已经存在！";
                request.setAttribute("errTip", errTip);
                // 回填参数
                request.setAttribute("user", paramsUser);

                return "userEdit";
            }

            // 添加用户
            userService.addUser(paramsUser);

            setSuccessTip("添加成功", "listUsers", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("添加用户异常", "listUsers", session);
            return "infoTip";
        }

        return "infoTip";
    }


}
