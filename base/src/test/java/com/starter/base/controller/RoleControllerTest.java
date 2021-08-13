package com.starter.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.starter.base.BaseTests;
import com.starter.base.controller.dto.SysRoleDto;
import com.starter.base.model.sys.SysMenu;
import com.starter.base.model.sys.SysRole;
import com.starter.base.model.sys.SysRoleMenu;
import com.starter.common.exception.HttpResult;
import com.starter.common.page.PageRequest;
import com.starter.common.page.PageResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RoleControllerTest extends BaseTests {

    final private String TEST_URL_ADD = "/sys/role/add";
    final private String TEST_URL_EDIT = "/sys/role/";
    final private String TEST_URL_DELETE = "/sys/role/";
    final private String TEST_URL_DELETE_BATCH = "/sys/role/delete";
    final private String TEST_URL_LIST = "/sys/role/list";
    final private String TEST_URL_LIST_ALL = "/sys/role/list-all";
    final private String TEST_URL_MENUS = "/sys/role/%d/menus";
    final private String TEST_URL_SAVE_MENUS = "/sys/role/save-menus";
    private SysRoleDto sysRoleAddDto;
    private SysRoleDto sysRoleEditDto;
    private Long id;

    public RoleControllerTest() {
        super(RoleControllerTest.class);
    }

    @Override
    public void init() throws Exception {
        sysRoleAddDto = new SysRoleDto();
        sysRoleAddDto.setName("role");
        sysRoleAddDto.setRemark("remark");

        sysRoleEditDto = new SysRoleDto();
        sysRoleEditDto.setName("role_edit");
        sysRoleEditDto.setRemark("remark_edit");
    }

    @Override
    public void done() throws Exception {
        System.out.println("done");
    }

    @Test
    public void testError() throws Exception {
    }

    @Test
    public void testRight() throws Exception {
        testAdd();

        testModify();

        testList();

        testSveRoleMenus();

        testRoleMenus();

        testDelete();

        testBatchDelete();
    }

    public void testAdd() throws Exception {
        RequestBuilder request = post(TEST_URL_ADD)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysRoleAddDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        SysRole sysRole = JSONObject.parseObject(httpResult.getData().toString(), SysRole.class);
        Assert.assertNotNull(sysRole.getId());
        Assert.assertNotNull(sysRole.getCreateBy());
        Assert.assertNotNull(sysRole.getCreateTime());
        Assert.assertNotNull(sysRole.getName());
        id = sysRole.getId();
        Assert.assertEquals(sysRoleAddDto.getName(), sysRole.getName());
        Assert.assertEquals(sysRoleAddDto.getRemark(), sysRole.getRemark());
    }

    public void testModify() throws Exception {
        RequestBuilder request = put(TEST_URL_EDIT + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysRoleEditDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        SysRole sysRole = JSONObject.parseObject(httpResult.getData().toString(), SysRole.class);
        Assert.assertNotNull(sysRole.getId());
        Assert.assertNotNull(sysRole.getCreateBy());
        Assert.assertNotNull(sysRole.getCreateTime());
        Assert.assertNotNull(sysRole.getName());
        Assert.assertEquals(sysRoleEditDto.getName(), sysRole.getName());
        Assert.assertEquals(sysRoleEditDto.getRemark(), sysRole.getRemark());
    }

    public void testList() throws Exception {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setRows(10);
        String name = sysRoleEditDto.getName().substring(1);
        pageRequest.setParams(new LinkedHashMap<String, Object>() {
            {
                put("name", name);
            }
        });
        RequestBuilder request = post(TEST_URL_LIST)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(pageRequest));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        PageResult pageResult = JSONObject.parseObject(httpResult.getData().toString(), PageResult.class);
        List<SysRole> list = JSONObject.parseArray(pageResult.getContent().toString(), SysRole.class);
        Assert.assertNotEquals(0, list.size());
        SysRole sysRole = list.get(0);
        Assert.assertNotNull(sysRole.getId());
        Assert.assertNotNull(sysRole.getCreateBy());
        Assert.assertNotNull(sysRole.getCreateTime());
        Assert.assertNotNull(sysRole.getLastUpdateBy());
        Assert.assertNotNull(sysRole.getLastUpdateTime());
        Assert.assertEquals(sysRoleEditDto.getName(), sysRole.getName());
        Assert.assertEquals(sysRoleEditDto.getRemark(), sysRole.getRemark());
    }

    public void testDelete() throws Exception {
        RequestBuilder request = delete(TEST_URL_DELETE + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysRoleEditDto));
        MvcResult result;
        result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertFalse(exists(sysRoleEditDto));
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
        Assert.assertFalse(exists(sysRoleAddDto));
    }

    @Test
    public void testListAll() throws Exception {
        RequestBuilder request = get(TEST_URL_LIST_ALL)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        List<SysRole> list = JSONObject.parseArray(httpResult.getData().toString(), SysRole.class);
        Assert.assertNotEquals(0, list.size());
        SysRole sysRole = list.get(0);
        Assert.assertNotNull(sysRole.getId());
        Assert.assertNotNull(sysRole.getCreateBy());
        Assert.assertNotNull(sysRole.getCreateTime());
        Assert.assertNotNull(sysRole.getLastUpdateBy());
        Assert.assertNotNull(sysRole.getLastUpdateTime());
        Assert.assertNotNull(sysRole.getName());
        Assert.assertNotNull(sysRole.getRemark());
    }

    public void testRoleMenus() throws Exception {
        RequestBuilder request = get(String.format(TEST_URL_MENUS, id))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        List<SysMenu> list = JSONObject.parseArray(httpResult.getData().toString(), SysMenu.class);
        Assert.assertNotEquals(0, list.size());
    }

    public void testSveRoleMenus() throws Exception {
        List<SysRoleMenu> list = (new LinkedList<SysRoleMenu>() {
            {
                add(new SysRoleMenu(id, 1l));
                add(new SysRoleMenu(id, 2l));
            }
        });
        RequestBuilder request = post(TEST_URL_SAVE_MENUS)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(list));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
    }

    private boolean exists(SysRoleDto test) throws Exception {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setRows(10);
        pageRequest.setParams(new LinkedHashMap<String, Object>() {
            {
                put("name", test.getName());
            }
        });
        RequestBuilder request = post(TEST_URL_LIST)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(pageRequest));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        PageResult pageResult = JSONObject.parseObject(httpResult.getData().toString(), PageResult.class);
        List<SysRole> list = JSONObject.parseArray(pageResult.getContent().toString(), SysRole.class);
        return !list.isEmpty();
    }
}
