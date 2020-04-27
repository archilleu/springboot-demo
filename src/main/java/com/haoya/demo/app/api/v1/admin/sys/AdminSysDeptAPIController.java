package com.haoya.demo.app.api.v1.admin.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysDept;
import com.haoya.demo.app.sys.repository.SysDeptRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/api/v1/admin/sys/dept")
public class AdminSysDeptAPIController {

    /**
     * 获取部门管理树形表格列表
     * @param name  搜索条件部门名称，支持SQL模糊匹配
     * @return
     */
    @GetMapping("/list.json")
    public List<SysDept> list(@RequestParam(required=false)String name) {
        if(StringUtils.isEmpty(name))
            return sysDeptRepository.findAll(new Sort(Sort.Direction.ASC, "orderNum"));

        return sysDeptRepository.findByNameLike(name);
    }

    @PostMapping("/add.json")
    public SysDept add(@RequestBody SysDept sysDept) {
        return sysDeptRepository.save(sysDept);
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysDept sysDept) {
        sysDeptRepository.save(sysDept);
    }

    @GetMapping("/{id}.json")
    public SysDept get(@PathVariable(name="id") BigInteger id) {
        try {
            return sysDeptRepository.findById(id).get();
        } catch (Exception e) {
            throw AppException.NotFound;
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable(name="id") BigInteger id) {
        sysDeptRepository.deleteDeptAndChild(id);
    }

    /**
     *
     * 获取layui树形结构的菜单
     * @return
     */
    @GetMapping("/listTree.json")
    public JSONArray listTree() {
        List<SysDept> depts = sysDeptRepository.findAll();
        return makeLayuiTreeData(depts);
    }

    //生成lauui tree需要的结构
    private JSONArray makeLayuiTreeData(List<SysDept> depts) {
        Map<BigInteger, List<SysDept>> idDeptMap = makeIdChildrenMap(depts);

        //一级菜单,父id为0
        List<SysDept> topDept = idDeptMap.get(BigInteger.ZERO);
        if(null == topDept)
            throw AppException.ServerError;

        //虚拟tree的根，方便递归
        JSONObject root = new JSONObject();
        root.put("id", BigInteger.ZERO);
        JSONArray children = new JSONArray();
        root.put("children", children);
        makeTreeJSON(idDeptMap, root);
        return children;
    }

    //生id下所有子菜单的map
    private Map<BigInteger, List<SysDept>> makeIdChildrenMap(List<SysDept> depts) {
        HashMap<BigInteger, List<SysDept>> idDeptMap = new HashMap<>();
        for(SysDept dept : depts) {
            List<SysDept> list = idDeptMap.get(dept.getParentId());
            if(null == list) {
                list = new LinkedList<>();
                idDeptMap.put(dept.getParentId(), list);
            }
            list.add(dept);
        }

        return idDeptMap;
    }

    private void makeTreeJSON(Map<BigInteger, List<SysDept>> idDeptMap, JSONObject jDept) {
        BigInteger parentId = jDept.getBigInteger("id");
        List<SysDept> depts = idDeptMap.get(parentId);
        if(null == depts) {
            //没有子机构
            return;
        }

        JSONArray children = jDept.getJSONArray("children");
        for(SysDept sysDept : depts) {
            JSONObject tmp = new JSONObject();
            tmp.put("title", sysDept.getName());
            tmp.put("id", sysDept.getDeptId());
            tmp.put("children", new JSONArray());
            tmp.put("spread", true);
            children.add(tmp);

            makeTreeJSON(idDeptMap, tmp);
        }

        idDeptMap.remove(parentId);
    }

    @Autowired
    private SysDeptRepository sysDeptRepository;
}
