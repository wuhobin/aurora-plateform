package com.aurora.entity;

import cn.idev.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.aurora.utils.DateUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("sys_role")
@Schema(description = "角色信息")
@EqualsAndHashCode
public class SysRole implements Serializable {

    @TableId(type = IdType.AUTO)
    private Integer id;

    @Schema(description = "角色编码")
    @ExcelProperty(value = "角色编码")
    private String code;

    @Schema(description = "角色名称")
    @ExcelProperty(value = "角色名称")
    private String name;

    @Schema(description = "角色描述")
    private String remarks;


    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS, timezone = "GMT+8")
    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.UPDATE)
    @JsonFormat(pattern = DateUtils.YYYY_MM_DD_HH_MM_SS, timezone = "GMT+8")
    private LocalDateTime updateTime;
}
