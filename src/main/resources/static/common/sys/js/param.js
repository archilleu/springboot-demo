$(function () {
  //选中的角色
  var selectedRole = null;
  var roleMenuLayerId = null;

  const option = {
    editElem: ".hy-edit-form",
    baseUrl: "/api/v1/admin/sys/param/",
    list: {
      url: "list.json",
      id: "id",
      title: "参数",
      cols: [
        [{
            type: 'radio'
          },
          {
            field: "title",
            title: "标题"
          },
          {
            field: "domain",
            title: "域名"
          },
          {
            field: "loginPage",
            title: "登录页",
            formatter: function (item, index) {}
          },
          {
            field: "homePage",
            title: "主页"
          },
          {
            field: "copyright",
            title: "版权"
          },
          {
            field: "logo",
            title: "logo"
          },
          {
            field: "favicon",
            title: "favicon"
          },
          {
            field: "backgroundImage",
            title: "背景图片"
          },
          {
            field: "domain",
            title: "域名"
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
            title: "添加参数",
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
            title: "修改参数",
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
            tip: "确定要删除参数?",
            success: "删除成功"
          }
        }
      ]
    },
    search: {
      elem: ".hy-sidebar button[type=submit]",
      where: {
        domain: ".hy-sidebar input[name=domain]",
        title: ".hy-sidebar input[name=title]"
      }
    }
  };

  $("#hy-table").hyTable(option);
  $(".hy-sidebar").hySidebar();

});