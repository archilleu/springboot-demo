package com.starter.base.controller;

import com.alibaba.fastjson.JSONObject;
import com.starter.base.BaseTests;
import com.starter.base.controller.dto.LoginDto;
import com.starter.base.controller.dto.SysUserAddDto;
import com.starter.base.controller.dto.SysUserEditDto;
import com.starter.base.controller.dto.SysUserPasswordDto;
import com.starter.base.controller.vo.SysUserVo;
import com.starter.common.exception.HttpResult;
import com.starter.common.page.PageRequest;
import com.starter.common.page.PageResult;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseTests {

    final private String URL_USER_LOGIN = "/user/login";
    final private String TEST_URL_ADD = "/sys/user/add";
    final private String TEST_URL_EDIT = "/sys/user/";
    final private String TEST_URL_MODIFY_PWD = "/sys/user/%d/password";
    final private String TEST_URL_LIST = "/sys/user/list";
    final private String TEST_URL_DELETE = "/sys/user/";
    final private String TEST_URL_DELETE_BATCH = "/sys/user/delete";
    private SysUserAddDto sysUserAddDto;
    private SysUserEditDto sysUserEditDto;
    private Long id;

    public UserControllerTest() {
        super(UserControllerTest.class);
    }

    @Override
    public void init() throws Exception {
        sysUserAddDto = new SysUserAddDto();
        sysUserAddDto.setAvatar("avatar");
        sysUserAddDto.setEmail("email");
        sysUserAddDto.setMobile("mobile");
        sysUserAddDto.setName("name");
        sysUserAddDto.setNickName("nickname");
        sysUserAddDto.setPassword("password");

        sysUserEditDto = new SysUserEditDto();
        sysUserEditDto.setAvatar("avatar_edit");
        sysUserEditDto.setEmail("email_edit");
        sysUserEditDto.setMobile("mobile_edit");
        sysUserEditDto.setNickName("nickname_edit");
        sysUserEditDto.setPassword("password_edit");
    }

    @Override
    public void done() throws Exception {
        System.out.println("done");
    }

    @Test
    public void testError() throws Exception {
        SysUserAddDto dto = new SysUserAddDto();
        BeanUtils.copyProperties(sysUserAddDto, dto);
        dto.setName("admin");
        RequestBuilder request = post(TEST_URL_ADD)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(dto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andReturn();
    }

    @Test
    public void testRight() throws Exception {
        testAdd();

        testModify();

        testModifyPassword();

        testList();

        testDelete();

        testBatchDelete();
    }

    public void testAdd() throws Exception {
        RequestBuilder request = post(TEST_URL_ADD)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysUserAddDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        SysUserVo sysUserVo = JSONObject.parseObject(httpResult.getData().toString(), SysUserVo.class);
        Assert.assertNotNull(sysUserVo.getId());
        Assert.assertNotNull(sysUserVo.getCreateBy());
        Assert.assertNotNull(sysUserVo.getCreateTime());
        Assert.assertNull(sysUserVo.getRoles());
        Assert.assertNull(sysUserVo.getDeptName());
        id = sysUserVo.getId();
        Assert.assertEquals(sysUserAddDto.getAvatar(), sysUserVo.getAvatar());
        Assert.assertEquals(sysUserAddDto.getEmail(), sysUserVo.getEmail());
        Assert.assertEquals(sysUserAddDto.getMobile(), sysUserVo.getMobile());
        Assert.assertEquals(sysUserAddDto.getName(), sysUserVo.getName());
        Assert.assertEquals(sysUserAddDto.getNickName(), sysUserVo.getNickName());

        login(sysUserAddDto.getPassword());
    }

    public void testModify() throws Exception {
        RequestBuilder request = put(TEST_URL_EDIT + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysUserEditDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        SysUserVo sysUserVo = JSONObject.parseObject(httpResult.getData().toString(), SysUserVo.class);
        Assert.assertNotNull(sysUserVo.getId());
        Assert.assertNotNull(sysUserVo.getCreateBy());
        Assert.assertNotNull(sysUserVo.getCreateTime());
        Assert.assertNotNull(sysUserVo.getLastUpdateBy());
        Assert.assertNotNull(sysUserVo.getLastUpdateTime());
        Assert.assertNull(sysUserVo.getRoles());
        Assert.assertNull(sysUserVo.getDeptName());
        Assert.assertEquals(sysUserEditDto.getAvatar(), sysUserVo.getAvatar());
        Assert.assertEquals(sysUserEditDto.getEmail(), sysUserVo.getEmail());
        Assert.assertEquals(sysUserEditDto.getMobile(), sysUserVo.getMobile());
        Assert.assertEquals(sysUserEditDto.getNickName(), sysUserVo.getNickName());

        login(sysUserEditDto.getPassword());
    }

    public void testModifyPassword() throws Exception {
        String newPwd = "new-password";
        SysUserPasswordDto sysUserPasswordDto = new SysUserPasswordDto();
        sysUserPasswordDto.setPassword(newPwd);
        RequestBuilder request = put(String.format(TEST_URL_MODIFY_PWD, id))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysUserPasswordDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();

        login(newPwd);
    }

    public void testList() throws Exception {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setRows(10);
        pageRequest.setParams(new LinkedHashMap<String, Object>() {
            {
                put("name", sysUserAddDto.getName());
                put("email", sysUserEditDto.getEmail());
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
        List<SysUserVo> list = JSONObject.parseArray(pageResult.getContent().toString(), SysUserVo.class);
        Assert.assertNotEquals(0, list.size());
        SysUserVo sysUserVo = list.get(0);
        Assert.assertNotNull(sysUserVo.getId());
        Assert.assertNotNull(sysUserVo.getCreateBy());
        Assert.assertNotNull(sysUserVo.getCreateTime());
        Assert.assertNotNull(sysUserVo.getLastUpdateBy());
        Assert.assertNotNull(sysUserVo.getLastUpdateTime());
        Assert.assertNotNull(sysUserVo.getRoles());
        Assert.assertNull(sysUserVo.getDeptName());
        Assert.assertEquals(sysUserEditDto.getAvatar(), sysUserVo.getAvatar());
        Assert.assertEquals(sysUserEditDto.getEmail(), sysUserVo.getEmail());
        Assert.assertEquals(sysUserEditDto.getMobile(), sysUserVo.getMobile());
        Assert.assertEquals(sysUserEditDto.getNickName(), sysUserVo.getNickName());
    }

    public void testDelete() throws Exception {
        RequestBuilder request = delete(TEST_URL_DELETE + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysUserEditDto));
        MvcResult result;
        result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        Assert.assertFalse(exists());
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

        Assert.assertFalse(exists());
    }

    private void login(String password) throws Exception {
        RequestBuilder request = post(URL_USER_LOGIN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSONObject.toJSONString(new LoginDto(sysUserAddDto.getName(), password)));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        String token = JSONObject.parseObject(httpResult.getData().toString()).getString(TOKEN);
        Assert.assertNotNull(token);
    }

    private boolean exists() throws Exception {
        PageRequest pageRequest = new PageRequest();
        pageRequest.setPage(1);
        pageRequest.setRows(10);
        pageRequest.setParams(new LinkedHashMap<String, Object>() {
            {
                put("name", sysUserAddDto.getName());
                put("email", sysUserEditDto.getEmail());
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
        List<SysUserVo> list = JSONObject.parseArray(pageResult.getContent().toString(), SysUserVo.class);
        return list.size() > 0;
    }

}
