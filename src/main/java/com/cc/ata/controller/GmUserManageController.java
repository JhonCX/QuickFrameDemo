package com.cc.ata.controller;


import com.cc.ata.constant.Constant;
import com.cc.ata.constant.RestBean;
import com.cc.ata.entity.QUser;
import com.cc.ata.service.IQUserService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CC
 * @since 2019-10-24
 */
@Controller
public class GmUserManageController {

    @Resource
    IQUserService userService;

    @RequiresRoles(Constant.ROLE_ADMIN)
    @GetMapping("/toUserManage")
    public String toUserManage() {
        return "admin/user";
    }



    @RequiresRoles(Constant.ROLE_ADMIN)
    @GetMapping("/changeFlag")
    @ResponseBody
    public RestBean changeFlag(@RequestParam(value = "id") Long id,
                               @RequestParam(value = "flag") Integer flag) {
        QUser user = new QUser();
        user.setId(id);
        user.setFlag(flag);
        boolean b = userService.updateById(user);
        if (b) {
            return new RestBean(Constant.SUCCESS_CODE, flag == 0 ? "冻结成功！" : "解锁成功！");
        } else {
            return new RestBean(Constant.FAIL_CODE, "修改失败！");
        }
    }


    @RequiresRoles(Constant.ROLE_ADMIN)
    @GetMapping("/changeGm")
    @ResponseBody
    public RestBean changeGm(@RequestParam(value = "id") Long id,
                             @RequestParam(value = "type") String type) {
        QUser account = new QUser();
        account.setId(id);
        if (Constant.ROLE_ADMIN.equals(type)) {
            account.setType(Constant.ROLE_ADMIN);
        } else {
            account.setType(Constant.ROLE_USER);
        }
        boolean b = userService.updateById(account);
        if (b) {
            return new RestBean(Constant.SUCCESS_CODE, Objects.equals(type, Constant.ROLE_ADMIN) ? "分配到管理员权限！" : "分配到用户权限！");
        } else {
            return new RestBean(Constant.FAIL_CODE, "修改失败！");
        }
    }

    @RequiresRoles(Constant.ROLE_ADMIN)
    @GetMapping("/resetPsw")
    @ResponseBody
    public RestBean resetPsw(@RequestParam(value = "id") Long id) {
        QUser account = new QUser();
        account.setId(id);
        account.setPwd("123456");
        boolean b = userService.updateById(account);
        if (b) {
            return new RestBean(Constant.SUCCESS_CODE, "修改成功，默认密码为123456！");
        } else {
            return new RestBean(Constant.FAIL_CODE, "修改失败！");
        }
    }


    @RequiresRoles(Constant.ROLE_ADMIN)
    @PostMapping("/getUserList")
    @ResponseBody
    public RestBean getUserList() {
        List<QUser> userList = userService.list();
        return new RestBean(Constant.JSON_CODE, "成功获取用户数据!", userList);
    }
}
