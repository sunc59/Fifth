package com.sunc.cwy.controller.admin;

import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.model.User;
import com.sunc.cwy.service.UserService;
import com.sunc.cwy.util.Md5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 个人中心信息控制器
 *
 * @author sunc
 */
@Controller
public class UserCenterController extends BaseController {


    @Autowired
    private UserService userService;


    /**
     * @return String
     * @Title: saveAdmin
     * @Description: 保存修改个人信息
     * 数据：-id -realName -userSex
     */
    @RequestMapping("/admin/saveUserInfo")
    public String saveAdmin(User paramsUser, HttpServletRequest request) {

        HttpSession session = request.getSession();

        User user = userService.getUserById(paramsUser.getId());

        user.setRealName(paramsUser.getRealName());
        user.setUserSex(paramsUser.getUserSex());

        try {
            // 验证用户会话是否失效
            if (!validateAdmin(session)) {
                return "loginTip";
            }
            // 保存修改个人信息
            User admin = userService.saveUserInfo(user);

            // 更新session
            if (admin != null) {
                session.setAttribute("admin", admin);
            }

            setSuccessTip("编辑成功", "modifyInfo.jsp", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("编辑异常", "modifyInfo.jsp", session);

            return "infoTip";

        }

        return "infoTip";
    }


    /**
     * @return String
     * @Title: saveAdminPass
     * @Description: 保存修改个人密码
     * 数据：-id -userPass
     */
    @RequestMapping("/admin/saveUserPass")
    public String saveAdminPass(User paramsUser, HttpServletRequest request) {

        HttpSession session = request.getSession();

        User user = userService.getUserById(paramsUser.getId());

        user.setUserPass(Md5.makeMd5(paramsUser.getUserPass()));

        try {
            // 验证用户会话是否失效
            if (!validateAdmin(session)) {
                return "loginTip";
            }
            // 保存修改个人密码
            User admin = userService.saveUserPass(user);

            // 更新session
            if (admin != null) {
                session.setAttribute("admin", admin);
            }

            setSuccessTip("修改成功", "modifyPwd.jsp", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("修改异常", "modifyPwd.jsp", session);
            return "infoTip";
        }

        return "infoTip";
    }

}
