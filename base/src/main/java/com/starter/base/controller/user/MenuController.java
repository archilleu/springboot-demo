package com.starter.base.controller.user;

import com.starter.base.constant.SysConstants;
import com.starter.base.controller.vo.MenuVo;
import com.starter.base.service.sys.SysMenuService;
import com.starter.base.shiro.util.TokenUser;
import com.starter.common.exception.ServerException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Slf4j
@Api(tags = "用户菜单")
@RestController
@RequestMapping("/user")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping(value = "/menu/findNavTree")
    @ApiOperation(value = "用户导航财产获取", notes = "返回用户导航菜单")
    public List<MenuVo> findNavTree(@ApiIgnore TokenUser tokenUser) {
        //获取当前登陆用户
        try {
            List<MenuVo> list = null;
            if (tokenUser.getRoles().contains(SysConstants.ROLE_ADMIN)) {
                list = sysMenuService.findNavTree();
            } else {
                list = sysMenuService.findNavTreeByUsername(tokenUser.getUsername());
            }

            return list;
        } catch (Exception e) {
            log.error(e.toString());
            throw ServerException.ServerError;
        }
    }

}
