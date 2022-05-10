package com.sunc.cwy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sunc.cwy.mapper.DeptMapper;
import com.sunc.cwy.model.Dept;
import com.sunc.cwy.model.Post;
import com.sunc.cwy.model.common.Condition;
import com.sunc.cwy.model.common.PageInfo;
import com.sunc.cwy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author sunc
 */
@Service
public class DeptService {


    @Autowired(required = false)
    private DeptMapper deptMapper;


    /**
     * 查询部门列表
     *
     * @return
     */
    public List<Dept> listDepts() {
        return deptMapper.selectList(null);
    }


    /**
     * 根据id查询单个部门信息
     *
     * @return
     */
    public Dept getDeptById(int id) {
        return deptMapper.selectById(id);
    }

    /**
     * 根据name查询单个部门信息
     *
     * @return
     */
    public Dept getDeptByName(String deptName) {
        QueryWrapper<Dept> wrapper = new QueryWrapper<>();
        wrapper.eq("dept_name", deptName);
        return deptMapper.selectOne(wrapper);
    }

    /**
     * 增加部门
     *
     * @param dept
     */
    public void addDept(Dept dept) {
        deptMapper.insert(dept);
    }

    /**
     * 更新部门信息
     *
     * @param dept
     */
    public void saveDept(Dept dept) {

        Dept department = getDeptById(dept.getId());

        if (!StringUtil.isEmptyString(dept.getDeptName())) {
            department.setDeptName(dept.getDeptName());
        }

        if (!StringUtil.isEmptyString(dept.getDeptNote())) {
            department.setDeptNote(dept.getDeptNote());
        }

        deptMapper.updateById(department);
    }

    /**
     * 批量删除部门记录
     *
     * @param ids
     */
    public void delDepts(String[] ids) {
        deptMapper.deleteBatchIds(Arrays.asList(ids));
    }


    /**
     * 条件分页查询部门信息列表
     *
     * @param paramsDept
     * @param currPage
     * @param count
     * @return 参数：-deptName
     */
    public Page<Dept> listDepts(Dept paramsDept, int currPage, int count) {

        String deptName = paramsDept.getDeptName();

        QueryWrapper<Dept> wrapper = new QueryWrapper<>();

        if (!StringUtil.isEmptyString(deptName)) {
            wrapper.like("dept_name", deptName);
        }

        if (currPage <= 0) {
            currPage = 1;
        }
        if (count <= 0) {
            count = 10;
        }

        Page<Dept> page = new Page<>(currPage, count);

        deptMapper.selectPage(page, wrapper);

        return page;
    }
}
