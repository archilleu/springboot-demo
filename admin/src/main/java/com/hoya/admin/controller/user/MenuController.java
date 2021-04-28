package com.hoya.admin.controller.user;

import com.hoya.admin.constant.SysConstants;
import com.hoya.admin.controller.user.vo.MenuVo;
import com.hoya.admin.model.sys.SysMenu;
import com.hoya.admin.security.JwtUserDetails;
import com.hoya.admin.server.sys.SysMenuService;
import com.hoya.admin.util.SecurityUtils;
import com.hoya.core.exception.ServerError;
import com.hoya.core.exception.ServerException;
import com.hoya.core.exception.ServerExceptionForbidden;
import com.hoya.core.exception.ServerExceptionServerError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/user")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/menu/findNavTree")
    public List<MenuVo> findNavTree() {
        //获取当前登陆用户
        JwtUserDetails user = SecurityUtils.getCurrentUser();
        try {
            List<MenuVo> list = null;
            if(user.getRoles().contains(SysConstants.ROLE_ADMIN)) {
                list = sysMenuService.findNavTree();
            } else {
                list = sysMenuService.findNavTreeByUsername(user.getUsername());
            }

            return list;
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

}
