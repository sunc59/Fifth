package com.sunc.cwy.controller;

import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.User;
import com.sunc.cwy.service.DeptService;
import com.sunc.cwy.service.UserService;
import com.sunc.cwy.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author sunc
 */
@Controller
public class LoginController {


    @Autowired
    private DeptService deptService;

    @Autowired
    private UserService userService;



    @RequestMapping("/admin/hello")
    public String hello(){
        System.out.println("hello");
        return "login";
    }

    /**
     * @return String
     * @Title: InSystem
     * @Description: 用户登录
     * 数据：
     *      -userName
     *      -userPass
     *      -random
     */
    @RequestMapping("/admin/login")
    public String login(User paramsUser, HttpServletRequest request) {

        HttpSession session = request.getSession();

        String errTip = "";

        try {
            // 验证码验证
            String random = (String) session.getAttribute("random");

            if (!random.equals(paramsUser.getRandom())) {
                errTip = "验证码错误";
                request.setAttribute("errTip", errTip);
                return "login";
            }

            // 用户登录查询
            User admin = userService.getUser(paramsUser);

            if (admin != null) {
                session.setAttribute("admin", admin);
            } else {
                errTip = "用户名或密码错误";
                request.setAttribute("errTip", errTip);
                return "login";
            }

            // 存部门列表信息
            List<Dept> depts = deptService.listDepts();
            session.setAttribute("depts", depts);



        } catch (Exception e) {
            e.printStackTrace();
            errTip = "登录异常，请稍后重试";
            request.setAttribute("errTip", errTip);
            return "login";
        }

        return "redirect:/admin/index.jsp";
    }


    /**
     * @return String
     * @Title: OutSystem
     * @Description: 退出登录
     */
    @RequestMapping("/admin/logout")
    public String logout(HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 用户查询
            User user = (User) session.getAttribute("admin");
            if (user != null) {
                // 退出登录
                session.invalidate();
            }

        } catch (Exception e) {

            e.printStackTrace();
            return "redirect:/admin/login.jsp";
        }

        return "redirect:/admin/login.jsp";
    }


    /**
     * @return String
     * @Title: RegSystem
     * @Description: 用户注册
     */
    @RequestMapping("/admin/register")
    @ResponseBody
    public Object register(User paramsUser, HttpServletRequest request, HttpServletResponse response) {

        HttpSession session = request.getSession();

        JsonResult<Void> result = new JsonResult<>();

        try {
            // 查询验证码
            String random = (String) session.getAttribute("random");
            if (!random.equals(paramsUser.getRandom())) {
                result.setMessage("验证码不正确");
                result.setState(0);
                return result;
            }

            // 查询用户名是否被占用
            User user = userService.getUserByName(paramsUser.getUserName());
            if (user != null){
                result.setMessage("注册失败，用户名已被注册");
                result.setState(0);
                return result;
            }

            // 补充用户数据 -用户编号就是testXX的XX，-用户部门放入临时部门7，-用户类型1
            String userName = paramsUser.getUserName();
            String userNo = UserNoUtil.userName2userNo(userName);
            paramsUser.setUserNo(userNo);
            paramsUser.setDeptId(7);
            paramsUser.setUserType(1);

            // 添加用户入库
            userService.addUser(paramsUser);

            result.setMessage("注册成功");
            result.setState(1);

        } catch (Exception e) {
            e.printStackTrace();
            result.setMessage("注册异常，请稍后重试");
            result.setState(0);
            return result;
        }

        return result;
    }
}
