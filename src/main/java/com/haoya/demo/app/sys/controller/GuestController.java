package com.haoya.demo.app.sys.controller;

import com.haoya.demo.app.exception.AppExceptionServerError;
import com.haoya.demo.common.annotation.TryAgain;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/guest")
public class GuestController {

    @GetMapping("/index.html")
    public String sysMenu() {
        return "guest";
    }

    @TryAgain(tryTimes = 5,  handleException="ex", beforeExceptionalReturn="before", shouldRetry="retry")
    @GetMapping("test")
    public @ResponseBody String test() throws Exception{
        throw new Exception("test retry",null);
    }

    private void ex() {
        System.out.println("ex");
    }
    private void before() {
        System.out.println("before");
    }

    private boolean retry() {
        return true;
    }
}
