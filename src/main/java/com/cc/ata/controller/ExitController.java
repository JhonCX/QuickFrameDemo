package com.cc.ata.controller;

import com.cc.ata.entity.ServerSet;
import com.cc.ata.service.IServerSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

@Component
@Slf4j
public class ExitController {
    @Resource
    IServerSetService serverService;

    @PreDestroy
    public void destory() {
        ServerSet server = new ServerSet();
        server.setId(1);
        boolean b = serverService.updateById(server);
        if (b) {
            log.info("大称号功能关闭成功~~");
        } else {
            log.info("大称号功能关闭失败~~");
        }
    }
}
