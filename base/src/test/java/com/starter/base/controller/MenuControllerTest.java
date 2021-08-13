package com.starter.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.starter.base.BaseTests;
import com.starter.base.controller.dto.SysMenuDto;
import com.starter.base.controller.vo.MenuVo;
import com.starter.base.model.sys.SysMenu;
import com.starter.common.exception.HttpResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.util.ObjectUtils;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MenuControllerTest extends BaseTests {


    final private String TEST_URL_ADD = "/sys/menu/add";
    final private String TEST_URL_EDIT = "/sys/menu/";
    final private String TEST_URL_DELETE = "/sys/menu/";
    final private String TEST_URL_DELETE_BATCH = "/sys/menu/delete";
    final private String TEST_URL_MENU_TREE = "/sys/menu/tree";
    final private String TEST_URL_FIND_NAV_TREE = "/user/menu/findNavTree";
    private SysMenuDto sysMenuAddDto;
    private SysMenuDto sysMenuEditDto;
    private Long id;

    public MenuControllerTest() {
        super(MenuControllerTest.class);
    }

    @Override
    public void init() throws Exception {
        sysMenuAddDto = new SysMenuDto();
        sysMenuAddDto.setParentId(0l);
        sysMenuAddDto.setName("name");
        sysMenuAddDto.setUrl("/url");
        sysMenuAddDto.setPerms("perms");
        sysMenuAddDto.setType(0);
        sysMenuAddDto.setIcon("icon");
        sysMenuAddDto.setOrderNum(1);

        sysMenuEditDto = new SysMenuDto();
        sysMenuEditDto.setParentId(0l);
        sysMenuEditDto.setName("name_edit");
        sysMenuEditDto.setUrl("/url_edit");
        sysMenuEditDto.setPerms("perms_edit");
        sysMenuEditDto.setType(1);
        sysMenuEditDto.setIcon("icon_edit");
        sysMenuEditDto.setOrderNum(2);
    }

    @Override
    public void done() throws Exception {
        System.out.println("done");
    }

    @Test
    public void testRight() throws Exception {
        testAdd();

        testModify();

        testDelete();

        testBatchDelete();
    }

    public void testAdd() throws Exception {
        RequestBuilder request = post(TEST_URL_ADD)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysMenuAddDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        SysMenu sysMenu = JSONObject.parseObject(httpResult.getData().toString(), SysMenu.class);
        Assert.assertNotNull(sysMenu.getId());
        Assert.assertNotNull(sysMenu.getCreateBy());
        Assert.assertNotNull(sysMenu.getCreateTime());
        Assert.assertNotNull(sysMenu.getName());
        id = sysMenu.getId();
        Assert.assertEquals(sysMenuAddDto.getParentId(), sysMenu.getParentId());
        Assert.assertEquals(sysMenuAddDto.getName(), sysMenu.getName());
        Assert.assertEquals(sysMenuAddDto.getUrl(), sysMenu.getUrl());
        Assert.assertEquals(sysMenuAddDto.getPerms(), sysMenu.getPerms());
        Assert.assertEquals(sysMenuAddDto.getType(), sysMenu.getType());
        Assert.assertEquals(sysMenuAddDto.getIcon(), sysMenu.getIcon());
        Assert.assertEquals(sysMenuAddDto.getOrderNum(), sysMenu.getOrderNum());
        Assert.assertTrue(exists(sysMenuAddDto));
    }

    public void testModify() throws Exception {
        RequestBuilder request = put(TEST_URL_EDIT + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysMenuEditDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        SysMenu sysMenu = JSONObject.parseObject(httpResult.getData().toString(), SysMenu.class);
        Assert.assertNotNull(sysMenu.getId());
        Assert.assertNotNull(sysMenu.getCreateBy());
        Assert.assertNotNull(sysMenu.getCreateTime());
        Assert.assertNotNull(sysMenu.getName());
        id = sysMenu.getId();
        Assert.assertEquals(sysMenuEditDto.getParentId(), sysMenu.getParentId());
        Assert.assertEquals(sysMenuEditDto.getName(), sysMenu.getName());
        Assert.assertEquals(sysMenuEditDto.getUrl(), sysMenu.getUrl());
        Assert.assertEquals(sysMenuEditDto.getPerms(), sysMenu.getPerms());
        Assert.assertEquals(sysMenuEditDto.getType(), sysMenu.getType());
        Assert.assertEquals(sysMenuEditDto.getIcon(), sysMenu.getIcon());
        Assert.assertEquals(sysMenuEditDto.getOrderNum(), sysMenu.getOrderNum());
        Assert.assertTrue(exists(sysMenuEditDto));
    }

    public void testDelete() throws Exception {
        RequestBuilder request = delete(TEST_URL_DELETE + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token);
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertFalse(exists(sysMenuEditDto));
    }

    public void testBatchDelete() throws Exception {
        // 增加
        testAdd();

        RequestBuilder request = delete(TEST_URL_DELETE_BATCH)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(new LinkedList<Long>() {{
                    add(id);
                }}));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertFalse(exists(sysMenuEditDto));
    }

    @Test
    public void testError() throws Exception {
        RequestBuilder request = get(TEST_URL_FIND_NAV_TREE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token + "invalid");
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();
        String res = result.getResponse().getContentAsString();
    }

    @Test
    public void testFindNavTree() throws Exception {
        RequestBuilder request = get(TEST_URL_FIND_NAV_TREE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        List<MenuVo> list = JSONObject.parseArray(httpResult.getData().toString(), MenuVo.class);
        Assert.assertFalse(list.isEmpty());
        MenuVo item = list.get(0);
        Assert.assertNotNull(item.getChildren());
        Assert.assertNotNull(item.getIcon());
        Assert.assertNotNull(item.getId());
        Assert.assertNotNull(item.getName());
        Assert.assertNotNull(item.getOrderNum());
        Assert.assertNotNull(item.getParentId());
    }

    private boolean exists(SysMenuDto sysMenuDto) throws Exception {
        RequestBuilder request = get(TEST_URL_MENU_TREE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        List<MenuVo> list = JSONObject.parseArray(httpResult.getData().toString(), MenuVo.class);
        Assert.assertFalse(list.isEmpty());
        int count = 0;
        for (MenuVo menuVo : list) {
            count = 0;
            if (!ObjectUtils.isEmpty(menuVo.getName()) && menuVo.getName().equals(sysMenuDto.getName())) count++;
            if (!ObjectUtils.isEmpty(menuVo.getUrl()) && menuVo.getUrl().equals(sysMenuDto.getUrl())) count++;
            if (!ObjectUtils.isEmpty(menuVo.getPerms()) && menuVo.getPerms().equals(sysMenuDto.getPerms())) count++;
            if (!ObjectUtils.isEmpty(menuVo.getType()) && menuVo.getType().equals(sysMenuDto.getType())) count++;
            if (!ObjectUtils.isEmpty(menuVo.getIcon()) && menuVo.getIcon().equals(sysMenuDto.getIcon())) count++;
            if (!ObjectUtils.isEmpty(menuVo.getOrderNum()) && menuVo.getOrderNum().equals(sysMenuDto.getOrderNum()))
                count++;
            if (6 == count) break;
        }
        return 6 == count;
    }
}
