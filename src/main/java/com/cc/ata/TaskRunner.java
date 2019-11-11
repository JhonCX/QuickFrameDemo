package com.cc.ata;

import com.cc.ata.entity.ServerSet;
import com.cc.ata.service.IServerSetService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;

import javax.annotation.Resource;

@Slf4j
public class TaskRunner implements ApplicationRunner, Ordered {
    @Resource
    IServerSetService serverService;

    @Override
    public int getOrder() {
      return 2;

    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ServerSet server = serverService.getById(1);
        boolean b = false;
        if (null == server) {
            server = new ServerSet();
            server.setId(1);
            server.setRegisterFlag(1);
            server.setLoginFlag(1);
             b = serverService.save(server);
        }else {
            server.setRegisterFlag(1);
            server.setLoginFlag(1);
             b = serverService.updateById(server);
        }
        if (b) {
            log.info("服务器启动成功~~");
        } else {
            log.warn("服务器启动失败~~");
        }
    }
  }
