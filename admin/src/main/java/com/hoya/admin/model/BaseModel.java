package com.hoya.admin.model;

import lombok.Data;

import java.sql.Timestamp;

/**
 * 基础模型
 */
@Data
public class BaseModel {

    private Long id;

    private String createBy;

    //时间精确到秒(数据库精确到秒)
    private Timestamp createTime;

    private String lastUpdateBy;

    //时间精确到秒(数据库精确到秒)
    private Timestamp lastUpdateTime;

}
