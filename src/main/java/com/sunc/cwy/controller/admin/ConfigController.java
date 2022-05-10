package com.sunc.cwy.controller.admin;

import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.manager.AdminManager;
import com.sunc.cwy.model.Config;
import com.sunc.cwy.service.ConfigService;
import com.sunc.cwy.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ConfigController extends BaseController {


    @Autowired
    private ConfigService configService;


    /**
     * 查询考勤时间
     * 勤时间只设置为一个吧，毕竟每天的考勤时间是一样的，而且也没有考勤时间配置生效的选项
     *
     * @param request
     * @return 参数：无参数
     */
    @RequestMapping("/admin/listConfig")
    public String listConfig(HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            Config config = configService.getConfig();
            request.setAttribute("config", config);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询考勤时间异常", "main.jsp", session);
            return "infoTip";
        }

        return "configShow";
    }

    /**
     * 保存考勤时间配置
     * @param paramsConfig
     * @param request
     * @return
     * 参数：-id -configDate1 -configDate2
     */
    @RequestMapping("/admin/saveConfig")
    public String saveConfig(Config paramsConfig, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            //保存编辑考勤时间
            configService.updateConfig(paramsConfig);

            setSuccessTip("更新成功", "listConfig", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("更新考勤时间异常", "main.jsp", session);
            return "infoTip";
        }

        return "infoTip";
    }

}
