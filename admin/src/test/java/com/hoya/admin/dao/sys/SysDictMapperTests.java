package com.hoya.admin.dao.sys;

import com.hoya.admin.BaseTests;
import com.hoya.admin.model.sys.SysDict;
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

public class SysDictMapperTests extends BaseTests {

    @Autowired
    SysDictMapper sysDictMapper;

    public SysDictMapperTests() {
        super(SysDictMapper.class);
    }

    @Override
    public void init() {
        LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.of(0,0,0));
        this.sysDict = new SysDict();
        sysDict.setValue("value");
        sysDict.setLabel("label");
        sysDict.setType("type");
        sysDict.setDescription("description");
        sysDict.setSort(1L);
        sysDict.setCreateBy("createBy");
        sysDict.setCreateTime(now);
        sysDict.setLastUpdateBy("updateBy");
        sysDict.setLastUpdateTime(now);
        sysDict.setRemarks("remarks");
        sysDict.setDelFlag((byte) 1);
    }

    @Override
    public void done() {
        System.out.println("done");
    }

    @Test
    public void testError() {
        SysDict sysDict = new SysDict();

        try {
            sysDictMapper.insert(sysDict);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            sysDict.setValue("val");
            sysDictMapper.insert(sysDict);
        } catch (DataIntegrityViolationException e) {
        }
        try {
            sysDict.setLabel("label");
            sysDictMapper.insert(sysDict);
        } catch (DataIntegrityViolationException e) {
        }

        sysDict.setType("type");
        sysDictMapper.insert(sysDict);

        //value值重复插入
        try {
            this.sysDict.setId(null);
            Assert.assertEquals(0, sysDictMapper.insert(sysDict));
        } catch (DuplicateKeyException e) {
        }

        sysDictMapper.deleteByPrimaryKey(sysDict.getId());
    }

    @Test
    public void testRight() {
        //完整插入
        Assert.assertEquals(1, sysDictMapper.insert(this.sysDict));
        SysDict sysDict = sysDictMapper.selectByPrimaryKey(this.sysDict.getId());
        Assert.assertEquals(sysDict, this.sysDict);

        //删除
        Assert.assertEquals(1, sysDictMapper.deleteByPrimaryKey((sysDict.getId())));
        Assert.assertEquals(null, sysDictMapper.selectByPrimaryKey(this.sysDict.getId()));

        //部分插入
        this.sysDict.setId(null);
        this.sysDict.setValue("newValue");
        this.sysDict.setCreateBy(null);
        this.sysDict.setCreateTime(null);
        this.sysDict.setLastUpdateBy(null);
        this.sysDict.setLastUpdateTime(null);
        this.sysDict.setRemarks(null);
        Assert.assertEquals(1, sysDictMapper.insertSelective(this.sysDict));
        sysDict = sysDictMapper.selectByPrimaryKey(this.sysDict.getId());
        Assert.assertEquals(sysDict, this.sysDict);

        //完整更新
        this.sysDict.setValue("update");
        Assert.assertEquals(1, sysDictMapper.updateByPrimaryKey(this.sysDict));
        Assert.assertEquals(this.sysDict, sysDictMapper.selectByPrimaryKey(this.sysDict.getId()));

        //部分更新
        this.sysDict.setValue("updateSelective");
        Assert.assertEquals(1, sysDictMapper.updateByPrimaryKeySelective(this.sysDict));
        Assert.assertEquals(this.sysDict, sysDictMapper.selectByPrimaryKey(this.sysDict.getId()));

    }

    @Test
    public void testPage() {
        this.sysDict.setId(null);
        this.sysDict.setLabel("pageLabel");
        this.sysDict.setValue("pageValue");
        sysDict.setType("type");
        sysDict.setDescription("description");
        sysDict.setSort(1L);
        Assert.assertEquals(1, sysDictMapper.insert(this.sysDict));
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("value", "null");
        params.put("label", "null");

        List<SysDict> list = sysDictMapper.findPage(params);
        Assert.assertEquals(0, list.size());

        params.remove("label");
        params.put("value", this.sysDict.getValue());
        list = sysDictMapper.findPage(params);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(this.sysDict, list.get(0));

        params.remove("value");
        params.put("label", this.sysDict.getLabel());
        list = sysDictMapper.findPage(params);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(this.sysDict, list.get(0));

        params.put("label", this.sysDict.getLabel());
        params.put("value", this.sysDict.getValue());
        list = sysDictMapper.findPage(params);
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(this.sysDict, list.get(0));
    }

    private SysDict sysDict;

}
