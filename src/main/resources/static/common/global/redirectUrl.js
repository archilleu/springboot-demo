//后台未授权一律返回401，302只有在web才会返回
$(function () {
	$(document).ajaxComplete(function (event, xhr, settings) {
		if (xhr.status == 401) {
			var redirectUrl = xhr.getResponseHeader('X-Redirect');
			top.location.href = redirectUrl;
		}
	})
});