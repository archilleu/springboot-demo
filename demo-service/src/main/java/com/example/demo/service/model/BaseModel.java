package com.example.demo.service.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础模型
 */
@Data
public class BaseModel {

    /**
     * 系统自动生成id,增加操作时候设置null，不接受指定
     */
    @ApiModelProperty("数据库id")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 创建人
     */
    @ApiModelProperty("创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @ApiModelProperty("更新人")
    private String lastUpdateBy;

    /**
     * 更新时间
     */
    @ApiModelProperty("更新时间")
    private LocalDateTime lastUpdateTime;

}
