package com.sunc.cwy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.mapper.DeptMapper;
import com.sunc.cwy.mapper.PostMapper;
import com.sunc.cwy.mapper.UserMapper;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.Post;
import com.sunc.cwy.model.User;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author sunc
 */
@Service
public class PostService {

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private PostMapper postMapper;

    @Autowired(required = false)
    private DeptMapper deptMapper;


    /**
     * 用户端
     * 添加离岗申请
     *
     * @param user
     * @param post 传递数据
     * 数据：-userId
     * 数据：-postDate1-postLesson1-postDate2-postLesson2-postReason
     */
    public void addPost(User user, Post post) {

        // 后端校验参数数据是否正确
        // TODO

        // 补充数据
        // 离岗人id
        post.setUserId(user.getId());
        // 离岗状态
        post.setPostFlag(1);
        // 添加离岗记录
        postMapper.insert(post);
    }


    /**
     * 根据id查询离职申请
     *
     * @param id
     * @return
     */
    public Post getPostById(int id) {
        return postMapper.selectById(id);
    }


    /**
     * 审核离岗申请，待审核1-通过2-驳回3
     *
     * @param id
     * @param postFlag
     */
    public void assessPost(int id, int postFlag) {

        Post post = postMapper.selectById(id);

        // 参数校验
        if (post != null && post.getPostFlag() == 1) {

            post.setPostFlag(postFlag);
            postMapper.updateById(post);
        }
    }


    /**
     * 用户端-管理员端
     * 批量删除记录
     *
     * @param ids
     */
    public void delPosts(String[] ids) {
        postMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 用户端
     * 保存编辑离职申请
     *
     * @param paramsPost
     */
    public void savePost(Post paramsPost) {
        postMapper.updateById(paramsPost);
    }


    /**
     * 非条件分页查询离岗申请列表
     *
     * @param uid
     * @return
     */
    public List<Post> listPosts(Integer uid) {

        User user = userMapper.selectById(uid);

        QueryWrapper<Post> wrapper = new QueryWrapper<>();

        if (user != null && user.getUserType() == 1) {
            // 普通用户
            wrapper.eq("user_id", uid);
        }

        List<Post> posts = postMapper.selectList(wrapper);

        // 填充离职申请的姓名和部门名
        for (Post post : posts) {
            User u = userMapper.selectById(post.getUserId());
            Dept dept = deptMapper.selectById(u.getDeptId());
            u.setDeptName(dept.getDeptName());
            post.setUser(u);
        }

        return posts;
    }


    /**
     * 条件分页查询离岗申请
     *
     * @param paramsPost
     * @param currPage
     * @param count
     * @return 数据
     * -管理员-postDate1-postDate2-realName-deptId-postFlag
     * -普通用户-postDate1-postDate2-postFlag
     */
    public Page<Post> listPosts(Post paramsPost, int currPage, int count) {

        String postDate1 = paramsPost.getPostDate1();
        String postDate2 = paramsPost.getPostDate2();
        Integer postFlag = paramsPost.getPostFlag();
        String realName = paramsPost.getRealName();
        int deptId = paramsPost.getDeptId();
        int userId = paramsPost.getUserId();

        QueryWrapper<Post> wrapper = new QueryWrapper<>();

        if (userId != 0) {
            wrapper.eq("user_id", userId);
        }

        // 数据库中的日期要在两个日期中间
        if (!StringUtil.isEmptyString(postDate1) && !StringUtil.isEmptyString(postDate2)) {

            wrapper.ge("post_date1", postDate1);
            wrapper.le("post_date2", postDate2);
        }

        // 审核状态
        if (postFlag != null && postFlag != 0) {
            wrapper.eq("post_flag", paramsPost.getPostFlag());
        }

        // 管理员可以查询用户姓名
        if (!StringUtil.isEmptyString(realName)) {
            QueryWrapper<User> w = new QueryWrapper<>();
            w.eq("real_name", realName);
            User user = userMapper.selectOne(w);
            if (user != null) {
                wrapper.eq("user_id", user.getId());
            } else {
                wrapper.eq("user_id", -1);
            }
        }

        // 管理员可以查询用户部门
        if (deptId != 0) {
            QueryWrapper<User> w = new QueryWrapper<>();
            w.eq("dept_id", deptId);
            List<User> users = userMapper.selectList(w);
            List<Integer> list = new ArrayList<>();
            for (User user : users) {
                list.add(user.getId());
            }
            if (list.size() > 0) {
                wrapper.in("user_id", list);
            }
        }

        if (currPage <= 0) {
            currPage = 1;
        }
        if (count <= 0) {
            count = 10;
        }

        Page<Post> page = new Page<>(currPage, count);

        postMapper.selectPage(page, wrapper);

        return page;

    }
}
