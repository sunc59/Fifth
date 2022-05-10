package com.sunc.cwy.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.sunc.cwy.mapper.ConfigMapper;
import com.sunc.cwy.model.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunc
 */
@Service
public class ConfigService {

    @Autowired(required = false)
    private ConfigMapper configMapper;

    /**
     * 更新考勤时间配置信息
     *
     * @param config
     */
    public void updateConfig(Config config) {
        configMapper.updateById(config);
    }

    /**
     * 获取考勤时间配置信息
     *
     * @return 参数：无参数
     */
    public Config getConfig() {
        return configMapper.selectList(null).get(0);
    }

}
