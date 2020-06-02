package com.haoya.demo.app.exception;

/**
 * 截获全局异常，做相应的异常处理(spring boot 默认/error)：
 *  1.json请求返回json格式
 *  2.web页面请求返回web页面
 */

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.haoya.demo.app.common.utils.RequestHelper.isJsonRequest;

@Controller
public class ErrorController extends AbstractErrorController {

    public ErrorController() {
        super(new DefaultErrorAttributes());
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
    @RequestMapping("/error")
    public ModelAndView getErrorPath(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> model = Collections.unmodifiableMap(getErrorAttributes(request, false));
        Integer status = (Integer)model.get("status");
        String message = (String)model.get("message");

        Throwable cause = getCause(request);
        String errorMessage = getErrorMessge(cause);

        LOG.error(status + "," + message, cause);

        response.setStatus(status);
        if(isJsonRequest(request)) {
            Map error = new HashMap();
            error.put("success", false);
            error.put("errorMessage", errorMessage);
            error.put("message", message);
            try {
                response.getWriter().print(JSONObject.toJSONString(error));
            } catch (Exception e){}
            return null;
        } else {
            ModelAndView view = new ModelAndView("error.html");
            view.addAllObjects(model);
            view.addObject("errorMessage", errorMessage);
            view.addObject("status", status);
            view.addObject("cause", cause);
            return view;
        }
    }

    private Throwable getCause(HttpServletRequest request) {
        Throwable error = (Throwable)request.getAttribute("javax.servlet.error.exception");
        if(error != null) {
            //MVC有可能会封装异常成ServletException，需要调用getCause获取真正的异常
            while((error instanceof ServletException) && error.getCause()!=null) {
                error = ((ServletException)error).getCause();
            }
        }

        return error;
    }

    private String getErrorMessge(Throwable ex) {
        //TODO:应用异常

        return "server error";
    }


    private static final Logger LOG = LoggerFactory.getLogger(ErrorController.class);
}
