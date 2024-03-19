package com.example.common.base.test;

import com.example.common.base.vo.HttpResult;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GlobalExceptionTests extends BaseTests {

    final private String URL_SERVER_EXCEPTION = "/server-exception";
    final private String URL_HTTP_MESSAGE_NOT_READABLE_EXCEPTION = "/http-message-not-readable-exception";
    final private String URL_ILLEGAL_ARGUMENT_EXCEPTION = "/illegal-argument-exception";
    final private String URL_ILLEGAL_ARGUMENT_EXCEPTION1 = "/illegal-argument-exception1";
    final private String URL_ERROR_HTTP_METHOD_EXCEPTION = "/error-http-method-exception";
    final private String URL_DATABASE_EXCEPTION = "/database-exception";
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;

    public GlobalExceptionTests() {
        super(GlobalExceptionTests.class);
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
    public void testRight() throws Exception {
        RequestBuilder request = get(URL_SERVER_EXCEPTION)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode root = objectMapper.readTree(res);
        Assert.assertEquals(root.get("code").asLong(), 4000);

        request = post(URL_HTTP_MESSAGE_NOT_READABLE_EXCEPTION)
                .contentType(MediaType.APPLICATION_JSON).content("{}")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();
        res = result.getResponse().getContentAsString();
        objectMapper = new ObjectMapper();
        HttpResult httpResult = objectMapper.readValue(res, HttpResult.class);
        Assert.assertEquals((long) httpResult.getCode(), 4000);

        request = post(URL_ILLEGAL_ARGUMENT_EXCEPTION)
                .contentType(MediaType.APPLICATION_JSON).content("{}")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();
        res = result.getResponse().getContentAsString();
        objectMapper = new ObjectMapper();
        httpResult = objectMapper.readValue(res, HttpResult.class);
        Assert.assertEquals((long) httpResult.getCode(), 4000);

        request = post(URL_ILLEGAL_ARGUMENT_EXCEPTION1)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();
        res = result.getResponse().getContentAsString();
        objectMapper = new ObjectMapper();
        httpResult = objectMapper.readValue(res, HttpResult.class);
        Assert.assertEquals((long) httpResult.getCode(), 4000);

        request = get(URL_ERROR_HTTP_METHOD_EXCEPTION)
                .contentType(MediaType.APPLICATION_JSON).content("{}")
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        result = mockMvc.perform(request)
                .andExpect(status().is4xxClientError())
                .andReturn();
        res = result.getResponse().getContentAsString();
        objectMapper = new ObjectMapper();
        httpResult = objectMapper.readValue(res, HttpResult.class);
        Assert.assertEquals((long) httpResult.getCode(), 4050);

        request = post(URL_DATABASE_EXCEPTION)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE);
        result = mockMvc.perform(request)
                .andExpect(status().is5xxServerError())
                .andReturn();
        res = result.getResponse().getContentAsString();
        objectMapper = new ObjectMapper();
        httpResult = objectMapper.readValue(res, HttpResult.class);
        Assert.assertEquals((long) httpResult.getCode(), 5040);
    }
}
