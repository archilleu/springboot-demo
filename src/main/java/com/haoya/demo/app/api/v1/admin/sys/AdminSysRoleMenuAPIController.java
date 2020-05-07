package com.haoya.demo.app.api.v1.admin.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysMenu;
import com.haoya.demo.app.sys.entity.SysRoleMenu;
import com.haoya.demo.app.sys.repository.SysMenuRepository;
import com.haoya.demo.app.sys.repository.SysRoleMenuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/sys/role_menu")
public class AdminSysRoleMenuAPIController {

    //获取角色layui树形结构的菜单
    @GetMapping("/listTree.json")
    public JSONArray listTree(@RequestParam(required=true)BigInteger roleId) {
        List<SysMenu> menus = sysMenuRepository.findAll();
        return makeLayuiTreeData(roleId, menus);
    }

    //修改角色菜单
    @PostMapping(value = "/roleMenus", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void roleMenus(
        @RequestParam(required = true)BigInteger roleId,
        @RequestBody(required = true)List<BigInteger> menuIds) {

        sysRoleMenuRepository.modifyRoleMenu(roleId, menuIds);

        return;
    }

    //生成lauui tree需要的结构
    private JSONArray makeLayuiTreeData(BigInteger roleId, List<SysMenu> menus) {
        Map<BigInteger, List<SysMenu>> idMenuMap = makeIdChildrenMap(menus);

        //构建已经分配菜单集合
        List<SysRoleMenu> ownMenus = sysRoleMenuRepository.findByRoleId(roleId);
        Set<BigInteger> checked = ownMenus
                .stream()
                .map(SysRoleMenu::getMenuId)
                .collect(Collectors.toSet());

        //一级菜单,父id为0
        List<SysMenu> topMenu = idMenuMap.get(BigInteger.ZERO);
        if(null == topMenu)
            throw AppException.ServerError;

        //虚拟tree的根，方便递归
        JSONObject root = new JSONObject();
        root.put("id", BigInteger.ZERO);
        JSONArray children = new JSONArray();
        root.put("children", children);
        makeTreeJSON(idMenuMap, checked, root);
        return children;
    }

    //生成id下所有子菜单的map
    private Map<BigInteger, List<SysMenu>> makeIdChildrenMap(List<SysMenu> menus) {
        HashMap<BigInteger, List<SysMenu>> idMenuMap = new LinkedHashMap<>();
        for(SysMenu menu : menus) {
            List<SysMenu> list = idMenuMap.get(menu.getParentId());
            if(null == list) {
                list = new LinkedList<>();
                idMenuMap.put(menu.getParentId(), list);
            }
            list.add(menu);
        }

        return idMenuMap;
    }

    private void makeTreeJSON(Map<BigInteger, List<SysMenu>> idMenuMap, Set<BigInteger> ownMenus, JSONObject jMenu) {
        //注意layui tree控件没有半勾选状态，所以如果子节点没有选完，父节点不能设置checked=true
        BigInteger parentId = jMenu.getBigInteger("id");
        List<SysMenu> menus = idMenuMap.get(parentId);
        if(null == menus) {
            if(ownMenus.contains(jMenu.getBigInteger("id"))) {
                jMenu.put("checked", true);
            }
            return;
        }

        int count = 0;
        JSONArray children = jMenu.getJSONArray("children");
        for(SysMenu sysMenu : menus) {
            JSONObject tmp = new JSONObject();
            tmp.put("title", sysMenu.getName());
            tmp.put("id", sysMenu.getMenuId());
            tmp.put("children", new JSONArray());
            if(ownMenus.contains(sysMenu.getMenuId())) {
                count++;
            }

            children.add(tmp);
            makeTreeJSON(idMenuMap, ownMenus, tmp);
        }
        if(menus.size() == count)
            jMenu.put("checked", true);

        idMenuMap.remove(parentId);
    }

    @Autowired
    private SysMenuRepository sysMenuRepository;

    @Autowired
    private SysRoleMenuRepository sysRoleMenuRepository;
}
