package com.cc.ata.controller;


import com.cc.ata.constant.Constant;
import com.cc.ata.constant.RestBean;
import com.cc.ata.entity.QUser;
import com.cc.ata.service.IQUserService;
import com.cc.ata.shiro.ShiroUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CC
 * @since 2019-10-20
 */
@Controller
public class UserInfoController {
    @Resource
    IQUserService userService;

    @RequiresRoles(Constant.ROLE_USER)
    @RequestMapping("/getNowUser")
    @ResponseBody
    public RestBean getNowUser() {
        Long id = (Long) SecurityUtils.getSubject().getPrincipal();
        QUser user = userService.getById(id);
        return new RestBean(Constant.SUCCESS_CODE, "", user.getEmail());
    }

    @RequiresRoles(Constant.ROLE_USER)
    @RequestMapping("/changePsw")
    @ResponseBody
    public RestBean changePsw(@RequestParam(name = "oldPsw") String oldPsw,
                              @RequestParam(name = "newPsw") String newPsw) {
        Subject subject = SecurityUtils.getSubject();
        Long id = (Long) subject.getPrincipal();
        QUser start = userService.getById(id);
        if (Objects.equals(oldPsw, start.getPwd())) {
            start.setPwd(newPsw);
            boolean b = userService.updateById(start);
            if (!b) {
                return new RestBean(Constant.FAIL_CODE, "密码修改失败！");
            } else {
                ShiroUtils.setUser(start);
                return new RestBean(Constant.SUCCESS_CODE, "密码修改成功！");
            }
        } else {
            return new RestBean(Constant.FAIL_CODE, "旧密码错误！");
        }

    }

    @RequestMapping("/userInfo")
    public String userInfo(Model model) {
        Subject subject = SecurityUtils.getSubject();
        Long id = (Long) subject.getPrincipal();

        QUser one = userService.getById(id);
        model.addAttribute("user", one);
        return "user/userInfo";
    }
}
