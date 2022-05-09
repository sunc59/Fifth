package com.sunc.cwy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.mapper.DeptMapper;
import com.sunc.cwy.mapper.UserMapper;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.User;
import com.sunc.cwy.model.common.Condition;
import com.sunc.cwy.model.common.PageInfo;
import com.sunc.cwy.util.Md5;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author sunc
 */
@Service
public class UserService {


    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private DeptMapper deptMapper;

    /**
     * 添加用户
     *
     * @param user
     */
    public void addUser(User user) {
        user.setUserPass(Md5.makeMd5(user.getUserPass()));
        userMapper.insert(user);
    }


    /**
     * 批量删除用户
     *
     * @param userIds
     */
    public void delUsers(String[] userIds) {
        userMapper.deleteBatchIds(Arrays.asList(userIds));
    }


    /**
     * 条件查询单个用户
     *
     * @param user
     * @return
     */
    public User getUser(User user) {

        User u = null;

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (user.getId() != null && user.getId() != 0) {
            wrapper.eq("id", user.getId());
        }
        if (!StringUtil.isEmptyString(user.getUserName())) {
            wrapper.eq("user_name", user.getUserName());
        }
        if (!StringUtil.isEmptyString(user.getUserPass())) {
            wrapper.eq("user_pass", Md5.makeMd5(user.getUserPass()));
        }
        if (!StringUtil.isEmptyString(user.getUserNo())) {
            wrapper.eq("user_no", user.getUserNo());
        }
        if (user.getUserType() != null && user.getUserType() != 0) {
            wrapper.eq("user_type", user.getUserType());
        }

        List<User> userList = userMapper.selectList(wrapper);

        if (userList != null && userList.size() > 0) {
            u = userList.get(0);
        }

        // 填充部门信息
        Dept dept = deptMapper.selectById(u.getDeptId());
        u.setDept(dept);
        return u;
    }


    /**
     * 根据deptId查询用户列表
     *
     * @param deptId
     * @return
     */
    public List<User> listUsersByDid(int deptId) {

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_id", deptId);

        return userMapper.selectList(wrapper);
    }

    /**
     * 根据用户编号查询用户
     *
     * @param userNo
     * @return
     */
    public User getUserByNo(String userNo) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_no", userNo);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 根据用户名查询用户
     *
     * @param userName
     * @return
     */
    public User getUserByName(String userName) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name", userName);
        return userMapper.selectOne(wrapper);
    }

    /**
     * 根据用户id查询用户
     *
     * @param uid
     * @return
     */
    public User getUserById(int uid) {

        return userMapper.selectById(uid);
    }

    /**
     * 保存用户信息
     *
     * @param paramsUser
     * @return 参数：-id -userPass -realName -userSex -deptId
     */
    public User saveUser(User paramsUser) {

        User u = userMapper.selectById(paramsUser.getId());

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.eq("id", paramsUser.getId());

        if (!StringUtil.isEmptyString(paramsUser.getUserName())) {
            u.setUserName(paramsUser.getUserName());
        }
        if (!StringUtil.isEmptyString(paramsUser.getUserPass())) {
            u.setUserPass(Md5.makeMd5(paramsUser.getUserPass()));
        }
        if (!StringUtil.isEmptyString(paramsUser.getUserNo())) {
            u.setUserNo(paramsUser.getUserNo());
        }
        if (!StringUtil.isEmptyString(paramsUser.getRealName())) {
            u.setRealName(paramsUser.getRealName());
        }
        if (paramsUser.getUserSex() != null && paramsUser.getUserSex() != 0) {
            u.setUserSex(paramsUser.getUserSex());
        }
        if (paramsUser.getDept() != null && paramsUser.getDept().getId() != null) {
            u.setDept(paramsUser.getDept());
        }
        if (paramsUser.getUserType() != null && paramsUser.getUserType() != 0) {
            u.setUserType(paramsUser.getUserType());
        }
        if (paramsUser.getDeptId() != 0) {
            u.setDeptId(paramsUser.getDeptId());
        }

        userMapper.update(u, wrapper);

        User user = userMapper.selectById(u.getId());

        // 填充部门信息
        Dept dept = deptMapper.selectById(user.getDeptId());
        user.setDept(dept);

        return user;
    }


    /**
     * 条件分页查询用户列表
     *
     * @param paramsUser
     * @param currPage
     * @param count
     * @return 参数：-userNo -realName -deptId
     */
    public Page<User> listUsers(User paramsUser, int currPage, int count) {

        String userNo = paramsUser.getUserNo();
        String realName = paramsUser.getRealName();

        int deptId = paramsUser.getDeptId();

        QueryWrapper<User> wrapper = new QueryWrapper<>();

        if (!StringUtil.isEmptyString(userNo)) {
            wrapper.like("user_no", userNo);
        }
        if (!StringUtil.isEmptyString(realName)) {
            wrapper.like("real_name", realName);
        }
        if (deptId != 0) {
            wrapper.eq("dept_id", deptId);
        }

        if (currPage <= 0) {
            currPage = 1;
        }
        if (count <= 0) {
            count = 10;
        }

        Page<User> page = new Page<>(currPage, count);

        userMapper.selectPage(page, wrapper);

        return page;
    }
}
