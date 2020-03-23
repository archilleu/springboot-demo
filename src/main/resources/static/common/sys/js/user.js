$(function () {

  //选中的用户
  var selectedUser = null;

  const option = {
    editElem: ".hy-edit-form",
    baseUrl: "/api/v1/admin/sys/user/",
    list: {
      url: "list.json",
      id: "userId",
      title: "用户",
      cols: [
        [{
            type: 'radio'
          },
          {
            field: "username",
            title: "名称"
          },
          {
            field: "nickname",
            title: "昵称"
          },
          {
            field: "email",
            title: "邮件"
          },
          {
            field: "mobile",
            title: "电话"
          },
          {
            title: '状态',
            field: 'status',
            align: 'center',
            valign: 'middle',
            sortable: true,
            templet: function (item, index) {
              if (item.status === 0) {
                return '<span class="layui-badge" data-type=' + item.status + '>禁用</span>';
              }
              if (item.status === 1) {
                return '<span class="layui-badge layui-bg-green" data-type=' + item.status + '>启用</span>';
              }
            }
          },
          {
            field: "createTime",
            title: "创建时间"
          },
        ]
      ],
      done: function (res, curr, count) {
        //todo
      },
    },
    toolbar: {
      elem: ".hy-list-toolbar",
      buttons: [{
          elem: ".hy-toolbar-add",
          url: "add.json",
          type: "add",
          layer: {
            title: "添加用户",
            content: ".hy-edit-form"
          },
          msg: {
            success: "添加成功"
          },
          beforeSend: function (data) {
            if (data.password != data.confirmPassword) {
              layer.msg("密码不一致!");
              return false;
            }
            delete data.confirmPassword;

            return true;
          }
        },
        {
          elem: ".hy-toolbar-edit",
          url: "modify",
          type: "modify",
          layer: {
            title: "修改用户",
            content: ".hy-edit-form"
          },
          msg: {
            success: "修改成功"
          },
          beforeShow: function (data) {
            data.confirmPassword = data.password;
            $("input[name=password]").prop("readonly", true);
            $("input[name=confirmPassword]").prop("readonly", true);
            return;
          },
          beforeSend: function (data) {
            delete data.confirmPassword;
            return true;
          },
          complete: function (data) {
            $("input[name=password]").prop('disabled', false);
            $("input[name=confirmPassword]").prop('disabled', false);
          }
        },
        {
          elem: ".hy-toolbar-delete",
          type: "delete",
          msg: {
            tip: "确定要删除用户?",
            success: "删除成功"
          }
        },
        {
          elem: ".hy-toolbar-role-edit",
          click: function () {
            selectedUser = $("#hy-table").hyTable("getSelections");
            if (selectedUser.length == 0) {
              layui.layer.msg("请选择用户!");
              return;
            }

            //获取系统角色列表
            const url = "/api/v1/admin/sys/user/roles?userId=" + selectedUser[0].userId;
            var $roleTable = layui.table.render({
              elem: '#role-table',
              url: url,
              title: '用户角色',
              cols: [
                [{
                    type: "checkbox"
                  },
                  {
                    field: "roleName",
                    title: "角色名称"
                  },
                  {
                    field: "remark",
                    title: "备注"
                  }
                ]
              ],
              page: true,
              parseData: (res) => {
                return {
                  "code": 0,
                  "msg": "success",
                  "count": res.count,
                  "data": res.list
                };
              },
              id: "roleTable",
              done: (res, curr, count) => {}
            });
            //打开编辑页面
            var roleLayerId = layer.open({
              title: "用户角色编辑",
              area: ['90%', '90%'],
              type: 1,
              shadeClose: true,
              maxmin: true,
              content: $(".role-edit")
            });
          }
        }
      ]
    },
    search: {
      elem: ".hy-sidebar button[type=submit]",
      where: {
        username: ".hy-sidebar input[name=username]",
        nickname: ".hy-sidebar input[name=nickname]"
      }
    }
  };

  $("#hy-table").hyTable(option);
  $(".hy-sidebar").hySidebar();

  $("#roleSave").click(function () {
    var checkStatus = layui.table.checkStatus('roleTable');
    var data = checkStatus.data;
    $.ajax({
      url: "/api/v1/admin/sys/user/roles?userId=" + selectedUser[0].userId,
      data: JSON.stringify(data),
      type: "POST",
      contentType: "application/json",
      success: (e) => {
        layer.msg("保存成功");
      },
      error: (e) => {
        layer.msg("保存失败");
      }
    });
  });
  $("#roleCancle").click(function () {
    layer.closeAll();
  });
});