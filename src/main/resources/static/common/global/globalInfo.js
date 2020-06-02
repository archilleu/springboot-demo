$(function () {

  setTimeout(userMenu, 0);

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
      url: "/api/v1/sys/user/menu_list.json",
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