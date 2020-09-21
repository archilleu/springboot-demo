package com.hoya.admin.dao.sys;

import com.hoya.admin.BaseTests;
import com.hoya.admin.model.sys.SysConfig;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;

public class SysConfigMapperTests extends BaseTests {

    @Autowired
    SysConfigMapper sysConfigMapper;

    public SysConfigMapperTests() {
        super(SysConfigMapper.class);
    }

    @Override
    public void init() {
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        this.sysConfig = new SysConfig();
        sysConfig.setValue("value");
        sysConfig.setLabel("label");
        sysConfig.setType("type");
        sysConfig.setDescription("description");
        sysConfig.setSort(1L);
        sysConfig.setCreateBy("createBy");
        sysConfig.setCreateTime(now);
        sysConfig.setLastUpdateBy("updateBy");
        sysConfig.setLastUpdateTime(now);
        sysConfig.setRemarks("remarks");
        sysConfig.setDelFlag((byte) 1);
    }

    @Override
    public void done() {
        System.out.println("done");
    }

    @Test
    public void testError() {
        SysConfig sysConfig = new SysConfig();

        try {
            sysConfigMapper.insert(sysConfig);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            sysConfig.setValue("val");
            sysConfigMapper.insert(sysConfig);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            sysConfig.setLabel("label");
            sysConfigMapper.insert(sysConfig);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            sysConfig.setType("type");
            sysConfigMapper.insert(sysConfig);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            sysConfig.setDescription("description");
            sysConfigMapper.insert(sysConfig);
        } catch (DataIntegrityViolationException e) {
        }

        sysConfig.setSort(0L);
        sysConfigMapper.insert(sysConfig);

        //value值重复插入
        try {
            this.sysConfig.setId(null);
            Assert.assertEquals(0, sysConfigMapper.insert(sysConfig));
        } catch (DuplicateKeyException e) {
        }

        sysConfigMapper.deleteByPrimaryKey(sysConfig.getId());
    }

    @Test
    public void testRight() {
        //完整插入
        Assert.assertEquals(1, sysConfigMapper.insert(this.sysConfig));
        SysConfig sysConfig = sysConfigMapper.selectByPrimaryKey(this.sysConfig.getId());
        Assert.assertEquals(sysConfig, this.sysConfig);

        //删除
        Assert.assertEquals(1, sysConfigMapper.deleteByPrimaryKey((sysConfig.getId())));
        Assert.assertEquals(null, sysConfigMapper.selectByPrimaryKey(this.sysConfig.getId()));

        //部分插入
        this.sysConfig.setId(null);
        this.sysConfig.setValue("newValue");
        this.sysConfig.setCreateBy(null);
        this.sysConfig.setCreateTime(null);
        this.sysConfig.setLastUpdateBy(null);
        this.sysConfig.setLastUpdateTime(null);
        this.sysConfig.setRemarks(null);
        Assert.assertEquals(1, sysConfigMapper.insertSelective(this.sysConfig));
        sysConfig = sysConfigMapper.selectByPrimaryKey(this.sysConfig.getId());
        Assert.assertEquals(sysConfig, this.sysConfig);

        //完整更新
        this.sysConfig.setValue("update");
        Assert.assertEquals(1, sysConfigMapper.updateByPrimaryKey(this.sysConfig));
        Assert.assertEquals(this.sysConfig, sysConfigMapper.selectByPrimaryKey(this.sysConfig.getId()));

        //部分更新
        this.sysConfig.setValue("updateSelective");
        Assert.assertEquals(1, sysConfigMapper.updateByPrimaryKeySelective(this.sysConfig));
        Assert.assertEquals(this.sysConfig, sysConfigMapper.selectByPrimaryKey(this.sysConfig.getId()));

    }

    @Test
    public void testPage() {
        this.sysConfig.setId(null);
        this.sysConfig.setLabel("pageLabel");
        this.sysConfig.setValue("pageValue");
        sysConfig.setType("type");
        sysConfig.setDescription("description");
        sysConfig.setSort(1L);
        Assert.assertEquals(1, sysConfigMapper.insert(this.sysConfig));
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("value", "null");
        params.put("label", "null");

        List<SysConfig> list = sysConfigMapper.findPage(params);
        Assert.assertEquals(0, list.size());

        params.remove("label");
        params.put("value", this.sysConfig.getValue());
        list = sysConfigMapper.findPage(params);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(this.sysConfig, list.get(0));

        params.remove("value");
        params.put("label", this.sysConfig.getLabel());
        list = sysConfigMapper.findPage(params);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(this.sysConfig, list.get(0));

        params.put("label", this.sysConfig.getLabel());
        params.put("value", this.sysConfig.getValue());
        list = sysConfigMapper.findPage(params);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(this.sysConfig, list.get(0));
    }

    private SysConfig sysConfig;

}
