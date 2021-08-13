package com.starter.base.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 基础模型
 */
@Data
public class BaseModel {

    // 系统自动生成id,增加操作时候设置null，不接受指定
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    private String createBy;

    //时间精确到秒(数据库精确到秒)
    private LocalDateTime createTime;

    private String lastUpdateBy;

    //时间精确到秒(数据库精确到秒)
    private LocalDateTime lastUpdateTime;

}
