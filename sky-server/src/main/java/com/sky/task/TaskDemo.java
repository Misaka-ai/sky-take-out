package com.sky.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
public class TaskDemo {
    @Scheduled(cron = "*/5 * * * * ?")
    public void test(){
        log.info("9999999999999999666666666666666666");
    }
}
