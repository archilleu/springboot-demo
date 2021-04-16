package com.hoya.admin.controller.user.vo;

import lombok.Data;

import java.util.LinkedList;
import java.util.List;

@Data
public class MenuVo {

    private Long id;

    private Long parentId;

    private String name;

    private String url;

    private String perms;

    private Integer type;

    private String icon;

    private Integer orderNum;

    private List<MenuVo> children = new LinkedList<>();
}
