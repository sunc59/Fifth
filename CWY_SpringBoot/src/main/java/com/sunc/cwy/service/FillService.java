package com.sunc.cwy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.mapper.AttendMapper;
import com.sunc.cwy.mapper.DeptMapper;
import com.sunc.cwy.mapper.FillMapper;
import com.sunc.cwy.mapper.UserMapper;
import com.sunc.cwy.model.Attend;
import com.sunc.cwy.model.Fill;
import com.sunc.cwy.model.Uleave;
import com.sunc.cwy.model.User;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author sunc
 */
@Service
public class FillService {

    @Autowired(required = false)
    private FillMapper fillMapper;

    @Autowired(required = false)
    private UserMapper userMapper;

    @Autowired(required = false)
    private DeptMapper deptMapper;

    @Autowired(required = false)
    private AttendMapper attendMapper;

    /**
     * 删除补签记录
     *
     * @param ids
     */
    public void delFills(String[] ids) {

        fillMapper.deleteBatchIds(Arrays.asList(ids));
    }

    /**
     * 添加补签申请
     *
     * @param fill
     */
    public void addFill(Fill fill) {
        fillMapper.insert(fill);
    }

    /**
     * 根据id查询补签记录
     *
     * @param id
     * @return
     */
    public Fill getFillById(int id) {
        return fillMapper.selectById(id);
    }


    /**
     * 保存补签记录
     *
     * @param paramsFill 数据：-id -fillDate -fillLesson -fillReason
     */
    public void saveFill(Fill paramsFill) {

        Integer id = paramsFill.getId();
        Date fillDate = paramsFill.getFillDate();
        Integer fillLesson = paramsFill.getFillLesson();
        String fillReason = paramsFill.getFillReason();

        Fill fill = fillMapper.selectById(id);

        if (fillDate != null) {
            fill.setFillDate(fillDate);
        }
        if (fillLesson != null && fillLesson != 0) {
            fill.setFillLesson(fillLesson);
        }
        if (!StringUtil.isEmptyString(fillReason)) {
            fill.setFillReason(fillReason);
        }

        fillMapper.updateById(fill);
    }


    /**
     * 审核补签记录
     *
     * @param paramsFill 数据：-id -fillFlag
     */
    public void assessFill(Fill paramsFill) {

        Integer fillFlag = paramsFill.getFillFlag();

        Fill fill = fillMapper.selectById(paramsFill.getId());
        fill.setFillFlag(fillFlag);
        fillMapper.updateById(fill);

        if (fillFlag == 2) {
            // 算是签到成功
            QueryWrapper<Attend> wrapper = new QueryWrapper<>();
            wrapper.eq("attend_date", fill.getFillDate());
            wrapper.eq("attend_lesson", fill.getFillLesson());
            wrapper.eq("user_id", fill.getUserId());
            Attend attend = attendMapper.selectOne(wrapper);
            if (attend != null) {
                // 更新attendType
                attend.setAttendType(2);
                attendMapper.updateById(attend);
            }
        }
    }

    /**
     * 条件分页查询补签记录
     *
     * @param paramsFill
     * @param currPage
     * @param count
     * @return 数据：-fillDate -fillFlag || -realName -deptId
     */
    public Page<Fill> listFills(Fill paramsFill, int currPage, int count) {

        Date fillDate = paramsFill.getFillDate();
        Integer fillFlag = paramsFill.getFillFlag();
        String realName = paramsFill.getRealName();
        int deptId = paramsFill.getDeptId();

        QueryWrapper<Fill> wrapper = new QueryWrapper<>();

        if (fillDate != null) {
            wrapper.eq("fill_date", fillDate);
        }
        if (fillFlag != null && fillFlag != 0) {
            wrapper.eq("fill_flag", fillFlag);
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

        if (currPage <= 0) {
            currPage = 1;
        }
        if (count <= 0) {
            count = 10;
        }

        Page<Fill> page = new Page<>(currPage, count);

        fillMapper.selectPage(page, wrapper);

        return page;
    }
}
