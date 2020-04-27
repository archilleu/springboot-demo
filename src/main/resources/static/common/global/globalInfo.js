$(function () {

  setTimeout(userInfo, 0);
  setTimeout(userMenu, 0);

  //用户基本信息
  function userInfo() {
    if (null != sessionStorage.getItem("user"))
      return;
    $.ajax({
      url: "/api/v1/sys/user/info",
      success: (data) => {
        sessionStorage.setItem("user", JSON.stringify(data));
      },
      error: (data) => {
        setTimeout(userInfo, 1000);
      },
      dataType: "json"
    });
  };

  //用户菜单
  function userMenu() {
    if (null != sessionStorage.getItem("menu"))
      return;

    const userStr = sessionStorage.getItem("user");
    if (null == userStr) {
      setTimeout(userMenu, 1000);
      return;
    }
    const user = JSON.parse(userStr);

    //只要id
    const roleIds = user.sysRoles.map((role) => {
      return role.roleId;
    });

    $.ajax({
      method: "POST",
      url: "/api/v1/admin/sys/role_menu/list.json",
      data: JSON.stringify(roleIds),
      contentType: "application/json",
      success: (data) => {
        sessionStorage.setItem("menu", JSON.stringify(data));
      },
      error: (data) => {
        setTimeout(userMenu, 1000);
      },
      dataType: "json"
    });
  }
});