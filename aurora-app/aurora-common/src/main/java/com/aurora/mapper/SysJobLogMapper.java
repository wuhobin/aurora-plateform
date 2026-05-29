package com.aurora.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.aurora.entity.SysJobLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysJobLogMapper extends BaseMapper<SysJobLog> {
    
    void cleanJobLog();
} 