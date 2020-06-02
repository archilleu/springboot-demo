package com.haoya.demo.app.service.sys;

import java.math.BigInteger;

public interface SysMenuExtRepository {
    /**
     * 删除菜单及其子菜单
     * @param menu_id
     * 菜单id
     */
    void deleteMenuAndChild(BigInteger menu_id);
}
