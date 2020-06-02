package com.haoya.demo.app.common.utils;

import javax.servlet.http.HttpServletRequest;

public class RequestHelper {
    public static boolean isJsonRequest(HttpServletRequest request) {
        String requestUri = (String)request.getAttribute("javax.servlet.error.request_uri");
        //后缀是json表示是请求json格式
        if(requestUri!=null && requestUri.endsWith(".json")) {
            return true;
        } else {
            //通过请求头判断是否请求json格式数据
            String accept = request.getHeader("Accept");
            if(accept != null) {
                return accept.contains("application/json");
            }

            return false;
        }
    }
}
