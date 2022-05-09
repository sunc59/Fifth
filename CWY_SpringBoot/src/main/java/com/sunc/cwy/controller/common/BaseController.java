package com.sunc.cwy.controller.common;


import com.sunc.cwy.model.User;

import javax.servlet.http.HttpSession;

public abstract class BaseController {

    protected User getAdmin(HttpSession session) {
        return (User) session.getAttribute("admin");
    }

    /**
     * @return boolean
     * @Title: validateAdmin
     * @Description: admin登录验证
     */
    protected boolean validateAdmin(HttpSession session) {
        //User admin = (User)Param.getSession("admin");
        User admin = (User) session.getAttribute("admin");
        return admin != null;
    }

    protected void setSuccessTip(String tip, String url, HttpSession session) {
        session.setAttribute("tipType", "success");
        session.setAttribute("tip", tip);
        session.setAttribute("url1", url);
        session.setAttribute("value1", "确 定");
    }

    protected void setErrorTip(String tip, String url, HttpSession session) {
        session.setAttribute("tipType", "error");
        session.setAttribute("tip", tip);
        session.setAttribute("url1", url);
        session.setAttribute("value1", "确 定");
    }


}
