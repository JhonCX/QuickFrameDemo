package com.cc.ata.controller;

import com.cc.ata.constant.Constant;
import com.cc.ata.constant.RestBean;
import com.cc.ata.entity.ServerSet;
import com.cc.ata.service.IQUserService;
import com.cc.ata.service.IServerSetService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class ServerController {
    @Resource
    IServerSetService serverService;
    @Resource
    IQUserService userService;

    @RequiresRoles(Constant.ROLE_ADMIN)
    @RequestMapping("/toServer")
    public String toServer() {
        return "admin/server";
    }

    @RequiresRoles(Constant.ROLE_ADMIN)
    @RequestMapping("/getServerSet")
    @ResponseBody
    public RestBean getServerSet() {
        ServerSet one = serverService.getById(1);
        return new RestBean(Constant.SUCCESS_CODE, "", one);
    }


    @RequiresRoles(Constant.ROLE_ADMIN)
    @ResponseBody
    @RequestMapping("/updateServer")
    public RestBean updateServer(@RequestParam(value = "loginFlag") Integer loginFlag,
                                 @RequestParam(value = "registerFlag") Integer registerFlag,
                                 @RequestParam(value = "message") String message) {
        ServerSet server = new ServerSet();
        server.setId(1);
        server.setLoginFlag(loginFlag);
        server.setRegisterFlag(registerFlag);
        server.setMessage(message);
        boolean b = serverService.updateById(server);
        if (b) {
            return new RestBean(Constant.SUCCESS_CODE, "修改配置成功！");
        } else {
            return new RestBean(Constant.FAIL_CODE, "修改配置失败！");
        }
    }
}
