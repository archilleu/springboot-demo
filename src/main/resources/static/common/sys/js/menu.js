$(function () {

  var option = {
    editElem: "#hy-edit-form",
    baseUrl: "/api/v1/admin/sys/menu/",
    list: {
      url: "list.json",
      id: 'menuId',
      parentId: 'parentId',
      cols: [{
          field: 'selectItem',
          radio: true
        },
        {
          title: '菜单编号',
          field: 'menuId',
          width: "80px",
          align: 'center',
          valign: 'middle'
        },
        {
          title: '名称',
          field: 'name',
          width: "200px"
        },
        {
          title: '地址',
          field: 'url'
        },
        {
          title: '权限',
          field: 'perms'
        },
        {
          title: '类型',
          field: 'type',
          align: 'center',
          valign: 'middle',
          sortable: true,
          formatter: function (item, index) {
            if (item.type === 0) {
              return '<span class="label label-primary" data-type=' + item.type + '>目录</span>';
            }
            if (item.type === 1) {
              return '<span class="label label-success" data-type=' + item.type + '>菜单</span>';
            }
            if (item.type === 2) {
              return '<span class="label label-warning" data-type=' + item.type + '>按钮</span>';
            }
          }
        },
        {
          title: '图标',
          field: 'icon'
        },
        {
          title: '排序',
          field: 'orderNum'
        }
      ]
    },
    toolbar: {
      elem: "#hy-treegrid-toolbar",
      buttons: [{
          elem: "#hy-toolbar-add",
          url: "add.json",
          type: "add",
          layer: {
            title: "添加",
            content: "#hy-edit-form"
          },
          msg: {
            success: "添加成功"
          },
          beforeSend: function (data) {
            //获取父节点
            var selected = $("#tableTree").tableTree("getSelections");
            if (0 == selected.length)
              return true;

            data.parentId = selected[0].id;
            return true;
          }
        },
        {
          elem: "#hy-toolbar-edit",
          url: "modify",
          type: "modify",
          layer: {
            title: "修改菜单",
            content: "#hy-edit-form"
          },
          msg: {
            success: "修改成功"
          }
        },
        {
          elem: "#hy-toolbar-delete",
          type: "delete",
          msg: {
            tip: "确定要删除菜单?",
            success: "删除成功"
          }
        },
      ]
    },
    search: {
      elem: ".hy-sidebar button[type=submit]",
      where: {
        name: ".hy-sidebar input[name=name]",
      }
    }
  };

  $(".hy-sidebar").hySidebar();
  $("#tableTree").tableTree(option);
});