package com.hoya.core.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResultResponseTests extends BaseTests {

    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    public ResultResponseTests() {
        super(ResultResponseTests.class);
    }

    @Override
    public void init() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Override
    public void done() {
        System.out.println("done");
    }

    @Test
    public void testInsertError() {

    }

    @Test
    public void testUpdateError() {

    }

    @Test
    public void testRight() throws Exception {
        RequestBuilder request = get(URL_SUCCESS)
//                .contentType(MediaType.APPLICATION_JSON).content(JSONObject.toJSONString(track))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())//返回HTTP状态为200
                .andReturn();
        JSONObject res = JSONObject.parseObject(result.getResponse().getContentAsString());
        System.out.println(res);
//        track = JSONObject.parseObject(res.getString("data"), Track.class);
//        Track track = JSONObject.parseObject(res.getString("data"), new TypeReference<Track>(){});

    }

    final private String URL_SUCCESS = "/success";
}
