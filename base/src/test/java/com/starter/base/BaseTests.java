package com.starter.base;

import com.alibaba.fastjson.JSONObject;
import com.starter.base.controller.dto.LoginDto;
import com.starter.common.exception.HttpResult;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
abstract public class BaseTests {

    final public String TOKEN = "token";
    final private String URL_USER_LOGIN = "/user/login";
    final private String ACCOUNT = "admin";
    final private String PASSWORD = "admin";
    private final Logger logger;
    private final Class clazz;
    protected MockMvc mockMvc;
    protected String token;
    @Autowired
    private WebApplicationContext webApplicationContext;

    public BaseTests(Class clazz) {
        this.clazz = clazz;
        logger = LoggerFactory.getLogger(this.clazz);
    }

    abstract public void init() throws Exception;

    abstract public void done() throws Exception;

    @Before
    public void before() throws Exception {
        // https://www.javatt.com/p/20028
        DefaultMockMvcBuilder builder = MockMvcBuilders.webAppContextSetup(webApplicationContext);
        builder.addFilters((javax.servlet.Filter) webApplicationContext.getBean("shiroFilter"));
        mockMvc = builder.build();
        login();

        logger.info(clazz.getName() + "开始测试-------------------------------------------------");

        this.init();
    }

    @After
    public void after() throws Exception {
        this.done();
        logger.info(this.clazz.getName() + "结束测试-------------------------------------------------");
    }

    private void login() throws Exception {
        RequestBuilder request = post(URL_USER_LOGIN)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(JSONObject.toJSONString(new LoginDto(ACCOUNT, PASSWORD)));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        HttpResult httpResult = JSONObject.parseObject(res, HttpResult.class);
        token = JSONObject.parseObject(httpResult.getData().toString()).getString(TOKEN);
    }
}
