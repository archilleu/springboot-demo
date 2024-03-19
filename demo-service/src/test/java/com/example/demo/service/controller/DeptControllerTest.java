package com.example.demo.service.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.BaseTests;
import com.example.demo.service.model.vo.SysDeptVo;
import com.example.demo.service.model.vo.input.SysDeptInputVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.LinkedList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class DeptControllerTest extends BaseTests {

    final private String TEST_URL_ADD = "/sys/dept/add";
    final private String TEST_URL_EDIT = "/sys/dept/";
    final private String TEST_URL_CHILDREN = "/sys/dept/%s/children";
    final private String TEST_URL_DELETE = "/sys/dept/";
    final private String TEST_URL_DELETE_BATCH = "/sys/dept/delete";
    final private String TEST_URL_TREE = "/sys/dept/tree";
    private SysDeptInputVo sysDeptAddDto;
    private SysDeptInputVo sysDeptEditDto;
    private Long id;

    public DeptControllerTest() {
        super(DeptControllerTest.class);
    }

    @Override
    public void init() {
        sysDeptAddDto = new SysDeptInputVo();
        sysDeptAddDto.setName("dept");
        sysDeptAddDto.setOrderNum(1);
        sysDeptAddDto.setParentId(0l);

        sysDeptEditDto = new SysDeptInputVo();
        sysDeptEditDto.setName("dept_edit");
        sysDeptEditDto.setOrderNum(2);
        sysDeptEditDto.setParentId(1l);
    }

    @Override
    public void done() {
        System.out.println("done");
    }

    @Test
    public void testError() {
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
                .content(JSONObject.toJSONString(sysDeptAddDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        SysDeptVo sysDeptVo = JSONObject.parseObject(res).getObject("data", SysDeptVo.class);
        Assert.assertNotNull(sysDeptVo.getId());
        Assert.assertNotNull(sysDeptVo.getCreateBy());
        Assert.assertNotNull(sysDeptVo.getCreateTime());
        Assert.assertNotNull(sysDeptVo.getName());
        id = sysDeptVo.getId();
        Assert.assertEquals(sysDeptAddDto.getName(), sysDeptVo.getName());
        Assert.assertEquals(sysDeptAddDto.getParentId(), sysDeptVo.getParentId());
        Assert.assertEquals(sysDeptAddDto.getOrderNum(), sysDeptVo.getOrderNum());
    }

    public void testModify() throws Exception {
        RequestBuilder request = put(TEST_URL_EDIT + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysDeptEditDto));
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        SysDeptVo sysDeptVo = JSONObject.parseObject(res).getObject("data", SysDeptVo.class);
        Assert.assertNotNull(sysDeptVo.getId());
        Assert.assertNotNull(sysDeptVo.getCreateBy());
        Assert.assertNotNull(sysDeptVo.getCreateTime());
        Assert.assertNotNull(sysDeptVo.getName());
        Assert.assertEquals(sysDeptEditDto.getName(), sysDeptVo.getName());
        Assert.assertEquals(sysDeptEditDto.getParentId(), sysDeptVo.getParentId());
        Assert.assertEquals(sysDeptEditDto.getOrderNum(), sysDeptVo.getOrderNum());
    }

    public void testDelete() throws Exception {
        RequestBuilder request = delete(TEST_URL_DELETE + id)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token)
                .content(JSONObject.toJSONString(sysDeptEditDto));
        MvcResult result;
        result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
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
    }

    @Test
    public void testChildren() throws Exception {
        Long parentId = 0l;
        RequestBuilder request = get(String.format(TEST_URL_CHILDREN, parentId))
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        String data = JSONObject.parseObject(res).getString("data");
        List<SysDeptVo> list = JSONObject.parseArray(data, SysDeptVo.class);
        Assert.assertNotEquals(list.size(), 0);
        SysDeptVo sysDept = list.get(0);
        Assert.assertNotNull(sysDept.getId());
        Assert.assertNotNull(sysDept.getCreateBy());
        Assert.assertNotNull(sysDept.getCreateTime());
        Assert.assertNotNull(sysDept.getName());
        Assert.assertEquals(sysDept.getParentId(), parentId);
    }

    @Test
    public void testTree() throws Exception {
        Long parentId = 0l;
        RequestBuilder request = get(TEST_URL_TREE)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .header(TOKEN, token);
        MvcResult result = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn();
        String res = result.getResponse().getContentAsString();
        String data = JSONObject.parseObject(res).getString("data");
        List<SysDeptVo> list = JSONObject.parseArray(data, SysDeptVo.class);
        Assert.assertNotEquals(list.size(), 0);
        SysDeptVo sysDept = list.get(0);
        Assert.assertNotNull(sysDept.getId());
        Assert.assertNotNull(sysDept.getCreateBy());
        Assert.assertNotNull(sysDept.getCreateTime());
        Assert.assertNotNull(sysDept.getName());
//        Assert.assertNotNull(sysDept.getHasChildren());
        Assert.assertNotNull(sysDept.getChildren());
        Assert.assertFalse(sysDept.getChildren().isEmpty());
        Assert.assertEquals(sysDept.getParentId(), parentId);
    }

}
