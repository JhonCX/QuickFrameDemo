package com.cc.ata.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cc.ata.common.utils.StringUtil;
import com.cc.ata.constant.Constant;
import com.cc.ata.constant.RestBean;
import com.cc.ata.entity.QUser;
import com.cc.ata.entity.ServerSet;
import com.cc.ata.service.IQUserService;
import com.cc.ata.service.IServerSetService;
import com.cc.ata.websocket.WebSocket;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.CredentialsException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author CC
 * @since 2019-10-19
 */
@Controller
public class LoginController {

    @Resource
    IQUserService userService;
    @Resource
    WebSocket webSocket;
    @Resource
    IServerSetService serverService;


    @RequestMapping("/")
    public String login(Model model, HttpServletResponse response) {
        return getIndex(model);
    }

    @RequestMapping("/index")
    @RequiresRoles(Constant.ROLE_USER)
    public String index(Model model) {
        return getIndex(model);
    }

    /*记住或已登录时 直接进入首页*/
    private String getIndex(Model model) {
        Subject subject = SecurityUtils.getSubject();
        if (subject.isRemembered()||subject.isAuthenticated()) {
            return "index";
        } else {
            return "login";
        }
    }

    @RequiresRoles(Constant.ROLE_USER)
    @RequestMapping(value = "/welcome")
    public String welcome() {
        return "welcome";
    }

    @RequestMapping("/getNotice")
    @ResponseBody
    @RequiresRoles(Constant.ROLE_USER)
    public void getNotice() {
        ServerSet server = serverService.getById(1);
        String message = server.getMessage();
        if (StringUtil.isNotBlank(message)) {
            webSocket.sendMessage(message);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String defaultLogin(Model model) {
        return getIndex(model);
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public RestBean login(@RequestParam(name = "email") String email,
                          @RequestParam(name = "password") String password) {
        ServerSet server = serverService.getById(1);
        if (Objects.equals(Constant.LOGIN_FORBID, server.getLoginFlag())) {
            return new RestBean(Constant.FAIL_CODE, "服务器暂时禁止登录！");
        }
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(email, password);
        // 执行认证登陆
        try {
            token.setRememberMe(true);//记住我
            subject.login(token);
        } catch (LockedAccountException lae) {
            return new RestBean(Constant.FAIL_CODE, "账户已锁定！");
        } catch (ExcessiveAttemptsException eae) {
            return new RestBean(Constant.FAIL_CODE, "输入错误次数过多！");
        } catch (CredentialsException ice) {
            return new RestBean(Constant.FAIL_CODE, "邮箱或密码错误！");
        } catch (RuntimeException re) {
            return new RestBean(Constant.FAIL_CODE, "系统错误！");
        }
        if (subject.isAuthenticated()) {
            return new RestBean(Constant.SUCCESS_CODE, "登录成功！");
        } else {
            token.clear();
            return new RestBean(Constant.FAIL_CODE, "邮箱或密码错误！");
        }
    }



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public RestBean register(@RequestParam(name = "email") String email,
                             @RequestParam(name = "password") String password,
                             @RequestParam(name = "nickName") String nickName) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        QUser one = userService.getOne(new QueryWrapper<>(new QUser().setEmail(email)));
        if (null != one) {
            return new RestBean(Constant.FAIL_CODE, "注册失败！");
        }

        QUser user = new QUser();
        user.setEmail(email);
        user.setPwd(password);
        user.setNickName(nickName);
        user.setType(Constant.ROLE_USER);
        user.setRegisterTime(sdf.format(new Date()));
        user.setFlag(Constant.LOGIN_ALLOW);
        boolean save = userService.save(user);
        if (!save) {
            return new RestBean(Constant.FAIL_CODE, "注册失败！");
        } else {
            return new RestBean(Constant.SUCCESS_CODE, "注册成功！");
        }
    }
}
