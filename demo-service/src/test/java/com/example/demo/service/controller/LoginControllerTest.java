package com.example.demo.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.BaseTests;
import com.example.demo.service.model.vo.LoginTokenVo;
import com.example.demo.service.model.vo.query.LoginQueryVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LoginControllerTest extends BaseTests {

    final private String URL_USER_LOGIN = "/user/login";
    final private String ACCOUNT = "admin";
    final private String PASSWORD = "admin";
    final private String TOKEN = "token";

    public LoginControllerTest() {
        super(LoginControllerTest.class);
    }

    @Test
    public void testError() throws Exception {
        RequestBuilder request = post(URL_USER_LOGIN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSONObject.toJSONString(new LoginQueryVo(ACCOUNT, PASSWORD + "error")));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        int code = JSONObject.parseObject(res).getInteger("code");
        Assert.assertEquals(code, 4010);
    }

    @Test
    public void testRight() throws Exception {
        RequestBuilder request = post(URL_USER_LOGIN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSONObject.toJSONString(new LoginQueryVo(ACCOUNT, PASSWORD)));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        token = JSONObject.parseObject(res).getObject("data", LoginTokenVo.class).getToken();
        Assert.assertNotNull(token);
    }

    @Override
    public void init() {

    }

    @Override
    public void done() {
    }
}
