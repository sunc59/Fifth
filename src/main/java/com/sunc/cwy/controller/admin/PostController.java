package com.sunc.cwy.controller.admin;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.controller.common.BaseController;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.Post;
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

/**
 * @author sunc
 */
@Controller
public class PostController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private PostService postService;


    /**
     * 非分页条件查询
     *
     * @return String
     * @Title: listPosts
     * @Description: 查询离岗申请
     */
    public String listPosts(HttpServletRequest request) {

        HttpSession session = request.getSession();

        User admin = getAdmin(session);

        try {
            // 校验当前登录用户
            // 普通用户，查询当前用户的记录 admin.getUserType() == 1
            // 管理员，查询当前用户的记录 admin.getUserType() == 2

            // 查询离岗申请记录
            List<Post> posts = postService.listPosts(admin.getId());

            request.setAttribute("posts", posts);

        } catch (Exception e) {

            setErrorTip("查询离岗申请异常", "main.jsp", session);
            return "infoTip";
        }

        return "postShow";
    }


    /**
     * 条件分页查询离岗申请列表
     *
     * @param paramsPost
     * @param request
     * @return 参数
     * 管理员-postDate1-postDate2-realName-deptId-postFlag
     * 普通用户-postDate1-postDate2-postFlag
     */
    @RequestMapping("/admin/listPosts")
    public String listPosts(Post paramsPost, HttpServletRequest request) {

        String pageNoStr = request.getParameter("pageNo");

        HttpSession session = request.getSession();

        int pageNo = 0;

        if (!StringUtil.isEmptyString(pageNoStr)) {
            pageNo = Integer.parseInt(pageNoStr);
        }

        try {

            if (paramsPost == null) {
                paramsPost = new Post();
            }

            //设置分页信息
            int currPage = 0;
            int count = 0;

            if (pageNo != 0) {
                currPage = pageNo;
            }

            Page<Post> page = postService.listPosts(paramsPost, currPage, count);

            List<Post> posts = page.getRecords();
            // 填充部门和真实姓名数据
            for (Post post : posts) {
                User user = userService.getUserById(post.getUserId());
                Dept dept = deptService.getDeptById(user.getDeptId());
                post.setRealName(user.getRealName());
                post.setDeptName(dept.getDeptName());
            }

            long totalCount = page.getTotal();
            long cPage = page.getCurrent();
            long pageCount = page.getPages();
            long size = page.getSize();

            // 将参数存入request
            request.setAttribute("posts", posts);
            request.setAttribute("totalCount", totalCount);
            request.setAttribute("pageNo", cPage);
            request.setAttribute("pageCount", pageCount);
            request.setAttribute("size", size);

            // 回填数据
            request.setAttribute("paramsPost", paramsPost);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("查询离岗申请异常", "main.jsp", session);
            return "infoTip";
        }

        return "postShow";
    }


    /**
     * 管理员端
     *
     * @return String
     * @Title: assessPost
     * @Description: 审核离岗申请
     * <p>
     * 传递参数
     * -id -postFlag
     */
    @RequestMapping("/admin/assessPost")
    public String assessPost(int id, int postFlag, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 校验参数数据 id是否存在 postFlag是否合理
            // TODO

            // 审核离岗申请
            postService.assessPost(id, postFlag);

            setSuccessTip("审核成功", "listPosts", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("审核异常", "listPosts", session);
            return "infoTip";
        }

        return "infoTip";
    }


    /**
     * 客户端
     *
     * @return String
     * @Title: editPost
     * @Description: 编辑离岗申请
     * 传递参数
     * -id
     */
    @RequestMapping("/admin/editPost")
    public String editPost(int id, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 校验离岗id是否属于当前登录用户
            // TODO

            // 获取离岗申请
            Post post = postService.getPost(id);
            request.setAttribute("paramsPost", post);

        } catch (Exception e) {
            e.printStackTrace();
            setErrorTip("查询离岗申请异常", "listPosts", session);
            return "infoTip";
        }

        return "postEdit";
    }


    /**
     * 客户端
     *
     * @return String
     * @Title: savePost
     * @Description: 保存编辑离岗申请
     * 传递数据
     * -id-postDate1-postDate2-postLesson1-postLesson2-postReason
     */
    @RequestMapping("/admin/savePost")
    public String savePost(Post paramsPost, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {
            // 校验离岗申请是否属于当前登录用户以及参数是否齐全和符合条件
            // TODO

            // 保存编辑离岗申请
            paramsPost.setUserId(getAdmin(session).getId());
            postService.savePost(paramsPost);

            setSuccessTip("编辑成功", "listPosts", session);

        } catch (Exception e) {

            e.printStackTrace();
            String errTip = "编辑失败";
            request.setAttribute("errTip", errTip);
            request.setAttribute("paramsPost", paramsPost);
            return "postEdit";
        }

        return "infoTip";
    }


    /**
     * 用户端
     *
     * @return String
     * @Title: addPostShow
     * @Description: 显示添加离岗申请页面
     */
    @RequestMapping("/admin/addPostShow")
    public String addPostShow() {
        return "postEdit";
    }

    /**
     * 用户端
     *
     * @return String
     * @Title: addPost
     * @Description: 添加离岗申请
     * <p>
     * 传递数据-postDate1-postLesson1-postDate2-postLesson2-postReason
     */
    @RequestMapping("/admin/addPost")
    public String addPost(Post paramsPost, HttpServletRequest request) {

        System.out.println(paramsPost);

        HttpSession session = request.getSession();

        try {
            // 校验离岗申请是否属于当前登录用户
            // TODO

            // 添加离岗申请
            postService.addPost(getAdmin(session), paramsPost);

            setSuccessTip("添加离岗申请成功，请等待审核", "listPosts", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("添加离岗申请异常", "listPosts", session);
        }

        return "infoTip";
    }

    /**
     * @return String
     * @Title: delPosts
     * @Description: 删除离岗申请
     * 传递参数
     * -ids(String)
     */
    @RequestMapping("/admin/delPosts")
    public String delPosts(String ids, HttpServletRequest request) {

        HttpSession session = request.getSession();

        try {

            // id和当前登录用户数据校验，校验离岗id是否属于当前登录用户
            // TODO

            String[] postIds = ids.split(",");

            // 删除离岗申请
            postService.delPosts(postIds);

            setSuccessTip("删除离岗申请成功", "listPosts", session);

        } catch (Exception e) {

            e.printStackTrace();
            setErrorTip("删除离岗申请异常", "listPosts", session);
            return "infoTip";
        }

        return "infoTip";
    }

}
