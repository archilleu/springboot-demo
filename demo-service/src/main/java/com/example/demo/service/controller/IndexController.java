package com.example.demo.service.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author cjy
 */
@Slf4j
@Controller
@ApiIgnore
public class IndexController {

    /**
     * SwaggerUI
     */
    @GetMapping("/docs")
    public String swagger() {
        return "redirect:/swagger-ui.html";
    }

}