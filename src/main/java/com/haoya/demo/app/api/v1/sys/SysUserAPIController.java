package com.haoya.demo.app.api.v1.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.haoya.demo.app.exception.AppException;
import com.haoya.demo.app.sys.entity.SysMenu;
import com.haoya.demo.app.sys.entity.SysUser;
import com.haoya.demo.app.sys.entity.SysUserDetails;
import com.haoya.demo.app.sys.repository.SysRoleMenuRepository;
import com.haoya.demo.app.sys.repository.SysUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping("/api/v1/sys/user")
public class SysUserAPIController {

    @PostMapping("/password")
    public void password(@RequestParam(required = true)String oldPassword, @RequestParam(required = true)String newPassword) {
        String oldCrypt = bCryptPasswordEncoder.encode(oldPassword);
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        SysUser sysUser = userDetails.getSysUser();
        if(sysUser.getPassword().equals(oldCrypt))
            throw AppException.Forbidden;

        sysUser.setPassword(bCryptPasswordEncoder.encode(newPassword));
        sysUserRepository.save(sysUser);

        return;
    }

    @PostMapping("/modify")
    public void modify(@RequestBody SysUser update) {
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        SysUser sysUser = userDetails.getSysUser();
        BeanUtils.copyProperties(sysUser, update, "nickname", "email", "mobile");
        sysUserRepository.save(update);
    }

    @GetMapping("/info")
    public SysUserDetails info() {
        SysUserDetails userDetails = (SysUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userDetails;
    }

    //获取用户可以看到的菜单
    @PostMapping(value = "/menu_list.json", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public JSONArray menuList(@RequestBody List<BigInteger> roleIds) {
        if(roleIds.isEmpty()) {
            return new JSONArray();
        }

        List<SysMenu> menus = sysRoleMenuRepository.getRoleMenu(roleIds);
        if(menus.isEmpty()) {
            return new JSONArray();
        }

        return makeMenuListData(menus);
    }

    //生成父子关系菜单
    private JSONArray makeMenuListData(List<SysMenu> menus) {
        Map<BigInteger, List<SysMenu>> idMenuMap = makeIdChildrenMap(menus);

        //一级菜单,父id为0
        List<SysMenu> topMenu = idMenuMap.get(BigInteger.ZERO);
        if(null == topMenu)
            throw AppException.ServerError;

        //虚拟tree的根，方便递归
        JSONObject root = new JSONObject();
        root.put("id", BigInteger.ZERO);
        JSONArray children = new JSONArray();
        root.put("children", children);
        makeListJSON(idMenuMap, root);
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

    private void makeListJSON(Map<BigInteger, List<SysMenu>> idMenuMap, JSONObject jMenu) {
        BigInteger parentId = jMenu.getBigInteger("id");
        List<SysMenu> menus = idMenuMap.get(parentId);
        if(null == menus) {
            return;
        }

        JSONArray children = jMenu.getJSONArray("children");
        for(SysMenu sysMenu : menus) {
            JSONObject tmp = new JSONObject();
            tmp.put("title", sysMenu.getName());
            tmp.put("id", sysMenu.getMenuId());
            tmp.put("type", sysMenu.getType());
            tmp.put("url", sysMenu.getUrl());
            tmp.put("children", new JSONArray());

            children.add(tmp);
            makeListJSON(idMenuMap, tmp);
        }

        idMenuMap.remove(parentId);
        return;
    }


    @Autowired
    private SysUserRepository sysUserRepository;

    @Autowired
    private SysRoleMenuRepository sysRoleMenuRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
}
