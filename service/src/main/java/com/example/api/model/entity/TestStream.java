package com.example.api.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cjy
 */
@Data
public class TestStream implements Serializable {
    private int id;
    private String user_id;
    private int groupId;
    private Date createTime;
}
