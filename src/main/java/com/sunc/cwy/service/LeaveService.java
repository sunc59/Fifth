package com.sunc.cwy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.mapper.UleaveMapper;
import com.sunc.cwy.mapper.UserMapper;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.Holiday;
import com.sunc.cwy.model.Uleave;
import com.sunc.cwy.model.User;
import com.sunc.cwy.model.common.Condition;
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
public class LeaveService {

    @Autowired(required = false)
    private UleaveMapper leaveMapper;

    @Autowired(required = false)
    private UserMapper userMapper;


    /**
     * 添加请假申请
     *
     * @param leave
     */
    public void addLeave(Uleave leave) {
        leave.setLeaveFlag(1);
        leaveMapper.insert(leave);
    }


    /**
     * 批量删除请假申请
     *
     * @param ids
     */
    public void delLeaves(String[] ids) {
        leaveMapper.deleteBatchIds(Arrays.asList(ids));
    }


    /**
     * 条件分页查询请假申请
     *
     * @param paramsLeave
     * @param currPage
     * @param count
     * @return 数据：-leaveDate1 -leaveDate2 -realName -deptId -leaveFlag -userId
     */
    public Page<Uleave> listLeaves(Uleave paramsLeave, int currPage, int count) {

        String leaveDate1 = paramsLeave.getLeaveDate1();
        String leaveDate2 = paramsLeave.getLeaveDate2();
        String realName = paramsLeave.getRealName();
        int deptId = paramsLeave.getDeptId();
        Integer leaveFlag = paramsLeave.getLeaveFlag();
        int userId = paramsLeave.getUserId();

        QueryWrapper<Uleave> wrapper = new QueryWrapper<>();

        if (userId != 0) {
            wrapper.eq("user_id", userId);
        }

        if (!StringUtil.isEmptyString(leaveDate1) && !StringUtil.isEmptyString(leaveDate2)) {

            wrapper.ge("leave_date1", leaveDate1);
            wrapper.le("leave_date2", leaveDate2);
        }
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
        if (leaveFlag != null && leaveFlag != 0) {
            wrapper.eq("leave_flag", leaveFlag);
        }

        if (currPage <= 0) {
            currPage = 1;
        }
        if (count <= 0) {
            count = 10;
        }

        Page<Uleave> page = new Page<>(currPage, count);

        leaveMapper.selectPage(page, wrapper);

        return page;
    }


    /**
     * 根据id获取请假申请
     *
     * @param id
     * @return
     */
    public Uleave getLeaveById(int id) {
        return leaveMapper.selectById(id);
    }


    /**
     * 保存编辑的请假申请
     *
     * @param paramsLeave 数据：-id -leaveDate1 -leaveLesson1 -leaveDate2 -leaveLesson2 -leaveReason -leaveType
     */
    public void saveLeave(Uleave paramsLeave) {
        leaveMapper.updateById(paramsLeave);
    }


    /**
     * 审核请假申请
     *
     * @param paramsLeave 数据：-id -leaveFlag
     */
    public void assessLeave(Uleave paramsLeave) {

        Uleave leave = leaveMapper.selectById(paramsLeave.getId());
        leave.setLeaveFlag(paramsLeave.getLeaveFlag());
        leaveMapper.updateById(leave);
    }
}
