$(function () {
    var $mainMenu = $("#main-menu");
    var $subMenu = $("#sub-menu");

    /**
     * 动态生成菜单,从session里面获取菜单缓存
     */
    setTimeout(init, 0);

    function init() {
        //生成菜单
        if (false == buildMenu()) {
            setTimeout(init, 1000);
            return;
        }

        //重新渲染导航菜单
        layui.element.render();

        //选中第一个主菜单
        $mainMenu.find("li:first-child").trigger("click");

        //绑定菜单点击事件
        bindAsideMenuClick();

        //绑定信息修改
        bindUserInfoModify();

        //绑定密码修改
        bindPasswordModify();

        //绑定登出
        bindUserLogout();


    }

    function buildMenu() {
        const menuStr = sessionStorage.getItem("menu");
        if (null == menuStr) {
            return false;
        }
        const menus = JSON.parse(menuStr);
        var mainHtml = "";
        var subHtml = "";
        for (const menu of menus) {
            $mainMenu.append("<li>{name}</li>".replace("{name}", menu.title));
            $subMenu.append(buildSubMenu(menu));
        }
    }

    function buildSubMenu(menu) {
        var $html = $('<div class="layui-tab-item"><ul class="layui-nav layui-nav-tree layui-nav-side" ></div>');
        var $ul = $html.find("ul");
        for (var subMenu of menu.children) {
            var $li = $('<li class="layui-nav-item"><a href="{href}" lay-id="{id}">{name}</a>'.replace(/{id}|{href}|{name}/g, function (matchStr) {
                var tokenMap = {
                    "{id}": subMenu.id,
                    "{name}": subMenu.title,
                    "{href}": subMenu.url || "javascript:;"
                };
                return tokenMap[matchStr];
            }));
            buildSubMenuItem($li, subMenu);
            $ul.append($li);
        }
        return $html;
    }

    function buildSubMenuItem($parent, menu) {
        for (subMenu of menu.children) {

            //按钮跳过
            if (subMenu.type == 2)
                continue;

            var $dl = $('<dl class="layui-nav-child"></dl>');
            var $dd = $('<dd><a href="{href}" lay-id="{id}">{name}</a></dd>'.replace(/{id}|{href}|{name}/g, function (matchStr) {
                var tokenMap = {
                    "{id}": subMenu.id,
                    "{name}": subMenu.title,
                    "{href}": subMenu.url || "javascript:;"
                };
                return tokenMap[matchStr];
            }));
            $dl.append($dd);
            $parent.append($dl);

            buildSubMenuItem($dd, subMenu);
        }
    }

    function bindAsideMenuClick() {
        //获取侧边菜单有效链接
        var $links = $("#sub-menu").find("a");
        for (link of $links) {
            //过滤不是链接的菜单
            if (!link.href || link.href == "" || link.href == "#" || link.href == "javascript:;")
                continue;

            (function (link) {
                link.addEventListener("click", (e) => {
                    e.preventDefault();

                    //当前点击菜单id
                    const menuId = link.getAttribute("lay-id");

                    //判断是否已经打开
                    var exist = false;
                    var $list = $('#main-board ul>li');
                    $list.each(function (idx, item) {
                        if (item.getAttribute("lay-id") != menuId)
                            return true;

                        exist = true;
                        layui.element.tabChange("main-board-tab", menuId);
                        return false;
                    });

                    if (exist)
                        return;

                    const url = '<iframe frameborder=0 height=100% width=100% name="" scrolling=auto seamless src="{url}"></iframe>'.replace("{url}", link.href);
                    layui.element.tabAdd('main-board-tab', {
                        title: link.text,
                        content: url,
                        id: menuId
                    })
                    layui.element.tabChange("main-board-tab", menuId);
                }, false);
            })(link);
        }
    }

    //个人信息编辑
    function bindUserInfoModify() {
        $("#user-info-modify").click(() => {
            var infoEdit = layer.open({
                title: "用户信息编辑",
                area: ['90%', '90%'],
                type: 1,
                shadeClose: true,
                maxmin: true,
                content: $("#info-modify")
            });
        });
    }

    //修改密码
    function bindPasswordModify() {
        $("#user-password-modify").click(() => {
            var infoEdit = layer.open({
                title: "用户密码修改",
                area: ['90%', '90%'],
                type: 1,
                shadeClose: true,
                maxmin: true,
                content: $("#password-modify")
            });
        });
    }

    //登出
    function bindUserLogout() {
        $("#user-logout").click(() => {
            $.ajax({
                url: "/logout",
                type: "POST",
                success: (e) => {
                    layer.msg("登出成功");
                    setTimeout('window.location.href = "/sys/login"', 1000);
                },
                error: (e) => {
                    layer.msg("层出失败");
                }
            });
        });
    }


    //提交表单
    layui.form.on('submit(formUserInfo)', function (data) {
        $.ajax({
            url: "/api/v1/sys/user/modify",
            data: JSON.stringify(data.field),
            type: "POST",
            contentType: "application/json",
            success: (e) => {
                layer.msg("保存成功");
            },
            error: (e) => {
                layer.msg("保存失败");
            }
        });
        return false;
    });

    //提交表单
    layui.form.on('submit(formPasswordModify)', function (data) {
        var field = data.field;
        //对比密码
        if(field.newPassword != field.confirmPassword) {
            layer.msg("两次输入密码不一致");
            return false;
        }
        $.ajax({
            url: "/api/v1/sys/user/password",
            data: data.field,
            type: "POST",
            success: (e) => {
                layer.msg("修改成功,请重新登陆");
                setTimeout('window.location.href = "/sys/login"', 1000);
            },
            error: (e) => {
                layer.msg("旧密码验证失败，请重试");
            }
        });
        return false;
    });
});