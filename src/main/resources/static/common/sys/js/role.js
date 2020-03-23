$(function () {
  //选中的角色
  var selectedRole = null;
  var roleMenuLayerId = null;

  const option = {
    editElem: ".hy-edit-form",
    baseUrl: "/api/v1/admin/sys/role/",
    list: {
      url: "list.json",
      id: "roleId",
      title: "角色",
      cols: [
        [{
            type: 'radio'
          },
          {
            field: "roleName",
            title: "角色名称"
          },
          {
            field: "remark",
            title: "备注"
          },
          {
            field: "createTime",
            title: "创建时间",
            formatter: function (item, index) {}
          },
        ]
      ],
    },
    toolbar: {
      elem: ".hy-list-toolbar",
      buttons: [{
          elem: ".hy-toolbar-add",
          url: "add.json",
          type: "add",
          layer: {
            title: "添加角色",
            content: ".hy-edit-form"
          },
          msg: {
            success: "添加成功"
          }
        },
        {
          elem: ".hy-toolbar-edit",
          url: "modify",
          type: "modify",
          layer: {
            title: "修改角色",
            content: ".hy-edit-form"
          },
          msg: {
            success: "修改成功"
          }
        },
        {
          elem: ".hy-toolbar-delete",
          type: "delete",
          msg: {
            tip: "确定要删除角色?",
            success: "删除成功"
          }
        },
        {
          elem: ".hy-toolbar-role-menu",
          click: (e) => {
            selectedRole = $("#hy-table").hyTable("getSelections");
            if (selectedRole.length == 0) {
              layui.layer.msg("请选择角色!");
              return;
            }
            //打开编辑页面
            roleMenuLayerId = layer.open({
              title: "用户角色菜单编辑",
              area: ['90%', '90%'],
              type: 1,
              shadeClose: true,
              maxmin: true,
              content: $(".role-menu")
            });

            //渲染树形控件
            var roleMenuTree = layui.tree.render({
              elem: '#roleMenuTree', //绑定元素
              showCheckbox: true, //是否显示复选框
              id: 'roleMenuTree',
              data: [],
            });

            //获取系统菜单
            $.get("/api/v1/admin/sys/role_menu/listTree.json?roleId=" + selectedRole[0].roleId, function (data) {
              layui.tree.reload('roleMenuTree', {
                data: data
              });
            });
          }
        },
      ]
    },
    search: {
      elem: ".hy-sidebar button[type=submit]",
      where: {
        roleName: ".hy-sidebar input[name=roleName]",
        remark: ".hy-sidebar input[name=remark]"
      }
    }
  };

  $("#hy-table").hyTable(option);
  $(".hy-sidebar").hySidebar();

  $("#roleMenuSave").click(function () {
    //获取选中的菜单
    var menus = layui.tree.getChecked('roleMenuTree');
    var ids = [];
    for (const menu of menus) {
      ids.push(menu.id);
      makeMenuIds(ids, menu);
    }

    $.ajax({
      url: "/api/v1/admin/sys/role_menu/roleMenus?roleId=" + selectedRole[0].roleId,
      data: JSON.stringify(ids),
      type: "POST",
      contentType: "application/json",
      success: (e) => {
        layer.msg("保存成功");
        layer.close(roleMenuLayerId);
      },
      error: (e) => {
        layer.msg("保存失败");
      }
    });
  });

  function makeMenuIds(ids, menu) {
    for (var child of menu.children) {
      ids.push(child.id);
      makeMenuIds(ids, child);
    }
  }

  $("#roleMenuCancle").click(function () {
    layer.closeAll();
  });

});