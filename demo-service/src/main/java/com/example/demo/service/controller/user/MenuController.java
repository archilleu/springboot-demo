package com.example.demo.service.controller.user;

import com.example.auth.vo.TokenUser;
import com.example.common.base.annotation.Log;
import com.example.demo.service.constant.SysConstants;
import com.example.demo.service.model.vo.MenuInfoVo;
import com.example.demo.service.service.ISysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * @author cjy
 */
@Slf4j
@Api(tags = "用户接口：用户菜单")
@RestController
@RequestMapping("/user")
public class MenuController {

    @Autowired
    private ISysMenuService sysMenuService;

    @Log("获取用户导航菜单")
    @GetMapping(value = "/menu/findNavTree")
    @ApiOperation(value = "用户导航菜单获取", notes = "返回用户导航菜单")
    public List<MenuInfoVo> findNavTree(@ApiIgnore TokenUser tokenUser) {
        if (tokenUser.getRoles().contains(SysConstants.ROLE_ADMIN)) {
            return sysMenuService.findNavTree();
        } else {
            return sysMenuService.findNavTreeByUsername(tokenUser);
        }
    }

}
