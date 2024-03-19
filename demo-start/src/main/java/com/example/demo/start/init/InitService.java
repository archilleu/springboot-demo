package com.example.demo.start.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * @author cjy
 */
@Slf4j
@Service
public class InitService {
    @PostConstruct
    public void init() {
        log.info("初始化服务...");
    }
}
