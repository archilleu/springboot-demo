package com.hoya.core.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class EnableGlobalResultResponseController {
    @GetMapping("/success")
    public List<String> success() {
        List<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        return list;
    }
}
